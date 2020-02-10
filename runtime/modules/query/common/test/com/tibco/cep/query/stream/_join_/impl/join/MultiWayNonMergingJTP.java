package com.tibco.cep.query.stream._join_.impl.join;

import com.tibco.cep.query.stream._join_.node.join.JoinTupleProducer;
import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.join.JoinedTuple;
import com.tibco.cep.query.stream.join.LiteJoinedTuple;
import com.tibco.cep.query.stream.misc.IdGenerator;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.FixedKeyHashMap;

/*
* Author: Ashwin Jayaprakash Date: Nov 20, 2008 Time: 1:22:30 PM
*/
public class MultiWayNonMergingJTP implements JoinTupleProducer {
    protected final String[] keys;

    protected final IdGenerator idGenerator;

    protected int[] tuplePositions;

    /**
     * @param idGenerator
     * @param keys
     */
    public MultiWayNonMergingJTP(IdGenerator idGenerator, String... keys) {
        this.keys = keys;
        this.idGenerator = idGenerator;
    }

    public String[] getKeys() {
        return keys;
    }

    public void init(FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        tuplePositions = new int[keys.length];

        for (int i = 0; i < keys.length; i++) {
            tuplePositions[i] = aliasAndTuples.getInternalPosition(keys[i]);
        }
    }

    public Tuple evaluate(GlobalContext globalContext, QueryContext queryContext,
                          FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        Object[] resultColumns = new Object[tuplePositions.length];

        for (int i = 0; i < tuplePositions.length; i++) {
            int tuplePosition = tuplePositions[i];

            Tuple tuple = aliasAndTuples.getValueAtInternalPosition(tuplePosition);

            resultColumns[i] = tuple;
        }

        Number id = idGenerator.generateNewId();

        return createTuple(id, resultColumns);
    }

    protected JoinedTuple createTuple(Number id, Object[] columns) {
        return new LiteJoinedTuple(id, columns);
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