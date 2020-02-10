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

package com.tibco.cep.runtime.service.cluster.system.impl;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.model.element.impl.ManagedObjectManager;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.model.event.impl.VariableTTLTimeEventImpl;
import com.tibco.cep.runtime.service.cluster.backingstore.BackingStore;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.ClusterCapability;
import com.tibco.cep.runtime.service.cluster.events.EventTable;
import com.tibco.cep.runtime.service.cluster.events.EventTuple;
import com.tibco.cep.runtime.service.cluster.om.EventLivenessChecker;
import com.tibco.cep.runtime.service.cluster.system.ObjectTable;
import com.tibco.cep.runtime.service.cluster.system.mm.ObjectTableCacheMBean;
import com.tibco.cep.runtime.service.cluster.system.mm.ObjectTableCacheMBeanImpl;
import com.tibco.cep.runtime.service.om.api.ComparisonResult;
import com.tibco.cep.runtime.service.om.api.ControlDao;
import com.tibco.cep.runtime.service.om.api.ControlDaoType;
import com.tibco.cep.runtime.service.om.api.Invocable;
import com.tibco.cep.runtime.util.DBException;
import com.tibco.cep.runtime.util.SystemProperty;
import com.tibco.cep.util.RuleTriggerManager;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Apr 14, 2008
 * Time: 4:27:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class ObjectTableCache implements ObjectTable {

    private final static Logger logger = LogManagerFactory.getLogManager().getLogger(ObjectTableCache.class);

    Cluster cluster;
    BackingStore cacheAsideBackingStore;

    ControlDao<Long, Tuple> objectIdTable;
    ControlDao<String, Tuple> objectExtIdTable;
    ControlDao<String, String> objectExtId2TypeTable;

    ObjectTableCacheMBean delegateBean = null;

    boolean cacheFullyLoaded = false;
    int dbRetryMaxAttempts;
    int dbRetrySleepInterval;

    public ObjectTableCache() {

    }

    public void init(Cluster cluster) throws Exception {
        this.cluster = cluster;
        if (ManagedObjectManager.isOn() == false) {
            delegateBean = new ObjectTableCacheMBeanImpl(this);
            objectIdTable = cluster.getDaoProvider().createControlDao(Long.class, Tuple.class, ControlDaoType.ObjectTableIds);
            objectExtIdTable = cluster.getDaoProvider().createControlDao(String.class, Tuple.class, ControlDaoType.ObjectTableExtIds);

            if (!ClusterCapability.getValue(cluster.getCapabilities(), ClusterCapability.USEOBJECTTABLE, Boolean.class)) {
                objectExtId2TypeTable = cluster.getDaoProvider().createControlDao(String.class, String.class, ControlDaoType.ExtIdTable);
            }

            cacheFullyLoaded = Boolean.parseBoolean(System.getProperty(SystemProperty.CLUSTER_CACHE_FULLY_LOADED.getPropertyName(), "false"));
            logger.log(Level.INFO, "Object table cache initialized with extId-to-type=%s fully-loaded=%s",
                (objectExtId2TypeTable == null ? "N/A":objectExtId2TypeTable.getName()), cacheFullyLoaded);

            dbRetryMaxAttempts = Integer.parseInt(
            		System.getProperty(SystemProperty.CLUSTER_DATAGRID_DB_RETRY_COUNT.getPropertyName(), 
            				String.valueOf(SystemProperty.CLUSTER_DATAGRID_DB_RETRY_COUNT.getValidValues()[0].toString())));
            
            dbRetrySleepInterval = Integer.parseInt(
            		System.getProperty(SystemProperty.CLUSTER_DATAGRID_DB_RETRY_SLEEP.getPropertyName(), 
            				SystemProperty.CLUSTER_DATAGRID_DB_RETRY_SLEEP.getValidValues()[0].toString()));
            
            delegateBean.registerMBean();

            cacheAsideBackingStore = cluster.getCacheAsideStore();
            logger.log(Level.INFO, "Object table cache initialized with backingstore=%s", cacheAsideBackingStore);
        }
    }

    public void start() {
        if (ManagedObjectManager.isOn() == false) {
            objectIdTable.start();
            objectExtIdTable.start();

            if (objectExtId2TypeTable != null) {
                objectExtId2TypeTable.start();
            }
        }
    }

    public ControlDao<Long, Tuple> getObjectIdTable() {
        return objectIdTable;
    }

    public ControlDao<String, Tuple> getObjectExtIdTable() {
        return objectExtIdTable;
    }

    /**
     * @param ids
     * @return <code>null</code> if nothing was found. If there were at least some matching values,
     *         then the order of the values will be the same as the order of keys sent. If that
     *         particular key did not have a matching value, then a <code>null</code> will be placed
     *         in the returned collection.
     * @throws IOException
     */
    public Collection<Tuple> getByIds(Collection<Long> ids)
            throws IOException {
        if (objectIdTable == null) {
            return null;
        }
        Collection<Tuple> results = null;
        int retryCount = 0;
        while (true) {
            try {
                results = objectIdTable.getAll(ids);
                break;
            }
            catch (RuntimeException rte) {
                if (0 == retryCount++) {
                    logger.log(Level.DEBUG, rte, "Failed getByIds: %s", ids);
                }
                if (handleRuntimeException(rte, ids, retryCount)) {
                    return null;
                }
            }
        }
        return results;
    }

    /**
     * @param extIds
     * @return <code>null</code> if nothing was found. If there were at least some matching values,
     *         then the order of the values will be the same as the order of keys sent. If that
     *         particular key did not have a matching value, then a <code>null</code> will be placed
     *         in the returned collection.
     * @throws IOException
     */
    public Collection<Tuple> getByExtIds(Collection<String> extIds)
            throws IOException {
        if (objectExtIdTable == null) {
            return null;
        }
        Collection<Tuple> results = null;
        int retryCount = 0;
        while (true) {
            try {
                results = objectExtIdTable.getAll(extIds);
                break;
            }
            catch (RuntimeException rte) {
                if (0 == retryCount++) {
                    logger.log(Level.DEBUG, rte, "Failed getByExtIds: %s", extIds);
                }
                if (handleRuntimeException(rte, extIds, retryCount)) {
                    return null;
                }
            }
        }
        return results;
    }

    /**
     * @param id
     * @return a generic Dao Tuple interface
     * @throws Exception, and retries indefinitely
     */
    public Tuple getById(long id) throws Exception {
        int retryCount = 0;
        while (true) {
            try {
                return objectIdTable.get(new Long(id));
            }
            catch (RuntimeException rte) {
                if (0 == retryCount++) {
                    logger.log(Level.DEBUG, rte, "Failed getById: %s", id);
                }
                if (handleRuntimeException(rte, id, retryCount)) {
                    return null;
                }
            }
        }
    }

    /**
     * @param extId
     * @return a generic Dao Tuple interface
     * @throws Exception, and retries indefinitely
     */
    public Tuple getByExtId(String extId) throws Exception {
        if (objectExtIdTable == null) {
            return null;
        }
        int retryCount = 0;
        while (true) {
            try {
                return objectExtIdTable.get(extId);
            }
            catch (RuntimeException rte) {
                if (0 == retryCount++) {
                    logger.log(Level.DEBUG, rte, "Failed getByExtId: %s", extId);
                }
                if (handleRuntimeException(rte, extId, retryCount)) {
                    return null;
                }
            }
        }
    }

    /**
     *
     * @param rte Exception to handle
     * @param args Supporting arguments (lookup-key, retry-count, ...)
     * @return boolean Returns 'true' if the exception should be ignored.
     */
    private boolean handleRuntimeException(RuntimeException rte, Object... args) {
        try {
            String message = rte.getMessage();
            String format = "Cache:  Exception in get(%s), retry num=%s: " + message;
            String msg = String.format(format, args);

            if (RuleTriggerManager.retryOnException() && message != null &&
                ((message.indexOf(DBException.DatabaseException_MESSAGE) >= 0) ||
                 (message.indexOf(DBException.PersistersOffline_MESSAGE) >= 0) ||
                 (message.indexOf(DBException.OperationTimeout_MESSAGE) >= 0))) {
                if (this.cacheFullyLoaded) {
                    return true;
                }
                int retryAttempt = ((Integer)args[1]);
                if (retryAttempt > this.dbRetryMaxAttempts) {
                	return true;
                }
                logger.log(Level.WARN, msg);
                try {
                    Thread.sleep(dbRetrySleepInterval);
                } catch (Exception e) {
                }
            } else {
                logger.log(Level.WARN, rte, msg);
                throw rte;
            }
            return false;
        }
        catch (Exception e) {
            throw rte;
        }
    }

    /**
     * mark the tuple as deleted
     * Suresh TODO : - very inefficient - 2 network ops when one call could be sufficed.
     * Also there is no locking - probably expecting some global ticketing is being issued.
     *
     * @param id
     * @throws Exception
     */
    public void markDeleted(long id) throws Exception {
        //return (ClusterEntityProvider.EntityTuple) OBJECTIDTABLE.get(new Long(id));
        Tuple tuple = objectIdTable.get(id);
        if (tuple != null) {
            tuple.markDeleted();
            HashMap<Long, Tuple> tmp = new HashMap<Long, Tuple>();
            tmp.put(id, tuple);

            String extId = tuple.getExtId();
            if (extId != null && extId.length() > 0) {
                HashMap<String, Tuple> extTmp = new HashMap(1);
                extTmp.put(extId, tuple);
                objectExtIdTable.putAll(extTmp);
            }
            objectIdTable.putAll(tmp);
        }
    }

    public boolean containsKey(Long key) {
        return objectIdTable.containsKey(key);
    }

    public void purgeDeletedObjects() {
        //Suresh TODO : This method is never used.
        //objectIdTable.invokeAll(new EqualsFilter(PurgeObjectTableExtractor.INSTANCE, true), new ConditionalRemove(AlwaysFilter.INSTANCE));
    }

    public void putAll(Map<Long, Tuple> data) {
        objectIdTable.putAll(data);
    }

    public void removeAllFromObjectIdTable(Set<Long> entries) {
        objectIdTable.removeAll(entries);
    }

    /**
     * Suresh : TODO - what does this mean?
     *
     * @param eventId
     * @return
     */
    public boolean isEventAlive(Long eventId) {
        try {
            Invocable.Result result = objectIdTable.invokeWithKey(eventId, new EventLivenessChecker());
            ComparisonResult comparisonResult = (ComparisonResult) result.getResult();

            if (comparisonResult == null) {
                return false;
            }

            switch (comparisonResult) {
                case SAME_VERSION:
                    return true;
                case VALUE_NOT_PRESENT:
                case NOT_VERSIONED:
                default:
                    return false;
            }
        } catch (Exception e) {
            logger.log(Level.WARN, e, "Event comparison failed: %s", eventId);
        }
        return true;
    }

    public void putAll(Map<Long, Tuple> changedMap, Map<String, Tuple> changedExtIdMap)
            throws Exception {
        //long t1 = System.currentTimeMillis();
        if (changedMap.size() > 0)
            objectIdTable.putAll(changedMap);
        //long t2 = System.currentTimeMillis();
        if (changedExtIdMap.size() > 0)
            objectExtIdTable.putAll(changedExtIdMap);
        //long t3 = System.currentTimeMillis();
        //logger.log(Level.INFO, "RETE OT Cache times: %s %s", (t2-t1), (t3-t2));
    }
    
    public void removeAll(Collection<Long> deletedIds, Collection<String> deletedExtIds, boolean cleanupBS, Class entityClass) throws Exception {
        if ((objectIdTable == null) && (objectExtIdTable == null)) {
            return;
        }

        if ((deletedIds != null) && (deletedIds.size() > 0)) {
            objectIdTable.removeAll(deletedIds);
        }
        if ((deletedExtIds != null) && (deletedExtIds.size() > 0)) {
            objectExtIdTable.removeAll(deletedExtIds);
        }
        
        Set<Long> deletedIdsAsSet = new HashSet<Long>();
        if (deletedIds != null) {
        	deletedIdsAsSet.addAll(deletedIds);
        }
      
        // Finally if cache-aside persistence is enabled, delete them from backing-store
        if (cacheAsideBackingStore != null && cleanupBS) {
            cacheAsideBackingStore.removeEntities(entityClass, deletedIdsAsSet);
        }
    }

    /**
     * Suresh : TODO - Shouldn't we doing this in a transaction?
     *
     * @param deletedIds
     * @param deletedExtIds
     * @throws Exception
     */
    public void removeAll(Collection<Long> deletedIds, Collection<String> deletedExtIds) throws Exception {
    	removeAll(deletedIds, deletedExtIds, true, null);
    }

    /**
     * @return
     */
    public ElementWriter createElementWriter() {
        return new ObjectWriter();
    }

    public void lockId(long id) {
        //TODO AA add timeouts?
        objectIdTable.lock(id, -1);
    }

    public void unlockId(long id) {
        //TODO AA add timeouts?
        objectIdTable.unlock(id);
    }

    public void shutdown() {
        objectExtIdTable.discard();
        objectIdTable.discard();
    }

    public class ObjectWriter implements ElementWriter {
        HashMap idMap = new HashMap();
        HashMap extIdMap = new HashMap();
        HashMap eventQueueMap = new HashMap();

        protected ObjectWriter() {
        }

        /**
         * @param tuple
         */
        public void addObject(Tuple tuple) throws Exception {

            idMap.put(tuple.getId(), tuple);

            if (tuple.getExtId() != null) {
                extIdMap.put(tuple.getExtId(), tuple);
            }

            if (ClusterCapability.getValue(cluster.getCapabilities(), ClusterCapability.ISMULTIENGINEMODE, Boolean.class)) {
                Class cls = cluster.getMetadataCache().getClass(tuple.getTypeId());
                // Variable TTL is rule based time event
                if (SimpleEvent.class.isAssignableFrom(cls) || VariableTTLTimeEventImpl.class.isAssignableFrom(cls)) {
                    HashMap eq = (HashMap) eventQueueMap.get(tuple.getTypeId());
                    if (eq == null) {
                        eq = new HashMap();
                        eventQueueMap.put(tuple.getTypeId(), eq);
                    }
                    eq.put(tuple.getId(), new EventTuple(tuple.getId()));
                }
            }
        }

        /**
         *
         */
        public void commit() throws Exception {
            if (idMap.size() > 0) {
                objectIdTable.putAll(idMap);
                if (extIdMap.size() > 0) {
                    objectExtIdTable.putAll(extIdMap);
                }
            }
            java.util.Iterator allKeys = eventQueueMap.keySet().iterator();
            while (allKeys.hasNext()) {
                int typeId = (Integer) allKeys.next();

                HashMap eq = (HashMap) eventQueueMap.get(typeId);
                if (eq != null) {
                    Class eventClz = cluster.getMetadataCache().getClass(typeId);
                    EventTable eventQueue = cluster.getEventTableProvider().getEventTable(eventClz);
                    if (eventQueue != null) {
                        eventQueue.addAllEvents(eq);
                    }
                }
            }
            idMap.clear();
            extIdMap.clear();
            eventQueueMap.clear();
        }
    }

    public ControlDao<String, String> getExtIdToTypeMap() {
        return objectExtId2TypeTable;
    }

    @Override
    public void putExtIdToTypeCache(String extId, String uri) {
        if (objectExtId2TypeTable != null) {
            objectExtId2TypeTable.put(extId, uri);
        }
    }

    @Override
    public void putExtIdsToTypeCache(Collection<String> extIds, String uri) {
        if (objectExtId2TypeTable != null) {
            Map<String, String> map = new HashMap<String, String>(extIds.size());
            for(String extId : extIds) {
                map.put(extId, uri);
            }
            objectExtId2TypeTable.putAll(map);
        }
    }

    @Override
    public String getURI(String extId) {
        if (objectExtId2TypeTable != null) {
            return (String) objectExtId2TypeTable.get(extId);
        }
        return null;
    }
}
