package br.com.lucy.controllers;

import java.sql.SQLException;

import javax.inject.Inject;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.com.lucy.daos.UserDAO;
import br.com.lucy.models.EType;
import br.com.lucy.models.User;
import br.com.lucy.utils.ControllerUtil;
import br.com.lucy.utils.UriUtil;
import br.com.lucy.vos.UserVO;

@Controller
public class UserController {
	
	private UserDAO dao;
	private UserVO vo;
	private Result result;
	private UriUtil uri;
	private ControllerUtil util;

	/**
	 * @deprecated CDI eyes only
	 */
	public UserController() {
		this(null, null, null, null, null);
	}

	@Inject
	public UserController(UserDAO dao, UserVO vo, Result result, UriUtil uri, ControllerUtil util) {
		this.dao = dao;
		this.vo = vo;
		this.result = result;
		this.uri = uri;
		this.util = util;
	}
	
	@Path("/clientdashboard")
	public void dashboard() {
		// TODO Change here to create a Dashboard	
		if(!vo.getLogged()){
			result.redirectTo(this).login(" ", "/");
		}else{
			try {
				util.writeHeader();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			result.include("username", vo.getName());
		}
	}
	
	@Path("/dologin")
	public void dologin(User user){
		if(vo.getLogged()){
			result.redirectTo(this).dashboard();
		}
		try {
			dao.login(user, vo);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(vo.getLogged()){
			if(vo.getType().equals(EType.ADMIN)){
				result.redirectTo(AdminController.class).dashboard();
			}else{
				result.redirectTo(uri.getValue());
			}
		}else{
			result.redirectTo(this).login("Incorrect login... Try again!", this.uri.getValue());
		}
	} 

	@Path("/{uri}/login/{message}/")
	public void login(String message, String uri){
		try {
			util.writeHeader();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		result.include("loginmessage", message);
		this.uri.setValue(uri);
	} 
	
	@Path("/user/{message}")
	public void user(String message){
		try {
			util.writeHeader();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		result.include("usermessage", message);
	} 
	
	@Path("/save")
	public void save(User user){
		try {
			user.setType(EType.CLIENT);
			if(dao.findByEmail(user.getEmail()).getId() == null){
				dao.login(dao.save(user), vo);
				result.redirectTo(uri.getValue());
			}else{
				result.redirectTo(this).user("Email already in use!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	} 
	
	@Path("/logout")
	public void logout(){
		vo.setLogged(false);
		result.redirectTo(IndexController.class).index();
	} 
	
}
