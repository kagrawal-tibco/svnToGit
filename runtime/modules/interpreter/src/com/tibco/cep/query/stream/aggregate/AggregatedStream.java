package com.tibco.cep.query.stream.aggregate;

import java.util.Collection;
import java.util.LinkedHashMap;

import com.tibco.cep.query.stream.context.Context;
import com.tibco.cep.query.stream.context.DefaultGlobalContext;
import com.tibco.cep.query.stream.context.DefaultQueryContext;
import com.tibco.cep.query.stream.context.LocalContext;
import com.tibco.cep.query.stream.core.AbstractStream;
import com.tibco.cep.query.stream.core.Stream;
import com.tibco.cep.query.stream.core.SubStream;
import com.tibco.cep.query.stream.core.SubStreamOwner;
import com.tibco.cep.query.stream.impl.aggregate.CountAggregator;
import com.tibco.cep.query.stream.monitor.CustomRuntimeException;
import com.tibco.cep.query.stream.monitor.Flags;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.tuple.TupleInfo;

/*
 * Author: Ashwin Jayaprakash Date: Oct 1, 2007 Time: 4:34:49 PM
 */

/**
 * This stream cannot not work on its own - it does not produce any output in the {@link
 * com.tibco.cep.query.stream.expression.ExpressionEvaluator#evaluateInteger(com.tibco.cep.query.stream.context.GlobalContext,com.tibco.cep.query.model.QueryContext,com.tibco.cep.query.stream.util.FixedKeyHashMap} step - in the normal sense. The output
 * at the end of each cycle ({@link #cycleEnd(com.tibco.cep.query.stream.context.Context)}) is
 * accumulated inside {@link #getNewAggregateAtCycleEnd()} and {@link #getOldAggregateAtCycleEnd()},
 * which has to be cleared using {@link #cleanupAfterCycleEnd()}.
 */
