package com.tibco.cep.query.stream.expression;

import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.FixedKeyHashMap;

/*
* Author: Ashwin Jayaprakash Date: Oct 26, 2007 Time: 12:37:52 PM
*/

public interface ExpressionEvaluator {
    /**
     * @param globalContext
     * @param queryContext
     * @param aliasAndTuples
     * @return
     */
    public Object evaluate(GlobalContext globalContext, QueryContext queryContext,
                           FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples);

    /**
     * Optional.
     *
     * @param globalContext
     * @param queryContext
     * @param aliasAndTuples
     * @return
     */
    public boolean evaluateBoolean(GlobalContext globalContext, QueryContext queryContext,
                                   FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples);

    /**
     * Optional.
     *
     * @param globalContext
     * @param queryContext
     * @param aliasAndTuples
     * @return
     */
    public int evaluateInteger(GlobalContext globalContext, QueryContext queryContext,
                               FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples);

    /**
     * Optional.
     *
     * @param globalContext
     * @param queryContext
     * @param aliasAndTuples
     * @return
     */
    public long evaluateLong(GlobalContext globalContext, QueryContext queryContext,
                             FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples);

    /**
     * Optional.
     *
     * @param globalContext
     * @param queryContext
     * @param aliasAndTuples
     * @return
     */
    public float evaluateFloat(GlobalContext globalContext, QueryContext queryContext,
                               FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples);

    /**
     * Optional.
     *
     * @param globalContext
     * @param queryContext
     * @param aliasAndTuples
     * @return
     */
    public double evaluateDouble(GlobalContext globalContext, QueryContext queryContext,
                                 FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples);
}
