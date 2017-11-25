package br.com.lucy.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.lucy.models.Shipping;

public class ShippingDAO extends ConnectionDAO implements DAO<Shipping> {

	@Override
	public Shipping save(Shipping shipping) throws SQLException {
		if(shipping.getId() != null){
			return shipping;
		}
		
		shipping.setId(nextId());
		String sql = "insert into shipping (id, name, description) values (?,?,?)";
		PreparedStatement stmt = connect().prepareStatement(sql);
		stmt.setLong(1, nextId());
		stmt.setString(2, shipping.getName());
		stmt.setString(3, shipping.getDescription());
		stmt.execute();
		close(stmt);
		return find(shipping.getId());
	}

	@Override
	public Shipping update(Shipping shipping) throws SQLException {
		if(shipping == null){
			return null;
		}else if(shipping.getId() == null){
			return save(shipping);
		}
		
		String sql = "update shipping set name = ?, description = ? where id = ?";
		PreparedStatement stmt = connect().prepareStatement(sql);
		stmt.setString(0, shipping.getName());
		stmt.setString(1, shipping.getDescription());
		stmt.setLong(2, shipping.getId());
		stmt.execute();
		close(stmt);
		return find(shipping.getId());
	}

	@Override
	public Shipping find(Long id) throws SQLException {
		if(id == null)
			return null;
		String sql = "select id, name, description from shipping where id = ?";
		PreparedStatement stmt = connect().prepareStatement(sql);
		stmt.setLong(1, id);
		ResultSet rs = stmt.executeQuery();
		Shipping shipping = new Shipping();
		while (rs.next()) {
			shipping.setId(rs.getLong(1));
			shipping.setName(rs.getString(2));
			shipping.setDescription(rs.getString(3));
		}
		close(rs, stmt);
		return shipping;
	}

	@Override
	public List<Shipping> list() throws SQLException {
		String sql = "select id, name, description from shipping";
		PreparedStatement stmt = connect().prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		List<Shipping> shippings = new ArrayList<>();
		while (rs.next()) {
			Shipping shipping = new Shipping();
			shipping.setId(rs.getLong(1));
			shipping.setName(rs.getString(2));
			shipping.setDescription(rs.getString(3));
			shippings.add(shipping);
		}
		close(rs, stmt);
		return shippings;
	}

	@Override
	public void delete(Long id) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Long nextId() throws SQLException {
		PreparedStatement stmt = connect().prepareStatement("select max(id) from shipping");
		ResultSet rs = stmt.executeQuery();
		Long id = 0L;
		while (rs.next()) {
			id = rs.getLong(1);
		}
		close(rs, stmt);
		return id + 1;
	}

}
