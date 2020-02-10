package com.tibco.cep.query.stream.io;

import com.tibco.cep.query.stream.context.Context;
import com.tibco.cep.query.stream.context.LocalContext;
import com.tibco.cep.query.stream.core.AbstractStream;
import com.tibco.cep.query.stream.core.Source;
import com.tibco.cep.query.stream.core.Stream;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.tuple.TupleInfo;
import com.tibco.cep.query.stream.util.CustomCollection;
import com.tibco.cep.query.stream.util.SingleElementCollection;

/*
 * Author: Ashwin Jayaprakash Date: Feb 21, 2008 Time: 2:04:26 PM
 */

public class SourceImpl implements Source {
    protected final ResourceId resourceId;

    protected final InternalDestination internalStream;

    protected final LocalContext localContext;

    protected final SingleElementCollection<Tuple> singleTupleHolder;

    public SourceImpl(ResourceId id, TupleInfo outputInfo) {
        this(id, outputInfo, false);
    }

    /**
     * @param id
     * @param outputInfo
     * @param producesDStream
     */
    protected SourceImpl(ResourceId id, TupleInfo outputInfo, boolean producesDStream) {
        this.resourceId = id;

        this.internalStream = new InternalDestination(id, outputInfo, producesDStream);
        this.localContext = this.internalStream.getLocalContext();

        this.singleTupleHolder = new SingleElementCollection<Tuple>(null);
    }

    public final ResourceId getResourceId() {
        return resourceId;
    }

    public TupleInfo getOutputInfo() {
        return internalStream.getOutputInfo();
    }

    public Stream getInternalStream() {
        return internalStream;
    }

    public final void sendNewTuple(Context context, Tuple tuple) throws Exception {
        singleTupleHolder.setElement(tuple);

        localContext.clear();
        localContext.setNewTuples(singleTupleHolder);

        try {
            internalStream.process(context);
        }
        finally {
            singleTupleHolder.clear();
        }
    }

    /**
     * @param context
     * @param tuples  Should not be modified concurrently!
     * @throws Exception
     */
    public final void sendNewTuples(Context context, CustomCollection<? extends Tuple> tuples)
            throws Exception {
        localContext.clear();
        localContext.setNewTuples(tuples);

        internalStream.process(context);
    }

    public void stop() throws Exception {
        internalStream.stop();

        resourceId.discard();

        singleTupleHolder.clear();
    }

    // ------------

    protected class InternalDestination extends AbstractStream {
        private final boolean producesDS;

        public InternalDestination(ResourceId id, TupleInfo outputInfo,
                                   boolean producesDStream) {
            super(null, id, outputInfo);

            this.producesDS = producesDStream;
        }

        @Override
        public boolean producesDStream() {
            return producesDS;
        }
    }
}
