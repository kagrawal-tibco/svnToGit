package com.tibco.rta.common;

/**
 * Similar to SQLRecoverableException -- a module can throw this exception if it want the 
 * operation to be entirely retried.
 * 
 * @author bgokhale
 *
 */
public class RecoverableException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5509771238720527026L;

	public RecoverableException(String string) {
		super(string);
	}
	
	public RecoverableException(String string, Throwable cause) {
		super(string, cause);
	}
}
