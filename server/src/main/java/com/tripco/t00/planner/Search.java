package com.tripco.t00.planner;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.tripco.t00.server.HTTP;
import spark.Request;

/**
 *
 */
public class Search {

  private Query query;

  /**
   *
   * @param request
   */
  public Search(Request request) {
    // first print the request
    System.out.println(HTTP.echoRequest(request));

    // extract the information from the body of the request.
    JsonParser jsonParser = new JsonParser();
    JsonElement requestBody = jsonParser.parse(request.body());

    // convert the body of the request to a Java class.
    Gson gson = new Gson();
    query = gson.fromJson(requestBody, Query.class);

    // execute the query.
    query.database();

    // log something.
    System.out.println(query);
  }

  /**
   *
   * @return
   */
  public String getResults() {
    Gson gson = new Gson();
    return gson.toJson(query);
  }
}
