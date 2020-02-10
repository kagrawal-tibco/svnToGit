package com.tibco.cep.dashboard.psvr.mal;

public class ElementNotFoundException extends Exception {

	private static final long serialVersionUID = 8143250616217235510L;

//	public ElementNotFoundException() {
//	}

	public ElementNotFoundException(String message) {
		super(message);
	}

//	public ElementNotFoundException(Throwable cause) {
//		super(cause);
//	}

	public ElementNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

}
