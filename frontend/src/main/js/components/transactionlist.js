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
                console.error(err.toString());
            }.bind(this)
        });
    }

	render() {
            var membernames = this.props.members.map( member => <div className = "two wide orange column center aligned" key={member.id}>{member.nickName}</div>);
            var transactions = this.props.transactions.map( transaction=>
                            <Transaction  key={transaction.id} eventId = {this.props.eventId}
                                        token = {this.props.token} transaction={transaction}
                                        LoadMembers={this.props.LoadMembers}/> );
		return (

                <div className= "ui container">
                    <div>
                        <div className="ui grid">
                            <div className="row">
                               <div className = "two wide orange column center aligned"><p> </p></div>
                               {membernames}
                            </div>
                               {transactions}
                        </div>
                        <div className='ui basic content center aligned segment'>
                            <button className='ui basic button icon' onClick={this.handleNewTransaction}>
                                <i className='plus icon' />
                            </button>
                        </div>
                    </div>
                </div>
		)
	}
}
export default TransactionList;