package com.tibco.cep.query.stream._join2_.impl;

import com.tibco.cep.query.stream._join2_.api.Evaluator;
import com.tibco.cep.query.stream._join2_.api.Store;
import com.tibco.cep.query.stream._join2_.api.View;

/*
* Author: Ashwin Jayaprakash Date: May 27, 2009 Time: 10:19:29 PM
*/
public class SimpleSource<K, V, W extends View<V>> extends AbstractSource<K, V, W> {
    @Override
    public SimpleSource<K, V, W> setFilter(Evaluator<V, Boolean> filter) {
        super.setFilter(filter);

        return this;
    }

    @Override
    public SimpleSource<K, V, W> setFilterPassedStore(Store<K, V> filterPassedStore) {
        super.setFilterPassedStore(filterPassedStore);

        return this;
    }

    @Override
    public SimpleSource<K, V, W> setFilterFailedStore(Store<K, V> filterFailedStore) {
        super.setFilterFailedStore(filterFailedStore);

        return this;
    }

    @Override
    public SimpleSource<K, V, W> setPrimaryKeyExtractor(Evaluator<V, K> primaryKeyExtractor) {
        super.setPrimaryKeyExtractor(primaryKeyExtractor);

        return this;
    }

    //----------------

    public void batchStart() {

    }

    public void add(K key, V value) {
        if (filter == null) {
            filterPassedStore.put(key, value);

            return;
        }

        //--------------

        Boolean b = filter.evaluate(value);
        if (b) {
            filterPassedStore.put(key, value);
        }
        else {
            filterFailedStore.put(key, value);
        }
    }

    public void remove(K key, V value) {
        filterPassedStore.remove(key);

        //Try in both since we do not know where exactly it is.
        if (filter != null) {
            filterFailedStore.remove(key);
        }
    }

    @SuppressWarnings({"unchecked"})
    public W batchEnd() {
        return (W) filterPassedStore;
    }
}
