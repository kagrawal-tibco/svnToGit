package com.tibco.cep.query.stream.partition;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;

import com.tibco.cep.query.stream.context.Context;
import com.tibco.cep.query.stream.context.DefaultGlobalContext;
import com.tibco.cep.query.stream.context.DefaultQueryContext;
import com.tibco.cep.query.stream.group.GroupKey;
import com.tibco.cep.query.stream.misc.Clock;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.partition.TimeWindowInfo.TupleTimestampExtractor;
import com.tibco.cep.query.stream.partition.purge.ImmediateWindowPurgeAdvice;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.tuple.TupleInfo;
import com.tibco.cep.query.stream.util.NullElementCollection;
import com.tibco.cep.query.stream.util.ReusableIterator;

/*
 * Author: Ashwin Jayaprakash Date: Nov 9, 2007 Time: 5:11:11 PM
 */

public class TimeWindow extends CommonWindow {
    protected PriorityQueue<Tuple> timeWindow;

    private long windowDurationMillis;

    private TupleTimestampExtractor tsExtractor;

    /**
     * Fixed, 0 size trigger.
     */
    private NullElementCollection<Tuple> nullTriggersForCycle;

    /**
     * Always empty.
     */
    private ReusableIterator<Tuple> nullTriggersForCycleIter;

    protected TupleTimestampComparator timestampComparator;

    protected TimeWindowInfo timeWindowInfo;

    public TimeWindow(ResourceId id, TupleInfo outputInfo, GroupKey groupKey,
                      WindowOwner windowOwner, TimeWindowInfo windowInfo) {
        super(id, outputInfo, groupKey, windowOwner, windowInfo,
                new PriorityQueue<Tuple>(15, new TupleTimestampComparator()));

        this.timeWindowInfo = windowInfo;
        this.timeWindow = (PriorityQueue<Tuple>) this.window;

        this.timestampComparator = (TupleTimestampComparator) this.timeWindow.comparator();
        TupleTimestampExtractor timestampExtractor = this.timeWindowInfo.getTimestampExtractor();
        this.timestampComparator.init(timestampExtractor);

        this.nullTriggersForCycle = new NullElementCollection<Tuple>();
        this.nullTriggersForCycleIter = this.nullTriggersForCycle.iterator();

        this.windowDurationMillis = windowInfo.getTimeMillis();
        this.tsExtractor = timestampExtractor;
    }

    @Override
    public TimeWindowInfo getWindowInfo() {
        return timeWindowInfo;
    }

    // ---------

    @Override
    protected Tuple tryAndForceATupleFromWindow() {
        /*
         * Tuples cannot be forced out like this. They will be removed only by
         * the custom addAndRemoveFromWindow(...) method for null-triggers.
         */
        return null;
    }

    @Override
    protected Collection<Tuple> getNullTriggerTuples(Context context) {
        if (timeWindow.size() == 0) {
            return null;
        }

        final DefaultQueryContext qc = context.getQueryContext();
        final long timestamp = qc.getCurrentCycleTimestamp();
        final Tuple t = timeWindow.peek();
        final long t1 = tsExtractor.extractLong(context.getGlobalContext(), qc, t);

        // Check if Tuple has expired or there is purge-advice.
        if (t1 + windowDurationMillis <= timestamp) {
            /*
            * Return this fixed, empty list now and then intercept it in
            * addToWindow(..).
            */
            return nullTriggersForCycle;
        }

        if (purgeAdvice != null) {
            Integer x = purgeAdvice.getFirstX();
            Timestamp oldestTS = purgeAdvice.getOldestAllowedTimestamp();

            if ((x != null && x > 0) || (oldestTS != null && t1 < oldestTS.getTime())) {
                return nullTriggersForCycle;
            }
        }

        return null;
    }

    @Override
    protected boolean isNextCycleScheduleRequired() {
        /*
         * Even if there are unprocessed Tuples, we do not schedule for the next
         * cycle, because the Tuples can be slid out only when they expire. So,
         * we schedule only based on the expiry time.
         */
        return false;
    }

