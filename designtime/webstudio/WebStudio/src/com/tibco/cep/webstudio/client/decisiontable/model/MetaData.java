package com.tibco.cep.webstudio.client.decisiontable.model;

/**
 * 
 * @author sasahoo
 *
 */
public class MetaData {
	private String name;
	private String type;
	private Object value;

	/**
	 * @param name
	 * @param type
	 * @param value
	 */
	public MetaData(String name, String type, Object value) {
		this.name = name;
		this.type = type;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

}
