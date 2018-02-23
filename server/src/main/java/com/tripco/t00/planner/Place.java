package com.tripco.t00.planner;

/**
 * Describes the places to visit in a trip in TFFI format.
 * There may be other attributes of a place, but these are required to plan a trip.
 */
public class Place {
  private String id;
  private String name;
  private String latitude;
  private String longitude;


  public int milesTo(Place to) {
    final double R=3958.7613; // *1760.0; // for yards
    return (int) Math.round(R * vincenty(to));
  }

  public int kilometersTo(Place to) {
    final double R=6371.0088; // *1000;  // for meters
    return (int) Math.round(R * vincenty(to));
  }

  private double haversine(Place to) {
    double lat1 = radiansLatitude();
    double lon1 = radiansLongitude();
    double lat2 = to.radiansLatitude();
    double lon2 = to.radiansLongitude();
    double dLat = lat2 - lat1;
    double dLon = lon2 - lon1;
    double a = Math.pow(Math.sin(dLat / 2.0),2.0)
        + Math.pow(Math.sin(dLon / 2.0),2.0) * Math.cos(lat1) * Math.cos(lat2);
    double c = 2.0 * Math.atan2(Math.sqrt(a), Math.sqrt(1.0-a));
    return c;
  }

  private double vincenty(Place to) {
    double lat1 = radiansLatitude();
    double lon1 = radiansLongitude();
    double lat2 = to.radiansLatitude();
    double lon2 = to.radiansLongitude();
    double dlon = Math.abs(lon1 - lon2);
    double num1 = Math.cos(lat2)*Math.sin(dlon);
    double num2 = Math.cos(lat1)*Math.sin(lat2)-Math.sin(lat1)*Math.cos(lat2)*Math.cos(dlon);
    double num = Math.sqrt(Math.pow(num1, 2.0)+Math.pow(num2, 2));
    double den = Math.sin(lat1)*Math.sin(lat2)+Math.cos(lat1)*Math.cos(lat2)*Math.cos(dlon);
    double c = Math.atan2(num, den);
    return c;
  }

  private double chord(Place to) {
    double lat1 = radiansLatitude();
    double lon1 = radiansLongitude();
    double lat2 = to.radiansLatitude();
    double lon2 = to.radiansLongitude();
    double dx = Math.cos(lat2)*Math.cos(lon2) - Math.cos(lat1)*Math.cos(lon1);
    double dy = Math.cos(lat2)*Math.sin(lon2) - Math.cos(lat1)*Math.sin(lon1);
    double dz = Math.sin(lat2)-Math.sin(lat1);
    double chord = Math.sqrt(dx*dx + dy*dy + dz*dz);
    double c = 2.0*Math.asin(chord/2.0);
    return c;
  }



  public double radiansLatitude() {
    return Math.toRadians(degreesLatitude());
  }

  public double radiansLongitude() {
    return Math.toRadians(degreesLongitude());
  }

  public double degreesLatitude() {
    return degrees(latitude);
  }

  public double degreesLongitude() {
    return degrees(longitude);
  }

  private double degrees(String dms) {
    double dd = 0;
    String[] d = dms.split("°");
    dd = Double.parseDouble(d[0]);
    if (dms.contains("'")) {
      String[] m = d[1].split("'");
      dd += Double.parseDouble(m[0])/60.0;
      if (dms.contains("\"")) {
        String[] s = m[1].split("\"");
        dd += Double.parseDouble(s[0])/3600.0;
      }
    }
    if (dms.contains("S") || dms.contains("W"))
      dd = -dd;
    return dd;
  }

}
