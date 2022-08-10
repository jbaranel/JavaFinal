package persist;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLDatabase extends Database {
	
	private Connection connection;
	
	public MySQLDatabase() {
		
	}
	public MySQLDatabase(String dbName, String username, String password) {
		try {
			connection = connect(dbName, username, password);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void insert(String query) throws SQLException {
		Statement statement = connection.createStatement();
		statement.executeUpdate(query);		
	}
	
	public ResultSet query(String query) throws SQLException {
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(query);
		return resultSet;
	}
	
	@Override
	public Connection connect(String dbName, String username, String password) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
	    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/" + dbName, username, password);
	    System.out.println("Database connected to " + dbName);
		return connection;
	}

}
