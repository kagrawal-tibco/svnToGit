package com.tibco.cep.runtime.service.om.impl.datastore;

import com.tibco.cep.runtime.service.om.exception.OMException;

/**
 * Created by IntelliJ IDEA.
 * User: nbansal
 * Date: Sep 19, 2004
 * Time: 4:11:16 PM
 * To change this template use File | Settings | File Templates.
 */
public interface DataStoreEnv {

    public DBTransaction starttxn() throws OMException;

    public void committxn(DBTransaction txn) throws OMException;

    public void close() throws OMException;

    public boolean tableExists(String tableName) throws OMException;
}
