package account;

import java.util.ArrayList;

import data.Product;
import persist.RecordTrades;
import trade.Position;

public class TradeAccount implements Account{
	
	private ArrayList<Position> positions;
	private double balance;
	private double cash;
	private RecordTrades records;

	public TradeAccount() {
		this.cash = 0;
		this.balance = 0;
		this.positions = new ArrayList<Position>();
	}
	
	public TradeAccount(double cash) {
		this();
		this.cash = cash;
	}
	
	public TradeAccount(RecordTrades records) {
		this();
		this.records = records;
		this.cash = records.getAccountBalance();
		this.positions = records.getPositions();
	}

	public double getAccountBalance() {
		positions.forEach((position) -> {
			balance += position.getValue();
		});
		return balance;
	}
	
	public double getCashBalance() {
		return cash;
	}
	
	public void depositCash(double amount) {
		if (amount > 0) {
			this.cash += amount;
			Product cp = new Product("cash", "cash", amount);
			Position p = new Position(cp, 1);
			records.addTrade(p);
		}
	}
	
	public double withdrawCash(double amount) {
		if (amount > 0) {
			Product cp = new Product("cash", "cash", -amount);
			Position p = new Position(cp, 1);
			records.addTrade(p);
			
			this.cash -= amount;
			return amount;
		}
		else {
			return 0;
		}
	}

	
	public void addPosition(Position position) {
		this.positions.add(position);
		records.addTrade(position);
	}

	@Override
	public double positionQuantity(Position position) {
		double quantity = 0;
		for (int i = 0; i < positions.size(); i++) {
			Position p = positions.get(i);
			if (p.getTicker() == position.getTicker()) {
				quantity += p.getQuantity();
			}
		}
		return quantity;
	}

}
