// tag::vars[]
const React = require('react');
const ReactDOM = require('react-dom');
var $ = require('jquery');

// tag::EventDashBoard[]
export class EventDashBoard extends React.Component {

	constructor(props) {
		super(props);
		this.state = {members: []};

        var url = '/Members/Event/' + this.props.eventId + '/PIN/9999';
        $.ajax({
            url: url,
            dataType: 'json',
            cache: false,
            success: function(data) {
               this.setState({members : data});
            }.bind(this),
            error: function(xhr, status, err) {
                console.error(err.toString());
            }.bind(this)
        });
	}

 	render() {
		return (
		    <div>
		        <Event eventId = {this.props.eventId}/>
                <div className="ui divider"></div>
                <MemberList eventId = {this.props.eventId} members={this.state.members}/>
                <div className="ui divider"></div>
                <TransactionList eventId = {this.props.eventId} members={this.state.members}/>
                <div className="ui divider"></div>
		    </div>
		)
	}
}
// end::EventDashBoard[]

export class Event extends React.Component {

	constructor(props) {
		super(props);
		this.state = {eventName:''};
	    this.handleEventNameChange = this.handleEventNameChange.bind(this);

        var url = '/Event/event/' + this.props.eventId + '/PIN/9999';
        $.ajax({
            url: url,
            dataType: 'json',
            cache: false,
            success: function(data) {
               this.setState({eventName : data.name});
            }.bind(this),
            error: function(xhr, status, err) {
                console.error(err.toString());
            }.bind(this)
        });
	}

    handleEventNameChange(event) {
        this.setState({eventName: event.target.value});
        var url = '/Event/update/' + this.props.eventId + '/name/' + event.target.value;
        $.ajax({
            url: url,
            dataType: 'json',
            cache: false,
            success: function(data) {
            }.bind(this),
            error: function(xhr, status, err) {
                console.error(err.toString());
            }.bind(this)
        });
    }

	render() {
		return (
            <div className='ui centered card'>
                <div className='content'>
                    <div className='header'>
                      <input type="text" value={this.state.eventName} onChange={this.handleEventNameChange}/>
                    </div>
                    <div className='extra content'>
                        <span className='right floated edit icon'>
                            <i className='edit icon' />
                        </span>
                        <span className='right floated trash icon'>
                            <i className='trash icon' />
                        </span>
                    </div>
                </div>
            </div>
		)
	}
}


export class MemberList extends React.Component {


	constructor(props) {
		super(props);
	}

	render() {
     	var members = this.props.members.map( (member) => <Member key={member.id} member={member}/> );
		return (
            <div className='ui three column centered grid'>
                <div className='column'>
                    <table className="ui collapsing celled table">
                        <tbody>
                            <tr>
                                <th className = "center aligned">Name</th>
                                <th className = "center aligned">Nickname</th>
                                <th className = "center aligned">E-mail</th>
                                <th className = "center aligned">Bankaccount</th>
                            </tr>
                            {members}
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

export class Member extends React.Component {

	constructor(props) {
		super(props);
	}

	render() {
		return (
			<tr>
				<td><input type="text" defaultValue={this.props.member.name}/></td>
				<td><input type="text" defaultValue={this.props.member.nickName}/></td>
				<td><input type="text" defaultValue={this.props.member.eMail}/></td>
				<td><input type="text" defaultValue={this.props.member.bankAccount}/></td>
			</tr>
	    )
	}
}

export class TransactionList extends React.Component {

	constructor(props) {
		super(props);
		this.state = {transactions: []};
	}

	componentDidMount(){
        var url = '/Transactions/Event/' + this.props.eventId + '/PIN/9999';
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
            <div className='ui three column centered grid'>
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

export class Transaction extends React.Component {

	constructor(props) {
		super(props);

	}
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

