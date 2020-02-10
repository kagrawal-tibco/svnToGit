package com.tibco.cep.query.stream.io;

import java.util.Collection;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

import com.tibco.cep.query.stream.context.Context;
import com.tibco.cep.query.stream.context.LocalContext;
import com.tibco.cep.query.stream.core.AbstractStream;
import com.tibco.cep.query.stream.core.Sink;
import com.tibco.cep.query.stream.core.Stream;
import com.tibco.cep.query.stream.monitor.Flags;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.tuple.TupleInfo;
import com.tibco.cep.query.stream.util.AppendOnlyQueue;

/*
 * Author: Ashwin Jayaprakash Date: Feb 15, 2008 Time: 4:08:25 PM
 */

public class StaticSink implements Sink {
    protected final ResourceId resourceId;

    protected final InternalSource internalSource;

    protected final ArrayBlockingQueue<AppendOnlyQueue<Tuple>> finalResultHolder;

    /**
     * Output is the same as the source Stream's output.
     *
     * @param source Uses this stream's {@link Stream#getOutputInfo()}.
     * @param id
     */
    public StaticSink(Stream source, ResourceId id) {
        this.resourceId = id;
        this.internalSource = new InternalSource(source, id);
        this.finalResultHolder = new ArrayBlockingQueue<AppendOnlyQueue<Tuple>>(1);
    }

    /**
     * @param id
     * @param outputInfo
     */
    public StaticSink(ResourceId id, TupleInfo outputInfo) {
        this.resourceId = id;
        this.internalSource = new InternalSource(id, outputInfo);
        this.finalResultHolder = new ArrayBlockingQueue<AppendOnlyQueue<Tuple>>(1);
    }

    public TupleInfo getOutputInfo() {
        return internalSource.getOutputInfo();
    }

    public Stream getInternalStream() {
        return internalSource;
    }

    public ResourceId getResourceId() {
        return resourceId;
    }

    /**
     * @return <code>null</code> if the queue is empty. After using this Tuple, the {@link
     *         Tuple#decrementRefCount()} must be invoked.
     */
    public Collection<Tuple> pollFinalResult() {
        return finalResultHolder.poll();
    }

    /**
     * @param timeout
     * @param unit
     * @return <code>null</code> if timeout expires. After using this Tuple, the {@link
     *         Tuple#decrementRefCount()} must be invoked.
     * @throws InterruptedException
     */
    public Collection<Tuple> pollFinalResult(long timeout, TimeUnit unit)
            throws InterruptedException {
        return finalResultHolder.poll(timeout, unit);
    }

    // ------------

    protected class InternalSource extends AbstractStream {
        public InternalSource(ResourceId id, TupleInfo outputInfo) {
            super(null, id, outputInfo);
        }

        /**
         * @param source Uses this stream's {@link Stream#getOutputInfo()}.
         * @param id
         */
        public InternalSource(Stream source, ResourceId id) {
            super(source, id, source.getOutputInfo());
        }

        @Override
        protected void doProcessing(Context context) throws Exception {
            super.doProcessing(context);

            LocalContext lc = context.getLocalContext();
            Collection<? extends Tuple> newTuples = lc.getNewTuples();
            if (newTuples != null) {
                AppendOnlyQueue<Tuple> result = new AppendOnlyQueue<Tuple>();
                for (Tuple tuple : newTuples) {
                    if (Flags.TRACK_TUPLE_REFS) {
                        tuple.incrementRefCount();
                    }

                    result.add(tuple);
                }

                finalResultHolder.add(result);
            }
        }
    }
}