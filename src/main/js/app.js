'use strict';

// tag::vars[]
const React = require('react');
const ReactDOM = require('react-dom');
//const client = require('./Client');
const axios = require('axios');
var message = 'Initial message';
// end::vars[]

// tag::app[]
class App extends React.Component {

	constructor(props) {
		super(props);
		this.state = {events: []};
	}

	componentDidMount() {
//	this.setState({events: ['{"id":"f6258d4c-56fa-4982-88fd-1a8b32764710","name":"Saariselkä 2018","pin":"9160"}']});
//  	client({method: 'GET', path: '/Event/find/email/mihkelmartin@gmail.com'}).done(response => {
//			this.setState({events: response.entity.events});
//		});

    fetch('/Event/find/email/mihkelmartin@gmail.com', {
        headers : {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
                   }})
      .then((response) => {response.text();console.log(response);})
      .then((responseJson) => {
        this.setState({
          events: ['{"idstr":"6ca4a784-3b37-481d-b6ae-01843cec6849","name":"Muremõtted 2019","pin":5652}']

        }); console.log(responseJson);
       })
      .catch( error => {console.log(error);
         });
    }

	render() {
		return (
            <EventList events={this.state.events}/>
		)
	}
}
// end::app[]

// tag::employee-list[]
class EventList extends React.Component{
	render() {
	     console.log(events);
		var events = this.props.events.map(event =>
			<Event key={event.idstr} event={event}/>
		);
		return (
			<table>
				<tbody>
					<tr>
						<th>Name</th>
						<th>PIN</th>
					</tr>
					{events}
				</tbody>
			</table>
		)
	}
}
// end::employee-list[]

// tag::event[]
class Event extends React.Component{
	render() {
		return (
			<tr>
				<td>{this.props.event.name}</td>
				<td>{this.props.event.pin}</td>
			</tr>
		)
	}
}
// end::event[]

// tag::render[]
ReactDOM.render(
   <App />,
	document.getElementById('react')
)

// end::render[]

function generateErrorHTMLOutput(error) {
  return  '<h4>Result</h4>' +
          '<h5>Message:</h5> ' +
          '<pre>' + error.message + '</pre>' +
          '<h5>Status:</h5> ' +
          '<pre>' + error.response.status + ' ' + error.response.statusText + '</pre>' +
          '<h5>Headers:</h5>' +
          '<pre>' + JSON.stringify(error.response.headers, null, '\t') + '</pre>' +
          '<h5>Data:</h5>' +
          '<pre>' + JSON.stringify(error.response.data, null, '\t') + '</pre>';
}

function generateSuccessHTMLOutput(response) {
  return  '<h4>Result</h4>' +
          '<h5>Status:</h5> ' +
          '<pre>' + response.status + ' ' + response.statusText + '</pre>' +
          '<h5>Headers:</h5>' +
          '<pre>' + JSON.stringify(response.headers, null, '\t') + '</pre>' +
          '<h5>Data:</h5>' +
          '<pre>' + JSON.stringify(response.data, null, '\t') + '</pre>';
}
