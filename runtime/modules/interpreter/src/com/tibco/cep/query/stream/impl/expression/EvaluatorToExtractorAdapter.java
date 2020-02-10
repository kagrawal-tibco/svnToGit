package com.tibco.cep.query.stream.impl.expression;

import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.expression.ExpressionEvaluator;
import com.tibco.cep.query.stream.expression.TupleValueExtractor;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.FixedKeyHashMap;

import java.io.Serializable;

/*
* Author: Ashwin Jayaprakash Date: Jun 13, 2008 Time: 3:10:06 PM
*/
public class EvaluatorToExtractorAdapter implements TupleValueExtractor, Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * {@value} used in {@link #adapterMap}.
     */
    public static final String FIXED_MAP_ADAPTER_KEY = "$_row_$";

    protected ExpressionEvaluator evaluator;

    static final ThreadLocal<FixedKeyHashMap<String,Tuple>> instances = new ThreadLocal<FixedKeyHashMap<String, Tuple>>();

    public EvaluatorToExtractorAdapter(ExpressionEvaluator evaluator) {
        this.evaluator = evaluator;
    }

    public ExpressionEvaluator getEvaluator() {
        return evaluator;
    }

    static FixedKeyHashMap<String,Tuple> adapterMap(){
        FixedKeyHashMap<String,Tuple> map = instances.get();
        if(map == null){
            map = new FixedKeyHashMap<String, Tuple>(FIXED_MAP_ADAPTER_KEY);

            instances.set(map);
        }

        return map;
    }

    public Object extract(GlobalContext globalContext, QueryContext queryContext, Tuple tuple) {
        FixedKeyHashMap<String,Tuple> adapterMap = adapterMap();

        adapterMap.put(FIXED_MAP_ADAPTER_KEY, tuple);

        Object retVal = null;
        try {
            retVal = evaluator.evaluate(globalContext, queryContext, adapterMap);
        }
        catch (RuntimeException e) {
            if(e instanceof NullPointerException || e.getCause() instanceof NullPointerException){
                //Do nothing
            }else{
                throw e;
            }
        }

        adapterMap.clear();

        return retVal;
    }

    public int extractInteger(GlobalContext globalContext, QueryContext queryContext, Tuple tuple) {
        FixedKeyHashMap<String,Tuple> adapterMap = adapterMap();

        adapterMap.put(FIXED_MAP_ADAPTER_KEY, tuple);

        int retVal = evaluator.evaluateInteger(globalContext, queryContext, adapterMap);

        adapterMap.clear();

        return retVal;
    }

    public long extractLong(GlobalContext globalContext, QueryContext queryContext, Tuple tuple) {
        FixedKeyHashMap<String,Tuple> adapterMap = adapterMap();

        adapterMap.put(FIXED_MAP_ADAPTER_KEY, tuple);

        long retVal = evaluator.evaluateLong(globalContext, queryContext, adapterMap);

        adapterMap.clear();

        return retVal;
    }

    public float extractFloat(GlobalContext globalContext, QueryContext queryContext, Tuple tuple) {
        FixedKeyHashMap<String,Tuple> adapterMap = adapterMap();

        adapterMap.put(FIXED_MAP_ADAPTER_KEY, tuple);

        float retVal = evaluator.evaluateFloat(globalContext, queryContext, adapterMap);

        adapterMap.clear();

        return retVal;
    }

    public double extractDouble(GlobalContext globalContext, QueryContext queryContext,
                                Tuple tuple) {
        FixedKeyHashMap<String,Tuple> adapterMap = adapterMap();

        adapterMap.put(FIXED_MAP_ADAPTER_KEY, tuple);

        double retVal = evaluator.evaluateDouble(globalContext, queryContext, adapterMap);

        adapterMap.clear();

        return retVal;
    }
}
