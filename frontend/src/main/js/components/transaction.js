const React = require('react');
import TransactionItem from './transactionitem.js';
var $ = require('jquery');
import {getBackEndUrl} from './getProperties';

class Transaction extends React.Component {

    state = {transaction : '',
             prevTransaction : ''};

    handleDeleteTransaction = () => {
        var url = getBackEndUrl() + '/Transactions/remove/' + this.props.eventId + '/' +
                    this.props.token + '/' + this.props.transaction.id;
        $.ajax({
            url: url,
            dataType: 'text',
            cache: false,
            success: function(data){
               this.props.LoadMembers();
            }.bind(this),
            error: function(xhr, status, err) {
                console.error(err.toString());
            }.bind(this)
        });
    }

    handleUpdateTransaction = (newTransaction) => {
        var url = getBackEndUrl() + '/Transactions/update/' + this.props.eventId + '/' +
                    this.props.token;
        $.ajax({
            url: url,
            type: "POST",
            contentType: 'application/json;charset=UTF-8',
            data: JSON.stringify(newTransaction),
            cache: false,
            success: function(data) {
               this.props.LoadMembers()
            }.bind(this),
            error: function(xhr, status, err) {
                console.error(err.toString());
            }.bind(this)
        });
    }

    onInputChange = (e) => {
        const newTransaction = Object.assign({}, this.state.transaction);
        newTransaction[e.target.name] = e.target.value;
   	    this.setState({transaction: newTransaction});
        this.handleUpdateTransaction(newTransaction);
    }

    static getDerivedStateFromProps(nextProps, prevState) {
        if(prevState.prevTransaction.name === nextProps.transaction.name &&
           prevState.prevTransaction.order === nextProps.transaction.order &&
            prevState.prevTransaction.bmanualCalculation === nextProps.transaction.bmanualCalculation)
            return null;

        return {transaction: nextProps.transaction,
                prevTransaction: nextProps.transaction}
    }

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
	                         LoadMembers={this.props.LoadMembers}/>)
	    return (
			<div className="row">
				<div className = "three wide grey column stretched center aligned">
                    <div className = "ui fluid input">
                        <input type="text" name = "name"
				            defaultValue={this.state.transaction.name} onChange = {this.onInputChange}/>
				    </div>
				</div>
				{transactionitems}
				<div className = "one wide grey column center aligned">
				    <i className="trash icon" onClick={this.handleDeleteTransaction}></i>
				</div>
			</div>
	    )
	}
}
export default Transaction;
