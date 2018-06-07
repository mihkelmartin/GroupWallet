const React = require('react');
var $ = require('jquery');
import {getBackEndUrl} from './getProperties';

var genCredit;

class TransactionItem extends React.Component {

    state = {debit : this.props.debit,
             credit : this.props.credit,
             bcreditAutoCalculated : this.props.bcreditAutoCalculated};

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
    onChangeAutoCalc = (e) => {
        // /Transactions/setAutoCalc/{eventid}/{token}/{transactionid}/{memberid}/{bAutoCalc}
        var url = getBackEndUrl() + 'Transactions/setAutoCalc/' + this.props.eventId + '/' +
                    this.props.token + '/' + this.props.transactionId + '/' + this.props.memberId +
                    '/' + e.target.checked;
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
    onbcreditAutoCalculatedChange = (e) => {
        this.setState({bcreditAutoCalculated : e.target.checked});
        this.onChangeAutoCalc(e);
    }

    static getDerivedStateFromProps(nextProps) {
        return {debit: parseFloat(nextProps.debit).toFixed(2),
                credit: parseFloat(nextProps.credit).toFixed(2),
                bcreditAutoCalculated : nextProps.bcreditAutoCalculated
               }
    }

	render() {
        return (
            <td>
                <form onSubmit={this.onAddDebit}>
                    <div className="ui input">
                       <input type="number" step="0.01" min="0" max="999999.99" pattern="\d+(\.\d{2})?"
                                value = {this.state.debit} onChange={this.onDebitChange}/>
                    </div>
                </form>
                <form onSubmit={this.onAddCredit}>
                    <div className="ui input">
                       <input type="number" step="0.01" min="0" max="999999.99" pattern="\d+(\.\d{2})?"
                       value = {this.state.credit} onChange={this.onCreditChange}/>
                    </div>
                </form>
                <form>
                    <div className="ui input">
                       <input type="checkbox" checked={this.state.bcreditAutoCalculated}
                            onChange={this.onbcreditAutoCalculatedChange}/>
                       <label>Credit calculated?</label>
                    </div>
                </form>
            </td>
	    )
	}
}
export default TransactionItem;
