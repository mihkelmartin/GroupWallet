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
                <MemberList/>
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
		return (
            <div className='ui three column centered grid'>
                <div className='column'>
                    <Member/>
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
                <p>Mihkel Märtin Miku</p>
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

