package com.tibco.rta.common;

/**
 * Similar to SQLRecoverableException -- a module can throw this exception if it want the 
 * operation to be entirely retried.
 * 
 * @author bgokhale
 *
 */
public class FatalException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5509771238720527026L;

	public FatalException(String string) {
		super(string);
	}
	
	public FatalException(String string, Throwable cause) {
		super(string, cause);
	}
}
