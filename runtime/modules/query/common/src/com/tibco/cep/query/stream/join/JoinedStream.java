package com.tibco.cep.query.stream.join;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import com.tibco.cep.query.stream.context.Context;
import com.tibco.cep.query.stream.context.DefaultQueryContext;
import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.LocalContext;
import com.tibco.cep.query.stream.core.AbstractStream;
import com.tibco.cep.query.stream.core.Registry;
import com.tibco.cep.query.stream.core.Stream;
import com.tibco.cep.query.stream.expression.Expression;
import com.tibco.cep.query.stream.monitor.CustomRuntimeException;
import com.tibco.cep.query.stream.monitor.Flags;
import com.tibco.cep.query.stream.monitor.Logger;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.tuple.TupleInfo;

/*
 * Author: Ashwin Jayaprakash Date: Oct 1, 2007 Time: 3:02:49 PM
 */

/**
 * <p> Let's say the Join has 2 source Tuples/Streams A and B. The result of the {@link
 * JoinedStream} is another Tuple that has the Tuples of Streams A and B and not the contents of the
 * Tuples from A and B. </p>
 */
public abstract class JoinedStream extends AbstractStream {
    protected final Map<String, Stream> sources;

    protected final Collection<NestedSource> nestedSources;

    protected final Expression joinExpression;

    protected final HashSet<StreamListener> sourceListeners;

    protected final HashSet<NestedStreamListener> nestedSourceListeners;

    /**
     * Can be <code>null</code>.
     */
    protected final JoinedStreamTupleTracker tupleTracker;

    private final boolean producesDStream;

    private final AtomicBoolean stopped;

    /**
     * This does not have a "main" Source.
     *
     * @param id
     * @param sources
     * @param nestedSources  Can be <code>null</code>. Cannot have {@link ProxyStream} as a
     *                       nested-source.
     * @param joinExpression Can be <code>null</code>.
     * @param outputInfo
     * @throws RuntimeException <p> If the source Stream Tuple types are not unique. </p> <p> Also,
     *                          if the Nested source aliases share the same names as the Sources.
     *                          </p>
     */
    public JoinedStream(ResourceId id, Map<String, Stream> sources,
                        Collection<NestedSource> nestedSources, Expression joinExpression,
                        JoinedTupleInfo outputInfo) {
        super(null, id, outputInfo);

        this.sources = sources;
        this.nestedSources = nestedSources;

        this.joinExpression = joinExpression;

        this.sourceListeners = new HashSet<StreamListener>();
        this.nestedSourceListeners = new HashSet<NestedStreamListener>();

        HashSet<Class> tupleTypes = new HashSet<Class>();

        HashMap<String, HashSet<AbstractPetey>> outerTuplePeteys =
                new HashMap<String, HashSet<AbstractPetey>>();
        HashMap<String, AbstractPetey> innerTuplePeteys = new HashMap<String, AbstractPetey>();

        /*
         * If at least 1 source produces a delete-stream, then this stream
         * should also produce a delete-stream.
         */
        boolean dStream = false;

        for (String key : this.sources.keySet()) {
            Stream s = this.sources.get(key);

            if (s instanceof ProxyStream) {
                continue;
            }

            if (tupleTypes.add(s.getOutputInfo().getContainerClass()) == false) {
                throw new CustomRuntimeException(id, "The Source Tuple Class: "
                        + s.getOutputInfo().getContainerClass() + " is not unique");
            }

            dStream = dStream || s.producesDStream();
        }

        if (this.nestedSources != null) {
            /*
             * If there are nested-streams (Peteys), then there will be
             * DStreams.
             */
            dStream = true;

            for (NestedSource nestedSource : this.nestedSources) {
                AbstractPetey petey = nestedSource.getPetey();
                Stream s = nestedSource.getStream();

                if (s instanceof ProxyStream) {
                    throw new CustomRuntimeException(id, "Nested Source cannot be a ProxyStream.");
                }

                Map<String, TupleInfo> outerAliasAndInfos = petey
                        .getOuterTupleAliasAndInfos();
                for (String alias : outerAliasAndInfos.keySet()) {
                    if (this.sources.containsKey(alias) == false) {
                        throw new CustomRuntimeException(id, "The Outer Tuple alias: " + alias
                                + " does not have a matching alias in the Source Streams: "
                                + this.sources.keySet());
                    }
                }

                for (TupleInfo info : outerAliasAndInfos.values()) {
                    // No need to add Outer Tuple types.
                    if (tupleTypes.contains(info.getContainerClass()) == false) {
                        throw new CustomRuntimeException(id, "The Outer Tuple Class: "
                                + info.getContainerClass() + " is not a valid Stream output Type");
                    }
                }

                for (String alias : outerAliasAndInfos.keySet()) {
                    HashSet<AbstractPetey> outerPeteys = outerTuplePeteys.get(alias);
                    if (outerPeteys == null) {
                        outerPeteys = new HashSet<AbstractPetey>();
                        outerTuplePeteys.put(alias, outerPeteys);
                    }
                    outerPeteys.add(petey);
                }

                // ---------

                String innerAlias = petey.getInnerTupleAlias();
                if (innerTuplePeteys.put(innerAlias, petey) != null) {
                    throw new CustomRuntimeException(id, "The alias: " + innerAlias
                            + " is not unique");
                }

                if (tupleTypes.add(petey.getInnerTupleInfo().getContainerClass()) == false
                        && (s instanceof ProxyStream) == false) {
                    throw new CustomRuntimeException(id, "The Tuple Class: "
                            + petey.getInnerTupleInfo().getContainerClass() + " is not unique");
                }

                // ---------

                NestedStreamListener myListener = new NestedStreamListener(petey);
                s.setListener(myListener);

                nestedSourceListeners.add(myListener);
            }
        }

        for (String alias : this.sources.keySet()) {
            Stream s = this.sources.get(alias);
            StreamListener myListener = new StreamListener(alias, outerTuplePeteys.get(alias));
            s.setListener(myListener);

            sourceListeners.add(myListener);
        }

        // ---------

        this.producesDStream = dStream;
        if (this.producesDStream) {
            this.tupleTracker = new JoinedStreamTupleTracker();
        }
        else {
            this.tupleTracker = null;
        }

        this.stopped = new AtomicBoolean();
    }

