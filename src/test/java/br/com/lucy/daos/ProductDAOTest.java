package br.com.lucy.daos;

import java.sql.SQLException;

import br.com.lucy.builders.ProductBuilder;
import br.com.lucy.models.Product;
import br.com.lucy.utils.ProductTestUtil;

public class ProductDAOTest {	

	private static final CategoryDAO categoryDAO = new CategoryDAO();
	private static final ProductDAO productDAO = new ProductDAO();
	
	public static void main(String[] args) throws SQLException {
		insertProducts();
	}
	
	public static void insertProducts() throws SQLException{
		long start = System.currentTimeMillis();
		for (int i = 0; i < 50; i++) {
			Product product = new ProductBuilder()
					.setName(ProductTestUtil.createNameProduct())
					.setDescription(ProductTestUtil.createDescriptionProduct())
					.setCategory(ProductTestUtil.getCategory(categoryDAO))
					.getValue(); 
			product.setPricing(ProductTestUtil.createPricing(product));
			ProductTestUtil.setSpecialOnProduct(product);
			System.out.println(productDAO.save(product));
		}
		long finish = System.currentTimeMillis();
		System.out.println("Time: " + (finish - start)/1000 + " second(s).");
	}

}
