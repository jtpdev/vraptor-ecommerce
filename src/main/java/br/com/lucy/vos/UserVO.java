package br.com.lucy.vos;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;

import br.com.lucy.models.EType;
import br.com.lucy.models.User;

@SessionScoped
public class UserVO implements VO<User>, Serializable{
	
	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private EType type;
	private Boolean logged = Boolean.FALSE;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getLogged() {
		return logged;
	}

	public void setLogged(Boolean logged) {
		this.logged = logged;
	}
	
	public EType getType() {
		return type;
	}
	
	public void setType(EType type) {
		this.type = type;
	}

	@Override
	public User toModel() {
		User user = new User();
		user.setId(getId());
		user.setType(getType());
		return user;
	}

	@Override
	public void toVO(User model) {
		setId(model.getId());
		setName(model.getName());
		setType(model.getType());
		setLogged(model.getId() != null);
	}

}
