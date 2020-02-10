package com.tibco.cep.runtime.service.debug;

public abstract class AbstractDebugResponseTask implements DebugResponseTask {
	
	protected Object response;

	/* (non-Javadoc)
	 * @see com.tibco.cep.runtime.service.debug.DebugResponseTask#getResponse()
	 */

	public Object getResponse() {
		return response;
	}
	


	/* (non-Javadoc)
	 * @see com.tibco.cep.runtime.service.debug.DebugResponseTask#setResponse(java.lang.Object)
	 */

	public void setResponse(Object response) {
		this.response = response;
	}
}
