
package com.tibco.cep.bemm.common.model;

/**
 * The object contains the result of the operation
 * 
 * @author dijadhav
 *
 */
public class BETeaOperationResult {
	/**
	 * Name of the instance/host
	 */
	private String name;
	/**
	 * Result of the operation
	 */
	private Object result;

	private String key;

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
	public void setName(String instanceName) {
		this.name = instanceName;
	}

	/**
	 * @return the result
	 */
	public Object getResult() {
		return result;
	}

	/**
	 * @param result
	 *            the result to set
	 */
	public void setResult(Object result) {
		this.result = result;
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	
}
