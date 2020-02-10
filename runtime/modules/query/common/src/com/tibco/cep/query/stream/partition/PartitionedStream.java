package com.tibco.cep.query.stream.partition;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.tibco.cep.query.stream.aggregate.AggregateInfo;
import com.tibco.cep.query.stream.aggregate.AggregateItemInfo;
import com.tibco.cep.query.stream.context.Context;
import com.tibco.cep.query.stream.context.DefaultGlobalContext;
import com.tibco.cep.query.stream.context.DefaultQueryContext;
import com.tibco.cep.query.stream.context.LocalContext;
import com.tibco.cep.query.stream.core.AbstractStream;
import com.tibco.cep.query.stream.core.IStream;
import com.tibco.cep.query.stream.core.Stream;
import com.tibco.cep.query.stream.expression.TupleValueExtractor;
import com.tibco.cep.query.stream.group.GroupAggregateInfo;
import com.tibco.cep.query.stream.group.GroupAggregateItemInfo;
import com.tibco.cep.query.stream.group.GroupAggregateTransformer;
import com.tibco.cep.query.stream.group.GroupChanges;
import com.tibco.cep.query.stream.group.GroupKeeper;
import com.tibco.cep.query.stream.group.GroupKey;
import com.tibco.cep.query.stream.monitor.CustomMultiSourceException;
import com.tibco.cep.query.stream.monitor.Flags;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.partition.accumulator.SubStreamCycleResultAccumulator;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.tuple.TupleInfo;
import com.tibco.cep.query.stream.util.AppendOnlyQueue;
import com.tibco.cep.query.stream.util.CustomCollection;
import com.tibco.cep.query.stream.util.CustomHashSet;
import com.tibco.cep.query.stream.util.LazyContainerTreeMap;
import com.tibco.cep.query.stream.util.ReusableIterator;

/*
 * Author: Ashwin Jayaprakash Date: Oct 1, 2007 Time: 4:52:32 PM
 */

/**
 * For an expression such as: <p/>
 * <pre>
 * select a, b/100, (avg(a)/max(a) * 1000), count(1), avg( (a*100)/(c-d) )
 * ..
 * group by a, b/100
 * having min(a) &lt;= max(b) + 1000
 * </pre>
 * <p/> This Stream would produce a raw Aggregated Stream with this output containing all the
 * aggregates used in the Query: <p/>
 * <pre>
 * Tuple{a, b/100, avg(a), max(a), count(1), min(a), max(b), avg( (a*100)/(c-d) )}
 * </pre>
 * <p/> <p> Of course, the {@link GroupAggregateInfo} would have to look like this. From this
 * output, further Filtering and Transformation would have to be performed. </p> <p> If
 * <code>stateful-capture: all</code> is specified, then use this Stream directly. If
 * <code>stateful-capture: new</code> is specified, then the {@link IStream} must be added before
 * this Stream. In that case, this Stream will never receive dead Tuples and so will never clear the
 * groups and might result in a Mem leak. </p>
 */
public class PartitionedStream extends AbstractStream implements WindowOwner {
    private AppendOnlyQueue<Tuple> resultAdds;

    private AppendOnlyQueue<Tuple> resultDeletes;

    /**
     * Windows that were touched in the current cycle.
     */
    protected CustomHashSet<Window> dirtyWindows;

    protected ReusableIterator<Window> dirtyWindowsIter;

    protected SubStreamCycleResultAccumulator.ResultHolder accumulatorResults;

    protected HashMap<GroupKey, Window> windows;

    private final boolean windowsRequireTupleDeletes;

    protected GroupAggregateInfo groupAggregateInfo;

    protected WindowBuilder windowBuilder;

    protected GroupKeeper groupKeeper;

    /**
     * Can be <code>null</code>.
     */
    protected AggregateInfo aggregateInfo;

    /**
     * Can be <code>null</code>.
     */
    protected GroupAggregateTransformer transformer;

    /**
     * Can be <code>null</code>.
     */
    protected WindowInfo windowInfo;

