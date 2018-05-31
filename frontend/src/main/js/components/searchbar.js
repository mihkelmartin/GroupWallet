const React = require('react');

// tag::SearchBar
class SearchBar extends React.Component {

    onEmailTextChange = (evt) => {
        this.props.onEmailChange((this.refs.eventEmail.value));
        this.setNewFormVisibility(false);
        evt.preventDefault();
    }

    setNewFormVisibility = () => {
        this.props.setNewFormVisibility(false);
    }

	render() {
		return (
		<form onSubmit={this.onEmailTextChange}>
            <div className="ui centered header">
                <div className="icon input">
                    <input ref="eventEmail" type="text" placeholder="Event member e-mail..." value = {this.props.email}
                                            onFocus={this.setNewFormVisibility}/>
                    <i className="search icon" onClick={this.onEmailTextChange}></i>
                </div>
            </div>
        </form>
        )
	}
}

export default SearchBar;
// end::SearchBar[]