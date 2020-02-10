package com.tibco.cep.query.stream.impl.expression.array;

import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.expression.ExpressionEvaluator;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.FixedKeyHashMap;

/*
* Created by IntelliJ IDEA.
* User: nprade
* Date: Aug 14, 2008
* Time: 4:08:45 PM
*/
public class BooleanArrayItemEvaluator
        extends ArrayItemEvaluator {


    public BooleanArrayItemEvaluator(ExpressionEvaluator arrayEvaluator, ExpressionEvaluator indexEvaluator) {
        super(arrayEvaluator, indexEvaluator);
    }


    /**
     * @param globalContext
     * @param queryContext
     * @param aliasAndTuples
     * @return
     */
    public Object evaluate(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        return this.evaluateBoolean(globalContext, queryContext, aliasAndTuples);
    }


    /**
     * Optional.
     *
     * @param globalContext
     * @param queryContext
     * @param aliasAndTuples
     * @return
     */
    public boolean evaluateBoolean(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        final boolean[] array = (boolean[]) this.arrayEvaluator.evaluate(globalContext, queryContext, aliasAndTuples);
        final int index = this.indexEvaluator.evaluateInteger(globalContext, queryContext, aliasAndTuples);
        return array[index];
    }


    /**
     * Optional.
     *
     * @param globalContext
     * @param queryContext
     * @param aliasAndTuples
     * @return
     */
    public double evaluateDouble(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        throw new UnsupportedOperationException();
    }


    /**
     * Optional.
     *
     * @param globalContext
     * @param queryContext
     * @param aliasAndTuples
     * @return
     */
    public float evaluateFloat(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        throw new UnsupportedOperationException();
    }


    /**
     * Optional.
     *
     * @param globalContext
     * @param queryContext
     * @param aliasAndTuples
     * @return
     */
    public int evaluateInteger(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        throw new UnsupportedOperationException();
    }


    /**
     * Optional.
     *
     * @param globalContext
     * @param queryContext
     * @param aliasAndTuples
     * @return
     */
    public long evaluateLong(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        throw new UnsupportedOperationException();
    }


}
