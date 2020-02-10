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
import java.util.concurrent.TimeUnit;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.service.ServiceRegistry;
import com.tibco.cep.runtime.service.basic.AsyncWorkerServiceWatcher;
import com.tibco.cep.runtime.session.BEManagedThread;
import com.tibco.cep.runtime.session.JobGroupAwareBlockingQueue;
import com.tibco.cep.runtime.session.JobGroupManager;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.impl.RuleServiceProviderImpl;
import com.tibco.cep.runtime.util.CustomDaemonThreadFactory;
import com.tibco.cep.runtime.util.CustomThreadPoolExecutor;
import com.tibco.cep.runtime.util.FQName;
import com.tibco.cep.runtime.util.SuspendAwareBlockingQueue;
import com.tibco.cep.runtime.util.SuspendAwareLBQImpl;
import com.tibco.cep.runtime.util.SystemProperty;

/*
* Author: Ashwin Jayaprakash Date: Mar 13, 2009 Time: 1:15:34 PM
*/

public class WorkManagerImpl extends CustomThreadPoolExecutor implements WorkManager {
    
    final static Logger LOGGER = LogManagerFactory.getLogManager().getLogger(WorkManagerImpl.class);
    
    protected RuleServiceProvider rsp;

    boolean pausable = true;

    public WorkManagerImpl(FQName fqName, String oldName, int maxNumberOfThreads,
                           RuleServiceProvider rsp) {
        this(fqName, oldName, maxNumberOfThreads, Integer.MAX_VALUE, rsp);
    }

    public WorkManagerImpl(FQName fqName, String oldName,
                           int maxNumberOfThreads, int maxJobQueueSize,
                           RuleServiceProvider rsp) {
        this(fqName, oldName, maxNumberOfThreads, maxJobQueueSize, rsp, null);
    }

    public WorkManagerImpl(FQName fqName, String oldName,
                           int maxNumberOfThreads, int maxJobQueueSize,
                           RuleServiceProvider rsp, boolean pausable) {
        this(fqName, oldName, maxNumberOfThreads, maxJobQueueSize, rsp, null);

        this.pausable = pausable;
    }

    public WorkManagerImpl(FQName fqName, String oldName,
            int maxNumberOfThreads, int maxJobQueueSize,
            RuleServiceProvider rsp, JobGroupManager jobGroupManager) {
        // TODO: Validate the following comment
        /* FI: keep coreNumberOfThreads at 0, making it higher value causes issues with RTC-Txn execution */
        this(fqName, oldName, 0, maxNumberOfThreads, maxJobQueueSize, rsp, jobGroupManager);
    }
    
    public WorkManagerImpl(FQName fqName, String oldName,
            int coreNumberOfThreads, int maxNumberOfThreads, int maxJobQueueSize,
            RuleServiceProvider rsp, JobGroupManager jobGroupManager) {
        super(oldName,
                coreNumberOfThreads, maxNumberOfThreads,
                10 * 60 /* 10 mins */, TimeUnit.SECONDS,
                createBQ(maxJobQueueSize, jobGroupManager),
                new CustomDaemonThreadFactory(oldName, rsp, jobGroupManager), rsp.getLogger(CustomThreadPoolExecutor.class));

        setName(fqName);

        ServiceRegistry registry = ServiceRegistry.getSingletonServiceRegistry();
        AsyncWorkerServiceWatcher workManagerWatcher = registry.getWorkManagerWatcher();
        if (workManagerWatcher != null) {
            workManagerWatcher.register(this);
        }

        this.rsp = rsp;
    }

     private static SuspendAwareBlockingQueue<Runnable> createBQ(int maxJobQueueSize,
                                                                JobGroupManager jobGroupManager) {
        if (jobGroupManager == null) {
            return new SuspendAwareLBQImpl<Runnable>(maxJobQueueSize);
        }

        return new JobGroupAwareBlockingQueue(new SuspendAwareLBQImpl<Runnable>(maxJobQueueSize), jobGroupManager);
    }

    public void start() {
        ((RuleServiceProviderImpl) rsp).registerExecutableResource(this);

        resumeResource();
    }

    //-----------

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        BEManagedThread managedThread = (BEManagedThread) t;
        managedThread.executePrologue();

        if (pausable) {
            super.beforeExecute(t, r);
        }
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        if (pausable) {
            super.afterExecute(r, t);
        }

        if (t == null) {
            BEManagedThread managedThread = (BEManagedThread) Thread.currentThread();
            managedThread.executeEpilogue();
        }
    }

    //-----------

    /**
     * @param jobs
     */
    public void submitJobs(List<Runnable> jobs) {
        for (Runnable job : jobs) {
            execute(job);
        }
    }

    public void submitJob(Object job) {

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
     */
    public void submitJob(Runnable job) {
        if (this.getMaximumPoolSize() > 1) {
            LOGGER.log(Level.DEBUG, "Work-pool core=%s curr=%s max=%s  Task active=%s completed=%s  Queue size=%s capacity=%s Thread active=%s max=%s", 
                    this.getCorePoolSize(), this.getPoolSize(), this.getMaximumPoolSize(),
                    this.getActiveCount(), this.getCompletedTaskCount(), 
                    this.getJobQueueSize(), this.getJobQueueCapacity(),
                    this.getNumActiveThreads(), this.getNumMaxThreads());
        }
        execute(job);
    }

    public void shutdown() {
        ((RuleServiceProviderImpl) rsp).registerExecutableResource(this);

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