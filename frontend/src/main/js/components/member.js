const React = require('react');
var $ = require('jquery');

import {getBackEndUrl} from './getProperties';

class Member extends React.Component {

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

	render() {
		return (
            <div className="row">
				<div className = "column"><input className='ui input' defaultValue={this.props.member.name}/></div>
				<div className = "column"><input className='ui input' defaultValue={this.props.member.nickName}/></div>
				<div className = "column"><input className='ui input' defaultValue={this.props.member.eMail}/></div>
				<div className = "column"><input className='ui input' defaultValue={this.props.member.bankAccount}/></div>
				<td>
                    <div className='extra content'>
                        <span className='right floated trash icon'>
                            <i className='trash icon' onClick={this.handleDeleteMember}/>
                        </span>
                    </div>
                </td>
			</div>
	    )
	}
}
export default Member;