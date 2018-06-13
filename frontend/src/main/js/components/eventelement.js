'use strict';

// tag::vars[]
const React = require('react');
import {Link} from 'react-router-dom';

// tag::EventElement[]
class EventElement extends React.Component{

    onSubmit = (e) => {
        e.preventDefault();
        this.props.onEventSelected(this.props.event.id, this.refs.EventPIN.value);
    }

	render() {
		return (
			<div className="row" >
                    <div className = "three wide column center aligned">
                        <a>{this.props.event.name}</a>
                    </div>
                    <div className = "three wide column center aligned">
                    <form onSubmit={this.onSubmit}>
                        <div className = "ui fluid input">
                            <input ref="EventPIN" type="number" placeholder="Enter PIN and press ENTER"/>
                        </div>
                    </form>
				</div>
			</div>
		)
	}
}
export default EventElement;
// end::EventElement[]
