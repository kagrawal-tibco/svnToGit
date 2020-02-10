package com.tibco.cep.query.stream.impl.expression.conversion;

import java.io.Serializable;

import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.expression.ExpressionEvaluator;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.FixedKeyHashMap;

/*
* Created by IntelliJ IDEA.
* User: nprade
* Date: Jul 29, 2008
* Time: 6:44:17 PM
*/
public class CastEvaluator
        implements ExpressionEvaluator, Serializable {


    protected ExpressionEvaluator castedEvaluator;
    protected Class clazz;


    /**
     * @param castedEvaluator ExpressionEvaluator that will be casted.
     * @param clazz           Class to cast to.
     */
    public CastEvaluator(
            ExpressionEvaluator castedEvaluator,
            Class clazz) {
        this.castedEvaluator = castedEvaluator;
        this.clazz = clazz;
    }


    public Object evaluate(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        final Object casted = this.castedEvaluator.evaluate(globalContext, queryContext, aliasAndTuples);
        return this.clazz.cast(casted);
    }


    public boolean evaluateBoolean(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        return (Boolean) this.evaluate(globalContext, queryContext, aliasAndTuples);
    }


    public double evaluateDouble(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        return (Double) this.evaluate(globalContext, queryContext, aliasAndTuples);
    }


    public float evaluateFloat(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        return (Float) this.evaluate(globalContext, queryContext, aliasAndTuples);
    }


    public int evaluateInteger(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        return (Integer) this.evaluate(globalContext, queryContext, aliasAndTuples);
    }


    public long evaluateLong(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        return (Long) this.evaluate(globalContext, queryContext, aliasAndTuples);
    }


    public ExpressionEvaluator getCastedEvaluator() {
        return this.castedEvaluator;
    }


}
