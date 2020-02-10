package com.tibco.cep.webstudio.client.model;

/**
 * 
 * @author Rohini Jadhav
 */

public class Resources {

	private String resourceName;
	private String resourceId;
	private String resourcetype;

	
	public String getResourceName(){
		return resourceName;
	}
	
	public void setResourceName(String resourceName){
		this.resourceName = resourceName;
	}
	
	public String getResourceID(){
		return resourceId;
	}
	
	public void setResourceID(String resourceId){
		this.resourceId = resourceId;
	}
	
	public String getResourceType(){
		return resourcetype;
	}
	
	public void setResourceType(String resourceType){
		this.resourcetype = resourceType;
	}
	
}
