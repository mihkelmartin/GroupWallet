'use strict';

// tag::vars[]
const React = require('react');
const ReactDOM = require('react-dom');
var $ = require('jquery');


// tag::Event[]
class Event extends React.Component {

	constructor(props) {
		super(props);

	}


	render() {
		return (
		    <div>
		    Siia tuleb Event suurlehekülg
		    </div>

		)
	}
}
// end::Event[]





// tag::render[]
ReactDOM.render(
   <Event />,
	document.getElementById('react')
)

// end::render[]