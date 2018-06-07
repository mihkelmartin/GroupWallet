const React = require('react');
import TransactionItem from './transactionitem.js';

class Transaction extends React.Component {

	render() {
	    var transactionitems = this.props.transaction.items.map(transactionitem =>
	        <TransactionItem key={transactionitem.memberId}
	                         eventId = {this.props.eventId}
	                         token = {this.props.token}
	                         transactionId = {transactionitem.transactionId}
	                         memberId={transactionitem.memberId}
	                         bcreditAutoCalculated={transactionitem.bcreditAutoCalculated}
                             debit = {transactionitem.debit}
	                         credit = {transactionitem.credit}
	                         LoadTransactions={this.props.LoadTransactions}/>)
	    return (
			<tr>
				<td><input type="text" defaultValue={this.props.transaction.name}/></td>
				{transactionitems}
			</tr>
	    )
	}
}
export default Transaction;
