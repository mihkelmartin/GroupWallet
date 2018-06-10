const React = require('react');
var $ = require('jquery');

import {getBackEndUrl} from './getProperties';

class Member extends React.Component {

    state = {member : {"id": "", "name": "", "nickName": "",
                       "bankAccount" : "", "order" : "", "payor" : "", "eMail" : ""},
             prevMember : {"id": "", "name": "", "nickName": "",
                           "bankAccount" : "", "order" : "", "payor" : "", "eMail" : ""}};

    handleDeleteMember = () => {
        var url = getBackEndUrl() + 'Members/remove/' + this.props.eventId + '/' +
                    this.props.token + '/' + this.props.member.id;
        console.log(url);
        $.ajax({
            url: url,
            dataType: 'text',
            cache: false,
            success: function(data){
               this.props.LoadMembers();
               this.props.LoadTransactions();
            }.bind(this),
            error: function(xhr, status, err) {
                console.error(err.toString());
            }.bind(this)
        });
    }

    handleUpdateMember = () => {
        var url = getBackEndUrl() + '/Members/update/' + this.props.eventId + '/' + this.props.token;
   	    console.log('before call' + this.state.member);
        $.ajax({
            url: url,
            type: "POST",
            contentType: 'application/json;charset=UTF-8',
            data: JSON.stringify(this.state.member),
            cache: false,
            success: function(data) {
         	    console.log('handleUpdateMember ' + this.state.member);
               this.props.LoadMembers();
               this.props.LoadTransactions()
            }.bind(this),
            error: function(xhr, status, err) {
                console.error(err.toString());
            }.bind(this)
        });
    }

    onNameChange = (e) => {
        var newName = e.target.value;
        this.setState(prevState => ({
          member: Object.assign({}, prevState.member, {name: newName})
        }), this.handleUpdateMember());
    }

    onNickNameChange = (e) => {
        var newNickName = e.target.value;
   	    console.log('var ' + newNickName);
        this.setState(prevState => ({
          member: Object.assign({}, prevState.member, {nickName: newNickName})
        }), this.handleUpdateMember());
    }

    static getDerivedStateFromProps(nextProps, prevState) {
        if(prevState.prevMember.name === nextProps.member.name)
            return null;
        return {member: nextProps.member,
                prevMember: nextProps.member}
    }

	render() {
	    console.log(this.state.member);
		return (
            <div className="row">
				<div className = "two wide orange column center aligned">
				    <div className = "ui fluid input">
				        <input type = "text" value={this.state.member.name} onChange = {this.onNameChange}/>
				    </div>
                </div>
				<div className = "two wide orange column center aligned">
				    <div className = "ui fluid input">
                        <input type = "text" value={this.state.member.nickName} onChange = {this.onNickNameChange}/>
                    </div>
                </div>
				<div className = "three wide orange column center aligned">
                    <div className = "ui fluid input">
                        <input type = "text" value={this.state.member.eMail}/>
                    </div>
                </div>
				<div className = "three wide orange column center aligned">
				    <div className = "ui fluid input">
                        <input type = "text" value={this.state.member.bankAccount}/>
                    </div>
                </div>
				<div className = "three wide orange column center aligned">
				    <div className = "ui fluid input">
                        <input type = "choice" defaultValue={this.props.member.payor}/>
                    </div>
                </div>
                <i className="trash icon" onClick={this.handleDeleteMember}></i>
			</div>
	    )
	}
}
export default Member;