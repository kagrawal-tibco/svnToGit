package com.tibco.store.query.model;

import com.tibco.store.persistence.model.MemoryTuple;

import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 27/11/13
 * Time: 11:42 AM
 *
 */
public interface ResultStream {

    /**
     *
     * @param tuples
     * @param <T>
     */
    public <T extends MemoryTuple> void addMemoryTuples(T... tuples);

    /**
     *
     * @param tuples
     * @param <T>
     */
    public <T extends MemoryTuple> void addMemoryTuples(Collection<T> tuples);

    /**
     *
     * @return
     */
    public <T extends MemoryTuple> Collection<T> getTuples();
}
