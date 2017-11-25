package br.com.lucy.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.lucy.models.Product;
import br.com.lucy.models.Special;

public class SpecialDAO extends ConnectionDAO implements DAO<Special> {

	@Override
	public Special save(Special special) throws SQLException {
		if (special == null) {
			return null;
		} else if (special.getId() != null) {
			return special;
		}
		special.setId(nextId());
		if (special.getDateFinish() != null) {
			String sql = "insert into special (id, datestart, dateFinish, pricevalue,product_id) values (?,?,?,?,?)";
			PreparedStatement stmt = connect().prepareStatement(sql);
			stmt.setLong(1, special.getId());
			stmt.setDate(2, new java.sql.Date(special.getDateStart().getTime()));
			stmt.setDate(3, new java.sql.Date(special.getDateFinish().getTime()));
			stmt.setBigDecimal(4, special.getPriceValue());
			stmt.setLong(5, special.getProduct().getId());
			stmt.execute();
			close(stmt);
		} else {
			String sql = "insert into special (id, datestart,  pricevalue,product_id) values (?,?,?,?)";
			PreparedStatement stmt = connect().prepareStatement(sql);
			stmt.setLong(1, special.getId());
			stmt.setDate(2, new java.sql.Date(special.getDateStart().getTime()));
			stmt.setBigDecimal(3, special.getPriceValue());
			stmt.setLong(4, special.getProduct().getId());
			stmt.execute();
			close(stmt);
		}
//		return find(special.getId());
		 return findByProduct(special); 
	}

	private Special findByProduct(Special special) throws SQLException {
		if (special.getId() == null)
			return null;
		PreparedStatement stmt = connect()
				.prepareStatement("select id, datestart, dateFinish, pricevalue, product_id from special where id = ?");
		stmt.setLong(1, special.getId());
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			special.setId(rs.getLong(1));
			special.setDateStart(rs.getDate(2));
			special.setDateFinish(rs.getDate(3));
			special.setPriceValue(rs.getBigDecimal(4));
		}
		close(rs, stmt);
		return special;
	}

	/**
	 * @deprecated Don't find a price without a product
	 */
	@Override
	public Special find(Long id) throws SQLException {
		if (id == null)
			return null;
		PreparedStatement stmt = connect()
				.prepareStatement("select id, datestart, dateFinish, pricevalue from special where id = ?");
		stmt.setLong(1, id);
		ResultSet rs = stmt.executeQuery();
		Special special = new Special();
		while (rs.next()) {
			special.setId(rs.getLong(1));
			special.setDateStart(rs.getDate(2));
			special.setDateFinish(rs.getDate(3));
			special.setPriceValue(rs.getBigDecimal(4));
		}
		close(rs, stmt);
		return special;
	}

	/**
	 * @deprecated Don't list a price without a product
	 */
	@Override
	public List<Special> list() throws SQLException {
		String sql = "select id, datestart, datefinish, pricevalue from special ";

		PreparedStatement stmt = connect().prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		List<Special> pricing = new ArrayList<>();
		while (rs.next()) {
			Special special = new Special();
			special.setId(rs.getLong(1));
			special.setDateStart(rs.getDate(2));
			special.setDateFinish(rs.getDate(3));

			pricing.add(special);
		}
		close(rs, stmt);
		return pricing;
	}

	public List<Special> listByProduct(Product product) throws SQLException {
		String sql = "select id, datestart, datefinish, pricevalue from special where product_id = ?";

		PreparedStatement stmt = connect().prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		List<Special> pricing = new ArrayList<>();
		while (rs.next()) {
			Special special = new Special();
			special.setId(rs.getLong(1));
			special.setDateStart(rs.getDate(2));
			special.setDateFinish(rs.getDate(3));
			special.setPriceValue(rs.getBigDecimal(4));
			special.setProduct(product);

			pricing.add(special);
		}
		close(rs, stmt);
		return pricing;
	}

	@Override
	public void delete(Long id) throws SQLException {
		PreparedStatement stmt = connect().prepareStatement("delete from special where id = ?");
		stmt.setLong(1, id);
		stmt.execute();
		close(stmt);
	}

	@Override
	public Long nextId() throws SQLException {
		PreparedStatement stmt = connect().prepareStatement("select max(id) from special");
		ResultSet rs = stmt.executeQuery();
		Long id = 0L;
		while (rs.next()) {
			id = rs.getLong(1);
		}
		close(rs, stmt);
		return id + 1;
	}

	public List<Special> listByProduct(Connection connection, Product product) throws SQLException {
		String sql = "select id, datestart, datefinish, pricevalue from special where product_id = ? ";

		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setLong(1, product.getId());
		ResultSet rs = stmt.executeQuery();
		List<Special> specials = new ArrayList<>();
		while (rs.next()) {
			Special special = new Special();
			special.setId(rs.getLong(1));
			special.setDateStart(rs.getDate(2));
			special.setDateFinish(rs.getDate(3));
			special.setPriceValue(rs.getBigDecimal(4));
			special.setProduct(product);
			specials.add(special);
		}
		stmt.close();
		rs.close();
		return specials;
	}

	@Override
	public Special update(Special special) throws SQLException {
		if (special == null) {
			return null;
		} else if (special.getId() == null) {
			return save(special);
		}
		String sql = "update special set dateFinish = ? where id = ?";
		PreparedStatement stmt = connect().prepareStatement(sql);
		stmt.setDate(0, new java.sql.Date(special.getDateFinish().getTime()));
		stmt.setLong(1, special.getId());
		stmt.execute();
		close(stmt);
		return find(special.getId());
	}

}
