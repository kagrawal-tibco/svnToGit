/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.cluster.scheduler.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.backingstore.BackingStore;
import com.tibco.cep.runtime.service.cluster.backingstore.GenericBackingStore;
import com.tibco.cep.runtime.service.cluster.scheduler.Poller;
import com.tibco.cep.runtime.service.cluster.scheduler.WorkEntry;
import com.tibco.cep.runtime.service.cluster.scheduler.WorkTuple;
import com.tibco.cep.runtime.service.cluster.scheduler.impl.DefaultScheduler.WorkTupleDBId;
import com.tibco.cep.runtime.service.om.api.ControlDao;
import com.tibco.cep.runtime.session.BEManagedThread;

/**
 * Database driven Poller. This class polls according to the interval, and queries the database with the refresh interval.
 */
public class DefaultDBPoller implements Runnable, Poller, ControlDao.ChangeListener<String, WorkTuple> {

    private BackingStore dbStore;
    private DefaultTaskScheduler taskScheduler;
    private DefaultScheduler scheduler;
    private ControlDao<String, WorkTuple> workListDao;
    private BEManagedThread thread;

    private final static Logger logger = LogManagerFactory.getLogManager().getLogger(DefaultTaskScheduler.class);

    private long totalExpired = 0L;
    private long previousExpSize = 1L;
    
    public DefaultDBPoller(DefaultTaskScheduler taskScheduler)throws Exception  {
        Cluster cluster = taskScheduler.getSchedulerCache().getCluster();
        scheduler = new DefaultScheduler();
        this.taskScheduler = taskScheduler;
        GenericBackingStore genericBackingStore = cluster.getBackingStore();
        if (genericBackingStore != null && (genericBackingStore instanceof BackingStore)) {
            dbStore = (BackingStore)genericBackingStore;
        }

        if (dbStore != null) {
            workListDao = taskScheduler.getSchedulerCache().getSchedulerWorkList(taskScheduler.getName());
            workListDao.registerListener(this);

            final String threadName = cluster.getClusterName() + "-" + taskScheduler.getName() + ".DBPoller";
            final int pollingInterval = taskScheduler.getPollingInterval();
            thread = new BEManagedThread(threadName, cluster.getRuleServiceProvider(), this, pollingInterval);
        }
    }

    @Override
    public void start() {
        if (thread != null) {
            thread.start();
        }
    }

    @Override
    public void shutdown() {
        workListDao.discard();
        if (thread != null) {
            thread.shutdown();
        }
    }

    @Override
    public void flush() {
        try {
            Map<WorkTupleDBId, String> completed = scheduler.markAsComplete();
            complete(completed);
            Map<String, WorkTuple> rescheduled = scheduler.markAsReschedule();
            reschedule(rescheduled);
        } catch (SQLException sqlex) {
            dbStore.check(sqlex);
        } catch (Exception ex) {
            logger.log(Level.ERROR, "Exception occured in the run method of DBPoller", ex);
        }
    }

    @Override
    public void run() {
        try {
            long start = System.currentTimeMillis();
            if (!dbStore.isConnectionAlive()) {
                logger.log(Level.WARN, "DBPoller:  SQL Connection Unavailable, Skipping ");
                return;
            }
            // Update the previously scheduled entries
            flush();

            List expiredKeys = getExpiredKeys();
        	totalExpired = totalExpired + expiredKeys.size();

            if ((previousExpSize > 0) && (expiredKeys.size() == 0)) {
                logger.log(Level.DEBUG, "DBPoller for %s: scheduler size=%s: limit=%s, None to expire.", taskScheduler.getName(), scheduler.size(), taskScheduler.schedulerCache.QUEUE_THRESHOLD == Integer.MAX_VALUE ? "unlimited" : String.valueOf(taskScheduler.schedulerCache.QUEUE_THRESHOLD));
            }

            scheduleExpiredKeys(expiredKeys);
            previousExpSize = expiredKeys.size();
            if (previousExpSize > 0) {
                logger.log(Level.DEBUG, "DBPoller : Scheduled %s timers total=%s took %s msec. Totals pending=%s expired=%s", 
						previousExpSize, scheduler.size(), (System.currentTimeMillis()-start), scheduler.pendingSize(), totalExpired);
            }
        } catch (SQLException sqlex) {
            dbStore.check(sqlex);
        } catch (Exception ex) {
            logger.log(Level.ERROR, "Exception occured in the run method of DBPoller", ex);
        }
    }

