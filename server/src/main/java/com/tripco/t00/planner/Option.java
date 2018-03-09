package com.tripco.t00.planner;

/**
 * Describes the options to apply when planning a trip in TFFI format.
 * At this point we are only using the values provided.
 */
public class Option {

  private String distance;
  private String optimization;

  public boolean miles() {
    return distance.equals("miles");
  }

  public boolean kilometers() {

    return distance.equals("kilometers");
  }

  public String optimization() {
    return optimization;
  }

}
