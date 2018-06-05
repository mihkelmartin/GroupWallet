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
			<tr>
				<td><input className='ui input' defaultValue={this.props.member.name}/></td>
				<td><input className='ui input' defaultValue={this.props.member.nickName}/></td>
				<td><input className='ui input' defaultValue={this.props.member.eMail}/></td>
				<td><input className='ui input' defaultValue={this.props.member.bankAccount}/></td>
				<td>
                    <div className='extra content'>
                        <span className='right floated trash icon'>
                            <i className='trash icon' onClick={this.handleDeleteMember}/>
                        </span>
                    </div>
                </td>
			</tr>
	    )
	}
}
export default Member;