package com.tibco.cep.webstudio.client.model;

import java.util.ArrayList;

/**
 * XML representation of User
 * 
 * @author Rohini Jadhav
 */


public class UserData {

	private String authType;
	
	private ArrayList<WebUser> userList;
	
	public String getAuthType(){
		return authType;
	}
	
	public void setAuthType(String authType){
		this.authType = authType;
	}
	
	public void setUserList(ArrayList<WebUser> userList){
		this.userList = userList;
	}
	
	public ArrayList<WebUser> getUserList(){
		return this.userList;
	}
}
