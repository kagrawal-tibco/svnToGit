package com.tibco.rta.queues;

/**
 * Created by aathalye
 * Date : 31/12/14
 * Time : 11:42 AM
 */
public interface BatchAwareRingBufferMBean extends BatchAwareQueueMBean {

    /**
     * Return a count of all evictions done so far.
     *
     * @return a count of evictions.
     */
    public int getCurrentPutIndex();

    /**
     * Return the frequency in milliseconds for eviction if async eviction is enabled.
     *
     * @return eviction frequency.
     */
    public int getCurrentTakeIndex();
}
