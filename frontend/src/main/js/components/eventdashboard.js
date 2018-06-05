'use strict';

// tag::vars[]
const React = require('react');
var $ = require('jquery');

import Event from './event.js';
import MemberList from './memberlist.js';
import TransactionList from './transactionlist.js';

import {getBackEndUrl} from './getProperties';

// tag::EventDashBoard[]
class EventDashBoard extends React.Component {

	constructor(props) {
		super(props);
		this.state = {members: []};
	}

    componentDidMount() {
        var url = getBackEndUrl() + 'Members/' + this.props.eventId + '/' + this.props.token;
        console.log(url);
        $.ajax({
            url: url,
            dataType: 'json',
            cache: false,
            success: function(data) {
               this.setState({members : data});
            }.bind(this),
            error: function(xhr, status, err) {
                console.error(err.toString());
            }.bind(this)
        });
    }
 	render() {
		return (
		    <div>
		        <Event eventId = {this.props.eventId} token = {this.props.token}/>
                <div className="ui divider"></div>
                <MemberList eventId = {this.props.eventId} members={this.state.members}/>
                <div className="ui divider"></div>
                <TransactionList eventId = {this.props.eventId} token = {this.props.token} members={this.state.members}/>
                <div className="ui divider"></div>
		    </div>
		)
	}
}
export default EventDashBoard;
// end::EventDashBoard[]