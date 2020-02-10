package com.tibco.cep.query.stream.impl.expression.numeric;

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
public class SubtractionEvaluator
        implements ExpressionEvaluator, Serializable {


    protected ExpressionEvaluator leftEvaluator;
    protected ExpressionEvaluator rightEvaluator;


    public SubtractionEvaluator(ExpressionEvaluator leftEvaluator, ExpressionEvaluator rightEvaluator) {
        this.leftEvaluator = leftEvaluator;
        this.rightEvaluator = rightEvaluator;
    }


    public Object evaluate(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        return this.evaluateDouble(globalContext, queryContext, aliasAndTuples);
    }


    public boolean evaluateBoolean(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        throw new UnsupportedOperationException();
    }


    public double evaluateDouble(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        final Number left = (Number) this.leftEvaluator.evaluate(globalContext, queryContext, aliasAndTuples);
        final Number right = (Number) this.rightEvaluator.evaluate(globalContext, queryContext, aliasAndTuples);
        return left.doubleValue() - right.doubleValue();
    }


    public float evaluateFloat(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        final double d = this.evaluateDouble(globalContext, queryContext, aliasAndTuples);
        return (float) d;
    }


    public int evaluateInteger(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        final double d = this.evaluateDouble(globalContext, queryContext, aliasAndTuples);
        return (int) d;
    }


    public long evaluateLong(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        final double d = this.evaluateDouble(globalContext, queryContext, aliasAndTuples);
        return (long) d;
    }
}
