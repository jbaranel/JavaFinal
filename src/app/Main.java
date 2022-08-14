package app;
import account.TradeAccount;
import data.APIData;
import data.Product;
import data.TradeProducts;
import errors.InsufficientFundsError;
import errors.InsufficientPositionsError;
import persist.Database;
import persist.MySQLDatabase;
import persist.RecordTrades;
import trade.Buy;
import trade.Position;
import trade.Sell;

public class Main {
	
	public void test(TradeAccount account) { 
		Product product = new Product("BTC", "Bitcoin", 23002.34);
		Position position = new Position(product, 2);
		
		Buy buy = new Buy(account);
		try {
			buy.execute(position);
		} catch (InsufficientFundsError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Sell sell = new Sell(account);
		Position sellPos = new Position(product, -1);
		try {
			sell.execute(sellPos);
		} catch (InsufficientPositionsError e) {
			System.err.println(e);
		}
		
		System.out.println(account.getCashBalance());
	}
	
	public static void main(String args[]) {
		Database db = new MySQLDatabase("CryptoData", "java", "");
		RecordTrades records = new RecordTrades(db);
		
		TradeAccount account = new TradeAccount(records);
		account.depositCash(10000);
		
		
		APIData productAPI = new APIData();
		TradeProducts tradeProducts = new TradeProducts(productAPI);
		Product btc = tradeProducts.findTicker("ETH");
		Buy buy = new Buy(account);
		
		try {
			buy.execute(new Position(btc, 1));
		} catch (InsufficientFundsError e) {
			System.err.println(e);
		}		
		
		
		
		
	}
}
