package com.tibco.cep.query.stream.group;

import java.util.LinkedHashMap;

import com.tibco.cep.query.stream.misc.IdGenerator;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.tuple.TupleInfo;

/*
 * Author: Ashwin Jayaprakash Date: Oct 10, 2007 Time: 12:03:15 PM
 */

/**
 * Combines Aggregate & Group columns to produce a combination column. The Aggregates are optional -
 * i.e can be empty ({@link #hasAggregates()}).
 */
public class GroupAggregateTransformer {
    protected TupleInfo outputInfo;

    protected int[] finalResultPositionsOfGroups;

    protected int[] finalResultPositionsOfAggregates;

    protected int finalResultRowLength;

    private IdGenerator idGenerator;

    private Mode mode;

    /**
     * @param outputInfo
     * @param groupColumnsToFinalResultPositions
     *                   Keys are in the same order as they appear in the Group column.
     * @param aggregateColumnsToFinalResultPositions
     *                   Can be empty. If not empty, Keys are in the same order as they appear in
     *                   the Aggregate columns.
     */
    public GroupAggregateTransformer(TupleInfo outputInfo,
                                     LinkedHashMap<String, Integer> groupColumnsToFinalResultPositions,
                                     LinkedHashMap<String, Integer> aggregateColumnsToFinalResultPositions) {
        this.outputInfo = outputInfo;

        this.finalResultPositionsOfGroups = new int[groupColumnsToFinalResultPositions.size()];
        int x = 0;
        for (String key : groupColumnsToFinalResultPositions.keySet()) {
            this.finalResultPositionsOfGroups[x] = groupColumnsToFinalResultPositions.get(key);

            x++;
        }

        this.finalResultPositionsOfAggregates =
                new int[aggregateColumnsToFinalResultPositions.size()];
        x = 0;
        for (String key : aggregateColumnsToFinalResultPositions.keySet()) {
            this.finalResultPositionsOfAggregates[x] =
                    aggregateColumnsToFinalResultPositions.get(key);

            x++;
        }

        this.finalResultRowLength = this.finalResultPositionsOfAggregates.length +
                this.finalResultPositionsOfGroups.length;

        if (finalResultPositionsOfAggregates.length == 0) {
            this.mode = Mode.USE_GROUP;
        }
        //Use the Aggregate columns as-is since there is no Group.
        else if (finalResultPositionsOfGroups.length == 0) {
            this.mode = Mode.USE_AGGREGATE;
        }
        else {
            this.mode = Mode.USE_ALL;
        }
    }

    public boolean hasAggregates() {
        return finalResultPositionsOfAggregates.length > 0;
    }

    public TupleInfo getOutputInfo() {
        return outputInfo;
    }

    public void init(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    // ----------

    /**
     * Creates a transformed tuple with a new Tuple-Id.
     *
     * @param groupColumns
     * @param aggregateColumns
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @see #transform(Object[], Object[], Number) .
     */
    public Tuple transform(Object[] groupColumns, Object[] aggregateColumns)
            throws InstantiationException, IllegalAccessException {
        return transform(groupColumns, aggregateColumns, null);
    }

    /**
     * @param groupColumns
     * @param aggregateColumns <code>null</code> if {@link #hasAggregates()} return
     *                         <code>false</code>.
     * @param useThisTupleId   Can be <code>null</code>. If so, then sets a new Id.
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public Tuple transform(Object[] groupColumns, Object[] aggregateColumns, Number useThisTupleId)
            throws InstantiationException, IllegalAccessException {
        Object[] resultRowColumns = null;

        switch (mode) {
            //Use the Aggregate columns as-is since there is no Group.
            case USE_AGGREGATE:
                resultRowColumns = aggregateColumns;
                break;

                //Use the Group columns as-is since there is no Aggregate.
            case USE_GROUP:
                resultRowColumns = groupColumns;
                break;

            case USE_ALL:
                resultRowColumns = new Object[finalResultRowLength];

                /*
                * Copy all the Group and Aggregate columns into their respective
                * positions in the result.
                */

                final int[] groupPositions = finalResultPositionsOfGroups;
                final int[] aggregatePositions = finalResultPositionsOfAggregates;

                // Will not have any Shared Objects. All references are direct.

                for (int i = 0; i < groupColumns.length; i++) {
                    int resultPos = groupPositions[i];
                    resultRowColumns[resultPos] = groupColumns[i];
                }

                for (int i = 0; i < aggregateColumns.length; i++) {
                    int resultPos = aggregatePositions[i];
                    resultRowColumns[resultPos] = aggregateColumns[i];
                }

                break;
        }

        //--------

        if (useThisTupleId == null) {
            useThisTupleId = idGenerator.generateNewId();
        }
        Tuple resultTuple = outputInfo.createTuple(useThisTupleId);

        resultTuple.setColumns(resultRowColumns);

        return resultTuple;
    }

    public void discard() {
        outputInfo = null;

        finalResultPositionsOfGroups = null;
        finalResultPositionsOfAggregates = null;

        idGenerator = null;

        mode = null;
    }

    //------------

    protected static enum Mode {
        USE_GROUP, USE_AGGREGATE, USE_ALL
    }
}
