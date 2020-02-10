package com.tibco.cep.query.stream.impl.expression.conversion;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Calendar;

import com.tibco.cep.query.model.DateFormatProvider;
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
public class StringToCalendarEvaluator
        implements ExpressionEvaluator, Serializable {


    protected ExpressionEvaluator stringEvaluator;


    public StringToCalendarEvaluator(
            ExpressionEvaluator stringEvaluator) {
        this.stringEvaluator = stringEvaluator;
    }


    public Object evaluate(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        final String s = (String) this.stringEvaluator.evaluate(globalContext, queryContext, aliasAndTuples);
        if (null == s) {
            return null;
        }
        final Calendar c = Calendar.getInstance();
        try {
            c.setTime(DateFormatProvider.getInstance().getDateFormat().parse(s));
        } catch (ParseException e) {
            try {
                c.setTimeInMillis(Long.parseLong(s));
            } catch (NumberFormatException nfe) {
                throw new IllegalArgumentException(e);
            }
        }
        return c;
    }


    public boolean evaluateBoolean(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        throw new UnsupportedOperationException();
    }


    public double evaluateDouble(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        final Calendar c = (Calendar) this.evaluate(globalContext, queryContext, aliasAndTuples);
        return c.getTimeInMillis();
    }


    public float evaluateFloat(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        return (float) this.evaluateDouble(globalContext, queryContext, aliasAndTuples);
    }


    public int evaluateInteger(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        return (int) this.evaluateDouble(globalContext, queryContext, aliasAndTuples);
    }


    public long evaluateLong(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        return (long) this.evaluateDouble(globalContext, queryContext, aliasAndTuples);
    }


    public ExpressionEvaluator getStringEvaluator() {
        return this.stringEvaluator;
    }


}
