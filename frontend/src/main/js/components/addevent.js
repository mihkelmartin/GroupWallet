// tag::vars[]
const React = require('react');
var $ = require('jquery');
var eventObject;
var captcha;

import ReCAPTCHA from 'react-google-recaptcha'
import {getBackEndUrl} from './getProperties';



class AddEvent extends React.Component {

	constructor(props) {
        super(props);
	}

    state = {msgText : 'Fill in and press Enter', eventname : '', email : ''};

    setResult = (data) => {
        if(data.id == null){
            this.setState({
                 msgText: 'Adding new event failed'
             });
             captcha.reset();

        } else {
             this.props.setNewFormVisibility(false);
             this.props.handleEmailChange(data.ownerEmail);
        }
    }
    onChange = (token) => {
        this.setState({
          msgText: 'Verifying...'
        });

        var url = getBackEndUrl() + '/Event/add/' + token;
        var eventJson = '{"name" : "' + this.refs.eventNameField.value +
                          '", "ownerEmail" : "' + this.refs.eMailField.value + '"}';
        $.ajax({
            url: url,
            type: "POST",
            contentType: 'application/json;charset=UTF-8',
            data: eventJson,
            cache: false,
            success: this.setResult,
            error: function(xhr, status, err) {
                console.error(err.toString());
            }.bind(this)
        });

    }

    onSubmit = (evt) => {
        captcha.execute();
        evt.preventDefault();
    }


	render() {
		return (
		<form onSubmit={this.onSubmit}>
            <div className='ui centered card'>
                <div className='content'>
                    <div className='header'>
                        <input ref="eventNameField" type="text" placeholder="Event name"/>
                        <input ref="eMailField" type="text" placeholder="e-mail"/>
                        <input type="submit" style={{display:"none"}}/>
                        <p>{this.state.msgText}</p>
                        <ReCAPTCHA
                          ref={(el) => { captcha = el; }}
                          size="invisible"
                          sitekey="6LdhaFwUAAAAAIorazYNYgkIr_sps2S2hrdMDxRa"
                          onChange={this.onChange}
                        />
                    </div>
                </div>
            </div>
        </form>
		)
	}
}

export default AddEvent;

