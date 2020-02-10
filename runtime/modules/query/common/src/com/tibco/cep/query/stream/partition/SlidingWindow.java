package com.tibco.cep.query.stream.partition;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.LinkedList;

import com.tibco.cep.query.stream.context.Context;
import com.tibco.cep.query.stream.group.GroupKey;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.tuple.TupleInfo;
import com.tibco.cep.query.stream.util.NullElementCollection;

/*
 * Author: Ashwin Jayaprakash Date: Nov 9, 2007 Time: 12:48:17 PM
 */

public class SlidingWindow extends CommonWindow {
    // todo Use better data structure.
    protected LinkedList<Tuple> slidingWindow;

    private Tuple oldestTupleFromPrevCycle;

    private Tuple youngestTupleFromPrevCycle;

    private NullElementCollection<Tuple> nullTriggers;

    public SlidingWindow(ResourceId id, TupleInfo outputInfo, GroupKey groupKey,
                         WindowOwner windowOwner,
                         SlidingWindowInfo windowInfo) {
        super(id, outputInfo, groupKey, windowOwner, windowInfo,
                new LinkedList<Tuple>());

        this.slidingWindow = (LinkedList<Tuple>) this.window;
        this.nullTriggers = new NullElementCollection<Tuple>();
    }

    @Override
    public SlidingWindowInfo getWindowInfo() {
        return (SlidingWindowInfo) commonWindowInfo;
    }

    // ---------

    @Override
    public void cycleStart(Context context) throws Exception {
        super.cycleStart(context);

        if (slidingWindow.size() == 0) {
            oldestTupleFromPrevCycle = null;
            youngestTupleFromPrevCycle = null;
        }
        else {
            oldestTupleFromPrevCycle = slidingWindow.getFirst();
            youngestTupleFromPrevCycle = slidingWindow.getLast();
        }
    }

    @Override
    protected Collection<? extends Tuple> getNullTriggerTuples(Context context) {
        //No trigger required. Attempt to slide out only when there is a "real" Tuple to slide in.
        if (purgeAdvice == null || slidingWindow.size() == 0) {
            return null;
        }

        //----------

        nullTriggers.clear();

        final LinkedList<Tuple> cachedSlidingWindow = slidingWindow;
        final boolean useFirstX = purgeAdvice.useFirstXInsteadOfTimestamp();
        Timestamp ts = null;

        if (useFirstX) {
            int x = purgeAdvice.getFirstX();
            int size = Math.min(cachedSlidingWindow.size(), x);

            nullTriggers.setNullCount(size);
        }
        else if ((ts = purgeAdvice.getOldestAllowedTimestamp()) != null) {
            long longTS = ts.getTime();
            for (Tuple tuple : cachedSlidingWindow) {
                /*
                Uses the Tuple's default timestamp unlike in a TimeWindow where there is
                provision for a timestamp extractor.
                */
                if (tuple.getTimestamp() < longTS) {
                    nullTriggers.add(null);
                }
                else {
                    break;
                }
            }
        }

        return (nullTriggers.size() == 0) ? null : nullTriggers;
    }

    @Override
    protected Tuple tryAndForceATupleFromWindow() {
        // First clear the oldest Tuple based on the window's default policy.
        if (oldestTupleFromPrevCycle != null) {
            oldestTupleFromPrevCycle = null;

            return slidingWindow.poll();
        }

        Tuple t = null;

        if (youngestTupleFromPrevCycle != null && purgeAdvice != null &&
                purgeAdvice.useFirstXInsteadOfTimestamp() && purgeAdvice.tryDecrementingFirstX()) {
            t = slidingWindow.poll();

            if (t == youngestTupleFromPrevCycle) {
                /*
                * Now, the whole set of Tuples from previous cycles have been
                * cleared. Cannot slide out any more Tuples in this cycle.
                */
                youngestTupleFromPrevCycle = null;
            }
        }

        return t;
    }

    @Override
    public void discard(Context context) {
        super.discard(context);

        slidingWindow.clear();
        slidingWindow = null;

        nullTriggers.clear();
        nullTriggers = null;

        oldestTupleFromPrevCycle = null;
        youngestTupleFromPrevCycle = null;
    }
}
