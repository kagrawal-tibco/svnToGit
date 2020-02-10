package com.tibco.cep.runtime.util;

import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: amgupta
 * Date: Mar 23, 2009
 * Time: 3:31:57 PM
 * To change this template use File | Settings | File Templates.
 */

public class DBConnectionsBusyException extends SQLException {
    // Do not change this message, as Tangosol clients depend on this message string to find cause of problem
    private static String EXC_MESSAGE = DBException.DatabaseException_MESSAGE + " : All Connections in the pool are busy. None available";

    public DBConnectionsBusyException() {
        super(EXC_MESSAGE);
    }

    public DBConnectionsBusyException(String msg) {
        super(EXC_MESSAGE + ", Exception=" + msg);
    }
}
