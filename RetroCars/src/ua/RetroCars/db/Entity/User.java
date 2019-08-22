package ua.RetroCars.db.Entity;

import java.io.Serializable;

/**
 * User entity
 */

public class User implements Serializable{	
	private static final long serialVersionUID = -3553126943048694666L;
	private int id;
	private String login;
	private String password;
	private String role;
	private boolean ban;	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	
	public String getRole() {
		return role;
	}
	public void setRoleId(String roleId) {
		this.role = roleId;
	}
	public boolean isBan() {
		return ban;
	}
	public void setBan(boolean ban) {
		this.ban = ban;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", login=" + login + ", password=" + password
				+ ", role=" + role + ", ban=" + ban + "]";
	}
	
	

}
