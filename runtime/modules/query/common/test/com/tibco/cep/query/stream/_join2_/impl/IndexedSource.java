package com.tibco.cep.query.stream._join2_.impl;

import com.tibco.cep.query.stream._join2_.api.Evaluator;
import com.tibco.cep.query.stream._join2_.api.IndexedStore;
import com.tibco.cep.query.stream._join2_.api.IndexedView;
import com.tibco.cep.query.stream._join2_.api.Store;

/*
* Author: Ashwin Jayaprakash Date: May 28, 2009 Time: 10:38:23 PM
*/
public class IndexedSource<C extends Comparable, K, V, W extends IndexedView<C, V>>
        extends AbstractSource<K, V, W> {
    protected IndexedStore<C, K, V> indexedFilterPassedStore;

    protected Evaluator<V, C> secondaryKeyExtractor;

    protected W currentIndexedView;

    @Override
    public IndexedSource<C, K, V, W> setFilter(Evaluator<V, Boolean> filter) {
        super.setFilter(filter);

        return this;
    }

    /**
     * Use {@link #setIndexedFilterPassedStore(IndexedStore)}.
     *
     * @param filterPassedStore
     * @return
     */
    @Override
    public IndexedSource<C, K, V, W> setFilterPassedStore(Store<K, V> filterPassedStore) {
        setIndexedFilterPassedStore((IndexedStore<C, K, V>) filterPassedStore);

        return this;
    }

    @Override
    public IndexedSource<C, K, V, W> setFilterFailedStore(Store<K, V> filterFailedStore) {
        super.setFilterFailedStore(filterFailedStore);

        return this;
    }

    @Override
    public IndexedSource<C, K, V, W> setPrimaryKeyExtractor(
            Evaluator<V, K> primaryKeyExtractor) {
        super.setPrimaryKeyExtractor(primaryKeyExtractor);

        return this;
    }

    public IndexedStore<C, K, V> getIndexedFilterPassedStore() {
        return indexedFilterPassedStore;
    }

    public IndexedSource<C, K, V, W> setIndexedFilterPassedStore(
            IndexedStore<C, K, V> indexedFilterPassedStore) {
        super.setFilterPassedStore(indexedFilterPassedStore);

        this.indexedFilterPassedStore = indexedFilterPassedStore;

        return this;
    }

    public Evaluator<V, C> getSecondaryKeyExtractor() {
        return secondaryKeyExtractor;
    }

    public IndexedSource<C, K, V, W> setSecondaryKeyExtractor(
            Evaluator<V, C> secondaryKeyExtractor) {
        this.secondaryKeyExtractor = secondaryKeyExtractor;

        return this;
    }

    //--------------

    public void batchStart() {
    }

    public void add(K key, V value) {
        C secondaryKey = secondaryKeyExtractor.evaluate(value);

        add(key, secondaryKey, value);
    }

    @Override
    public void add(V value) {
        K primaryKey = primaryKeyExtractor.evaluate(value);
        C secondaryKey = secondaryKeyExtractor.evaluate(value);

        add(primaryKey, secondaryKey, value);
    }

    protected void add(K primaryKey, C secondaryKey, V value) {
        if (filter == null) {
            indexedFilterPassedStore.put(primaryKey, value);
            indexedFilterPassedStore.record(primaryKey, secondaryKey, value);

            return;
        }

        //--------------

        Boolean b = filter.evaluate(value);
        if (b) {
            indexedFilterPassedStore.put(primaryKey, value);
            indexedFilterPassedStore.record(primaryKey, secondaryKey, value);
        }
        else {
            filterFailedStore.put(primaryKey, value);
        }
    }

    public void remove(K key, V value) {
        C secondaryKey = secondaryKeyExtractor.evaluate(value);

        remove(key, secondaryKey, value);
    }

    @Override
    public void remove(V value) {
        K primaryKey = primaryKeyExtractor.evaluate(value);
        C secondaryKey = secondaryKeyExtractor.evaluate(value);

        remove(primaryKey, secondaryKey, value);
    }

    protected void remove(K primaryKey, C secondaryKey, V value) {
        indexedFilterPassedStore.erase(primaryKey, secondaryKey);
        indexedFilterPassedStore.remove(primaryKey);

        //Try in both since we do not know where exactly it is.
        if (filter != null) {
            filterFailedStore.remove(primaryKey);
        }
    }

    @SuppressWarnings({"unchecked"})
    public W batchEnd() {
        return (W) indexedFilterPassedStore;
    }
}
