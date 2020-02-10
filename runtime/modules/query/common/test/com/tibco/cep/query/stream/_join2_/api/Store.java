package com.tibco.cep.query.stream._join2_.api;

/*
* Author: Ashwin Jayaprakash Date: May 28, 2009 Time: 7:44:53 PM
*/
public interface Store<K, V> extends View<V> {
    void put(K k, V v);

    void remove(K k);

    void removeAll();
}
