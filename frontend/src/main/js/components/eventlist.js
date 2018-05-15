'use strict';

// tag::vars[]
const React = require('react');

import EventElement from './eventelement.js';

// tag::EventList[]
class EventList extends React.Component {

        render(){
           	var events = this.props.events.map(event =>
			<EventElement key={event.id} event={event} onEventSelected = {this.props.onEventSelected}/>
		);
		return (
            <div className='ui three column centered grid'>
                <div className='column'>
                    <table className="ui collapsing celled table">
                        <tbody>
                            <tr>
                                <th className = "center aligned">Name</th>
                                <th className = "center aligned">PIN</th>
                            </tr>
                            {events}
                        </tbody>
                    </table>
                </div>
            </div>
		)
	}
}

export default EventList;
// end::EventList[]