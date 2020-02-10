package com.tibco.cep.query.stream.impl.expression.comparison;

import java.io.Serializable;

import com.tibco.cep.query.exec.util.Comparisons;
import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.expression.ExpressionEvaluator;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.FixedKeyHashMap;
import com.tibco.cep.query.utils.TypeHelper;

/*
* Created by IntelliJ IDEA.
* User: nprade
* Date: Aug 14, 2008
* Time: 4:08:45 PM
*/
public class ComparisonEvaluator
        implements ExpressionEvaluator, Serializable {


    protected ExpressionEvaluator leftEvaluator;
    protected ExpressionEvaluator rightEvaluator;


    public ComparisonEvaluator(ExpressionEvaluator leftEvaluator, ExpressionEvaluator rightEvaluator) {
        this.leftEvaluator = leftEvaluator;
        this.rightEvaluator = rightEvaluator;
    }

    public ExpressionEvaluator[] getOperands() {
        return new ExpressionEvaluator[] {leftEvaluator, rightEvaluator};
    }

    /**
     * @param globalContext
     * @param queryContext
     * @param aliasAndTuples
     * @return
     */
    public Object evaluate(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        return evaluateInteger(globalContext,  queryContext,  aliasAndTuples);
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
    public double evaluateDouble(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        return evaluateInteger(globalContext,  queryContext,  aliasAndTuples);
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
        return evaluateInteger(globalContext,  queryContext,  aliasAndTuples);
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
        final Comparable left = (Comparable) this.leftEvaluator.evaluate(globalContext, queryContext, aliasAndTuples);
        final Comparable right = (Comparable) this.rightEvaluator.evaluate(globalContext, queryContext, aliasAndTuples);
        return Comparisons.compare(TypeHelper.toDouble(left), TypeHelper.toDouble(right));
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
        return evaluateInteger(globalContext,  queryContext,  aliasAndTuples);
    }
}
