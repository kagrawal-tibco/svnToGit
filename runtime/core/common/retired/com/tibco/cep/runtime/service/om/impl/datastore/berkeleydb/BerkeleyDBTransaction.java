package com.tibco.cep.runtime.service.om.impl.datastore.berkeleydb;

import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Transaction;
import com.tibco.cep.runtime.service.om.exception.OMException;
import com.tibco.cep.runtime.service.om.impl.datastore._retired_.DBTransaction;

/**
 * Created by IntelliJ IDEA.
 * User: nbansal
 * Date: Sep 20, 2004
 * Time: 3:02:19 AM
 * To change this template use File | Settings | File Templates.
 */
public class BerkeleyDBTransaction implements DBTransaction {

    private Transaction txn;

    public BerkeleyDBTransaction(Transaction txn) {
        this.txn = txn;
    }

    public Transaction get() {
        return txn;
    }

    public void commit() throws OMException {
        try {
            txn.commit();
        } catch (DatabaseException e) {
            throw new OMException(e);
        }
    }

    public void abort() throws OMException {
        try {
            txn.abort();
        } catch (DatabaseException e) {
            throw new OMException(e);
        }
    }
}
