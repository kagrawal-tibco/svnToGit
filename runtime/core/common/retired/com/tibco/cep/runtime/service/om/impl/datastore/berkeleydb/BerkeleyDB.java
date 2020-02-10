package com.tibco.cep.runtime.service.om.impl.datastore.berkeleydb;

import com.sleepycat.je.*;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.service.om.exception.*;
import com.tibco.cep.runtime.service.om.impl.datastore._retired_.DBTransaction;
import com.tibco.cep.runtime.service.om.impl.datastore._retired_.DataStore;
import com.tibco.cep.runtime.service.om.impl.datastore._retired_.Serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 * Created by IntelliJ IDEA.
 * User: nbansal
 * Date: Jul 13, 2004
 * Time: 4:05:59 PM
 * To change this template use File | Settings | File Templates.
 */
public final class BerkeleyDB implements DataStore {

    //static private final boolean PRIMARY_KEY = true;
    //static private final boolean SECONDARY_KEY = false;

    private Environment env;
    private BerkeleyDBConfig config;
    private Database db;
    //private SecondaryDatabase secondaryDb;
    //private boolean hasSecondaryKeyDb = false;
    private Logger m_logger;

    private static ThreadLocal threadlocal = new ThreadLocal() {
        protected synchronized Object initialValue() {
            return new DBThreadLocals();
        }
    };

    BerkeleyDB(BerkeleyDBConfig _config, Logger _logger) {
        config = _config;
        m_logger = _logger;
    }

    // Constructor for use with ability to create a datastore from rules independent of OM only.
    BerkeleyDB(BerkeleyDBEnv bdbenv, BerkeleyDBConfig config, Logger _logger) throws OMException {
        //env = BerkeleyDBEnv.create(dbDir).getEnvironment();
        this.env = bdbenv.getEnvironment();
        this.config = config;
        m_logger = _logger;
        DatabaseConfig dbconfig = new DatabaseConfig();
        dbconfig.setTransactional(true);
        dbconfig.setAllowCreate(true);
        dbconfig.setSortedDuplicates(false);
        try {
            db = env.openDatabase(null, config.dbname, dbconfig);
        } catch (DatabaseException e) {
            m_logger.log(Level.ERROR,"", e);
            throw new OMInitException();
        }
    }

    public void setEnv(Environment env) {
        this.env = env;
    }

    private void berkeleydbinit() throws DatabaseException {

        /* Open the database and optionally create it */
        DatabaseConfig dbconfig = new DatabaseConfig();
        dbconfig.setTransactional(!BerkeleyDBEnv.noRecovery);
        dbconfig.setAllowCreate(true);
        dbconfig.setSortedDuplicates(false);

        db = env.openDatabase(null, config.dbname, dbconfig);

/* zTODO
        if(config.secondarydb != null) {
            hasSecondaryKeyDb = true;
            SecondaryConfig secDbConfig = new SecondaryConfig();
            secDbConfig.setTransactional(!BerkeleyDBEnv.noRecovery);
            secDbConfig.setAllowCreate(true);
            secDbConfig.setSortedDuplicates(false); // zTODO: Should sorted duplicates be allowed for extId??
            secDbConfig.setKeyCreator(new URISecondaryKeyCreator());

            secondaryDb = env.openSecondaryDatabase(null, config.secondarydb, db, secDbConfig);

        }
*/

    }

    public void init() throws OMInitException {

        try {
            berkeleydbinit();
        } catch (DatabaseException e) {
            m_logger.log(Level.ERROR,"", e);
            throw new OMInitException();
        }
    }

    public void close() {
        try {
            //if(secondaryDb != null) {
            //    secondaryDb.close();
            //    secondaryDb = null;
            //}
            if(db != null) {
                db.close();
                db = null;
            }
        } catch (DatabaseException e) {
            m_logger.log(Level.ERROR, "", e);
        }
    }

    private void berkeleydbput(DBTransaction txn, Object key, Object obj, boolean newObj) throws DatabaseException {

        Transaction bdbtxn = ( txn != null ? ((BerkeleyDBTransaction)txn).get() : null);
        DBThreadLocals reusables = (DBThreadLocals)threadlocal.get();
        reusables.clearAll();

        ByteArrayOutputStream keybytes = reusables.keybytes;
        DataOutputStream keyos = reusables.keyos;

        ByteArrayOutputStream bytes = reusables.databytes;
        DataOutputStream os = reusables.dataos;
        Serializer serializer = config.serializer;

        try {
            //key.serializeKey(keyos);
            //obj.writeHeader(os);
            //obj.write(os);
            serializer.serialize(keyos, key);
            serializer.serialize(os, obj);
        } catch (Exception e) {
            m_logger.log(Level.ERROR, "", e);
            throw new DatabaseException();
        }

        DatabaseEntry keyEntry = reusables.keyEntry;
        keyEntry.setData(keybytes.toByteArray());
        DatabaseEntry dataEntry;
        //if(hasSecondaryKeyDb && obj.secondaryKey() != null && obj.secondaryKey().key() != null) {
/* zTODO: Secondary DB support
        if(hasSecondaryKeyDb) {
            reusables.dataEntry2nd.setObject(obj);
            reusables.dataEntry2nd.setNew(newObj);
            dataEntry = reusables.dataEntry2nd;
        }
        else {
            dataEntry = reusables.dataEntry;
        }
*/
        dataEntry = reusables.dataEntry;

        dataEntry.setData(bytes.toByteArray());
        OperationStatus status = db.put(bdbtxn, keyEntry, dataEntry);


        if (status != OperationStatus.SUCCESS) {
            throw new DatabaseException("Data insertion got status " +
                                        status);
        }
    }

