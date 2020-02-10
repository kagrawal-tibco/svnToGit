package com.tibco.cep.query.stream._join2_.impl;

import com.tibco.cep.query.stream._join2_.api.IndexedStore;
import com.tibco.cep.query.stream._join2_.api.Operator;
import com.tibco.cep.query.stream.util.CustomIterable;
import com.tibco.cep.query.stream.util.ReusableIterator;
import com.tibco.cep.query.stream.util.SingleElementCollection;
import com.tibco.cep.query.stream.util.WrappedReusableIterator;

import java.util.*;

/*
* Author: Ashwin Jayaprakash Date: Aug 4, 2008 Time: 4:56:44 PM
*/
public class SimpleIndexedStore<C extends Comparable, K, V>
        implements IndexedStore<C, K, V> {
    protected TreeMap<C, Object> delegate;

    protected ComplexValueReusableIterator<K, V> complexValueIterator;

    protected EmptyIterator<V> emptyIterator;

    protected WrappedReusableIterator<V> wrappedReusableIterator;

    protected WrappedIterable<V> wrappedIterable;

    public SimpleIndexedStore() {
        //todo Need a comparator that handles nulls.
        this.delegate = new TreeMap<C, Object>();

        this.complexValueIterator =
                new ComplexValueReusableIterator<K, V>(new SingleElementCollection<Object>(null));

        this.emptyIterator = new EmptyIterator<V>();

        this.wrappedReusableIterator =
                new WrappedReusableIterator<V>(new SingleElementCollection<V>(null));

        this.wrappedIterable = new WrappedIterable<V>(this.emptyIterator, 0);
    }

    //--------------

    public void put(K k, V v) {
    }

    public void remove(K k) {
    }

    public void removeAll() {
    }

    public CustomIterable<V> getAll() {
        ReusableIterator<V> reusableIterator = createIterator(delegate);
        wrappedIterable.reset(reusableIterator, delegate.size());

        return wrappedIterable;
    }

    //--------------

    public void record(K primaryKey, C secondaryKey, V value) {
        Object existing = delegate.get(secondaryKey);

        if (existing == null) {
            delegate.put(secondaryKey, new SinglePairContainer<K, V>(primaryKey, value));

            return;
        }

        if (existing instanceof SinglePairContainer) {
            MultiPairContainer<K, V> multiPairContainer = new MultiPairContainer<K, V>();

            SinglePairContainer<K, V> singlePairContainer = (SinglePairContainer<K, V>) existing;
            multiPairContainer.put(singlePairContainer.key, singlePairContainer.value);

            multiPairContainer.put(primaryKey, value);

            delegate.put(secondaryKey, multiPairContainer);

            return;
        }

        MultiPairContainer<K, V> container = (MultiPairContainer<K, V>) existing;
        container.put(primaryKey, value);
    }

    public void erase(K primaryKey, C secondaryKey) {
        Object existing = delegate.remove(secondaryKey);

        //Do not hold on to some long deleted entry.
        wrappedReusableIterator.clear();
        complexValueIterator.clear();

        if (existing == null || existing instanceof SinglePairContainer) {
            return;
        }

        MultiPairContainer<K, V> multiPairContainer = (MultiPairContainer<K, V>) existing;

        multiPairContainer.remove(primaryKey);

        //Still some items in the container, so put it back.
        if (multiPairContainer.size() > 0) {
            delegate.put(secondaryKey, multiPairContainer);
        }
    }

    public void eraseAll() {
        wrappedReusableIterator.clear();
        complexValueIterator.clear();

        delegate.clear();
    }

    //------------

    public V getAnyMatch(Operator operator, C c) {
        throw new UnsupportedOperationException();
    }

    public V getAnyMatch(Operator operator1, C c1, Operator operator2, C c2) {
        throw new UnsupportedOperationException();
    }

    public V getAnyMatch(Operator operator, C[] c) {
        throw new UnsupportedOperationException();
    }

    public V getAnyMatch(Operator operator, Collection<C> c) {
        throw new UnsupportedOperationException();
    }

    public CustomIterable<V> getMatches(Operator operator, C c) {
        ReusableIterator<V> reusableIterator = fetch(operator, c);
        wrappedIterable.reset(reusableIterator,
                (reusableIterator == emptyIterator) ? 0 : Integer.MAX_VALUE);

        return wrappedIterable;
    }

    public CustomIterable<V> getMatches(Operator operator1, C c1, Operator operator2, C c2) {
        throw new UnsupportedOperationException();
    }

    public CustomIterable<V> getMatches(Operator operator, C[] c) {
        throw new UnsupportedOperationException();
    }

    public CustomIterable<V> getMatches(Operator operator, Collection<C> c) {
        throw new UnsupportedOperationException();
    }

    //------------

    //todo If key itself is null, then throw errors
    //todo Puts cannot accept nulls.

    protected ReusableIterator<V> fetch(Operator operator, C key) {
        if (delegate.size() == 0) {
            return emptyIterator;
        }

        //-------------

        ReusableIterator<V> retVal = null;

        switch (operator) {
            case Equal:
                return fetchEquals(key);

            case NotEqual:
                //todo
                break;

            case Less: {
                SortedMap<C, Object> lessThanMap = delegate.headMap(key);

                return createIterator(lessThanMap);
            }

            //todo When we move to Java1.6 change this to NavigableMap.
            case LessOrEqual: {
                //Try to find the key that is greater than requested key.
                C nextKey = getGreaterThanKeyIfExists(key);

                //Requested key is the last key.
                if (nextKey == null) {
                    //Return the entire map.
                    return createIterator(delegate);
                }

                //Return everything that is less than or equal to requested key.
                SortedMap<C, Object> lessThanOrEqualMap = delegate.headMap(nextKey);

                return createIterator(lessThanOrEqualMap);
            }

            //todo When we move to Java1.6 change this to NavigableMap.
            case Greater: {
                //Try to find the key that is greater than requested key.
                C nextKey = getGreaterThanKeyIfExists(key);

                //Requested key is the last key.
                if (nextKey == null) {
                    return emptyIterator;
                }

                //Return everything that is greater than requested map.
                SortedMap<C, Object> greaterThanMap = delegate.tailMap(nextKey);

                return createIterator(greaterThanMap);
            }

            case GreaterOrEqual: {
                SortedMap<C, Object> greaterThanOrEqualMap = delegate.tailMap(key);

                return createIterator(greaterThanOrEqualMap);
            }

            default: {
                throw new UnsupportedOperationException(
                        "Operation [" + operator + "] is not supported.");
            }
        }

        return retVal;
    }

    private ReusableIterator<V> createIterator(SortedMap<C, Object> map) {
        if (map.size() == 0) {
            return emptyIterator;
        }

        Collection<Object> collection = map.values();
        complexValueIterator.reset(collection);

        return complexValueIterator;
    }

    /**
     * @param key
     * @return <code>null</code> if it does not exist.
     */
    private C getGreaterThanKeyIfExists(C key) {
        C nextKey = null;

        SortedMap<C, Object> greaterThanOrEqualMap = delegate.tailMap(key);
        Set<C> keysGreaterThanOrEqual = greaterThanOrEqualMap.keySet();
        Iterator<C> keysGreaterThanOrEqualIter = keysGreaterThanOrEqual.iterator();
        if (keysGreaterThanOrEqualIter.hasNext()) {
            nextKey = keysGreaterThanOrEqualIter.next();

            //This is our key. Get the next key that is greater than this.
            if (nextKey == key || nextKey.equals(key)) {
                if (keysGreaterThanOrEqualIter.hasNext()) {
                    nextKey = keysGreaterThanOrEqualIter.next();
                }
                else {
                    nextKey = null;
                }
            }
        }

        return nextKey;
    }

    protected ReusableIterator<V> fetchEquals(C key) {
        Object object = delegate.get(key);

        if (object == null) {
            return emptyIterator;
        }

        Collection<V> collection = null;

        if (object instanceof SinglePairContainer) {
            SinglePairContainer<K, V> spc = (SinglePairContainer<K, V>) object;

            V value = spc.getValue();
            collection = new SingleElementCollection<V>(value);
        }
        else {
            MultiPairContainer<K, V> mpc = (MultiPairContainer<K, V>) object;

            collection = mpc.values();
        }

        wrappedReusableIterator.reset(collection);

        return wrappedReusableIterator;
    }

    //---------------

    /**
     * Only for type safety.
     */
    protected static class MultiPairContainer<K, V> extends HashMap<K, V> {
    }

    protected static class SinglePairContainer<K, V> {
        protected K key;

        protected V value;

        public SinglePairContainer(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }
    }

    protected static class WrappedIterable<V> implements CustomIterable<V> {
        protected ReusableIterator<V> iterator;

        protected int size;

        public WrappedIterable(ReusableIterator<V> iterator, int size) {
            this.iterator = iterator;
            this.size = size;
        }

        public void reset(ReusableIterator<V> iterator, int size) {
            this.iterator = iterator;
            this.size = size;
        }

        public int size() {
            return size;
        }

        public ReusableIterator<V> iterator() {
            return iterator;
        }
    }

    protected static class EmptyIterator<V> implements ReusableIterator<V> {
        public void reset() {
        }

        public boolean hasNext() {
            return false;
        }

        public V next() {
            throw new NoSuchElementException();
        }

        /**
         * @throws UnsupportedOperationException
         */
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    protected static class ComplexValueReusableIterator<K, V> implements ReusableIterator<V> {
        protected Collection<Object> outerContent;

        protected Iterator<Object> outerContentValueIterator;

        protected Iterator<V> innerMultiValueIterator;

        public ComplexValueReusableIterator(Collection<Object> collection) {
            this.outerContent = collection;

            doReset();
        }

        public void reset(Collection<Object> content) {
            this.outerContent = content;

            doReset();
        }

        public void clear() {
            outerContent = null;
            outerContentValueIterator = null;
            innerMultiValueIterator = null;
        }

        //-------------

        public void reset() {
            doReset();
        }

        private void doReset() {
            this.outerContentValueIterator = outerContent.iterator();
        }

        public boolean hasNext() {
            if (innerMultiValueIterator != null && innerMultiValueIterator.hasNext()) {
                return true;
            }

            return outerContentValueIterator.hasNext();
        }

        public V next() {
            for (; ;) {
                if (innerMultiValueIterator == null) {
                    Object currElement = outerContentValueIterator.next();

                    if (currElement instanceof SinglePairContainer) {
                        SinglePairContainer<K, V> spc = (SinglePairContainer<K, V>) currElement;

                        return spc.getValue();
                    }

                    MultiPairContainer<K, V> mpc = (MultiPairContainer<K, V>) currElement;
                    innerMultiValueIterator = mpc.values().iterator();

                    continue;
                }

                //------------

                if (innerMultiValueIterator.hasNext()) {
                    return innerMultiValueIterator.next();
                }

                innerMultiValueIterator = null;
            }
        }

        /**
         * @throws UnsupportedOperationException
         */
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}