const React = require('react');
import TransactionItem from './transactionitem.js';
var $ = require('jquery');
import ReactModal from 'react-modal';
import {getBackEndUrl, dialogStyles} from './getProperties';

ReactModal.setAppElement('#react');

class Transaction extends React.Component {

    state = {transaction : '',
             prevTransaction : '',
             bDeleteDialogOpen : false};

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
                this.props.handleRESTError(xhr);
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
                this.props.handleRESTError(xhr);
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

    onTransactionDelete = (e) => {
       this.setState({bDeleteDialogOpen : true});
    }

    closeModal = (e) => {
       this.setState({bDeleteDialogOpen : false});
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
	                         LoadMembers={this.props.LoadMembers}
	                         handleRESTError = {this.props.handleRESTError}/>)
	    return (
			<div className="row">
				<div className = "three wide grey column">
                    <div className = "ui fluid input">
                        <input type="text" name = "name" maxLength="48"
				            defaultValue={this.state.transaction.name} onChange = {this.onInputChange}/>
				    </div>
				</div>
				{transactionitems}
				<div className = "one wide grey column center aligned">
				    <i className="trash icon" onClick={this.onTransactionDelete}></i>
				</div>
                <ReactModal
                    isOpen={this.state.bDeleteDialogOpen}
                    onRequestClose={this.closeModal}
                    style={dialogStyles}
                    contentLabel='Delete Transaction?'>
                    <p>This will remove {this.state.transaction.name} from Event!</p>
                    <p>Remove {this.state.transaction.name}?</p>
                    <div className="ui two buttons">
                      <div className="ui basic blue button" onClick={this.handleDeleteTransaction}>Yes</div>
                      <div className="ui basic blue button" onClick={this.closeModal}>No</div>
                    </div>
                </ReactModal>
			</div>
	    )
	}
}
export default Transaction;
