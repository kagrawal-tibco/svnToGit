package com.tibco.rta.queues;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 30/5/13
 * Time: 4:52 PM
 * MBean to view stats regarding the batch queue.
 */
public interface BatchedBlockingQueueMBean extends BatchAwareQueueMBean {

    public int getTotalEvictions();

    public int getEvictionFrequency();
}
