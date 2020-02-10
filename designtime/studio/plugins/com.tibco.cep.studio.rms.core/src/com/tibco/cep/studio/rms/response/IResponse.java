/**
 * 
 */
package com.tibco.cep.studio.rms.response;


/**
 * @author aathalye
 *
 */
public interface IResponse {
	
	void holdResponseObject(Object responseObject);
	
	Object getResponseObject();
	
	
}
