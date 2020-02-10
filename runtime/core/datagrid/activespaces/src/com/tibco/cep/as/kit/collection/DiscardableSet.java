package com.tibco.cep.as.kit.collection;

import java.util.Set;

/*
* Author: Ashwin Jayaprakash Date: Apr 25, 2009 Time: 3:00:59 PM
*/
public interface DiscardableSet<E> extends Set<E>, Discardable {
    DiscardableIterator<E> iterator();
}
