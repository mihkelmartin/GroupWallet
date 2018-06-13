'use strict';

// tag::vars[]
const React = require('react');
const ReactDOM = require('react-dom');
var $ = require('jquery');

import {Router, Switch, Route} from 'react-router-dom';

import MainDashBoard from './components/maindashboard.js';
import EventDashBoard from './components/eventdashboard.js';
import createHistory from "history/createBrowserHistory"
const history = createHistory();

import {getBackEndUrl} from './components/getProperties';

class Groupwallet extends React.Component {

    state = {selectedEvent:''};

    handleEventSelected = (eventSelected, pin) => {
        if(pin){
            var url = getBackEndUrl() + 'login/' + eventSelected + '/' + pin;
            console.log(url);
            $.ajax({
                url: url,
                dataType: 'text',
                cache: false,
                success: function(data) {
                    history.push({ pathname: '/Event/'+ eventSelected + '/' + data});
                    this.setState({selectedEvent: eventSelected});
              }.bind(this),
                error: function(xhr, status, err) {
                    console.error(err.toString());
                }.bind(this)
            });
        }
    }
	render() {
		return (
            <Router history = {history}>
                <Switch>
                  <Route exact path='/' render={(props) => <MainDashBoard{...props} onEventSelected = {this.handleEventSelected}/>}/>
                  <Route path='/Event/:eventId/:token' render={(props) => <EventDashBoard{...props}
                        eventId = {props.match.params.eventId} token={props.match.params.token}/>}/>
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