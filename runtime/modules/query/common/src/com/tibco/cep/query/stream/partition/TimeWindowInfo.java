package com.tibco.cep.query.stream.partition;

import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.expression.TupleValueExtractor;
import com.tibco.cep.query.stream.tuple.Tuple;

/*
 * Author: Ashwin Jayaprakash Date: Nov 13, 2007 Time: 3:26:09 PM
 */

public class TimeWindowInfo extends CommonWindowInfo {
    protected final long timeMillis;

    /**
     * Can be <code>null</code>. Default to be used if so.
     */
    protected final TupleTimestampExtractor timestampExtractor;

    /**
     * Uses a default {@link TupleTimestampExtractor}.
     *
     * @param size
     * @param timeMillis
     */
    public TimeWindowInfo(int size, long timeMillis) {
        this(size, timeMillis, null);
    }

    /**
     * @param size
     * @param timeMillis
     * @param timestampExtractor Can be <code>null</code>, in which case the {@link
     *                           DefaultTupleTimestampExtractor} will be used.
     */
    public TimeWindowInfo(int size, long timeMillis, TupleTimestampExtractor timestampExtractor) {
        super(size);

        this.timeMillis = timeMillis;
        this.timestampExtractor =
                (timestampExtractor == null) ? new DefaultTupleTimestampExtractor()
                        : timestampExtractor;
    }

    public long getTimeMillis() {
        return timeMillis;
    }

    public TupleTimestampExtractor getTimestampExtractor() {
        return timestampExtractor;
    }

    // ---------

    public interface TupleTimestampExtractor extends TupleValueExtractor {
        public abstract Long extract(GlobalContext globalContext, QueryContext queryContext,
                                     Tuple tuple);
    }
}