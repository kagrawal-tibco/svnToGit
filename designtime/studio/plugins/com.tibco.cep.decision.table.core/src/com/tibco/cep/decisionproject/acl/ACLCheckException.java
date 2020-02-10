package com.tibco.cep.decisionproject.acl;
@SuppressWarnings("serial")
public class ACLCheckException extends Exception {
	
	public ACLCheckException(Throwable cause){
		super(cause);
	}
	public ACLCheckException() {
		super();
	}
	public ACLCheckException(String message) {
		super(message);
	}
	public ACLCheckException(String message,Throwable cause) {
		super(message,cause);
	}

}
