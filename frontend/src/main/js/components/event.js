// tag::vars[]
const React = require('react');
var $ = require('jquery');
var eventObject;

import {getBackEndUrl} from './getProperties';

class Event extends React.Component {

	constructor(props) {
        super(props);
		this.state = {eventName:''};
	    this.handleEventNameChange = this.handleEventNameChange.bind(this);

	}

	componentDidMount(){
        var url = getBackEndUrl() + 'Event/load/' + this.props.eventId + '/' + this.props.token;
        $.ajax({
            url: url,
            dataType: 'json',
            cache: false,
            success: function(data) {
               this.setState({eventName : data.name});
               eventObject = data;
            }.bind(this),
            error: function(xhr, status, err) {
                console.error(err.toString());
            }.bind(this)
        });

	}

    handleEventNameChange(event) {
        this.setState({eventName: event.target.value});
        eventObject.name = event.target.value
        var url = getBackEndUrl() + '/Event/update/' + this.props.token;
        $.ajax({
            url: url,
            type: "POST",
            contentType: 'application/json;charset=UTF-8',
            data: JSON.stringify(eventObject),
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

