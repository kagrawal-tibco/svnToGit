package com.tibco.cep.studio.ui.editors.clusterconfig;

/*
@author ssailapp
@date Dec 18, 2009 6:43:20 PM
 */

public class PropertyPresentation {
	
	public static final String TYPE_BOOLEAN = "boolean";
	public static final String TYPE_STRING = "string";
	
	/*
	public static final String TYPE_CONNECTION = "connection";
	public static final String TYPE_DIR_SELECTION = "dir";
	public static final String TYPE_DOMAIN_OBJ = "domain-object";
	public static final String TYPE_GROUP = "group";	
	public static final String TYPE_INT = "int";
	public static final String TYPE_SELECTION = "selection";
	*/
	
	String agentType = "";
	String propertyName = "";
	String uiName = "";
	String propertyType = "string";
	String defaultValue = null;
	String currentValue = null;
	
	public PropertyPresentation(String agentType, String propertyName, String uiName, String propertyType, String defaultValue) {
		this.agentType = agentType;
		this.propertyName = propertyName;
		this.uiName = uiName;
		this.propertyType = propertyType;
		this.defaultValue = defaultValue;
	}

}
