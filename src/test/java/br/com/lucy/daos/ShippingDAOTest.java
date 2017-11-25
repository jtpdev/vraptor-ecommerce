package br.com.lucy.daos;

import java.sql.SQLException;

import br.com.lucy.models.Shipping;

public class ShippingDAOTest {
	
	private static final ShippingDAO dao = new ShippingDAO();
	
	public static void main(String[] args) throws SQLException {
		insertShipping();
	}
	
	public static void insertShipping() throws SQLException{
		Shipping shipping = new Shipping();
		shipping.setName("Fedex");
		shipping.setDescription("10 working days delivery!");
		System.out.println(dao.save(shipping));
		
		Shipping shipping2 = new Shipping();
		shipping2.setName("Harbor - Shipping company");
		shipping2.setDescription("5 working days delivery!");
		System.out.println(dao.save(shipping2));
	}
	

}
