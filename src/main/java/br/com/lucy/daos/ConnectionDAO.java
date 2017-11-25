package br.com.lucy.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.inject.Inject;

import br.com.lucy.utils.ConnectionUtil;

public class ConnectionDAO {

	@Inject
	private ConnectionUtil conn;

	public ConnectionDAO() {
		this.conn = new ConnectionUtil();
	}

	protected Connection connect() {
		return conn.connect();
	}

	protected void close() {
		if (conn != null)
			conn.close();
	}

	protected void close(ResultSet rs, PreparedStatement stmt) throws SQLException {
		rs.close();
		close(stmt);
	}

	protected void close(PreparedStatement stmt) throws SQLException {
		stmt.close();
		close();
	}

	protected Connection refresh() {
		return conn.refresh();
	}

}
