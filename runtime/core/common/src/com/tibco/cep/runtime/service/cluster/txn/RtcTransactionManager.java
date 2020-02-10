/*
 * Copyright (c) TIBCO Software Inc 2010.
 * All rights reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 *
 * Author: Suresh Subramani (suresh.subramani@tibco.com)
 * Date  : 27/8/2010
 */

package com.tibco.cep.runtime.service.cluster.txn;

import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.kernel.model.knowledgebase.DuplicateException;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.impl.EntityImpl;
import com.tibco.cep.runtime.model.element.impl.ManagedObjectManager;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.ClusterCapability;
import com.tibco.cep.runtime.service.cluster.agent.InferenceAgent;
import com.tibco.cep.runtime.service.cluster.agent.tasks.AgentAction;
import com.tibco.cep.runtime.service.cluster.backingstore.BackingStore;
import com.tibco.cep.runtime.service.cluster.backingstore.DBJobGroupManager;
import com.tibco.cep.runtime.service.cluster.events.EventTable;
import com.tibco.cep.runtime.service.cluster.events.EventTuple;
import com.tibco.cep.runtime.service.cluster.gmp.GroupMembershipService;
import com.tibco.cep.runtime.service.cluster.gmp.QuorumStatus;
import com.tibco.cep.runtime.service.cluster.system.ObjectTable.Tuple;
import com.tibco.cep.runtime.service.cluster.util.WorkManager;
import com.tibco.cep.runtime.service.om.api.EntityDao;
import com.tibco.cep.runtime.service.om.api.EntityDaoConfig;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.impl.RuleSessionImpl;
import com.tibco.cep.runtime.session.locks.LockManager;
import com.tibco.cep.runtime.util.DBConnectionsBusyException;
import com.tibco.cep.runtime.util.DBNotAvailableException;
import com.tibco.cep.runtime.util.SystemProperty;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.StandardMBean;

