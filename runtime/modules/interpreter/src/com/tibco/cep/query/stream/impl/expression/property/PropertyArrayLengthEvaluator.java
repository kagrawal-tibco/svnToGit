package com.tibco.cep.query.stream.impl.expression.property;

import java.io.Serializable;

import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.expression.ExpressionEvaluator;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.FixedKeyHashMap;
import com.tibco.cep.runtime.model.element.PropertyArray;


/*
* Created by IntelliJ IDEA.
* User: nprade
* Date: Jul 29, 2008
* Time: 5:47:59 PM
*/
public class PropertyArrayLengthEvaluator
        implements ExpressionEvaluator, Serializable {


    private ExpressionEvaluator propertyArrayEvaluator;


    /**
     * @param propertyArrayEvaluator ExpressionEvaluator for the PropertyArray.
     */
    public PropertyArrayLengthEvaluator(
            ExpressionEvaluator propertyArrayEvaluator) {
        this.propertyArrayEvaluator = propertyArrayEvaluator;
    }


    public Object evaluate(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        return this.evaluateInteger(globalContext, queryContext, aliasAndTuples) ;
    }


    public boolean evaluateBoolean(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        throw new UnsupportedOperationException();
    }


    public double evaluateDouble(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        return this.evaluateInteger(globalContext, queryContext, aliasAndTuples) ;
    }


    public float evaluateFloat(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        return this.evaluateInteger(globalContext, queryContext, aliasAndTuples) ;
    }


    public int evaluateInteger(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        final PropertyArray array = (PropertyArray)
                this.propertyArrayEvaluator.evaluate(globalContext, queryContext, aliasAndTuples);
        return array.length();
    }


    public long evaluateLong(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        return this.evaluateInteger(globalContext, queryContext, aliasAndTuples) ;
    }


}
