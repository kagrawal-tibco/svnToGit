package com.tibco.cep.query.stream._join_.impl.join;

import com.tibco.cep.query.stream._join_.node.join.JoinTupleProducer;
import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.misc.IdGenerator;
import com.tibco.cep.query.stream.tuple.LiteTuple;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.FixedKeyHashMap;

/*
* Author: Ashwin Jayaprakash Date: Nov 20, 2008 Time: 1:22:30 PM
*/
public class MultiWayMergingJTP implements JoinTupleProducer {
    protected final TupleInput[] tupleInputs;

    protected final IdGenerator idGenerator;

    protected int[] tuplePositions;

    private final int resultArrayLength;

    /**
     * @param idGenerator
     * @param tupleInputs
     */
    public MultiWayMergingJTP(IdGenerator idGenerator, TupleInput... tupleInputs) {
        this.tupleInputs = tupleInputs;
        this.idGenerator = idGenerator;

        int totalColumns = 0;
        for (TupleInput input : tupleInputs) {
            int[] columnsToCopy = input.getColumnsToCopy();

            totalColumns = totalColumns + columnsToCopy.length;
        }
        this.resultArrayLength = totalColumns;
    }

    public void init(FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        tuplePositions = new int[tupleInputs.length];

        for (int i = 0; i < tupleInputs.length; i++) {
            TupleInput input = tupleInputs[i];
            String key = input.getKey();

            tuplePositions[i] = aliasAndTuples.getInternalPosition(key);
        }
    }

    public Tuple evaluate(GlobalContext globalContext, QueryContext queryContext,
                          FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        final TupleInput[] localTupleInputs = tupleInputs;
        final int[] localTuplePositions = tuplePositions;

        Object[] resultColumns = new Object[resultArrayLength];
        int resultColumnPos = 0;

        for (int i = 0; i < localTupleInputs.length; i++) {
            int tuplePosition = localTuplePositions[i];
            Tuple tuple = aliasAndTuples.getValueAtInternalPosition(tuplePosition);

            TupleInput tupleInput = localTupleInputs[i];
            int[] columnsToCopy = tupleInput.getColumnsToCopy();

            for (int position : columnsToCopy) {
                resultColumns[resultColumnPos] = tuple.getColumn(position);
                resultColumnPos++;
            }
        }

        Number id = idGenerator.generateNewId();

        return createTuple(id, resultColumns);
    }

    protected Tuple createTuple(Number id, Object[] columns) {
        return new LiteTuple(id, columns);
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

    //-------------

    public static class TupleInput {
        protected final String key;

        protected int[] columnsToCopy;

        public TupleInput(String key, int... columnsToCopy) {
            this.key = key;
            this.columnsToCopy = columnsToCopy;
        }

        public String getKey() {
            return key;
        }

        public int[] getColumnsToCopy() {
            return columnsToCopy;
        }
    }
}