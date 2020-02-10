package com.tibco.cep.webstudio.client.model;

import java.io.Serializable;

/**
 * Model for storing User object 
 * @author Vikram Patil
 */
public class User implements Serializable {

//	public static String[] ROLES = {"Administrator", "Business-User", "Technical-User"};
	
	/**
	 * Add this variable for serialization
	 */
	private static final long serialVersionUID = 1L;

	private String userName;
	private String userRole;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

//	public void setUserRole(String userRole) {
//		this.userRole = userRole;
//	}
//
//	public String getUserRole() {
//		return userRole;
//	}

}
