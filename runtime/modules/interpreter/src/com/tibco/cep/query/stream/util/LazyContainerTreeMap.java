package com.tibco.cep.query.stream.util;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/*
* Author: Ashwin Jayaprakash Date: Apr 1, 2008 Time: 11:59:31 AM
*/

/**
 * <p>Custom data structure to hold more than 1 item under a key. If there is only 1 item ({@link
 * I}) under the key {@link K}, then it gets stored directly. If there are more than 1 items, then
 * they get stored in the container {@link V} provided by {@link com.tibco.cep.query.stream.util.LazyContainerTreeMap.ValueCollectionCreator}.
 * </p> <p>Note: Make sure that the individual elements {@link I} are not {@link
 * java.util.Collection} instances.</p>
 */
public final class LazyContainerTreeMap<K, I, V extends Collection<I>> extends TreeMap<K, Object> {
    protected final ValueCollectionCreator<I, V> collectionCreator;

    /**
     * @param collectionCreator
     */
    public LazyContainerTreeMap(ValueCollectionCreator<I, V> collectionCreator) {
        this.collectionCreator = collectionCreator;
    }

    /**
     * @param c
     * @param collectionCreator
     */
    public LazyContainerTreeMap(Comparator<? super K> c,
                                ValueCollectionCreator<I, V> collectionCreator) {
        super(c);

        this.collectionCreator = collectionCreator;
    }

    /**
     * Remove the value stored under the given key. If there aren't any other items stored under the
     * key, the key is also removed.
     *
     * @param key
     * @param value
     * @return <code>true</code> if the remove succeeded.
     */
    public boolean removeValueForKey(K key, I value) {
        Object actualValueInMap = remove(key);

        if (actualValueInMap instanceof Collection == false) {
            return actualValueInMap != null;
        }

        //-----------

        V container = (V) actualValueInMap;

        boolean b = container.remove(value);

        //Put the container back into the map because it is not empty.
        if (container.isEmpty() == false) {
            super.put(key, container);
        }

        return b;
    }

    //----------

    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public void putAll(Map<? extends K, ? extends Object> map) {
        throw new UnsupportedOperationException();
    }

    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public Set<Map.Entry<K, Object>> entrySet() {
        throw new UnsupportedOperationException();
    }

    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public boolean containsValue(Object value) {
        throw new UnsupportedOperationException();
    }

    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public Collection<Object> values() {
        throw new UnsupportedOperationException();
    }

    /**
     * Stores the value under the key. If there is another value existing, then a container is
     * created ({@link V}) and all of the values are stored in it.
     *
     * @param key
     * @param value Type {@link I}
     * @return
     */
    @Override
    public Object put(K key, Object value) {
        Object obj = get(key);

        if (obj == null) {
            return super.put(key, value);
        }
        //Something already there.
        else if (obj instanceof Collection) {
            V container = (V) obj;

            return container.add((I) value);
        }

        //Create new one.
        V container = collectionCreator.createContainer();
        container.add((I) obj);
        super.put(key, container);

        return container.add((I) value);
    }

    //----------

    public static interface ValueCollectionCreator<I, V extends Collection<I>> {
        public V createContainer();
    }
}
