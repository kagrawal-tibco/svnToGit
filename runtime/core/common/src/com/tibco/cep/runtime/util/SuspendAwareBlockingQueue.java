package com.tibco.cep.runtime.util;

import java.util.Collection;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/*
* Author: Ashwin Jayaprakash Date: Mar 12, 2009 Time: 7:11:07 PM
*/

public interface SuspendAwareBlockingQueue<R> extends BlockingQueue<R> {
    /**
     * Set this before using the queue.
     *
     * @param suspendableResource
     */
    void setSuspendableResource(SuspendableResource suspendableResource);

    SuspendableResource getSuspendableResource();

    @Override
    void put(R r) throws InterruptedException;

    @Override
    boolean offer(R r, long timeout, TimeUnit unit) throws InterruptedException;

    /**
     * Offers regardless of the suspend status.
     *
     * @param r
     * @param timeout
     * @param unit
     * @return
     * @throws InterruptedException
     */
    boolean offerAlways(R r, long timeout, TimeUnit unit) throws InterruptedException;

    @Override
    boolean offer(R r);

    @Override
    boolean add(R r);

    @Override
    boolean addAll(Collection<? extends R> c);

    //------------------

    public static interface SuspendableResource {
        void checkAndBlockIfSuspended();
    }
}
