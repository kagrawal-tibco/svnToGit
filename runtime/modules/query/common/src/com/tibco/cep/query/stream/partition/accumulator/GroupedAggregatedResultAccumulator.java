package com.tibco.cep.query.stream.partition.accumulator;

import com.tibco.cep.query.stream.aggregate.AggregatedStream;
import com.tibco.cep.query.stream.context.Context;
import com.tibco.cep.query.stream.core.AbstractStream;
import com.tibco.cep.query.stream.core.SubStream;
import com.tibco.cep.query.stream.core.SubStreamOwner;
import com.tibco.cep.query.stream.group.GroupAggregateTransformer;
import com.tibco.cep.query.stream.monitor.CustomRuntimeException;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.SingleElementCollection;

/*
* Author: Ashwin Jayaprakash Date: Apr 23, 2008 Time: 6:33:59 PM
*/
public class GroupedAggregatedResultAccumulator extends AbstractStream<SubStream>
        implements SubStream<SubStreamOwner>, SubStreamCycleResultAccumulator {
    protected GroupAggregateTransformer aggregateTransformer;

    protected Object[] groupColumns;

    protected Tuple previousTransformedTuple;

    protected SingleElementCollection<Tuple> accumulatedNewResultHolder;

    protected SingleElementCollection<Tuple> accumulatedDeadResultHolder;

    /**
     * @param source
     * @param id
     * @param aggregateTransformer
     * @param groupColumns
     */
    public GroupedAggregatedResultAccumulator(AggregatedStream source, ResourceId id,
                                              GroupAggregateTransformer aggregateTransformer,
                                              Object[] groupColumns) {
        super(source, id, aggregateTransformer.getOutputInfo());

        this.aggregateTransformer = aggregateTransformer;
        this.accumulatedNewResultHolder = new SingleElementCollection<Tuple>(null);
        this.accumulatedDeadResultHolder = new SingleElementCollection<Tuple>(null);
        this.groupColumns = groupColumns;
    }

    public void fillWithAccumulatedResults(ResultHolder holder) {
        holder.setDeletes(
                (accumulatedDeadResultHolder.size() > 0) ? accumulatedDeadResultHolder : null);

        holder.setAdditions(
                (accumulatedNewResultHolder.size() > 0) ? accumulatedNewResultHolder : null);
    }

    public void setOwner(SubStreamOwner owner) {
    }

    public SubStreamOwner getOwner() {
        return null;
    }

    public void cycleStart(Context context) throws Exception {
        if (listener != null) {
            listener.cycleStart(context);
        }
    }

    /**
     * @param context
     * @throws CustomRuntimeException
     */
    public void cycleEnd(Context context) throws Exception {
        try {
            final AggregatedStream aggregatedStream = (AggregatedStream) source;
            final Object[] newAggregateAtCycleEnd = aggregatedStream.getNewAggregateAtCycleEnd();
            final Object[] oldAggregateAtCycleEnd = aggregatedStream.getOldAggregateAtCycleEnd();

            /*
            * Perform delete first to get the previous Tuple Id before they get
            * replaced by adds.
            */
            if (oldAggregateAtCycleEnd != null) {
                accumulatedDeadResultHolder.setElement(previousTransformedTuple);

                previousTransformedTuple = null;
            }

            if (newAggregateAtCycleEnd != null) {
                Tuple finalAdd =
                        aggregateTransformer.transform(groupColumns, newAggregateAtCycleEnd);

                accumulatedNewResultHolder.setElement(finalAdd);

                // Store for later when this tuple changes or gets deleted.
                previousTransformedTuple = finalAdd;
            }
        }
        finally {
            ((AggregatedStream) source).cleanupAfterCycleEnd();
        }

        if (listener != null) {
            listener.cycleEnd(context);
        }
    }

    /**
     * @return Always <code>true</code>.
     */
    public boolean canDiscard() {
        return true;
    }

    @Override
    public void stop() throws Exception {
        super.stop();

        aggregateTransformer = null;
        groupColumns = null;
        previousTransformedTuple = null;

        if (accumulatedNewResultHolder != null) {
            accumulatedNewResultHolder.clear();
            accumulatedNewResultHolder = null;
        }

        if (accumulatedDeadResultHolder != null) {
            accumulatedDeadResultHolder.clear();
            accumulatedDeadResultHolder = null;
        }
    }
}
