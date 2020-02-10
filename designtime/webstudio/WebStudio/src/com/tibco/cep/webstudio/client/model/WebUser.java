package com.tibco.cep.webstudio.client.model;

/**
 * XML representation of User
 * 
 * @author Rohini Jadhav
 */


public class WebUser {

	private String userName;
	private String password;
	private String role;
	
	public String getUserName(){
		return userName;
	}
	
	public void setUserName(String userName){
		this.userName = userName;
	}
	
	public String getPassword(){
		return password;
	}
	
	public void setPassword(String password){
		this.password = password;
	}
	
	public String getRole(){
		return role;
	}
	
	public void setRole(String role){
		this.role = role;
	}
	
}
