package com.tibco.cep.query.stream.expression;

import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.tuple.Tuple;

/*
 * Author: Ashwin Jayaprakash Date: Oct 4, 2007 Time: 3:24:59 PM
 */

public interface TupleValueExtractor {
    /**
     * Optional.
     *
     * @param globalContext
     * @param queryContext
     * @param tuple
     * @return
     */
    public Object extract(GlobalContext globalContext, QueryContext queryContext, Tuple tuple);

    /**
     * Optional.
     *
     * @param globalContext
     * @param queryContext
     * @param tuple
     * @return
     */
    public int extractInteger(GlobalContext globalContext, QueryContext queryContext, Tuple tuple);

    /**
     * Optional.
     *
     * @param globalContext
     * @param queryContext
     * @param tuple
     * @return
     */
    public long extractLong(GlobalContext globalContext, QueryContext queryContext, Tuple tuple);

    /**
     * Optional.
     *
     * @param globalContext
     * @param queryContext
     * @param tuple
     * @return
     */
    public float extractFloat(GlobalContext globalContext, QueryContext queryContext, Tuple tuple);

    /**
     * Optional.
     *
     * @param globalContext
     * @param queryContext
     * @param tuple
     * @return
     */
    public double extractDouble(GlobalContext globalContext, QueryContext queryContext,
                                Tuple tuple);
}
