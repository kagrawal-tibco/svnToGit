package com.tibco.cep.query.stream.impl.rete.expression;

import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.expression.TupleValueExtractor;
import com.tibco.cep.query.stream.tuple.Tuple;

/*
* Author: Ashwin Jayaprakash Date: Jul 14, 2008 Time: 11:28:09 AM
*/
public class ReteEntityFilterImpl implements ReteEntityFilter {


    protected TupleValueExtractor extractor;

    public TupleValueExtractor getExtractor() {
        return extractor;
    }


    public ReteEntityFilterImpl(TupleValueExtractor extractor) {
        this.extractor = extractor;
    }


    public boolean allow(GlobalContext globalContext, QueryContext queryContext,
                         Tuple tuple) {
        final Boolean b = (Boolean) extractor.extract(globalContext, queryContext, tuple);

        return b;
    }

    
}
