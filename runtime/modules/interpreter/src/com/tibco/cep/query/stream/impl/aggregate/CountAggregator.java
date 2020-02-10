package com.tibco.cep.query.stream.impl.aggregate;

import com.tibco.cep.query.stream.aggregate.AggregateCreator;
import com.tibco.cep.query.stream.aggregate.Aggregator;
import com.tibco.cep.query.stream.context.DefaultGlobalContext;
import com.tibco.cep.query.stream.context.DefaultQueryContext;
import com.tibco.cep.query.stream.expression.TupleValueExtractor;
import com.tibco.cep.query.stream.tuple.Tuple;

/*
 * Author: Ashwin Jayaprakash Date: Oct 9, 2007 Time: 11:27:33 AM
 */

/**
 * Counts the number of Tuples. Outputs <code>int</code>.
 */
public class CountAggregator implements Aggregator {
    public static final Creator CREATOR = new Creator();

    protected boolean recordAddsOnly;

    protected TupleValueExtractor extractor;

    protected int count;

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
    }

    //-----------

    public void add(DefaultGlobalContext globalContext, DefaultQueryContext queryContext, Tuple tuple) {
        count++;
    }

    public void remove(DefaultGlobalContext globalContext, DefaultQueryContext queryContext, Tuple tuple) {
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
        public final CountAggregator createInstance() {
            return new CountAggregator();
        }
    }
}
    