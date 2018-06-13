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
            <div className='ui grid center aligned'>
                <div className="row">
                    <div className = "three wide blue column center aligned">Name</div>
                    <div className = "three wide blue column center aligned">PIN</div>
                </div>
                {events}
            </div>
		)
	}
}

export default EventList;
// end::EventList[]