package com.tibco.rta.queues;

/**
 * Created by aathalye
 * Date : 18/12/14
 * Time : 1:26 PM
 */
public class BatchAwareQueueFactory {

    public static AbstractBatchAwareQueue getBatchAwareQueue(String queueName,
                                                             int queueDepth,
                                                             int batchSize,
                                                             long batchExpiry) {
        return (batchSize == 1) ? new ConcurrentSingleBatchQueue(queueName, queueDepth) :
                new BatchAwareRingBuffer(queueName, queueDepth, new BatchSizeBufferCriterion(batchSize, batchExpiry));
    }
}