    @Override
    public JoinedTupleInfo getOutputInfo() {
        return (JoinedTupleInfo) super.getOutputInfo();
    }

    @Override
    public boolean producesDStream() {
        return producesDStream;
    }

    public Expression getJoinExpression() {
        return joinExpression;
    }

    public Collection<NestedSource> getNestedSources() {
        return nestedSources;
    }

    public Map<String, Stream> getSources() {
        return sources;
    }

    protected void beforeHandling(Context context) {
    }

    protected abstract void handleSource(Context context) throws Exception;

    protected void afterHandling(Context context) {
    }

    public boolean isStopped() {
        return stopped.get();
    }

    @Override
    public void stop() throws Exception {
        super.stop();

        if (nestedSources != null) {
            for (NestedSource nestedSource : nestedSources) {
                nestedSource.getPetey().discard();
            }
        }

        if (tupleTracker != null) {
            tupleTracker.discard();
        }
    }

    // ----------

    public class AbstractListener {
        public void doStop() {
            if (stopped.get()) {
                return;
            }

            synchronized (stopped) {
                if (stopped.get()) {
                    return;
                }

                try {
                    JoinedStream.this.stop();
                }
                catch (Exception e) {
                    Logger logger = Registry.getInstance().getComponent(Logger.class);
                    logger.log(Logger.LogLevel.WARNING, e);
                }

                stopped.set(true);
            }
        }
    }

    public class StreamListener extends AbstractListener
            implements com.tibco.cep.query.stream.core.StreamListener {
        protected final AbstractPetey[] outerTuplePeteys;

        protected final String alias;

        /**
         * @param alias
         * @param outerTuplePeteys Can be <code>null</code>.
         */
        public StreamListener(String alias, HashSet<AbstractPetey> outerTuplePeteys) {
            this.alias = alias;
            this.outerTuplePeteys = (outerTuplePeteys == null) ? null :
                    outerTuplePeteys.toArray(new AbstractPetey[outerTuplePeteys.size()]);
        }

        public void process(Context context) throws Exception {
            final LocalContext lc = context.getLocalContext();
            final GlobalContext gc = context.getGlobalContext();
            final DefaultQueryContext qc = context.getQueryContext();
            final AbstractPetey[] cachedOuterTuplePeteys = outerTuplePeteys;
            final String cachedAlias = alias;

            JoinedStream.this.beforeHandling(context);

            Collection<? extends Tuple> deadTuples = lc.getDeadTuples();
            if (deadTuples != null && cachedOuterTuplePeteys != null) {
                for (Tuple tuple : deadTuples) {
                    for (AbstractPetey petey : cachedOuterTuplePeteys) {
                        petey.removeOuter(gc, qc, cachedAlias, tuple);
                    }
                }
            }

            Collection<? extends Tuple> newTuples = lc.getNewTuples();
            if (newTuples != null && cachedOuterTuplePeteys != null) {
                for (Tuple tuple : newTuples) {
                    for (AbstractPetey petey : cachedOuterTuplePeteys) {
                        petey.addOuter(gc, qc, cachedAlias, tuple);
                    }
                }
            }

            JoinedStream.this.handleSource(context);

            JoinedStream.this.afterHandling(context);

            JoinedStream.this.process(context);

            if (Flags.TRACK_TUPLE_REFS) {
                if (deadTuples != null) {
                    for (Tuple tuple : deadTuples) {
                        // End of the road for this Tuple.
                        tuple.decrementRefCount();
                    }
                }
            }
        }

        public void stop() {
            doStop();
        }
    }

    public class NestedStreamListener extends AbstractListener
            implements com.tibco.cep.query.stream.core.StreamListener {
        protected final AbstractPetey innerTuplePetey;

        public NestedStreamListener(AbstractPetey innerTuplePetey) {
            this.innerTuplePetey = innerTuplePetey;
        }

        public void process(Context context) throws Exception {
            // Doesn't dump Events into the Rete. Goes directly into Petey.

            final LocalContext lc = context.getLocalContext();
            final GlobalContext gc = context.getGlobalContext();
            final DefaultQueryContext qc = context.getQueryContext();
            final AbstractPetey cachedInnerTuplePetey = innerTuplePetey;

            JoinedStream.this.beforeHandling(context);

            Collection<? extends Tuple> deadTuples = lc.getDeadTuples();
            if (deadTuples != null) {
                for (Tuple tuple : deadTuples) {
                    cachedInnerTuplePetey.removeInner(gc, qc, tuple);
                }
            }

            Collection<? extends Tuple> newTuples = lc.getNewTuples();
            if (newTuples != null) {
                for (Tuple tuple : newTuples) {
                    cachedInnerTuplePetey.addInner(gc, qc, tuple);
                }
            }

            JoinedStream.this.afterHandling(context);

            JoinedStream.this.process(context);

            if (Flags.TRACK_TUPLE_REFS) {
                if (deadTuples != null) {
                    for (Tuple tuple : deadTuples) {
                        // End of the road for this Tuple.
                        tuple.decrementRefCount();
                    }
                }
            }
        }

        public void stop() {
            doStop();
        }
    }
}
