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
public class StringToDoubleEvaluator
        implements ExpressionEvaluator, Serializable {


    protected ExpressionEvaluator stringEvaluator;


    public StringToDoubleEvaluator(
            ExpressionEvaluator stringEvaluator) {
        this.stringEvaluator = stringEvaluator;
    }


    public Object evaluate(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        final String s = (String) this.stringEvaluator.evaluate(globalContext, queryContext, aliasAndTuples);
        if (null == s) {
            return null;
        }
        try {
            return Double.valueOf(s);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
    }


    public boolean evaluateBoolean(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        final String s = (String) this.stringEvaluator.evaluate(globalContext, queryContext, aliasAndTuples);
        if (null == s) {
            return false;
        }
        try {
            return 0 != Double.valueOf(s);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
    }


    public double evaluateDouble(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        final String s = (String) this.stringEvaluator.evaluate(globalContext, queryContext, aliasAndTuples);
        try {
            return Double.valueOf(s);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
    }


    public float evaluateFloat(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        final String s = (String) this.stringEvaluator.evaluate(globalContext, queryContext, aliasAndTuples);
        try {
            return Double.valueOf(s).floatValue();
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
    }


    public int evaluateInteger(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        final String s = (String) this.stringEvaluator.evaluate(globalContext, queryContext, aliasAndTuples);
        try {
            return Double.valueOf(s).intValue();
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
    }


    public long evaluateLong(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        final String s = (String) this.stringEvaluator.evaluate(globalContext, queryContext, aliasAndTuples);
        try {
            return Double.valueOf(s).longValue();
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
    }


    public ExpressionEvaluator getStringEvaluator() {
        return this.stringEvaluator;
    }


}
