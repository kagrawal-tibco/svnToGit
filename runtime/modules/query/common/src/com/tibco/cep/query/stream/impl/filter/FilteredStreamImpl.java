package com.tibco.cep.query.stream.impl.filter;

import com.tibco.cep.query.stream.context.Context;
import com.tibco.cep.query.stream.context.DefaultGlobalContext;
import com.tibco.cep.query.stream.context.DefaultQueryContext;
import com.tibco.cep.query.stream.context.LocalContext;
import com.tibco.cep.query.stream.core.Stream;
import com.tibco.cep.query.stream.expression.ExpressionEvaluator;
import com.tibco.cep.query.stream.filter.FilteredStream;
import com.tibco.cep.query.stream.monitor.Flags;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.AppendOnlyQueue;
import com.tibco.cep.query.stream.util.CustomCollection;
import com.tibco.cep.query.stream.util.CustomHashSet;
import com.tibco.cep.query.stream.util.FixedKeyHashMap;

/*
 * Author: Ashwin Jayaprakash Date: Oct 1, 2007 Time: 5:39:15 PM
 */

public class FilteredStreamImpl extends FilteredStream {
    protected FixedKeyHashMap<String, Tuple> aliasAndTuples;

    protected CustomHashSet<Number> tuplesThatWentThru;

    protected AppendOnlyQueue<Tuple> sessionAdds;

    protected AppendOnlyQueue<Tuple> sessionDeletes;

    public FilteredStreamImpl(Stream source, ResourceId id,
                              ExpressionEvaluator filterExpression) {
        this(DEFAULT_STREAM_ALIAS, source, id, filterExpression);
    }

    public FilteredStreamImpl(String alias, Stream source, ResourceId id,
                              ExpressionEvaluator filterExpression) {
        super(alias, source, id, filterExpression);

        this.aliasAndTuples = new FixedKeyHashMap<String, Tuple>(alias);

        if (sourceProducesDStream) {
            this.tuplesThatWentThru = new CustomHashSet<Number>();
        }
        else {
            this.tuplesThatWentThru = null;
        }
    }

    @Override
    public void stop() throws Exception {
        super.stop();

        aliasAndTuples.clear();
        aliasAndTuples = null;

        if (tuplesThatWentThru != null) {
            tuplesThatWentThru.clear();
            tuplesThatWentThru = null;
        }

        if (sessionAdds != null) {
            sessionAdds.clear();
            sessionAdds = null;
        }

        if (sessionDeletes != null) {
            sessionDeletes.clear();
            sessionDeletes = null;
        }
    }

    @Override
    protected void doProcessing(Context context) throws Exception {
        super.doProcessing(context);

        final DefaultGlobalContext gc = context.getGlobalContext();
        final DefaultQueryContext qc = context.getQueryContext();
        final LocalContext lc = context.getLocalContext();

        CustomCollection<? extends Tuple> deletes = lc.getDeadTuples();
        CustomCollection<? extends Tuple> adds = lc.getNewTuples();

        if (sessionAdds == null) {
            sessionDeletes = new AppendOnlyQueue<Tuple>(qc.getArrayPool());
            sessionAdds = new AppendOnlyQueue<Tuple>(qc.getArrayPool());
        }

        final CustomHashSet<Number> cachedTuplesThatWentThru = tuplesThatWentThru;
        final AppendOnlyQueue<Tuple> cachedSessionAdds = sessionAdds;
        final AppendOnlyQueue<Tuple> cachedSessionDeletes = sessionDeletes;
        final FixedKeyHashMap<String, Tuple> cachedAliasAndTuples = aliasAndTuples;
        final String cachedAlias = alias;
        final ExpressionEvaluator cachedFilterExpression = filterExpression;

        if (deletes != null) {
            for (Tuple data : deletes) {
                // This tuple went through before when it was added.
                if (cachedTuplesThatWentThru.remove(data.getId())) {
                    cachedSessionDeletes.add(data);
                }
                else {
                    if (Flags.TRACK_TUPLE_REFS) {
                        // End of the road for this Tuple.
                        data.decrementRefCount();
                    }
                }
            }
        }

        if (adds != null) {
            for (Tuple data : adds) {
                cachedAliasAndTuples.put(cachedAlias, data);

                boolean result =
                        cachedFilterExpression.evaluateBoolean(gc, qc, cachedAliasAndTuples);
                if (result) {
                    if (cachedTuplesThatWentThru != null) {
                        cachedTuplesThatWentThru.add(data.getId());
                    }

                    cachedSessionAdds.add(data);
                }
            }

            cachedAliasAndTuples.clear();
        }

        /*
        todo If there's too much noise, then this filter will just be triggering the whole
        downstream sequence unnecessarily. If nothing got past, then return and stop flow.
        */

        if (cachedSessionDeletes.isEmpty() == false) {
            localContext.setDeadTuples(cachedSessionDeletes);
        }
        if (cachedSessionAdds.isEmpty() == false) {
            localContext.setNewTuples(cachedSessionAdds);
        }
    }

    @Override
    protected void endProcessing(Context context) {
        super.endProcessing(context);

        if (sessionAdds != null) {
            sessionDeletes.clear();
            sessionAdds.clear();
        }
    }
}
