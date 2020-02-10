package com.tibco.rta.util;


public class InvalidQueryException extends Exception {

	private static final long serialVersionUID = -5089803742462350491L;

	public InvalidQueryException(String message) {
		super(message);
	}

	public InvalidQueryException() {
		super();
	}
}