    private void scheduleExpiredKeys(List<WorkTuple> workTuples) throws Exception {
        if (workTuples != null) {
            for(WorkTuple tuple : workTuples) {
                if (tuple != null) {
                	String key = tuple.getWorkId();
                	if (key.startsWith(tuple.getWorkQueue() + ".")) {
                		key = key.substring(tuple.getWorkQueue().length() + 1);
                	}
                	if (!scheduler.isScheduled(key)) {
                		scheduler.schedule(key, tuple, taskScheduler.schedulerAgent);
                	}
                }
            }
        }
    }

    private List<WorkTuple> getExpiredKeys() throws Exception {
        String name = taskScheduler.getName();
        int refreshAhead = taskScheduler.getRefreshAhead();
        long currentTime = System.currentTimeMillis();
        List<WorkTuple> workTuples = dbStore.getWorkItems(name, currentTime + refreshAhead, WorkEntry.STATUS_PENDING);
        return workTuples;
    }
    
    private void complete(Map<WorkTupleDBId, String> keys) throws Exception {
        while (true) {
            try {
                if (keys.size() > 0) {
                	removeWorkItemsFromDbAndCache(keys);
                    scheduler.removeAll(keys.values());
                }
                break;
            } catch (SQLException sqlex) {
                dbStore.check(sqlex);
                if (!dbStore.waitForReconnect(60000*10, -1)) {
                    throw sqlex;
                }
            }
        }
    }

    private void reschedule(Map<String, WorkTuple> work) throws Exception {
        while (true) {
            try {
                if (work.size() > 0) {
                    updateWorkItemsToDbAndCache(work);
                    scheduler.removeAll(work.keySet());
                }
                break;
            } catch (SQLException sqlex) {
                dbStore.check(sqlex);
                if (!dbStore.waitForReconnect(60000*10, -1)) {
                    throw sqlex;
                }
            }
        }
    }

    @Override
    public String getType() {
        return "DB";
    }

    @Override
    public int getPendingCount() {
        int ret = scheduler.size();
        ret += workListDao.size();
        return ret;
    }

	@Override
    public ControlDao<String, WorkTuple> getWorkListDao() {
        return workListDao;
    }
    
	@Override
	public void onPut(String key, WorkTuple tuple) {
		handlePutOrUpdate(key, tuple);
	}

	@Override
	public void onRemove(String key, WorkTuple tuple) {
	    // TODO: Remove this - Seems redundant (see: DefaultScheduler$WorkEntryWrapper.done())
	    //       unless it is used when user requests a cancellation.
	    // pass false for addToPendingWrites since code that deletes from the cache always deletes from the DB as well
        scheduler.cancel(key, tuple, false, false);
	}

	@Override
	public void onUpdate(String key, WorkTuple oldValue, WorkTuple newValue) {
		handlePutOrUpdate(key, newValue);
	}

	private void handlePutOrUpdate(String key, WorkTuple tuple){
		long now = System.currentTimeMillis();
        if ((tuple.getScheduledTime() - now) <= taskScheduler.entry.getRefreshAhead()) {
            if ((scheduler.scheduled.size() < taskScheduler.schedulerCache.QUEUE_THRESHOLD) && !scheduler.isScheduled(key)) {
                scheduler.schedule(key, tuple, taskScheduler.schedulerAgent);
            }
        }
	}
	    
    private WorkEntry removeWorkItemsFromDbAndCache(Map<WorkTupleDBId, String> keys) throws Exception {
        if (dbStore != null) {
            dbStore.removeWorkItems(keys.keySet());
        }
        workListDao.removeAll(keys.values());
        return null;
    }

    private WorkEntry updateWorkItemsToDbAndCache(Map<String, WorkTuple> work) throws Exception {
        if (dbStore != null) {
            for (WorkTuple tuple: work.values()) {
                dbStore.updateWorkItem(tuple);
            }
        }
        workListDao.putAll(work);
        return null;
    }
}
