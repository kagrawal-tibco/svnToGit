package com.tibco.cep.as.kit.collection;

import java.util.Collection;

/*
* Author: Ashwin Jayaprakash Date: Apr 25, 2009 Time: 2:45:10 PM
*/
public interface DiscardableCollection<E> extends Collection<E>, Discardable {
    DiscardableIterator<E> iterator();
}
