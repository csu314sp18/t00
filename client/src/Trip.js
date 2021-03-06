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
    this.destroyClickedElement = this.destroyClickedElement.bind(this);
  }

  /* Sends a request to the server with the destinations and options.
   * Receives a response containing the map and itinerary to update the
   * state for this object.
   */
  fetchResponse(){
    const serverURL = "http://" + location.host + '/plan';
    return fetch(serverURL, {
      method:"POST",
      body: JSON.stringify(this.props.trip)
    });
  }

  async plan(){
    try {
      let serverResponse = await this.fetchResponse();
      console.log(serverResponse.headers);
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

    var textToSave = JSON.stringify(this.props.trip);
    var textToSaveAsBlob = new Blob([textToSave], {type:"text/plain"});
    var textToSaveAsURL = window.URL.createObjectURL(textToSaveAsBlob);
    var fileNameToSaveAs = "trip.json";

    var downloadLink = document.createElement("a");
    downloadLink.download = fileNameToSaveAs;
    downloadLink.innerHTML = "Download File";
    downloadLink.href = textToSaveAsURL;
    downloadLink.onclick = this.destroyClickedElement;
    downloadLink.style.display = "none";
    document.body.appendChild(downloadLink);

    downloadLink.click();
  }

  destroyClickedElement(event)
  {
    document.body.removeChild(event.target);
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
              <input type="text" className="form-control" placeholder="Trip title..." value={this.props.trip.title}/>
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