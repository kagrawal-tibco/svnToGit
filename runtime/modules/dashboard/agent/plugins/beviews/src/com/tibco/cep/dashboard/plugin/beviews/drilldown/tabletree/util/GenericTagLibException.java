package com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.util;

/**
 * @author apatil
 * 
 */
public class GenericTagLibException extends Exception {

	/**
	 * 
	 */
	public GenericTagLibException() {
		super();
	}

	/**
	 * @param message
	 */
	public GenericTagLibException(String message) {
		super(message);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public GenericTagLibException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param cause
	 */
	public GenericTagLibException(Throwable cause) {
		super(cause);
	}
}