package br.com.lucy.controllers;

import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import br.com.caelum.vraptor.Controller;
import br.com.lucy.daos.ShippingDAO;
import br.com.lucy.models.Shipping;


@Controller
public class ShippingController {
	
	private final ShippingDAO shippingDAO;

	/**
	 * @deprecated CDI eyes only
	 */
	protected ShippingController() {
		this(null);
	}

	@Inject
	public ShippingController(ShippingDAO shippingDAO) {
		this.shippingDAO = shippingDAO;
	}
	
	public List<Shipping> list(){
		try {
			return shippingDAO.list();
		} catch (SQLException e) {
			e.printStackTrace(); // TODO only for tests
		}
		return null;
	}

}
