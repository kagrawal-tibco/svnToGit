/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.cluster.scheduler.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.backingstore.BackingStore;
import com.tibco.cep.runtime.service.cluster.backingstore.GenericBackingStore;
import com.tibco.cep.runtime.service.cluster.scheduler.Poller;
import com.tibco.cep.runtime.service.cluster.scheduler.WorkTuple;
import com.tibco.cep.runtime.service.om.api.ControlDao;
import com.tibco.cep.runtime.service.om.api.Filter;
import com.tibco.cep.runtime.service.om.api.FilterContext;
import com.tibco.cep.runtime.session.BEManagedThread;

/**
 * Cache driven Poller. This class polls according to the interval, and queries the cache with the refresh interval.
 */
public class DefaultCachePoller implements Runnable, Poller, ControlDao.ChangeListener<String, WorkTuple> {

    private BackingStore dbStore;
    private DefaultTaskScheduler taskScheduler;
    private DefaultScheduler scheduler;
    private ControlDao<String, WorkTuple> workListDao;
    private BEManagedThread thread;

    private final static Logger logger = LogManagerFactory.getLogManager().getLogger(DefaultTaskScheduler.class);

    private long totalExpired = 0L;
    private long previousExpSize = 1L;

    public DefaultCachePoller(DefaultTaskScheduler taskScheduler) throws Exception {
        Cluster cluster = taskScheduler.getSchedulerCache().getCluster();
        scheduler = new DefaultScheduler();
        this.taskScheduler = taskScheduler;
        GenericBackingStore genericBackingStore = cluster.getBackingStore();
        if (genericBackingStore != null && (genericBackingStore instanceof BackingStore)) {
            dbStore = (BackingStore)genericBackingStore;
        }

        workListDao = taskScheduler.getSchedulerCache().getSchedulerWorkList(taskScheduler.getName());
        workListDao.registerListener(this);
        
        final String threadName = cluster.getClusterName() + "-" + taskScheduler.getName() + ".CachePoller";
        final int pollingInterval = taskScheduler.getPollingInterval();
        thread = new BEManagedThread(threadName, cluster.getRuleServiceProvider(), this, pollingInterval);
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
	        Set<String> completed = new HashSet<String>(scheduler.markAsComplete().values()); // use small keys
	        complete(completed);
	        Map<String, WorkTuple> rescheduled = scheduler.markAsReschedule();
	        reschedule(rescheduled);
        } catch (Exception ex) {
            logger.log(Level.ERROR, "Exception occured while flushing out the scheduled entries", ex);
        }
    }

    @Override
    public void run() {
    	try {
    		long start = System.currentTimeMillis();
            // Update the previously scheduled entries
        	flush();

        	Set expiredKeys = getExpiredKeys();
            if ((previousExpSize > 0) && (expiredKeys.size() == 0)) {
                logger.log(Level.DEBUG, "CachePoller for %s: scheduler size=%s: limit=%s, None to expire.", taskScheduler.getName(), scheduler.size(), taskScheduler.schedulerCache.QUEUE_THRESHOLD == Integer.MAX_VALUE ? "unlimited" : String.valueOf(taskScheduler.schedulerCache.QUEUE_THRESHOLD));
            }

        	long failedEvents = scheduleExpiredKeys(expiredKeys);
        	previousExpSize = expiredKeys.size() - failedEvents;
        	totalExpired = totalExpired + previousExpSize;
        	if (previousExpSize > 0) {
                logger.log(Level.DEBUG, "CachePoller : Scheduled %s timers - total=%s, failed=%s took %s msec. Totals pending=%s expired=%s", 
                        previousExpSize, scheduler.size(), failedEvents, (System.currentTimeMillis()-start), scheduler.pendingSize(), totalExpired);
            }
        } catch (Exception ex) {
            logger.log(Level.ERROR, "Exception occured in the run method of CachePoller", ex);
        }
    }

    private long scheduleExpiredKeys(Set expiredKeys) {
        ArrayList<Map.Entry<String, WorkTuple>> expireThese = new ArrayList(expiredKeys);
        Collections.sort(expireThese, ScheduledTimeComparator.INSTANCE);
        
        long failCnt = 0l;
        for (Map.Entry<String, WorkTuple> entry : expireThese) {
        	if (entry != null) {
            	String key = entry.getKey();
            	WorkTuple tuple = entry.getValue();
                if (tuple != null && key != null && !scheduler.isScheduled(entry.getKey())) {
                	try {
                		scheduler.schedule(key, tuple, taskScheduler.schedulerAgent);
                	} catch (Exception exception) {
                		failCnt++;
                		logger.log(Level.ERROR, "Exception schedulling the event with following details WorkId[%s] & Schedule Time[%s]", exception, tuple.getWorkId(), tuple.getScheduledTime());
                	}
            	}
        	}
		}
        return failCnt;
	}

    private Set getExpiredKeys() {
        for (int tries = 0; tries < 3; tries++) {
        	try {
        	    return workListDao.entrySet(new WorkEntryFilter(taskScheduler.getEntry().getRefreshAhead()), 0);
        	}
        	catch (Exception e) {
        	    if (tries < 2)
        	        logger.log(Level.DEBUG, "Failed querying expired keys: %s [retry=%s]", e.getMessage(), tries);
        	    else
                    logger.log(Level.WARN, "Failed querying expired keys: %s", e.getMessage());
        	}
    	}
    	return Collections.EMPTY_SET;
    }
    
    private void complete(Set<String> keys) {
        if (keys.size() > 0) {
            workListDao.removeAll(keys);
            scheduler.removeAll(keys);
        }
    }

    private void reschedule(Map<String, WorkTuple> work) throws Exception {
        if (work.size() > 0) {
            workListDao.putAll(work);
            scheduler.removeAll(work.keySet());
        }
    }

    @Override
    public String getType() {
        return "Cache";
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
    
    static class WorkEntryFilter implements Filter {

		private static final long serialVersionUID = -3769521229765785426L;

		long refreshAhead;
		
    	public WorkEntryFilter() {
    	}
    	
    	public WorkEntryFilter(long refreshAhead) {
    		this.refreshAhead = refreshAhead;
    	}

		@Override
		public boolean evaluate(Object obj, FilterContext context) {
			WorkTuple work = (WorkTuple)obj;
			boolean ret = work.getScheduledTime() <= (System.currentTimeMillis() + refreshAhead);
			return ret;
		}
    }

	@Override
	public void onPut(String key, WorkTuple tuple) {
		handlePutOrUpdate(key, tuple);
	}

	@Override
	public void onRemove(String key, WorkTuple tuple) {
	    // TODO: Remove this - Seems redundant (see: DefaultScheduler$WorkEntryWrapper.done())
	    //       unless it is used when user requests a cancellation.
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
	
	static class ScheduledTimeComparator implements Comparator<Map.Entry<String, WorkTuple>> {
        static ScheduledTimeComparator INSTANCE = new ScheduledTimeComparator();
        public int compare(Map.Entry<String, WorkTuple> aEntry, Map.Entry<String, WorkTuple> bEntry) {
        	if(aEntry == null || bEntry == null) return 0;
        	WorkTuple a = aEntry.getValue();
        	WorkTuple b = bEntry.getValue();
        	if(a == null || b == null) return 0;
        	
            long diff = a.getScheduledTime() - b.getScheduledTime();
            if(diff < 0) return -1;
            else if(diff == 0) return 0;
            else return 1;
        }
    }
}
