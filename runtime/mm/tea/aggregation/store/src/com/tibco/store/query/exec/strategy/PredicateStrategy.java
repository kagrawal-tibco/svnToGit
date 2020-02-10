package com.tibco.store.query.exec.strategy;

import com.tibco.store.query.model.ResultStream;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 12/12/13
 * Time: 10:27 AM
 * To change this template use File | Settings | File Templates.
 */
public interface PredicateStrategy {

    /**
     *
     * @param inputResultStream
     * @param <R>
     * @return
     */
    public <R extends ResultStream> R filter(R inputResultStream);
}
