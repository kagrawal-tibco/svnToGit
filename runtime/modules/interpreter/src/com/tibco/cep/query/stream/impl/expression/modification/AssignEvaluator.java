package com.tibco.cep.query.stream.impl.expression.modification;

import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.expression.ExpressionEvaluator;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.FixedKeyHashMap;

import java.io.Serializable;


/*
* Created by IntelliJ IDEA.
* User: nprade
* Date: Aug 14, 2008
* Time: 4:08:45 PM
*/
public abstract class AssignEvaluator
        implements ExpressionEvaluator, Serializable {


    protected ExpressionEvaluator leftEvaluator;
    protected ExpressionEvaluator rightEvaluator;


    public AssignEvaluator(
            ExpressionEvaluator leftEvaluator,
            ExpressionEvaluator rightEvaluator) {
        this.leftEvaluator = leftEvaluator;
        this.rightEvaluator = rightEvaluator;
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
