package com.tibco.cep.query.stream.impl.aggregate;

import java.util.HashMap;

import com.tibco.cep.query.stream.aggregate.AggregateCreator;
import com.tibco.cep.query.stream.aggregate.Aggregator;
import com.tibco.cep.query.stream.context.DefaultGlobalContext;
import com.tibco.cep.query.stream.context.DefaultQueryContext;
import com.tibco.cep.query.stream.expression.TupleValueExtractor;
import com.tibco.cep.query.stream.tuple.Tuple;

/*
 * Author: Ashwin Jayaprakash Date: Oct 9, 2007 Time: 3:19:19 PM
 */

/**
 * Sums up the values of all Tuples.
 */
public class IntegerSumDistinctAggregator implements Aggregator {
    public static final Creator CREATOR = new Creator();

    protected boolean recordAddsOnly;

    protected TupleValueExtractor extractor;

    protected int sum;

    protected HashMap<Integer, Integer> distinctValueAndCounts;

    protected HashMap<Number, Integer> extractedValues;

    public IntegerSumDistinctAggregator() {
        this.distinctValueAndCounts = new HashMap<Integer, Integer>();
    }

    /**
     * @return Can be <code>null</code>.
     */
    public TupleValueExtractor getExtractor() {
        return extractor;
    }

    public void setExtractor(TupleValueExtractor extractor) {
        this.extractor = extractor;
    }

    public boolean isRecordAddsOnly() {
        return recordAddsOnly;
    }

    public void setRecordAddsOnly(boolean recordAddsOnly) {
        this.recordAddsOnly = recordAddsOnly;

        if (recordAddsOnly == false) {
            this.extractedValues = new HashMap<Number, Integer>();
        }
        else {
            this.extractedValues = null;
        }
    }

    //----------

    public void add(DefaultGlobalContext globalContext, DefaultQueryContext queryContext, Tuple tuple) {
        int i = extractor.extractInteger(globalContext, queryContext, tuple);
        Integer objI = i;

        if (recordAddsOnly == false) {
            extractedValues.put(tuple.getId(), objI);
        }

        Integer currentCount = distinctValueAndCounts.get(objI);
        if (currentCount == null) {
            distinctValueAndCounts.put(objI, 1);
        }
        else {
            distinctValueAndCounts.put(objI, currentCount + 1);

            return;
        }

        sum = sum + i;
    }

    public void remove(DefaultGlobalContext globalContext, DefaultQueryContext queryContext, Tuple tuple) {
        Integer i = extractedValues.remove(tuple.getId());

        if (i == null) {
            return;
        }

        Integer currentCount = distinctValueAndCounts.remove(i);
        if (currentCount > 1) {
            //Put it back after decrementing.
            distinctValueAndCounts.put(i, currentCount - 1);

            return;
        }

        sum = sum - i;
    }

    public Integer calculateAggregate() throws Exception {
        return sum;
    }

    public int calculateAggregateAsInteger() throws Exception {
        return sum;
    }

    public long calculateAggregateAsLong() throws Exception {
        throw new UnsupportedOperationException();
    }

    public float calculateAggregateAsFloat() throws Exception {
        throw new UnsupportedOperationException();
    }

    public double calculateAggregateAsDouble() throws Exception {
        throw new UnsupportedOperationException();
    }

// ----------

    public static class Creator implements AggregateCreator {
        public final IntegerSumDistinctAggregator createInstance() {
            return new IntegerSumDistinctAggregator();
        }
    }
}