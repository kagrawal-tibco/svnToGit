package com.tibco.cep.query.stream.impl.expression;

import java.io.Serializable;

import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.expression.ExpressionEvaluator;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.FixedKeyHashMap;

public class TupleExtractorToEvaluatorAdapter implements ExpressionEvaluator, Serializable {


    private static final long serialVersionUID = 1L;

    protected final TupleExtractor extractor;
    protected final String key;

    public TupleExtractorToEvaluatorAdapter(TupleExtractor extractor, String key) {

        this.extractor = extractor;
        this.key = key;
    }


    public Object evaluate(GlobalContext globalContext, QueryContext queryContext,
                           FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        final Tuple tuple =  this.getTuple(aliasAndTuples);
        return this.extractor.extractColumnAsObject(globalContext, queryContext, tuple);
    }


    public boolean evaluateBoolean(GlobalContext globalContext, QueryContext queryContext,
                                   FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        final Tuple tuple = this.getTuple(aliasAndTuples);
        final Object o = this.extractor.extractColumnAsObject(globalContext, queryContext, tuple);
        return (Boolean) o;
    }


    public int evaluateInteger(GlobalContext globalContext, QueryContext queryContext,
                               FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        final Tuple tuple = this.getTuple(aliasAndTuples);
        return this.extractor.extractColumnAsInteger(globalContext, queryContext, tuple);
    }


    public long evaluateLong(GlobalContext globalContext, QueryContext queryContext,
                             FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        final Tuple tuple = this.getTuple(aliasAndTuples);
        return this.extractor.extractColumnAsLong(globalContext, queryContext, tuple);
    }


    public float evaluateFloat(GlobalContext globalContext, QueryContext queryContext,
                               FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        final Tuple tuple = this.getTuple(aliasAndTuples);
        return this.extractor.extractColumnAsFloat(globalContext, queryContext, tuple);
    }


    public double evaluateDouble(GlobalContext globalContext, QueryContext queryContext,
                                 FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        final Tuple tuple = this.getTuple(aliasAndTuples);
        return this.extractor.extractColumnAsDouble(globalContext, queryContext, tuple);
    }


    private Tuple getTuple(FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        Tuple t = aliasAndTuples.get(this.key);

        if (t == null) {
            String[] fks = aliasAndTuples.getFixedKeys();
            if (fks.length == 1) {
                t = aliasAndTuples.get(fks[0]);
            }
        }

        return t;
    }


    public TupleExtractor getTupleExtractor() {
        return this.extractor;
    }




}
