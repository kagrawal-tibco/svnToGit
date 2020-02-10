package com.tibco.cep.impl.service;

import com.tibco.cep.common.exception.RecoveryException;
import com.tibco.cep.common.resource.ResourceProvider;
import com.tibco.cep.service.AsyncScheduler;

import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;
import java.util.concurrent.TimeUnit;

/*
* Author: Ashwin Jayaprakash Date: Jul 20, 2009 Time: 1:50:57 PM
*/

/**
 * The return values like {@link ScheduledFuture} are not recoverable. The scheduled jobs are not
 * serialized.
 */
public class DefaultAsyncScheduler extends DefaultAsyncExecutor implements AsyncScheduler {
    protected transient ScheduledThreadPoolExecutor scheduledTPE;

    public DefaultAsyncScheduler() {
    }

    //---------------

    @Override
    protected ScheduledThreadPoolExecutor createThreadPoolExecutor() {
        scheduledTPE = new ScheduledThreadPoolExecutor(DEFAULT_CORE_POOL_SIZE, threadFactory,
                new AbortPolicy());

        //ScheduledTPE does not take core-pool-size in the ctor.
        scheduledTPE.setCorePoolSize(Math.max(DEFAULT_CORE_POOL_SIZE, maxThreads));
        //this is a no-op according to find bugs
        scheduledTPE.setMaximumPoolSize(Math.max(DEFAULT_CORE_POOL_SIZE, maxThreads));

        return scheduledTPE;
    }

    @Override
    public DefaultAsyncScheduler recover(ResourceProvider resourceProvider, Object... params)
            throws RecoveryException {
        super.recover(resourceProvider, params);

        return this;
    }

    //---------------

    public ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit) {
        return scheduledTPE.schedule(command, delay, unit);
    }

    public <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit) {
        return scheduledTPE.schedule(callable, delay, unit);
    }

    public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period,
                                                  TimeUnit unit) {
        return scheduledTPE.scheduleAtFixedRate(command, initialDelay, period, unit);
    }

    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay,
                                                     long delay, TimeUnit unit) {
        return scheduledTPE.scheduleWithFixedDelay(command, initialDelay, delay, unit);
    }
}
