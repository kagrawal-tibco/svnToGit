package com.tibco.cep.bpmn.runtime.agent;

/**
 * Created by IntelliJ IDEA.
 * User: suresh
 * Date: Nov 19, 2011
 * Time: 7:45:27 AM
 * To change this template use File | Settings | File Templates.
 */

public class ProcessException extends Exception {
	
	

    /**
	 * 
	 */
	private static final long serialVersionUID = 3754970990929207056L;

	public ProcessException() {
		super();
		
	}

	public ProcessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}

	public ProcessException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public ProcessException(String message) {
		super(message);
		
	}

	public ProcessException(Throwable cause) {
		super(cause);
		
	}


}
