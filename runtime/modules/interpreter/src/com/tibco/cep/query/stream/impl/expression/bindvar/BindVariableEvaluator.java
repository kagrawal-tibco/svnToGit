package com.tibco.cep.query.stream.impl.expression.bindvar;

import java.io.Serializable;

import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.expression.ExpressionEvaluator;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.FixedKeyHashMap;

/*
* Created by IntelliJ IDEA.
* User: nprade
* Date: Aug 14, 2008
* Time: 4:08:45 PM
*/
public class BindVariableEvaluator
        implements ExpressionEvaluator, Serializable {


    protected String name;


    public BindVariableEvaluator(String name) {
        this.name = "BV$" + name;
    }

    public String getName() {
        return name;
    }

    public Object evaluate(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        return queryContext.getGenericStore().get(this.name);
    }


    public boolean evaluateBoolean(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        return (Boolean) queryContext.getGenericStore().get(this.name);
    }


    public double evaluateDouble(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        return (Double) queryContext.getGenericStore().get(this.name);
    }


    public float evaluateFloat(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        return (Float) queryContext.getGenericStore().get(this.name);
    }


    public int evaluateInteger(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        return (Integer) queryContext.getGenericStore().get(this.name);
    }


    public long evaluateLong(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        return (Long) queryContext.getGenericStore().get(this.name);
    }
}
