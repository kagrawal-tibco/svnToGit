package com.tibco.rta.model;

/**
 * 
 * This exception is thrown if when you try to associate model elements of one schema with
 * those of another. 
 *
 */
public class IllegalSchemaException extends Exception {

	private static final long serialVersionUID = 3723206159603252473L;

	public IllegalSchemaException(String message) {
		super(message);
	}

	
}
