package com.tibco.cep.webstudio.client.model;
import java.util.ArrayList;

import com.tibco.cep.webstudio.client.model.Entry;
import com.tibco.cep.webstudio.client.model.Resources;

/**
 * XML representation of ACL File
 * 
 * @author Rohini Jadhav
 */

public class AclData {

	private ArrayList<Entry> entryArray;
	private ArrayList<Resources> resourceArray;
	private String projectName;
	
	public ArrayList<Entry> getEntries(){
		return this.entryArray;
	}
	
	public void setEntries(ArrayList<Entry> entryArray){
		this.entryArray = entryArray;
	}
	
	public ArrayList<Resources> getResources(){
		return resourceArray;
	}
	
	public void setResources(ArrayList<Resources> resourceArray){
		this.resourceArray = resourceArray;
	}	

	public String getProjectName(){
		return projectName;
	}
	
	public void setProjectName(String projectName){
		this.projectName = projectName;
	}

}
