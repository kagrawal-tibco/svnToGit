package com.tibco.cep.query.stream.util;

import java.util.Collection;

/*
 * Author: Ashwin Jayaprakash Date: Feb 27, 2008 Time: 2:32:36 PM
 */

/**
 * Custom Collection that has a reusable Iterator: {@link ReusableIterator}.
 */
public interface CustomCollection<E> extends Collection<E> {
    public ReusableIterator<E> iterator();
}
