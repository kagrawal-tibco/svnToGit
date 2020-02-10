/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 *  All Rights Reserved.
 *
 *  This software is confidential and proprietary information of
 *  TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.dao.impl.tibas.backingstore;

import com.tibco.as.space.ASException;
import com.tibco.as.space.ASStatus;
import com.tibco.as.space.Tuple;
import com.tibco.as.space.persistence.ActionResult;
import com.tibco.as.space.persistence.AlterAction;
import com.tibco.as.space.persistence.CloseAction;
import com.tibco.as.space.persistence.LoadAction;
import com.tibco.as.space.persistence.OpenAction;
import com.tibco.as.space.persistence.Persister;
import com.tibco.as.space.persistence.PutOp;
import com.tibco.as.space.persistence.ReadAction;
import com.tibco.as.space.persistence.WriteAction;
import com.tibco.as.space.persistence.WriteOp;
import com.tibco.be.oracle.OracleStore;
import com.tibco.cep.as.kit.map.KeyValueTupleAdaptor;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.service.dao.impl.tibas.ASControlDao;
import com.tibco.cep.runtime.service.dao.impl.tibas.ASEntityDao;
import com.tibco.cep.runtime.util.DBNotAvailableException;

/**
 * Created by IntelliJ IDEA.
 * User: chung/ildiz
 * Date: Jan 12, 2011
 * Time: 7:08:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class BEDataGridOracleStore extends OracleStore implements Persister {

    private final static Logger logger = LogManagerFactory.getLogManager().getLogger("backingstore.BEDataGridOracleStore");
    protected boolean isReadOnly = false;
    protected KeyValueTupleAdaptor tupleAdaptor;
    protected boolean isControlCache = true;
    protected ASControlDao controlDao;
    protected ASEntityDao entityDao;
    protected String spaceName;

    // Used only for loading of this class
    public BEDataGridOracleStore() {
        super();
        logger.log(Level.INFO, "Constructing generic ActiveSpaces JdbcStore");
    }

    public BEDataGridOracleStore(String cacheName, Integer readOnly, ASControlDao controlDao) {
        super(cacheName, readOnly);
        this.controlDao = controlDao;
        isReadOnly = (readOnly != 0);
        logger.log(Level.INFO, "Constructing ActiveSpaces Control JdbcStore for %s [read-only=%s]",
                this.controlDao.getName(), this.isReadOnly);
    }
    
    public BEDataGridOracleStore(String cacheName, String schedulerCacheName, Integer readOnly, ASControlDao controlDao) {
        super(cacheName, schedulerCacheName, readOnly);
        this.controlDao = controlDao;
        isReadOnly = (readOnly != 0);
        logger.log(Level.INFO, "Constructing ActiveSpaces Scheduler Control JdbcStore for %s [read-only=%s]",
                this.controlDao.getName(), this.isReadOnly);
    }

    public BEDataGridOracleStore(String cacheName, Integer readOnly, String className, ASEntityDao entityDao) {
        super(cacheName, readOnly, className);
        this.entityDao = entityDao;
        isControlCache = false;
        isReadOnly = (readOnly != 0);
        logger.log(Level.INFO, "Constructing ActiveSpaces Entity JdbcStore for %s [read-only=%s]",
                this.entityDao.getName(), this.isReadOnly);
    }

    public ActionResult onOpen(OpenAction openAction) {
        spaceName = openAction.getSpaceName();
        logger.log(Level.DEBUG, "onOpen is called for " + spaceName);
        return ActionResult.create();
    }

    public ActionResult onAlter(AlterAction alterAction) {
        logger.log(Level.TRACE, "onAlter is called for " + spaceName);
        return ActionResult.create();
    }
    
    public ActionResult onLoad(LoadAction loadAction) {
        logger.log(Level.TRACE, "onLoad is called for " + spaceName);
        return ActionResult.create();
    }

    public ActionResult onClose(CloseAction closeAction) {
        logger.log(Level.TRACE, "onClose is called for " + spaceName);
        return ActionResult.create();
    }
    
    public ActionResult onRead(ReadAction readAction) {
        if (this.getWriteMode()) {
        	setupTupleAdaptor();
            try {
	            Tuple tuple = readAction.getTuple();
	            Object key = tupleAdaptor.extractKey(tuple);
	            Object value = load(key);
	            if (logger.isEnabledFor(Level.TRACE)) {
	                logger.log(Level.TRACE, "Persister on-read is called for %s - key : %s return : %s", spaceName, key, value);
	            }
	            if (value != null) {
	                Tuple val = tupleAdaptor.makeTuple(key);
	                tupleAdaptor.setValue(val, value);
	                return ActionResult.create(val);
	            }
            } catch (Exception exception) {
                if (suspendPersisterException(exception)) {
                    logger.log(Level.WARN, "On-read operation failed - Suspending: %s", exception, exception);
                    return ActionResult.create().setFailed(new ASException(ASStatus.PERSISTER_OFFLINE, "On-read failed: " + exception.getMessage()));
                } else {
                    logger.log(Level.WARN, "On-read operation failed: %s", exception, exception);
                    throw new RuntimeException(exception);
                }
            }
        }
        return ActionResult.create();
    }

    public ActionResult onWrite(WriteAction writeAction) {
        if (!this.isReadOnly && this.getWriteMode()) {
        	setupTupleAdaptor();
            try {
                logger.log(Level.DEBUG, "Persister on-write is called for %s [size=%s]", spaceName, writeAction.getOps().size());
                for (WriteOp op : writeAction.getOps()) {
                    WriteOp.OpType ot = op.getType();
                    Tuple tuple = op.getTuple();
                    Object key = tupleAdaptor.extractKey(tuple);
                    Object value = tupleAdaptor.extractValue(tuple);
                    if (ot == WriteOp.OpType.PUT) {
                        if (((PutOp)op).hasOldTuple()) {
                            if (logger.isEnabledFor(Level.TRACE)) {
                                logger.log(Level.TRACE, "Persister onWrite(update) is called for %s (%s) - value : %s", spaceName, key, value);
                            }
                            // This is for sure an 'update', there is no possibility of this being an 'insert'
                            store(key, value, true);
                        } else {
                            if (logger.isEnabledFor(Level.TRACE)) {
                                logger.log(Level.TRACE, "Persister onWrite(insert) is called for %s (%s) - value : %s", spaceName, key, value);
                            }
                            // Although this is most likely 'insert', there is a possibility of this being an 'update'
                            store(key, value, null);
                        }
                    } else if (ot == WriteOp.OpType.TAKE) {
                        if (logger.isEnabledFor(Level.TRACE)) {
                            logger.log(Level.TRACE, "Persister onWrite(delete) is called for %s (%s)", spaceName, key);
                        }
                        erase(key);
                    }
                }
            } catch (Exception exception) {
                if (suspendPersisterException(exception)) {
                    logger.log(Level.WARN, "On-write operation failed - Suspending: %s", exception, exception);
                    return ActionResult.create().setFailed(new ASException(ASStatus.PERSISTER_OFFLINE, "On-write failed: " + exception.getMessage()));
                } else {
                    logger.log(Level.WARN, "On-write operation failed: %s", exception, exception);
                    throw new RuntimeException(exception);
                }
            }
        }
        return ActionResult.create();
    }

    private boolean suspendPersisterException(Throwable exception) {
        if (exception == null) {
            return false;
        }
        if (exception instanceof DBNotAvailableException) {
            return true;
        }
        // Continue to check the cause!
        return suspendPersisterException(exception.getCause());
    }
    
    public String getCacheClassName(String name) throws Exception{
        char sep = '-';
        int beginIndex = name.lastIndexOf(sep);

        String className = name.substring(beginIndex + 1, name.length());
        //Do not do the following step because the actual entity class name is passed into the constructor
        //className = className.replace('_', '.');
        return className;
    }

    protected void setupTupleAdaptor() {
        if (tupleAdaptor == null) {
            if (isControlCache) {
                tupleAdaptor = controlDao.getTupleAdaptor();
            } else {
                tupleAdaptor = entityDao.getTupleAdaptor();
            }
        }
    }

    @Override
    public void disconnected() {
        super.disconnected();
    }
    
    @Override
    public void reconnected() {
        super.reconnected();
    }
}
