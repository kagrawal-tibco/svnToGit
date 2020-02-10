package com.tibco.cep.query.stream.io;

import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.tuple.TupleInfo;

/*
 * Author: Ashwin Jayaprakash Date: Dec 4, 2007 Time: 4:55:50 PM
 */

/**
 * Async listener.
 */
public abstract class QueryResultListener {
    protected final ResourceId resultProviderResourceId;

    protected final TupleInfo resultInfo;

    public QueryResultListener(ResourceId resultProviderResourceId, TupleInfo resultOutputInfo) {
        this.resultProviderResourceId = resultProviderResourceId;
        this.resultInfo = resultOutputInfo;
    }

    public ResourceId getResultProviderResourceId() {
        return resultProviderResourceId;
    }

    public TupleInfo getResultInfo() {
        return resultInfo;
    }

    /**
     * <p> Must return <b>immediately</b>! </p> <p> By the time the tuple reaches this listener, the
     * Tuple's ref-count is already incremented by 1. This listener must then decrement the count
     * when the tuple is no longer needed. </p>
     *
     * @param tuple
     */
    public abstract void onResult(Tuple tuple);
}