    protected LazyContainerTreeMap<Long, GroupKey, CustomHashSet<GroupKey>> timeScheduledWindowHashes;

    protected ScheduledWindows scheduledWindows;

    protected LocalContext nullLocalContext;

    protected LocalContext aggregateInputContext;

    private final boolean subStreamProducesDStream;

    /**
     * @param source
     * @param id
     * @param outputInfo
     * @param groupAggregateInfo Can be <code>null</code>.
     * @param windowInfo         Can be <code>null</code>.
     * @param windowBuilder
     */
    public PartitionedStream(Stream source, ResourceId id, TupleInfo outputInfo,
                             GroupAggregateInfo groupAggregateInfo, WindowInfo windowInfo,
                             WindowBuilder windowBuilder) {
        super(source, id, outputInfo);

        this.groupAggregateInfo = groupAggregateInfo;
        this.windowInfo = windowInfo;
        this.windowBuilder = windowBuilder;

        // ---------

        /*
        Aggregates and the Groups are produced separately. They are then merged together based on
        the overall column order specified.

        The Group-columns appear in the same order as they appear in the results - but all bunched
        together.

        The Aggregate-columns appear in the same order as they appear in the results - but all
        bunched together.
        */

        LinkedHashMap<String, TupleValueExtractor> groupColumnExtractors =
                new LinkedHashMap<String, TupleValueExtractor>();
        LinkedHashMap<String, Integer> groupColumnPositions = new LinkedHashMap<String, Integer>();

        LinkedHashMap<String, AggregateItemInfo> aggregateItemInfos =
                new LinkedHashMap<String, AggregateItemInfo>();
        LinkedHashMap<String, Integer> aggregateColumnPositions =
                new LinkedHashMap<String, Integer>();

        if (this.groupAggregateInfo != null) {
            LinkedHashMap<String, GroupAggregateItemInfo> specifiedPositions =
                    this.groupAggregateInfo
                            .getItemInfos();
            int overallColumnPosition = 0;
            for (String key : specifiedPositions.keySet()) {
                GroupAggregateItemInfo gaItemInfo = specifiedPositions.get(key);

                if (gaItemInfo.getAggregateItemInfo() != null) {
                    aggregateItemInfos.put(key, gaItemInfo.getAggregateItemInfo());

                    aggregateColumnPositions.put(key, overallColumnPosition);
                }
                else {
                    groupColumnExtractors.put(key, gaItemInfo.getGroupItemInfo()
                            .getGroupColumnExtractor());

                    groupColumnPositions.put(key, overallColumnPosition);
                }

                overallColumnPosition++;
            }
        }

        this.groupKeeper = new GroupKeeper(groupColumnExtractors, sourceProducesDStream);

        this.aggregateInfo = aggregateItemInfos.isEmpty() ? null : new AggregateInfo(
                aggregateItemInfos);
        this.transformer = useTransformer(aggregateItemInfos) ? new GroupAggregateTransformer(
                outputInfo, groupColumnPositions, aggregateColumnPositions) : null;

        // ---------

        this.windows = new HashMap<GroupKey, Window>();

        this.dirtyWindows = new CustomHashSet<Window>();
        this.dirtyWindowsIter = this.dirtyWindows.iterator();

        this.accumulatorResults = new SubStreamCycleResultAccumulator.ResultHolder();

        LazyContainerTreeMap.ValueCollectionCreator<GroupKey, CustomHashSet<GroupKey>> creator =
                new LazyContainerTreeMap.ValueCollectionCreator<GroupKey, CustomHashSet<GroupKey>>() {
                    public CustomHashSet<GroupKey> createContainer() {
                        return new CustomHashSet<GroupKey>();
                    }
                };
        this.timeScheduledWindowHashes =
                new LazyContainerTreeMap<Long, GroupKey, CustomHashSet<GroupKey>>(
                        creator);

        this.scheduledWindows = new ScheduledWindows();

        this.nullLocalContext = new LocalContext(true);
        this.aggregateInputContext = new LocalContext();

        Object[] columns = new Object[this.groupKeeper.getGroupColumnsLength()];
        GroupKey tempGroupKey = this.groupKeeper.createKey(-1, columns);
        Window disposableWindow = buildWindow(tempGroupKey);
        this.windowsRequireTupleDeletes = disposableWindow.requiresTupleDeletes();
        this.subStreamProducesDStream = disposableWindow.producesDStream();

        DefaultQueryContext nullQueryContext = new DefaultQueryContext("$dispose-region", "$dispose-query");
        DefaultGlobalContext nullGlobalContext = new DefaultGlobalContext();
        Context disposableContext = new Context(nullGlobalContext, nullQueryContext);
        disposableContext.setLocalContext(this.nullLocalContext);
        disposableWindow.discard(disposableContext);
        nullQueryContext.discard();
    }

