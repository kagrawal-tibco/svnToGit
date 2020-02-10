package com.tibco.cep.query.stream.partition;

import com.tibco.cep.query.stream.aggregate.AggregatedStream;
import com.tibco.cep.query.stream.context.Context;
import com.tibco.cep.query.stream.context.LocalContext;
import com.tibco.cep.query.stream.core.AbstractStream;
import com.tibco.cep.query.stream.core.DSubStream;
import com.tibco.cep.query.stream.core.Registry;
import com.tibco.cep.query.stream.core.Stream;
import com.tibco.cep.query.stream.core.StreamListener;
import com.tibco.cep.query.stream.core.SubStream;
import com.tibco.cep.query.stream.core.SubStreamOwner;
import com.tibco.cep.query.stream.group.GroupAggregateTransformer;
import com.tibco.cep.query.stream.group.GroupKey;
import com.tibco.cep.query.stream.monitor.CustomRuntimeException;
import com.tibco.cep.query.stream.monitor.Logger;
import com.tibco.cep.query.stream.monitor.Logger.LogLevel;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.partition.accumulator.GroupedAggregatedResultAccumulator;
import com.tibco.cep.query.stream.partition.accumulator.GroupedResultAccumulator;
import com.tibco.cep.query.stream.partition.accumulator.SubStreamCycleResultAccumulator;
import com.tibco.cep.query.stream.partition.accumulator.WindowResultAccumulator;
import com.tibco.cep.query.stream.partition.purge.ImmediateWindowPurgeAdvice;
import com.tibco.cep.query.stream.partition.purge.WindowPurgeManager;
import com.tibco.cep.query.stream.tuple.TupleInfo;

/*
 * Author: Ashwin Jayaprakash Date: Oct 1, 2007 Time: 3:38:02 PM
 */

public abstract class Window extends AbstractStream<SubStream> implements SubStream<WindowOwner> {
    private boolean cycleStartDone;

    protected WindowPurgeManager purgeManager;

    protected AggregatedSubStreamOwner aggregatedSubStreamOwner;

    protected SubStreamCycleResultAccumulator accumulator;

    protected SubStream lastStreamInFlow;

    protected WindowOwner windowOwner;

    protected ImmediateWindowPurgeAdvice purgeAdvice;

    protected GroupKey groupKey;

    public Window(ResourceId id, TupleInfo outputInfo, GroupKey groupKey, WindowOwner windowOwner) {
        super(null /* No source. */, id, outputInfo);

        this.groupKey = groupKey;
        this.windowOwner = windowOwner;
    }

    /**
     * @param groupAggregateTransformer Can be <code>null</code>.
     * @param theLastStreamInFlow       If <code>null</code>, then self/<code>this</code> will be
     *                                  used. Self/<code>this</code> can be sent if there is no
     *                                  other sub-stream. <code>Group -> Window -> [Aggregate] ->
     *                                  ResultCollectorAndTransformer</code>. Can't be
     *                                  <code>null</code>/<code>this</code> if {@link
     *                                  GroupAggregateTransformer#hasAggregates()} is
     *                                  <code>true</code>, in which case it has to be an {@link
     *                                  com.tibco.cep.query.stream.aggregate.AggregatedStream}.
     * @param purgeManager              Can be <code>null</code>.
     * @throws CustomRuntimeException If the setup does not match the sequence of components
     *                                described above.
     */
    public void init(GroupAggregateTransformer groupAggregateTransformer,
                     SubStream theLastStreamInFlow,
                     WindowPurgeManager purgeManager) {
        this.lastStreamInFlow = (theLastStreamInFlow == null) ? this : theLastStreamInFlow;

        //-----------

        ResourceId accumulatorId = new ResourceId(getResourceId(), "Accumulator");
        if (groupAggregateTransformer == null) {
            accumulator = new WindowResultAccumulator(this.lastStreamInFlow, accumulatorId);
        }
        else if (groupAggregateTransformer.hasAggregates()) {
            // Say what the Window says.
            boolean sendDStream = producesDStream();
            this.aggregatedSubStreamOwner = new AggregatedSubStreamOwner(sendDStream);

            this.lastStreamInFlow.setOwner(this.aggregatedSubStreamOwner);

            AggregatedStream aggregatedStream = (AggregatedStream) this.lastStreamInFlow;
            accumulator = new GroupedAggregatedResultAccumulator(aggregatedStream, accumulatorId,
                    groupAggregateTransformer, groupKey.getGroupColumns());
        }
        else {
            accumulator = new GroupedResultAccumulator(this.lastStreamInFlow, accumulatorId,
                    groupAggregateTransformer, groupKey.getGroupColumns());
        }

        //-----------

        this.purgeManager = purgeManager;
        if (this.purgeManager != null) {
            addAdditionalListener(this.purgeManager.getWindowSpy());
        }
    }

    @Override
    public void setListener(SubStream listener) {
        super.setListener(listener);

        if (listener instanceof DSubStream) {
            listener.setOwner(new ProxySubStreamOwner(producesDStream()));
        }
    }

