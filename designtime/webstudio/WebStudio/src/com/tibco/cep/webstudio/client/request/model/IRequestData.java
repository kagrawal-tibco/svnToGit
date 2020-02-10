package com.tibco.cep.webstudio.client.request.model;


/**
 * Interface for element &lt;data&gt;
 *
 */
public interface IRequestData<D extends IRequestDataItem, P extends IRequestProject<D>> extends ISerializableObject {
	
	/**
	 * 
	 * @param requestProject
	 */
	public void addRequestProject(P requestProject);
}