    @Override
    public void stop() throws Exception {
        super.stop();

        groupKeeper.discard();
        groupKeeper = null;

        windows.clear();
        windows = null;

        dirtyWindows.clear();
        dirtyWindows = null;
        dirtyWindowsIter = null;

        accumulatorResults.clear();
        accumulatorResults = null;

        timeScheduledWindowHashes.clear();
        timeScheduledWindowHashes = null;

        scheduledWindows.discard();
        scheduledWindows = null;

        groupAggregateInfo = null;

        windowBuilder = null;

        aggregateInfo = null;
        windowInfo = null;

        if (transformer != null) {
            transformer.discard();
            transformer = null;
        }

        nullLocalContext.clear();
        nullLocalContext = null;

        aggregateInputContext.clear();
        aggregateInputContext = null;

        if (resultAdds != null) {
            resultAdds.clear();
            resultAdds = null;

            resultDeletes.clear();
            resultDeletes = null;
        }
    }

    /**
     * In some cases - like Sliding/Time/Tumbling windows, if there is no aggregate-info, then the
     * Transformer is not used at all. That would mean that the output of this Stream will be the
     * same as the input. Otherwise, the output will be just the Group-by columns since there are no
     * aggregates.
     *
     * @param aggregateItemInfos
     * @return
     */
    protected boolean useTransformer(LinkedHashMap<String, AggregateItemInfo> aggregateItemInfos) {
        return aggregateItemInfos.isEmpty() == false;
    }

    private Window buildWindow(GroupKey groupKey) {
        String windowId = "Window::" + groupKey.getGroupHash();

        Window window = windowBuilder.buildAndInit(resourceId, windowId, groupKey, aggregateInfo,
                windowInfo, this, transformer);

        return window;
    }

    @Override
    protected final void doProcessing(Context context) throws Exception {
        super.doProcessing(context);

        final DefaultQueryContext qc = context.getQueryContext();

        // ----------

        dirtyWindows.clear();

        if (resultAdds == null) {
            if (transformer != null) {
                transformer.init(qc.getIdGenerator());
            }

            resultDeletes = new AppendOnlyQueue<Tuple>(qc.getArrayPool());
            resultAdds = new AppendOnlyQueue<Tuple>(qc.getArrayPool());
        }

        // ----------

        /*
        * A little paranoid exception handling here to make sure that there are
        * no dangling Windows/resources etc.
        */
        CustomMultiSourceException multiSourceException = null;

        try {
            try {
                extractAndHandleWindowChanges(context);

                //This should always be invoked after extractAndHandleWindowChanges(...).
                handleScheduledWindows(context);
            }
            catch (Throwable t) {
                multiSourceException = new CustomMultiSourceException(getResourceId());
                multiSourceException.addSource(new CustomMultiSourceException.Source(t));
            }

            // ----------

            multiSourceException = handleDirtyWindowCleanup(context, multiSourceException);

            // ----------

            try {
                generateResults(context, (resultAdds.isEmpty() ? null : resultAdds),
                        (resultDeletes.isEmpty() ? null : resultDeletes));
            }
            catch (Throwable t) {
                if (multiSourceException == null) {
                    multiSourceException = new CustomMultiSourceException(getResourceId());
                }

                multiSourceException.addSource(new CustomMultiSourceException.Source(
                        "Partition result generation error.", t));
            }

            // ----------

            if (scheduledWindows.scheduledWindowsForNextCycle.size() > 0) {
                qc.addStreamForNextCycle(this);
            }

            scheduledWindows.swap();

            // ----------

            if (timeScheduledWindowHashes.size() > 0) {
                qc.scheduleStreamAt(this, timeScheduledWindowHashes.firstKey());
            }
        }
        catch (Throwable t) {
            if (multiSourceException == null) {
                multiSourceException = new CustomMultiSourceException(getResourceId());
            }

            multiSourceException
                    .addSource(
                            new CustomMultiSourceException.Source("Partition execution error.", t));
        }

        if (multiSourceException != null) {
            throw multiSourceException;
        }
    }

