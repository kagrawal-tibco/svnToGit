package com.tibco.rta.queues;

/**
 * Created by aathalye
 * Date : 18/12/14
 * Time : 2:40 PM
 */
public interface BatchAwareQueueMBean {

    /**
     * Return the name of this queue.
     *
     * @return unique name.
     */
    public String getQueueName();

    /**
     * Return the size of queue at the given instant.
     *
     * @return the current size
     */
    public int getCurrentSize();

    /**
     * Get configured queue depth beyond which evictions begin.
     *
     * @return the queue depth.
     */
    public int getQueueDepth();

    /**
     * Number of elements added to queue from time of creation.
     *
     * @return number of elements added.
     */
    public long getAddCount();

    /**
     * Number of elements drained from this queue from time of creation.
     *
     * @return number of elements drained.
     */
    public long getDrainedCount();

    /**
     * Current rate at which messages are flowing into the queue.
     *
     */
    public double getInflowRate();

    /**
     * Current rate at which messages are flowing out of the queue.
     *
     */
    public double getOutflowRate();

    /**
     * If the queue impl is blocking/CAS.
     *
     * @return a boolean indicating this.
     */
    public boolean isBlocking();
}
