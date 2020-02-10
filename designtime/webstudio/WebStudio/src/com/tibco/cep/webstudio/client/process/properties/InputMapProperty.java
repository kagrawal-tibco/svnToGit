package com.tibco.cep.webstudio.client.process.properties;

/**
 * This class is used to store input map property
 * 
 * @author dijadhav
 * 
 */
public class InputMapProperty extends Property {
	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = -5776266873008136011L;

	private String mapping;

	/**
	 * @return the mapping
	 */
	public String getMapping() {
		return mapping;
	}

	/**
	 * @param mapping
	 *            the mapping to set
	 */
	public void setMapping(String mapping) {
		this.mapping = mapping;
	}
}
