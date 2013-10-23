package es.ficonlan.web.backend.model.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;



@Entity
public class User {
	
	@Id 
	public int userId;
	@Column
	public String name;
	@Column
	public String login;
	@Column
	public String password;
	@Column
	public String dni;
	@Column
	public int priviledgeLevel;
	
	
	public User(int userId, String name, String login, String password,
			String dni, int priviledgeLevel) {
		super();
		this.userId = userId;
		this.name = name;
		this.login = login;
		this.password = password;
		this.dni = dni;
		this.priviledgeLevel = priviledgeLevel;
	}
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public int getPriviledgeLevel() {
		return priviledgeLevel;
	}
	public void setPriviledgeLevel(int priviledgeLevel) {
		this.priviledgeLevel = priviledgeLevel;
	}
	
	
	

}
