'use strict';

// tag::vars[]
const React = require('react');
var $ = require('jquery');

import SearchBar from './searchbar.js';
import {EventListConditionalRender} from './eventlistconditionalrenderer.js';
import {getBackEndUrl} from './getProperties';

// tag::MainDashBoard[]
class MainDashBoard extends React.Component {

    state = {events: [], email:' '};
    handleEmailChange = (email) => {
        this.setState({
          email: email
        }, () => {
           var url = getBackEndUrl() + 'Event/find/email/' + this.state.email;
            $.ajax({
                url: url,
                dataType: 'json',
                cache: false,
                success: function(data) {
                    this.setState({events: data});
                }.bind(this),
                error: function(xhr, status, err) {
                    console.error(err.toString());
                }.bind(this)
            });
        });
    }
	render() {
        return (
            <div>
                <div className="ui divider"></div>
                <SearchBar currentEmail = {this.state.email} onEmailChange = {this.handleEmailChange}/>
                <div className="ui divider"></div>
                <EventListConditionalRender this={this}/>
            </div>
		)
	}
}

export default MainDashBoard;
// end::MainDashBoard[]