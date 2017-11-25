package br.com.lucy.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import br.com.lucy.models.Sale;
import br.com.lucy.models.ShippingSale;

public class ShippingSaleDAO extends ConnectionDAO implements DAO<ShippingSale>{
	
	@Inject
	private ShippingDAO shippingDAO;

	@Override
	public ShippingSale save(ShippingSale shippingSale) throws SQLException {
		if(shippingSale == null){
			return null;
		}else if(shippingSale.getId() != null){
			return shippingSale;
		}
		shippingSale.setId(nextId());
		String sql = "insert into shippingsale (id, sale_id, shipping_id, shippingvalue) values (?,?,?,?)";
		PreparedStatement stmt = connect().prepareStatement(sql);
		stmt.setLong(1, shippingSale.getId());
		stmt.setLong(2, shippingSale.getSale().getId());
		stmt.setLong(3, shippingSale.getShipping().getId());
		stmt.setBigDecimal(4, shippingSale.getShippingValue());
		stmt.execute();
		close(stmt);
		return find(shippingSale.getId());
	}

	@Override
	public ShippingSale update(ShippingSale object) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ShippingSale find(Long id) throws SQLException {
		if(id == null)
			return null;
		PreparedStatement stmt = connect()
				.prepareStatement("select id, shipping_id, shippingvalue from shippingsale where id = ?");
		stmt.setLong(1, id);
		ResultSet rs = stmt.executeQuery();
		ShippingSale shippingSale = new ShippingSale();
		while (rs.next()) {
			shippingSale.setId(rs.getLong(1));
			shippingSale.setShipping(shippingDAO.find(rs.getLong(2)));
			shippingSale.setShippingValue(rs.getBigDecimal(3));
		}
		close(rs, stmt);
		return shippingSale;
	}
	
	public ShippingSale findBySale(Long id, Sale sale) throws SQLException {
		if(id == null)
			return null;
		PreparedStatement stmt = connect()
				.prepareStatement("select id, shipping_id, shippingvalue from shippingsale where id = ?");
		stmt.setLong(1, id);
		ResultSet rs = stmt.executeQuery();
		ShippingSale shippingSale = new ShippingSale();
		while (rs.next()) {
			shippingSale.setId(rs.getLong(1));
			shippingSale.setSale(sale);
			shippingSale.setShipping(shippingDAO.find(rs.getLong(2)));
			shippingSale.setShippingValue(rs.getBigDecimal(3));
		}
		close(rs, stmt);
		return shippingSale;
	}

	@Override
	public List<ShippingSale> list() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Long nextId() throws SQLException {
		PreparedStatement stmt = connect().prepareStatement("select max(id) from shippingsale");
		ResultSet rs = stmt.executeQuery();
		Long id = 0L;
		while (rs.next()) {
			id = rs.getLong(1);
		}
		close(rs, stmt);
		return id + 1;
	}

}
