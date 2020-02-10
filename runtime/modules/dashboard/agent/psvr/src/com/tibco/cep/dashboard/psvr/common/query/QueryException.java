package com.tibco.cep.dashboard.psvr.common.query;

/**
 * @author anpatil
 *
 */
public class QueryException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8201065664351174952L;

	/**
	 * 
	 */
	public QueryException() {
	}

	/**
	 * @param message
	 */
	public QueryException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public QueryException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public QueryException(String message, Throwable cause) {
		super(message, cause);
	}

}
