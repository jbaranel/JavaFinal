package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

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

public class App extends JFrame {
	
	private static final int WIDTH = 500;
	private static final int HEIGHT = 500;
	private static DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.00");
	private static final int TEXT_WIDTH = 5;
	private ProductPanel productPanel;
	private  JPanel topPanel;
	private TradeAccount account;
	ProductDropDown productDropDown;
	JLabel accountLabel = new JLabel();
	TradeProducts products;
	
	public App() {
		Database db = new MySQLDatabase("CryptoData", "java", "");
		RecordTrades records = new RecordTrades(db);
		
		account = new TradeAccount(records);
				
		setTitle("Crypto Trader");
		setSize(WIDTH,HEIGHT);
		initialize();
		
		initTradePanel();
		initDepositPanel();
		
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		

		
	}
	
	public void initTradePanel() {
		JPanel tradePanel = new JPanel();
		JLabel quantityLabel = new JLabel("Qty:");
		JTextField quantityInput = new JTextField(App.TEXT_WIDTH);
		tradePanel.add(quantityLabel);
		tradePanel.add(quantityInput);
		JButton execute = new JButton("Execute");
		tradePanel.add(execute);
		add(tradePanel,BorderLayout.CENTER);
		
		JRadioButton buyButton = new JRadioButton("Buy");
		JRadioButton sellButton = new JRadioButton("Sell");

		tradePanel.add(buyButton);
		tradePanel.add(sellButton);
		
		buyButton.addActionListener((e) -> {
			if (sellButton.isSelected()) {
				sellButton.setSelected(false);
			}
		});
		
		sellButton.addActionListener((e) -> {
			if (buyButton.isSelected()) {
				buyButton.setSelected(false);
			}
		});
		
		execute.addActionListener((e) -> {
			Product selectedProduct = (Product) productDropDown.getSelectedItem();
			Integer quantity = Integer.parseInt(quantityInput.getText());
			
			if (buyButton.isSelected()) {
				Position trade = new Position(selectedProduct, quantity); 
				Buy buy = new Buy(account);
				try {
					buy.execute(trade);
				} catch (InsufficientFundsError e1) {
					JOptionPane.showMessageDialog(this, e1.getMessage(),
				               "Insufficient Funds", JOptionPane.ERROR_MESSAGE);
				}				
			}
			else if (sellButton.isSelected()) {
				Position trade = new Position(selectedProduct, -quantity); 

				Sell sell = new Sell(account);
				try {
					sell.execute(trade);
				} catch (InsufficientPositionsError e1) {
					JOptionPane.showMessageDialog(this, e1.getMessage(),
				               "Insufficient Positions", JOptionPane.ERROR_MESSAGE);
				}
			}
			
			Double accountBalance = account.getCashBalance();
			String balance = DECIMAL_FORMAT.format(accountBalance);
			accountLabel.setText("$" + balance);
			
			
		});
	}

	public void initDepositPanel() {
		accountLabel = new JLabel();
		
		JPanel depositPanel = new JPanel();
		JLabel depositLabel = new JLabel("Amount:");
		JTextField depositInput = new JTextField(App.TEXT_WIDTH);
		JButton depositButton = new JButton("Deposit");
		depositButton.addActionListener((e) -> {
			Double amount = Double.parseDouble(depositInput.getText());
			account.depositCash(amount);
			depositInput.setText("");
			Double accountBalance = account.getCashBalance();
			String balance = DECIMAL_FORMAT.format(accountBalance);
			accountLabel.setText("$" + balance);
		});
		
		Double accountBalance = account.getCashBalance();
		String balance = DECIMAL_FORMAT.format(accountBalance);
		accountLabel.setText("$" + balance);
		
		depositPanel.add(accountLabel);
		depositPanel.add(depositLabel);
		depositPanel.add(depositInput);
		depositPanel.add(depositButton);

		add(depositPanel,BorderLayout.SOUTH);
}
	
	public void initialize() {
		topPanel = new JPanel();
		JPanel dropdownPanel = new JPanel();
		productDropDown = new ProductDropDown();
		dropdownPanel.add(productDropDown);
		topPanel.add(dropdownPanel, BorderLayout.WEST);
		
		productPanel  = new ProductPanel(products.getProducts().get(0));
		topPanel.add(productPanel,BorderLayout.EAST);
		
		add(topPanel,BorderLayout.WEST);
		
	}
	
	class ProductDropDown extends JComboBox<Product>{
		
		public ProductDropDown() {
			setEditable(false);
			products = new TradeProducts();
			products.sortByTicker(true);
			products.getProducts().forEach((p) -> {
				addItem(p);			
			});
			
			
			addActionListener((e) -> {
				Product selectedProduct = (Product) this.getSelectedItem();
				productPanel.removeAll();
				productPanel = new ProductPanel(selectedProduct);
				topPanel.add(productPanel,BorderLayout.EAST);
				System.out.println(selectedProduct);
			});
		}
	}
	
	public class ProductPanel extends JPanel {
		
		String name;
		String ticker;
		Double price;
		Double price_change;
		Product product;
		
		JLabel nameLabel;
		JLabel tickerLabel;
		JLabel priceLabel;
		JLabel price_changeLabel;
		
		public ProductPanel() {
			nameLabel = new JLabel();
			tickerLabel = new JLabel();
			priceLabel = new JLabel();
			price_changeLabel = new JLabel();			
		}
		
		public ProductPanel(Product product) {
			this();
			this.name = product.getName();
			this.ticker = product.getTicker();
			this.price = product.getPrice();
			this.price_change = product.getPrice_change_24h();
			this.product = product;
			setPanel();
		}
		
		public ProductPanel(String name, String ticker, Double price, Double price_change) {
			this();
			this.name = name;
			this.ticker = ticker;
			this.price = price;
			this.price_change = price_change;
			setPanel();
		}
		
		public void setPanel() {
			nameLabel.setText(name);
			tickerLabel.setText(ticker);
			priceLabel.setText(String.format("$%.2f", price));
			price_changeLabel.setText(String.format("%.2f", price_change) + '%');
			setLayout(new GridLayout(4, 2));  
			// DECIMAL_FORMAT.format(value);
			add(new JLabel("Name:"));
			add(nameLabel);

			add(new JLabel("Ticker:"));
			add(tickerLabel);
			
			add(new JLabel("Price:"));
			add(priceLabel);
			
			add(new JLabel("Change:"));
			add(price_changeLabel);	
			
		}
	}
	
	public static void main(String args[]) {
		
		App gui = new App();
		
		
	}
}
