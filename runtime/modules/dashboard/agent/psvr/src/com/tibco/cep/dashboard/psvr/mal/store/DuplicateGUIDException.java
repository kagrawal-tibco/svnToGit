package com.tibco.cep.dashboard.psvr.mal.store;

import com.tibco.cep.dashboard.psvr.mal.MALException;

public class DuplicateGUIDException extends MALException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1275870954738896216L;

	public DuplicateGUIDException() {
	}

	public DuplicateGUIDException(String message) {
		super(message);
	}

	public DuplicateGUIDException(Throwable cause) {
		super(cause);
	}

	public DuplicateGUIDException(String message, Throwable cause) {
		super(message, cause);
	}

}
