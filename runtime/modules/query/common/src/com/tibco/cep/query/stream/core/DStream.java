package com.tibco.cep.query.stream.core;

import com.tibco.cep.query.stream.context.Context;
import com.tibco.cep.query.stream.context.DefaultQueryContext;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.AppendOnlyQueue;
import com.tibco.cep.query.stream.util.ArrayPool;
import com.tibco.cep.query.stream.util.CustomCollection;

/*
 * Author: Ashwin Jayaprakash Date: Oct 1, 2007 Time: 5:05:11 PM
 */

/**
 * Just a do-nothing Adaptor to indicate that the Listener is interested in dead states only. <p/>
 * The output type is the same as the Source. </p>
 */
public class DStream extends AbstractStream {
    protected AppendOnlyQueue<Tuple> deletesForwardedAsNewInCurrCycle;

    protected AppendOnlyQueue<Tuple> tuplesPassedOfAsNewFromPrevCycle;

    /**
     * @param source
     * @param id
     */
    public DStream(Stream source, ResourceId id) {
        super(source, id, source.getOutputInfo());
    }

    @Override
    protected void doProcessing(Context context) throws Exception {
        super.doProcessing(context);

        //----------

        if (deletesForwardedAsNewInCurrCycle == null) {
            ArrayPool arrayPool = context.getQueryContext().getArrayPool();

            tuplesPassedOfAsNewFromPrevCycle = new AppendOnlyQueue<Tuple>(arrayPool);
            deletesForwardedAsNewInCurrCycle = new AppendOnlyQueue<Tuple>(arrayPool);
        }

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

        //----------

        //Clear these. They've been sent as Dead Tuples just now. We don't need them anymore.
        tuplesPassedOfAsNewFromPrevCycle.clear();

        //----------

        AppendOnlyQueue<Tuple> temp = tuplesPassedOfAsNewFromPrevCycle;

        /*
        Store these so that in the beginning of the next cycle, they can all be sent at once
        as Dead Tuples.
        */
        tuplesPassedOfAsNewFromPrevCycle = deletesForwardedAsNewInCurrCycle;
        if (tuplesPassedOfAsNewFromPrevCycle.size() > 0) {
            DefaultQueryContext qc = context.getQueryContext();

            qc.addStreamForNextCycle(this);
        }

        deletesForwardedAsNewInCurrCycle = temp;
    }

    @Override
    public void stop() throws Exception {
        super.stop();


        if (deletesForwardedAsNewInCurrCycle != null) {
            deletesForwardedAsNewInCurrCycle.clear();
        }

        if (tuplesPassedOfAsNewFromPrevCycle != null) {
            tuplesPassedOfAsNewFromPrevCycle.clear();
        }
    }
}