    public void add(DBTransaction txn, Object key, Object obj) throws OMAddException {
        try {
            berkeleydbput(txn, key, obj, true);
        } catch(DatabaseException e) {
            m_logger.log(Level.ERROR,  "", e);
            throw new OMAddException(e.getMessage());
        }
    }

    public void modify(DBTransaction txn, Object key, Object obj) throws OMModifyException {
        try {
            berkeleydbput(txn, key, obj, false);
        } catch (DatabaseException e) {
            m_logger.log(Level.ERROR,"", e);
            throw new OMModifyException();
        }
    }

    /**
     * Retrieves data based on the Primary or Secondary key
     * @param key Primary Key
     * @param obj
     * @return StreamCreatable object
     * @throws DatabaseException
     */
    private Object berkeleydbfetch(DBTransaction txn, Object key, /*boolean isPrimary,*/ Object obj) throws DatabaseException {

        if( key == null /*|| key.key() == null */) {
            return null;
        }

        Transaction bdbtxn = (txn != null ? ((BerkeleyDBTransaction)txn).get() : null);
        DBThreadLocals reusables = (DBThreadLocals) threadlocal.get();
        reusables.clearAll();

        //if( !isPrimary && !hasSecondaryKeyDb )
        //    throw new DatabaseException("No Secondary Key Database exists and attempt to fetch using a Secondary Key");

        ByteArrayOutputStream keyBytes = reusables.keybytes;
        DataOutputStream os = reusables.keyos;
        Serializer serializer = config.serializer;

        try {
            serializer.serialize(os, key);
            //key.serializeKey(os);
        } catch (Exception e) {
            m_logger.log(Level.ERROR,"", e);
            throw new DatabaseException();
        }

        DatabaseEntry keyEntry = reusables.keyEntry;
        keyEntry.setData(keyBytes.toByteArray());
        DatabaseEntry dataEntry = reusables.dataEntry;

        OperationStatus status;
        //if(isPrimary)
            status = db.get(bdbtxn, keyEntry, dataEntry, LockMode.READ_UNCOMMITTED);
        //else {
        //    DatabaseEntry pKeyEntry = new DatabaseEntry();
        //    // doneTODO: Using dirty read in a secondary db call sends berkeleydb for a tight loop. Not using dirty read for now.
        //    // fixed in 1.1 because of the changes in URISecondaryKeyCreator. Now using Dirty read.
        //    //status = secondaryDb.get(bdbtxn, keyEntry, pKeyEntry, dataEntry, null);
        //    status = secondaryDb.get(bdbtxn, keyEntry, pKeyEntry, dataEntry, LockMode.READ_UNCOMMITTED);
        //    // zTODO: Deserialize the primary key and use it in object creation.
        //}

        if (status != OperationStatus.SUCCESS) {
            return null;
            //throw new DatabaseException("Data Retrieval got status " + status );
        }

        DataInputStream is = new DataInputStream(new ByteArrayInputStream(dataEntry.getData()));
        try {
/*
            if(obj != null) {
                if(config.objectFactory != null)
                    obj = (StreamCreatable) config.objectFactory.create(is, obj);
                else
                    obj.read(is);
            }
            else // Has to construct using the configured objectFactory.
                obj = (StreamCreatable) config.objectFactory.create(is);
*/
            obj = serializer.deserialize(is, obj);
        } catch (Exception e) {
            m_logger.log(Level.ERROR,"", e);
            throw new DatabaseException();
        }

        return obj;
    }

    public Object fetch(DBTransaction txn, Object key, Object obj) throws OMFetchException {

        try {
            return berkeleydbfetch(txn, key, obj);
        } catch (DatabaseException e) {
            m_logger.log(Level.ERROR,"", e);
            throw new OMFetchException();
        }
    }

    /*public Object fetchUsingSecondary(DBTransaction txn, Object key, Object obj) throws OMFetchException {

        try {
            return berkeleydbfetch(txn, key, SECONDARY_KEY, obj);
        } catch (DatabaseException e) {
            m_logManager.logError("", e);
            throw new OMFetchException();
        }
    }*/

