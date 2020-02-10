package com.tibco.cep.query.stream._join2_.api;

/*
* Author: Ashwin Jayaprakash Date: May 28, 2009 Time: 10:28:09 PM
*/
public interface Source<K, V, W extends View<V>> {
    Evaluator<V, K> getPrimaryKeyExtractor();

    void batchStart();

    void add(V value);

    void add(K key, V value);

    void remove(V value);

    void remove(K key, V value);

    W batchEnd();
}
