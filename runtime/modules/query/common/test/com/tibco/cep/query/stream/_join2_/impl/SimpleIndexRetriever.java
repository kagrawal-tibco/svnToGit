package com.tibco.cep.query.stream._join2_.impl;

import com.tibco.cep.query.stream._join2_.api.IndexRetriever;
import com.tibco.cep.query.stream._join2_.api.IndexedView;
import com.tibco.cep.query.stream._join2_.api.Operator;
import com.tibco.cep.query.stream.util.CustomIterable;
import com.tibco.cep.query.stream.util.ReusableIterator;

/*
* Author: Ashwin Jayaprakash Date: Jun 1, 2009 Time: 7:34:33 PM
*/
public class SimpleIndexRetriever<C extends Comparable, O> implements IndexRetriever<C, O> {
    protected final Operator operator1;

    protected final Operator optionalOperator2;

    protected IndexedView<C, O> indexedViewForBatch;

    public SimpleIndexRetriever(Operator singleOperator) {
        this(singleOperator, null);
    }

    public SimpleIndexRetriever(Operator operator1, Operator operator2) {
        this.operator1 = operator1;
        this.optionalOperator2 = operator2;
    }

    public void batchStart(IndexedView<C, O> indexedView) {
        this.indexedViewForBatch = indexedView;
    }

    public ReusableIterator<O> retrieve(C... c) {
        switch (c.length) {
            case 1: {
                CustomIterable<O> iterable = indexedViewForBatch.getMatches(operator1, c[0]);
                return iterable.iterator();
            }

            case 2: {
                CustomIterable<O> iterable =
                        indexedViewForBatch.getMatches(operator1, c[0], optionalOperator2, c[1]);
                return iterable.iterator();
            }

            default: {
                CustomIterable<O> iterable = indexedViewForBatch.getMatches(operator1, c);
                return iterable.iterator();
            }
        }
    }

    public void batchEnd() {
        indexedViewForBatch = null;
    }
}
