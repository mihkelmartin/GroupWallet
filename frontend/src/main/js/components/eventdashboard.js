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

	state = {members: [], transactions : []};
    LoadMembers = () => {
        var url = getBackEndUrl() + 'Members/' + this.props.eventId + '/' + this.props.token;
        $.ajax({
            url: url,
            dataType: 'json',
            cache: false,
            success: function(data) {
                data.sort(function(a,b) {return (a.order > b.order) ? 1 : ((b.order> a.order) ? -1 : 0);} );
                this.setState({members : data});
            }.bind(this),
            error: function(xhr, status, err) {
                console.error(err.toString());
            }.bind(this)
        });
    }
    LoadTransactions = () => {
        var url = getBackEndUrl() + 'Transactions/' + this.props.eventId + '/' + this.props.token;
        $.ajax({
            url: url,
            dataType: 'json',
            cache: false,
            success: function(data) {
                data.sort(function(a,b) {return (a.order > b.order) ? 1 : ((b.order> a.order) ? -1 : 0);} );
                this.setState({transactions : data});
            }.bind(this),
            error: function(xhr, status, err) {
                console.error(err.toString());
            }.bind(this)
        });
    }

    componentDidMount() {
        this.LoadMembers();
        this.LoadTransactions();
    }
 	render() {
		return (
		    <div>
		        <Event eventId = {this.props.eventId} token = {this.props.token}/>
                <div className="ui divider"></div>
                <MemberList eventId = {this.props.eventId} token = {this.props.token}
                    members={this.state.members}
                    LoadMembers={this.LoadMembers}
                    LoadTransactions={this.LoadTransactions}/>
                <div className="ui divider"></div>
                <TransactionList eventId = {this.props.eventId} token = {this.props.token}
                        members={this.state.members} transactions={this.state.transactions}
                        LoadMembers={this.LoadMembers}
                        LoadTransactions={this.LoadTransactions}/>
                <div className="ui divider"></div>
		    </div>
		)
	}
}
export default EventDashBoard;
// end::EventDashBoard[]