package errors;

public class InsufficientPositionsError extends Exception{
	
	public InsufficientPositionsError() {
		super("Insufficient positions held in account");
	}
}
