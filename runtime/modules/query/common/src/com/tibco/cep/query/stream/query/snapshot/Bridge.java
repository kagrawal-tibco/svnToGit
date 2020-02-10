package com.tibco.cep.query.stream.query.snapshot;

import com.tibco.cep.query.stream.context.Context;
import com.tibco.cep.query.stream.context.LocalContext;
import com.tibco.cep.query.stream.core.AbstractStream;
import com.tibco.cep.query.stream.core.Stream;
import com.tibco.cep.query.stream.core.StreamListener;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.tuple.TupleInfo;
import com.tibco.cep.query.stream.util.CustomCollection;

/*
 * Author: Ashwin Jayaprakash Date: Mar 3, 2008 Time: 11:16:19 AM
 */

/**
 * <p> Bridge between streaming components and the static parts. Cannot add any listeners: {@link
 * #getListener()} and {@link #getAdditionalListeners()}. This stream just just keeps accumulating
 * the tuples sent to it. </p> <p> Only in the end, when {@link #endStreamInputAndFlush(Context)} is
 * invoked does the {@link #batchResultReceiver} get invoked. </p>
 */
public abstract class Bridge extends AbstractStream {
    /**
     * Next non-streaming segment in the flow.
     */
    protected Stream batchResultReceiver;

    protected Flusher flusher;

    /**
     * @param source
     * @param id
     */
    public Bridge(Stream source, ResourceId id) {
        super(source, id, source.getOutputInfo());

        ResourceId flusherId = new ResourceId(resourceId, Flusher.class.getName());
        this.flusher = new Flusher(null, flusherId, source.getOutputInfo());
    }

    public Stream getFlusher() {
        return flusher;
    }

    public void setup(Stream batchResultReceiver) {
        this.batchResultReceiver = batchResultReceiver;
    }

    public Stream getBatchResultReceiver() {
        return batchResultReceiver;
    }

    /**
     * To be invoked by caller after each {@link #process(Context)} method on this stream.
     *
     * @return <code>false</code> to stop any further stream input - Optimization step, like
     *         "stop-filters".
     */
    public boolean canContinueStreamInput() {
        return true;
    }

    /**
     * @param context
     * @return Can be <code>null</code>. Cannot be empty otherwise.
     */
    protected abstract CustomCollection<? extends Tuple> getFinalCollectedTuples(Context context);

    /**
     * <p>Invokes {@link Stream#process(Context)} on the {@link #batchResultReceiver}.</p> <p/>
     * <p>Uses the values provided by {@link #getFinalCollectedTuples(com.tibco.cep.query.stream.context.Context)}.</p>
     * <p/> <p>Also invokes {@link #clearFinalCollectedTuples(com.tibco.cep.query.stream.context.Context)}
     * after sending tuples to {@link #batchResultReceiver}.</p>
     *
     * @param context
     * @throws Exception
     */
    protected void endStreamInputAndFlush(Context context) throws Exception {
        LocalContext originalLC = context.getLocalContext();

        try {
            localContext.clear();

            CustomCollection<? extends Tuple> collection = getFinalCollectedTuples(context);

            if (collection != null) {
                localContext.setNewTuples(collection);
            }

            context.setLocalContext(localContext);
            batchResultReceiver.process(context);

            clearFinalCollectedTuples(context);
        }
        finally {
            context.setLocalContext(originalLC);
        }

        flusher = null;
    }

    protected abstract void clearFinalCollectedTuples(Context context);

    // ---------

    /**
     * @return <code>false</code>.
     */
    @Override
    public final boolean producesDStream() {
        return false;
    }

    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public final void addAdditionalListener(StreamListener anotherListener) {
        throw new UnsupportedOperationException();
    }

    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public final void setListener(StreamListener listener) {
        throw new UnsupportedOperationException();
    }

    //-----------

    protected class Flusher extends AbstractStream {
        /**
         * @param source     Can be <code>null</code>
         * @param id
         * @param outputInfo
         */
        public Flusher(Stream source, ResourceId id, TupleInfo outputInfo) {
            super(source, id, outputInfo);
        }

        @Override
        protected void doProcessing(Context context) throws Exception {
            super.doProcessing(context);

            Bridge.this.endStreamInputAndFlush(context);
        }
    }
}
