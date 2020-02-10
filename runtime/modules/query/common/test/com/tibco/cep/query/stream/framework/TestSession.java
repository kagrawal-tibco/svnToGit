package com.tibco.cep.query.stream.framework;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.tibco.cep.query.stream.tuple.Tuple;

/*
 * Author: Ashwin Jayaprakash Date: Dec 20, 2007 Time: 11:01:35 AM
 */

public class TestSession {
    protected final ConcurrentMap<Object, Object> cacheData;

    protected final ConcurrentMap<Number, Tuple> tuples;

    public TestSession() {
        this.cacheData = new ConcurrentHashMap<Object, Object>();
        this.tuples = new ConcurrentHashMap<Number, Tuple>();
    }

    public ConcurrentMap<Object, Object> getCacheData() {
        return cacheData;
    }

    public ConcurrentMap<Number, Tuple> getTuples() {
        return tuples;
    }

    public void discard() {
        cacheData.clear();
        tuples.clear();
    }
}
