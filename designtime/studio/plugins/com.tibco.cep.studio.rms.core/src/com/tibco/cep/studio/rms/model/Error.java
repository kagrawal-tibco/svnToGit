/**
 * 
 */
package com.tibco.cep.studio.rms.model;

/**
 * @author aathalye
 *
 */
public class Error {
	
	private String errorCode;
	
	private String errorString;
	
	private String errorDetail;

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
	 * @return the errorString
	 */
	public final String getErrorString() {
		return errorString;
	}

	/**
	 * @param errorString the errorString to set
	 */
	public final void setErrorString(String errorString) {
		this.errorString = errorString;
	}

	/**
	 * @return the errorDetail
	 */
	public final String getErrorDetail() {
		return errorDetail;
	}

	/**
	 * @param errorDetail the errorDetail to set
	 */
	public final void setErrorDetail(String errorDetail) {
		this.errorDetail = errorDetail;
	}
}
