package com.tibco.cep.query.stream.impl.expression.reflection;

import java.io.Serializable;

import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.expression.ExpressionEvaluator;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.FixedKeyHashMap;


/**
 * User: nprade
 * Date: 9/9/11
 * Time: 6:02 PM
 */
public class InstanceOfEvaluator
        implements ExpressionEvaluator, Serializable {


    private ExpressionEvaluator instanceEvaluator;
    private ExpressionEvaluator classEvaluator;


    public InstanceOfEvaluator(
            ExpressionEvaluator instanceEvaluator,
            ExpressionEvaluator classEvaluator) {
        this.instanceEvaluator = instanceEvaluator;
        this.classEvaluator = classEvaluator;

    }


    public Object evaluate(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        return this.evaluateBoolean(globalContext, queryContext, aliasAndTuples);
    }


    public boolean evaluateBoolean(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        final Object instance = this.instanceEvaluator.evaluate(globalContext, queryContext, aliasAndTuples);
        final Class clazz = (Class) this.classEvaluator.evaluate(globalContext, queryContext, aliasAndTuples);
        return instance.getClass().isAssignableFrom(clazz);
    }


    public double evaluateDouble(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        throw new UnsupportedOperationException();
    }


    public float evaluateFloat(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        throw new UnsupportedOperationException();
    }


    public int evaluateInteger(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        throw new UnsupportedOperationException();
    }


    public long evaluateLong(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        throw new UnsupportedOperationException();
    }


}
