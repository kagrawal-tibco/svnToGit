package com.tibco.cep.query.stream.util;

import java.util.Collection;
import java.util.Iterator;

/*
* Author: Ashwin Jayaprakash Date: Oct 09, 2008 Time: 4:26:58 PM
*/

public class WrappedReusableIterator<E> implements ReusableIterator<E> {
    protected Collection<E> actualCollection;

    protected Iterator<E> actualIterator;

    public WrappedReusableIterator(Collection<E> actualCollection) {
        doReset(actualCollection);
    }

    public void reset(Collection<E> actualCollection) {
        this.actualCollection = actualCollection;

        reset();
    }

    public void clear() {
        actualCollection = null;
        actualIterator = null;
    }

    //---------------

    public void reset() {
        doReset(actualCollection);
    }

    private void doReset(Collection<E> actualCollection) {
        this.actualCollection = actualCollection;
        this.actualIterator = actualCollection.iterator();
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
