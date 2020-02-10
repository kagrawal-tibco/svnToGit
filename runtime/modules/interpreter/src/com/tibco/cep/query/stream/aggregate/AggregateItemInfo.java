package com.tibco.cep.query.stream.aggregate;

import com.tibco.cep.query.stream.expression.TupleValueExtractor;

/*
 * Author: Ashwin Jayaprakash Date: Oct 9, 2007 Time: 11:37:58 AM
 */

public class AggregateItemInfo {
    protected final AggregateCreator creator;

    protected final TupleValueExtractor extractor;

    /**
     * @param creator
     * @param extractor
     */
    public AggregateItemInfo(AggregateCreator creator, TupleValueExtractor extractor) {
        this.creator = creator;
        this.extractor = extractor;
    }

    public AggregateCreator getCreator() {
        return creator;
    }

    public TupleValueExtractor getExtractor() {
        return extractor;
    }
}
