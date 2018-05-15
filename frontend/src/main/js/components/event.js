// tag::vars[]
const React = require('react');
var $ = require('jquery');

import {getBackEndUrl} from './getProperties';

class Event extends React.Component {

	constructor(props) {
		super(props);
		this.state = {eventName:''};
	    this.handleEventNameChange = this.handleEventNameChange.bind(this);

        var url = getBackEndUrl() + 'Event/event/' + this.props.eventId + '/PIN/9999';
        $.ajax({
            url: url,
            dataType: 'json',
            cache: false,
            success: function(data) {
               this.setState({eventName : data.name});
            }.bind(this),
            error: function(xhr, status, err) {
                console.error(err.toString());
            }.bind(this)
        });
	}

    handleEventNameChange(event) {
        this.setState({eventName: event.target.value});
        var url = getBackEndUrl() + 'Event/update/' + this.props.eventId + '/name/' + event.target.value;
        $.ajax({
            url: url,
            dataType: 'json',
            cache: false,
            success: function(data) {
            }.bind(this),
            error: function(xhr, status, err) {
                console.error(err.toString());
            }.bind(this)
        });
    }

	render() {
		return (
            <div className='ui centered card'>
                <div className='content'>
                    <div className='header'>
                      <input type="text" value={this.state.eventName} onChange={this.handleEventNameChange}/>
                    </div>
                    <div className='extra content'>
                        <span className='right floated edit icon'>
                            <i className='edit icon' />
                        </span>
                        <span className='right floated trash icon'>
                            <i className='trash icon' />
                        </span>
                    </div>
                </div>
            </div>
		)
	}
}

export default Event;

