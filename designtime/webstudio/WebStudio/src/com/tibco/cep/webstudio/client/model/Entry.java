package com.tibco.cep.webstudio.client.model; 

import java.util.ArrayList;
import com.tibco.cep.webstudio.client.model.Permission;

/**
 * 
 * @author Rohini Jadhav
 */

public class Entry {
	
	public String roleName;
	public ArrayList<Permission> permission;

	
	public String getRoleName(){
		return roleName;
	}
	
	public void setRoleName(String roleName){
		this.roleName = roleName;
	}
	
	public ArrayList<Permission> getPermission(){
		return permission;
	}
	
	public void setPermission(ArrayList<Permission> permissionArray){
		this.permission = permissionArray;
	}
}
