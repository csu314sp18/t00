import React, {Component} from 'react';
import Map from './Map';
import Itinerary from './Itinerary';

/* Trip computes the map an intinerary based on a set of destinations and options.
 * The destinations and options reside in the parent object so they may be set by
 * the Destinations and Options classes.
 * The map and itinerary reside in this object so they can be passed to the Map and Itinerary classes.
 */
class Trip extends Component {
  constructor(props) {
    super(props);

    this.plan = this.plan.bind(this);
    this.saveTFFI = this.saveTFFI.bind(this);
  }

  /* Sends a request to the server with the destinations and options.
   * Receives a response containing the map and itinerary to update the
   * state for this object.
   */
  fetchResponse(){
    // need to get the request body from the trip in state object.
    let requestBody = {
        "type"    : "trip",
        "title"   : "PLANNING",
        "options" : { 
          "distance":"kilometers",
          "optimization":"none"
        },
        "places"  : [
          {"id":"dnvr", "name":"Denver", "latitude": "39.739236", "longitude": "-104.990251"},
          {"id":"bldr", "name":"Boulder", "latitude": "40.014986", "longitude": "-105.270546"},
          {"id":"foco", "name":"Fort Collins", "latitude": "40.585260", "longitude": "-105.084423"},
          {"id":"grly", "name":"Greeley", "latitude": "40.423314", "longitude": "-104.709132"},
          {"id":"fomo", "name":"Fort Morgan", "latitude": "40.250258", "longitude": "-103.799951"},
          {"id":"frst", "name":"Firestone", "latitude": "40.112484", "longitude": "-104.936644"},
          {"id":"vail", "name":"Vail", "latitude": "39.640264", "longitude": "-106.374195"},
          {"id":"gunn", "name":"Gunnison", "latitude": "38.545825", "longitude": "-106.925321"},
          {"id":"stmb", "name":"Steamboat Springs", "latitude": "40.484977", "longitude": "-106.831716"},
          {"id":"aspn", "name":"Aspen", "latitude": "39.191098", "longitude": "-106.8175395"}
          ]
      };

    console.log(process.env.SERVICE_URL);
    console.log(requestBody);

    return fetch(process.env.SERVICE_URL + '/plan', {
      method:"POST",
      body: JSON.stringify(requestBody)
    });
  }

  async plan(){
    try {
      let serverResponse = await this.fetchResponse();
      let tffi = await serverResponse.json();
      console.log(tffi);
      this.props.updateTrip(tffi);
    } catch(err) {
      console.error(err);
    }
  }

  /* Saves the map and itinerary to the local file system.
   */
  saveTFFI(){
  }

  /* Renders the buttons, map, and itinerary.
   * The title should be specified before the plan or save buttons are valid.
   */
  render(){
    return(
        <div id="trip" className="card">
          <div className="card-header bg-info text-white">
            Trip
          </div>
          <div className="card-body">
            <p>Give your trip a title before planning or saving.</p>
            <div className="input-group" role="group">
              <span className="input-group-btn">
              <button className="btn btn-primary " onClick={this.plan} type="button">Plan</button>
            </span>
              <input type="text" className="form-control" placeholder="Trip title..."/>
              <span className="input-group-btn">
              <button className="btn btn-primary " onClick={this.saveTFFI} type="button">Save</button>
            </span>
            </div>
            <Map trip={this.props.trip} />
            <Itinerary trip={this.props.trip} />
          </div>
        </div>
    )
  }
}

export default Trip;