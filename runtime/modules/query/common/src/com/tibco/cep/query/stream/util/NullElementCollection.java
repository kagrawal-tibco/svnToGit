package com.tibco.cep.query.stream.util;

import java.util.AbstractCollection;

/*
* Author: Ashwin Jayaprakash Date: Apr 18, 2008 Time: 6:14:43 PM
*/
public final class NullElementCollection<E> extends AbstractCollection<E>
        implements CustomCollection<E> {
    protected int nullCount;

    public NullElementCollection() {
    }

    public NullElementCollection(int nullCount) {
        this.nullCount = nullCount;
    }

    public void setNullCount(int nullCount) {
        this.nullCount = nullCount;
    }

    public int size() {
        return nullCount;
    }

    @Override
    public boolean isEmpty() {
        return nullCount == 0;
    }

    @Override
    public boolean contains(Object o) {
        return o == null;
    }

    public ReusableIterator<E> iterator() {
        return new CollectionIterator();
    }

    @Override
    public boolean add(E o) {
        if (o == null) {
            nullCount++;

            return true;
        }

        return false;
    }

    @Override
    public boolean remove(Object o) {
        if (o == null && nullCount > 0) {
            nullCount--;

            return true;
        }

        return false;
    }

    @Override
    public void clear() {
        nullCount = 0;
    }

    // -----------

    public class CollectionIterator implements ReusableIterator<E> {
        protected int counter;

        public CollectionIterator() {
            reset();
        }

        public void reset() {
            counter = NullElementCollection.this.size();
        }

        public boolean hasNext() {
            return counter > 0;
        }

        /**
         * {@inheritDoc} Relies on {@link #hasNext()} being called before this method.
         */
        public E next() {
            counter--;

            return null;
        }

        public void remove() {
            NullElementCollection.this.remove(null);
        }
    }
}
