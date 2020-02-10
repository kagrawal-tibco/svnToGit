package com.tibco.cep.query.stream.impl.expression.conversion;

import java.io.Serializable;

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
public class ObjectToDoubleEvaluator
        implements ExpressionEvaluator, Serializable {


    protected ExpressionEvaluator objectEvaluator;


    public ObjectToDoubleEvaluator(
            ExpressionEvaluator objectEvaluator) {
        this.objectEvaluator = objectEvaluator;
    }


    public Object evaluate(
            GlobalContext globalContext,
            QueryContext queryContext,
            FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {

        final Object value = this.objectEvaluator.evaluate(globalContext, queryContext, aliasAndTuples);
        if (null == value) {
            return null;
        }
        return TypeHelper.toDouble(value);
    }


    public boolean evaluateBoolean(
            GlobalContext globalContext,
            QueryContext queryContext,
            FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {

        final Object value = this.objectEvaluator.evaluate(globalContext, queryContext, aliasAndTuples);
        return (null != value)
            && (0 != TypeHelper.toDouble(value));
    }


    public double evaluateDouble(
            GlobalContext globalContext,
            QueryContext queryContext,
            FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {

        return TypeHelper.toDouble(
                this.objectEvaluator.evaluate(globalContext, queryContext, aliasAndTuples));
    }


    public float evaluateFloat(
            GlobalContext globalContext,
            QueryContext queryContext,
            FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {

        return TypeHelper.toDouble(
                this.objectEvaluator.evaluate(globalContext, queryContext, aliasAndTuples)).floatValue();
    }


    public int evaluateInteger(
            GlobalContext globalContext,
            QueryContext queryContext,
            FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {

        return TypeHelper.toDouble(
                this.objectEvaluator.evaluate(globalContext, queryContext, aliasAndTuples)).intValue();
    }


    public long evaluateLong(
            GlobalContext globalContext,
            QueryContext queryContext,
            FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {

        return TypeHelper.toDouble(
                this.objectEvaluator.evaluate(globalContext, queryContext, aliasAndTuples)).longValue();
    }



    public ExpressionEvaluator getObjectEvaluator() {
        return this.objectEvaluator;
    }


}
