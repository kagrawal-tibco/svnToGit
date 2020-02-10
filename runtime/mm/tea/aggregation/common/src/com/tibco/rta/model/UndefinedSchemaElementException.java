package com.tibco.rta.model;

/**
 * 
 * This exception is thrown when an undefined schema element is accessed.
 *
 */

public class UndefinedSchemaElementException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3723206159603252473L;

	public UndefinedSchemaElementException(String message) {
		super(message);
	}

	
}