    private CustomMultiSourceException handleDirtyWindowCleanup(Context context,
                                                                CustomMultiSourceException multiSourceException) {
        final ReusableIterator<Window> iter = dirtyWindowsIter;
        final HashMap<GroupKey, Window> cachedWindows = windows;
        final SubStreamCycleResultAccumulator.ResultHolder cachedAccumulatorResults =
                accumulatorResults;
        final AppendOnlyQueue<Tuple> cachedResultDeletes = resultDeletes;
        final AppendOnlyQueue<Tuple> cachedResultAdds = resultAdds;

        for (iter.reset(); iter.hasNext();) {
            Window window = iter.next();

            try {
                window.cycleEnd(context);

                // ---------

                // Collect output from all groups.
                SubStreamCycleResultAccumulator accumulator = window.getAccumulator();

                try {
                    accumulator.fillWithAccumulatedResults(cachedAccumulatorResults);

                    Collection<? extends Tuple> windowDeadTuples =
                            cachedAccumulatorResults.getDeletes();

                    Collection<? extends Tuple> windowNewTuples =
                            cachedAccumulatorResults.getAdditions();

                    if (windowDeadTuples != null) {
                        cachedResultDeletes.addAll(windowDeadTuples);
                    }
                    if (windowNewTuples != null) {
                        cachedResultAdds.addAll(windowNewTuples);
                    }

                    onWindowResult(window, windowNewTuples, windowDeadTuples);
                }
                finally {
                    cachedAccumulatorResults.clear();
                }

                // ---------

                // Window cleanup.
                if (window.canDiscard()) {
                    cachedWindows.remove(window.getGroupKey());

                    window.discard(context);
                }
            }
            catch (Throwable t) {
                if (multiSourceException == null) {
                    multiSourceException = new CustomMultiSourceException(getResourceId());
                }

                multiSourceException.addSource(new CustomMultiSourceException.Source(
                        "Window cleanup error.", t));
            }

            iter.remove();
        }

        return multiSourceException;
    }

    @Override
    protected void endProcessing(Context context) {
        super.endProcessing(context);

        if (resultAdds != null) {
            resultAdds.clear();
            resultDeletes.clear();
        }
    }

