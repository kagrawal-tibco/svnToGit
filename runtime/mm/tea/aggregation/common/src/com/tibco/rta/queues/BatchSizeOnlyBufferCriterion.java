package com.tibco.rta.queues;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 6/2/13
 * Time: 12:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class BatchSizeOnlyBufferCriterion implements BufferCriterion {

    private int batchSize;

    protected int divisionFactor;

    public BatchSizeOnlyBufferCriterion(int batchSize) {
        this(batchSize, 1);
    }

    /**
     *
     * Division factor added to allow more uniform work distribution among taker threads so that
     * an entire batch is not taken by one thread starving others till next batch criterion is met.
     * @param divisionFactor - Typically division fact will be equal to number of contending readers
     *
     */
    public BatchSizeOnlyBufferCriterion(int batchSize, int divisionFactor) {
        this.batchSize = batchSize;
        this.divisionFactor = divisionFactor;
    }


    @Override
    public <A extends AbstractBatchAwareQueue> boolean isMet(A queue) {
        return queue.size()  >= batchSize / divisionFactor;
    }


    @Override
    public int getDrainCount() {
        return batchSize;
    }
}
