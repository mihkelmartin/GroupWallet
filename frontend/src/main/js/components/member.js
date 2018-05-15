const React = require('react');

class Member extends React.Component {

	render() {
		return (
			<tr>
				<td><input type="text" defaultValue={this.props.member.name}/></td>
				<td><input type="text" defaultValue={this.props.member.nickName}/></td>
				<td><input type="text" defaultValue={this.props.member.eMail}/></td>
				<td><input type="text" defaultValue={this.props.member.bankAccount}/></td>
				<td>
                    <div className='extra content'>
                        <span className='right floated trash icon'>
                            <i className='trash icon' />
                        </span>
                    </div>
                </td>
			</tr>
	    )
	}
}
export default Member;