// tag::vars[]
const React = require('react');

class Error extends React.Component {


	render() {
		return (
		<div>
            <div className="ui centered header">
              <i className="ban icon"></i>
              <div className="content">
                {this.props.errorText}
              </div>
            </div>
        </div>
		)
	}
}

export default Error;

