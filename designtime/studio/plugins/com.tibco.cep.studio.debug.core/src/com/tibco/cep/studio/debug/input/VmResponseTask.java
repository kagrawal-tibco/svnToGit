package com.tibco.cep.studio.debug.input;



/**
 * @author pdhar
 *
 */
public interface VmResponseTask extends VmTask {
	
	/**
	 * return true if the task has a response
	 * @return
	 */
	boolean hasResponse();
	
	/**
	 * get the response object or null
	 * @return
	 */
	Object getResponse();
	
	
	/**
	 * set the response object
	 * @param response
	 */
	void setResponse(Object response);
	
	
}
