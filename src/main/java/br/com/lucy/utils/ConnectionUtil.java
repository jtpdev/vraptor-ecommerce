package br.com.lucy.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {

	protected Connection connection = null;

	/**
	 * @return a connection
	 */
	public Connection connect() {

		try {
			// TODO Change here
			String driverName = "com.mysql.jdbc.Driver";
			Class.forName(driverName);
			String url = "jdbc:mysql://localhost:3306/ecommerce";
			String username = "root";
			String password = "";
			connection = DriverManager.getConnection(url, username, password);

			if (connection != null) {
				return connection;
			} else {
				System.out.println("Connection is null"); // TODO only for test
				return null;
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * Close the connection
	 */
	public void close() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace(); // only for tests
		}

	}

	/**
	 * @return connection refreshed
	 */
	public Connection refresh() {
		close();
		return connect();
	}

}