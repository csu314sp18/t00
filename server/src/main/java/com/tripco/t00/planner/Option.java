package com.tripco.t00.planner;

/**
 * Describes the options to apply when planning a trip in TFFI format.
 * At this point we are only using the values provided.
 */
public class Option {

  private String distance;
  private String optimization;
  private String userUnit;
  private String userRadius;

  Option( String d, String o, String u, String r) {
    this.distance = d;
    this.optimization = o;
    this.userUnit = u;
    this.userRadius = r;
  }

  public boolean verify() {
    return (miles() || kilometers() || nauticalMiles() || userDefinedUnits())
        && (!userDefinedUnits() || (userRadius != null) && userRadius.matches("\\d+(\\.\\d+)"));
  }

  public boolean miles() {

    return distance.equals("miles");
  }

  public boolean kilometers() {

    return distance.equals("kilometers");
  }

  public boolean nauticalMiles() {
    return distance.equals("nautical miles");
  }

  public boolean userDefinedUnits() {
    return distance.equals("user defined");
  }

  public double userDefinedRadius() {
    return Double.parseDouble(userRadius);
  }

  public String optimization() {
    return optimization;
  }

  @Override
  public String toString() {
    return "options: { optimization:"+optimization+", distance:"+distance
      +", units:"+userUnit+", radius:"+userRadius+" }";
  }
}
