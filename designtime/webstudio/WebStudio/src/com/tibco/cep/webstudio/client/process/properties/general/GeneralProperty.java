package com.tibco.cep.webstudio.client.process.properties.general;

import java.io.Serializable;

import com.tibco.cep.webstudio.client.process.properties.Property;

/**
 * This class holds the general properties of process and its component.
 * 
 * @author dijadhav
 * 
 */
public class GeneralProperty extends Property implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4304622192549283906L;

	/**
	 * Variable for name of the node/edge.
	 */
	private String name;
	/**
	 * Variable for label of the node/edge.
	 */
	private String label;

	/**
	 * Variable for type of node/edge.
	 */
	private String type;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label
	 *            the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
}
