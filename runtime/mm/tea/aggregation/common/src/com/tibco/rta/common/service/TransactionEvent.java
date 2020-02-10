package com.tibco.rta.common.service;

import java.io.Serializable;

public class TransactionEvent implements Serializable {

	private static final long serialVersionUID = 6875411154557464767L;

	public enum Status {
		SUCCESS,
		FAILURE
	}
	
	private String id;

	private Exception exception;
	
	private Status status;
	
	public TransactionEvent(String transID) {
		this.id = transID;
	}
	
	public String getTransactionId() {
		return id;
	}

	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}

	public Status getStatus() {
		return status;
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}


}
