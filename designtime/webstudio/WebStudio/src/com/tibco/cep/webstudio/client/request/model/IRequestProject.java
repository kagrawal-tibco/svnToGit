/**
 * 
 */
package com.tibco.cep.webstudio.client.request.model;

/**
 * Root project with repeatable items.
 * @author aathalye
 *
 */
public interface IRequestProject<D extends IRequestDataItem> extends ISerializableObject {
	
	/**
	 * Set project name.
	 * @param name
	 */
	public void setName(String name);
	
	/**
	 * Get project name.
	 * @return
	 */
	public String getName();
	
	/**
	 * 
	 * @param dataItem
	 */
	public void addRequestDataItem(D dataItem);
	
	/**
	 * 
	 * @return
	 */
	public String getOperationName();
	
	/**
	 * 
	 * @param operationName
	 */
	public void setOperationName(String operationName);
}
