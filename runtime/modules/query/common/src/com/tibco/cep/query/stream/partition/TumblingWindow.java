package com.tibco.cep.query.stream.partition;

import java.util.Collection;
import java.util.LinkedList;

import com.tibco.cep.query.stream.context.Context;
import com.tibco.cep.query.stream.group.GroupKey;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.tuple.TupleInfo;
import com.tibco.cep.query.stream.util.NullElementCollection;

/*
 * Author: Ashwin Jayaprakash Date: Nov 9, 2007 Time: 3:50:31 PM
 */

/**
 * Does not use {@link com.tibco.cep.query.stream.partition.purge.WindowPurgeAdvice}.
 */
public class TumblingWindow extends CommonWindow {
    // todo Use better data structure.
    protected LinkedList<Tuple> tumblingWindow;

    private NullElementCollection<Tuple> nullTriggersForCycle;

    private Tuple youngestTupleFromPrevCycle;

    public TumblingWindow(ResourceId id, TupleInfo outputInfo, GroupKey groupKey,
                          WindowOwner windowOwner,
                          TumblingWindowInfo windowInfo) {
        super(id, outputInfo, groupKey, windowOwner, windowInfo, new LinkedList<Tuple>());

        this.nullTriggersForCycle = new NullElementCollection<Tuple>();
        this.tumblingWindow = (LinkedList<Tuple>) this.window;
    }

    @Override
    public void cycleStart(Context context) throws Exception {
        super.cycleStart(context);

        if (tumblingWindow.size() == 0) {
            youngestTupleFromPrevCycle = null;
        }
        else {
            youngestTupleFromPrevCycle = tumblingWindow.getLast();
        }
    }

    @Override
    protected Collection<Tuple> getNullTriggerTuples(Context context) {
        if (tumblingWindow.size() == 0) {
            return null;
        }

        // All Tuples in the Window should be slid out.

        int size = tumblingWindow.size();
        nullTriggersForCycle.setNullCount(size);

        return nullTriggersForCycle;
    }

    @Override
    protected Tuple tryAndForceATupleFromWindow() {
        Tuple t = null;

        if (youngestTupleFromPrevCycle != null) {
            t = tumblingWindow.poll();

            if (t == youngestTupleFromPrevCycle) {
                youngestTupleFromPrevCycle = null;
            }
        }

        return t;
    }

    @Override
    protected void doProcessing(Context context) throws Exception {
        super.doProcessing(context);

        /*
         * All the Tuples in the Window will be slid-out in the next immediate
         * cycle.
         */
        if (tumblingWindow.size() > 0) {
            windowOwner.scheduleSubStreamForNextCycle(context, this);
        }
    }

    @Override
    public void discard(Context context) {
        super.discard(context);

        tumblingWindow.clear();
        tumblingWindow = null;

        nullTriggersForCycle.clear();
        nullTriggersForCycle = null;

        youngestTupleFromPrevCycle = null;
    }
}
