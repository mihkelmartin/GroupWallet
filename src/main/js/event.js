// tag::vars[]
const React = require('react');
const ReactDOM = require('react-dom');
var $ = require('jquery');

// tag::EventDashBoard[]
export class EventDashBoard extends React.Component {

	constructor(props) {
		super(props);

	}
	render() {
		return (
		    <div>
		        <Event eventId = {this.props.eventId}/>
                <div className="ui divider"></div>
                <MemberList eventId = {this.props.eventId}/>
                <div className="ui divider"></div>
                <TransactionList/>
                <div className="ui divider"></div>
		    </div>
		)
	}
}
// end::EventDashBoard[]

export class Event extends React.Component {

	constructor(props) {
		super(props);

	}
	render() {
		return (
            <div className='ui centered card'>
                <div className='content'>
                    <div className='header'>
                      <input type="text" defaultValue = {this.props.eventId} onChange={this.onEmailTextChange}/>
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
	    var url = '/Members/Event/' + this.props.eventId + '/PIN/9999';
	    var membersdb = [];
        $.ajax({
            url: url,
            dataType: 'json',
            cache: false,
            success: function(data) {
                for(var i = 0, len = data.length; i < len; ++i)
                    membersdb.push(data[i]);
            }.bind(this),
            error: function(xhr, status, err) {
                console.error(err.toString());
            }.bind(this)
        });

        var memberscpy = Array.from(membersdb);
        console.log(membersdb);
        console.log(memberscpy);
        var members = memberscpy.map( (member) => <Member key={member.id} member={member}/> );
        console.log(members);
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
				<td><input type="text" value={this.props.member.name}/></td>
				<td><input type="text" value={this.props.member.nickName}/></td>
				<td><input type="text" value={this.props.member.eMail}/></td>
				<td><input type="text" value={this.props.member.bankAccount}/></td>
			</tr>
	    )
	}
}

export class TransactionList extends React.Component {

	constructor(props) {
		super(props);

	}
	render() {
		return (
            <div className='ui three column centered grid'>
                <div className='column'>
                    <Transaction/>
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
		return (
                <p>Esimene poeskäik</p>
		)
	}
}

