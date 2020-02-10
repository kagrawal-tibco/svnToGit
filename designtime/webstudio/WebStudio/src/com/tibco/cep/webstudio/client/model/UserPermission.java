package com.tibco.cep.webstudio.client.model;

/**
 * XML representation of User Permissions
 * 
 * @author Rohini Jadhav
 */

public class UserPermission {
	
	private UserData userObject = new UserData();
	private AclData aclObject = new AclData();
	
	
	public UserData getUserData(){
		return userObject;
	}
	
	public void setUserData(UserData userObject){
		this.userObject = userObject;
	}
	
	public AclData getAclData(){
		return aclObject;
	}
	
	public void setAclData(AclData aclObject){
		this.aclObject = aclObject;
	}
}