    @Override
    protected Iterator<? extends Tuple> addAndRemoveFromWindow(Context context,
                                                               Collection<? extends Tuple> tupleSrc,
                                                               boolean nullTrigger,
                                                               boolean removeFromSrc,
                                                               Collection<Tuple> addedTuplesTracker,
                                                               Collection<Tuple> deletedTuplesTracker) {
        if (nullTrigger == false) {
            return super.addAndRemoveFromWindow(context, tupleSrc, nullTrigger, removeFromSrc,
                    addedTuplesTracker,
                    deletedTuplesTracker);
        }

        /*
         * Do all purging, time-window maintenance etc here - once for each
         * cycle - at the beginning, which also happens to be the time when the null-triggers
         * collection is sent.
         */
        final DefaultGlobalContext gc = context.getGlobalContext();
        final DefaultQueryContext qc = context.getQueryContext();
        final long timestamp = qc.getCurrentCycleTimestamp();

        final PriorityQueue<Tuple> cachedTimeWindow = timeWindow;
        final long cachedWindowDurationMillis = windowDurationMillis;
        final TupleTimestampExtractor cachedTSExtractor = tsExtractor;
        final ImmediateWindowPurgeAdvice cachedPurgeAdvice = purgeAdvice;

        timestampComparator.setGlobalContext(gc);
        timestampComparator.setQueryContext(qc);

        while (cachedTimeWindow.size() > 0) {
            Tuple t = cachedTimeWindow.peek();
            long t1 = cachedTSExtractor.extractLong(gc, qc, t);

            boolean take = false;

            // Check if Tuple has expired.
            if (t1 + cachedWindowDurationMillis <= timestamp) {
                take = true;
            }
            else if (cachedPurgeAdvice != null) {
                Timestamp oldestTS = null;

                if (cachedPurgeAdvice.useFirstXInsteadOfTimestamp() &&
                        cachedPurgeAdvice.tryDecrementingFirstX()) {
                    take = true;
                }
                else if ((oldestTS = cachedPurgeAdvice.getOldestAllowedTimestamp()) != null &&
                        t1 < oldestTS.getTime()) {
                    take = true;
                }
            }

            if (take) {
                // Pull the Tuple out.
                cachedTimeWindow.poll();

                deletedTuplesTracker.add(t);
            }
            else {
                break;
            }
        }

        return nullTriggersForCycleIter;
    }

    @Override
    public void cycleEnd(Context context) throws Exception {
        super.cycleEnd(context);

        if (timeWindow.size() > 0) {
            final DefaultGlobalContext gc = context.getGlobalContext();
            final DefaultQueryContext qc = context.getQueryContext();

            Tuple t = timeWindow.peek();
            long t1 = tsExtractor.extractLong(gc, qc, t);

            long ts = t1 + windowDurationMillis;
            ts = Clock.roundToNearestSecondInMillis(ts);

            windowOwner.scheduleWindowAt(context, this, ts);
        }
    }

    @Override
    public void discard(Context context) {
        super.discard(context);

        timeWindow.clear();
        timeWindow = null;

        tsExtractor = null;

        nullTriggersForCycle.clear();
        nullTriggersForCycle = null;

        nullTriggersForCycleIter = null;

        timestampComparator.discard();
        timestampComparator = null;

        timeWindowInfo = null;
    }

    // ---------

    public static class TupleTimestampComparator implements Comparator<Tuple> {
        /**
         * Set once.
         */
        private TupleTimestampExtractor timestampExtractor;

        /**
         * Set before every call.
         */
        private DefaultGlobalContext globalContext;

        /**
         * Set before every call.
         */
        private DefaultQueryContext queryContext;

        public void init(TupleTimestampExtractor timestampExtractor) {
            this.timestampExtractor = timestampExtractor;
        }

        public DefaultGlobalContext getGlobalContext() {
            return globalContext;
        }

        public void setGlobalContext(DefaultGlobalContext globalContext) {
            this.globalContext = globalContext;
        }

        public DefaultQueryContext getQueryContext() {
            return queryContext;
        }

        public void setQueryContext(DefaultQueryContext queryContext) {
            this.queryContext = queryContext;
        }

        public TupleTimestampExtractor getTimestampExtractor() {
            return timestampExtractor;
        }

        public int compare(Tuple o1, Tuple o2) {
            final DefaultGlobalContext gc = globalContext;
            final DefaultQueryContext qc = queryContext;
            final TupleTimestampExtractor cachedTE = timestampExtractor;

            long thisVal = cachedTE.extractLong(gc, qc, o1);
            long thatVal = cachedTE.extractLong(gc, qc, o2);

            return thisVal < thatVal ? -1 : (thisVal == thatVal ? 0 : 1);
        }

        public void discard() {
            timestampExtractor = null;
            globalContext = null;
            queryContext = null;
        }
    }
}
