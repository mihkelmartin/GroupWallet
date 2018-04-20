'use strict';

// tag::vars[]
const React = require('react');
const ReactDOM = require('react-dom');
var $ = require('jquery');
const event = require('./event.js');


// tag::app[]
class App extends React.Component {

	constructor(props) {
		super(props);
		this.state = {events: [], email:' '};
		this.handleEmailChange = this.handleEmailChange.bind(this);
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
            console.log(this.state.email)
        });
    }
	render() {
		return (
		    <div>
                <SearchBar currentEmail = {this.state.email} onEmailChange = {this.handleEmailChange}/>
                <EventList events={this.state.events}/>
            </div>
		)
	}
}
// end::app[]


// tag::employee-list[]
class EventList extends React.Component {

        render(){
           	var events = this.props.events.map(event =>
			<EventElement key={event.id} event={event}/>
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
	render() {
		return (
			<tr>
				<td><a href="event.js">{this.props.event.name}</a></td>
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
   <App />,
	document.getElementById('react')
)

// end::render[]