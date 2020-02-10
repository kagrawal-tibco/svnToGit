/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 *
 * Author: Suresh Subramani (suresh.subramani@tibco.com)
 * Date  : 30/6/2010
 */

package com.tibco.cep.runtime.service.cluster.util;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.AbstractCollection;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/*
* Author: Ashwin Jayaprakash Date: Jan 7, 2009 Time: 2:08:20 PM
*/
public class SingleValueLiteMap<K, V> implements Map<K, V>, Iterable<K>, Externalizable {
    private static final long serialVersionUID = 1L;

    private transient Entry<K, V> headEntry;

    public SingleValueLiteMap() {
    }

    //-----------

    /**
     * @param key Cannot be <code>null</code>. Has to be one of the keys provided in the
     *            constructor.
     * @return
     */
    public V get(Object key) {
        if (headEntry == null || (headEntry.key != key && headEntry.key.equals(key) == false)) {
            return null;
        }

        return headEntry.value;
    }

    /**
     * @param key      Cannot be <code>null</code>. Has to be one of the keys provided in the
     *                 constructor.
     * @param newValue
     * @return Previous value or <code>null</code>.
     */
    public V put(K key, V newValue) {
        if (headEntry == null) {
            headEntry = new Entry<K, V>(key, newValue);

            return null;
        }

        headEntry.key = key;

        V oldValue = headEntry.value;
        headEntry.value = newValue;

        return oldValue;
    }

    /**
     * @param key <p>Cannot be <code>null</code>. Has to be one of the keys provided in the
     *            constructor.</p> <p>Note: The key will still remain in the map. Only the value
     *            will be cleared.</p>
     * @return The value associated with the key before clearing.
     */
    public V remove(Object key) {
        Entry<K, V> e = headEntry;

        if (e == null || (e.key != key && e.key.equals(key) == false)) {
            return null;
        }

        headEntry = null;

        return e.value;
    }

    /**
     * Clears the values only. The keys remain.
     */
    public void clear() {
        headEntry = null;
    }

    /**
     * Never changes during the life of this instance.
     *
     * @return The number of keys present.
     */
    public int size() {
        return (headEntry == null) ? 0 : 1;
    }

    /**
     * Never changes during the life of this instance.
     *
     * @return
     */
    public boolean isEmpty() {
        return (headEntry == null);
    }

    /**
     * @param key Unlike the other methods - this method allows any arbitrary key to be checked and
     *            is more tolerant.
     * @return
     */
    public boolean containsKey(Object key) {
        if (key == null) {
            return false;
        }

        if (headEntry == null) {
            return false;
        }

        K k = headEntry.key;

        return k == key || k.equals(key);
    }

    /**
     * @return Readonly set.
     */
    public Set<Map.Entry<K, V>> entrySet() {
        return new MyEntrySet();
    }

    /**
     * @return Readonly iterator.
     */
    public Iterator<K> iterator() {
        return new MyKeyIterator();
    }

    /**
     * @return Readonly set.
     */
    public Set<K> keySet() {
        return new MyKeySet();
    }

    /**
     * @return Readonly collection
     */
    public Collection<V> values() {
        return new MyValues();
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("{");

        s.append(headEntry.key).append('=').append(headEntry.value).append(';');
        s.append('}');

        return s.toString();
    }

    //-----------

    public void writeExternal(ObjectOutput out) throws IOException {
        int x = size();

        out.writeByte(x);

        if (x == 0) {
            return;
        }

        out.writeObject(headEntry.key);
        out.writeObject(headEntry.value);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        int size = in.readByte();

        if (size == 0) {
            return;
        }

        K key = (K) in.readObject();
        V value = (V) in.readObject();

        headEntry = new Entry<K, V>(key, value);
    }

    //-----------

    /**
     * @throws UnsupportedOperationException
     */
    public boolean containsValue(Object value) {
        throw new UnsupportedOperationException();
    }

    /**
     * @throws UnsupportedOperationException
     */
    public void putAll(Map<? extends K, ? extends V> t) {
        throw new UnsupportedOperationException();
    }

    //-----------

    protected static class Entry<K, V> implements Map.Entry<K, V> {
        private K key;

