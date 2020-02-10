/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tibco.cep.runtime.util.scheduler.internal.impl;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.tibco.cep.runtime.util.scheduler.Id;
import com.tibco.cep.runtime.util.scheduler.Job;
import com.tibco.cep.runtime.util.scheduler.internal.JobRegistry;
import com.tibco.cep.runtime.util.scheduler.internal.JobRegistryException;

/**
 *
 * @param <K>
 * @param <V> 
 * Author: Karthikeyan Subramanian / Date: Jan 27, 2010 / Time: 12:20:25 PM
 */
public class JobRegistryImpl<K extends Job, V extends Id> implements JobRegistry<K, V> {
    private static final long serialVersionUID = 1L;
    private final ConcurrentMap<K, V> keyToValue;
    private final ConcurrentMap<V, K> valueToKey;

    public JobRegistryImpl() {
        this.keyToValue = new ConcurrentHashMap<K, V>();
        this.valueToKey = new ConcurrentHashMap<V, K>();
    }

    public K getKey(V value) {
        return valueToKey.get(value);
    }

    public V getValue(K key) {
        return keyToValue.get(key);
    }

    public V addEntry(K key, V value) throws JobRegistryException {
        V val = keyToValue.putIfAbsent(key, value);
        if(val == null) {
            K k = valueToKey.putIfAbsent(value, key);
            if(k != null) {
                // Key is mapped to another value. Throw exception
                throw new JobRegistryException("Value " + value +
                        "is mapped to another Key " + k);
            }
            return value;
        } 
        return val;
    }

    public V removeEntry(K key) throws JobRegistryException {
        V value = keyToValue.remove(key);
        if(value != null) {
            if(valueToKey.remove(value, key) == false) {
                throw new JobRegistryException("Value " + value +
                        " is used by another key " + key);
            }
        }
        return value;
    }

    public K removeEntry(V value) throws JobRegistryException {
        K key = valueToKey.remove(value);
        if(key != null) {
            if(keyToValue.remove(key, value) == false) {
                throw new JobRegistryException("Key " + key +
                        " is used by another value " + value);
            }
        }
        return key;
    }

    public Set<K> getKeys() {
        return keyToValue.keySet();
    }

    public Set<V> getValues() {
        return valueToKey.keySet();
    }
}
