package com.tibco.cep.query.stream.impl.expression.conversion;

import java.io.Serializable;
import java.util.Calendar;

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
public class CalendarToShortEvaluator
        implements ExpressionEvaluator, Serializable {


    protected ExpressionEvaluator calendarEvaluator;


    public CalendarToShortEvaluator(
            ExpressionEvaluator calendarEvaluator) {
        this.calendarEvaluator = calendarEvaluator;
    }


    public Object evaluate(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        final Calendar value = (Calendar) this.calendarEvaluator.evaluate(globalContext, queryContext, aliasAndTuples);
        if (null == value) {
            return null;
        }

        return (short) value.getTimeInMillis();
    }


    public boolean evaluateBoolean(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        final Calendar value = (Calendar) this.calendarEvaluator.evaluate(globalContext, queryContext, aliasAndTuples);
        return (null != value)
                && (0 != value.getTimeInMillis());
    }


    public double evaluateDouble(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        final Calendar value = (Calendar) this.calendarEvaluator.evaluate(globalContext, queryContext, aliasAndTuples);
        try {
            return (short) value.getTimeInMillis();
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
    }


    public float evaluateFloat(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        final Calendar value = (Calendar) this.calendarEvaluator.evaluate(globalContext, queryContext, aliasAndTuples);
        try {
            return (short) value.getTimeInMillis();
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
    }


    public int evaluateInteger(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        final Calendar value = (Calendar) this.calendarEvaluator.evaluate(globalContext, queryContext, aliasAndTuples);
        try {
            return (short) value.getTimeInMillis();
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
    }


    public long evaluateLong(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        final Calendar value = (Calendar) this.calendarEvaluator.evaluate(globalContext, queryContext, aliasAndTuples);
        try {
            return (short) value.getTimeInMillis();
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
    }


    public ExpressionEvaluator getCalendarEvaluator() {
        return this.calendarEvaluator;
    }


}
