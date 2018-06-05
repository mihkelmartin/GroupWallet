const React = require('react');
var $ = require('jquery');

import Member from './member.js';
import {getBackEndUrl} from './getProperties';

class MemberList extends React.Component {

    handleNewMember = () => {
        var url = getBackEndUrl() + 'Members/add/' + this.props.eventId + '/' + this.props.token;
        console.log(url);
        $.ajax({
            url: url,
            dataType: 'text',
            cache: false,
            success: function(data) {
               this.props.LoadMembers();
               this.props.LoadTransactions();
            }.bind(this),
            error: function(xhr, status, err) {
                console.error(err.toString());
            }.bind(this)
        });
    }

	render() {
     	var members = this.props.members.map( (member) => <Member key={member.id} eventId = {this.props.eventId}
                                                                LoadMembers={this.props.LoadMembers}
                                                                LoadTransactions={this.props.LoadTransactions}
     	                                                       token = {this.props.token} member={member}/> );
		return (
            <div className='ui three column grid'>
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
                        <button className='ui basic button icon' onClick={this.handleNewMember}>
                            <i className='plus icon' />
                        </button>
                    </div>
                </div>
            </div>
		)
	}
}
export default MemberList;