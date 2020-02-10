package com.tibco.cep.query.stream.io;

import com.tibco.cep.query.stream.context.Context;
import com.tibco.cep.query.stream.core.WithdrawableSource;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.tuple.TupleInfo;
import com.tibco.cep.query.stream.util.CustomCollection;
import com.tibco.cep.query.stream.util.SingleElementCollection;

/*
 * Author: Ashwin Jayaprakash Date: Feb 8, 2008 Time: 5:06:50 PM
 */

public class WithdrawableSourceImpl extends SourceImpl implements WithdrawableSource {
    protected final SingleElementCollection<Tuple> deadTupleHolder;

    public WithdrawableSourceImpl(ResourceId id, TupleInfo outputInfo) {
        super(id, outputInfo, true);

        this.deadTupleHolder = new SingleElementCollection<Tuple>(null);
    }

    public final void sendModifiedTuple(Context context, Tuple oldTuple, Tuple newTuple)
            throws Exception {
        deadTupleHolder.setElement(oldTuple);
        singleTupleHolder.setElement(newTuple);

        localContext.clear();
        // Dead tuples are handled first in all the Streams.
        localContext.setDeadTuples(deadTupleHolder);
        localContext.setNewTuples(singleTupleHolder);

        try {
            internalStream.process(context);
        }
        finally {
            singleTupleHolder.clear();
            deadTupleHolder.clear();
        }
    }

    public final void sendDeadTuple(Context context, Tuple tuple) throws Exception {
        deadTupleHolder.setElement(tuple);

        localContext.clear();
        localContext.setDeadTuples(deadTupleHolder);

        try {
            internalStream.process(context);
        }
        finally {
            deadTupleHolder.clear();
        }
    }

    /**
     * @param context
     * @param tuples  Should not be modified concurrently!
     * @throws Exception
     */
    public final void sendDeadTuples(Context context, CustomCollection<? extends Tuple> tuples)
            throws Exception {
        localContext.clear();
        localContext.setDeadTuples(tuples);

        internalStream.process(context);
    }

    @Override
    public void stop() throws Exception {
        super.stop();

        deadTupleHolder.clear();
    }
}
