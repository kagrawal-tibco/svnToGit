package com.tibco.cep.query.stream.impl.expression.attribute;

import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.expression.ExpressionEvaluator;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.FixedKeyHashMap;
import com.tibco.cep.runtime.model.event.TimeEvent;

/*
* Created by IntelliJ IDEA.
* User: nprade
* Date: Jul 29, 2008
* Time: 4:50:13 PM
*/
public class ScheduledTimeAttributeEvaluator
        extends AttributeEvaluator {


    /**
     * @param containerEvaluator ExpressionEvaluator for the container of the attribute.
     */
    public ScheduledTimeAttributeEvaluator(ExpressionEvaluator containerEvaluator) {
        super(containerEvaluator);
    }


    public Object evaluate(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        final Object container = this.containerEvaluator.evaluate(globalContext, queryContext, aliasAndTuples);
        return ((TimeEvent) container).getScheduledTime();
    }


    public boolean evaluateBoolean(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        throw new UnsupportedOperationException();
    }


    public double evaluateDouble(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        return this.evaluateLong(globalContext, queryContext, aliasAndTuples);
    }


    public float evaluateFloat(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        return this.evaluateLong(globalContext, queryContext, aliasAndTuples);
    }


    public int evaluateInteger(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        return (int) this.evaluateLong(globalContext, queryContext, aliasAndTuples);
    }


    public long evaluateLong(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        final Object container = this.containerEvaluator.evaluate(globalContext, queryContext, aliasAndTuples);
        return ((TimeEvent) container).getScheduledTimeMillis();
    }


}
