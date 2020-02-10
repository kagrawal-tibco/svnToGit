package com.tibco.cep.query.stream.partition;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import com.tibco.cep.query.stream.context.Context;
import com.tibco.cep.query.stream.context.LocalContext;
import com.tibco.cep.query.stream.group.GroupKey;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.tuple.TupleInfo;
import com.tibco.cep.query.stream.util.AppendOnlyQueue;
import com.tibco.cep.query.stream.util.ArrayPool;

/*
 * Author: Ashwin Jayaprakash Date: Nov 12, 2007 Time: 11:13:27 AM
 */

public abstract class CommonWindow extends Window {
    protected CommonWindowInfo commonWindowInfo;

    protected Queue<Tuple> window;

    //todo Use better data structure.
    protected LinkedList<Tuple> unprocessedBuffer;

    /**
     * Can be <code>null</code>.
     */
    private Collection<? extends Tuple> nullTriggerTuplesForCycle;

    private AppendOnlyQueue<Tuple> addedTuplesWhileProcessing;

    private AppendOnlyQueue<Tuple> deletedTuplesWhileProcessing;

    /**
     * @param id
     * @param outputInfo
     * @param groupKey
     * @param windowOwner
     * @param commonWindowInfo
     * @param commonWindowInfo
     * @param windowImpl
     */
    public CommonWindow(ResourceId id, TupleInfo outputInfo, GroupKey groupKey,
                        WindowOwner windowOwner,
                        CommonWindowInfo commonWindowInfo,
                        Queue<Tuple> windowImpl) {
        super(id, outputInfo, groupKey, windowOwner);

        this.commonWindowInfo = commonWindowInfo;
        this.window = windowImpl;
        this.unprocessedBuffer = new LinkedList<Tuple>();
    }

    public CommonWindowInfo getWindowInfo() {
        return commonWindowInfo;
    }

    @Override
    public boolean requiresTupleDeletes() {
        // Not required. Window Policy decides that.
        return false;
    }

    @Override
    public int getUnprocessedBufferSize() {
        return unprocessedBuffer.size();
    }

    // ---------

    @Override
    public void cycleStart(Context context) throws Exception {
        if (isCycleStartDone() == false) {
            super.cycleStart(context);

            nullTriggerTuplesForCycle = getNullTriggerTuples(context);
        }
    }

    /**
     * Invoked in the beginning of the cycle during {@link #cycleStart(Context)}.
     *
     * @param context
     * @return <code>null</code> if no trigger is required. See {@link com.tibco.cep.query.stream.expression.ExpressionEvaluator#evaluateInteger(com.tibco.cep.query.stream.context.GlobalContext,com.tibco.cep.query.model.QueryContext,com.tibco.cep.query.stream.util.FixedKeyHashMap} for
     *         the meaning of "Trigger". Or, a collection of <code>null</code>s to push the Tuples
     *         in the Window out, based on the Window policy. This list will be sent <b>once</b>
     *         during the beginning of the cycle.
     */
    protected abstract Collection<? extends Tuple> getNullTriggerTuples(Context context);

    /**
     * {@inheritDoc} <p> The new Tuples in the {@link LocalContext#getNewTuples()} can be a list of
     * <code>null</code>s, in which case they will be treated as empty placeholders/triggers to
     * slide the Tuples out of the Window. </p> <p> The first list to be processed will be the
     * results of {@link #getNullTriggerTuples(Context)}. </p> <p/> <p>The dead tuples are
     * completely ignored.</p>
     */
    @Override
    protected void doProcessing(Context context) throws Exception {
        super.doProcessing(context);

        if (addedTuplesWhileProcessing == null) {
            ArrayPool arrayPool = context.getQueryContext().getArrayPool();

            addedTuplesWhileProcessing = new AppendOnlyQueue<Tuple>(arrayPool);
            deletedTuplesWhileProcessing = new AppendOnlyQueue<Tuple>(arrayPool);
        }

        // ----------

        /*
         * Clear old Tuples based on Window policy.
         */
        if (nullTriggerTuplesForCycle != null) {
            addAndRemoveFromWindow(context, nullTriggerTuplesForCycle, true, true,
                    addedTuplesWhileProcessing,
                    deletedTuplesWhileProcessing);

            // /Don't try this block again until the next cycle.
            nullTriggerTuplesForCycle = null;
        }

        // ----------

        // First process the buffered Tuples from previous cycles.
        if (unprocessedBuffer.size() > 0) {
            addAndRemoveFromWindow(context, unprocessedBuffer, false, true,
                    addedTuplesWhileProcessing,
                    deletedTuplesWhileProcessing);
        }

        // ----------

        processNewInputTuples(context);

        // ----------

        if (isNextCycleScheduleRequired()) {
            windowOwner.scheduleSubStreamForNextCycle(context, this);
        }

        // ----------

        localContext.setNewTuples(
                addedTuplesWhileProcessing.isEmpty() ? null : addedTuplesWhileProcessing);
        localContext.setDeadTuples(
                deletedTuplesWhileProcessing.isEmpty() ? null : deletedTuplesWhileProcessing);
    }

