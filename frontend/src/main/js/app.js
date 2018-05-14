'use strict';


// tag::vars[]
const React = require('react');
const ReactDOM = require('react-dom');
var $ = require('jquery');

import {BrowserRouter, Route, Switch, Link} from 'react-router-dom';
import {EventDashBoard} from './event.js';
import {getBackEndUrl} from './getProperties';


// tag::app[]
class App extends React.Component {

    state = {events: [], email:' '};
    handleEmailChange = (email) => {
        this.setState({
          email: email
        }, () => {
           var url = getBackEndUrl() + 'Event/find/email/' + this.state.email;
            $.ajax({
                url: url,
                dataType: 'json',
                cache: false,
                success: function(data) {
                    this.setState({events: data});
                }.bind(this),
                error: function(xhr, status, err) {
                    console.error(err.toString());
                }.bind(this)
            });
        });
    }
	render() {
        return (
            <div>
                <div className="ui divider"></div>
                <SearchBar currentEmail = {this.state.email} onEmailChange = {this.handleEmailChange}/>
                <div className="ui divider"></div>
                <EventListConditionalRender this={this}/>
            </div>
		)
	}
}
// end::app[]

function EventListConditionalRender(props){
    if(props.this.state.events.length != 0){
     return  <EventList events={props.this.state.events} onEventSelected = {props.this.props.onEventSelected}/>;
    }
    return (null);
}

// tag::employee-list[]
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
// end::employee-list[]

// tag::event[]
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
// end::event[]

// tag::search bar
class SearchBar extends React.Component {

    onEmailTextChange = (emailInput) => {
        this.props.onEmailChange(emailInput.target.value);
    }

	render() {
		return (
            <div className="ui centered header">
                <div className="icon input">
                    <input type="text" placeholder="Event member e-mail..." value = {this.props.email} onChange={this.onEmailTextChange}/>
                    <i className="search icon"></i>
                </div>
            </div>
        )
	}
}

class Main extends React.Component {

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
                  <Route exact path='/' render={(props) => <App{...props} onEventSelected = {this.handleEventSelected}/>}/>
                  <Route path='/Event' render={(props) => <EventDashBoard{...props} eventId = {this.state.selectedEvent}/>}/>
                </Switch>
            </BrowserRouter>
        )
	}
}

// tag::render[]
ReactDOM.render(
	<Main/>,
 	document.getElementById('react')
)

// end::render[]