package com.tibco.rta.service.persistence.db;

import java.sql.SQLException;

public class DBConnectionsBusyException extends SQLException {

	private static final long serialVersionUID = 3151568898455075806L;

	private static String EXC_MESSAGE = DBException.DatabaseException_MESSAGE + " : All Connections in the pool are busy. None available";

    public DBConnectionsBusyException() {
        super(EXC_MESSAGE);
    }

    public DBConnectionsBusyException(String msg) {
        super(EXC_MESSAGE + ", Exception=" + msg);
    }
}