    /**
     * @param context
     * @return
     * @throws Exception
     */
    protected void extractAndHandleWindowChanges(Context context)
            throws Exception {
        Map<? extends GroupKey, ? extends GroupChanges> changes =
                groupKeeper.extractChanges(context);
        if (changes.size() == 0) {
            return;
        }

        final boolean cachedWindowsRequireTupleDeletes = windowsRequireTupleDeletes;
        final HashMap<GroupKey, Window> cachedWindows = windows;
        final LocalContext cachedAggregateInputContext = aggregateInputContext;
        final CustomHashSet<Window> cachedDirtyWindows = dirtyWindows;

        for (GroupKey groupKey : changes.keySet()) {
            GroupChanges gc = changes.get(groupKey);
            AppendOnlyQueue<Tuple> groupAdditions = gc.getAdditions();
            AppendOnlyQueue<Tuple> groupDeletions = gc.getDeletions();
            Object[] groupColumns = groupKey.getGroupColumns();

            if (groupAdditions == null && groupDeletions == null) {
                // Nothing else to do.
                continue;
            }

            if (groupDeletions != null && cachedWindowsRequireTupleDeletes == false) {
                if (Flags.TRACK_TUPLE_REFS) {
                    // End of the road for these Tuples.
                    for (Tuple t : groupDeletions) {
                        t.decrementRefCount();
                    }
                }

                if (groupAdditions == null) {
                    // Nothing else to do.
                    continue;
                }

                // Windows don't need these anyway.
                groupDeletions = null;
            }

            Window window = cachedWindows.get(groupKey);
            if (window == null) {
                window = buildWindow(groupKey);

                cachedWindows.put(groupKey, window);
            }

            if (groupAdditions != null && cachedWindowsRequireTupleDeletes == false) {
                /*
                 * If the Windows do not need Tuple deletes, then it means that
                 * the Tuples are maintained by Windows with a different
                 * lifecycle. The Window could purge Tuples even before the
                 * Partition receives delete notifications for those Tuples. So,
                 * the Partition maintains an additional count in such cases.
                 */
                if (Flags.TRACK_TUPLE_REFS) {
                    for (Tuple t : groupAdditions) {
                        t.incrementRefCount();
                    }
                }
            }

            // ---------

            LocalContext originalCtx = context.getLocalContext();
            try {
                cachedAggregateInputContext.clear();
                cachedAggregateInputContext.setNewTuples(groupAdditions);
                cachedAggregateInputContext.setDeadTuples(groupDeletions);

                context.setLocalContext(cachedAggregateInputContext);

                //------------

                if (cachedDirtyWindows.add(window)) {
                    window.cycleStart(context);
                }

                window.process(context);
            }
            finally {
                context.setLocalContext(originalCtx);
            }
        }
    }

    protected void handleScheduledWindows(Context context) throws Exception {
        final DefaultQueryContext qc = context.getQueryContext();
        final long cycleTS = qc.getCurrentCycleTimestamp();

        final LazyContainerTreeMap<Long, GroupKey, CustomHashSet<GroupKey>> cachedTimeScheduledWindowHashes =
                timeScheduledWindowHashes;
        final CustomHashSet<Window> cachedCurrCycle = scheduledWindows.scheduledWindowsForCurrCycle;
        final HashMap<GroupKey, Window> cachedWindows = windows;

        /*
        * Check if the future time scheduled Windows are ripe for scheduling
        * now.
        */
        while (cachedTimeScheduledWindowHashes.size() > 0) {
            Long earliestScheduleTime = cachedTimeScheduledWindowHashes.firstKey();
            if (earliestScheduleTime > cycleTS) {
                // Nothing for now. Everything is in the future.
                break;
            }

            Object content = cachedTimeScheduledWindowHashes.remove(earliestScheduleTime);

            if (content instanceof CustomHashSet) {
                CustomHashSet<GroupKey> set = (CustomHashSet<GroupKey>) content;

                for (GroupKey groupKey : set) {
                    Window scheduledWindow = cachedWindows.get(groupKey);
                    //Hasn't been purged and discarded in some previous cycle.
                    if (scheduledWindow != null) {
                        cachedCurrCycle.add(scheduledWindow);
                    }
                }

                set.clear();
            }
            else {
                GroupKey groupKey = (GroupKey) content;

                Window scheduledWindow = cachedWindows.get(groupKey);
                if (scheduledWindow != null) {
                    cachedCurrCycle.add(scheduledWindow);
                }
            }

            qc.removeStreamScheduledAt(this, earliestScheduleTime);
        }

        // ---------

        if (cachedCurrCycle.size() > 0) {
            final LocalContext beforeForcingCtx = context.getLocalContext();

            try {
                context.setLocalContext(nullLocalContext);

                final CustomHashSet<Window> cachedDirtyWindows = dirtyWindows;
                final ReusableIterator<Window> iter =
                        scheduledWindows.scheduledWindowsForCurrCycleIter;

                for (iter.reset(); iter.hasNext();) {
                    Window window = iter.next();
                    iter.remove();

                    //This window has already have been processed/touched. Skip it.
                    if (cachedDirtyWindows.contains(window)) {
                        continue;
                    }

                    //--------

                    if (cachedDirtyWindows.add(window)) {
                        window.cycleStart(context);
                    }

                    window.process(context);
                }
            }
            finally {
                context.setLocalContext(beforeForcingCtx);
            }
        }
    }

