package Model;

public class TransactionHeader {
	private String transactionID, userEmail, transactionDate;

	public TransactionHeader(String transactionID, String userEmail, String transactionDate) {
		super();
		this.transactionID = transactionID;
		this.userEmail = userEmail;
		this.transactionDate = transactionDate;
	}

	public String getTransactionID() {
		return transactionID;
	}

	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

}
