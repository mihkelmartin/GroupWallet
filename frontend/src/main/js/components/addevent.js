// tag::vars[]
const React = require('react');
var $ = require('jquery');
var eventObject;

import {getBackEndUrl} from './getProperties';

class AddEvent extends React.Component {

	render() {
		return (
            <div className='ui centered card'>
                <div className='content'>
                    <div className='header'>
                        <input type="text" placeholder="Event name ..."/>
                        <input type="text" placeholder="e-mail ..."/>
                    </div>
                </div>
            </div>
		)
	}
}

export default AddEvent;

