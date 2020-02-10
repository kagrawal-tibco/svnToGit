/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.cluster.scheduler.impl;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.service.cluster.scheduler.WorkEntry;
import com.tibco.cep.runtime.service.cluster.scheduler.WorkTuple;

public class DefaultScheduler {
    
    private final static Logger logger = LogManagerFactory.getLogManager().getLogger(DefaultTaskScheduler.class);

    // Value is the cache workId which is the db workId minus the scheduler name prefix
    private Map<WorkTupleDBId, String> pendingWrites = new HashMap<WorkTupleDBId, String>();
    private Map<String, WorkTuple> pendingUpdates = new HashMap<String, WorkTuple>();
    
    Map<String, WorkEntryWrapper> scheduled = new ConcurrentHashMap<String, WorkEntryWrapper>();

    private final ReentrantLock writeLock = new ReentrantLock();
    private Timer timer = new Timer();

    public DefaultScheduler() {
        
    }

    void shutdown() {
        timer.cancel();
    }

    int size() {
        return scheduled.size();
    }

    int pendingSize() {
        return pendingWrites.size();
    }
    
    public Map<WorkTupleDBId, String> markAsComplete() {
        try {
            acquireLock();
            Map<WorkTupleDBId, String> tmp = pendingWrites;
            pendingWrites = new HashMap<WorkTupleDBId, String>();
            return tmp;
        }
        finally {
            releaseLock();
        }
    }

    public Map<String, WorkTuple> markAsReschedule() {
        try {
            acquireLock();
            Map<String, WorkTuple> tmp = pendingUpdates;
            pendingUpdates = new HashMap<String, WorkTuple>();
            return tmp;
        }
        finally {
            releaseLock();
        }
    }

	private void releaseLock() {
		writeLock.unlock();
	}

	private void acquireLock() {
		writeLock.lock();
	}

    public void schedule(String key, WorkTuple work, Object schedulerAgent) {
        try {
            acquireLock();
            WorkEntryWrapper entry = new WorkEntryWrapper(key, work, schedulerAgent);
            Date scheduleIn = new Date(work.getWork().getScheduleTime());
            timer.schedule(entry, scheduleIn);
            scheduled.put(key, entry);
        }
        finally {
            releaseLock();
        }
    }

    boolean isScheduled(String key) {
        try {
            acquireLock();
            return scheduled.containsKey(key);
        }
        finally {
            releaseLock();
        }
    }

    void cancel(String key, WorkTuple canceledEntry, boolean addToPendingWrites, boolean addToPendingUpdates) {
        try {
            acquireLock();
            WorkEntryWrapper entry = (WorkEntryWrapper) scheduled.get(key);
            if (entry != null && (canceledEntry == null || entry.scheduledExecutionTime() == canceledEntry.getWork().getScheduleTime())) {
                entry.cancel();
                scheduled.remove(key);
                pendingUpdates.remove(key);
            }

            if(addToPendingWrites) done(key, canceledEntry);
            if(addToPendingUpdates) repeat(key, canceledEntry);
        }
        finally {
            releaseLock();
        }
    }

    void removeAll(Collection<String> keys) {
        try {
            acquireLock();
            for (String s:keys) {
            	scheduled.remove(s);
            }
        }
        finally {
            releaseLock();
        }
    }

    private void done(String key, WorkTuple work) {
        try {
        	acquireLock();
            pendingWrites.put(new WorkTupleDBId(work.getWorkId(), work.getScheduledTime()), key);
        }
        finally {
            releaseLock();
        }
    }

    private void repeat(String key, WorkTuple work) {
        try {
            acquireLock();
            pendingUpdates.put(key, work);
        }
        finally {
            releaseLock();
        }
    }
    
    class WorkEntryWrapper extends TimerTask 
    {
        WorkTuple entry;
        String key;
        boolean done = false;
        Object schedulerAgent;

        WorkEntryWrapper(String key, WorkTuple entry, Object schedulerAgent) {
            this.entry = entry;
            this.key = key;
            this.schedulerAgent = schedulerAgent;
        }

        public void run() {
            try {
                WorkEntry work = entry.getWork();
                work.execute(key, schedulerAgent);
                done = true;
                if (work.getRepeatInterval() > 0) {
                    work.setScheduleTime(work.getScheduleTime() + work.getRepeatInterval());
                    entry.setScheduledTime(work.getScheduleTime());
                    repeat(key, entry);
                }
                else {
                    done(key, entry);
                }
                this.cancel();
            } catch (Exception ex) {
                logger.log(Level.ERROR, ex, "Exception during execution of scheduled task. Canceling task with key=%s, exception=%s",
                        key, ex.getMessage());
                DefaultScheduler.this.cancel(key, entry, true, true);
                done = false;
            }
        }
    }
    
    public static class WorkTupleDBId 
    {
        public String dbKey;
        public long scheduledTime;
        
        public WorkTupleDBId(String dbKey, long scheduledTime) {
            this.dbKey = dbKey;
            this.scheduledTime = scheduledTime;
        }
        
        public int hashCode() {
            return new HashCodeBuilder().append(dbKey).append(scheduledTime).toHashCode();
        }
        
        public boolean equals(Object test) {
            return test instanceof WorkTupleDBId && ((WorkTupleDBId)test).dbKey == dbKey && ((WorkTupleDBId)test).scheduledTime == scheduledTime;
        }
        
        public String toString() {
            String id = "id: ";
            String time = " time: ";
            return new StringBuilder(id.length() + time.length() + dbKey.length() + 20).append(id).append(dbKey).append(time).append(scheduledTime).toString();
        }
    }
}