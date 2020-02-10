package com.tibco.cep.runtime.service.debug;

public interface DebugResponseTask extends DebugTask {
	/**
	 * return the response value
	 * @return
	 */
	Object getResponse();
	
	/**
	 * @param response the response to set
	 */
	void setResponse(Object response);

}
