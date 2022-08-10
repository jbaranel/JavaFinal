package gui;

import javax.swing.JFrame;
import data.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

@SuppressWarnings("serial")
public class Encoder extends JFrame {
	
	private JTextField textInput;
	private JTextField textOutput;

	private JButton clearButton;
	private JComboBox<String> dropdownBox;
	
	private static final int TEXT_WIDTH = 10;

	public Encoder() {
		setSize(500,500);
		createPanel();
	}
	
	public String encodeNumeric(String s) {
		String encoded = new String("");
		char[] c = s.toCharArray();
		for (int i=0; i < c.length; i++) {	
			if (c[i] <= 'z' && c[i] >= 'a') {
				encoded += (c[i] - 'a' + 1) + ".";
			}
		}
		
		return encoded;
	}
	
	public String encodeROT13(String s) {
		char[] c = s.toCharArray();
		for (int i=0; i < c.length; i++) {	
			if (c[i] <= 'z' && c[i] >= 'a') {
				int n = ((c[i] - 'a') + 13) % 26 + 'a';
				c[i] = (char) n;
			}
		}
				
		return new String(c);
	}
	
	class UpdateValues implements ActionListener {
		
	    public void actionPerformed(ActionEvent e) {
	    	String input = textInput.getText();
			String encoded = "";
			APIData productAPI = new APIData();
			TradeProducts tradeProducts = new TradeProducts(productAPI);
			String ticker = dropdownBox.getSelectedItem().toString();
			Product product  = tradeProducts.findTicker(ticker);

			textOutput.setText((product.toString()));
	    };

	}
	
	public void createPanel() {
		textInput = new JTextField(Encoder.TEXT_WIDTH);
		textOutput = new JTextField(Encoder.TEXT_WIDTH);
		
		clearButton = new JButton("Clear");
		clearButton.addActionListener((e) -> {
			textInput.setText("");
			textOutput.setText("");
		});
		
		dropdownBox = new JComboBox<String>();
		dropdownBox.setEditable(false);
		
		APIData productAPI = new APIData();
		TradeProducts tradeProducts = new TradeProducts(productAPI);
		
		tradeProducts.sortByTicker(true);
		tradeProducts.getProducts().forEach((p) -> {
			dropdownBox.addItem(p.getTicker());
			
		});
				
		UpdateValues update = new UpdateValues();
		
		dropdownBox.addActionListener(update);
		
		textInput.addCaretListener((e) -> {
			update.actionPerformed(null);
		});
		
		MenuBar menuBar = new MenuBar();
		setJMenuBar(menuBar);
		menuBar.createMenu();
		
		JPanel panel = new JPanel();
		panel.add(textInput);
		panel.add(clearButton);
		panel.add(dropdownBox);
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.add(textOutput);
		
		JPanel wrapper = new JPanel();
		wrapper.add(panel);
		wrapper.add(bottomPanel);
		
		add(wrapper);
	}
	
	
	public static void main(String[] args) {
		Encoder frame = new Encoder();
	      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	      frame.setVisible(true);  
	      
	}

}
