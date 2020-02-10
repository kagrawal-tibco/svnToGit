package com.tibco.cep.query.stream._join2_.api;

import com.tibco.cep.query.stream.util.ReusableIterator;

/*
* Author: Ashwin Jayaprakash Date: May 28, 2009 Time: 5:31:49 PM
*/
public interface IndexRetriever<C extends Comparable, O> {
    void batchStart(IndexedView<C, O> indexedView);

    ReusableIterator<O> retrieve(C... c);

    void batchEnd();
}
