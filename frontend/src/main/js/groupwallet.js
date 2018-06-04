'use strict';

// tag::vars[]
const React = require('react');
const ReactDOM = require('react-dom');
var $ = require('jquery');

import {BrowserRouter, Switch, Route} from 'react-router-dom';

import MainDashBoard from './components/maindashboard.js';
import EventDashBoard from './components/eventdashboard.js';
import {getBackEndUrl} from './components/getProperties';

class Groupwallet extends React.Component {

    state = {selectedEvent:'', token:''};

    handleEventSelected = (eventSelected, pinGiven) => {
        var url = getBackEndUrl() + 'login/' + eventSelected + '/' + pinGiven;
        console.log(url);
        $.ajax({
            url: url,
            dataType: 'text',
            cache: false,
            success: function(data) {
                this.setState({token: data, selectedEvent: eventSelected});
            }.bind(this),
            error: function(xhr, status, err) {
                console.error(err.toString());
            }.bind(this)
        });

    }

	render() {
		return (
            <BrowserRouter>
                <Switch>
                  <Route exact path='/' render={(props) => <MainDashBoard{...props} onEventSelected = {this.handleEventSelected}/>}/>
                  <Route path='/Event/:eventId' render={(props) => <EventDashBoard{...props} eventId = {props.match.params.eventId} token={this.state.token}/>}/>
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