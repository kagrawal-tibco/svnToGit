/*
 * Copyright (c) TIBCO Software Inc 2010.
 * All rights reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 *
 * Author: Suresh Subramani (suresh.subramani@tibco.com)
 * Date  : 26/8/2010
 */

package com.tibco.cep.runtime.service.cluster.backingstore;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tibco.cep.runtime.service.cluster.events.EventTable;
import com.tibco.cep.runtime.service.cluster.scheduler.WorkTuple;
import com.tibco.cep.runtime.service.cluster.scheduler.impl.DefaultScheduler.WorkTupleDBId;
import com.tibco.cep.runtime.service.cluster.system.ObjectTable;
import com.tibco.cep.runtime.service.cluster.txn.RtcTransaction;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Mar 26, 2007
 * Time: 9:07:12 PM
 * To change this template use File | Settings | File Templates.
 */
public interface BackingStore extends GenericBackingStore {

    public Iterator keySet() throws Exception; //TODO: Fatih - Do we need this, why?

    public Iterator objectSet() throws Exception; //TODO: Fatih - Do we need this, why?

    public void setWriteMode(boolean on);

    public boolean getWriteMode();

    public void setClusterDatasource(boolean usePrimary);

    public long loadKeys(int typeId, ObjectTable objectTable) throws Exception;

    public long loadKeys(int typeId, ObjectTable objectTable, EventTable eventQueue) throws Exception;

    public long getNumHandles() throws Exception;

    public long getNumEntities(int typeId, long maxCount) throws Exception;

    public ResultSetCache query(String query, Object[] args) throws Exception;

    public void cleanup() throws Exception;

    public void saveClassRegistry(Map<String, Integer> classRegistry) throws Exception;
    public void saveClassRegistration(String className, int typeId) throws Exception;

    public LinkedHashMap getClassRegistry() throws Exception;

    public void saveWorkItem(WorkTuple item) throws Exception;

    public void updateWorkItem(WorkTuple item) throws Exception;

    public List<WorkTuple> getWorkItems(String workQueue, long time, int status) throws Exception;
    
	public WorkTuple getWorkItem(String key) throws Exception;

    public void removeWorkItem(String key) throws Exception;
    public void removeWorkItems(Collection<WorkTupleDBId> keysAndScheduledTimes) throws Exception;

    public long getMaxEntityId() throws Exception;

    long recoverObjectTable(int typeId, long maxRows) throws Exception;
    
    void removeEntities(Class type, Set<Long> entries) throws Exception;

    void cleanup(int typeId) throws Exception;

    void migrate(int typeId) throws Exception;

    Iterator getObjects(int typeId) throws Exception;

    void clear() throws Exception;

    void saveTransaction(com.tibco.cep.runtime.service.cluster.txn.RtcTransaction txn) throws Exception;

    void saveTransaction(Collection<RtcTransaction> txns) throws Exception;

    boolean isConnectionAlive();

    void addConnectionListener(ConnectionPoolListener lsnr);

    void check(SQLException sqlEx);

    boolean waitForReconnect(long timeout, long maxTries) throws InterruptedException;

    void createSequence(String sequenceName, long minValue, long maxValue, long start, int increment) throws SQLException;

    void removeSequence(String sequenceName) throws SQLException;

    long nextSequence(String sequenceName) throws Exception;

    void setUsePrimaryDatasource(boolean usingPrimary);

    public interface ResultSetCache {
        
        public Object getColumn(int i, int j) throws Exception;

        public Object[] getRow(int i);

        public Set getRows(int start, int end);

        public void closeSet();

        public int size();
    }

    public interface ConnectionPoolListener {
        
        void disconnected();
        
        void reconnected();

        void switched(boolean toSecondary);
    }
    
    void registerType(Class type, int version);
    
    void reInitializeTypes();
    
    public long getMinId(int typeId) throws Exception;
    
    public long getMaxId(int typeId) throws Exception;
    
    public long loadWorkitems(String workQueue, Map targetCache);
    
    long loadObjectKeys(int typeId, int maxRows) throws Exception;
    
    long loadObjects(int typeId, Long[] keys, boolean loadHandles, boolean loadEntities) throws Exception;

    long loadObjects(int typeId, long maxRows, boolean loadHandles, boolean loadEntities) throws Exception;
    
    long loadObjects(int typeId, long maxRows, long start, long end, boolean loadHandles, boolean loadEntities) throws Exception;
}
