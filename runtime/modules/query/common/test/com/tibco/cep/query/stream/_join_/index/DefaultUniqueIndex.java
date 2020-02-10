package com.tibco.cep.query.stream._join_.index;

import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.util.SingleElementCollection;
import com.tibco.cep.query.stream.util.WrappedReusableIterator;

import java.util.Collection;
import java.util.HashMap;

/*
* Author: Ashwin Jayaprakash Date: Aug 4, 2008 Time: 5:46:57 PM
*/
public class DefaultUniqueIndex<K extends Comparable, V, N> implements Index<K, V, N> {
    protected HashMap<K, V> delegate;

    protected WrappedReusableIterator<V> wrappedReusableIterator;

    public DefaultUniqueIndex() {
        this.delegate = new HashMap<K, V>();
        this.wrappedReusableIterator =
                new WrappedReusableIterator<V>(new SingleElementCollection<V>(null));
    }

    public boolean supportsOperator(Operator operator) {
        switch (operator) {
            case ALL:
            case EQUALS:
                return true;

            default:
                return false;
        }
    }

    //------------

    public void record(K key, V value, N idOfValue) {
        delegate.put(key, value);
    }

    public void erase(K key, N idOfValue) {
        delegate.remove(key);

        //Do not hold on to some long deleted entry.
        wrappedReusableIterator.clear();
    }

    public Object fetch(GlobalContext globalContext, QueryContext queryContext, Operator operator,
                        K key) {
        switch (operator) {
            case ALL:
                Collection<V> collection = delegate.values();
                wrappedReusableIterator.reset(collection);

                return wrappedReusableIterator;

            case EQUALS:
                return delegate.get(key);

            default:
                throw new UnsupportedOperationException(operator.name());
        }
    }
}
