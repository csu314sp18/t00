package com.tripco.t00.planner;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.tripco.t00.server.HTTP;
import java.io.InputStreamReader;
import spark.Request;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The Trip class supports TFFI so it can easily be converted to/from Json by Gson.
 *
 */
public class Trip {
  // The variables in this class should reflect a trip TFFI.
  public String type;
  public String title;
  public Option options;
  public ArrayList<Place> places;
  public ArrayList<Integer> distances;
  public String map;

  /** The top level method that does planning.
   * At this point it just adds the map and distances for the places in order.
   * It might need to reorder the places in the future.
   */
  public void plan() {

    if (type == null || !type.equals("trip"))
      System.out.println("not a trip object");

    if (options == null) {
      options = new Option("miles","0.0", "", "");
    }
    System.out.println(options);

    this.map = svg();
    this.distances = legDistances();

  }

  /**
   * Returns an SVG containing the background and the legs of the trip.
   * @return
   */
  private String svg() {
    String map = "<svg\n"
        + "   xmlns:svg=\"http://www.w3.org/2000/svg\"\n"
        + "   xmlns=\"http://www.w3.org/2000/svg\"\n"
        + "   version=\"1.0\"\n"
        + "   width=\"1066.6073\"\n"
        + "   height=\"783.0824\"\n"
        + "   id=\"svg0001\"\n> "
        + "  <g>"+background()+"</g>\n"
        + "  <g>"+tour()+"</g>\n"
        + "</svg>\n";

    return map;
  }

  private String tour() {
    // uses a transform so we can simply specify latitude and longitude
    String border = "<polyline style=\"fill:none;stroke:blue;stroke-width:0.02\""
        + " points=\"-109,41 -102,41 -102,37 -109,37 -109,41\"/>\n";

    String path = "<polyline style=\"fill:none;stroke:purple;stroke-width:0.02\""
        + " points=\"";
    if (places != null) {
      for (int i = 0; i < places.size(); i++) {
        Place p = places.get(i);
        path += p.degreesLongitude() + "," + p.degreesLatitude() + " ";
      }
      path += places.get(0).degreesLongitude() + "," + places.get(0).degreesLatitude();
    }
    path += "\"/>\n";

    return "<svg\n >\n"
        + "   xmlns:svg=\"http://www.w3.org/2000/svg\"\n"
        + "   xmlns=\"http://www.w3.org/2000/svg\"\n"
        + "   version=\"1.0\"\n"
        + "   width=\"1066.6073\"\n"
        + "   height=\"783.0824\"\n"
        + "   id=\"svg0002\"\n> "
        + "  <g transform=\"matrix(142.14,0,0,-177.5,15528.57,7312.5)\">\n"
        + border
        + path
        + "  </g>\n"
        + "\n</svg>\n";
  }

  private String background() {
    InputStream is = getClass().getResourceAsStream("/colorado.svg");
    BufferedReader br = new BufferedReader(new InputStreamReader(is));
    String svg = "";  // to accumulate the svg
    try {
      for (String line = br.readLine(); line != null; line = br.readLine()) {
        svg += line;
      }
    } catch (IOException e)
    { };
    return svg;
  }

  /**
   * Returns the distances between consecutive places,
   * including the return to the starting point to make a round trip.
   * @return
   */
  private ArrayList<Integer> legDistances() {

    ArrayList<Integer> dist = new ArrayList<Integer>();
    double R = 0;
    if (options.userDefinedUnits())
      R = options.userDefinedRadius();

    if (places == null || places.size() == 0)
      return dist;
    else if (places.size() == 1) {
      dist.add(0);
    } else if (places.size() > 1) {
      for (int i = 1; i < places.size(); i++) {
        if (options.miles())
          dist.add(places.get(i - 1).milesTo(places.get(i)));
        else if (options.kilometers())
          dist.add(places.get(i - 1).kilometersTo(places.get(i)));
        else if (options.nauticalMiles())
          dist.add(places.get(i - 1).nauticalMilesTo(places.get(i)));
        else
          dist.add(places.get(i - 1).userDefinedTo(places.get(i),R));
      }
      if (options.miles())
        dist.add(places.get(places.size() - 1).milesTo(places.get(0)));
      else if (options.kilometers())
        dist.add(places.get(places.size() - 1).kilometersTo(places.get(0)));
      else if (options.nauticalMiles())
        dist.add(places.get(places.size() - 1).nauticalMilesTo(places.get(0)));
      else
        dist.add(places.get(places.size() - 1).userDefinedTo(places.get(0),R));
    }
    return dist;
  }

}