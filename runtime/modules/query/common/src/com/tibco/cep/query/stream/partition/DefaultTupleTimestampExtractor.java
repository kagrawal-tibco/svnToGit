package com.tibco.cep.query.stream.partition;

import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.partition.TimeWindowInfo.TupleTimestampExtractor;
import com.tibco.cep.query.stream.tuple.Tuple;

/*
 * Author: Ashwin Jayaprakash Date: Nov 14, 2007 Time: 12:38:40 PM
 */

public class DefaultTupleTimestampExtractor implements TupleTimestampExtractor {
    public Long extract(GlobalContext globalContext, QueryContext queryContext, Tuple tuple) {
        return tuple.getTimestamp();
    }

    public int extractInteger(GlobalContext globalContext, QueryContext queryContext, Tuple tuple) {
        throw new UnsupportedOperationException();
    }

    public long extractLong(GlobalContext globalContext, QueryContext queryContext, Tuple tuple) {
        return tuple.getTimestamp();
    }

    public float extractFloat(GlobalContext globalContext, QueryContext queryContext, Tuple tuple) {
        throw new UnsupportedOperationException();
    }

    public double extractDouble(GlobalContext globalContext, QueryContext queryContext,
                                Tuple tuple) {
        throw new UnsupportedOperationException();
    }
}