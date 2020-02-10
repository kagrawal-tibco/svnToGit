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
 * Finds the average of all the Tuples. Outputs <code>double</code>.
 */
public class AverageAggregator implements Aggregator {
    public static final Creator CREATOR = new Creator();

    protected boolean recordAddsOnly;

    protected TupleValueExtractor extractor;

    protected double sum;

    protected double size;

    protected double avg;

    protected HashMap<Number, Double> extractedValues;

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

        if (recordAddsOnly == false) {
            extractedValues.put(tuple.getId(), d);
        }

        sum = sum + d;
        size++;

        avg = sum / size;
    }

    public void remove(DefaultGlobalContext globalContext, DefaultQueryContext queryContext, Tuple tuple) {
        Double d = extractedValues.remove(tuple.getId());

        if (d == null) {
            return;
        }

        sum = sum - d;
        size--;

        avg = (size == 0.0) ? 0.0 : (sum / size);
    }

    public Double calculateAggregate() throws Exception {
        return avg;
    }

    public double calculateAggregateAsDouble() throws Exception {
        return avg;
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

    // ----------

    public static class Creator implements AggregateCreator {
        public final AverageAggregator createInstance() {
            return new AverageAggregator();
        }
    }
}