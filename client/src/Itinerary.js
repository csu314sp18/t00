import React, {Component} from 'react';

class Itinerary extends Component {
  constructor(props) {
    super(props);

    this.createTables = this.createTables.bind(this);
  }

  createTables () {
    let dests = this.props.trip.places.map((item) => <td>{item.name}</td>);
    dests.push(dests[0]);

    let dists = this.props.trip.distances.map((item) => <td>{item}</td>);
    dists.unshift( <td>0</td> );

    let sum = 0;
    let cum = [0];
    this.props.trip.distances.forEach(function(item){
      sum += item;
      cum.push(sum);
    });
    let cumdis = cum.map((item) => <td>{item}</td>);

    return {sum, dests, dists, cumdis };
  }

  render() {
    let units = this.props.trip.options.distance;
    let table = this.createTables();

    return(
        <div id="itinerary">
          <h4>Round trip distance of {table.sum} {units}. </h4>
          <table className="table table-responsive table-bordered">
            <thead>
            <tr className="table-info">
              <th className="align-middle">Destination</th>
              {table.dests}
            </tr>
            </thead>
            <tbody>
            <tr>
              <th className="table-info align-middle">{units}</th>
              {table.dists}
            </tr>
            <tr>
              <th className="table-info align-middle">Cumulative</th>
              {table.cumdis}
            </tr>
            </tbody>
          </table>
        </div>
    )
  }
}

export default Itinerary;
