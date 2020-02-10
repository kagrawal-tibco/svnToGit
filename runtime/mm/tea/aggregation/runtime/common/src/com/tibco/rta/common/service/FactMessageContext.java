package com.tibco.rta.common.service;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 3/4/13
 * Time: 4:55 PM
 * Message context.
 */
public interface FactMessageContext extends MessageContext {


    /**
     * Batch size for number of facts batched.
     * @param factBatchSize
     */
    void setBatchSize(int factBatchSize);
    
    String getId();

    public long getCreatedTime();
}
