package com.tibco.cep.query.stream.query.snapshot;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import com.tibco.cep.query.stream.context.Context;
import com.tibco.cep.query.stream.core.Stream;
import com.tibco.cep.query.stream.expression.TupleValueExtractor;
import com.tibco.cep.query.stream.monitor.Flags;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.sort.ContinuousSorter;
import com.tibco.cep.query.stream.sort.Helper;
import com.tibco.cep.query.stream.sort.SortInfo;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.CustomCollection;

/*
 * Author: Ashwin Jayaprakash
 * Date: Mar 19, 2008
 * Time: 4:11:15 PM
 */

/**
 * To be used in place of the {@link com.tibco.cep.query.stream.sort.SortedStream}.
 */
public class SortedBridge extends Bridge {
    protected ContinuousSorter sorter;

    protected SortInfo sortInfo;

    protected TupleValueExtractor[] extractors;

    protected List<Comparator<Object>> comparators;

    public SortedBridge(Stream source, ResourceId id, SortInfo sortInfo,
                        TupleValueExtractor[] extractors, List<Comparator<Object>> comparators) {
        super(source, id);

        this.sortInfo = sortInfo;
        this.extractors = extractors;
        this.comparators = comparators;
    }

    @Override
    protected void doProcessing(Context context) throws Exception {
        if (sorter == null) {
            this.sorter = Helper.buildSorter(context, sourceProducesDStream, sortInfo, extractors,
                    comparators);
        }

        //Deletes first.
        if (sourceProducesDStream) {
            Collection<? extends Tuple> deadTuples = sorter.handleDeletes(context);

            if (Flags.TRACK_TUPLE_REFS) {
                if (deadTuples != null) {
                    //End of the road for these Tuples.
                    for (Tuple deadTuple : deadTuples) {
                        deadTuple.decrementRefCount();
                    }
                }
            }
        }

        sorter.handleAdds(context);
    }

    @Override
    protected CustomCollection<? extends Tuple> getFinalCollectedTuples(Context context) {
        return (null == sorter) ? null : sorter.getSortedLiveTuples(context);
    }

    @Override
    protected void clearFinalCollectedTuples(Context context) {
        if (null != sorter) {
            sorter.endProcessing(context);
        }
    }

    @Override
    public void stop() throws Exception {
        super.stop();

        if (null != sorter) {
            sorter.discard();
        }
    }
}
