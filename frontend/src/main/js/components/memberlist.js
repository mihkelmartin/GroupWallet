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
                <div className= "ui container">
                    <div className="ui seven column padded grid">
                        <div className="row">
                            <div className = "three wide green column">
                                <div className = "ui center aligned inverted green raised segment">
                                    <h3 className = "ui header">
                                        Name
                                    </h3>
                                </div>
                            </div>
                            <div className = "two wide green column">
                                <div className = "ui center aligned inverted green raised segment">
                                    <h3 className = "ui header">
                                        Nickname
                                    </h3>
                                </div>
                            </div>
                            <div className = "three wide green column">
                                <div className = "ui center aligned inverted green raised segment">
                                    <h3 className = "ui header">
                                        e-mail
                                    </h3>
                                </div>
                            </div>
                            <div className = "three wide green column">
                                <div className = "ui center aligned inverted green raised segment">
                                    <h3 className = "ui header">
                                        IBAN
                                    </h3>
                                </div>
                            </div>
                            <div className = "two wide green column">
                                 <div className = "ui center aligned inverted green raised segment">
                                     <h3 className = "ui header">
                                         Payor
                                     </h3>
                                 </div>
                            </div>
                            <div className = "two wide green column">
                                 <div className = "ui center aligned inverted green raised segment">
                                     <h3 className = "ui header">
                                         Saldo
                                     </h3>
                                 </div>
                            </div>
                            <div className = "one wide green column center aligned"><p></p></div>
                        </div>
                        {members}
                    </div>
                </div>
                <div className='ui basic content center aligned segment'>
                    <button className='ui basic green button icon' onClick={this.handleNewMember}>
                        Add member  <i className='plus icon' />
                    </button>
                </div>
            </div>
		)
	}
}
export default MemberList;