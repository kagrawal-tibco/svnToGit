// copyright 2001 TIBCO Software Inc

package com.tibco.cep.mapper.xml.xdata;

import java.util.Iterator;

/**
 * Used to wrap list iterators or other iterators that may
 * have functioning remove() methods.  Hides the remove()
 * functionality so that the iterator can be handed off
 * to "untrusted" code if we're feeling paranoid.
 */
final class NoRemoveIterator implements Iterator {

    private Iterator iterator;

    protected NoRemoveIterator(Iterator iterator) {
        this.iterator = iterator;
    }

    public boolean hasNext() {
        return iterator.hasNext();
    }

    public Object next() {
        return iterator.next();
    }

    public void remove() {
        throw new UnsupportedOperationException("remove() not supported");
    }
}