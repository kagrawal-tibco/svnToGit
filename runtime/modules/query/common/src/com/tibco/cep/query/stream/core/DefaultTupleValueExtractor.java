package com.tibco.cep.query.stream.core;

import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.expression.TupleValueExtractor;
import com.tibco.cep.query.stream.tuple.Tuple;

/*
* Author: Ashwin Jayaprakash Date: Apr 7, 2008 Time: 11:02:12 AM
*/
public class DefaultTupleValueExtractor implements TupleValueExtractor {
    public Object extract(GlobalContext globalContext, QueryContext queryContext, Tuple tuple) {
        return null;
    }

    public int extractInteger(GlobalContext globalContext, QueryContext queryContext, Tuple tuple) {
        Number n = (Number) extract(globalContext, queryContext, tuple);

        if (n == null) {
            return 0;
        }

        return n.intValue();
    }

    public long extractLong(GlobalContext globalContext, QueryContext queryContext, Tuple tuple) {
        Number n = (Number) extract(globalContext, queryContext, tuple);

        if (n == null) {
            return 0;
        }

        return n.longValue();
    }

    public float extractFloat(GlobalContext globalContext, QueryContext queryContext, Tuple tuple) {
        Number n = (Number) extract(globalContext, queryContext, tuple);

        if (n == null) {
            return 0;
        }

        return n.floatValue();
    }

    public double extractDouble(GlobalContext globalContext, QueryContext queryContext,
                                Tuple tuple) {
        Number n = (Number) extract(globalContext, queryContext, tuple);

        if (n == null) {
            return 0;
        }

        return n.doubleValue();
    }
}

