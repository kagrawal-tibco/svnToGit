package com.tibco.cep.query.stream.impl.expression.modification;

import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.expression.ExpressionEvaluator;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.FixedKeyHashMap;

import java.io.Serializable;


/**
 * User: nprade
 * Date: 6/26/12
 * Time: 7:04 PM
 */
public class MultiAssignEvaluator
        implements ExpressionEvaluator, Serializable {


    private final ExpressionEvaluator returnedValueEvaluator;
    private final AssignEvaluator[] assignEvaluators;


    public MultiAssignEvaluator(
            ExpressionEvaluator returnedValueEvaluator,
            AssignEvaluator[] assignEvaluators) {

        this.returnedValueEvaluator = returnedValueEvaluator;
        this.assignEvaluators = assignEvaluators;
    }


    public Object evaluate(
            GlobalContext globalContext,
            QueryContext queryContext,
            FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {

        if (null != this.assignEvaluators) {
            for (final AssignEvaluator assignEvaluator : this.assignEvaluators) {
                if (null != assignEvaluator) {
                    assignEvaluator.evaluate(globalContext, queryContext, aliasAndTuples);
                }
            }
        }

        return returnedValueEvaluator.evaluate(globalContext, queryContext, aliasAndTuples);
    }


    public boolean evaluateBoolean(
            GlobalContext globalContext,
            QueryContext queryContext,
            FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {

        return (Boolean) this.evaluate(globalContext, queryContext, aliasAndTuples);
    }


    public double evaluateDouble(
            GlobalContext globalContext,
            QueryContext queryContext,
            FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {

        return (Double) this.evaluate(globalContext, queryContext, aliasAndTuples);
    }


    public float evaluateFloat(
            GlobalContext globalContext,
            QueryContext queryContext,
            FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {

        return (Float) this.evaluate(globalContext, queryContext, aliasAndTuples);
    }


    public int evaluateInteger(
            GlobalContext globalContext,
            QueryContext queryContext,
            FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {

        return (Integer) this.evaluate(globalContext, queryContext, aliasAndTuples);
    }


    public long evaluateLong(
            GlobalContext globalContext,
            QueryContext queryContext,
            FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {

        return (Long) this.evaluate(globalContext, queryContext, aliasAndTuples);
    }


}