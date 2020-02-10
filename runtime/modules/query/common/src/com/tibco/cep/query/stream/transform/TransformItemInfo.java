package com.tibco.cep.query.stream.transform;

import com.tibco.cep.query.stream.expression.TupleValueExtractor;

/*
 * Author: Ashwin Jayaprakash Date: Nov 6, 2007 Time: 11:08:35 AM
 */

public class TransformItemInfo {
    protected final TupleValueExtractor extractor;

    public TransformItemInfo(TupleValueExtractor extractor) {
        this.extractor = extractor;
    }

    public TupleValueExtractor getExtractor() {
        return extractor;
    }
}
