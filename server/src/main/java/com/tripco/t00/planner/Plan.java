package com.tripco.t00.planner;

import com.tripco.t00.server.RestAPI;
import spark.Request;

import java.util.ArrayList;

/**
 * This class handles to the conversions of the trip request/resopnse,
 * converting from the Json string in the request body to a Trip object,
 * planning the Trip, and
 * converting the resulting Trip object back to a Json string
 * so it may returned as the response.
 */
public class Plan extends RestAPI {

  public Plan(Request request) {

    super(request, Trip.class);

    ((Trip) object).plan();
  }

}
