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
        console.log(data);
        if(data == "success"){
            this.setState({
              msgText: 'Verified'
            });
        } else {
            this.setState({
                msgText: 'Bot test failed, try again'
            });
        }
    }
    onChange = (token) => {
        this.setState({
          msgText: 'Verifying...'
        });

        var url = getBackEndUrl() + token;
        $.ajax({
            url: url,
            dataType: 'text',
            cache: false,
            success: this.setResult,
            error: function(xhr, status, err) {
                console.error(err.toString());
            }.bind(this)
        });

    }

    onSubmit = (evt) => {
        evt.preventDefault();
        captcha.execute();
    }


	render() {
		return (
		<form onSubmit={this.onSubmit}>
            <div className='ui centered card'>
                <div className='content'>
                    <div className='header'>
                        <input type="text" placeholder="Event name"/>
                        <input type="text" placeholder="e-mail"/>
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

