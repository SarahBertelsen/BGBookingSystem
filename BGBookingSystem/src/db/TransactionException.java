package db;

//Unchecked exception
public class TransactionException extends RuntimeException{
	public static final String COULD_NOT_COMPLETE = "Could not complete the transaction and it has been rolled back.";
	public static final String UNEXPECTED_ERROR = "Unexpected error in transaction.";

	public TransactionException(String message, Throwable clause) {
		super(message, clause);
	}
}
