package account;

import java.util.ArrayList;

import trade.Position;

public interface Account {
	
		public static final ArrayList<Position> positions = new ArrayList<Position>();

		public abstract double getAccountBalance();
		
		public abstract double getCashBalance();
		
		public void depositCash(double amount);
		
		public double withdrawCash(double amount);
		
		public void addPosition(Position position);
		
		public double positionQuantity(Position position);
		
}
