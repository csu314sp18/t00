import React, {Component} from 'react';


/* Map obtains and renders the map for the trip.
 * Might be an SVG or KML contained in the server response.
 */
class Map extends Component {
  constructor(props){
    super(props);
  }

  render() {
   {
      let svgHeader='data:image/svg+xml;charset=UTF-8,';
      let svgData = this.props.trip.map;
      if (svgData == null || svgData == "")
        svgData="<svg width=\"1920\" height=\"20\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:svg=\"http://www.w3.org/2000/svg\"><g></g></svg>";

     return (
          <figure className="figure" id="map">
            <img className="figure-img img-fluid" alt="Map"
                 src={svgHeader.concat(svgData)}/>
          </figure>
      )
    }
  }
}

export default Map;