package com.tibco.cep.query.stream.partition;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;

import com.tibco.cep.query.stream.aggregate.AggregateItemInfo;
import com.tibco.cep.query.stream.context.Context;
import com.tibco.cep.query.stream.core.Stream;
import com.tibco.cep.query.stream.group.GroupAggregateInfo;
import com.tibco.cep.query.stream.group.GroupKey;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.tuple.TupleInfo;
import com.tibco.cep.query.stream.util.AppendOnlyQueue;
import com.tibco.cep.query.stream.util.CustomCollection;

/*
 * Author: Ashwin Jayaprakash Date: Nov 8, 2007 Time: 5:41:59 PM
 */

public class AggregatedPartitionedStream extends PartitionedStream {
    /**
     * Window gets discarded, but the last new value is orphaned and never gets removed in the next
     * cycle. To avoid such situations, they are recorded here and discarded manually.
     */
    protected HashMap<GroupKey, Tuple> lastNewTuplesOfDiscardedWindowsCurr;

    protected HashMap<GroupKey, Tuple> lastNewTuplesOfDiscardedWindowsPrev;

    private AppendOnlyQueue<Tuple> occassionalDeletes;

    private HashMap<GroupKey, Tuple> parkedMap;

    public AggregatedPartitionedStream(Stream source, ResourceId id, TupleInfo outputInfo,
                                       GroupAggregateInfo groupAggregateInfo,
                                       WindowBuilder windowBuilder) {
        super(source, id, outputInfo, groupAggregateInfo, null, windowBuilder);
    }

    @Override
    public void stop() throws Exception {
        super.stop();

        if (lastNewTuplesOfDiscardedWindowsCurr != null) {
            lastNewTuplesOfDiscardedWindowsCurr.clear();
            lastNewTuplesOfDiscardedWindowsCurr = null;
        }

        if (lastNewTuplesOfDiscardedWindowsPrev != null) {
            lastNewTuplesOfDiscardedWindowsPrev.clear();
            lastNewTuplesOfDiscardedWindowsPrev = null;
        }

        if (occassionalDeletes != null) {
            occassionalDeletes.clear();
            occassionalDeletes = null;
        }

        if (parkedMap != null) {
            parkedMap.clear();
            parkedMap = null;
        }
    }

    @Override
    protected boolean useTransformer(LinkedHashMap<String, AggregateItemInfo> aggregateItemInfos) {
        /*
         * Use the Transformer always. Even if there are no Aggregates - i.e
         * only Group-by and no Aggregates defined.
         */
        return true;
    }

    @Override
    protected void onWindowResult(Window window,
                                  Collection<? extends Tuple> windowNewTuples,
                                  Collection<? extends Tuple> windowDeadTuples) {
        super.onWindowResult(window, windowNewTuples, windowDeadTuples);

        if (window.canDiscard() && windowNewTuples != null) {
            if (lastNewTuplesOfDiscardedWindowsCurr == null) {
                if (parkedMap == null) {
                    parkedMap = new HashMap<GroupKey, Tuple>();
                }

                lastNewTuplesOfDiscardedWindowsCurr = parkedMap;
            }

            Tuple just1Tuple = windowNewTuples.iterator().next();
            lastNewTuplesOfDiscardedWindowsCurr.put(window.getGroupKey(), just1Tuple);
        }
    }

    @Override
    protected void generateResults(Context context,
                                   CustomCollection<Tuple> sessionAdds,
                                   CustomCollection<Tuple> sessionDeletes) throws Exception {
        CustomCollection<Tuple> deletes = sessionDeletes;

        /*
         * "New" Tuples from the previous cycle. Send them as Deletes in this
         * cycle.
         */
        if (lastNewTuplesOfDiscardedWindowsPrev != null) {
            if (deletes == null) {
                if (occassionalDeletes == null) {
                    occassionalDeletes = new AppendOnlyQueue<Tuple>(context.getQueryContext()
                            .getArrayPool());
                }

                deletes = occassionalDeletes;
            }

            deletes.addAll(lastNewTuplesOfDiscardedWindowsPrev.values());
        }

        if (lastNewTuplesOfDiscardedWindowsCurr != null) {
            context.getQueryContext().addStreamForNextCycle(this);
        }

        parkedMap = lastNewTuplesOfDiscardedWindowsPrev;
        if (parkedMap != null) {
            parkedMap.clear();
        }
        lastNewTuplesOfDiscardedWindowsPrev = lastNewTuplesOfDiscardedWindowsCurr;
        lastNewTuplesOfDiscardedWindowsCurr = null;

        // ---------

        localContext.setNewTuples(sessionAdds);
        localContext.setDeadTuples(deletes);
    }

    @Override
    protected void endProcessing(Context context) {
        super.endProcessing(context);

        if (occassionalDeletes != null) {
            occassionalDeletes.clear();
        }
    }
}
