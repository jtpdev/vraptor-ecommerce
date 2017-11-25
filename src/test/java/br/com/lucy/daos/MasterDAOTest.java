package br.com.lucy.daos;

import java.sql.SQLException;

public class MasterDAOTest {
	
	/**
	 * TODO To run, uncomment the empty ProductDAO constructor rows.
	 * 
	 * @param args
	 * @throws SQLException
	 */
	public static void main(String[] args) throws SQLException {
		CategoryDAOTest.insertCategories();
		ProductDAOTest.insertProducts();
		ShippingDAOTest.insertShipping();
	}

}
