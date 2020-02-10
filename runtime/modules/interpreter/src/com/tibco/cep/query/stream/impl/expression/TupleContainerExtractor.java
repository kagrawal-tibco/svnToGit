package com.tibco.cep.query.stream.impl.expression;

import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.FixedKeyHashMap;


public class TupleContainerExtractor extends TupleExtractor {

    final protected TupleExtractor tupleExtractor;


    public TupleContainerExtractor(
            TupleExtractor containedTupleExtractor,
            int sourceColumn) {
        super(sourceColumn);
        this.tupleExtractor = containedTupleExtractor;
    }


    public TupleContainerExtractor(
            TupleExtractor containedTupleExtractor,
            String sourceAlias) {
        super(sourceAlias);
        this.tupleExtractor = containedTupleExtractor;
    }


    public TupleExtractor getTupleExtractor() {
        return this.tupleExtractor;
    }


    public Object extractColumnAsObject(
            GlobalContext globalContext,
            QueryContext queryContext,
            Tuple sourceTuple) {

        final Tuple containedTuple = (Tuple) sourceTuple.getColumn(this.sourceColumn);
        return this.tupleExtractor.extractColumnAsObject(globalContext, queryContext, containedTuple);
    }


    public Tuple extractColumnAsTuple(
            GlobalContext globalContext,
            QueryContext queryContext,
            Tuple sourceTuple) {

        return (Tuple) this.extractColumnAsObject(globalContext, queryContext, sourceTuple);
    }


    public int extractColumnAsInteger(
            GlobalContext globalContext,
            QueryContext queryContext,
            Tuple sourceTuple) {
        
        final Tuple containedTuple = (Tuple) sourceTuple.getColumn(this.sourceColumn);
        return this.tupleExtractor.extractColumnAsInteger(globalContext, queryContext, containedTuple);
    }


    public float extractColumnAsFloat(
            GlobalContext globalContext,
            QueryContext queryContext,
            Tuple sourceTuple) {
        
        final Tuple containedTuple = (Tuple) sourceTuple.getColumn(this.sourceColumn);
        return this.tupleExtractor.extractColumnAsFloat(globalContext, queryContext, containedTuple);
    }


    public long extractColumnAsLong(
            GlobalContext globalContext,
            QueryContext queryContext,
            Tuple sourceTuple) {

        final Tuple containedTuple = (Tuple) sourceTuple.getColumn(this.sourceColumn);
        return this.tupleExtractor.extractColumnAsLong(globalContext, queryContext, containedTuple);
    }


    public double extractColumnAsDouble(
            GlobalContext globalContext,
            QueryContext queryContext,
            Tuple sourceTuple) {

        final Tuple containedTuple = (Tuple) sourceTuple.getColumn(this.sourceColumn);
        return this.tupleExtractor.extractColumnAsDouble(globalContext, queryContext, containedTuple);
    }


    protected Tuple extractTuple(
            GlobalContext globalContext,
            QueryContext queryContext,
            FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {

        final Tuple tuple = super.extractTuple(globalContext, queryContext, aliasAndTuples);
        return (Tuple) this.tupleExtractor.extractColumnAsObject(globalContext, queryContext, tuple);
    }

    
}
