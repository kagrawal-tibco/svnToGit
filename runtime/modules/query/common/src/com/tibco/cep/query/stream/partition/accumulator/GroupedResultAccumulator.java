package com.tibco.cep.query.stream.partition.accumulator;

import java.util.Collection;

import com.tibco.cep.query.stream.context.Context;
import com.tibco.cep.query.stream.context.DefaultGlobalContext;
import com.tibco.cep.query.stream.context.DefaultQueryContext;
import com.tibco.cep.query.stream.context.LocalContext;
import com.tibco.cep.query.stream.core.AbstractStream;
import com.tibco.cep.query.stream.core.SubStream;
import com.tibco.cep.query.stream.core.SubStreamOwner;
import com.tibco.cep.query.stream.group.GroupAggregateTransformer;
import com.tibco.cep.query.stream.impl.aggregate.CountAggregator;
import com.tibco.cep.query.stream.monitor.Flags;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.SingleElementCollection;

/*
* Author: Ashwin Jayaprakash Date: Apr 23, 2008 Time: 6:33:59 PM
*/
public class GroupedResultAccumulator extends AbstractStream<SubStream>
        implements SubStream<SubStreamOwner>, SubStreamCycleResultAccumulator {
    protected GroupAggregateTransformer aggregateTransformer;

    protected Object[] groupColumns;

    protected Tuple previousTransformedTuple;

    protected SingleElementCollection<Tuple> resultDeletes;

    protected SingleElementCollection<Tuple> resultAdds;

    protected CountAggregator internalCounter;

    /**
     * @param source
     * @param id
     * @param aggregateTransformer
     * @param groupColumns
     */
    public GroupedResultAccumulator(SubStream source, ResourceId id,
                                    GroupAggregateTransformer aggregateTransformer,
                                    Object[] groupColumns) {
        super(source, id, aggregateTransformer.getOutputInfo());

        this.aggregateTransformer = aggregateTransformer;
        this.groupColumns = groupColumns;

        this.internalCounter = new CountAggregator();
        boolean recordAddsOnly = ! /*Inverse.*/ source.producesDStream();
        this.internalCounter.setRecordAddsOnly(recordAddsOnly);

        this.resultAdds = new SingleElementCollection<Tuple>(null);
        this.resultDeletes = new SingleElementCollection<Tuple>(null);
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

    @Override
    protected void doProcessing(Context context) throws Exception {
        final DefaultGlobalContext gc = context.getGlobalContext();
        final DefaultQueryContext qc = context.getQueryContext();
        final LocalContext lc = context.getLocalContext();
        final CountAggregator cachedInternalCounter = internalCounter;

        /*
        * Perform delete first to get the previous Tuple Id before they get
        * replaced by adds.
        */
        Collection<? extends Tuple> deadTuples = lc.getDeadTuples();
        if (deadTuples != null) {
            for (Tuple tuple : deadTuples) {
                cachedInternalCounter.remove(gc, qc, tuple);

                if (Flags.TRACK_TUPLE_REFS) {
                    // End of the road for this Tuple.
                    tuple.decrementRefCount();
                }
            }
        }

        Collection<? extends Tuple> newTuples = lc.getNewTuples();
        if (newTuples != null) {
            for (Tuple t : newTuples) {
                cachedInternalCounter.add(gc, qc, t);
            }
        }
    }

    public void cycleEnd(Context context) throws Exception {
        /*
        * If the Group-by columns are transformed once, then it never
        * changes because there are no other Aggregate columns. So, there
        * is no need to keep transforming again and again.
        */
        if (previousTransformedTuple == null) {
            // No Aggregate here.
            Tuple finalAdd = aggregateTransformer.transform(groupColumns, null);

            resultAdds.setElement(finalAdd);

            // Store for later when this tuple changes or gets deleted.
            previousTransformedTuple = finalAdd;
        }
        /*
        * As long as there are some members in the Group, this Window's
        * result will not change.
        */
        else if (internalCounter.calculateAggregateInteger() == 0) {
            resultDeletes.setElement(previousTransformedTuple);

            previousTransformedTuple = null;
        }

        if (listener != null) {
            listener.cycleEnd(context);
        }
    }

    public void fillWithAccumulatedResults(ResultHolder holder) {
        holder.setDeletes((resultDeletes.size() > 0) ? resultDeletes : null);

        holder.setAdditions((resultAdds.size() > 0) ? resultAdds : null);
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

        if (resultDeletes != null) {
            resultDeletes.clear();
            resultDeletes = null;
        }

        if (resultAdds != null) {
            resultAdds.clear();
            resultAdds = null;
        }

        internalCounter = null;
    }
}
