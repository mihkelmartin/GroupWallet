'use strict';

// tag::vars[]
const React = require('react');
const ReactDOM = require('react-dom');
const client = require('./client');
// end::vars[]

// tag::app[]
class App extends React.Component {

	constructor(props) {
		super(props);
		this.state = {events: []};
	}

	componentDidMount() {
		client({method: 'GET', path: '/Event/find/email/mihkelmartin@gmail.com'}).done(response => {
			this.setState({events: response.entity.events});
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
						<th>ID</th>
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
				<td>{this.props.event.id}</td>
				<td>{this.props.event.name}</td>
				<td>{this.props.event.PIN}</td>
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
