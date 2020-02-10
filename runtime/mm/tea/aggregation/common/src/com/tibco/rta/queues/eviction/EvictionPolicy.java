package com.tibco.rta.queues.eviction;


import com.tibco.rta.queues.BatchJob;
import com.tibco.rta.queues.BatchedBlockingQueue;

/**
 * Eviction policy when fact queue becomes full 
 * @see {@link com.tibco.rta.queues.BatchedBlockingQueue}
 * <p>
 * Date: 9/3/13
 * 
 */
public interface EvictionPolicy {
 
    /**
     *
     * @param batchedBlockingQueue
     * @return
     */
    BatchJob selectElement(BatchedBlockingQueue batchedBlockingQueue);
}
