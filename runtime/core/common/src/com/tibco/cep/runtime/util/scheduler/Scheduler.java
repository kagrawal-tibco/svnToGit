package com.tibco.cep.runtime.util.scheduler;

import java.util.List;

/**
 *
 * Author: Karthikeyan Subramanian / Date: Jan 27, 2010 / Time: 12:20:25 PM
 */
public interface Scheduler {
    
    void start(Parameters parameters);
    
    void stop();

    Id registerJob(Job job) throws SchedulerException;

    void unregisterJob(Id jobId) throws SchedulerException;

    void scheduleNow(Id jobId) throws SchedulerException;

    void scheduleAt(Id jobId, long timestamp) throws SchedulerException;

    void suspendFutureExecutions(Id jobId) throws SchedulerException;

    long estimateRunTime(Id jobId);

    void resumeJob(Id jobId) throws SchedulerException;

    boolean isJobQueued(Id jobId);

    List<? extends Id> getRegisteredJobIds();

    public static interface Parameters {
        String getName();
        int getMaxThreads();
        int getMinThreads();
    }
}
