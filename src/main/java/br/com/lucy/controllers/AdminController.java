package br.com.lucy.controllers;

import javax.inject.Inject;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.com.lucy.vos.UserVO;

@Controller
public class AdminController {
	private UserVO vo;
	private Result result;

	/**
	 * @deprecated CDI eyes only
	 */
	public AdminController() {
		this(null, null);
	}

	@Inject
	public AdminController(UserVO vo, Result result) {
		this.vo = vo;
		this.result = result;
	}
	
	@Path("/dashboard")
	public void dashboard(){
		// TODO create admin dashboard
	}

}