    protected void onWindowResult(Window window,
                                  Collection<? extends Tuple> windowNewTuples,
                                  Collection<? extends Tuple> windowDeadTuples) {
    }

    /**
     * Records and outputs the incremental changes.
     *
     * @param context
     * @param sessionAdds    Can be <code>null</code>.
     * @param sessionDeletes Can be <code>null</code>.
     * @throws Exception
     */
    protected void generateResults(Context context,
                                   CustomCollection<Tuple> sessionAdds,
                                   CustomCollection<Tuple> sessionDeletes) throws Exception {
        localContext.setNewTuples(sessionAdds);
        localContext.setDeadTuples(sessionDeletes);
    }

    // ----------

    public void scheduleWindowAt(Context context, Window window, Long timestamp) {
        /*
        Don't store the window directly. Because the Window might get scheduled at some time in the
        future and then get purged and discarded completely due to some purge-advice way before the
        initial scheduled time. In such cases, the Window remains in the scheduled-window list when
        it has long been removed from the master-window list - thus causing bugs and mem leaks.

        So, just store the window-hash and when the time comes for scheduling, verify if the window
        is still in the master-list.

        The original Window might get discarded and sometime later be replaced by new one under the
        same hash, but that does not cause any problems.
        */
        timeScheduledWindowHashes.put(timestamp, window.getGroupKey());
    }

    public boolean canSendDeletesToSubStream() {
        return source.producesDStream();
    }

    public void scheduleSubStreamForNextCycle(Context context, Window window) {
        scheduledWindows.scheduledWindowsForNextCycle.add(window);
    }

    @Override
    public boolean producesDStream() {
        return subStreamProducesDStream;
    }

    // ----------

    protected static class ScheduledWindows {
        protected CustomHashSet<Window> scheduledWindowsForCurrCycle;

        protected ReusableIterator<Window> scheduledWindowsForCurrCycleIter;

        protected CustomHashSet<Window> scheduledWindowsForNextCycle;

        protected ReusableIterator<Window> scheduledWindowsForNextCycleIter;

        public ScheduledWindows() {
            this.scheduledWindowsForCurrCycle = new CustomHashSet<Window>();
            this.scheduledWindowsForCurrCycleIter = this.scheduledWindowsForCurrCycle.iterator();

            this.scheduledWindowsForNextCycle = new CustomHashSet<Window>();
            this.scheduledWindowsForNextCycleIter = this.scheduledWindowsForNextCycle.iterator();
        }

        public void swap() {
            CustomHashSet<Window> tmpSet = scheduledWindowsForCurrCycle;
            ReusableIterator<Window> tmpIter = scheduledWindowsForCurrCycleIter;

            scheduledWindowsForCurrCycle = scheduledWindowsForNextCycle;
            scheduledWindowsForCurrCycleIter = scheduledWindowsForNextCycleIter;

            scheduledWindowsForNextCycle = tmpSet;
            scheduledWindowsForNextCycleIter = tmpIter;
        }

        public void discard() {
            scheduledWindowsForCurrCycle.clear();
            scheduledWindowsForCurrCycle = null;
            scheduledWindowsForCurrCycleIter = null;

            scheduledWindowsForNextCycle.clear();
            scheduledWindowsForNextCycle = null;
            scheduledWindowsForNextCycleIter = null;
        }
    }
}