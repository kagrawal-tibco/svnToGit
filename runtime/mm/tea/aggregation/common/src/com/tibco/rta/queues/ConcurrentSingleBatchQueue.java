package com.tibco.rta.queues;

import com.tibco.rta.annotations.ThreadSafe;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by aathalye
 * Date : 18/12/14
 * Time : 12:19 PM
 *
 * Single batch take CAS based queue implementation.
 */
@ThreadSafe
public class ConcurrentSingleBatchQueue extends AbstractBatchAwareQueue implements ConcurrentSingleBatchQueueMBean {

    private ConcurrentLinkedQueue<BatchJob> wrappedQueue;

    public ConcurrentSingleBatchQueue(String name, int highUnits) {
        super(name, highUnits);
        this.wrappedQueue = new ConcurrentLinkedQueue<BatchJob>();
    }

    @Override
    @ThreadSafe
    public boolean offer(BatchJob batchJob) throws QueueException {
        boolean offered = false;
        if (size() < highUnits) {
            offered = wrappedQueue.offer(batchJob);
            incOffer();
            //Notify
            notifyEavesDroppers(batchJob);
        }
        return offered;
    }

    @Override
    @ThreadSafe
    public Collection<BatchJob> take() throws QueueException {
        ArrayList<BatchJob> arrayList = new ArrayList<BatchJob>(1);
        arrayList.add(wrappedQueue.poll());
        //Simply add 1
        incTake(1);
        return arrayList;
    }

    @Override
    public BatchJob peek() {
        return wrappedQueue.peek();
    }

    public int size() {
        return wrappedQueue.size();
    }

    @Override
    public boolean isBlocking() {
        return false;
    }

    @Override
    public void clear() {
        wrappedQueue.clear();
    }
}
