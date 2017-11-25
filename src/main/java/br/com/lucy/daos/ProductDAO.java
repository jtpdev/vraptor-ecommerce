package br.com.lucy.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.com.lucy.models.Price;
import br.com.lucy.models.Product;
import br.com.lucy.models.Special;
import br.com.lucy.vos.ProductVO;

public class ProductDAO extends ConnectionDAO implements DAO<Product> {

	@Inject
	private PriceDAO priceDAO;
	@Inject
	private SpecialDAO specialDAO;
	@Inject
	private CategoryDAO categoryDAO;

//	/**
//	 * @deprecated only for test
//	 */
//	public ProductDAO() {
//		priceDAO = new PriceDAO();
//		specialDAO = new SpecialDAO();
//		categoryDAO = new CategoryDAO();
//	}

	@Override
	public Product save(Product product) throws SQLException {
		if(product == null){
			return null;
		}else if(product.getId() != null){
			return product;
		}
		product.setId(nextId());
		if (product.getCategory() != null) {
			String sql = "insert into product (id,name,description,category_id) values (?,?,?,?)";
			PreparedStatement stmt = connect().prepareStatement(sql);
			stmt.setLong(1, product.getId());
			stmt.setString(2, product.getName());
			stmt.setString(3, product.getDescription());
			stmt.setLong(4, product.getCategory().getId());
			stmt.execute();
			close(stmt);
		} else {
			String sql = "insert into product (id,name,description) values (?,?,?)";
			PreparedStatement stmt = connect().prepareStatement(sql);
			stmt.setLong(1, product.getId());
			stmt.setString(2, product.getName());
			stmt.setString(3, product.getDescription());
			stmt.execute();
			close(stmt);
		}
		Product savedProduct = find(product.getId());
		for (Price price : product.getPricing()) {
			price.setProduct(savedProduct);
			savedProduct.getPricing().add(priceDAO.save(price));
		}
		for (Special special : product.getSpecials()) {
			special.setProduct(savedProduct);
			savedProduct.getSpecials().add(specialDAO.save(special));
		}
		return savedProduct;
	}

	@Override
	public Product find(Long id) throws SQLException {
		if(id == null)
			return null;
		Connection connection = connect();
		PreparedStatement stmt = connection.prepareStatement("select id, name, description, category_id from product where id = ?");
		stmt.setLong(1, id);
		ResultSet rs = stmt.executeQuery();
		Product product = new Product();
		while (rs.next()) {
			product.setId(rs.getLong(1));
			product.setName(rs.getString(2));
			product.setDescription(rs.getString(3));
			product.setPricing(priceDAO.listByProduct(connection, product));
			product.setSpecials(specialDAO.listByProduct(connection, product));
			product.setCategory(categoryDAO.find(connection, rs.getLong(4)));
		}
		close(rs, stmt);
		return product;
	}

	@Override
	public List<Product> list() throws SQLException {

		String sql = "select id, name, description, category_id from product ";

		Connection connection = connect();
		PreparedStatement stmt = connection.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		List<Product> products = new ArrayList<>();
		while (rs.next()) {
			Product product = new Product();
			product.setId(rs.getLong(1));
			product.setName(rs.getString(2));
			product.setDescription(rs.getString(3));

			product.setPricing(priceDAO.listByProduct(connection, product));
			product.setSpecials(specialDAO.listByProduct(connection, product));
			product.setCategory(categoryDAO.find(connection, rs.getLong(4)));
			products.add(product);
		}
		close(rs, stmt);
		return products;
	}

	public List<Product> listSpecial() throws SQLException {

		String sql = "select distinct p.id, p.name, p.description, p.category_id " + "from product p "
				+ "inner join special s on s.product_id = p.id";

		Connection connection = connect();
		PreparedStatement stmt = connection.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		List<Product> products = new ArrayList<>();
		while (rs.next()) {
			Product product = new Product();
			product.setId(rs.getLong(1));
			product.setName(rs.getString(2));
			product.setDescription(rs.getString(3));

			product.setPricing(priceDAO.listByProduct(connection, product));
			product.setSpecials(specialDAO.listByProduct(connection, product));
			product.setCategory(categoryDAO.find(connection, rs.getLong(4)));
			products.add(product);
		}
		close(rs, stmt);
		return products;
	}

	public List<ProductVO> listSpecialVO() throws SQLException {
		List<ProductVO> products = new ArrayList<ProductVO>();
		for (Product product : listSpecial()) {
			ProductVO vo = new ProductVO();
			vo.toVO(product);
			products.add(vo);
		}
		return products;
	}

	@Override
	public void delete(Long id) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public Long nextId() throws SQLException {
		PreparedStatement stmt = connect().prepareStatement("select max(id) from product");
		ResultSet rs = stmt.executeQuery();
		Long id = 0L;
		while (rs.next()) {
			id = rs.getLong(1);
		}
		close(rs, stmt);
		return id + 1;
	}

	@Override
	public Product update(Product product) throws SQLException {
		if(product == null){
			return null;
		}else if(product.getId() == null){
			return save(product);
		}
		String sql = "update product set name = ?, description = ?,category_id = ? where id = ?";
		PreparedStatement stmt = connect().prepareStatement(sql);
		stmt.setString(0, product.getName());
		stmt.setString(1, product.getDescription());
		stmt.setLong(2, product.getCategory().getId());
		stmt.setLong(3, product.getId());
		stmt.execute();
		close(stmt);
		Product savedProduct = find(product.getId());
		for (Price price : product.getPricing()) {
			priceDAO.delete(price.getId());
			savedProduct.getPricing().add(priceDAO.save(price));
		}
		for (Special special : product.getSpecials()) {
			specialDAO.delete(special.getId());
			savedProduct.getSpecials().add(specialDAO.save(special));
		}
		return savedProduct;
	}

	public List<ProductVO> listByCategory(Long category_id) throws SQLException {
		if(category_id == null){
			return null;
		}
		String sql = "select distinct id, name, description, category_id from product "
				+ "where category_id = ?";

		Connection connection = connect();
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setLong(1, category_id);
		ResultSet rs = stmt.executeQuery();
		List<ProductVO> products = new ArrayList<>();
		while (rs.next()) {
			Product product = new Product();
			product.setId(rs.getLong(1));
			product.setName(rs.getString(2));
			product.setDescription(rs.getString(3));
			product.setPricing(priceDAO.listByProduct(connection, product));
			product.setSpecials(specialDAO.listByProduct(connection, product));
			ProductVO productVO = new ProductVO();
			productVO.toVO(product);
			products.add(productVO);
		}
		close(rs, stmt);
		return products;
	}

}
