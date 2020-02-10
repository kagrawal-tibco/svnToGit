package com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.integration;

/**
 * @author rajesh
 * 
 */
public class TableModelException extends Exception {

	private static final long serialVersionUID = 4284274545661132841L;

	/**
	 * 
	 */
	public TableModelException() {
		super();
	}

	/**
	 * @param message
	 */
	public TableModelException(String message) {
		super(message);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public TableModelException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param cause
	 */
	public TableModelException(Throwable cause) {
		super(cause);
	}

}
