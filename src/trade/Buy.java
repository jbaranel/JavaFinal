package trade;

import account.Account;
import errors.InsufficientFundsError;

public class Buy implements Trade{
	
	Account account;
	
	public Buy(Account account) {
		this.account = account;
	}
	 
	public void execute(Position position) throws InsufficientFundsError{
		
		double totalPrice = position.getValue();
		
		double cashBalance = account.getCashBalance();
		
		if (cashBalance < totalPrice) {
			throw new InsufficientFundsError();
		}
		else {
			account.withdrawCash(totalPrice);
			account.addPosition(position);
			System.out.println(position);
		}

	}
}
