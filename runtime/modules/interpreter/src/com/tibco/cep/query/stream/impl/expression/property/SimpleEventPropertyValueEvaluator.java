package com.tibco.cep.query.stream.impl.expression.property;

import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.expression.ExpressionEvaluator;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.FixedKeyHashMap;
import com.tibco.cep.runtime.model.event.SimpleEvent;

/*
* Created by IntelliJ IDEA.
* User: nprade
* Date: Jul 29, 2008
* Time: 5:47:59 PM
*/
public class SimpleEventPropertyValueEvaluator extends PropertyEvaluator {


    /**
     * @param containerEvaluator ExpressionEvaluator for the container of the attribute.
     * @param propertyName       String name of the property.
     */
    public SimpleEventPropertyValueEvaluator(ExpressionEvaluator containerEvaluator, String propertyName) {
        super(containerEvaluator, propertyName);
    }


    public Object evaluate(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        final Object container = this.containerEvaluator.evaluate(globalContext, queryContext, aliasAndTuples);
        try {
            return ((SimpleEvent) container).getProperty(this.propertyName);
        } catch (NoSuchFieldException e) {
            throw new IllegalArgumentException(e);
        }
    }


    public boolean evaluateBoolean(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        return (Boolean) this.evaluate(globalContext, queryContext, aliasAndTuples);
    }


    public double evaluateDouble(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        return ((Number) this.evaluate(globalContext, queryContext, aliasAndTuples)).doubleValue();
    }


    public float evaluateFloat(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        return ((Number) this.evaluate(globalContext, queryContext, aliasAndTuples)).floatValue();
    }


    public int evaluateInteger(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        return ((Number) this.evaluate(globalContext, queryContext, aliasAndTuples)).intValue();
    }


    public long evaluateLong(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        return ((Number) this.evaluate(globalContext, queryContext, aliasAndTuples)).longValue();
    }


}
