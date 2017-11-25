package br.com.lucy.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.lucy.models.Price;
import br.com.lucy.models.Product;

public class PriceDAO extends ConnectionDAO implements DAO<Price> {

	@Override
	public Price save(Price price) throws SQLException {
		if(price == null){
			return null;
		}else if(price.getId() != null){
			return price;
		}
		price.setId(nextId());
		if(price.getDateFinish() != null){
			String sql = "insert into price (id, datestart, dateFinish, pricevalue,product_id) values (?,?,?,?,?)";
			PreparedStatement stmt = connect().prepareStatement(sql);
			stmt.setLong(1, price.getId());
			stmt.setDate(2, new java.sql.Date(price.getDateStart().getTime()));
			stmt.setDate(3, new java.sql.Date(price.getDateFinish().getTime()));
			stmt.setBigDecimal(4, price.getPriceValue());
			stmt.setLong(5, price.getProduct().getId());
			stmt.execute();
			close(stmt);
		}else{
			String sql = "insert into price (id, datestart, pricevalue,product_id) values (?,?,?,?)";
			PreparedStatement stmt = connect().prepareStatement(sql);
			stmt.setLong(1, price.getId());
			stmt.setDate(2, new java.sql.Date(price.getDateStart().getTime()));
			stmt.setBigDecimal(3, price.getPriceValue());
			stmt.setLong(4, price.getProduct().getId());
			stmt.execute();
			close(stmt);
		}
//		return find(price.getId());
		return findByProduct(price); // TODO only for test
	}

	private Price findByProduct(Price price) throws SQLException {

		if (price.getId() == null)
			return null;
		PreparedStatement stmt = connect()
				.prepareStatement("select id, datestart, dateFinish, pricevalue from price where id = ?");
		stmt.setLong(1, price.getId());
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			price.setId(rs.getLong(1));
			price.setDateStart(rs.getDate(2));
			price.setDateFinish(rs.getDate(3));
			price.setPriceValue(rs.getBigDecimal(4));
		}
		close(rs, stmt);
		return price;
	
	}

	/**
	 * @deprecated Don't find a special without a product
	 */
	@Override
	public Price find(Long id) throws SQLException {
		if (id == null)
			return null;
		PreparedStatement stmt = connect()
				.prepareStatement("select id, datestart, dateFinish, pricevalue from price where id = ?");
		stmt.setLong(1, id);
		ResultSet rs = stmt.executeQuery();
		Price price = new Price();
		while (rs.next()) {
			price.setId(rs.getLong(1));
			price.setDateStart(rs.getDate(2));
			price.setDateFinish(rs.getDate(3));
			price.setPriceValue(rs.getBigDecimal(4));
		}
		close(rs, stmt);
		return price;
	}
	
	/**
	 * @deprecated Don't list a special without a product
	 */

	@Override
	public List<Price> list() throws SQLException {
		String sql = "select id, datestart, datefinish, pricevalue from price ";

		PreparedStatement stmt = connect().prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		List<Price> pricing = new ArrayList<>();
		while (rs.next()) {
			Price price = new Price();
			price.setId(rs.getLong(1));
			price.setDateStart(rs.getDate(2));
			price.setDateFinish(rs.getDate(3));
			price.setPriceValue(rs.getBigDecimal(4));

			pricing.add(price);
		}
		close(rs, stmt);
		return pricing;
	}

	@Override
	public void delete(Long id) throws SQLException {
		PreparedStatement stmt = connect().prepareStatement("delete from price where id = ?");
		stmt.setLong(1, id);
		stmt.execute();
		close(stmt);
	}

	@Override
	public Long nextId() throws SQLException {
		PreparedStatement stmt = connect().prepareStatement("select max(id) from price");
		ResultSet rs = stmt.executeQuery();
		Long id = 0L;
		while (rs.next()) {
			id = rs.getLong(1);
		}
		close(rs, stmt);
		return id + 1;
	}

	public List<Price> listByProduct(Connection connection, Product product) throws SQLException {
		String sql = "select id, datestart, datefinish, pricevalue from price where product_id = ? ";

		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setLong(1, product.getId());
		ResultSet rs = stmt.executeQuery();
		List<Price> pricing = new ArrayList<>();
		while (rs.next()) {
			Price price = new Price();
			price.setId(rs.getLong(1));
			price.setDateStart(rs.getDate(2));
			price.setDateFinish(rs.getDate(3));
			price.setPriceValue(rs.getBigDecimal(4));
			price.setProduct(product);
			pricing.add(price);
		}
		stmt.close();
		rs.close();
		return pricing;
	}

	@Override
	public Price update(Price price) throws SQLException {
		if (price == null) {
			return null;
		} else if (price.getId() == null) {
			return save(price);
		}
		String sql = "update price set datefinish = ? where id = ?";
		PreparedStatement stmt = connect().prepareStatement(sql);
		stmt.setDate(0, new java.sql.Date(price.getDateFinish().getTime()));
		stmt.setLong(1, price.getId());
		stmt.execute();
		close(stmt);
		return find(price.getId());
	}

}
