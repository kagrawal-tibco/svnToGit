package com.tibco.cep.query.stream.impl.aggregate;

import com.tibco.cep.query.stream.aggregate.AggregateCreator;

/*
 * Author: Ashwin Jayaprakash Date: Oct 12, 2007 Time: 6:07:53 PM
 */

public class NullsSmallestMinAggregator extends MinMaxAggregator {
    public static final Creator CREATOR = new Creator();

    public Comparable<Object> calculateAggregate() throws Exception {
        if (sortedNonNullEntries.isEmpty()) {
            return null;
        }

        return (nullEntryCount > 0) ? null : sortedNonNullEntries.firstKey();
    }

    // ----------

    public static class Creator implements AggregateCreator {
        public final NullsSmallestMinAggregator createInstance() {
            return new NullsSmallestMinAggregator();
        }
    }
}
