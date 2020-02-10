package com.tibco.rta.common.exception;


public class PersistenceStoreNotAvailableException extends RuntimeException {

	private static final long serialVersionUID = -8472079944651300350L;
	
	private static String EXC_MESSAGE = " Persistence Store Exception : persistence store Not Available.";

    public PersistenceStoreNotAvailableException() {
        super(EXC_MESSAGE);
    }

    public PersistenceStoreNotAvailableException(String msg) {
        super(EXC_MESSAGE + ", Exception=" + msg);
    }
}