'use strict';


const React = require('react');
import EventList from './eventlist.js';
import AddEvent from './addevent.js';

export function EventListConditionalRender(props){

    let result = [];

    const plusbutton =  <div key ="plusButton" className="ui centered header">
                            <div className='ui basic content center aligned segment'>
                                <button className='ui basic button icon' onClick={setNewFormVisibility}>
                                    <i className='plus icon' />
                                </button>
                            </div>
                         </div>;

    const eventlist =  <EventList key ="eventList" events={props.this.state.events} onEventSelected = {props.this.props.onEventSelected}/>;

    const addevent = <AddEvent key ="addEvent" handleEmailChange={props.this.handleEmailChange}
                                setNewFormVisibility={props.this.setNewFormVisibility}/>;

    function setNewFormVisibility() {
        props.this.setNewFormVisibility(true);
     }


    if(props.this.state.newFormVisible)
        result.push(addevent);
    else
        result.push(plusbutton);

    if(props.this.state.events.length != 0){
        result.push(eventlist);
    }

    return (result);
}