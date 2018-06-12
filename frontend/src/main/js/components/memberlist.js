const React = require('react');
var $ = require('jquery');

import Member from './member.js';
import {getBackEndUrl} from './getProperties';

class MemberList extends React.Component {

    handleNewMember = () => {
        var url = getBackEndUrl() + 'Members/add/' + this.props.eventId + '/' + this.props.token;
         $.ajax({
            url: url,
            dataType: 'text',
            cache: false,
            success: function(data) {
               this.props.LoadMembers();
            }.bind(this),
            error: function(xhr, status, err) {
                console.error(err.toString());
            }.bind(this)
        });
    }

	render() {
     	var members = this.props.members.map( (member) => <Member key={member.id} eventId={this.props.eventId}
     	                                                        token = {this.props.token}
     	                                                        members={this.props.members}
     	                                                        member={member}
                                                                LoadMembers={this.props.LoadMembers}/> );
		return (
            <div>
                <div className= "ui container center aligned">
                    <div className="ui seven column padded grid">
                        <div className="row">
                            <div className = "three wide orange column">
                                <div className = "ui center aligned inverted orange raised segment">
                                    <h2 className = "ui teal large header">
                                        Name
                                    </h2>
                                </div>
                            </div>
                            <div className = "two wide orange column">
                                <div className = "ui center aligned inverted orange raised segment">
                                    <h2 className = "ui teal large header">
                                        Nickname
                                    </h2>
                                </div>
                            </div>
                            <div className = "three wide orange column">
                                <div className = "ui center aligned inverted orange raised segment">
                                    <h2 className = "ui teal large header">
                                        E-Mail
                                    </h2>
                                </div>
                            </div>
                            <div className = "three wide orange column">
                                <div className = "ui center aligned inverted orange raised segment">
                                    <h2 className = "ui teal large header">
                                        Bankaccount
                                    </h2>
                                </div>
                            </div>
                            <div className = "two wide orange column">
                                 <div className = "ui center aligned inverted orange raised segment">
                                     <h2 className = "ui teal large header">
                                         Payor
                                     </h2>
                                 </div>
                            </div>
                            <div className = "two wide orange column">
                                 <div className = "ui center aligned inverted orange raised segment">
                                     <h2 className = "ui teal large header">
                                         Saldo
                                     </h2>
                                 </div>
                            </div>
                        </div>
                        {members}
                    </div>
                </div>
                <div className='ui basic content center aligned segment'>
                    <button className='ui basic button icon' onClick={this.handleNewMember}>
                        Add member  <i className='plus icon' />
                    </button>
                </div>
            </div>
		)
	}
}
export default MemberList;