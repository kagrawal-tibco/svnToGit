package com.tibco.cep.webstudio.client.decisiontable.model;

/**
 * 
 * @author sasahoo
 *
 */
public class ArgumentData {
	private String propertyPath;
	private String alias;
	private String resourceType;
	private String direction;
	private String array;

	/**
	 * @param propertyPath
	 * @param alias
	 * @param resourceType
	 * @param direction
	 * @param array
	 */
	public ArgumentData(String propertyPath, 
			            String alias, 
			            String resourceType, 
			            String direction, 
			            String array) {
		this.propertyPath = propertyPath;
		this.alias = alias;
		this.resourceType = resourceType;
		this.direction = direction;
		this.array = array;
	}

	public String getPropertyPath() {
		return propertyPath;
	}

	public String getAlias() {
		return alias;
	}

	public String getResourceType() {
		return resourceType;
	}

	public String getDirection() {
		return direction;
	}

	public String getArray() {
		return array;
	}

	public void setArray(String array) {
		this.array = array;
	}
	
	
}


