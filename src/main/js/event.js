// tag::vars[]
const React = require('react');
const ReactDOM = require('react-dom');
var $ = require('jquery');

// tag::Event[]
export class Event extends React.Component {

	constructor(props) {
		super(props);

	}
	render() {
	        console.log(this.props.eventId);

		return (
		    <p>Siia tuleb Event suurlehekülg. Event {this.props.eventId}</p>
		)
	}
}
// end::Event[]


