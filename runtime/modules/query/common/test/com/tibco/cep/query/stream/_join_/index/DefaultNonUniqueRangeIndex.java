package com.tibco.cep.query.stream._join_.index;

import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.util.ReusableIterator;
import com.tibco.cep.query.stream.util.SingleElementCollection;
import com.tibco.cep.query.stream.util.WrappedReusableIterator;

import java.util.*;

/*
* Author: Ashwin Jayaprakash Date: Aug 4, 2008 Time: 4:56:44 PM
*/
public class DefaultNonUniqueRangeIndex<K extends Comparable, V, N> implements Index<K, V, N> {
    protected TreeMap<K, Object> delegate;

    protected ComplexValueReusableIterator<N, V> complexValueIterator;

    protected EmptyIterator<V> emptyIterator;

    protected WrappedReusableIterator<V> wrappedReusableIterator;

    public DefaultNonUniqueRangeIndex() {
        //todo Need a comparator that handles nulls.
        this.delegate = new TreeMap<K, Object>();

        this.complexValueIterator =
                new ComplexValueReusableIterator<N, V>(new SingleElementCollection<Object>(null));

        this.emptyIterator = new EmptyIterator<V>();

        this.wrappedReusableIterator =
                new WrappedReusableIterator<V>(new SingleElementCollection<V>(null));
    }

    public boolean supportsOperator(Operator operator) {
        return true;
    }

    //------------

    public void record(K key, V value, N idOfValue) {
        Object existing = delegate.get(key);

        if (existing == null) {
            delegate.put(key, new SinglePairContainer<N, V>(idOfValue, value));

            return;
        }

        if (existing instanceof SinglePairContainer) {
            MultiPairContainer<N, V> multiPairContainer = new MultiPairContainer<N, V>();

            SinglePairContainer<N, V> singlePairContainer = (SinglePairContainer<N, V>) existing;
            multiPairContainer.put(singlePairContainer.key, singlePairContainer.value);

            multiPairContainer.put(idOfValue, value);

            delegate.put(key, multiPairContainer);

            return;
        }

        MultiPairContainer<N, V> container = (MultiPairContainer<N, V>) existing;
        container.put(idOfValue, value);
    }

    public void erase(K key, N idOfValue) {
        Object existing = delegate.remove(key);

        //Do not hold on to some long deleted entry.
        wrappedReusableIterator.clear();
        complexValueIterator.clear();

        if (existing == null || existing instanceof SinglePairContainer) {
            return;
        }

        MultiPairContainer<N, V> multiPairContainer = (MultiPairContainer<N, V>) existing;

        multiPairContainer.remove(idOfValue);

        //Still some items in the container, so put it back.
        if (multiPairContainer.size() > 0) {
            delegate.put(key, multiPairContainer);
        }
    }

    //------------

    //todo If key itself is null, then throw errors
    //todo Puts cannot accept nulls.

    public Object fetch(GlobalContext globalContext, QueryContext queryContext, Operator operator,
                        K key) {
        if (delegate.size() == 0) {
            return emptyIterator;
        }

        //-------------

        Object retVal = null;

        switch (operator) {
            case ALL:
                return createIterator(delegate);

            case EQUALS:
                return fetchEquals(key);

            case NOT_EQUALS:
                //todo
                break;

            case LESS_THAN: {
                SortedMap<K, Object> lessThanMap = delegate.headMap(key);

                return createIterator(lessThanMap);
            }

            //todo When we move to Java1.6 change this to NavigableMap.
            case LESS_THAN_EQUALS: {
                //Try to find the key that is greater than requested key.
                K nextKey = getGreaterThanKeyIfExists(key);

                //Requested key is the last key.
                if (nextKey == null) {
                    //Return the entire map.
                    return createIterator(delegate);
                }

                //Return everything that is less than or equal to requested key.
                SortedMap<K, Object> lessThanOrEqualMap = delegate.headMap(nextKey);

                return createIterator(lessThanOrEqualMap);
            }

            //todo When we move to Java1.6 change this to NavigableMap.
            case GREATER_THAN: {
                //Try to find the key that is greater than requested key.
                K nextKey = getGreaterThanKeyIfExists(key);

                //Requested key is the last key.
                if (nextKey == null) {
                    return emptyIterator;
                }

                //Return everything that is greater than requested map.
                SortedMap<K, Object> greaterThanMap = delegate.tailMap(nextKey);

                return createIterator(greaterThanMap);
            }

            case GREATER_THAN_EQUALS: {
                SortedMap<K, Object> greaterThanOrEqualMap = delegate.tailMap(key);

                return createIterator(greaterThanOrEqualMap);
            }
        }

        return retVal;
    }

    private ReusableIterator<V> createIterator(SortedMap<K, Object> map) {
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
    private K getGreaterThanKeyIfExists(K key) {
        K nextKey = null;

        SortedMap<K, Object> greaterThanOrEqualMap = delegate.tailMap(key);
        Set<K> keysGreaterThanOrEqual = greaterThanOrEqualMap.keySet();
        Iterator<K> keysGreaterThanOrEqualIter = keysGreaterThanOrEqual.iterator();
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

    protected Object fetchEquals(K key) {
        Object object = delegate.get(key);

        if (object == null) {
            return emptyIterator;
        }

        if (object instanceof SinglePairContainer) {
            SinglePairContainer<N, V> spc = (SinglePairContainer<N, V>) object;

            return spc.getValue();
        }

        MultiPairContainer<N, V> mpc = (MultiPairContainer<N, V>) object;
        Collection<V> collection = mpc.values();
        wrappedReusableIterator.reset(collection);

        return wrappedReusableIterator;
    }

    //---------------

    /**
     * Only for type safety.
     */
    protected static class MultiPairContainer<N, V> extends HashMap<N, V> {
    }

    protected static class SinglePairContainer<N, V> {
        protected N key;

        protected V value;

        public SinglePairContainer(N key, V value) {
            this.key = key;
            this.value = value;
        }

        public N getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }
    }

    protected static class EmptyIterator<V> implements ReusableIterator<V> {
        public void reset() {
        }

        public boolean hasNext() {
            return false;
        }

        public V next() {
            return null;
        }

        /**
         * @throws UnsupportedOperationException
         */
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    protected static class ComplexValueReusableIterator<N, V> implements ReusableIterator<V> {
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
                        SinglePairContainer<N, V> spc = (SinglePairContainer<N, V>) currElement;

                        return spc.getValue();
                    }

                    MultiPairContainer<N, V> mpc = (MultiPairContainer<N, V>) currElement;
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
