package com.tibco.cep.query.stream.impl.aggregate;

import com.tibco.cep.query.stream.aggregate.AggregateCreator;

/*
 * Author: Ashwin Jayaprakash Date: Oct 12, 2007 Time: 6:07:53 PM
 */

public class NullsSmallestMaxAggregator extends MinMaxAggregator {
    public static final Creator CREATOR = new Creator();

    public Comparable<Object> calculateAggregate() throws Exception {
        if (sortedNonNullEntries.isEmpty()) {
            return null;
        }

        return sortedNonNullEntries.lastKey();
    }

    // ----------

    public static class Creator implements AggregateCreator {
        public final NullsSmallestMaxAggregator createInstance() {
            return new NullsSmallestMaxAggregator();
        }
    }
}
