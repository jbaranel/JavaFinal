package data;

public class Product {
	String ticker;
	String name;
	double price;
	double price_change_24h;
	
	public Product(String ticker, String name) {
		this.ticker = ticker.toUpperCase();
		this.name = name;
		this.price = 0;
		this.price_change_24h = 0;
	}
	
	public Product(String ticker, String name, double price) {
		this(ticker, name);
		this.price = price;
	}
	
	public Product(String ticker, String name, double price, double price_change_24h) {
		this(ticker, name, price);
		this.price_change_24h = price_change_24h;
	}
	
	public String toString() {
		return "TICKER: " + this.ticker + ", Name: " + this.name + ", PRICE: $" + this.price + "," + this.price_change_24h+ "%";
	}

	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getPrice_change_24h() {
		return price_change_24h;
	}

	public void setPrice_change_24h(double price_change_24h) {
		this.price_change_24h = price_change_24h;
	}
	
	
		
}
