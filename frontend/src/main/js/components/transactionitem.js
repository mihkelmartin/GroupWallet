const React = require('react');
var $ = require('jquery');
import {getBackEndUrl} from './getProperties';

var genCredit;

class TransactionItem extends React.Component {

    state = {debit : this.props.debit, credit : this.props.credit};

    onAddCredit = (e) => {
        e.preventDefault();
        // /Transactions/addCredit/{eventid}/{token}/{transactionid}/{memberid}/{credit}
        var url = getBackEndUrl() + 'Transactions/addCredit/' + this.props.eventId + '/' +
                    this.props.token + '/' + this.props.transactionId + '/' + this.props.memberId +
                    '/' + this.state.credit;
        console.log(url);
        $.ajax({
            url: url,
            dataType: 'text',
            cache: false,
            success: function(data){
               this.props.LoadTransactions();
            }.bind(this),
            error: function(xhr, status, err) {
                console.error(err.toString());
            }.bind(this)
        });
    }

    onAddDebit = (e) => {
        e.preventDefault();
        // /Transactions/addDebit/{eventid}/{token}/{transactionid}/{memberid}/{debit}
        var url = getBackEndUrl() + 'Transactions/addDebit/' + this.props.eventId + '/' +
                    this.props.token + '/' + this.props.transactionId + '/' + this.props.memberId +
                    '/' + this.state.debit;
        console.log(url);
        $.ajax({
            url: url,
            dataType: 'text',
            cache: false,
            success: function(data){
               this.props.LoadTransactions();
            }.bind(this),
            error: function(xhr, status, err) {
                console.error(err.toString());
            }.bind(this)
        });
    }
    onDebitChange = (e) => {
        this.setState({debit : e.target.value});
    }
    onCreditChange = (e) => {
        this.setState({credit : e.target.value});
    }

    static getDerivedStateFromProps(nextProps) {
        return {debit: nextProps.debit,
                credit: nextProps.credit
               }
    }

	render() {
        return (
            <td>
                <form onSubmit={this.onAddDebit}>
                    <div className="ui input">
                       <input type="number" value = {this.state.debit} onChange={this.onDebitChange}/>
                    </div>
                </form>
                <form onSubmit={this.onAddCredit}>
                    <div className="ui input">
                       <input type="number" value = {this.state.credit} onChange={this.onCreditChange}/>
                    </div>
                </form>
            </td>
	    )
	}
}
export default TransactionItem;
