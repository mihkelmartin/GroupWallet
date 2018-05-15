const React = require('react');

var $ = require('jquery');

import Transaction from './transaction.js';
import {getBackEndUrl} from './getProperties';

class TransactionList extends React.Component {

    state = {transactions: []};

	componentDidMount(){
        var url = getBackEndUrl() + 'Transactions/Event/' + this.props.eventId + '/PIN/9999';
        $.ajax({
            url: url,
            dataType: 'json',
            cache: false,
            success: function(data) {
               this.setState({transactions: data});
            }.bind(this),
            error: function(xhr, status, err) {
                console.error(err.toString());
            }.bind(this)
        });
	}

	render() {
            var membernames = this.props.members.map( member => <th key={member.id} className = "center aligned">{member.nickName}</th>);
            var transactions = this.state.transactions.map( transaction=> <Transaction  key={transaction.id} transaction={transaction}/> );
		return (
            <div className='ui three column grid'>
                <div className='column'>
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
                        <button className='ui basic button icon' onClick={this.handleFormOpen}>
                            <i className='plus icon' />
                        </button>
                    </div>
                </div>
            </div>
		)
	}
}
export default TransactionList;