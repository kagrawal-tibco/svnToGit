package com.tibco.cep.query.stream.util;

import java.util.AbstractCollection;
import java.util.Collection;

/*
* Author: Ashwin Jayaprakash Date: Apr 30, 2008 Time: 7:01:18 PM
*/

public class WrappedCustomCollection<E> extends AbstractCollection<E>
        implements CustomCollection<E> {
    protected Collection<E> actualSource;

    public WrappedCustomCollection(Collection<E> actualSource) {
        this.actualSource = actualSource;
    }

    public Collection<E> getActualSource() {
        return actualSource;
    }

    @Override
    public boolean add(E o) {
        return actualSource.add(o);
    }

    /**
     * If more control is desired over repeated creation of these iterators, then it might be good
     * to have a look at {@link com.tibco.cep.query.stream.util.WrappedReusableIterator}.
     *
     * @return
     */
    public ReusableIterator<E> iterator() {
        return new WrappedReusableIterator<E>(actualSource);
    }

    public int size() {
        return actualSource.size();
    }
}
