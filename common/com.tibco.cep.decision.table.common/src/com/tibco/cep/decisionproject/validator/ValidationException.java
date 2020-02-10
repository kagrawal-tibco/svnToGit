package com.tibco.cep.decisionproject.validator;

public class ValidationException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4271303084982202377L;
	
	public ValidationException() {
		super();
	}
	public ValidationException(String message) {
		super(message);
	}
	public ValidationException(String message ,Throwable cause){
		super(message,cause);
	}
	public ValidationException(Throwable cause){
		super(cause);
	}
}
