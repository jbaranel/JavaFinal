package data;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import org.json.*;

public class APIData {
	
	public JSONArray data;
	private String urlString;
	
	public APIData() {
		urlString = "https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd";
		data = fetch(urlString);
	}
	
	public APIData(String urlString) {
		data = fetch(urlString);
	}
	
	public static JSONArray fetch(String urlString) {	
		JSONArray data_obj = null;

		try {
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();
			if(conn.getResponseCode() == 200) {
				Scanner scan = new Scanner(url.openStream());
				while(scan.hasNext()) {
					String temp = scan.nextLine();
					data_obj = new JSONArray(temp);
					break;
				}
			}
		}
		catch (Exception e) {
			System.out.println(e);
		}
		return data_obj;
	}
	
	public static Product parseToProduct(JSONObject obj) {
		String name = obj.getString("name");
		String ticker = obj.getString("symbol");
		double price = obj.getDouble("current_price");
		double price_change_24h = obj.getDouble("price_change_percentage_24h");
		return new Product(ticker, name, price, price_change_24h);
	}
	
}
