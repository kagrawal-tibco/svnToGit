package com.tibco.rta.model;

/**
 * 
 * This exception is thrown when a duplicate schema element is detected.
 *
 */
public class DuplicateSchemaElementException extends Exception {

	public DuplicateSchemaElementException(String message) {
		super(message);
	}

	private static final long serialVersionUID = -1220761413985978450L;
	
}
