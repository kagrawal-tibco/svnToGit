package com.tibco.cep.dashboard.management;

/**
 * @author anpatil
 *
 */
public class ServiceUnavailableException extends RuntimeException {

	private static final long serialVersionUID = -2094227751960796103L;
	
	@SuppressWarnings("unused")
	private String serviceName;
	
	@SuppressWarnings("unused")
	private STATE serviceState;

	public ServiceUnavailableException() {
		super();
	}

	public ServiceUnavailableException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceUnavailableException(String message) {
		super(message);
	}

	public ServiceUnavailableException(Throwable cause) {
		super(cause);
	}

	public ServiceUnavailableException(String serviceName,STATE state) {
		super(serviceName +" is "+((state == STATE.PAUSED)?"paused":"stopped"));
		this.serviceName = serviceName;
		this.serviceState = state;
	}

}