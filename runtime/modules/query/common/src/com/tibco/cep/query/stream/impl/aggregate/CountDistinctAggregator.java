package com.tibco.cep.query.stream.impl.aggregate;

import java.util.HashMap;

import com.tibco.cep.query.stream.aggregate.AggregateCreator;
import com.tibco.cep.query.stream.aggregate.Aggregator;
import com.tibco.cep.query.stream.context.DefaultGlobalContext;
import com.tibco.cep.query.stream.context.DefaultQueryContext;
import com.tibco.cep.query.stream.expression.TupleValueExtractor;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.FixedKeys;

/*
 * Author: Ashwin Jayaprakash Date: Oct 9, 2007 Time: 11:27:33 AM
 */

/**
 * Counts the number of Tuples. Outputs <code>int</code>.
 */
public class CountDistinctAggregator implements Aggregator {
    public static final Creator CREATOR = new Creator();

    protected static final Object NULL_VALUE = FixedKeys.NULL;

    protected boolean recordAddsOnly;

    protected TupleValueExtractor extractor;

    protected int count;

    protected HashMap<Object, Integer> distinctValueAndCounts;

    protected HashMap<Number, Object> extractedValues;

    public CountDistinctAggregator() {
        this.distinctValueAndCounts = new HashMap<Object, Integer>();
    }

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
            this.extractedValues = new HashMap<Number, Object>();
        }
        else {
            this.extractedValues = null;
        }
    }

    //-----------

    public void add(DefaultGlobalContext globalContext, DefaultQueryContext queryContext, Tuple tuple) {
        Object obj = extractor.extract(globalContext, queryContext, tuple);

        if (obj == null) {
            obj = NULL_VALUE;
        }

        if (recordAddsOnly == false) {
            extractedValues.put(tuple.getId(), obj);
        }

        Integer currentCount = distinctValueAndCounts.get(obj);
        if (currentCount == null) {
            distinctValueAndCounts.put(obj, 1);
        }
        else {
            distinctValueAndCounts.put(obj, currentCount + 1);

            return;
        }

        count++;
    }

    public void remove(DefaultGlobalContext globalContext, DefaultQueryContext queryContext, Tuple tuple) {
        Object obj = extractedValues.remove(tuple.getId());

        if (obj == null) {
            return;
        }

        Integer currentCount = distinctValueAndCounts.remove(obj);
        if (currentCount > 1) {
            //Put it back after decrementing.
            distinctValueAndCounts.put(obj, currentCount - 1);

            return;
        }

        count--;
    }

    public int calculateAggregateInteger() {
        return count;
    }

    public Integer calculateAggregate() throws Exception {
        return count;
    }

    public int calculateAggregateAsInteger() throws Exception {
        return count;
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
        public final CountDistinctAggregator createInstance() {
            return new CountDistinctAggregator();
        }
    }
}