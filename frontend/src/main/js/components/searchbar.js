const React = require('react');

// tag::SearchBar
class SearchBar extends React.Component {

    onEmailTextChange = (emailInput) => {
        this.props.onEmailChange(emailInput.target.value);
    }

	render() {
		return (
            <div className="ui centered header">
                <div className="icon input">
                    <input type="text" placeholder="Event member e-mail..." value = {this.props.email} onChange={this.onEmailTextChange}/>
                    <i className="search icon"></i>
                </div>
            </div>
        )
	}
}

export default SearchBar;
// end::SearchBar[]