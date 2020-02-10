package com.tibco.cep.query.stream.query.continuous;

import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.tibco.cep.query.stream.context.Context;
import com.tibco.cep.query.stream.core.ControllableResource;
import com.tibco.cep.query.stream.core.Sink;
import com.tibco.cep.query.stream.core.Source;
import com.tibco.cep.query.stream.monitor.ResourceId;

/*
* Author: Karthikeyan Subramanian / Date: Jan 29, 2010 / Time: 12:06:41 PM
*/

public interface ContinuousQuery<I> extends ControllableResource {

    // Query property methods.

    ResourceId getResourceId();

    String getRegionName();

    String getName();

    Sink getSink();

    Source[] getSources();

    Context getContext();

    ConcurrentLinkedQueue<I> getQueuedInput();

    // Query inquiry methods.

    boolean isPaused();

    boolean hasStopped();

    boolean isSynchronous();

    // query component methods.

    void init(Map<String, Object> externalData) throws Exception;

    void discard();

    void pause();

    void resume();

    void ping();

    // Query processing and input methods.

    /**
     * queue the input
     *
     * @param input
     *
     * @throws Exception
     */
    void enqueueInput(I input) throws Exception;

    void performSyncWork();

    long calcEstimatedFinishTime();

    /**
     * Lock related methods.
     */
    void acquireQueryLock();

    void relinquishQueryLock();

    public static enum CycleType {

        SCHEDULED, TRIGGERED
    }
}
