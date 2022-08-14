package data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONObject;

public class TradeProducts {
	
	ArrayList<Product> products = null;
	APIData data;
	
	public TradeProducts() {
		products = new ArrayList<Product>();
		this.data = new APIData();
		parse(data);
	}
	
	public TradeProducts(APIData data) {
		this();
		this.data = data;
		parse(data);
	}
	
	
	public void parse(APIData data) {
		data.data.forEach((d) -> {
			Product prod = APIData.parseToProduct((JSONObject) d);
			products.add(prod);
		});
	}
	
	private static Comparator<Product> PriceCompare = new Comparator<Product>() {
		public int compare(Product p1, Product p2) {
			Double price1 = p1.price;
			Double price2 = p2.price;
			
			return price1.compareTo(price2);
		}
	};
	
	public void sortByPrice(boolean asc) {
		
		Comparator<Product> PriceCompare = new Comparator<Product>() {
			public int compare(Product p1, Product p2) {
				Double price1 = p1.price;
				Double price2 = p2.price;
				
				if (asc) {
					return price1.compareTo(price2);
					
				}
				return price2.compareTo(price1);
			}
		};
		products.sort(PriceCompare);
		
	}
	
	public void sortByTicker(boolean asc) {
	
	Comparator<Product> TickerCompare = new Comparator<Product>() {
		public int compare(Product p1, Product p2) {
			   String name1 = p1.ticker.toUpperCase();
			   String name2 = p2.ticker.toUpperCase();
			   if (asc) {
					return name1.compareTo(name2);
					
				}
				return name2.compareTo(name1);
			}
		};
		products.sort(TickerCompare);

	}
	
	public Product findTicker(String ticker) {
		for (int i = 0; i < products.size(); i++) {
			Product p = products.get(i);
			if (p.ticker.compareTo(ticker) == 0) {
				return p;
			}
		}
		return null;
	}
	
	public ArrayList<Product> getProducts() {
		return products;
	}
	
	public ArrayList<Product> refreshProducts() {
		parse(data);
		return products;
	}

}
