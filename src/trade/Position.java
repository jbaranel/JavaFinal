package trade;

import data.Product;

public class Position {
	
	double quantity;
	Product product;
	
	public Position(Product product) {
		this.product = product;
		this.quantity = 0;
	}
	
	public Position(Product product, double quantity) {
		this(product);
		this.quantity = quantity;
	}	
	
	public double getValue() {
		return product.getPrice() * quantity;
	}
	
	public String getTicker() {
		return product.getTicker();
	}
	
	public String getName() {
		return product.getName();
	}
	
	public double getPrice() {
		return product.getPrice();
	}
	
	public double getQuantity() {
		return quantity;
	}
	
	
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
	
	public String toString() {
		return "TICKER: " + this.getTicker() + ", QTY: " + this.quantity + ", PRICE: $" + this.getPrice();
	}

}
