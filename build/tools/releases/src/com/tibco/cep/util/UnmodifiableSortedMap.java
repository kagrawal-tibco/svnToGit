package com.tibco.cep.util;

import java.util.*;

/**
 * User: nprade
 * Date: 6/28/11
 * Time: 5:59 PM
 */
public class UnmodifiableSortedMap<K , V>
        implements SortedMap<K, V> {

    private final SortedMap<K, V> delegate;


    @SuppressWarnings({"UnusedDeclaration"})
    public UnmodifiableSortedMap(
            Map<K, V> map) {

        this.delegate = Collections.unmodifiableSortedMap(new TreeMap<K, V>(map));
    }


    @SuppressWarnings({"UnusedDeclaration"})
    public UnmodifiableSortedMap(
            SortedMap<K, V> map) {

        this.delegate = Collections.unmodifiableSortedMap(map);
    }


    @Override
    public void clear() {

        this.delegate.clear();
    }


    @Override
    public Comparator<? super K> comparator() {

        return this.delegate.comparator();
    }


    @Override
    public boolean containsKey(
            Object key) {

        return this.delegate.containsKey(key);
    }


    @Override
    public boolean containsValue(
            Object value) {

        return this.delegate.containsValue(value);
    }


    @Override
    public Set<Entry<K, V>> entrySet() {

        return this.delegate.entrySet();
    }


    @SuppressWarnings({"EqualsWhichDoesntCheckParameterClass"})
    @Override
    public boolean equals(
            Object o) {

        return this.delegate.equals(o);
    }


    @Override
    public int hashCode() {

        return this.delegate.hashCode();
    }


    @Override
    public K firstKey() {

        return this.delegate.firstKey();
    }


    @Override
    public V get(
            Object key) {

        return this.delegate.get(key);
    }


    @Override
    public SortedMap<K, V> headMap(
            K toKey) {

        return this.delegate.headMap(toKey);
    }


    @Override
    public boolean isEmpty() {
        return this.delegate.isEmpty();
    }


    @Override
    public Set<K> keySet() {

        return this.delegate.keySet();
    }


    @Override
    public K lastKey() {
        return this.delegate.lastKey();
    }


    @Override
    public V put(
            K key,
            V value) {

        return this.delegate.put(key, value);
    }

    @Override
    public void putAll(
            Map<? extends K, ? extends V> m) {

        this.delegate.putAll(m);
    }


    @Override
    public V remove(
            Object key) {

        return this.delegate.remove(key);
    }


    @Override
    public int size() {
        return this.delegate.size();
    }


    @Override
    public SortedMap<K, V> subMap(
            K fromKey,
            K toKey) {

        return this.delegate.subMap(fromKey, toKey);
    }


    @Override
    public SortedMap<K, V> tailMap(
            K fromKey) {

        return this.delegate.tailMap(fromKey);
    }


    @Override
    public Collection<V> values() {

        return this.delegate.values();
    }


}

