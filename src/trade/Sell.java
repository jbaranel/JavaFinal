package trade;

import account.Account;
import errors.InsufficientPositionsError;

public class Sell implements Trade{
	
	Account account;
	
	public Sell(Account account) {
		this.account = account;
	}

	public void execute(Position position) throws InsufficientPositionsError{
		
		double totalPrice = position.getValue();
				
		if (totalPrice > 0) {
			throw new Error();
		}
		else if (account.positionQuantity(position) <= 0) {
			throw new InsufficientPositionsError();
		}
		else {
			account.depositCash(totalPrice);
			account.addPosition(position);
		}


	}

}
