package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import data.*;

public class ProductDropDown extends JComboBox<Product>{
	TradeProducts products;
	
	public ProductDropDown() {
		setEditable(false);
		TradeProducts products = new TradeProducts();
		products.sortByTicker(true);
		products.getProducts().forEach((p) -> {
			addItem(p);			
		});
		
		//addActionListener(update);
	}

}
