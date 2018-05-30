'use strict';

// tag::vars[]
const React = require('react');
const ReactDOM = require('react-dom');
import {BrowserRouter, Switch, Route} from 'react-router-dom';

import MainDashBoard from './components/maindashboard.js';
import EventDashBoard from './components/eventdashboard.js';

var pin;

class Groupwallet extends React.Component {

    state = {selectedEvent:''};

    handleEventSelected = (eventSelected, pinGiven) => {
        this.setState({
          selectedEvent: eventSelected
        });
        pinGiven == "" ? pin = -1 : pin = pinGiven;
    }

	render() {
		return (
            <BrowserRouter>
                <Switch>
                  <Route exact path='/' render={(props) => <MainDashBoard{...props} onEventSelected = {this.handleEventSelected}/>}/>
                  <Route path='/Event/:eventId' render={(props) => <EventDashBoard{...props} eventId = {props.match.params.eventId} pin={pin}/>}/>
                </Switch>
            </BrowserRouter>
        )
	}
}

// tag::render[]
ReactDOM.render(
	<Groupwallet/>,
 	document.getElementById('react')
)

// end::render[]