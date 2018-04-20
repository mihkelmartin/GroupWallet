'use strict';


// tag::vars[]
const React = require('react');
const ReactDOM = require('react-dom');
var $ = require('jquery');

import {Event} from './event.js';

// tag::app[]
class App extends React.Component {

	constructor(props) {
		super(props);
		this.state = {events: [], email:' ', selectedEvent:''};
		this.handleEventSelected = this.handleEventSelected.bind(this);
		this.handleEmailChange = this.handleEmailChange.bind(this);
	}

    handleEventSelected(eventSelected){
        this.setState({
          selectedEvent: eventSelected
        });
    }
    handleEmailChange(email) {
        this.setState({
          email: email
        }, () => {
           var url = '/Event/find/email/' + this.state.email;
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
        const isEventSelect = this.state.selectedEvent === '';
        const result = isEventSelect ? (
            <div>
                <SearchBar currentEmail = {this.state.email} onEmailChange = {this.handleEmailChange}/>
                <EventList events={this.state.events} onEventSelected = {this.handleEventSelected}/>
            </div>

        ) :
        (
            <Event even/>
        )
        return (
            result
		)

	}
}
// end::app[]


// tag::employee-list[]
class EventList extends React.Component {

        render(){
           	var events = this.props.events.map(event =>
			<EventElement key={event.id} event={event} onEventSelected = {this.props.onEventSelected}/>
		);
		return (
			<table>
				<tbody>
					<tr>
						<th>Name</th>
						<th>PIN</th>
					</tr>
					{events}
				</tbody>
			</table>
		)
	}
}
// end::employee-list[]

// tag::event[]
class EventElement extends React.Component{
	constructor(props) {
		super(props);
		this.onEventClick = this.onEventClick.bind(this);
	}

	    onEventClick(e)
        {
            e.preventDefault();
            this.props.onEventSelected(this.props.event.id);
        }


	render() {
		return (
			<tr>
				<td><a href="" onClick={this.onEventClick}>{this.props.event.name}</a></td>
				<td><input type="number" name="PIN" /></td>
			</tr>
		)
	}
}
// end::event[]

// tag::search bar
class SearchBar extends React.Component {
	constructor(props) {
		super(props);
		this.onEmailTextChange = this.onEmailTextChange.bind(this);
	}

    onEmailTextChange(emailInput)
    {
        this.props.onEmailChange(emailInput.target.value);
    }

	render() {
		return (
		<div>
              <input type="text" name="E-mail" value = {this.props.email} onChange={this.onEmailTextChange}/>
        </div>
        )
	}
}


// tag::render[]
ReactDOM.render(
	<App/>,
 	document.getElementById('react')
)

// end::render[]