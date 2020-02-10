/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tibco.cep.runtime.util.scheduler.internal;

import java.util.Set;

import com.tibco.cep.runtime.util.scheduler.Id;
import com.tibco.cep.runtime.util.scheduler.Job;

/**
 *
 * @param <K>
 * @param <V> 
 * Author: Karthikeyan Subramanian / Date: Jan 27, 2010 / Time: 12:20:25 PM
 */
public interface JobRegistry<K extends Job, V extends Id> {

    V addEntry(K key, V value) throws JobRegistryException;

    K getKey(V value);

    V getValue(K key);

    V removeEntry(K key) throws JobRegistryException;

    K removeEntry(V value) throws JobRegistryException;

    Set<K> getKeys();

    Set<V> getValues();
}
