package com.tibco.cep.query.stream.partition.accumulator;

import java.util.Collection;

import com.tibco.cep.query.stream.context.Context;
import com.tibco.cep.query.stream.context.LocalContext;
import com.tibco.cep.query.stream.core.AbstractStream;
import com.tibco.cep.query.stream.core.SubStream;
import com.tibco.cep.query.stream.core.SubStreamOwner;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.AppendOnlyQueue;
import com.tibco.cep.query.stream.util.ArrayPool;

/*
* Author: Ashwin Jayaprakash Date: Apr 23, 2008 Time: 6:33:59 PM
*/
public class WindowResultAccumulator extends AbstractStream<SubStream>
        implements SubStream<SubStreamOwner>, SubStreamCycleResultAccumulator {
    protected AppendOnlyQueue<Tuple> additions;

    protected AppendOnlyQueue<Tuple> deletes;

    /**
     * @param source Can be <code>null</code>
     * @param id
     */
    public WindowResultAccumulator(SubStream source, ResourceId id) {
        super(source, id, source.getOutputInfo());
    }

    public void setOwner(SubStreamOwner owner) {
    }

    public SubStreamOwner getOwner() {
        return null;
    }

    public void cycleStart(Context context) throws Exception {
        if (listener != null) {
            listener.cycleStart(context);
        }
    }

    @Override
    protected void doProcessing(Context context) throws Exception {
        if (additions == null) {
            ArrayPool arrayPool = context.getQueryContext().getArrayPool();

            deletes = new AppendOnlyQueue<Tuple>(arrayPool);
            additions = new AppendOnlyQueue<Tuple>(arrayPool);
        }

        final LocalContext lc = context.getLocalContext();

        Collection<? extends Tuple> deadTuples = lc.getDeadTuples();
        if (deadTuples != null) {
            deletes.addAll(deadTuples);
        }

        Collection<? extends Tuple> newTuples = lc.getNewTuples();
        if (newTuples != null) {
            additions.addAll(newTuples);
        }
    }

    public void cycleEnd(Context context) throws Exception {
        if (listener != null) {
            listener.cycleEnd(context);
        }
    }

    public void fillWithAccumulatedResults(ResultHolder holder) {
        holder.setAdditions(additions);
        holder.setDeletes(deletes);
    }

    /**
     * @return Always <code>true</code>.
     */
    public boolean canDiscard() {
        return true;
    }

    @Override
    public void stop() throws Exception {
        super.stop();

        if (additions != null) {
            additions.clear();
            additions = null;
        }

        if (deletes != null) {
            deletes.clear();
            deletes = null;
        }
    }
}
