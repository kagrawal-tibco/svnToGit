package com.tibco.cep.query.stream.impl.expression.bool;

import java.io.Serializable;

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
public class OrEvaluator
        implements ExpressionEvaluator, Serializable {


    protected ExpressionEvaluator[] childEvaluators;

    public ExpressionEvaluator[] getOperands() {
        return childEvaluators;
    }

    public OrEvaluator(ExpressionEvaluator leftEvaluator, ExpressionEvaluator rightEvaluator) {
        this(new ExpressionEvaluator[]{leftEvaluator, rightEvaluator});
    }


    public OrEvaluator(ExpressionEvaluator[] expressionEvaluators) {
        this.childEvaluators = expressionEvaluators;
    }


    /**
     * @param globalContext
     * @param queryContext
     * @param aliasAndTuples
     * @return
     */
    public Object evaluate(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        return evaluateBoolean(globalContext,  queryContext,  aliasAndTuples);
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
        for (ExpressionEvaluator evaluator : this.childEvaluators) {
            if (evaluator.evaluateBoolean(globalContext, queryContext, aliasAndTuples)) {
                return true;
            }
        }
        return false;
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
