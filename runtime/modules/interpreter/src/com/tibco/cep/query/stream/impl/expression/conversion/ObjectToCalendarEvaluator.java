package com.tibco.cep.query.stream.impl.expression.conversion;

import java.io.Serializable;
import java.util.Calendar;

import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.expression.ExpressionEvaluator;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.FixedKeyHashMap;
import com.tibco.cep.query.utils.TypeHelper;

/*
* Created by IntelliJ IDEA.
* User: nprade
* Date: Jul 29, 2008
* Time: 6:44:17 PM
*/
public class ObjectToCalendarEvaluator
        implements ExpressionEvaluator, Serializable {


    protected ExpressionEvaluator objectEvaluator;


    public ObjectToCalendarEvaluator(
            ExpressionEvaluator objectEvaluator) {
        this.objectEvaluator = objectEvaluator;
    }


    public Object evaluate(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        return TypeHelper.toDateTime(this.objectEvaluator.evaluate(globalContext, queryContext, aliasAndTuples));
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



    public ExpressionEvaluator getObjectEvaluator() {
        return this.objectEvaluator;
    }


}
