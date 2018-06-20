const React = require('react');

var $ = require('jquery');

import Transaction from './transaction.js';
import {getBackEndUrl} from './getProperties';

class TransactionList extends React.Component {

    handleNewTransaction = () => {
        var url = getBackEndUrl() + 'Transactions/add/' + this.props.eventId + '/' + this.props.token;
        $.ajax({
            url: url,
            dataType: 'text',
            cache: false,
            success: function(data) {
               this.props.LoadMembers();
            }.bind(this),
            error: function(xhr, status, err) {
                this.props.handleRESTError(xhr);
            }.bind(this)
        });
    }

	render() {
            var membernames = this.props.members.map( member => <div className = "two wide blue column center aligned" key={member.id}>{member.nickName}</div>);
            var transactions = this.props.transactions.map( transaction=>
                            <Transaction  key={transaction.id} eventId = {this.props.eventId}
                                        token = {this.props.token} transaction={transaction}
                                        LoadMembers={this.props.LoadMembers}
                                        handleRESTError = {this.props.handleRESTError}/> );
		return (
            <div className= "ui container">
                <div className='ui basic content center aligned segment'>
                    <button className='ui basic green button icon' onClick={this.handleNewTransaction}>
                        Add transaction  <i className='plus icon' />
                    </button>
                </div>
                <div className="ui grid center aligned">
                    <div className="row">
                       <div className = "three wide blue column center aligned"><p></p></div>
                       {membernames}
                       <div className = "one wide blue column center aligned"><p></p></div>
                    </div>
                    {transactions}
                </div>
            </div>
		)
	}
}
export default TransactionList;