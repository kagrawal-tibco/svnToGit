package com.tibco.cep.query.stream.sort;

import java.util.Comparator;
import java.util.List;

import com.tibco.cep.query.stream.context.Context;
import com.tibco.cep.query.stream.core.AbstractStream;
import com.tibco.cep.query.stream.core.Stream;
import com.tibco.cep.query.stream.expression.TupleValueExtractor;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.tuple.TupleInfo;
import com.tibco.cep.query.stream.util.CustomCollection;

/*
 * Author: Ashwin Jayaprakash Date: Oct 1, 2007 Time: 4:20:00 PM
 */

/**
 * <p/> <b>Creates</b> and sends the <b>entire</b> sorted live-tuple list during every cycle - only
 * if something changes. Otherwise does not send anything. </p>
 */
public class SortedStream extends AbstractStream {
    protected ContinuousSorter sorter;

    protected SortInfo sortInfo;

    protected TupleValueExtractor[] extractors;

    protected List<Comparator<Object>> comparators;

    /**
     * @param source      Uses this stream's {@link Stream#getOutputInfo()}.
     * @param id
     * @param sortInfo
     * @param extractors
     * @param comparators
     */
    public SortedStream(Stream source, ResourceId id, SortInfo sortInfo,
                        TupleValueExtractor[] extractors, List<Comparator<Object>> comparators) {
        this(source, id, source.getOutputInfo(), sortInfo, extractors, comparators);
    }

    /**
     * @param id
     * @param tupleInfo
     * @param sortInfo
     * @param extractors
     * @param comparators
     */
    public SortedStream(ResourceId id, TupleInfo tupleInfo, SortInfo sortInfo,
                        TupleValueExtractor[] extractors, List<Comparator<Object>> comparators) {
        this(null, id, tupleInfo, sortInfo, extractors, comparators);
    }

    protected SortedStream(Stream source, ResourceId id, TupleInfo tupleInfo,
                           SortInfo sortInfo, TupleValueExtractor[] extractors,
                           List<Comparator<Object>> comparators) {
        super(source, id, tupleInfo);

        this.sortInfo = sortInfo;
        this.extractors = extractors;
        this.comparators = comparators;
    }

    @Override
    public void stop() throws Exception {
        super.stop();

        if (sorter != null) {
            sorter.discard();
        }
    }

    // ---------

    @SuppressWarnings("unchecked")
    @Override
    protected void doProcessing(Context context) throws Exception {
        super.doProcessing(context);

        if (sorter == null) {
            sorter = Helper.buildSorter(context, sourceProducesDStream, sortInfo, extractors,
                    comparators);
        }

        // Deletes first.
        CustomCollection<? extends Tuple> deletes = null;
        if (sourceProducesDStream) {
            deletes = handleDeletes(context);
        }

        boolean somethingChanged = handleAdds(context);

        somethingChanged = somethingChanged || (deletes != null);

        // ----------

        if (somethingChanged) {
            CustomCollection<? extends Tuple> newTuples = getSortedLiveTuples(context);
            localContext.setNewTuples(newTuples);
        }
        localContext.setDeadTuples(deletes);
    }

    /**
     * By default, it forwards the entire sorted data to the next processing step.
     *
     * @param context
     */
    protected CustomCollection<? extends Tuple> getSortedLiveTuples(Context context) {
        return (null == sorter) ? null : sorter.getSortedLiveTuples(context);
    }

    @Override
    protected void endProcessing(Context context) {
        super.endProcessing(context);
        if (null != sorter) {
            sorter.endProcessing(context);
        }
    }

    /**
     * @param context
     * @return <code>true</code> if something changed.
     */
    protected boolean handleAdds(Context context) {
        return (null == sorter) ? null : sorter.handleAdds(context);
    }

    /**
     * Invoked only if {@link  #sourceProducesDStream} is true.
     *
     * @param context
     * @return
     */
    protected CustomCollection<? extends Tuple> handleDeletes(Context context) {
        return (null == sorter) ? null : sorter.handleDeletes(context);
    }
}
