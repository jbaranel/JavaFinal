package errors;

public class InsufficientFundsError extends Exception{
	
	public InsufficientFundsError() {
		super("Insufficient funds in account");
	}
}
