package com.tibco.cep.query.stream.impl.expression;

import java.io.Serializable;

import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.expression.ExpressionEvaluator;
import com.tibco.cep.query.stream.expression.TupleValueExtractor;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.FixedKeyHashMap;

/*
* Author: Ashwin Jayaprakash Date: Jun 13, 2008 Time: 3:10:06 PM
*/
public class ExtractorToEvaluatorAdapter implements ExpressionEvaluator, Serializable {
    private static final long serialVersionUID = 1L;

    protected TupleValueExtractor extractor;

    public ExtractorToEvaluatorAdapter(TupleValueExtractor extractor, int fixedKeyPosition) {
        this.extractor = extractor;
    }

    public TupleValueExtractor getExtractor() {
        return extractor;
    }

    public Object evaluate(GlobalContext globalContext, QueryContext queryContext,
                           FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        Tuple tuple = aliasAndTuples.get(EvaluatorToExtractorAdapter.FIXED_MAP_ADAPTER_KEY);

        return extractor.extract(globalContext, queryContext, tuple);
    }

    public boolean evaluateBoolean(GlobalContext globalContext, QueryContext queryContext,
                                   FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        Tuple tuple = aliasAndTuples.get(EvaluatorToExtractorAdapter.FIXED_MAP_ADAPTER_KEY);

        Boolean b = (Boolean) extractor.extract(globalContext, queryContext, tuple);

        return b;
    }

    public int evaluateInteger(GlobalContext globalContext, QueryContext queryContext,
                               FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        Tuple tuple = aliasAndTuples.get(EvaluatorToExtractorAdapter.FIXED_MAP_ADAPTER_KEY);

        return extractor.extractInteger(globalContext, queryContext, tuple);
    }

    public long evaluateLong(GlobalContext globalContext, QueryContext queryContext,
                             FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        Tuple tuple = aliasAndTuples.get(EvaluatorToExtractorAdapter.FIXED_MAP_ADAPTER_KEY);

        return extractor.extractLong(globalContext, queryContext, tuple);
    }

    public float evaluateFloat(GlobalContext globalContext, QueryContext queryContext,
                               FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        Tuple tuple = aliasAndTuples.get(EvaluatorToExtractorAdapter.FIXED_MAP_ADAPTER_KEY);

        return extractor.extractFloat(globalContext, queryContext, tuple);
    }

    public double evaluateDouble(GlobalContext globalContext, QueryContext queryContext,
                                 FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        Tuple tuple = aliasAndTuples.get(EvaluatorToExtractorAdapter.FIXED_MAP_ADAPTER_KEY);

        return extractor.extractDouble(globalContext, queryContext, tuple);
    }
}