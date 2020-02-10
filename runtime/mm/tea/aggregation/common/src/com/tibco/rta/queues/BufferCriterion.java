package com.tibco.rta.queues;

/**
 * Define buffer criterion to perform batching for client side ops.
 */
public interface BufferCriterion {

    public <A extends AbstractBatchAwareQueue> boolean isMet(A workQueue);

    public int getDrainCount();
}
