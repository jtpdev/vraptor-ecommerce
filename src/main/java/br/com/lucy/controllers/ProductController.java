package br.com.lucy.controllers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.lucy.daos.ProductDAO;
import br.com.lucy.models.Product;
import br.com.lucy.utils.ControllerUtil;
import br.com.lucy.vos.ProductVO;

@Controller
public class ProductController {

	private ProductDAO dao;
	private ControllerUtil util;

	/**
	 * @deprecated CDI eyes only
	 */
	public ProductController() {
		this(null, null);
	}

	@Inject
	public ProductController(ProductDAO dao, ControllerUtil util) {
		this.dao = dao;
		this.util = util;
	}
	
	@Path("/product/{product.id}")
	public ProductVO product(Product product){
		try {
			util.writeHeader();
			ProductVO productVO = new ProductVO();
			productVO.toVO(dao.find(product.getId()));
			return productVO;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Product> list() {
		try {
			return dao.list();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}

}