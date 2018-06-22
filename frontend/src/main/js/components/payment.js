const React = require('react');

class Payment extends React.Component {


	render() {

		return (
                <div className="row">
                    <div className = "two wide column left aligned">
                        {this.props.payment.payor}
                    </div>
                    <div className = "two wide column left aligned">
                        {this.props.payment.receiver}
                    </div>

                    <div className = "two wide column left aligned">
                        {this.props.payment.amount}
                    </div>
                    <div className = "two wide column left aligned">
                        {this.props.payment.bankAccount}
                    </div>
                    <div className = "two wide column left aligned">
                        {this.props.payment.receivereMail}
                    </div>
                </div>
	    )
	}
}
export default Payment;