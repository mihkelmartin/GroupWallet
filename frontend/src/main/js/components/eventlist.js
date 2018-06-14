'use strict';

// tag::vars[]
const React = require('react');
var $ = require('jquery');
import {getBackEndUrl} from './getProperties';

import EventElement from './eventelement.js';
import ReCAPTCHA from 'react-google-recaptcha'

var captcha;
var loginType, eventid, pin;

// tag::EventList[]
class EventList extends React.Component {

    state = {loginFailedEventID : ''};

    onPINLogin = (token) => {
        if(pin){
            var url = getBackEndUrl() + 'login/' + eventid + '/' + pin + '/' + token;
            console.log(url);
            $.ajax({
                url: url,
                dataType: 'text',
                cache: false,
                success: function(data) {
                    if(data === "")
                        this.setState({loginFailedEventID : eventid});
                    else
                        this.props.onEventSelected(eventid, data);
              }.bind(this),
                error: function(xhr, status, err) {
                    console.error(err.toString());
                }.bind(this)
            });
        }
        captcha.reset();
    }

    onPUKLogin = (token) => {
        if(pin){
            var url = getBackEndUrl() + 'puk/' + eventid + '/' + pin + '/' + token;
            console.log(url);
            $.ajax({
                url: url,
                dataType: 'text',
                cache: false,
                success: function(data) {
                    this.setState({loginFailedEventID : ''});
              }.bind(this),
                error: function(xhr, status, err) {
                    console.error(err.toString());
                }.bind(this)
            });
        }
        captcha.reset();
    }

    onSubmit = (pLoginType, pEventid, pPin) => {
        loginType = pLoginType;
        eventid = pEventid;
        pin = pPin;
        captcha.execute();
    }

    onChange = (token) => {
        if(loginType === 'PIN')
            this.onPINLogin(token);
        if(loginType === 'PUK')
            this.onPUKLogin(token);
    }

    render(){
        var events = this.props.events.map(
            event => <EventElement key={event.id} event={event} pin={""}
                loginFailedEventID={this.state.loginFailedEventID} onSubmit={this.onSubmit}/>
        );
        return (
            <div className='ui grid center aligned'>
                {events}
                <ReCAPTCHA
                  ref={(el) => { captcha = el; }}
                  size="invisible"
                  sitekey="6LdhaFwUAAAAAIorazYNYgkIr_sps2S2hrdMDxRa"
                  onChange={this.onChange}
                />
            </div>
        )
    }
}

export default EventList;
// end::EventList[]