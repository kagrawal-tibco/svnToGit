package com.tibco.cep.runtime.session;

import java.util.AbstractQueue;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import com.tibco.cep.runtime.util.BatchedOneWayBlockingQueue;
import com.tibco.cep.runtime.util.SuspendAwareBlockingQueue;

/*
* Author: Ashwin Jayaprakash Date: May 12, 2009 Time: 5:37:59 PM
*/
public class JobGroupAwareBlockingQueue extends AbstractQueue<Runnable> implements
        BlockingQueue<Runnable>, SuspendAwareBlockingQueue<Runnable> {
    protected BatchedOneWayBlockingQueue<Runnable> blockingQueueWrapper;

    protected SuspendAwareBlockingQueue<Runnable> internalRawQ;

    protected JobGroupManager groupManager;

    public JobGroupAwareBlockingQueue(SuspendAwareBlockingQueue<Runnable> queue, JobGroupManager groupManager) {
        this.blockingQueueWrapper = new BatchedOneWayBlockingQueue<Runnable>(queue, groupManager.getMaxJobsInaGroup());

        this.internalRawQ = queue;

        this.groupManager = groupManager;
    }

    public BatchedOneWayBlockingQueue<Runnable> getBlockingQueueWrapper() {
        return blockingQueueWrapper;
    }

    public JobGroupManager getGroupManager() {
        return groupManager;
    }

    //-------------

    public SuspendAwareBlockingQueue<Runnable> getInternalRawQ() {
        return internalRawQ;
    }

    @Override
    public void setSuspendableResource(SuspendableResource suspendableResource) {
        internalRawQ.setSuspendableResource(suspendableResource);
    }

    @Override
    public SuspendableResource getSuspendableResource() {
        return internalRawQ.getSuspendableResource();
    }


    //-------------

    public Runnable poll() {
        Collection<Runnable> collection = blockingQueueWrapper.poll();
        if (collection == null) {
            return null;
        }

        return new WrapperJob(groupManager, collection);
    }

    public Runnable peek() {
        Collection<Runnable> collection = blockingQueueWrapper.peek();
        if (collection == null) {
            return null;
        }

        return new WrapperJob(groupManager, collection);
    }

    public Runnable poll(long timeout, TimeUnit unit) throws InterruptedException {
        Collection<Runnable> collection = blockingQueueWrapper.poll(timeout, unit);
        if (collection == null) {
            return null;
        }

        return new WrapperJob(groupManager, collection);
    }

    public Runnable take() throws InterruptedException {
        Collection<Runnable> collection = blockingQueueWrapper.take();
        if (collection == null) {
            return null;
        }

        return new WrapperJob(groupManager, collection);
    }

    //--------------

    public Iterator<Runnable> iterator() {
        return internalRawQ.iterator();
    }

    public int size() {
        return blockingQueueWrapper.size();
    }

    public boolean offer(Runnable o) {
        return internalRawQ.offer(o);
    }

    @Override
    public boolean offerAlways(Runnable runnable, long timeout, TimeUnit unit) throws InterruptedException {
        return internalRawQ.offerAlways(runnable, timeout, unit);
    }

    public boolean offer(Runnable o, long timeout, TimeUnit unit) throws InterruptedException {
        return internalRawQ.offer(o, timeout, unit);
    }

    public void put(Runnable o) throws InterruptedException {
        internalRawQ.put(o);
    }

    public int remainingCapacity() {
        return blockingQueueWrapper.remainingCapacity();
    }

    public int drainTo(Collection<? super Runnable> c) {
        return internalRawQ.drainTo(c);
    }

    public int drainTo(Collection<? super Runnable> c, int maxElements) {
        return internalRawQ.drainTo(c);
    }

    //--------------

    protected static class WrapperJob implements Runnable {
        protected JobGroupManager groupManager;

        protected Collection<Runnable> subTasks;

        public WrapperJob(JobGroupManager groupManager, Collection<Runnable> subTasks) {
            this.groupManager = groupManager;
            this.subTasks = subTasks;
        }

        public void run() {
            try {
				groupManager.execute(subTasks);
			} catch (InterruptedException ignore) {
			}
        }
    }
}
