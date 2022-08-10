package trade;

import errors.InsufficientFundsError;
import errors.InsufficientPositionsError;

public abstract interface Trade {
	
	public void execute(Position position) throws InsufficientFundsError, InsufficientPositionsError;
	
}
