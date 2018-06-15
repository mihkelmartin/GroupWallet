'use strict';

// tag::vars[]
const React = require('react');
const ReactDOM = require('react-dom');

import {Router, Switch, Route} from 'react-router-dom';

import MainDashBoard from './components/maindashboard.js';
import EventDashBoard from './components/eventdashboard.js';
import createHistory from "history/createBrowserHistory"
const history = createHistory();


class Groupwallet extends React.Component {

    state = {selectedEvent:''};

    handleEventSelected = (eventSelected, data) => {
        this.setState({selectedEvent: eventSelected});
        if(eventSelected !== '')
            history.push({ pathname: '/Event/'+ eventSelected + '/' + data});
        else
            history.replace({ pathname: '/'});
    }
	render() {
		return (
            <Router history = {history}>
                <Switch>
                  <Route exact path='/' render={(props) => <MainDashBoard{...props} onEventSelected = {this.handleEventSelected}/>}/>
                  <Route path='/Event/:eventId/:token' render={(props) => <EventDashBoard{...props}
                        eventId = {props.match.params.eventId} token={props.match.params.token}
                        onEventSelected = {this.handleEventSelected}/>}/>
                </Switch>
            </Router>
        )
	}
}

// tag::render[]
ReactDOM.render(
	<Groupwallet/>,
 	document.getElementById('react')
)

// end::render[]