    private void processNewInputTuples(Context context) {
        // Use only the New Tuples. Dead Tuples are completely ignored.

        final Collection<? extends Tuple> newTuples = context.getLocalContext().getNewTuples();
        if (newTuples == null) {
            return;
        }

        final LinkedList<Tuple> cachedUnprocessedBuffer = unprocessedBuffer;
        Iterator<? extends Tuple> newTuplesToBuffer = null;

        if (cachedUnprocessedBuffer.isEmpty()) {
            // Cannot modify the source's list.
            newTuplesToBuffer = addAndRemoveFromWindow(context, newTuples, false, false,
                    addedTuplesWhileProcessing, deletedTuplesWhileProcessing);

            // Add remaining Tuples to the buffer, if there are any by not closing the Iterator.
        }
        else {
            newTuplesToBuffer = newTuples.iterator();
        }

        /*
        * There are still some Tuples in the Buffer. So, these new ones
        * cannot be processed immediately. Just buffer them for later.
        */
        for (; newTuplesToBuffer.hasNext();) {
            Tuple tuple = newTuplesToBuffer.next();

            cachedUnprocessedBuffer.add(tuple);
        }
    }

    @Override
    protected void endProcessing(Context context) {
        super.endProcessing(context);

        if (addedTuplesWhileProcessing != null) {
            addedTuplesWhileProcessing.clear();
            deletedTuplesWhileProcessing.clear();
        }
    }

    /**
     * @return <code>true</code> if there are unprocessed Tuples. They have to be processed in the
     *         next cycle.
     */
    protected boolean isNextCycleScheduleRequired() {
        return ! /* Invert */unprocessedBuffer.isEmpty();
    }

    /**
     * Invoked when there are new/null-trigger Tuples to slide the existing Tuples out. Will
     * <b>not</b> get invoked if the Window is empty. Sub-class must decide which Tuple to oust
     * based on its policy.
     *
     * @return <code>null</code> if nothing could be forced out or the candidate Tuple that got
     *         removed from the window to make room for the new one.
     */
    protected abstract Tuple tryAndForceATupleFromWindow();

    /**
     * @param context
     * @param tupleSrc             If the Tuples in this list are <code>null</code>s, then they will
     *                             be treated as placeholders and will not be added to the {@link
     *                             #window} and the <code>addedTuplesTracker</code>.
     * @param nullTrigger          If <code>true</code>, this the "tupleSrc" is a collection of
     *                             <code>null</code>s.
     * @param removeFromSrc        If <code>true</code> and the "tupleSrc" Tuples get added to the
     *                             Window, then they will <b>get removed from this list</b>.
     * @param addedTuplesTracker
     * @param deletedTuplesTracker
     * @return The iterator on the tupleSrc with the remaining tuples that couldn't be serviced by
     *         this method.
     */
    protected Iterator<? extends Tuple> addAndRemoveFromWindow(Context context,
                                                               Collection<? extends Tuple> tupleSrc,
                                                               boolean nullTrigger,
                                                               boolean removeFromSrc,
                                                               Collection<Tuple> addedTuplesTracker,
                                                               Collection<Tuple> deletedTuplesTracker) {
        final Queue<Tuple> cachedWindow = window;
        final CommonWindowInfo cachedCommonWindowInfo = commonWindowInfo;
        final int cachedWindowInfoSize = cachedCommonWindowInfo.getSize();

        // Slide in if there are empty slots.
        Iterator<? extends Tuple> iter = tupleSrc.iterator();

        if (nullTrigger == false) {
            while (iter.hasNext() && cachedWindow.size() < cachedWindowInfoSize) {
                Tuple addedTuple = iter.next();

                cachedWindow.add(addedTuple);
                addedTuplesTracker.add(addedTuple);

                if (removeFromSrc) {
                    iter.remove();
                }
            }
        }

        //------------

        /*
        Slide in only if old slots can be slid out.
        Use the same Iterator - continue from where the previous step stopped.
        */
        while (iter.hasNext() && cachedWindow.size() > 0) {
            Tuple deletedTuple = tryAndForceATupleFromWindow();
            if (deletedTuple == null) {
                break;
            }

            //Slid one out.
            deletedTuplesTracker.add(deletedTuple);

            //---------

            Tuple addedTuple = iter.next();

            //Not just a null-trigger. 
            if (nullTrigger == false) {
                cachedWindow.add(addedTuple);
                addedTuplesTracker.add(addedTuple);
            }

            if (removeFromSrc) {
                iter.remove();
            }
        }

        return iter;
    }

    @Override
    public void cycleEnd(Context context) throws Exception {
        super.cycleEnd(context);

        nullTriggerTuplesForCycle = null;
    }

    /**
     * @return {@link #window} is empty and {@link #getListener()} is a {@link
     *         com.tibco.cep.query.stream.core.SubStream} and it's {@linkplain
     *         com.tibco.cep.query.stream.core.SubStream#canDiscard()} value.
     */
    public boolean canDiscard() {
        boolean b = window.isEmpty();

        if (b == false) {
            return false;
        }

        return listener == null || listener.canDiscard();
    }

    @Override
    public boolean producesDStream() {
        return true;
    }

    @Override
    public void discard(Context context) {
        super.discard(context);

        commonWindowInfo = null;

        //Don't clear this. Sub-class owns it.
        window = null;

        unprocessedBuffer.clear();
        unprocessedBuffer = null;

        if (nullTriggerTuplesForCycle != null) {
            nullTriggerTuplesForCycle.clear();
            nullTriggerTuplesForCycle = null;
        }

        if (addedTuplesWhileProcessing != null) {
            addedTuplesWhileProcessing.clear();
            addedTuplesWhileProcessing = null;
        }

        if (deletedTuplesWhileProcessing != null) {
            deletedTuplesWhileProcessing.clear();
            deletedTuplesWhileProcessing = null;
        }
    }
}
