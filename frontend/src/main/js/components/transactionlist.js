const React = require('react');

var $ = require('jquery');

import Transaction from './transaction.js';
import {getBackEndUrl} from './getProperties';

class TransactionList extends React.Component {

    handleNewTransaction = () => {
        var url = getBackEndUrl() + 'Transactions/add/' + this.props.eventId + '/' + this.props.token;
        console.log(url);
        $.ajax({
            url: url,
            dataType: 'text',
            cache: false,
            success: function(data) {
               this.props.LoadTransactions();
            }.bind(this),
            error: function(xhr, status, err) {
                console.error(err.toString());
            }.bind(this)
        });
    }

	render() {
            var membernames = this.props.members.map( member => <th key={member.id} className = "center aligned">{member.nickName}</th>);
            var transactions = this.props.transactions.map( transaction=>
                            <Transaction  key={transaction.id} eventId = {this.props.eventId}
                                        token = {this.props.token} transaction={transaction}
                                        LoadTransactions={this.props.LoadTransactions}/> );
		return (
                <div>
                    <table className="ui collapsing celled table">
                        <tbody>
                            <tr>
     				           <td><p> </p></td>
                               {membernames}
                            </tr>
                               {transactions}
                        </tbody>
                    </table>
                    <div className='ui basic content center aligned segment'>
                        <button className='ui basic button icon' onClick={this.handleNewTransaction}>
                            <i className='plus icon' />
                        </button>
                    </div>
                </div>
		)
	}
}
export default TransactionList;