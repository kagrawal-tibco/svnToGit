/**
 * 
 */
package com.tibco.cep.webstudio.client.process.properties;

import java.io.Serializable;

/**
 * @author dijadhav
 * 
 */
public class ProcessVariable implements Serializable {
	
	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 1599293698771287895L;
	private long id;
	private String name;
	private String path;
	private String type;
	private boolean isMultiple;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

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
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path
	 *            the path to set
	 */
	public void setPath(String path) {
		this.path = path;
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

	/**
	 * @return the isMultiple
	 */
	public boolean isMultiple() {
		return isMultiple;
	}

	/**
	 * @param isMultiple
	 *            the isMultiple to set
	 */
	public void setMultiple(boolean isMultiple) {
		this.isMultiple = isMultiple;
	}

}
