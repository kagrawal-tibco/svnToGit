package com.tibco.cep.query.stream._join2_.impl;

import com.tibco.cep.query.stream._join2_.api.Evaluator;
import com.tibco.cep.query.stream._join2_.api.Source;
import com.tibco.cep.query.stream._join2_.api.Store;
import com.tibco.cep.query.stream._join2_.api.View;

/*
* Author: Ashwin Jayaprakash Date: May 27, 2009 Time: 10:19:29 PM
*/
public abstract class AbstractSource<K, V, W extends View<V>> implements Source<K, V, W> {
    protected Evaluator<V, Boolean> filter;

    protected Evaluator<V, K> primaryKeyExtractor;

    protected Store<K, V> filterPassedStore;

    protected Store<K, V> filterFailedStore;

    public Evaluator<V, Boolean> getFilter() {
        return filter;
    }

    /**
     * @param filter Can be <code>null</code>.
     * @return
     */
    public AbstractSource<K, V, W> setFilter(Evaluator<V, Boolean> filter) {
        this.filter = filter;

        return this;
    }

    public Store<K, V> getFilterPassedStore() {
        return filterPassedStore;
    }

    public AbstractSource<K, V, W> setFilterPassedStore(Store<K, V> filterPassedStore) {
        this.filterPassedStore = filterPassedStore;

        return this;
    }

    public Store<K, V> getFilterFailedStore() {
        return filterFailedStore;
    }

    /**
     * @param filterFailedStore <code>null</code> if there is no {@link #getFilter()}.
     * @return
     */
    public AbstractSource<K, V, W> setFilterFailedStore(Store<K, V> filterFailedStore) {
        this.filterFailedStore = filterFailedStore;

        return this;
    }

    public Evaluator<V, K> getPrimaryKeyExtractor() {
        return primaryKeyExtractor;
    }

    public AbstractSource<K, V, W> setPrimaryKeyExtractor(Evaluator<V, K> primaryKeyExtractor) {
        this.primaryKeyExtractor = primaryKeyExtractor;

        return this;
    }

    //----------------

    public void add(V value) {
        K key = primaryKeyExtractor.evaluate(value);

        add(key, value);
    }

    public void remove(V value) {
        K key = primaryKeyExtractor.evaluate(value);

        remove(key, value);
    }
}