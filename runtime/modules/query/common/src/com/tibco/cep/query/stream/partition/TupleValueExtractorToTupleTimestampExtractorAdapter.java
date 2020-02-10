package com.tibco.cep.query.stream.partition;

import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.expression.TupleValueExtractor;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.utils.TypeHelper;

/*
* Created by IntelliJ IDEA.
* User: nprade
* Date: Jan 21, 2009
* Time: 5:01:26 PM
*/
public class TupleValueExtractorToTupleTimestampExtractorAdapter
        implements TimeWindowInfo.TupleTimestampExtractor {


    private TupleValueExtractor tve;


    public TupleValueExtractorToTupleTimestampExtractorAdapter(
            TupleValueExtractor tve) {
        this.tve = tve;
    }


    public Long extract(
            GlobalContext globalContext,
            QueryContext queryContext,
            Tuple tuple) {
        return this.extractLong(globalContext, queryContext, tuple);
    }


    public double extractDouble(
            GlobalContext globalContext,
            QueryContext queryContext,
            Tuple tuple) {
        return this.extractLong(globalContext, queryContext, tuple);
    }

    public float extractFloat(
            GlobalContext globalContext,
            QueryContext queryContext,
            Tuple tuple) {
        return this.extractLong(globalContext, queryContext, tuple);
    }


    public int extractInteger(
            GlobalContext globalContext,
            QueryContext queryContext,
            Tuple tuple) {
        return (int) this.extractLong(globalContext, queryContext, tuple);
    }


    public long extractLong(
            GlobalContext globalContext,
            QueryContext queryContext,
            Tuple tuple) {
        return TypeHelper.toLong(this.tve.extract(globalContext, queryContext, tuple));
    }


}
