const React = require('react');

var $ = require('jquery');

import Transaction from './transaction.js';
import {getBackEndUrl} from './getProperties';

class TransactionList extends React.Component {

    state = {transactions: []};

	render() {
            var membernames = this.props.members.map( member => <th key={member.id} className = "center aligned">{member.nickName}</th>);
            var transactions = this.props.transactions.map( transaction=> <Transaction  key={transaction.id} transaction={transaction}/> );
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
                        <button className='ui basic button icon' onClick={this.handleFormOpen}>
                            <i className='plus icon' />
                        </button>
                    </div>
                </div>
		)
	}
}
export default TransactionList;