package com.tibco.cep.query.stream._join2_.util;

import com.tibco.cep.query.stream.util.CustomIterable;
import com.tibco.cep.query.stream.util.ReusableIterator;

import java.util.Collection;
import java.util.Iterator;

/*
* Author: Ashwin Jayaprakash Date: Jun 1, 2009 Time: 9:09:56 PM
*/
public class SimpleCustomIterable<E> implements CustomIterable<E> {
    protected Collection<E> delegate;

    public SimpleCustomIterable(Collection<E> delegate) {
        this.delegate = delegate;
    }

    public int size() {
        return delegate.size();
    }

    public ReusableIterator<E> iterator() {
        return new SimpleReusableIteratorImpl();
    }

    //-----------

    protected class SimpleReusableIteratorImpl implements ReusableIterator<E> {
        protected Iterator<E> actualIterator;

        public SimpleReusableIteratorImpl() {
            doReset();
        }

        private void doReset() {
            actualIterator = delegate.iterator();
        }

        public void reset() {
            doReset();
        }

        public boolean hasNext() {
            return actualIterator.hasNext();
        }

        public E next() {
            return actualIterator.next();
        }

        public void remove() {
            actualIterator.remove();
        }
    }
}
