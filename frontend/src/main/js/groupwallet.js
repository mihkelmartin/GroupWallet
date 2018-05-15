'use strict';

// tag::vars[]
const React = require('react');
const ReactDOM = require('react-dom');
import {BrowserRouter, Switch, Route} from 'react-router-dom';

import MainDashBoard from './components/maindashboard.js';
import EventDashBoard from './components/eventdashboard.js';


class Groupwallet extends React.Component {

    state = {selectedEvent:''};

    handleEventSelected = (eventSelected) => {
        this.setState({
          selectedEvent: eventSelected
        });
    }

	render() {
		return (
            <BrowserRouter>
                <Switch>
                  <Route exact path='/' render={(props) => <MainDashBoard{...props} onEventSelected = {this.handleEventSelected}/>}/>
                  <Route path='/Event' render={(props) => <EventDashBoard{...props} eventId = {this.state.selectedEvent}/>}/>
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