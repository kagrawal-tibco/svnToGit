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
public class LongSumAggregator implements Aggregator {
    public static final Creator CREATOR = new Creator();

    protected boolean recordAddsOnly;

    protected TupleValueExtractor extractor;

    protected long sum;

    protected HashMap<Number, Long> extractedValues;

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
            this.extractedValues = new HashMap<Number, Long>();
        }
        else {
            this.extractedValues = null;
        }
    }

    //----------

    public void add(DefaultGlobalContext globalContext, DefaultQueryContext queryContext, Tuple tuple) {
        long l = extractor.extractLong(globalContext, queryContext, tuple);

        if (recordAddsOnly == false) {
            extractedValues.put(tuple.getId(), l);
        }

        sum = sum + l;
    }

    public void remove(DefaultGlobalContext globalContext, DefaultQueryContext queryContext, Tuple tuple) {
        Long l = extractedValues.remove(tuple.getId());

        if (l != null) {
            sum = sum - l;
        }
    }

    public Long calculateAggregate() throws Exception {
        return sum;
    }

    public int calculateAggregateAsInteger() throws Exception {
        throw new UnsupportedOperationException();
    }

    public long calculateAggregateAsLong() throws Exception {
        return sum;
    }

    public float calculateAggregateAsFloat() throws Exception {
        throw new UnsupportedOperationException();
    }

    public double calculateAggregateAsDouble() throws Exception {
        throw new UnsupportedOperationException();
    }

// ----------

    public static class Creator implements AggregateCreator {
        public final LongSumAggregator createInstance() {
            return new LongSumAggregator();
        }
    }
}