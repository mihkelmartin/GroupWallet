'use strict';

// tag::vars[]
const React = require('react');

// tag::EventElement[]
class EventElement extends React.Component{

    onSubmit = (e) => {
        e.preventDefault();
        this.props.onSubmit(e.target.name, this.props.event.id, this.refs.EventPIN.value);
    }

	render() {
        var PINError;
        if(this.props.event.id === this.props.loginFailedEventID){
            PINError =  <div className="floating ui red label">Incorrect PIN!</div>;
        }

		return (
			<div className="row" >
                <div className = "three wide column">
                    <div className="ui card">
                        <h4 className="ui blue header">{this.props.event.name}</h4>
                        <a className="ui label mini left aligned" name="PUK" onClick={this.onSubmit}>Reset PIN with PUK</a>
                    </div>
                </div>
                <div className = "three wide column middle aligned">
                    <form name="PIN" onSubmit={this.onSubmit}>
                    <div className = "ui fluid input">
                        {PINError}
                        <input ref="EventPIN" type="number" placeholder="Enter PIN and press ENTER"/>
                    </div>
                    </form>
				</div>
			</div>
		)
	}
}
export default EventElement;
// end::EventElement[]
