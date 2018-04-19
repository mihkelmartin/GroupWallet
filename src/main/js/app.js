'use strict';

// tag::vars[]
const React = require('react');
const ReactDOM = require('react-dom');
var $ = require('jquery');

// tag::app[]
class App extends React.Component {

	constructor(props) {
		super(props);
		this.state = {events: [], email:'mihkelmartin@gmail.com'};
	}


	componentDidMount() {
       var url = '/Event/find/email/' + this.state.email;
		$.ajax({
      		url: url,
      		dataType: 'json',
      		cache: false,
      		success: function(data) {
        		this.setState({events: data});
      		}.bind(this),
      		error: function(xhr, status, err) {
        		console.error(err.toString());
      		}.bind(this)
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
           	var events = this.props.events.map(event =>
			<Event key={event.id} event={event}/>
		);
		return (
			<table>
				<tbody>
					<tr>
						<th>Name</th>
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