        private V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        //------------

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public V setValue(V value) {
            V oldValue = this.value;
            this.value = value;

            return oldValue;
        }
    }

    /**
     * Readonly set.
     */
    protected class MyEntrySet extends AbstractSet<Map.Entry<K, V>> {
        public int size() {
            return SingleValueLiteMap.this.size();
        }

        public boolean isEmpty() {
            return SingleValueLiteMap.this.isEmpty();
        }

        public boolean contains(Object o) {
            return SingleValueLiteMap.this.containsKey(o);
        }

        public Iterator<Map.Entry<K, V>> iterator() {
            return new MyEntryIterator();
        }

        //--------

        public boolean add(Entry<K, V> o) {
            throw new UnsupportedOperationException();
        }

        public boolean remove(Object o) {
            throw new UnsupportedOperationException();
        }

        public boolean addAll(Collection<? extends Map.Entry<K, V>> c) {
            throw new UnsupportedOperationException();
        }

        public boolean retainAll(Collection<?> c) {
            throw new UnsupportedOperationException();
        }

        public boolean removeAll(Collection<?> c) {
            throw new UnsupportedOperationException();
        }

        public void clear() {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Readonly set.
     */
    protected class MyKeySet extends AbstractSet<K> {
        public int size() {
            return SingleValueLiteMap.this.size();
        }

        public boolean isEmpty() {
            return SingleValueLiteMap.this.isEmpty();
        }

        public boolean contains(Object o) {
            return SingleValueLiteMap.this.containsKey(o);
        }

        public Iterator<K> iterator() {
            return new MyKeyIterator();
        }

        //--------

        public boolean add(K o) {
            throw new UnsupportedOperationException();
        }

        public boolean remove(Object o) {
            throw new UnsupportedOperationException();
        }

        public boolean addAll(Collection<? extends K> c) {
            throw new UnsupportedOperationException();
        }

        public boolean retainAll(Collection<?> c) {
            throw new UnsupportedOperationException();
        }

        public boolean removeAll(Collection<?> c) {
            throw new UnsupportedOperationException();
        }

        public void clear() {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Readonly iterator.
     */
    protected class MyEntryIterator implements Iterator<Map.Entry<K, V>> {
        boolean started = false;

        public void reset() {
            started = false;
        }

        public boolean hasNext() {
            return !started && (headEntry != null);
        }

        public Entry<K, V> next() {
            if (started == false && SingleValueLiteMap.this.headEntry != null) {
                started = true;

                return SingleValueLiteMap.this.headEntry;
            }

            throw new NoSuchElementException();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Readonly iterator.
     */
    protected class MyKeyIterator implements Iterator<K> {
        boolean started = false;

        public boolean hasNext() {
            return !started && (headEntry != null);
        }

        public K next() {
            if (started == false && SingleValueLiteMap.this.headEntry != null) {
                started = true;

                return SingleValueLiteMap.this.headEntry.getKey();
            }

            throw new NoSuchElementException();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Readonly collection.
     */
    protected class MyValues extends AbstractCollection<V> {
        public int size() {
            return SingleValueLiteMap.this.size();
        }

        public boolean isEmpty() {
            return SingleValueLiteMap.this.isEmpty();
        }

        public boolean contains(Object o) {
            return SingleValueLiteMap.this.containsValue(o);
        }

        public Iterator<V> iterator() {
            return new MyValueIterator();
        }

        //--------

        public boolean add(V o) {
            throw new UnsupportedOperationException();
        }

        public boolean remove(Object o) {
            throw new UnsupportedOperationException();
        }

        public boolean addAll(Collection<? extends V> c) {
            throw new UnsupportedOperationException();
        }

        public boolean retainAll(Collection<?> c) {
            throw new UnsupportedOperationException();
        }

        public boolean removeAll(Collection<?> c) {
            throw new UnsupportedOperationException();
        }

        public void clear() {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Readonly iterator.
     */
    protected class MyValueIterator implements Iterator<V> {
        boolean started = false;

        public boolean hasNext() {
            return !started && (headEntry != null);
        }

        public V next() {
            if (started == false && SingleValueLiteMap.this.headEntry != null) {
                started = true;

                return SingleValueLiteMap.this.headEntry.getValue();
            }

            throw new NoSuchElementException();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
