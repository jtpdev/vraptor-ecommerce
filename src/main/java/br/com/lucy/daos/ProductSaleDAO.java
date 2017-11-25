package br.com.lucy.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.com.lucy.models.ProductSale;
import br.com.lucy.models.Sale;

public class ProductSaleDAO extends ConnectionDAO implements DAO<ProductSale> {
	
	@Inject
	private ProductDAO productDAO;

	@Override
	public ProductSale save(ProductSale productSale) throws SQLException {
		if(productSale == null){
			return null;
		}else if(productSale.getId() != null){
			return productSale;
		}
		productSale.setId(nextId());
		String sql = "insert into productsale (id, sale_id, product_id, salevalue) values (?,?,?,?)";
		PreparedStatement stmt = connect().prepareStatement(sql);
		stmt.setLong(1, productSale.getId());
		stmt.setLong(2, productSale.getSale().getId());
		stmt.setLong(3, productSale.getProduct().getId());
		stmt.setBigDecimal(4, productSale.getSaleValue());
		stmt.execute();
		close(stmt);
		return find(productSale.getId());
	}

	@Override
	public ProductSale update(ProductSale object) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProductSale find(Long id) throws SQLException {
		if(id == null)
			return null;
		PreparedStatement stmt = connect()
				.prepareStatement("select id, product_id, saleValue from productsale where id = ?");
		stmt.setLong(1, id);
		ResultSet rs = stmt.executeQuery();
		ProductSale productSale = new ProductSale();
		while (rs.next()) {
			productSale.setId(rs.getLong(1));
			productSale.setProduct(productDAO.find(rs.getLong(2)));
			productSale.setSaleValue(rs.getBigDecimal(3));
		}
		close(rs, stmt);
		return productSale;
	}

	@Override
	public List<ProductSale> list() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Long nextId() throws SQLException {
		PreparedStatement stmt = connect().prepareStatement("select max(id) from productsale");
		ResultSet rs = stmt.executeQuery();
		Long id = 0L;
		while (rs.next()) {
			id = rs.getLong(1);
		}
		close(rs, stmt);
		return id + 1;
	}

	public List<ProductSale> listBySale(Sale sale) throws SQLException {
		PreparedStatement stmt = connect()
				.prepareStatement("select id, product_id, saleValue from productsale where sale_id = ?");
		stmt.setLong(1, sale.getId());
		ResultSet rs = stmt.executeQuery();
		List<ProductSale> products = new ArrayList<>();
		while (rs.next()) {
			ProductSale productSale = new ProductSale();
			productSale.setId(rs.getLong(1));
			productSale.setSale(sale);
			productSale.setProduct(productDAO.find(rs.getLong(2)));
			productSale.setSaleValue(rs.getBigDecimal(3));
			products.add(productSale);
		}
		close(rs, stmt);
		return products;
	}

}
