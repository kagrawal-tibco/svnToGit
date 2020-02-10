package com.tibco.cep.query.stream.io;

import java.util.Collection;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.TimeUnit;

import com.tibco.cep.query.stream.core.ControllableResource;
import com.tibco.cep.query.stream.core.Registry;
import com.tibco.cep.query.stream.monitor.CustomDaemonThread;
import com.tibco.cep.query.stream.monitor.CustomThreadGroup;
import com.tibco.cep.query.stream.monitor.Flags;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.monitor.ThreadCentral;
import com.tibco.cep.query.stream.query.continuous.ContinuousQuery;
import com.tibco.cep.query.stream.tuple.Tuple;

/*
 * Author: Ashwin Jayaprakash Date: Dec 4, 2007 Time: 2:54:39 PM
 */

/**
 * <p> Thread-safe! </p> <p> If this is used to publish/distribute the Query results, then that
 * Query's {@link ContinuousQuery#getSink() Sink} cannot be used directly. </p>
 */
public class QueryResultPublisher implements ControllableResource {
    public static final int POLL_WAIT_TIME_MILLIS = 5000;

    protected final ResourceId resultProviderResourceId;

    protected final StreamedSink resultProvider;

    protected final ResourceId resourceId;

    private final Tuple batchEndMarker;

    /**
     * Values are just <code>null</code>s.
     */
    protected final CopyOnWriteArraySet<QueryResultListener> listeners;

    private final PublisherThread publisherThread;

    /**
     * @param query      Sink has to be of {@link StreamedSink} type.
     * @param resourceId
     */
    public QueryResultPublisher(ContinuousQuery query, ResourceId resourceId) {
        this.resultProviderResourceId = query.getResourceId();
        this.resultProvider = (StreamedSink) query.getSink();

        this.resourceId = resourceId;
        this.listeners = new CopyOnWriteArraySet<QueryResultListener>();

        ThreadCentral threadCentral = Registry.getInstance().getComponent(ThreadCentral.class);
        ResourceId threadGroupId = resourceId.getParent();
        if (threadGroupId == null) {
            threadGroupId = resourceId;
        }
        CustomThreadGroup threadGroup = threadCentral.createOrGetThreadGroup(threadGroupId);
        this.publisherThread = new PublisherThread(threadGroup, resourceId);

        this.batchEndMarker = this.resultProvider.getBatchEndMarker();
    }

    public ResourceId getResourceId() {
        return resourceId;
    }

    public ResourceId getResultProviderResourceId() {
        return resultProviderResourceId;
    }

    public StreamedSink getResultProvider() {
        return resultProvider;
    }

    public Collection<QueryResultListener> getListeners() {
        return listeners;
    }

    public void registerListener(QueryResultListener listener) {
        listeners.add(listener);
    }

    public void unregisterListener(QueryResultListener listener) {
        listeners.remove(listener);
    }

    // ---------

    public void start() throws Exception {
        publisherThread.start();
    }

    public void stop() throws Exception {
        publisherThread.signalStop();
    }

    // ---------

    public class PublisherThread extends CustomDaemonThread {
        public PublisherThread(CustomThreadGroup group, ResourceId resourceId) {
            super(group, resourceId);
        }

        @Override
        protected void doWorkLoop() throws InterruptedException {
            Tuple tuple = resultProvider.poll(POLL_WAIT_TIME_MILLIS, TimeUnit.MILLISECONDS);
            if (tuple == null || tuple == batchEndMarker) {
                return;
            }

            try {
                for (QueryResultListener listener : listeners) {
                    /*
                     * Since the tuple is going outside the boundary of the
                     * Query, the ref-counts must be tracked.
                     */
                    if (Flags.TRACK_TUPLE_REFS) {
                        tuple.incrementRefCount();
                    }

                    listener.onResult(tuple);
                }
            }
            finally {
                if (Flags.TRACK_TUPLE_REFS) {
                    // Decrement the increment performed in the Sink.
                    tuple.decrementRefCount();
                }
            }
        }
    }
}
