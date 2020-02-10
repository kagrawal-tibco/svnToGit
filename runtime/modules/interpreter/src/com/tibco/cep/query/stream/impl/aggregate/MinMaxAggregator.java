package com.tibco.cep.query.stream.impl.aggregate;

import java.util.HashMap;
import java.util.TreeMap;

import com.tibco.cep.query.stream.aggregate.Aggregator;
import com.tibco.cep.query.stream.context.DefaultGlobalContext;
import com.tibco.cep.query.stream.context.DefaultQueryContext;
import com.tibco.cep.query.stream.expression.TupleValueExtractor;
import com.tibco.cep.query.stream.tuple.Tuple;

/*
 * Author: Ashwin Jayaprakash Date: Oct 12, 2007 Time: 5:24:29 PM
 */

public abstract class MinMaxAggregator implements Aggregator {
    protected boolean recordAddsOnly;

    protected TupleValueExtractor extractor;

    protected TreeMap<Comparable<Object>, Integer> sortedNonNullEntries;

    protected int nullEntryCount;

    protected HashMap<Number, Comparable<Object>> extractedValues;

    public MinMaxAggregator() {
        this.sortedNonNullEntries = new TreeMap<Comparable<Object>, Integer>();
        this.nullEntryCount = 0;
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
            this.extractedValues = new HashMap<Number, Comparable<Object>>();
        }
        else {
            this.extractedValues = null;
        }
    }

    //-----------

    public void add(DefaultGlobalContext globalContext, DefaultQueryContext queryContext, Tuple tuple) {
        Number id = tuple.getId();

        Object object = extractor.extract(globalContext, queryContext, tuple);
        Comparable<Object> comparable = (Comparable<Object>) object;

        if (object == null) {
            nullEntryCount++;
        }
        else {
            Integer count = sortedNonNullEntries.get(comparable);
            if (count == null) {
                //Compiler will automatically box it to fetch from cache via Integer.valueOf(x).
                count = 1;
            }
            else {
                count++;
            }
            sortedNonNullEntries.put(comparable, count);
        }

        if (recordAddsOnly == false) {
            extractedValues.put(id, comparable);
        }
    }

    public void remove(DefaultGlobalContext globalContext, DefaultQueryContext queryContext, Tuple tuple) {
        Comparable<Object> comparable = extractedValues.remove(tuple.getId());

        if (comparable != null) {
            Integer count = sortedNonNullEntries.get(comparable);
            count = count - 1;
            if (count == 0) {
                sortedNonNullEntries.remove(comparable);
            }
            else {
                sortedNonNullEntries.put(comparable, count);
            }
        }
        else {
            nullEntryCount--;
        }
    }

    /**
     * @throws UnsupportedOperationException
     */
    public final int calculateAggregateAsInteger() throws Exception {
        throw new UnsupportedOperationException();
    }

    /**
     * @throws UnsupportedOperationException
     */
    public final long calculateAggregateAsLong() throws Exception {
        throw new UnsupportedOperationException();
    }

    /**
     * @throws UnsupportedOperationException
     */
    public final float calculateAggregateAsFloat() throws Exception {
        throw new UnsupportedOperationException();
    }

    /**
     * @throws UnsupportedOperationException
     */
    public final double calculateAggregateAsDouble() throws Exception {
        throw new UnsupportedOperationException();
    }
}
