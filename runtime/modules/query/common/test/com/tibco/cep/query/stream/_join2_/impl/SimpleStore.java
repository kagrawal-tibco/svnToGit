package com.tibco.cep.query.stream._join2_.impl;

import com.tibco.cep.query.stream._join2_.api.Store;
import com.tibco.cep.query.stream._join2_.util.SimpleCustomIterable;
import com.tibco.cep.query.stream.util.CustomIterable;

import java.util.HashMap;
import java.util.Map;

/*
* Author: Ashwin Jayaprakash Date: May 28, 2009 Time: 9:34:46 PM
*/
public class SimpleStore<K, V> implements Store<K, V> {
    protected Map<K, V> delegate;

    public SimpleStore() {
        this(new HashMap<K, V>());
    }

    protected SimpleStore(Map<K, V> delegate) {
        this.delegate = delegate;
    }

    public void put(K k, V v) {
        delegate.put(k, v);
    }

    public void remove(K k) {
        delegate.remove(k);
    }

    public void removeAll() {
        delegate.clear();
    }

    public CustomIterable<V> getAll() {
        return new SimpleCustomIterable<V>(delegate.values());
    }
}
