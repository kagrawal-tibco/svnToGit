package com.tibco.cep.query.benchmark.query;

import java.util.Collection;

/*
* Author: Ashwin Jayaprakash Date: Jun 19, 2008 Time: 2:12:15 PM
*/
public interface Chunk<E> {
    int getSize();

    /**
         * @return Read-only collection!
     */
    Collection<E> getElements();

    void add(E e);

    void discard();
}
