package com.tibco.cep.dashboard.psvr.data;

import com.tibco.cep.dashboard.psvr.common.FatalException;

public class DataException extends FatalException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1189668374647534886L;	

	public DataException() {
		super();
	}

	public DataException(String message, Throwable cause) {
		super(message, cause);
	}

	public DataException(String message) {
		super(message);
	}

	public DataException(Throwable cause) {
		super(cause);
	}


}