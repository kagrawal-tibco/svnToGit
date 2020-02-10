/**
 * 
 */
package com.tibco.cep.webstudio.client.request.model;


/**
 * Interface for the base request containing op specific children.
 * @author aathalye
 *
 */
public interface IRequest<I extends IRequestDataItem, P extends IRequestProject<I>, D extends IRequestData<I, P>> extends ISerializableObject {
	
	/**
	 * 
	 * @param requestData
	 */
	public void addRequestData(D requestData);
}
