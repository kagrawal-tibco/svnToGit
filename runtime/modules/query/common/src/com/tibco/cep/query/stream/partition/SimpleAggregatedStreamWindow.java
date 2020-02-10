package com.tibco.cep.query.stream.partition;

import com.tibco.cep.query.stream.aggregate.AggregatedStream;
import com.tibco.cep.query.stream.context.Context;
import com.tibco.cep.query.stream.context.DefaultGlobalContext;
import com.tibco.cep.query.stream.context.DefaultQueryContext;
import com.tibco.cep.query.stream.context.LocalContext;
import com.tibco.cep.query.stream.core.Registry;
import com.tibco.cep.query.stream.core.SubStream;
import com.tibco.cep.query.stream.group.GroupAggregateTransformer;
import com.tibco.cep.query.stream.group.GroupKey;
import com.tibco.cep.query.stream.impl.aggregate.CountAggregator;
import com.tibco.cep.query.stream.monitor.CustomException;
import com.tibco.cep.query.stream.monitor.CustomRuntimeException;
import com.tibco.cep.query.stream.monitor.KnownResource;
import com.tibco.cep.query.stream.monitor.Logger;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.partition.purge.WindowPurgeManager;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.tuple.TupleInfo;
import com.tibco.cep.query.stream.util.CustomCollection;

/*
 * Author: Ashwin Jayaprakash Date: Nov 8, 2007 Time: 3:28:39 PM
 */

/**
 * <p> This class is only used for simple Group-By clauses where there is no explicit Window, but
 * just Grouping and Aggregation. Aggregation is optional. </p>
 */
public class SimpleAggregatedStreamWindow extends Window {
    /**
     * Can be <code>null</code>.
     */
    protected AggregatedStream lastAggregatedStream;

    /**
     * Used if {@link #lastAggregatedStream} is <code>null</code>, to keep track of the lifespan of
     * this Window. <code>null</code> if {@link #lastAggregatedStream} is not <code>null</code>.
     */
    protected CountAggregator internalCounter;

    public SimpleAggregatedStreamWindow(ResourceId id, TupleInfo outputInfo, GroupKey groupKey,
                                        WindowOwner windowOwner) {
        super(id, outputInfo, groupKey, windowOwner);
    }

    @Override
    public boolean requiresTupleDeletes() {
        return true;
    }

    /**
     * {@inheritDoc}
     *
     * @param theLastStreamInFlow Has to be an {@link com.tibco.cep.query.stream.aggregate.AggregatedStream}
     *                            or <code>null</code>.
     * @throws RuntimeException If the lastStreamInFlow is not <code>null</code> and not an {@link
     *                          AggregatedStream}.
     */
    @Override
    public void init(GroupAggregateTransformer aggregateTransformer, SubStream theLastStreamInFlow,
                     WindowPurgeManager purgeManager) {
        if (theLastStreamInFlow != null &&
                (theLastStreamInFlow instanceof AggregatedStream) == false) {
            throw new CustomRuntimeException(resourceId, "Only " + AggregatedStream.class.getName()
                    + " types or nulls are allowed to be used as LastStreamInFlow.");
        }

        //-----------

        super.init(aggregateTransformer, theLastStreamInFlow, purgeManager);

        //-----------

        this.lastAggregatedStream = (AggregatedStream) theLastStreamInFlow;

        if (this.lastAggregatedStream == null) {
            boolean b = windowOwner.canSendDeletesToSubStream();
            this.internalCounter = new CountAggregator();
            this.internalCounter.setRecordAddsOnly(!b);
        }
    }

    @Override
    public int getUnprocessedBufferSize() {
        return 0;
    }

    @Override
    protected void doProcessing(Context context) throws Exception {
        super.doProcessing(context);

        final LocalContext lc = context.getLocalContext();

        CustomCollection<? extends Tuple> deadTuples = lc.getDeadTuples();
        CustomCollection<? extends Tuple> newTuples = lc.getNewTuples();

        if (internalCounter != null) {
            final DefaultGlobalContext gc = context.getGlobalContext();
            final DefaultQueryContext qc = context.getQueryContext();
            final CountAggregator cachedInternalCounter = internalCounter;

            if (deadTuples != null) {
                for (Tuple t : deadTuples) {
                    cachedInternalCounter.remove(gc, qc, t);
                }
            }

            if (newTuples != null) {
                for (Tuple t : newTuples) {
                    cachedInternalCounter.add(gc, qc, t);
                }
            }
        }

        // Just copy.
        localContext.setDeadTuples(deadTuples);
        localContext.setNewTuples(newTuples);
    }

    @Override
    public boolean producesDStream() {
        if (this.lastAggregatedStream == null) {
            return windowOwner.canSendDeletesToSubStream();
        }

        return true;
    }

    public boolean canDiscard() {
        if (listener != null) {
            boolean b = listener.canDiscard();

            if (b == false) {
                return b;
            }
        }

        if (lastAggregatedStream != null) {
            try {
                return lastAggregatedStream.getNumOfAggregatedTuples() == 0;
            }
            catch (Exception e) {
                Logger logger = Registry.getInstance().getComponent(Logger.class);

                if (e instanceof KnownResource) {
                    logger.log(Logger.LogLevel.ERROR, e);
                }
                else {
                    logger.log(Logger.LogLevel.ERROR, new CustomException(getResourceId(), e));
                }
            }
        }

        return internalCounter.calculateAggregateInteger() == 0;
    }

    @Override
    public void discard(Context context) {
        super.discard(context);

        lastAggregatedStream = null;
        internalCounter = null;
    }
}
