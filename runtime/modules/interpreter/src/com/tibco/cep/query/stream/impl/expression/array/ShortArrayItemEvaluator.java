package com.tibco.cep.query.stream.impl.expression.array;

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
public class ShortArrayItemEvaluator
        extends ArrayItemEvaluator {


    public ShortArrayItemEvaluator(ExpressionEvaluator arrayEvaluator, ExpressionEvaluator indexEvaluator) {
        super(arrayEvaluator, indexEvaluator);
    }


    public Object evaluate(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        final short[] array = (short[]) this.arrayEvaluator.evaluate(globalContext, queryContext, aliasAndTuples);
        final int index = this.indexEvaluator.evaluateInteger(globalContext, queryContext, aliasAndTuples);
        return array[index];
    }


    public boolean evaluateBoolean(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        throw new UnsupportedOperationException();
    }


    public double evaluateDouble(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        return this.evaluateInteger(globalContext, queryContext, aliasAndTuples);
    }


    public float evaluateFloat(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        return this.evaluateInteger(globalContext, queryContext, aliasAndTuples);
    }


    public int evaluateInteger(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        final short[] array = (short[]) this.arrayEvaluator.evaluate(globalContext, queryContext, aliasAndTuples);
        final int index = this.indexEvaluator.evaluateInteger(globalContext, queryContext, aliasAndTuples);
        return array[index];
    }


    public long evaluateLong(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        return this.evaluateInteger(globalContext, queryContext, aliasAndTuples);
    }


}
