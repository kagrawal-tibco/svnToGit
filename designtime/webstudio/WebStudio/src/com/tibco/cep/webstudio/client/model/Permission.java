package com.tibco.cep.webstudio.client.model;

/**
 * XML representation of User Permissions
 * 
 * @author Rohini Jadhav
 */

public class Permission {
	
	public String resourceRef;
	private String permissionType;
	public String actionType;
	public String actionValue;

	
	public String getResourceRef(){
		return resourceRef;
	}
	
	public void setResourceRef(String resourceRef){
		this.resourceRef = resourceRef;
	}
	
	public String getPermissionType(){
		return permissionType;
	}
	
	public void setPermissionType(String permissionType){
		this.permissionType = permissionType;
	}
	
	public String getActionType(){
		return actionType;
	}
	
	public void setActionType(String actionType){
		this.actionType = actionType;
	}
	
	public String getActionValue(){
		return actionValue;
	}
	
	public void setActionValue(String actionValue){
		this.actionValue = actionValue;
	}
}
