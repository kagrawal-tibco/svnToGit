/*
 * Copyright (c) TIBCO Software Inc 2010.
 * All rights reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 *
 * Author: Suresh Subramani (suresh.subramani@tibco.com)
 * Date  : 19/8/2010
 */

package com.tibco.cep.runtime.service.cluster.util;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import com.tibco.cep.runtime.service.ServiceRegistry;
import com.tibco.cep.runtime.service.basic.AsyncWorkerServiceWatcher;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.util.CustomDaemonThreadFactory;
import com.tibco.cep.runtime.util.CustomScheduledThreadPoolExecutor;
import com.tibco.cep.runtime.util.FQName;
import com.tibco.cep.runtime.util.SystemProperty;

/*
* Author: Ashwin Jayaprakash Date: Mar 13, 2009 Time: 1:15:34 PM
*/

public class ScheduledWorkManagerImpl extends CustomScheduledThreadPoolExecutor
        implements ScheduledWorkManager {
    public ScheduledWorkManagerImpl(FQName fqName, String oldName,
                                    int maxNumberOfThreads,
                                    RuleServiceProvider rsp) {
        super(oldName, 1, new CustomDaemonThreadFactory(oldName, rsp));

        setMaximumPoolSize(maxNumberOfThreads);

        setName(fqName);

        ServiceRegistry registry = ServiceRegistry.getSingletonServiceRegistry();
        AsyncWorkerServiceWatcher workManagerWatcher = registry.getWorkManagerWatcher();
        if (workManagerWatcher != null) {
            workManagerWatcher.register(this);
        }
    }

    public void start() {
    }

    /**
     * @param jobs
     */
    public void submitJobs(List<Runnable> jobs) {
        for (int i = 0; i < jobs.size(); i++) {
            Runnable run = (Runnable) jobs.get(i);
            execute(run);
        }
    }

    /**
     * @param job
     */
    public void submitJob(Runnable job) {
        execute(job);
    }

    public void submitJob(Object job) throws InterruptedException {
    }

    public void submitJob(ParallelJob job) throws InterruptedException {
        Iterator<Runnable> r = job.jobs();
        while (r.hasNext()) {
            Runnable runnable = r.next();
            execute(runnable);
        }
    }

    /**
     * @param job
     * @param periodMillis
     * @return
     * @see java.util.concurrent.ScheduledExecutorService#scheduleWithFixedDelay(Runnable, long,
     *      long, java.util.concurrent.TimeUnit)
     */
    public ScheduledFuture<?> scheduleJobAtFixedDelay(Runnable job, long periodMillis) {
        return scheduleWithFixedDelay(job, 0, periodMillis, TimeUnit.MILLISECONDS);
    }

    /**
     * * @see java.util.concurrent.ScheduledExecutorService#scheduleAtFixedRate(Runnable, long,
     * long, java.util.concurrent.TimeUnit)
     *
     * @param job
     * @param delayMillis
     * @return
     */
    public ScheduledFuture<?> scheduleJobAtFixedRate(Runnable job, long delayMillis) {
        return scheduleAtFixedRate(job, 0, delayMillis, TimeUnit.MILLISECONDS);
    }

    public void shutdown() {
        super.shutdown();

        try {
            awaitTermination(Integer.getInteger(SystemProperty.THREADPOOL_SHUTDOWN_TIMEOUT.getPropertyName(), 5), TimeUnit.SECONDS);
        }
        catch (InterruptedException e) {
        }

        ServiceRegistry registry = ServiceRegistry.getSingletonServiceRegistry();
        AsyncWorkerServiceWatcher workManagerWatcher = registry.getWorkManagerWatcher();
        if (workManagerWatcher != null) {
            workManagerWatcher.unregister(this);
        }
    }

    @Override
    public BlockingQueue getJobQueue() {
        return getQueue();
    }
}