    public GroupKey getGroupKey() {
        return groupKey;
    }

    public WindowOwner getOwner() {
        return windowOwner;
    }

    /**
     * @throws UnsupportedOperationException
     */
    public void setOwner(WindowOwner owner) {
        throw new UnsupportedOperationException();
    }

    public Stream getLastStreamInFlow() {
        return lastStreamInFlow;
    }

    public SubStreamCycleResultAccumulator getAccumulator() {
        return accumulator;
    }

    public WindowPurgeManager getPurgeManager() {
        return purgeManager;
    }

    public abstract boolean requiresTupleDeletes();

    public abstract int getUnprocessedBufferSize();

    /**
     * @return Can be <code>null</code>.
     */
    public ImmediateWindowPurgeAdvice getPurgeAdvice() {
        return purgeAdvice;
    }

    public boolean isCycleStartDone() {
        return cycleStartDone;
    }

    // ---------

    @Override
    public abstract boolean producesDStream();

    /**
     * Use {@link #isCycleStartDone()}.
     */
    public void cycleStart(Context context) throws Exception {
        if (cycleStartDone == false) {
            cycleStartDone = true;

            if (listener != null) {
                listener.cycleStart(context);
            }

            if (purgeManager != null) {
                purgeAdvice = purgeManager.cycleStart(context);
            }

            if (additionalListeners != null) {
                for (StreamListener additionalListener : additionalListenersCache) {
                    ((SubStream) additionalListener).cycleStart(context);
                }
            }
        }
    }

    public void cycleEnd(Context context) throws Exception {
        // Reset.
        cycleStartDone = false;

        if (additionalListeners != null) {
            for (StreamListener additionalListener : additionalListenersCache) {
                ((SubStream) additionalListener).cycleEnd(context);
            }
        }

        if (purgeManager != null) {
            purgeManager.cycleEnd(context);
        }

        if (listener != null) {
            listener.cycleEnd(context);
        }
    }

    /**
     * Does nothing. Use {@link #discard(com.tibco.cep.query.stream.context.Context)} instead.
     *
     * @throws Exception
     */
    @Override
    public final void stop() throws Exception {
    }

    public void discard(Context context) {
        if (aggregatedSubStreamOwner != null) {
            LocalContext originalContext = context.getLocalContext();

            LocalContext nullLocalContext = new LocalContext(true);
            try {
                context.setLocalContext(nullLocalContext);

                /*
                 * The output gets ignored. This is just to purge the last [0, 0, ..]
                 * aggregate-column Tuples from the AggregatedStream.
                 */
                try {
                    lastStreamInFlow.cycleStart(context);
                    try {
                        lastStreamInFlow.process(context);
                    }
                    finally {
                        lastStreamInFlow.cycleEnd(context);
                    }
                }
                catch (Exception e) {
                    Logger logger = Registry.getInstance().getComponent(Logger.class);

                    String s = "Error occurred during Aggregate Sub-Stream cleanup: "
                            + lastStreamInFlow.getResourceId();
                    logger.log(LogLevel.WARNING, s, e);
                }
            }
            finally {
                // Re-instate the original.
                context.setLocalContext(originalContext);
            }
        }

        //-----------

        if (purgeManager != null) {
            this.getAdditionalListeners().remove(purgeManager.getWindowSpy());

            purgeManager.discard(context);
        }

        //-----------

        try {
            super.stop();
        }
        catch (Exception e) {
            Logger logger = Registry.getInstance().getComponent(Logger.class);

            String s = "Error occurred during Sub-Stream cleanup" + getResourceId();
            logger.log(LogLevel.WARNING, s, e);
        }

        //-----------

        accumulator = null;
        aggregatedSubStreamOwner = null;
        lastStreamInFlow = null;
        purgeAdvice = null;
        purgeManager = null;
        groupKey = null;
        windowOwner = null;

        if (resourceId != null) {
            resourceId.discard();
        }
    }

    // ----------

    protected class ProxySubStreamOwner implements SubStreamOwner<SubStream> {
        protected final boolean sendDStream;

        public ProxySubStreamOwner(boolean sendDStream) {
            this.sendDStream = sendDStream;
        }

        public void scheduleSubStreamForNextCycle(Context context, SubStream subStream) {
            if (Window.this.windowOwner != null) {
                Window.this.windowOwner.scheduleSubStreamForNextCycle(context, Window.this);
            }
        }

        public boolean canSendDeletesToSubStream() {
            return sendDStream;
        }
    }

    protected static class AggregatedSubStreamOwner implements SubStreamOwner<SubStream> {
        protected final boolean sendDStream;

        public AggregatedSubStreamOwner(boolean sendDStream) {
            this.sendDStream = sendDStream;
        }

        public void scheduleSubStreamForNextCycle(Context context, SubStream subStream) {
            /*
             * Do nothing. This will get invoked by the AggregateStream, just
             * before the Window gets discarded.
             */
        }

        public boolean canSendDeletesToSubStream() {
            return sendDStream;
        }
    }
}
