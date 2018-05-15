const React = require('react');

class Transaction extends React.Component {

	render() {
	    var transactionitems = this.props.transaction.items.map(transactionitem =>
	         <td key={transactionitem.transactionId + transactionitem.memberId}>
	         <input type="number" defaultValue={transactionitem.debit}/>
	         <input type="number" defaultValue={transactionitem.credit}/></td>);
		return (
			<tr>
				<td><input type="text" defaultValue={this.props.transaction.name}/></td>
				{transactionitems}
			</tr>
	    )
	}
}
export default Transaction;
