const React = require('react');
var $ = require('jquery');
import {getBackEndUrl} from './getProperties';

class Member extends React.Component {

    state = {member : '',
             prevMember : ''};

    handleDeleteMember = () => {
        var url = getBackEndUrl() + 'Members/remove/' + this.props.eventId + '/' +
                    this.props.token + '/' + this.props.member.id;
        $.ajax({
            url: url,
            dataType: 'text',
            cache: false,
            success: function(data){
               this.props.LoadMembers();
            }.bind(this),
            error: function(xhr, status, err) {
                console.error(err.toString());
            }.bind(this)
        });
    }

    handleUpdateMember = (newMember) => {
        var url = getBackEndUrl() + '/Members/update/' + this.props.eventId + '/' + this.props.token;
        $.ajax({
            url: url,
            type: "POST",
            contentType: 'application/json;charset=UTF-8',
            data: JSON.stringify(newMember),
            cache: false,
            success: function(data) {
              this.props.LoadMembers();
            }.bind(this),
            error: function(xhr, status, err) {
                console.error(err.toString());
            }.bind(this)
        });
    }

    onInputChange = (e) => {
    console.log(e.target.name + ' value:' + e.target.value);
        const newMember = Object.assign({}, this.state.member);
        newMember[e.target.name] = e.target.value;
   	    this.setState({member: newMember});
        this.handleUpdateMember(newMember);
    }

    static getDerivedStateFromProps(nextProps, prevState) {
        if(prevState.prevMember.name === nextProps.member.name &&
           prevState.prevMember.nickName === nextProps.member.nickName &&
            prevState.prevMember.eMail === nextProps.member.eMail &&
            prevState.prevMember.bankAccount === nextProps.member.bankAccount &&
            prevState.prevMember.credit=== nextProps.member.credit &&
            prevState.prevMember.debit === nextProps.member.debit)
            return null;

        return {member: nextProps.member,
                prevMember: nextProps.member}
    }

	render() {
        var options = this.props.members.map( (member) => <option key={member.id} value={member.id}>
                                                            {member.name}
                                                          </option> );
		return (
            <div className="row">
				<div className = "three wide orange column center aligned">
				    <div className = "ui fluid input">
				        <input type = "text" name="name" value={this.state.member.name} onChange = {this.onInputChange}/>
				    </div>
                </div>
				<div className = "two wide orange column center aligned">
				    <div className = "ui fluid input">
                        <input type = "text" name="nickName" value={this.state.member.nickName} onChange = {this.onInputChange}/>
                    </div>
                </div>
				<div className = "three wide orange column center aligned">
                    <div className = "ui fluid input">
                        <input type = "text" name="eMail" value={this.state.member.eMail} onChange = {this.onInputChange}/>
                    </div>
                </div>
				<div className = "three wide orange column center aligned">
				    <div className = "ui fluid input">
                        <input type = "text" name="bankAccount" value={this.state.member.bankAccount} onChange = {this.onInputChange}/>
                    </div>
                </div>
				<div className = "two wide orange column center aligned">
				    <div className = "ui fluid input">
				        <select defaultValue={this.state.member.payor} name="payor" onChange = {this.onInputChange}>
				            {options}
				        </select>
                    </div>
                </div>
                <div className = "two wide orange column center aligned">
                    <p>{parseFloat(this.state.member.debit-this.state.member.credit).toFixed(2)}</p>
                </div>
                <i className="trash icon" onClick={this.handleDeleteMember}></i>
			</div>
	    )
	}
}
export default Member;