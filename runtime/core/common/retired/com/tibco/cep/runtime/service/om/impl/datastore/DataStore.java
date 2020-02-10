package com.tibco.cep.runtime.service.om.impl.datastore;

import com.tibco.cep.runtime.service.om.exception.*;

/**
 * Created by IntelliJ IDEA.
 * User: nbansal
 * Date: Jul 13, 2004
 * Time: 1:50:40 PM
 * To change this template use File | Settings | File Templates.
 */
public interface DataStore {

    public void init() throws OMInitException;

    public void close();

    public void add(DBTransaction txn, Object key, Object obj) throws OMAddException;

    public Object fetch(DBTransaction txn, Object key, Object obj) throws OMFetchException;

    //public Object fetchUsingSecondary(DBTransaction txn, Object key, Object obj) throws OMFetchException;

    public void modify(DBTransaction txn, Object key, Object obj) throws OMModifyException;

    public boolean delete(DBTransaction txn, Object key) throws OMDeleteException;
    
    public void deleteDatabase(DBTransaction txn) throws OMException;

    //public boolean deleteUsingSecondary(DBTransaction txn, Object key) throws OMDeleteException;

    public void truncate() throws OMException;

    public void readAll(ReadCallback cb) throws OMException;

    public void readAll(ReadCallback cb, Object extObj) throws OMException;

    interface ReadCallback {
        void readObj(Object obj) throws Exception;
    }
}
