package com.tibco.cep.query.stream.io;

import java.util.Collection;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import com.tibco.cep.query.stream.context.Context;
import com.tibco.cep.query.stream.context.LocalContext;
import com.tibco.cep.query.stream.core.AbstractStream;
import com.tibco.cep.query.stream.core.Sink;
import com.tibco.cep.query.stream.core.Stream;
import com.tibco.cep.query.stream.monitor.Flags;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.tuple.LiteTuple;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.tuple.TupleInfo;

/*
 * Author: Ashwin Jayaprakash Date: Oct 3, 2007 Time: 1:05:04 PM
 */

public class StreamedSink implements Sink {
    protected final ResourceId resourceId;

    protected final InternalDestination internalStream;

    protected final LinkedBlockingQueue<Tuple> results;

    protected final BatchEndMarker batchEndMarker;

    /**
     * Output is the same as the source Stream's output.
     *
     * @param source Uses this stream's {@link Stream#getOutputInfo()}.
     * @param id
     */
    public StreamedSink(Stream source, ResourceId id) {
        this.resourceId = id;
        this.internalStream = new InternalDestination(source, id);
        this.results = new LinkedBlockingQueue<Tuple>();

        this.batchEndMarker = new BatchEndMarker();
    }

    /**
     * @param id
     * @param outputInfo
     */
    public StreamedSink(ResourceId id, TupleInfo outputInfo) {
        this.resourceId = id;
        this.internalStream = new InternalDestination(id, outputInfo);
        this.results = new LinkedBlockingQueue<Tuple>();

        this.batchEndMarker = new BatchEndMarker();
    }

    public TupleInfo getOutputInfo() {
        return internalStream.getOutputInfo();
    }

    public Stream getInternalStream() {
        return internalStream;
    }

    public ResourceId getResourceId() {
        return resourceId;
    }

    public Tuple getBatchEndMarker() {
        return batchEndMarker;
    }

    /**
     * @param tuples
     */
    protected void handleAdds(Collection<? extends Tuple> tuples) {
        results.addAll(tuples);
        results.add(batchEndMarker);
    }

    /**
     * <p> Thread-safe. </p> <p> Every time the Query produces results, the last tuple in the
     * collection is always a marker tuple which is a singleton instance - {@link
     * #getBatchEndMarker()}. </p>
     *
     * @return <code>null</code> if the queue is empty. After using this Tuple, the {@link
     *         Tuple#decrementRefCount()} must be invoked. The same instance can appear multiple
     *         times. The decrement method must be invoked for every time the Tuple gets returned by
     *         this method.
     */
    public Tuple poll() {
        return results.poll();
    }

    /**
     * <p> Thread-safe. </p> <p> Every time the Query produces results, the last tuple in the
     * collection is always a marker tuple which is a singleton instance - {@link
     * #getBatchEndMarker()}. </p>
     *
     * @param timeout
     * @param unit
     * @return <code>null</code> if timeout expires. After using this Tuple, the {@link
     *         Tuple#decrementRefCount()} must be invoked. The same instance can appear multiple
     *         times. The decrement method must be invoked for every time the Tuple gets returned by
     *         this method.
     * @throws InterruptedException
     */
    public Tuple poll(long timeout, TimeUnit unit) throws InterruptedException {
        return results.poll(timeout, unit);
    }

    // ------------

    /**
     * Collects the new-Tuples as Results and decrements the ref-count on the dead-Tuples.
     */
    protected class InternalDestination extends AbstractStream {
        public InternalDestination(ResourceId id, TupleInfo outputInfo) {
            super(null, id, outputInfo);
        }

        /**
         * @param source Uses this stream's {@link Stream#getOutputInfo()}.
         * @param id
         */
        public InternalDestination(Stream source, ResourceId id) {
            super(source, id, source.getOutputInfo());
        }

        @Override
        protected void doProcessing(Context context) throws Exception {
            super.doProcessing(context);

            LocalContext lc = context.getLocalContext();

            Collection<? extends Tuple> deadTuples = lc.getDeadTuples();
            if (Flags.TRACK_TUPLE_REFS) {
                if (deadTuples != null) {
                    for (Tuple tuple : deadTuples) {
                        tuple.decrementRefCount();
                    }
                }
            }

            Collection<? extends Tuple> newTuples = lc.getNewTuples();
            if (newTuples != null) {
                /*
                 * Increments the ref-count by 1 before adding the Tuple to the
                 * queue.
                 */
                if (Flags.TRACK_TUPLE_REFS) {
                    for (Tuple tuple : newTuples) {
                        tuple.incrementRefCount();
                    }
                }

                StreamedSink.this.handleAdds(newTuples);
            }
        }
    }

    protected static class BatchEndMarker extends LiteTuple {
        public BatchEndMarker() {
            super(0L);
        }

        @Override
        public String toString() {
            return "Batch end marker.";
        }
    }
}
