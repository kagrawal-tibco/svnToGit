package com.tibco.cep.runtime.util;

import java.util.Collection;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/*
* Author: Ashwin Jayaprakash / Date: Oct 5, 2010 / Time: 3:17:49 PM
*/

public class SuspendAwareLBQImpl<R> extends LinkedBlockingQueue<R> implements SuspendAwareBlockingQueue<R> {
    private SuspendableResource suspendableResource;

    public SuspendAwareLBQImpl() {
    }

    public SuspendAwareLBQImpl(int capacity) {
        super(capacity);
    }

    public SuspendAwareLBQImpl(Collection<? extends R> c) {
        super(c);
    }

    /**
     * Set this before using the queue.
     *
     * @param suspendableResource
     */
    public void setSuspendableResource(SuspendableResource suspendableResource) {
        this.suspendableResource = suspendableResource;
    }

    public SuspendableResource getSuspendableResource() {
        return suspendableResource;
    }

    @Override
    public void put(R r) throws InterruptedException {
        suspendableResource.checkAndBlockIfSuspended();

        super.put(r);
    }

    @Override
    public boolean offer(R r, long timeout, TimeUnit unit) throws InterruptedException {
        suspendableResource.checkAndBlockIfSuspended();

        return super.offer(r, timeout, unit);
    }

    @Override
    public boolean offerAlways(R r, long timeout, TimeUnit unit) throws InterruptedException {
        return super.offer(r, timeout, unit);
    }

    @Override
    public boolean offer(R r) {
        suspendableResource.checkAndBlockIfSuspended();

        return super.offer(r);
    }

    @Override
    public boolean add(R r) {
        suspendableResource.checkAndBlockIfSuspended();

        return super.add(r);
    }

    @Override
    public boolean addAll(Collection<? extends R> c) {
        suspendableResource.checkAndBlockIfSuspended();

        return super.addAll(c);
    }
}