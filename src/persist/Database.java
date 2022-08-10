package persist;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class Database {
	
	public Database() {
	}
	public Database(String table, String username, String password) {
	}
	public abstract Connection connect(String table, String username, String password) throws ClassNotFoundException, SQLException;
	
	public abstract ResultSet query(String query) throws SQLException;	
	
	public abstract void insert(String query) throws SQLException;
}
