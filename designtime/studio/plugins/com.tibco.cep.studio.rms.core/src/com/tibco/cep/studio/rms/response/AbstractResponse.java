/**
 * 
 */
package com.tibco.cep.studio.rms.response;

/**
 * @author aathalye
 *
 */
public abstract class AbstractResponse implements IResponse {

	private String errorCode;
	
	private String message;
	
	/**
	 * @return the errorCode
	 */
	public final String getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode the errorCode to set
	 */
	public final void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * @return the message
	 */
	public final String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public final void setMessage(String message) {
		this.message = message;
	}
}
