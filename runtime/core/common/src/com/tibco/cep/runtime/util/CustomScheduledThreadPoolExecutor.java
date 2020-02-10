package com.tibco.cep.runtime.util;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;

import com.tibco.cep.runtime.service.basic.AsyncWorkerServiceWatcher;
import com.tibco.cep.runtime.session.RuleServiceProvider;

/*
* Author: Ashwin Jayaprakash Date: Mar 12, 2009 Time: 7:11:07 PM
*/
public class CustomScheduledThreadPoolExecutor extends ScheduledThreadPoolExecutor
        implements AsyncWorkerServiceWatcher.AsyncWorkerService {
    protected String threadPoolName;

    protected FQName name;

    public static CustomScheduledThreadPoolExecutor create(String threadPoolName,
                                                           int corePoolSize,
                                                           RuleServiceProvider rsp) {
        CustomDaemonThreadFactory threadFactory =
                new CustomDaemonThreadFactory(threadPoolName, rsp);

        return new CustomScheduledThreadPoolExecutor(threadPoolName, corePoolSize, threadFactory);
    }

    protected CustomScheduledThreadPoolExecutor(String threadPoolName, int corePoolSize,
                                                ThreadFactory threadFactory) {
        super(corePoolSize, threadFactory);

        this.threadPoolName = threadPoolName;
    }

    public String getThreadPoolName() {
        return threadPoolName;
    }

    public void setName(FQName name) {
        this.name = name;
    }

    //-----------

    public boolean isActive() {
        return !isShutdown();
    }

    public FQName getName() {
        return name;
    }

    public int getNumMaxThreads() {
        return getMaximumPoolSize();
    }

    public int getNumActiveThreads() {
        return getActiveCount();
    }

    public int getJobQueueCapacity() {
        BlockingQueue q = getQueue();

        int c = q.remainingCapacity();
        //Avoid overflow.
        if (c == Integer.MAX_VALUE) {
            return c;
        }

        return (q.size() + c);
    }

    public int getJobQueueSize() {
        return getQueue().size();
    }
}