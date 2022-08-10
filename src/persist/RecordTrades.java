package persist;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import data.Product;
import trade.Position;

public class RecordTrades {
	
	Database db;
	
	public RecordTrades(){
		this.db = new MySQLDatabase();
	}
	
	public RecordTrades(Database db) {
		this.db = db;
	}
	
	public void addTrade(Position position) {
		double price = position.getPrice();
		double qty = position.getQuantity();
		String ticker = position.getTicker();
		String name = position.getName();
		
		try {
			String q = String.format("('%s','%s',%f,%f)",ticker,name,price,qty);					
			db.insert("insert into trade values " + q);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public double getAccountBalance() {
		double balance = 0;
		try {
			ResultSet resultSet = db.query("(select sum(price) from trade where ticker = 'CASH')");
			while(resultSet.next()) {
				balance = resultSet.getDouble(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return balance;
	}
	
	public ArrayList<Position> getPositions() {
		ArrayList<Position> positions = new ArrayList<Position>();

		try {
			ResultSet resultSet = db.query("(select * "
					+ "FROM trade "
					+ "WHERE quantity > 0 "
					+ "AND ticker != 'CASH')");
			while(resultSet.next()) {
				String ticker = resultSet.getString(1);
				String name = resultSet.getString(2);
				double price = resultSet.getDouble(3);
				double quantity = resultSet.getDouble(4);

				Product product = new Product(ticker, name, price);
				Position position = new Position(product, quantity);
				positions.add(position);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return positions;
	}

}
