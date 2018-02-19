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
    final double R=3958.7613;
    return (int) Math.round(R * arc(to));
  }

  public int kilometersTo(Place to) {
    final double R=6371.0088;
    return (int) Math.round(R * arc(to));
  }

  private double arc(Place to) {
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
