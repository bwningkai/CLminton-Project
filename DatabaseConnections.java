package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnections {
	private static final String URL = "jdbc:mysql://localhost:3306/climnton";
	private final String USERNAME = "root";
	private final String PASSWORD = "";
	private final String DATABASE = "clminton";
	private final String HOST = "localhost:3306";
	private final String CONNECTION = String.format("jdbc:mysql://%s/%s", HOST, DATABASE);

	private static DatabaseConnections instance;
	private static Connection con;

	public ResultSet rs;
	public ResultSetMetaData rsm;

	private Statement st;

	private DatabaseConnections() {
		try {
			con = DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);
		} catch (SQLException e) {
			handleSQLException(e);
		}

	}

	public static DatabaseConnections getInstance() {
		if (instance == null) {
			instance = new DatabaseConnections();
		}
		return instance;
	}

	public Connection getConnection() {
		return con;
	}

	private void handleSQLException(SQLException e) {
		e.printStackTrace();

	}

	public ResultSet execQuery(String query) {
		try {
			Statement statement = con.createStatement();
			rs = statement.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	public void execUpdate(String query) {
		try {
			st = con.createStatement();
			st.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
