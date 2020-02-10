package com.tibco.cep.query.stream._join2_.api;

/*
* Author: Ashwin Jayaprakash Date: Jun 3, 2009 Time: 5:52:01 PM
*/
public interface SourceChangeListener<K, V> {
    void batchStart();

    void onRemove(K key, V value);

    void batchEnd();
}
