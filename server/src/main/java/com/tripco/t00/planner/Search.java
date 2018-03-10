package com.tripco.t00.planner;

import com.tripco.t00.server.RestAPI;
import spark.Request;

/**
 *
 */
public class Search extends RestAPI {

  /**
   *
   * @param request
   */
  public Search(Request request) {

    super(request, Query.class);

    ((Query) object).database();
  }

}
