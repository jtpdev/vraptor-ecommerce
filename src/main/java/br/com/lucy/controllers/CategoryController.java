package br.com.lucy.controllers;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.com.lucy.daos.CategoryDAO;
import br.com.lucy.daos.ProductDAO;
import br.com.lucy.models.Category;
import br.com.lucy.utils.ControllerUtil;

@ApplicationScoped
@Controller
public class CategoryController implements Serializable{

	private static final long serialVersionUID = 1L;
	private CategoryDAO dao;
	private Result result;
	private ProductDAO productDAO;
	private ControllerUtil util;

	/**
	 * @deprecated CDI eyes only
	 */
	public CategoryController() {
		this(null, null, null, null);
	}

	@Inject
	public CategoryController(CategoryDAO dao, Result result, ProductDAO productDAO, ControllerUtil util) {
		this.dao = dao;
		this.result = result;
		this.productDAO = productDAO;
		this.util = util;
	}

	public List<Category> list() {
		try {
			return dao.list();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}
	
	@Path("/category/{category_id}")
	public void category(Long category_id){
		try {
			util.writeHeader();
			result.include("category", dao.find(category_id));
			result.include("products", productDAO.listByCategory(category_id));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}