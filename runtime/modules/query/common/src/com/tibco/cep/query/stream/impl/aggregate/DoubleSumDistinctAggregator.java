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
public class DoubleSumDistinctAggregator implements Aggregator {
    public static final Creator CREATOR = new Creator();

    protected boolean recordAddsOnly;

    protected TupleValueExtractor extractor;

    protected double sum;

    protected HashMap<Double, Integer> distinctValueAndCounts;

    protected HashMap<Number, Double> extractedValues;

    public DoubleSumDistinctAggregator() {
        this.distinctValueAndCounts = new HashMap<Double, Integer>();
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
            this.extractedValues = new HashMap<Number, Double>();
        }
        else {
            this.extractedValues = null;
        }
    }

    //----------

    public void add(DefaultGlobalContext globalContext, DefaultQueryContext queryContext, Tuple tuple) {
        double d = extractor.extractDouble(globalContext, queryContext, tuple);
        Double objD = d;

        if (recordAddsOnly == false) {
            extractedValues.put(tuple.getId(), objD);
        }       

        Integer currentCount = distinctValueAndCounts.get(objD);
        if (currentCount == null) {
            distinctValueAndCounts.put(objD, 1);
        }
        else {
            distinctValueAndCounts.put(objD, currentCount + 1);

            return;
        }

        sum = sum + d;
    }

    public void remove(DefaultGlobalContext globalContext, DefaultQueryContext queryContext, Tuple tuple) {
        Double d = extractedValues.remove(tuple.getId());

        if (d == null) {
            return;
        }

        Integer currentCount = distinctValueAndCounts.remove(d);
        if (currentCount > 1) {
            //Put it back after decrementing.
            distinctValueAndCounts.put(d, currentCount - 1);

            return;
        }

        sum = sum - d;
    }

    public Double calculateAggregate() throws Exception {
        return sum;
    }

    public int calculateAggregateAsInteger() throws Exception {
        throw new UnsupportedOperationException();
    }

    public long calculateAggregateAsLong() throws Exception {
        throw new UnsupportedOperationException();
    }

    public float calculateAggregateAsFloat() throws Exception {
        throw new UnsupportedOperationException();
    }

    public double calculateAggregateAsDouble() throws Exception {
        return sum;
    }

// ----------

    public static class Creator implements AggregateCreator {
        public final DoubleSumDistinctAggregator createInstance() {
            return new DoubleSumDistinctAggregator();
        }
    }
}