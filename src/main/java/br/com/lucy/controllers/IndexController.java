package br.com.lucy.controllers;

import java.io.Serializable;
import java.sql.SQLException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.lucy.utils.ControllerUtil;

@ApplicationScoped
@Controller
public class IndexController implements Serializable{

	private static final long serialVersionUID = 1L;
	private final ControllerUtil util;

	/**
	 * @deprecated CDI eyes only
	 */
	protected IndexController() {
		this(null);
	}

	@Inject
	public IndexController(ControllerUtil util) {
		this.util = util;
	}

	@Path("/")
	public void index() {
		try {
			util.writeHeader();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
}