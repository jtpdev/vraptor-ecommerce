package br.com.lucy.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.com.lucy.models.EStatus;
import br.com.lucy.models.ProductSale;
import br.com.lucy.models.Sale;

public class SaleDAO extends ConnectionDAO implements DAO<Sale>{
	
	@Inject
	private UserDAO userDAO;
	@Inject
	private ShippingSaleDAO shippingSaleDAO;
	@Inject
	private ProductSaleDAO productSaleDAO;

	@Override
	public Sale save(Sale sale) throws SQLException {
		if(sale == null){
			return null;
		}else if(sale.getId() != null){
			return sale;
		}
		sale.setId(nextId());
		String sql = "insert into sale (id, status, user_id, datesale, totalvalue) values (?,?,?,?,?)";
		PreparedStatement stmt = connect().prepareStatement(sql);
		stmt.setLong(1, sale.getId());
		stmt.setInt(2, sale.getStatus().ordinal());
		stmt.setLong(3, sale.getUser().getId());
		stmt.setDate(4, new java.sql.Date(sale.getDateSale().getTime()));
//		stmt.setLong(5, sale.getShipping().getId());
		stmt.setBigDecimal(5, sale.getTotalValue());
//		stmt.setDate(6, new java.sql.Date(sale.getFinishDate().getTime()));
		stmt.execute();
		close(stmt);
		
		sale.setShipping(shippingSaleDAO.save(sale.getShipping()));
		
		update(sale);
		
		List<ProductSale> products = new ArrayList<>();
		for (ProductSale productSale : sale.getProducts()) {
			products.add(productSaleDAO.save(productSale));
		}
		Sale savedSale = find(sale.getId());
		savedSale.setProducts(products);
		return savedSale;
	}

	@Override
	public Sale find(Long id) throws SQLException {
		if(id == null)
			return null;
		PreparedStatement stmt = connect().prepareStatement("select id, status, user_id, datesale, shippingsale_id, totalvalue, finishdate from sale where id = ?");
		stmt.setLong(1, id);
		ResultSet rs = stmt.executeQuery();
		Sale sale = new Sale();
		while (rs.next()) {
			sale.setId(rs.getLong(1));
			sale.setStatus(EStatus.values()[rs.getInt(2)]);
			sale.setUser(userDAO.find(rs.getLong(3)));
			sale.setDateSale(rs.getDate(4));
			sale.setShipping(shippingSaleDAO.findBySale(rs.getLong(5), sale));
		}
		sale.setProducts(productSaleDAO.listBySale(sale));
		close(rs, stmt);
		return sale;
	}

	@Override
	public List<Sale> list() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Long nextId() throws SQLException {
		PreparedStatement stmt = connect().prepareStatement("select max(id) from sale");
		ResultSet rs = stmt.executeQuery();
		Long id = 0L;
		while (rs.next()) {
			id = rs.getLong(1);
		}
		close(rs, stmt);
		return id + 1;
	}

	@Override
	public Sale update(Sale sale) throws SQLException {
		if(sale == null){
			return null;
		}else if(sale.getId() == null){
			return save(sale);
		}
		if(sale.getFinishDate() != null){
			String sql = "update sale set status = ?, finishdate = ?, shippingsale_id = ? where id = ?";
			PreparedStatement stmt = connect().prepareStatement(sql);
			stmt.setInt(1, sale.getStatus().ordinal());
			stmt.setDate(2, new java.sql.Date(sale.getFinishDate().getTime()));
			stmt.setLong(3, sale.getShipping().getId());
			stmt.setLong(4, sale.getId());
			stmt.execute();
			close(stmt);
		}else{
			String sql = "update sale set status = ?, shippingsale_id = ? where id = ?";
			PreparedStatement stmt = connect().prepareStatement(sql);
			stmt.setInt(1, sale.getStatus().ordinal());
			stmt.setLong(2, sale.getShipping().getId());
			stmt.setLong(3, sale.getId());
			stmt.execute();
			close(stmt);
			
		}
		return find(sale.getId());
	}

}
