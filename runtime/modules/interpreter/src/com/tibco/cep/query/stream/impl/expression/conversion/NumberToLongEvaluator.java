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
public class NumberToLongEvaluator
        implements ExpressionEvaluator, Serializable {


    protected ExpressionEvaluator numberEvaluator;


    public NumberToLongEvaluator(
            ExpressionEvaluator numberEvaluator) {
        this.numberEvaluator = numberEvaluator;
    }


    public Object evaluate(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        final Number number = (Number) this.numberEvaluator.evaluate(globalContext, queryContext, aliasAndTuples);
        if (null == number) {
            return null;
        }
        return number.longValue();
    }


    public boolean evaluateBoolean(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        final Number number = (Number) this.numberEvaluator.evaluate(globalContext, queryContext, aliasAndTuples);
        return (null == number)
                || (number.longValue() == 0);
    }


    public double evaluateDouble(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        final Number number = (Number) this.numberEvaluator.evaluate(globalContext, queryContext, aliasAndTuples);
        return number.longValue();
    }


    public float evaluateFloat(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        final Number number = (Number) this.numberEvaluator.evaluate(globalContext, queryContext, aliasAndTuples);
        return number.longValue();
    }


    public int evaluateInteger(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        final Number number = (Number) this.numberEvaluator.evaluate(globalContext, queryContext, aliasAndTuples);
        return (int) number.longValue();
    }


    public long evaluateLong(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        final Number number = (Number) this.numberEvaluator.evaluate(globalContext, queryContext, aliasAndTuples);
        return number.longValue();
    }


    public ExpressionEvaluator getNumberEvaluator() {
        return this.numberEvaluator;
    }


}
