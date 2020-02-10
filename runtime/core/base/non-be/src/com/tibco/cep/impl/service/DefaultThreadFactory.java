package com.tibco.cep.impl.service;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import com.tibco.cep.common.exception.RecoveryException;
import com.tibco.cep.common.resource.Recoverable;
import com.tibco.cep.common.resource.ResourceProvider;

/*
* Author: Ashwin Jayaprakash Date: Jul 20, 2009 Time: 2:20:59 PM
*/

/**
 * Creates only daemon threads.
 */
public class DefaultThreadFactory implements ThreadFactory, Recoverable<DefaultThreadFactory> {
    protected String poolName;

    protected AtomicInteger counter;

    protected int priority;

    public DefaultThreadFactory(String poolName, int priority) {
        this.poolName = poolName;
        this.counter = new AtomicInteger(0);
        this.priority = Thread.NORM_PRIORITY;
    }

    /**
     * @param poolName
     * @see Thread#NORM_PRIORITY
     */
    public DefaultThreadFactory(String poolName) {
        this(poolName, Thread.NORM_PRIORITY);
    }

    public String getPoolName() {
        return poolName;
    }

    //--------------

    public Thread newThread(Runnable r) {
        String name = poolName + "-" + counter.incrementAndGet();

        Thread t = new Thread(r, name);
        t.setDaemon(true);
        t.setPriority(priority);

        return t;
    }

    //--------------

    public DefaultThreadFactory recover(ResourceProvider resourceProvider, Object... params)
            throws RecoveryException {
        counter.set(0);

        return this;
    }
}
