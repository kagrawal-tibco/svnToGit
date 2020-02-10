package com.tibco.cep.query.stream.sort;

import java.util.Comparator;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

/*
 * Author: Ashwin Jayaprakash Date: Oct 5, 2007 Time: 2:29:59 PM
 */

/**
 * Hierarchy of maps supported by {@link #getParent()} and {@link #getKeyInParent()}.
 */
public abstract class GenericHierarchicalSortedMap<K, V> {
    protected TreeMap<K, V> delegateMap;

    protected GenericHierarchicalSortedMap parent;

    protected Object keyInParent;

    public GenericHierarchicalSortedMap(Comparator<? super K> c) {
        this.delegateMap = new TreeMap<K, V>(c);
    }

    public GenericHierarchicalSortedMap getParent() {
        return parent;
    }

    public void setParent(GenericHierarchicalSortedMap parent) {
        this.parent = parent;
    }

    public Object getKeyInParent() {
        return keyInParent;
    }

    public void setKeyInParent(Object keyInParent) {
        this.keyInParent = keyInParent;
    }

    public Set<K> keySet() {
        return delegateMap.keySet();
    }

    public K firstKey() {
        return delegateMap.firstKey();
    }

    public K lastKey() {
        return delegateMap.lastKey();
    }

    public SortedMap<K, V> headMap(K key) {
        return delegateMap.headMap(key);
    }

    public SortedMap<K, V> tailMap(K key) {
        return delegateMap.tailMap(key);
    }

    public void put(K key, V value) {
        delegateMap.put(key, value);
    }

    public V get(K key) {
        return delegateMap.get(key);
    }

    public void remove(K key) {
        delegateMap.remove(key);
    }

    public int size() {
        return delegateMap.size();
    }

    /**
     * Clears the internal map and also clears the values recursively if they happen to be {@link
     * com.tibco.cep.query.stream.sort.GenericHierarchicalSortedMap} by invoking {@link #discard()}
     * .
     */
    public void discard() {
        for (K k : delegateMap.keySet()) {
            V v = delegateMap.get(k);

            if (v instanceof GenericHierarchicalSortedMap) {
                ((GenericHierarchicalSortedMap) v).discard();
            }
        }
        delegateMap.clear();
        delegateMap = null;

        parent = null;
        keyInParent = null;
    }
}