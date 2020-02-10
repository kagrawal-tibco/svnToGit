/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.om.api;

import java.util.Collection;
import java.util.Map;

/*
* Author: Ashwin Jayaprakash Date: Apr 28, 2009 Time: 10:09:26 AM
*/
public interface ControlDao<K, V> extends Map<K, V>, FilterableMap, InvocationService {
    String getName();

    ControlDaoType getType();

    void start();

    boolean lock(K key, long timeoutMillis);

    boolean lockAll(long timeoutMillis);

    boolean unlock(K key);

    void unlockAll();

    void discard();
    
    Map getInternal();

    void registerListener(MapChangeListener<K, V> changeListener);

    //void registerListener(ChangeListener<K, V> changeListener, Filter filter);

    void unregisterListener(ChangeListener<K, V> changeListener);

    Collection<V> getAll(Collection<K> keys);

    void removeAll(Collection<K> ks);

    //-------------

    public interface ChangeListener<K, V> {
        void onPut(K key, V value);

        void onUpdate(K key, V oldValue, V newValue);

        void onRemove(K key, V oldValue);
    }

}
