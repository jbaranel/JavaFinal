package gui;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MenuBar extends JMenuBar {
	
	public MenuBar() {
		super();		
	}
	
	public void createMenu() {
		JMenu fileMenu = new JMenu("File");
		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.addActionListener(e -> System.exit(0));
		add(fileMenu);
		fileMenu.add(exitItem);
		setSize(300,400);
		setVisible(true);
	}
}