import java.lang.management.ManagementFactory;
import java.lang.ref.SoftReference;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Feb 12, 2009
 * Time: 2:36:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class RtcTransactionManager {
    protected static final RTCTxnManagerReport report = new RTCTxnManagerReport();

    protected static Logger logger;
    public static final int TXN_DB_ERROR = -1;
    public static final int TXN_ACTION_ERROR = -2;
    
    protected static int actionRetryCount = 10;
    protected static long actionRetrySleep = 500;
    
    protected static int databaseRetryCount = Integer.MAX_VALUE;
    protected static long databaseRetrySleep = 5000;

    public static void init(Logger logger) {
        try {
            String s = "com.tibco.be:service=" + RTCTxnManagerReport.class.getSimpleName();
            ObjectName name = new ObjectName(s);

            try {
                StandardMBean mBean = new StandardMBean(report, RTCTxnManagerReportMBean.class);

                MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
                mbs.registerMBean(mBean, name);
            }
            catch (InstanceAlreadyExistsException e) {
                //Ignore.
            }
        }
        catch (Exception e) {
            logger.log(Level.ERROR,
                    "Error occurred while registering: " + RTCTxnManagerReportMBean.class.getName(), e);
        }

        RtcTransactionManager.logger = logger;
        
        try {
        	Properties props = RuleServiceProviderManager.getInstance().getDefaultProvider().getProperties();
        	actionRetryCount = Integer.parseInt(props.getProperty(SystemProperty.RTC_TXN_ERROR_ACTION_RETRY_COUNT.getPropertyName(), "10"));
        	actionRetrySleep = Integer.parseInt(props.getProperty(SystemProperty.RTC_TXN_ERROR_ACTION_RETRY_SLEEP.getPropertyName(), "500"));
        	databaseRetryCount = Integer.parseInt(props.getProperty(SystemProperty.RTC_TXN_ERROR_DATABASE_RETRY_COUNT.getPropertyName(), String.valueOf(Integer.MAX_VALUE)));
        	databaseRetrySleep = Integer.parseInt(props.getProperty(SystemProperty.RTC_TXN_ERROR_DATABASE_RETRY_SLEEP.getPropertyName(), "5000"));
        } catch (Exception e) {
        	logger.log(Level.WARN, "Invalid transaction retry parameter specified. Will use action=%s,%s  database=%s,%s",
        			actionRetryCount, actionRetrySleep, databaseRetryCount, databaseRetrySleep);
        }
    }

    public static RTCTxnManagerReport getReport() {
        return report;
    }

    public static void publish(InferenceAgent cacheAgent,
                               RtcTransaction txn, Object trigger,
                               int[] topic,
                               Collection<LockManager.LockData> heldLocks,
                               Collection<AgentAction> actions,
                               boolean useParallel, WorkManager cacheWriter, WorkManager dbWriter) throws Exception {
        final int eventCount = (txn != null) ? txn.getAckEvents().size() : 0;
        Cluster cluster = cacheAgent.getCluster();
        boolean isBackingStoreEnabled = ClusterCapability.getValue(cluster.getCapabilities(), ClusterCapability.HASBACKINGSTORE, Boolean.class);
        boolean isCacheAside = ClusterCapability.getValue(cluster.getCapabilities(), ClusterCapability.ISCACHEASIDE, Boolean.class);

        report.increasePendingEventsToAck(eventCount);

        if (useParallel && !ManagedObjectManager.isOn()) {
            if (txn != null) {
                if (txn.hasOperations()) {
                    if (isBackingStoreEnabled && isCacheAside) {
                        int num_jobs = ((actions != null) && (actions.size() > 0)) ? 3 : 2;
                        TxnTask task = new TxnTask(cacheAgent, topic, txn, heldLocks, actions, num_jobs);
                        report.incrementPendingCacheWrites();
                        cacheWriter.submitJob(new TxnTask_Cache(task));
                        report.incrementPendingDBWrites();
                        task.dbQueueStartTime = System.nanoTime();
                        dbWriter.submitJob(task);
                        if (actions != null) {
                            if (actions.size() > 0) {
                                report.incrementPendingActions();
                                cacheWriter.submitJob(new TxnTask_Actions(task));
                            }
                        }
                    } else {
                        int num_jobs = ((actions != null) && (actions.size() > 0)) ? 2 : 1;
                        TxnTask task = new TxnTask(cacheAgent, topic, txn, heldLocks, actions, num_jobs);
                        report.incrementPendingCacheWrites();
                        cacheWriter.submitJob(new TxnTask_Cache(task));
                        if (actions != null) {
                            if (actions.size() > 0)
                                report.incrementPendingActions();
                            cacheWriter.submitJob(new TxnTask_Actions(task));
                        }
                    }
                } else {
                    //no operation but only action.
                    if (actions != null && actions.size() > 0) {
                        TxnTask task = new TxnTask(cacheAgent, topic, txn, heldLocks, actions, 1);
                        report.incrementPendingActions();
                        cacheWriter.submitJob(new TxnTask_Actions(task));
                    } else {
                        //no operation, no action, just ack events maybe.
                    	try {
                    		applyTransactionEpilogue(cacheAgent, topic, txn, trigger, heldLocks, System.nanoTime());
                    	} finally {
                            // Release the locks
                            if (heldLocks != null) {
                                cacheAgent.getCluster().getLockManager().unlock(heldLocks, null);
                            }
                    	}
                    }
                }
            } else {
                if (actions != null && actions.size() > 0) {
                    TxnTask task=new TxnTask(cacheAgent, topic, txn, heldLocks, actions, 1);
                    report.incrementPendingActions();
                    cacheWriter.submitJob(new TxnTask_Actions(task));
                } else {
                	try {
                		applyTransactionEpilogue(cacheAgent, topic, txn, trigger, heldLocks, System.nanoTime());
                	} finally {
                        // Release the locks
                        if (heldLocks != null) {
                            cacheAgent.getCluster().getLockManager().unlock(heldLocks, null);
                        }
                	}
                }
            }
        } else {
            applyTransaction(cacheAgent, topic, txn, trigger, heldLocks, actions);
        }
    }

    protected static void insertConcepts(InferenceAgent cacheAgent, Map<Integer, Map<Long, Concept>> entries, boolean updateCache) throws Exception {
        Iterator all_entries = entries.entrySet().iterator();
        while (all_entries.hasNext()) {
            Map.Entry<Integer, Map<Long, Concept>> entry = (Map.Entry) all_entries.next();
            int typeId = entry.getKey();
            EntityDao<Long, Entity> provider = cacheAgent.getCluster().getMetadataCache().getEntityDao(typeId);
            if (updateCache || !provider.getConfig().isEvictOnUpdate()) {
                Map<Long, Entity> txnMap = (Map) entry.getValue();
                provider.putAll(txnMap);
            }
        }
    }

    protected static void modifyConcepts(InferenceAgent cacheAgent, Map<Integer, Map<Long, Concept>> entries, boolean updateCache) throws Exception {
        Iterator all_entries = entries.entrySet().iterator();
        while (all_entries.hasNext()) {
            Map.Entry<Integer, Map<Long, Concept>> entry = (Map.Entry) all_entries.next();
            int typeId = entry.getKey();
            EntityDao<Long, Entity> provider = cacheAgent.getCluster().getMetadataCache().getEntityDao(typeId);
            if (updateCache || !provider.getConfig().isEvictOnUpdate()) {
                Map<Long, Entity> txnMap = (Map) entry.getValue();
                provider.putAll(txnMap);
            }
        }
    }

    protected static void deleteConcepts(InferenceAgent cacheAgent, Map<Integer, Set<Long>> entries) throws Exception {
        Iterator all_entries = entries.entrySet().iterator();
        while (all_entries.hasNext()) {
            Map.Entry<Integer, Set<Long>> entry = (Map.Entry) all_entries.next();
            int typeId = entry.getKey();
            Set<Long> txnMap = entry.getValue();
            EntityDao<Long, Entity> provider = cacheAgent.getCluster().getMetadataCache().getEntityDao(typeId);
            provider.removeAll(txnMap);
        }
    }

    protected static void deleteEvents(InferenceAgent cacheAgent, Map<Integer, Set<Long>> entries) throws Exception {
        Iterator all_entries = entries.entrySet().iterator();
        while (all_entries.hasNext()) {
            Map.Entry<Integer, Set<Long>> entry = (Map.Entry) all_entries.next();
            int typeId = entry.getKey();
            Set<Long> txnMap = entry.getValue();
            EntityDao<Long, Entity> provider = cacheAgent.getCluster().getMetadataCache().getEntityDao(typeId);
            provider.removeAll(txnMap);
        }
    }

    protected static void insertEvents(InferenceAgent cacheAgent, Map<Integer, Map<Long, Event>> entries, boolean updateCache) throws Exception {
        Iterator all_entries = entries.entrySet().iterator();
        while (all_entries.hasNext()) {
            Map.Entry<Integer, Map<Long, Event>> entry = (Map.Entry) all_entries.next();
            int typeId = entry.getKey();
            EntityDao<Long, Entity> provider = cacheAgent.getCluster().getMetadataCache().getEntityDao(typeId);
            if (updateCache || !provider.getConfig().isEvictOnUpdate()) {
                Map<Long, Entity> txnMap = (Map) entry.getValue();
                provider.putAll(txnMap);
            }
        }
    }

    protected static void evictConcepts(InferenceAgent cacheAgent, Map<Integer, Map<Long, Concept>> entries, boolean updateCache) throws Exception {
        Iterator all_entries = entries.entrySet().iterator();
        while (all_entries.hasNext()) {
            Map.Entry<Integer, Map<Long, Concept>> entry = (Map.Entry) all_entries.next();
            int typeId = entry.getKey();
            Map<Long, Concept> txnMap = (Map) entry.getValue();
            EntityDao<Long, Entity> provider = cacheAgent.getCluster().getMetadataCache().getEntityDao(typeId);
            if (!updateCache && provider.getConfig().isEvictOnUpdate()) {
                provider.removeAll(txnMap.keySet());
            }
        }
    }

    protected static void ackEvents(InferenceAgent cacheAgent, List<SimpleEvent> entries) throws Exception {
        Iterator<SimpleEvent> all_entries = entries.iterator();
        while (all_entries.hasNext()) {
            SimpleEvent entry = all_entries.next();
            ((SimpleEvent) entry).acknowledge();
            report.decreasePendingEventsToAck(1);
        }
    }

    protected static void saveObjectTable(InferenceAgent cacheAgent, Map<Long, Tuple> idEntries, Map<String, Tuple> extIdEntries) throws Exception {
        cacheAgent.getCluster().getObjectTableCache().putAll(idEntries, extIdEntries);
    }

    @SuppressWarnings("unused")
    protected static void saveEventTableChanged(InferenceAgent cacheAgent, Map<Class, Map<Long, EventTuple>> eventTableChangedMap) {
        for (Map.Entry<Class, Map<Long, EventTuple>> entry : eventTableChangedMap.entrySet()) {
            EntityDaoConfig entityConfig = cacheAgent.getEntityConfig(entry.getKey());
            EventTable eventQueue = cacheAgent.getCluster().getEventTableProvider().getEventTable(entry.getKey());
            if (eventQueue != null) eventQueue.addAllEvents(entry.getValue());
        }
    }

    protected static void removeObjectTable(InferenceAgent cacheAgent, Set<Long> entries) throws Exception {
        cacheAgent.getCluster().getObjectTableCache().removeAllFromObjectIdTable(entries);
    }

    protected static void removeObjectExtIdTable(InferenceAgent cacheAgent, Set<String> entries) throws Exception {
        cacheAgent.getCluster().getObjectTableCache().removeAll(null, entries);
    }

    protected static void removeEventTable(InferenceAgent cacheAgent, Map<Class, Set<Long>> eventTableDeletedSet) {
        for (Map.Entry<Class, Set<Long>> entry : eventTableDeletedSet.entrySet()) {
            EventTable eventQueue = cacheAgent.getCluster().getEventTableProvider().getEventTable(entry.getKey());
            eventQueue.consumeEvent(entry.getValue());
        }
    }

    protected static void runActions(RtcTransaction txn, Object trigger, InferenceAgent cacheAgent, Collection<AgentAction> actions, boolean useParallel) 
    		throws InterruptedException {
        if (actions != null) {
            Iterator allActions = actions.iterator();
            while (allActions.hasNext()) {
                AgentAction action = (AgentAction) allActions.next();

                int i = 0;
                Exception error = null;
                for (; i < actionRetryCount; i++) {
                    try {
                        action.run(cacheAgent);
                        break;
                    }
                    catch (Exception ex) {
                        logger.log(Level.ERROR, "Retrying Action: " + action);
                        ArrayList<RtcTransaction> txns = new ArrayList<RtcTransaction>(1);
                        txns.add(txn);
                    	handleException(txns, trigger, TXN_ACTION_ERROR, -1, ex.getMessage(), i, cacheAgent, useParallel);
                        Thread.sleep(actionRetrySleep);
                        error = ex;
                    }
                }

                if (i == actionRetryCount) {
                    throw new RuntimeException(
                            "Action failed even after " + i + " attempts. Giving up.", error);
                }
            }
        }
    }
    
	protected static void applyTransactionToCacheWithRetry(InferenceAgent cacheAgent, int[] topic, RtcTransaction txn,
														   Collection<LockManager.LockData> heldLocks, 
														   Collection<AgentAction> actions) {
	    int retryAttempts = 0;
		while (true) {
            try {
                applyTransactionToCache(cacheAgent, topic, txn, heldLocks, actions);
                break;
            } catch (Exception ex) {
                handleCacheException(ex, txn, cacheAgent);
                retryAttempts++;
                if (logger.isEnabledFor(Level.DEBUG) && retryAttempts % 10 == 0) {
                    logger.log(Level.DEBUG, ex, "Task apply transaction to cache threw exception. Have already made " +
                                                retryAttempts + " retry attempts.");
                }
            }
        }
	    if (logger.isEnabledFor(Level.DEBUG) && retryAttempts > 0) {
	        logger.log(Level.DEBUG, "It took " + retryAttempts + " retries to apply transaction to cache.");
	    }
	}

    protected static void applyTransactionToCache(InferenceAgent cacheAgent, int[] topic, RtcTransaction txn,
                                                  Collection<LockManager.LockData> heldLocks,
                                                  Collection<AgentAction> actions) throws Exception {
        if (ManagedObjectManager.isOn()) {
            txnToMom(cacheAgent, topic, txn, heldLocks, actions);
        } else {
            txnToCache(cacheAgent, topic, txn, heldLocks, actions);
        }
    }

    protected static void txnToCache(InferenceAgent cacheAgent, int[] topic, RtcTransaction txn,
                                                  Collection<LockManager.LockData> heldLocks,
                                                  Collection<AgentAction> actions) throws Exception {
        if (txn != null) {
            // Transaction Semantics of Isolation are per provider
            boolean updateCache = txn.getRtcTransactionProperties().updateCache;
            if (txn.getAddedConcepts().isEmpty() == false)
                insertConcepts(cacheAgent, txn.getAddedConcepts(), updateCache);
            if (txn.getModifiedConcepts().isEmpty() == false) {
                modifyConcepts(cacheAgent, txn.getModifiedConcepts(), updateCache);
                evictConcepts(cacheAgent, txn.getModifiedConcepts(), updateCache);
            }
            if (txn.getDeletedConcepts().isEmpty() == false)
                deleteConcepts(cacheAgent, txn.getDeletedConcepts());
            if (txn.getAddedEvents().isEmpty() == false)
                insertEvents(cacheAgent, txn.getAddedEvents(), updateCache);
            if (txn.getDeletedEvents().isEmpty() == false)
                deleteEvents(cacheAgent, txn.getDeletedEvents());
            if (txn.getObjectTable_delete().isEmpty() == false)
                removeObjectTable(cacheAgent, txn.getObjectTable_delete());
            if (txn.getObjectExtIdTable_delete().isEmpty() == false)
                removeObjectExtIdTable(cacheAgent, txn.getObjectExtIdTable_delete());
            if (txn.getObjectTable_save().isEmpty() == false || txn.getObjectExtIdTable_save().isEmpty() == false)
                saveObjectTable(cacheAgent, txn.getObjectTable_save(), txn.getObjectExtIdTable_save());
            if (txn.getEventTableDeleted().isEmpty() == false)
                removeEventTable(cacheAgent, txn.getEventTableDeleted());
            if (txn.getEventTableChanged().isEmpty() == false)
                saveEventTableChanged(cacheAgent, txn.getEventTableChanged());
        }
    }

    protected static void txnToMom(InferenceAgent cacheAgent, int[] topic, RtcTransaction txn,
                                   Collection<LockManager.LockData> heldLocks,
                                   Collection<AgentAction> actions) throws Exception {
        final boolean debugLog = logger.isEnabledFor(Level.DEBUG);

        for (Entry<Integer, Map<Long, Event>> entry : txn.getAddedEvents().entrySet()) {
            for (Event event : entry.getValue().values()) {
                ManagedObjectManager.insert((EntityImpl) event);
            }

            if (debugLog) {
                for (Event event : entry.getValue().values()) {
                    logger.log(Level.DEBUG, "Added event " + event);
                }
            }
        }

        for (Entry<Integer, Set<Long>> entry : txn.getDeletedEvents().entrySet()) {
            int typeId = entry.getKey();
            Class entityClass = cacheAgent.getCluster().getMetadataCache().getClass(typeId);

            for (Long eventId : entry.getValue()) {
                ManagedObjectManager.delete(eventId, entityClass);

                if (debugLog) {
                    logger.log(Level.DEBUG, "Deleted event " + eventId);
                }
            }
        }

        for (Entry<Integer, Map<Long, Concept>> entry : txn.getAddedConcepts().entrySet()) {
            for (Concept concept : entry.getValue().values()) {
                ManagedObjectManager.insert((EntityImpl) concept);
            }

            if (debugLog) {
                for (Concept concept : entry.getValue().values()) {
                    logger.log(Level.DEBUG, "Added concept " + concept);
                }
            }
        }

        for (Entry<Integer, Map<Long, Concept>> entry : txn.getModifiedConcepts().entrySet()) {
            for (Concept concept : entry.getValue().values()) {
                ManagedObjectManager.update((EntityImpl) concept);
            }

            if (debugLog) {
                for (Concept concept : entry.getValue().values()) {
                    logger.log(Level.DEBUG, "Modified concept " + concept);
                }
            }
        }

        for (Entry<Integer, Set<Long>> entry : txn.getDeletedConcepts().entrySet()) {
            int typeId = entry.getKey();
            Class entityClass = cacheAgent.getCluster().getMetadataCache().getClass(typeId);

            for (Long conceptId : entry.getValue()) {
                ManagedObjectManager.delete(conceptId, entityClass);

                if (debugLog) {
                    logger.log(Level.DEBUG, "Deleted concept " + conceptId);
                }
            }
        }
    }

    protected static void handleCacheException(Exception ex, RtcTransaction txn, InferenceAgent agent) {
        report.incrementTotalErrors();
        report.recordException(txn, ex);

        GroupMembershipService gmp = agent.getCluster().getGroupMembershipService();
        QuorumStatus quorumStatus = gmp.checkQuorum();

        if (!quorumStatus.isQuorum()) {
            gmp.waitForQuorum();
        }
        else {
            try {
                // Wait for a few seconds and pray that the problem disappears.
                Thread.sleep(5000);
            }
            catch (InterruptedException e) {
            }
        }
    }

    protected static void applyTransactionToDB(InferenceAgent cacheAgent, int[] topic, RtcTransaction txn,
                                               Collection<LockManager.LockData> heldLocks,
                                               Collection<AgentAction> actions) throws Exception {
        if (txn != null) {
            // Apply the transaction to the backingstore database with error handling
            applyTransactionToDBWithRetry(cacheAgent, topic, txn, heldLocks, actions);
        }
    }

    protected static void applyTransactionToActions(InferenceAgent cacheAgent, int[] topic, RtcTransaction txn,
                                                    Object trigger, Collection<LockManager.LockData> heldLocks,
                                                    Collection<AgentAction> actions, boolean useParallel) throws Exception {
        runActions(txn, trigger, cacheAgent, actions, useParallel);
    }

    protected static void applyTransactionEpilogue(InferenceAgent cacheAgent, int[] topic, RtcTransaction txn,
                                                   Object trigger, Collection<LockManager.LockData> heldLocks,
                                                   long startTime) throws Exception {
        if (txn != null) {
            ackEvents(cacheAgent, txn.getAckEvents());
        }

        if (txn != null && txn.doPublish() && cacheAgent.getTxnCache() != null) {
            cacheAgent.getEntityMediator().publish(txn.getKey(topic), txn, cacheAgent);
        }

        long diff = System.nanoTime() - startTime;
        long dur = diff / (1000 * 1000);
        cacheAgent.timeTransactionsPublished += dur;
        
        if (txn != null) {
	        Collection triggers = txn.getTriggers();
	        if (triggers.size() > 0) {
	        	long durFraction = dur / triggers.size();
	        	for(Object trig : triggers) {
	        		triggerEpilogue(cacheAgent, trig, durFraction);
	        	}
	        }
        } else if (trigger != null) {
        	triggerEpilogue(cacheAgent, trigger, dur);
        }
        
        report.addSuccessfulTxnTimeMillis(dur);
        report.incrementTotalSuccessfulTxns();
    }
    
    protected static void triggerEpilogue(InferenceAgent cacheAgent, Object trigger, long dur) {
    	int typeId = cacheAgent.getCluster().getMetadataCache().getTypeId(trigger.getClass());
		cacheAgent.agentStats.incrementPostRTC(typeId, dur);
    }

    /**
     * @param txn
     * @param heldLocks
     * @param actions
     * @throws Exception
     */
    protected static void applyTransaction(InferenceAgent cacheAgent, int[] topic, RtcTransaction txn,
                                           Object trigger, Collection<LockManager.LockData> heldLocks,
                                           Collection<AgentAction> actions) throws Exception {
    	try {
	        long startTime = System.nanoTime();

	        if (txn != null && txn.hasOperations()) {
	            Cluster cluster = cacheAgent.getCluster();
	            boolean isBackingStoreEnabled = ClusterCapability.getValue(cluster.getCapabilities(), ClusterCapability.HASBACKINGSTORE, Boolean.class);
	            boolean isCacheAside = ClusterCapability.getValue(cluster.getCapabilities(), ClusterCapability.ISCACHEASIDE, Boolean.class);

                report.incrementPendingCacheWrites();
                applyTransactionToCacheWithRetry(cacheAgent, topic, txn, heldLocks, actions);
                report.decrementPendingCacheWrites(0L, 0L);

                if (isBackingStoreEnabled && isCacheAside) {
			        report.incrementPendingDBWrites();
	                try {
	                    applyTransactionToDB(cacheAgent, topic, txn, heldLocks, actions);
	                }
	                catch (Exception e) {
	                    report.incrementTotalErrors();
	                    report.recordException(txn, e);

	                    throw e;
	                }
					// Note: decrementPendingDBWrites is done in updateDBOpsStats
	            }
	        }
	        report.incrementPendingActions();
	        try {
	            applyTransactionToActions(cacheAgent, topic, txn, trigger, heldLocks, actions, false);
	        }
	        catch (Exception e) {
	            report.incrementTotalErrors();
	            report.recordException(txn, e);
	            throw e;
	        } finally {
	        	report.decrementPendingActions(0L, 0L);
	        }

	        try {
	            applyTransactionEpilogue(cacheAgent, topic, txn, trigger, heldLocks, startTime);
	        }
	        catch (Exception e) {
	            report.incrementTotalErrors();
	            report.recordException(txn, e);

	            throw e;
	        }
	    } finally {
	        // Release the locks
	        if (heldLocks != null) {
	            cacheAgent.getCluster().getLockManager().unlock(heldLocks, null);
	        }
	    }
    }

    public static class TxnTask_Cache implements Runnable {
        TxnTask txnTask;
        long QInsertTimeStamp = 0L;

        TxnTask_Cache(TxnTask txnTask) {
            this.txnTask = txnTask;
            QInsertTimeStamp = System.nanoTime();
        }

        public void run() {
            long timePickedFromQueue = System.nanoTime();
			applyTransactionToCacheWithRetry(txnTask.agent, txnTask.topic,
											 txnTask.getTransaction(), txnTask.heldLocks,
											 txnTask.actions);
            txnTask.notifyDone(false);
            report.decrementPendingCacheWrites((timePickedFromQueue - QInsertTimeStamp) / (1000 * 1000), (System.nanoTime() - timePickedFromQueue) / (1000 * 1000));
        }
    }


    public static class TxnTask_Actions implements Runnable {
        TxnTask txnTask;
        long QInsertTimeStamp = 0L;

        TxnTask_Actions(TxnTask txnTask) {
            this.txnTask = txnTask;
            QInsertTimeStamp = System.nanoTime();
        }

        public void run() {
            long timePickedFromQueue = System.nanoTime();
            long end = -1;
            try {
                applyTransactionToActions(txnTask.agent, txnTask.topic, txnTask.getTransaction(), txnTask.getTrigger(), txnTask.heldLocks, txnTask.actions, true);
                txnTask.notifyDone(false);
                end = System.nanoTime(); 
            } catch (Exception ex) {
            	end = System.nanoTime();
                report.incrementTotalErrors();
                report.recordException(txnTask.getTransaction(), ex);
                txnTask.notifyDone(true);
            } finally {
                report.decrementPendingActions((timePickedFromQueue - QInsertTimeStamp) / (1000 * 1000), (end - timePickedFromQueue) / (1000 * 1000));
            }
        }
    }

    public static class TxnTask implements Runnable {
        private final Object txnOrTrigger;

        Collection<LockManager.LockData> heldLocks;

        Collection<AgentAction> actions;

        int[] topic;
        InferenceAgent agent;

        int numJobsTobeCompleted;
        int numJobsCompleted;
        boolean gotException = false;

        public long txnQueueTime = 0L;
        public long dbQueueStartTime = 0L;

        /**
         * @param txn
         * @param heldLocks
         * @param actions
         */
        TxnTask(InferenceAgent agent, int[] topic, final Object txnOrTrigger, Collection<LockManager.LockData> heldLocks,
                Collection<AgentAction> actions, int numJobsToBeCompleted) {
            this.agent = agent;
            this.topic = topic;
            this.txnOrTrigger = txnOrTrigger;
            this.heldLocks = heldLocks;
            this.actions = actions;
            this.numJobsTobeCompleted = numJobsToBeCompleted;
            txnQueueTime = System.nanoTime();
        }

        public void run() {
            // This should never be executed!
            // It is however called once, if WorkManagerImpl has [corePoolSize = 1]
        }

        public synchronized void notifyDone(boolean gotException) {
        	this.gotException |= gotException;
            if (++numJobsCompleted == numJobsTobeCompleted) {
                try {
                	try {
                		// Without parallel mode, exceptions in the other post RTC tasks would skip applyTransactionEpilogue
                		if(!gotException) {
                			applyTransactionEpilogue(agent, topic, getTransaction(), getTrigger(), heldLocks, txnQueueTime);
                		}
                	} finally {
	                    // Release the locks
	                    if (heldLocks != null) {
	                        agent.getCluster().getLockManager().unlock(heldLocks, null);
	                    }
                	}
                } catch (Exception ex) {
                    report.incrementTotalErrors();
                    report.recordException(getTransaction(), ex);
                }
            }
        }

        public String toString() {
        	StringBuilder bld = new StringBuilder("TxnTask: Trigger(s)=");
        	String sep = ", ";
        	if (getTransaction() != null) {
        		for(Object trigger : getTransaction().getTriggers()) bld.append(trigger).append(sep);
        		bld.delete(bld.length() - sep.length(), bld.length());
        	} else {
        		bld.append(getTrigger());
        	}
        	//remove trailing comma
        	bld.delete(bld.length() - sep.length(), bld.length());
            return bld.toString();
        }

        public final RtcTransaction getTransaction() {
            return txnOrTrigger instanceof RtcTransaction ? (RtcTransaction)txnOrTrigger : null;
        }
        
        private final Object getTrigger() {
        	return txnOrTrigger instanceof RtcTransaction ? null : txnOrTrigger;
        }
    }

    public static void updateDBOpsStats(int numOps, long timeTakenMillis, long timeInQueueMillis) {
        report.updateDBOpsStats(numOps, timeTakenMillis, timeInQueueMillis);
    }

    public static class RTCTxnManagerReport implements RTCTxnManagerReportMBean {
        protected AtomicLong totalSuccessfulTxns;
        protected AtomicLong totalSuccessfulTxnTimeMillis;
        protected AtomicLong totalErrors;
        protected AtomicLong totalDBQwaitTime;
        protected int lastBatchSize;
        protected AtomicInteger pendingCacheWrites;
        protected AtomicLong totalDBOpsCompleted;
        protected AtomicLong totalDBOpsMillis;
        protected AtomicLong dBOpsBatchCount;
        protected AtomicInteger pendingDBWrites;
        protected AtomicInteger pendingActions;
        protected AtomicInteger pendingLocksToRelease;
        protected AtomicInteger pendingEventsToAck;
        protected AtomicLong totalCacheTxns;
        protected AtomicLong totalActionTxns;
        protected AtomicLong totalCacheOpsMillis;
        protected AtomicLong totalActionsOpsMillis;
        protected AtomicLong totalCacheQwaitTime;
        protected ArrayBlockingQueue<TxnException> recentExceptions;

        public RTCTxnManagerReport() {
            this.totalSuccessfulTxns = new AtomicLong();
            this.totalSuccessfulTxnTimeMillis = new AtomicLong();
            this.totalErrors = new AtomicLong();
            this.totalDBQwaitTime = new AtomicLong();
            this.totalCacheOpsMillis = new AtomicLong();
            this.totalCacheQwaitTime = new AtomicLong();

            this.totalCacheTxns = new AtomicLong();
            this.totalActionTxns = new AtomicLong();
            this.totalActionsOpsMillis = new AtomicLong();

            this.lastBatchSize = 0;
            this.pendingCacheWrites = new AtomicInteger();
            this.pendingDBWrites = new AtomicInteger();
            this.totalDBOpsCompleted = new AtomicLong();
            this.totalDBOpsMillis = new AtomicLong();
            this.dBOpsBatchCount = new AtomicLong();
            this.pendingActions = new AtomicInteger();
            this.pendingLocksToRelease = new AtomicInteger();
            this.pendingEventsToAck = new AtomicInteger();
            this.recentExceptions = new ArrayBlockingQueue<TxnException>(48);
        }

        public long getTotalSuccessfulTxns() {
            return totalSuccessfulTxns.get();
        }

        protected void incrementTotalSuccessfulTxns() {
            this.totalSuccessfulTxns.incrementAndGet();
        }

        public float getAvgSuccessfulTxnTimeMillis() {
            long total = totalSuccessfulTxns.get();
            if (total == 0) {
                return 0;
            }
            return totalSuccessfulTxnTimeMillis.get() / (float) total;
        }

        public long getTotalDBTxnsCompleted() {
            return this.totalDBOpsCompleted.get();
        }

        public void updateDBOpsStats(int numOps, long timeExecuteMillis, long timeQWaitMillis) {
            this.lastBatchSize = numOps;
            this.totalDBOpsCompleted.addAndGet((long) numOps);
            this.pendingDBWrites.addAndGet(numOps * -1);
            this.totalDBOpsMillis.addAndGet(timeExecuteMillis);
            this.totalDBQwaitTime.addAndGet(timeQWaitMillis);
            this.dBOpsBatchCount.incrementAndGet();
        }

        public float getAvgDBQueueWaitTimeMillis() {
            long total = totalDBOpsCompleted.get();
            if (total == 0) {
                return 0;
            }
            return totalDBQwaitTime.get() / (float) total;
        }

        public int getLastDBBatchSize() {
            return this.lastBatchSize;
        }

        public float getAvgDBTxnMillis() {
            long total = totalDBOpsCompleted.get();
            if (total == 0) {
                return 0;
            }
            return totalDBOpsMillis.get() / (float) total;
        }

        public float getAvgDBOpsBatchSize() {
            if (dBOpsBatchCount.get() == 0) {
                return 0;
            }
            return totalDBOpsCompleted.get() / (float) dBOpsBatchCount.get();
        }

        public float getAvgCacheTxnMillis() {
            long total = totalCacheTxns.get();
            if (total == 0) {
                return 0;
            }
            return totalCacheOpsMillis.get() / (float) total;
        }

        public float getAvgActionTxnMillis() {
            long total = totalActionTxns.get();
            if (total == 0) {
                return 0;
            }
            return totalActionsOpsMillis.get() / (float) total;
        }

        public float getAvgCacheQueueWaitTimeMillis() {
            long total = totalActionTxns.get() + totalCacheTxns.get();
            if (total == 0) {
                return 0;
            }
            return totalCacheQwaitTime.get() / (float) total;
        }

        protected void addSuccessfulTxnTimeMillis(long durationMillis) {
            this.totalSuccessfulTxnTimeMillis.addAndGet(durationMillis);
        }

        public long getTotalErrors() {
            return totalErrors.get();
        }

        public void printRecentErrors() {
            for (TxnException recentException : recentExceptions) {
                Throwable error = recentException.getThrowable();
                long at = recentException.getTimestamp();

                RtcTransactionManager.logger.log(Level.ERROR, "Post RTC error occurred at: " + new Timestamp(at), error);
            }
            recentExceptions.clear();
        }

        protected void incrementTotalErrors() {
            this.totalErrors.incrementAndGet();
        }

        public long getPendingCacheWrites() {
            return pendingCacheWrites.get();
        }

        protected void incrementPendingCacheWrites() {
            this.pendingCacheWrites.incrementAndGet();
        }

        protected void decrementPendingCacheWrites(long cacheQWaitTime, long cacheExecTime) {
            this.pendingCacheWrites.decrementAndGet();
            this.totalCacheQwaitTime.addAndGet(cacheQWaitTime);
            this.totalCacheOpsMillis.addAndGet(cacheExecTime);
            this.totalCacheTxns.incrementAndGet();
        }

        public long getPendingDBWrites() {
            return pendingDBWrites.get();
        }

        protected void incrementPendingDBWrites() {
            this.pendingDBWrites.incrementAndGet();
        }

        protected void decrementPendingDBWrites() {
            this.pendingDBWrites.decrementAndGet();
        }

        public int getPendingActions() {
            return pendingActions.get();
        }

        protected void incrementPendingActions() {
            this.pendingActions.incrementAndGet();
        }

        protected void decrementPendingActions(long qWaitTime, long execTime) {
            this.pendingActions.decrementAndGet();
            this.totalActionTxns.incrementAndGet();
            this.totalCacheQwaitTime.addAndGet(qWaitTime);
            this.totalActionsOpsMillis.addAndGet(execTime);
        }

        public int getPendingLocksToRelease() {
            return pendingLocksToRelease.get();
        }

        public void increasePendingLocksToRelease(int numLocks) {
            this.pendingLocksToRelease.addAndGet(numLocks);
        }

        public void decreasePendingLocksToRelease(int numLocks) {
            this.pendingLocksToRelease.addAndGet(numLocks * -1);
        }

        public int getPendingEventsToAck() {
            return pendingEventsToAck.get();
        }

        public void increasePendingEventsToAck(int numEvents) {
            this.pendingEventsToAck.addAndGet(numEvents);
        }

        protected void decreasePendingEventsToAck(int numEvents) {
            this.pendingEventsToAck.addAndGet(numEvents * -1);
        }

        protected BlockingQueue<TxnException> getRecentExceptions() {
            return recentExceptions;
        }

        protected void recordException(RtcTransaction rtcTransaction, Throwable throwable) {
            RtcTransactionManager.logger.log(Level.ERROR, "Post RTC error", throwable);

            for (int i = 0; i < 3; i++) {
                if (recentExceptions.remainingCapacity() == 0) {
                    try {
                        recentExceptions.poll();
                    }
                    catch (Exception e) {
                        //Ignore.
                    }
                }

                try {
                    boolean b = recentExceptions.offer(new TxnException(rtcTransaction, throwable),
                            10, TimeUnit.MILLISECONDS);

                    if (b) {
                        break;
                    }
                }
                catch (InterruptedException e) {
                }
            }
        }

        public void resetStats() {
            totalSuccessfulTxns.set(0);
            totalSuccessfulTxnTimeMillis.set(0);
            totalErrors.set(0);
            totalDBOpsCompleted.set(0);
            totalDBOpsMillis.set(0);
            totalDBQwaitTime.set(0);
            totalActionTxns.set(0);
            totalActionsOpsMillis.set(0);
            totalCacheQwaitTime.set(0);
            totalCacheOpsMillis.set(0);
            totalCacheTxns.set(0);
            recentExceptions.clear();
            dBOpsBatchCount.set(0);
        }
    }

    /**
     * Retry DB-Operations when/if database failures prevents successful commits.
     * @throws InterruptedException 
     */
    public static void applyTransactionToDBWithRetry(InferenceAgent cacheAgent, int[] topic, RtcTransaction txn,
            Collection<LockManager.LockData> heldLocks,
            Collection<AgentAction> actions) throws InterruptedException {
        ArrayList<RtcTransaction> txns = new ArrayList<RtcTransaction>();
        txns.add(txn);

        BackingStore backingStore = cacheAgent.getCluster().getCacheAsideStore();

        int retryCount = 0;
        Throwable error = null;
        for ( ; retryCount < databaseRetryCount; retryCount++) {
        	if (retryCount > 0) {
        		logger.log(Level.WARN, "Attempting to write txn to DB. Retry Count = " + retryCount);
        	}
        	try {
        		long startTime = System.currentTimeMillis();
        		try {
        			if (backingStore != null) {
        				backingStore.saveTransaction(txn);
        			}
        		} catch (DuplicateException dpe) {
        			// No ops. Proceed to notify
        			error = dpe;
        			int errCode = DBJobGroupManager.DB_OTHER;
        			if (dpe.getCause() instanceof SQLException) {
        				errCode = ((SQLException)dpe.getCause()).getErrorCode();
        			}
        			DBJobGroupManager.handleDBException(txns, errCode, dpe.getMessage(), retryCount, cacheAgent);
        		}
        		long totalExecuteMillis = System.currentTimeMillis() - startTime;
        		int actualTxnTasks = 1;
        		long totalQWaitMillis = 0;
        		RtcTransactionManager.updateDBOpsStats(actualTxnTasks, totalExecuteMillis, totalQWaitMillis);
        		if (retryCount > 0) {
        			logger.log(Level.INFO, "Attempting to write txn to DB successful. Retry Count = " + retryCount);
        		}
        		return;
        	}
        	catch (DBNotAvailableException connEx) {
        		// Database is disconnected. Wait until reconnected.
        		error = connEx;
        		logger.log(Level.WARN, "DBNotAvailableException: " + connEx.getMessage() + ", Waiting to reconnect.");
        		DBJobGroupManager.handleDBException(txns, DBJobGroupManager.DB_NOTAVAILABLE, connEx.getMessage(), retryCount, cacheAgent);
        		try {
        			if (backingStore != null) {
        				if (!backingStore.waitForReconnect(60000, -1)) {
        					cacheAgent.getCluster().getRuleServiceProvider().shutdown();
        				}
        			}
        		}
        		catch (Exception e) {
        			logger.log(Level.DEBUG, "Wait-for-reconnect failure: " + e.getMessage());
        		}
        	}
        	catch (DBConnectionsBusyException busyEx) {
        		// All connections are busy. Wait for sometime.
        		error = busyEx;
        		logger.log(Level.WARN, "DBConnectionsBusyException: " + busyEx.getMessage());
        		DBJobGroupManager.handleDBException(txns, busyEx.getErrorCode(), busyEx.getMessage(), retryCount, cacheAgent);
        		Thread.sleep(databaseRetrySleep);
        	}
        	catch (RuntimeException runtEx) {
        		if (runtEx instanceof java.lang.ClassCastException) {
        			throw runtEx;
        		}
        		error = runtEx;
        		logger.log(Level.WARN, "Failed writing to DB: " + runtEx.getMessage());
        		DBJobGroupManager. handleDBException(txns, DBJobGroupManager.DB_OTHER, runtEx.getMessage(), retryCount, cacheAgent);
        		Thread.sleep(databaseRetrySleep);
        	}
        	catch (Throwable otherEx) {
        		error = otherEx;
        		logger.log(Level.ERROR, "Failed executing DB transactions: " + otherEx.getMessage());
        		DBJobGroupManager.handleDBException(txns, DBJobGroupManager.DB_OTHER, otherEx.getMessage(), retryCount, cacheAgent);
        		Thread.sleep(databaseRetrySleep);
        	}
        }

        if (retryCount > 0) {
            logger.log(Level.INFO, "Attempting to write txn to DB successful. Retry Count = " + retryCount);
        }
        
        if (retryCount == databaseRetryCount) {
        	throw new RuntimeException(
        			"DB transaction failed even after " + retryCount + " attempts. Giving up.", error);
        }
    }

    public static class TxnException {
        protected final long timestamp;

        protected final SoftReference<RtcTransaction> txn;

        protected final Throwable throwable;

        public TxnException(RtcTransaction txn, Throwable throwable) {
            this.timestamp = System.currentTimeMillis();
            this.txn = new SoftReference<RtcTransaction>(txn);
            this.throwable = throwable;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public RtcTransaction getTxn() {
            return txn.get();
        }

        public Throwable getThrowable() {
            return throwable;
        }
    }

    /**
     *
     * Invokes a user-defined error handler rulefunction if there is a post-rtc exception. (for SR_ID:1-ASHRTF)
     *
     * The signature of this rulefunction should be
     *
     * int AppErrorHandler (Object txns, int errorType, int errCode, String errMsg, long retryCount)
     * 	txns is an Object[] where
     *    txns[0] -> Concept[] of new concepts
     *    txns[1] -> Concept[] of modified concepts
     *    txns[2] -> Long[] of deleted concept ids
     *    txns[3] -> SimpleEvent[] of new Events
     *    txns[4] -> Long[] of deleted Event ids
     *  errorType -> -1 for database errors, -2 for actions errors.
     *  errCode -> associated error code.
     *  errMsg -> associated exception message.
     *  retryCount -> associated retry count.
     *
     */
	public static int handleException(ArrayList<RtcTransaction> txns, Object trigger,
			int errorType, int errCode, String exMsg, long retryCnt, InferenceAgent cacheAgent, boolean useParallel)
	{
		String errRFN = RuleServiceProviderManager.getInstance().getDefaultProvider().getProperties()
						.getProperty(SystemProperty.RTC_TXN_ERROR_FN.getPropertyName());
        boolean recursiveRTC = Boolean.parseBoolean(
                cacheAgent.getRuleSession().getRuleServiceProvider().getProperties()
                        .getProperty(SystemProperty.RTC_TXN_ERROR_FN_RECURSIVE_RTC.getPropertyName()));
		if (errRFN != null) {
			try {
				Concept[] newCepts = null;
				Concept[] updCepts = null;
				Long[] delCepts = null;
				SimpleEvent[] newEvents = null;
				Long[] delEvents = null;

				Object[] entities = null;
				Object[] args = null;
				
				if (txns != null && txns.size() > 0) {
					Iterator <RtcTransaction> txnI = txns.iterator();
					List newCeptList = new ArrayList();
					List updCeptList = new ArrayList();
					List delCeptList = new ArrayList();
					List newEventList = new ArrayList();
					Set delEventList = new HashSet();
					
					while (txnI.hasNext()) {
						RtcTransaction txn = txnI.next();
						if (txn != null) {
							if (txn.getAddedConcepts() != null) {
								for (Iterator<Map<Long, Concept>> i = txn.getAddedConcepts().values().iterator(); i.hasNext();) {
									newCeptList.addAll(i.next().values());
								}
							}
							if (txn.getModifiedConcepts() != null) {
								for (Iterator<Map<Long, Concept>> i = txn.getModifiedConcepts().values().iterator(); i.hasNext();) {
									updCeptList.addAll(i.next().values());
								}
							}
							if (txn.getDeletedConcepts() != null) {
								for (Iterator<Set<Long>> i = txn.getDeletedConcepts().values().iterator(); i.hasNext();) {
									delCeptList.addAll(i.next());
								}
							}
							if (txn.getAddedEvents() != null) {
								for (Iterator<Map<Long, Event>> i = txn.getAddedEvents().values().iterator(); i.hasNext();) {
									newEventList.addAll(i.next().values());
								}
							}
							if (txn.getDeletedEvents() != null) {
								for (Set<Long> s : txn.getDeletedEvents().values()) {
									delEventList.addAll(s);
								}
							}
							if (txn.getAckEvents() != null) {
								//delEventList doesn't include 0 ttl events in general
								//but the docs say it should include the trigger event if it is 0 ttl.
								HashSet triggerSet = new HashSet(txn.getTriggers());
								//0 ttl events are in the ack list but not the new list, unlike new non-0 ttl cache events
								triggerSet.retainAll(txn.getAckEvents());
								triggerSet.removeAll(newEventList);
								for(Object o : triggerSet) {
									if (o instanceof Event) {
										delEventList.add(((Event)o).getId());
									}
								}
							}
						}
					}
					newCepts = (Concept[])newCeptList.toArray(new Concept[] {});
					updCepts = (Concept[])updCeptList.toArray(new Concept[] {});
					delCepts = (Long[])delCeptList.toArray(new Long[] {});
					newEvents = (SimpleEvent[])newEventList.toArray(new SimpleEvent[]{});
					delEvents = (Long[])delEventList.toArray(new Long[]{});
	
					entities = new Object[]{newCepts, updCepts, delCepts, newEvents, delEvents};
					args = new Object[]{entities,  new Integer (errorType), errCode, exMsg, retryCnt};
				} else if (trigger != null && trigger instanceof Event) {
					entities = new Object[]{newCepts, updCepts, delCepts, newEvents, new Long[] {((Event)trigger).getId()}};
					args = new Object[]{entities,  new Integer (errorType), errCode, exMsg, retryCnt};
				}

				ExceptionCallbackTask.invokeCallback(cacheAgent, errRFN, args, useParallel, recursiveRTC);
			} catch (Exception ex) {
				System.err.println ("Exception while performing user-defined exception handler: " + errRFN);
				ex.printStackTrace();
			}
		}
		return 0;
	}
	
	/*
	 * With parallel ops the rtc thread always puts the post rtc tasks onto a queue and clears the current rulesession
	 * so invokeRuleFunction does not throw the exception "cannot invoke in same rulesesson."
	 * However, invokeFunction starts an RTC and the caller waits for the rtc to finish (but not the post RTC).
	 * Without parallel ops the exception callback needs to be transferred to another thread to avoid the invoked in same rulesession exception
	 * and the RTC worker must wait for the callback's RTC to finish.  However any exception callbacks needed for the RTC
	 * started by the first callback should not force the exception's thread to wait so that a series of nested exception callbacks do not block all the threads.
	 * Without concurrentwm it is not ok to wait for the first exception callback RTC to complete because only one RTC is allowed at a time
	 * so the RTC that caused the exception must be allowed to complete before invoking the callback.  
	 */
	public static class ExceptionCallbackTask implements Runnable
	{
	    private int result = 0;
	    private String errRFN;
	    private Object[] args;

	    private static ThreadLocal<ExceptionCallbackTask> currentExceptionCallback = new ThreadLocal();
	    
	    private ExceptionCallbackTask(String errRFN, Object[] args) {
	        this.errRFN = errRFN;
	        this.args = args;
	    }

	    public static int invokeCallback(InferenceAgent cacheAgent, String errRFN, Object[] args, boolean useParallel, boolean recursiveRTC) throws InterruptedException {
            useParallel &= !ManagedObjectManager.isOn();
            if (useParallel) {
                return _invokeCallback(errRFN, args, false);
            } else {
                boolean isConcurrent = cacheAgent.getAgentConfig().isConcurrent();
                if (isConcurrent) {
                    //Simulate parallel ops, by making the current RTC worker thread act like a post RTC thread.
                    //With parallel ops, the post rtc thread if it gets an exception will run an error callback rtc on its own thread
                    //and wait for it to complete before returning the result of the callback function.
                    //An RTC worker can't do this so use another thread to run it and wait for it to complete
                    //before returning the result.
                    //If the error callback RTC has an error
                    //In Parallel ops the error RTC would already have finished and returned the result
                    //and the post RTC would be a task in the post rtc thread pool and would be run later
                    //at which point the error would be hit and the callback would run.  The invocations
                    //are not recursive.
                    //Without parallel ops detect that the current rtc is for an error callback with the thread local
                    //currentExceptionCallback.  This means that ExceptionCallbackTask.run() is above this point in the call stack
                    //so this thread has the monitor and is OK to call notify, which causes the previous error callback's
                    //result to be returned before we execute this error callaback, the same as with parallel ops.
                    //The result is returned to the different thread that called wait on the ExceptionCallbackTask whose
                    //run() method is above this point in the call stack.
                    ExceptionCallbackTask currentCallback = currentExceptionCallback.get();

                    ExceptionCallbackTask task = new ExceptionCallbackTask(errRFN, args);
                    if (currentCallback != null) {
                        currentCallback.notify();
                        cacheAgent.getPostRTCWorkPool().submitJob(task);
                        //this return value is ultimately discarded so don't wait for return value of the new task
                        //because waiting would cause a deadlock
                        return 0;
                    } else {
                        synchronized (task) {
                            cacheAgent.getPostRTCWorkPool().submitJob(task);
                            task.wait();
                            return task.result;
                        }
                    }
                } else if (recursiveRTC) {
                    return _invokeCallback(errRFN, args, true);
                }
                // Without recursiveRTC, or useParallel or isConcurrent, the callback won't work so just do nothing.
                else {
                    return 0;
                }
            }
	    }

	    private static int _invokeCallback(String errRFN, Object[] args, boolean recursiveRTC) {
	        if (RuleServiceProviderManager.getInstance().getDefaultProvider().getRuleRuntime().getRuleSessions().length > 0) {
                RuleSession session = RuleServiceProviderManager.getInstance().getDefaultProvider().getRuleRuntime().getRuleSessions()[0];
                if (session != null) {
                    Object retVal;
                    if (recursiveRTC) {
                        retVal = ((RuleSessionImpl)session).invokeFunction(errRFN, args, true, true);
                    } else {
                        retVal = session.invokeFunction(errRFN, args, true);
                    }

                    if (retVal instanceof Integer) {
                        return ((Integer)retVal).intValue();
                    }
                }
            }
	        return 0;
	    }
	    
	    public synchronized void run() {
	        try {
	            currentExceptionCallback.set(this);
    	        result = _invokeCallback(errRFN, args, false);
	        } finally {
	            currentExceptionCallback.set(null);
                notify();
	        }
	    }
	}
}