public class AggregatedStream extends AbstractStream<SubStream>
        implements SubStream<SubStreamOwner> {
    protected Aggregator[] aggregators;

    /**
     * Keeps track of the number of {@link Tuple}s that are currently being held/aggregated by this
     * Stream.
     */
    protected CountAggregator internalCounter;

    protected boolean changedInCycle;

    protected Object[] previousResult;

    protected Object[] newAggregateAtCycleEnd;

    protected Object[] oldAggregateAtCycleEnd;

    protected boolean decrementRefCountOnSrcTuples;

    protected AggregateInfo aggregateInfo;

    protected SubStreamOwner owner;

    /**
     * @param source
     * @param id
     * @param aggregateInfo
     * @param decrementRefCountOnSrcTuples If <code>true</code>, then the source Tuples are
     *                                     decremented before dropping the references.
     */
    public AggregatedStream(Stream<? extends SubStream> source, ResourceId id,
                            AggregateInfo aggregateInfo, boolean decrementRefCountOnSrcTuples) {
        super(source, id, null /*No TupleInfo.*/);

        this.aggregateInfo = aggregateInfo;

        LinkedHashMap<String, AggregateItemInfo> aggregateItems = this.aggregateInfo
                .getAggregateItems();
        this.aggregators = new Aggregator[aggregateItems.size()];

        int x = 0;
        for (String key : aggregateItems.keySet()) {
            AggregateItemInfo itemInfo = aggregateItems.get(key);

            try {
                AggregateCreator creator = itemInfo.getCreator();
                this.aggregators[x] = creator.createInstance();
                this.aggregators[x].setExtractor(itemInfo.getExtractor());
                this.aggregators[x].setRecordAddsOnly(!sourceProducesDStream);
            }
            catch (Exception e) {
                throw new CustomRuntimeException(getResourceId(), e);
            }

            x++;
        }

        // Does not need an Extractor.
        this.internalCounter = new CountAggregator();
        this.internalCounter.setRecordAddsOnly(!sourceProducesDStream);

        this.decrementRefCountOnSrcTuples = decrementRefCountOnSrcTuples;
    }

    /**
     * @return Always <code>null</code>.
     */
    @Override
    public TupleInfo getOutputInfo() {
        return null;
    }

    /**
     * @return
     * @see {@link #internalCounter}
     */
    public int getNumOfAggregatedTuples() {
        return internalCounter.calculateAggregateInteger();
    }

    public void setOwner(SubStreamOwner owner) {
        this.owner = owner;
    }

    public SubStreamOwner getOwner() {
        return owner;
    }

    public void cycleStart(Context context) throws Exception {
        changedInCycle = false;

        if (listener != null) {
            listener.cycleStart(context);
        }
    }

    /**
     * Does not produce any output - just keeps updating the aggregates. Final output only generated
     * in {@link #cycleEnd(com.tibco.cep.query.stream.context.Context)}.
     *
     * @param context
     * @throws Exception
     */
    @Override
    protected void doProcessing(Context context) throws Exception {
        super.doProcessing(context);

        // ---------

        final LocalContext lc = context.getLocalContext();
        final DefaultQueryContext qc = context.getQueryContext();
        final DefaultGlobalContext gc = context.getGlobalContext();

        boolean d = remove(gc, qc, lc);
        boolean a = add(gc, qc, lc);

        changedInCycle = changedInCycle || a || d;
    }

    /**
     * {@link #cleanupAfterCycleEnd()} must be invoked after this.
     *
     * @param context
     * @throws Exception
     */
    public void cycleEnd(Context context) throws Exception {
        if (changedInCycle) {
            final Object[] aggregates = new Object[aggregators.length];

            for (int i = 0; i < aggregates.length; i++) {
                //todo Use specific data type calcAggrXXX().
                aggregates[i] = aggregators[i].calculateAggregate();
            }

            // ----------

            //New value.
            newAggregateAtCycleEnd = aggregates;

            //Store the old value.
            if (previousResult != null) {
                oldAggregateAtCycleEnd = previousResult;
            }
            //Save this new value for next cycle.
            previousResult = aggregates;

            // ----------

            if (owner != null && internalCounter.calculateAggregateInteger() == 0) {
                owner.scheduleSubStreamForNextCycle(context, this);
            }
        }
        else if (internalCounter.calculateAggregateInteger() == 0) {
            /*
             * Nothing has changed. Which means that this Stream's aggregates
             * were emptied in the previous cycle and the only thing remaining
             * is the [0, 0, ...] Tuple that was sent as the last value. This
             * Tuple now has to be discarded.
             */
            if (previousResult != null) {
                oldAggregateAtCycleEnd = previousResult;

                previousResult = null;
            }
        }

        if (listener != null) {
            listener.cycleEnd(context);
        }
    }

    /**
     * @return Can be <code>null</code>.
     */
    public Object[] getOldAggregateAtCycleEnd() {
        return oldAggregateAtCycleEnd;
    }

    /**
     * @return Can be <code>null</code>.
     */
    public Object[] getNewAggregateAtCycleEnd() {
        return newAggregateAtCycleEnd;
    }

    /**
     * To be invoked after {@link #cycleEnd(com.tibco.cep.query.stream.context.Context)} and after
     * the {@link #getOldAggregateAtCycleEnd()} and {@link #getNewAggregateAtCycleEnd()} have been
     * read.
     */
    public void cleanupAfterCycleEnd() {
        newAggregateAtCycleEnd = null;
        oldAggregateAtCycleEnd = null;
    }

    /**
     * @param gc
     * @param qc
     * @param lc
     * @return <code>false</code> if no additions were made.
     */
    protected boolean add(DefaultGlobalContext gc, DefaultQueryContext qc, LocalContext lc) {
        Collection<? extends Tuple> newTuples = lc.getNewTuples();
        if (newTuples == null) {
            return false;
        }

        final Aggregator[] cachedAggregators = aggregators;
        final CountAggregator cachedCounter = internalCounter;

        for (Tuple tuple : newTuples) {
            for (Aggregator aggregator : cachedAggregators) {
                aggregator.add(gc, qc, tuple);
            }

            cachedCounter.add(gc, qc, tuple);
        }

        return true;
    }

    /**
     * @param gc
     * @param qc
     * @param lc
     * @return <code>false</code> if no deletions were made.
     */
    public boolean remove(DefaultGlobalContext gc, DefaultQueryContext qc, LocalContext lc) {
        Collection<? extends Tuple> deadTuples = lc.getDeadTuples();
        if (deadTuples == null) {
            return false;
        }

        final Aggregator[] cachedAggregators = aggregators;
        final CountAggregator cachedCounter = internalCounter;
        final boolean cachedDecrementCheck = decrementRefCountOnSrcTuples;

        for (Tuple tuple : deadTuples) {
            for (Aggregator aggregator : cachedAggregators) {
                aggregator.remove(gc, qc, tuple);
            }

            cachedCounter.remove(gc, qc, tuple);

            if (Flags.TRACK_TUPLE_REFS) {
                if (cachedDecrementCheck) {
                    // This Tuple will not be used anymore.
                    tuple.decrementRefCount();
                }
            }
        }

        return true;
    }

    /**
     * @return Always <code>true</code>.
     */
    @Override
    public final boolean producesDStream() {
        return true;
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

        aggregators = null;
        internalCounter = null;
        aggregateInfo = null;
        owner = null;

        previousResult = null;
        newAggregateAtCycleEnd = null;
        oldAggregateAtCycleEnd = null;
    }
}
