package com.tibco.cep.query.stream.aggregate;

import com.tibco.cep.query.stream.context.DefaultGlobalContext;
import com.tibco.cep.query.stream.context.DefaultQueryContext;
import com.tibco.cep.query.stream.expression.TupleValueExtractor;
import com.tibco.cep.query.stream.tuple.Tuple;

/*
* Author: Ashwin Jayaprakash Date: Apr 6, 2008 Time: 12:42:38 AM
*/
public interface Aggregator {
    public void setExtractor(TupleValueExtractor extractor);

    public void setRecordAddsOnly(boolean recordAddsOnly);

    /**
     * @param globalContext
     * @param queryContext
     * @param tuple
     */
    public void add(DefaultGlobalContext globalContext, DefaultQueryContext queryContext, Tuple tuple);

    /**
     * @param globalContext
     * @param queryContext
     * @param tuple
     */
    public void remove(DefaultGlobalContext globalContext, DefaultQueryContext queryContext,
                       Tuple tuple);

    /**
     * Must be idempotent.
     *
     * @return
     * @throws Exception
     */
    public Object calculateAggregate() throws Exception;

    /**
     * Optional. Must be idempotent.
     *
     * @return
     * @throws Exception
     */
    public int calculateAggregateAsInteger() throws Exception;

    /**
     * Optional. Must be idempotent.
     *
     * @return
     * @throws Exception
     */
    public long calculateAggregateAsLong() throws Exception;

    /**
     * Optional. Must be idempotent.
     *
     * @return
     * @throws Exception
     */
    public float calculateAggregateAsFloat() throws Exception;

    /**
     * Optional. Must be idempotent.
     *
     * @return
     * @throws Exception
     */
    public double calculateAggregateAsDouble() throws Exception;
}
