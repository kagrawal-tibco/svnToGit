package com.tibco.cep.query.stream.partition.purge;

import java.util.Map;

import com.tibco.cep.query.stream.aggregate.AggregatedStream;
import com.tibco.cep.query.stream.context.Context;
import com.tibco.cep.query.stream.core.AbstractStream;
import com.tibco.cep.query.stream.core.SubStream;
import com.tibco.cep.query.stream.core.SubStreamOwner;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.partition.Window;

/*
* Author: Ashwin Jayaprakash Date: Apr 7, 2009 Time: 3:20:59 PM
*/
public class WindowSpyChannelEnd extends AbstractStream<SubStream>
        implements SubStream<SubStreamOwner> {
    protected final Window window;

    protected final WindowStats windowStats;

    protected AggregatedStream aggregatedSource;

    /**
     * Can be <code>null</code>.
     */
    protected String[] aggregatedColumnAliases;

    protected SubStreamOwner subStreamOwner;

    /**
     * @param id
     * @param source
     * @param window
     * @param windowStats
     */
    public WindowSpyChannelEnd(ResourceId id, SubStream source,
                               Window window, WindowStats windowStats) {
        super(source, id, source.getOutputInfo());

        this.window = window;
        this.windowStats = windowStats;
    }

    /**
     * @param id
     * @param aggregatedSource
     * @param window
     * @param windowStats
     * @param aggregatedColumnAliases
     */
    public WindowSpyChannelEnd(ResourceId id, AggregatedStream aggregatedSource,
                               Window window, WindowStats windowStats,
                               String[] aggregatedColumnAliases) {
        this(id, aggregatedSource, window, windowStats);

        this.aggregatedColumnAliases = aggregatedColumnAliases;
        this.aggregatedSource = aggregatedSource;
    }

    public Window getWindow() {
        return window;
    }

    public WindowStats getWindowStats() {
        return windowStats;
    }

    public void setOwner(SubStreamOwner owner) {
        this.subStreamOwner = owner;
    }

    public SubStreamOwner getOwner() {
        return subStreamOwner;
    }

    public void cycleStart(Context context) throws Exception {
        if (listener != null) {
            listener.cycleStart(context);
        }
    }

    public void cycleEnd(Context context) throws Exception {
        if (listener != null) {
            listener.cycleEnd(context);
        }

        //-------------

        Map<String, Object> stats = windowStats.getStats();

        if (aggregatedSource != null) {
            handleOtherAggregates(stats, context);
        }

        int pendingCount = window.getUnprocessedBufferSize();
        stats.put(WindowStats.ALIAS_PENDING_COUNT, pendingCount);
    }

    /**
     * If {@link #aggregatedSource} is not <code>null</code>.
     *
     * @param stats
     * @param context
     */
    protected void handleOtherAggregates(Map<String, Object> stats, Context context) {
        Object[] newAggregateAtCycleEnd = aggregatedSource.getNewAggregateAtCycleEnd();
        if (newAggregateAtCycleEnd == null) {
            return;
        }

        final String[] cachedAggregatedColumnAliases = aggregatedColumnAliases;
        for (int i = 0; i < cachedAggregatedColumnAliases.length; i++) {
            /*
            * Will not have any Shared Objects. All references are
            * direct.
            */
            stats.put(cachedAggregatedColumnAliases[i], newAggregateAtCycleEnd[i]);
        }
    }

    public boolean canDiscard() {
        return ((SubStream) source).canDiscard();
    }

    @Override
    public boolean producesDStream() {
        return source.producesDStream();
    }

    @Override
    public void stop() throws Exception {
        super.stop();

        if (resourceId != null) {
            resourceId.discard();
        }
    }
}
