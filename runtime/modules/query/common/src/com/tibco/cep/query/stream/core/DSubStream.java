package com.tibco.cep.query.stream.core;

import com.tibco.cep.query.stream.context.Context;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.AppendOnlyQueue;
import com.tibco.cep.query.stream.util.ArrayPool;
import com.tibco.cep.query.stream.util.CustomCollection;

/*
 * Author: Ashwin Jayaprakash Date: Feb 13, 2008 Time: 5:53:40 PM
 */

/**
 * See {@link com.tibco.cep.query.stream.core.DStream}.
 */
public class DSubStream extends AbstractStream<SubStream> implements SubStream<SubStreamOwner> {
    protected AppendOnlyQueue<Tuple> deletesForwardedAsNewInCurrCycle;

    protected AppendOnlyQueue<Tuple> tuplesPassedOfAsNewFromPrevCycle;

    protected SubStreamOwner owner;

    public DSubStream(SubStream source, ResourceId id) {
        super(source, id, source.getOutputInfo());
    }

    public SubStreamOwner getOwner() {
        return owner;
    }

    public void setOwner(SubStreamOwner owner) {
        this.owner = owner;
    }

    public boolean canDiscard() {
        boolean a =
                tuplesPassedOfAsNewFromPrevCycle == null ||
                        tuplesPassedOfAsNewFromPrevCycle.size() == 0;
        boolean b =
                deletesForwardedAsNewInCurrCycle == null ||
                        deletesForwardedAsNewInCurrCycle.size() == 0;

        return a && b;
    }

    public void cycleStart(Context context) throws Exception {
        if (deletesForwardedAsNewInCurrCycle == null) {
            ArrayPool arrayPool = context.getQueryContext().getArrayPool();

            tuplesPassedOfAsNewFromPrevCycle = new AppendOnlyQueue<Tuple>(arrayPool);
            deletesForwardedAsNewInCurrCycle = new AppendOnlyQueue<Tuple>(arrayPool);
        }

        //----------

        if (listener != null) {
            listener.cycleStart(context);
        }
    }

    @Override
    protected void doProcessing(Context context) throws Exception {
        super.doProcessing(context);

        //----------

        if (tuplesPassedOfAsNewFromPrevCycle.size() > 0) {
            localContext.setDeadTuples(tuplesPassedOfAsNewFromPrevCycle);
        }

        //----------

        CustomCollection<? extends Tuple> deadTuples = context.getLocalContext().getDeadTuples();
        if (deadTuples != null) {
            /*
            Keep collecting all these Dead Tuples that've been passed off as New. They will have
            to be sent as Dead Tuples in the next, immediate cycle.
            */
            deletesForwardedAsNewInCurrCycle.addAll(deadTuples);

            //Forward these as if they were new.
            localContext.setNewTuples(deadTuples);
        }
    }

    @Override
    protected void endProcessing(Context context) {
        super.endProcessing(context);

        //Clear these. They've been sent as Dead Tuples just now. We don't need them anymore.
        tuplesPassedOfAsNewFromPrevCycle.clear();
    }

    public void cycleEnd(Context context) throws Exception {
        AppendOnlyQueue<Tuple> temp = tuplesPassedOfAsNewFromPrevCycle;

        /*
        Store these so that in the beginning of the next cycle, they can all be sent at once
        as Dead Tuples.
        */
        tuplesPassedOfAsNewFromPrevCycle = deletesForwardedAsNewInCurrCycle;
        if (tuplesPassedOfAsNewFromPrevCycle.size() > 0 && owner != null) {
            owner.scheduleSubStreamForNextCycle(context, this);
        }

        deletesForwardedAsNewInCurrCycle = temp;

        //----------

        if (listener != null) {
            listener.cycleEnd(context);
        }
    }

    @Override
    public void stop() throws Exception {
        super.stop();

        owner = null;

        if (deletesForwardedAsNewInCurrCycle != null) {
            deletesForwardedAsNewInCurrCycle.clear();
        }

        if (tuplesPassedOfAsNewFromPrevCycle != null) {
            tuplesPassedOfAsNewFromPrevCycle.clear();
        }
    }
}
