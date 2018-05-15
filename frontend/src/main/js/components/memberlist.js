const React = require('react');

import Member from './member.js';

class MemberList extends React.Component {

	render() {
     	var members = this.props.members.map( (member) => <Member key={member.id} member={member}/> );
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
                        <button className='ui basic button icon' onClick={this.handleFormOpen}>
                            <i className='plus icon' />
                        </button>
                    </div>
                </div>
            </div>
		)
	}
}
export default MemberList;