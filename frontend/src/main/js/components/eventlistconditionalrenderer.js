'use strict';


const React = require('react');
import EventList from './eventlist.js';

export function EventListConditionalRender(props){
    if(props.this.state.events.length != 0){
     return  <EventList events={props.this.state.events} onEventSelected = {props.this.props.onEventSelected}/>;
    }
    return (null);
}