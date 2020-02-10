package com.tibco.cep.runtime.session.locks;

import java.util.Collection;

/*
* Author: Ashwin Jayaprakash Date: Jul 22, 2008 Time: 7:02:49 PM
*/
public interface LockKeeper<K, V> {
    void init();

    /**
     * Performance optimization. Marks the beginning of a batch.
     */
    void batchStart();

    /**
     * @param key
     * @param timeoutNanos <b>0 or less</b> to indicate wait and try <b>forever</b>.
     * @return
     */
    boolean lock(K key, long timeoutNanos);

    /**
     * @param lockLevel
     * @param lockData
     * @param affectedIds Can be <code>null</code>
     */
    void put(LockManager.LockLevel lockLevel, LockManager.LockData<K, V> lockData,
             Collection<Long> affectedIds);

    /**
     * @param lockLevel
     * @param key
     * @return
     */
    V remove(LockManager.LockLevel lockLevel, K key);

    void unlock(K key);

    /**
     * Performance optimization. Marks the end of a batch.
     */
    void batchEnd();

    void discard();
}
