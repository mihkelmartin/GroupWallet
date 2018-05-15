'use strict';

// tag::vars[]
const React = require('react');
import {Link} from 'react-router-dom';

// tag::EventElement[]
class EventElement extends React.Component{

    onEventClick = (e) => {
        this.props.onEventSelected(this.props.event.id);
    }

	render() {
		return (
			<tr>
				<td><Link to='/Event' onClick={this.onEventClick}>{this.props.event.name}</Link></td>
				<td><input id = "EventPIN" type="number" name="PIN" /></td>
			</tr>
		)
	}
}
export default EventElement;
// end::EventElement[]
