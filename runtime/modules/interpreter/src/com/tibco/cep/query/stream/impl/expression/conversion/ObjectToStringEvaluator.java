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
public class ObjectToStringEvaluator
        implements ExpressionEvaluator, Serializable {


    protected ExpressionEvaluator objectEvaluator;


    public ObjectToStringEvaluator(
            ExpressionEvaluator calendarEvaluator) {
        this.objectEvaluator = calendarEvaluator;
    }


    public Object evaluate(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        final Object value = this.objectEvaluator.evaluate(globalContext, queryContext, aliasAndTuples);
        if (null == value) {
            return null;
        }

        return value.toString();
    }


    public boolean evaluateBoolean(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        final String value = (String) this.evaluate(globalContext, queryContext, aliasAndTuples);
        return (null != value)
                && Boolean.valueOf(value);
    }


    public double evaluateDouble(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        final String value = (String) this.evaluate(globalContext, queryContext, aliasAndTuples);
        return Double.valueOf(value);
    }


    public float evaluateFloat(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        final String value = (String) this.evaluate(globalContext, queryContext, aliasAndTuples);
        return Float.valueOf(value);
    }


    public int evaluateInteger(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        final String value = (String) this.evaluate(globalContext, queryContext, aliasAndTuples);
        return Integer.valueOf(value);
    }


    public long evaluateLong(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        final String value = (String) this.evaluate(globalContext, queryContext, aliasAndTuples);
        return Long.valueOf(value);
    }


    public ExpressionEvaluator getObjectEvaluator() {
        return this.objectEvaluator;
    }


}