    private OperationStatus berkeleydbdelete(DBTransaction txn, Object key/*, boolean isPrimary*/) throws DatabaseException {

        if( key == null /*|| key.key() == null*/ )
            return OperationStatus.KEYEMPTY;

        Transaction bdbtxn = (txn != null ? ((BerkeleyDBTransaction)txn).get() : null);
        DBThreadLocals reusables = (DBThreadLocals) threadlocal.get();
        reusables.clearAll();

        ByteArrayOutputStream keyBytes = reusables.keybytes;
        DataOutputStream keyOs = reusables.keyos;
        Serializer serializer = config.serializer;

        try {
            serializer.serialize(keyOs, key);
            //key.serializeKey(keyOs);
        } catch (Exception e) {
            m_logger.log(Level.ERROR,"", e);
            throw new DatabaseException();
        }

        DatabaseEntry keyEntry = reusables.keyEntry;
        keyEntry.setData(keyBytes.toByteArray());

        OperationStatus status;
        //if(isPrimary)
            status = db.delete(bdbtxn, keyEntry);
        //else
        //    status = secondaryDb.delete(bdbtxn, keyEntry);

        if (status != OperationStatus.SUCCESS) {
            if(status == OperationStatus.NOTFOUND) {
                return status;
            } else {
                throw new DatabaseException("Data deletion got status " +
                                        status);
            }
        }

        return status;
    }

    public boolean delete(DBTransaction txn, Object key) throws OMDeleteException {
        try {
            OperationStatus status = berkeleydbdelete(txn, key);
            if(status == OperationStatus.NOTFOUND) {
                return false;
            }
        } catch (DatabaseException e) {
            m_logger.log(Level.ERROR,"", e);
            throw new OMDeleteException();
        }
        return true;
    }

    /*public boolean deleteUsingSecondary(DBTransaction txn, Object key) throws OMDeleteException {
        try {
            OperationStatus status = berkeleydbdelete(txn, key, SECONDARY_KEY);
            if(status == OperationStatus.NOTFOUND) {
                return false;
            }
        } catch (DatabaseException e) {
            m_logManager.logError("", e);
            throw new OMDeleteException();
        }
        return true;
    }*/

    public void truncate() throws OMException {
        try {
            close();
            env.truncateDatabase(null, config.dbname, false);
            init();
        } catch (DatabaseException e) {
            m_logger.log(Level.ERROR,"", e);
            throw new OMException();
        }
    }
    
    public void deleteDatabase(DBTransaction txn) throws OMException {
        try {
            String dbname = db.getDatabaseName();
            close();
            env.removeDatabase(txn == null ? null : ((BerkeleyDBTransaction)txn).get(), dbname);
        } catch (DatabaseException e) {
            m_logger.log(Level.ERROR,"", e);
            throw new OMException();
        }
    }

    public void readAll(DataStore.ReadCallback cb, Object extObj) throws OMException {

        DatabaseEntry keyEntry = new DatabaseEntry();
        DatabaseEntry dataEntry = new DatabaseEntry();
        //StreamCreatable obj = null;
        Cursor cursor = null;
//        long numread = 0;

        try {
            cursor = db.openCursor(null, null);
            Serializer serializer = config.serializer;

            while(cursor.getNext(keyEntry, dataEntry, null) != OperationStatus.NOTFOUND) {
                DataInputStream is = new DataInputStream(new ByteArrayInputStream(dataEntry.getData()));
//                StreamCreatable obj = extObj;
//                if(obj != null) {
//                    if(config.objectFactory != null)
//                        obj = (StreamCreatable) config.objectFactory.create(is, obj);
//                    else
//                        obj.read(is);
//                }
//                else // Has to construct using the configured objectFactory.
//                    obj = (StreamCreatable) config.objectFactory.create(is);
                Object obj = serializer.deserialize(is, extObj);
                //of obj was null then the wasDeleted f
                if(obj == null) continue;
                cb.readObj(obj);
//                numread++;
//                if(numread % 1000 == 0 && m_logManager.isDebug()) {
//                    m_logManager.logDebug(ResourceManager.getInstance().formatMessage("be.om.recovered",new Long(numread)));
//                }
            }
//            if(m_logManager.isDebug()) {
//                m_logManager.logDebug(ResourceManager.getInstance().formatMessage("be.om.recovered.total", new Long(numread)));
//            }
            cursor.close();
        } catch(Exception e) {
            m_logger.log(Level.ERROR,"", e);
            try {
                if(cursor != null)
                    cursor.close();
            } catch (DatabaseException e1) {
                m_logger.log(Level.ERROR,"", e1);
                throw new OMException();
            }
            throw new OMException();
        }
    }

    public void readAll(DataStore.ReadCallback cb) throws OMException {
        readAll(cb, null);
    }
}
