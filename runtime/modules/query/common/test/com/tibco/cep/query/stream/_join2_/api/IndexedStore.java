package com.tibco.cep.query.stream._join2_.api;

/*
* Author: Ashwin Jayaprakash Date: May 28, 2009 Time: 9:57:03 PM
*/
public interface IndexedStore<C extends Comparable, K, V> extends IndexedView<C, V>, Store<K, V> {
    void record(K primaryKey, C secondaryKey, V value);

    void erase(K primaryKey, C secondaryKey);

    void eraseAll();
}
