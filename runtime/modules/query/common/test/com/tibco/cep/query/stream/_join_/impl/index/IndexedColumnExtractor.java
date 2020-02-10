package com.tibco.cep.query.stream._join_.impl.index;

import com.tibco.cep.query.stream._join_.node.index.IndexExtractor;
import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.FixedKeyHashMap;

/*
* Author: Ashwin Jayaprakash Date: Nov 20, 2008 Time: 1:21:30 PM
*/
public class IndexedColumnExtractor implements IndexExtractor {
    protected final String mapKey;

    protected final int tupleColumnNumber;

    public IndexedColumnExtractor(String mapKey, int tupleColumnNumber) {
        this.mapKey = mapKey;
        this.tupleColumnNumber = tupleColumnNumber;
    }

    public Comparable evaluate(GlobalContext globalContext, QueryContext queryContext,
                               FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        Tuple tuple = aliasAndTuples.get(mapKey);

        return (Comparable) tuple.getColumn(tupleColumnNumber);
    }

    /**
     * @param globalContext
     * @param queryContext
     * @param aliasAndTuples
     * @return
     * @throws UnsupportedOperationException
     */
    public boolean evaluateBoolean(GlobalContext globalContext, QueryContext queryContext,
                                   FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        throw new UnsupportedOperationException();
    }

    /**
     * @param globalContext
     * @param queryContext
     * @param aliasAndTuples
     * @return
     * @throws UnsupportedOperationException
     */
    public int evaluateInteger(GlobalContext globalContext, QueryContext queryContext,
                               FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        throw new UnsupportedOperationException();
    }

    /**
     * @param globalContext
     * @param queryContext
     * @param aliasAndTuples
     * @return
     * @throws UnsupportedOperationException
     */
    public long evaluateLong(GlobalContext globalContext, QueryContext queryContext,
                             FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        throw new UnsupportedOperationException();
    }

    /**
     * @param globalContext
     * @param queryContext
     * @param aliasAndTuples
     * @return
     * @throws UnsupportedOperationException
     */
    public float evaluateFloat(GlobalContext globalContext, QueryContext queryContext,
                               FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        throw new UnsupportedOperationException();
    }

    /**
     * @param globalContext
     * @param queryContext
     * @param aliasAndTuples
     * @return
     * @throws UnsupportedOperationException
     */
    public double evaluateDouble(GlobalContext globalContext, QueryContext queryContext,
                                 FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        throw new UnsupportedOperationException();
    }
}
