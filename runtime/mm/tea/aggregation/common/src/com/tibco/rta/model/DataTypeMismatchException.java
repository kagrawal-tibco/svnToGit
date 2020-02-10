package com.tibco.rta.model;

/**
 * 
 * This exception is thrown when a duplicate schema element is detected.
 *
 */
public class DataTypeMismatchException extends Exception {

	public DataTypeMismatchException(String message) {
		super(message);
	}
	
	public DataTypeMismatchException(Attribute attribute, Object value) {
		this (String.format("Expected datatype for attribute [%s]: [%s], found: [%s]", attribute.getName(), attribute.getDataType(), value.getClass().getName()));
	}

    public DataTypeMismatchException(DataType dataType, Object value) {
   		this (String.format("Expected datatype : [%s], found: [%s]", dataType, value.getClass().getName()));
   	}

	private static final long serialVersionUID = -1220761413985978450L;
	
}
