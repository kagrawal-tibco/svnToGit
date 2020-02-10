package com.tibco.rta.queues.eviction;


import com.tibco.rta.queues.BatchJob;
import com.tibco.rta.queues.BatchedBlockingQueue;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 11/3/13
 * Time: 10:31 AM
 * To change this template use File | Settings | File Templates.
 */
public class OldestEntryEvictionPolicy implements EvictionPolicy {

    @Override
    public BatchJob selectElement(BatchedBlockingQueue batchedBlockingQueue) {
        //Remove first entry
        return batchedBlockingQueue.peek();
    }
}
