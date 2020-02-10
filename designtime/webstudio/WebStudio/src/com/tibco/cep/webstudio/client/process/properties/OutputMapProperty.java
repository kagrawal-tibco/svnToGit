package com.tibco.cep.webstudio.client.process.properties;


/**
 * This class is used to store output map property
 * 
 * @author dijadhav
 * 
 */
public class OutputMapProperty extends Property {

	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = -4840542308789161387L;
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
