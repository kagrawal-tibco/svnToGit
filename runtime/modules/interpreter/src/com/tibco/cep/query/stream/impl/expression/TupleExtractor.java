package com.tibco.cep.query.stream.impl.expression;

import java.io.Serializable;

import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.FixedKeyHashMap;

/*
* Author: Ashwin Jayaprakash Date: Jun 10, 2008 Time: 3:07:16 PM
*/
public class TupleExtractor implements Serializable {
    private static final long serialVersionUID = 1L;

    protected int sourceColumn;

    protected String sourceAlias;

    /**
     * Used by all methods <b>except</b> {@link #extractTuple(com.tibco.cep.query.stream.context.GlobalContext,com.tibco.cep.query.stream.context.QueryContext,com.tibco.cep.query.stream.util.FixedKeyHashMap}.
     *
     * @param sourceColumn
     */
    public TupleExtractor(int sourceColumn) {
        this.sourceColumn = sourceColumn;
    }

    /**
     * Used only by {@link #extractTuple(com.tibco.cep.query.stream.context.GlobalContext,com.tibco.cep.query.stream.context.QueryContext,com.tibco.cep.query.stream.util.FixedKeyHashMap}.
     *
     * @param sourceAlias
     */
    public TupleExtractor(String sourceAlias) {
        this.sourceAlias = sourceAlias;
    }

    public int getSourceColumn() {
        return sourceColumn;
    }

    public String getSourceAlias() {
        return sourceAlias;
    }

    public Object extractColumnAsObject(GlobalContext globalContext, QueryContext queryContext,
                                        Tuple sourceTuple) {
        return sourceTuple.getColumn(sourceColumn);
    }

    public Tuple extractColumnAsTuple(GlobalContext globalContext, QueryContext queryContext,
                                      Tuple sourceTuple) {
        return (Tuple) extractColumnAsObject(globalContext, queryContext, sourceTuple);
    }

    public int extractColumnAsInteger(GlobalContext globalContext, QueryContext queryContext,
                                      Tuple sourceTuple) {
        return sourceTuple.getColumnAsInteger(sourceColumn);
    }

    public float extractColumnAsFloat(GlobalContext globalContext, QueryContext queryContext,
                                      Tuple sourceTuple) {
        return sourceTuple.getColumnAsFloat(sourceColumn);
    }

    public long extractColumnAsLong(GlobalContext globalContext, QueryContext queryContext,
                                    Tuple sourceTuple) {
        return sourceTuple.getColumnAsLong(sourceColumn);
    }

    public double extractColumnAsDouble(GlobalContext globalContext, QueryContext queryContext,
                                        Tuple sourceTuple) {
        return sourceTuple.getColumnAsDouble(sourceColumn);
    }

    protected Tuple extractTuple(GlobalContext globalContext, QueryContext queryContext,
                                 FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        return aliasAndTuples.get(sourceAlias);
    }
}
