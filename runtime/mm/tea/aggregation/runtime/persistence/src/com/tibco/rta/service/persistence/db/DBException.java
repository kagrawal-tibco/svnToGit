package com.tibco.rta.service.persistence.db;
public class DBException extends RuntimeException {
    
	private static final long serialVersionUID = 8930136169770500294L;

	public final static String DatabaseException_MESSAGE ="DatabaseException";

    private static String EXC_MESSAGE = DatabaseException_MESSAGE + " : Database Not Available.";

    public DBException() {
        super(EXC_MESSAGE);
    }

    public DBException(String msg) {
        super(EXC_MESSAGE + ", Exception=" + msg);
    }
}