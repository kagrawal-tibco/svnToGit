package com.tibco.store.query.model.impl;

import com.tibco.store.persistence.model.MemoryTuple;
import com.tibco.store.query.model.ResultStream;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 3/12/13
 * Time: 2:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class MutableResultStream implements ResultStream {

    /**
     * Mutable contents
     */
    private Collection<MemoryTuple> containedTuples;

    public MutableResultStream() {
        this.containedTuples = new ArrayList<MemoryTuple>();
    }


    @Override
    public <T extends MemoryTuple> void addMemoryTuples(T... tuples) {
        Collections.addAll(containedTuples, tuples);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends MemoryTuple> void addMemoryTuples(Collection<T> tuples) {
        this.containedTuples = (Collection<MemoryTuple>) tuples;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends MemoryTuple> Collection<T> getTuples() {
        return (Collection<T>) Collections.unmodifiableCollection(containedTuples);
    }
}
