package com.tibco.cep.query.stream.core;

import java.util.Set;

import com.tibco.cep.query.stream.context.Context;
import com.tibco.cep.query.stream.context.DefaultQueryContext;
import com.tibco.cep.query.stream.context.LocalContext;
import com.tibco.cep.query.stream.monitor.CustomException;
import com.tibco.cep.query.stream.monitor.KnownResource;
import com.tibco.cep.query.stream.monitor.Logger;
import com.tibco.cep.query.stream.monitor.QueryWatcher;
import com.tibco.cep.query.stream.monitor.QueryWatcher.Run;
import com.tibco.cep.query.stream.monitor.QueryWatcher.Step;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.tuple.TupleInfo;
import com.tibco.cep.query.stream.util.CustomHashSet;

/*
 * Author: Ashwin Jayaprakash Date: Oct 1, 2007 Time: 2:57:21 PM
 */

/**
 * <p>Note: The fields in this class and many other hotspot classes may seem to be in random order.
 * But they have been arranged in order of most->least frequent access. These first few "hot" fields
 * are meant to be in the same Cacheline as the Object Header.</p>
 */
public abstract class AbstractStream<L extends StreamListener> implements Stream<L> {
    protected final LocalContext localContext;

    protected final boolean sourceProducesDStream;

    protected L listener;

    /**
     * Internal elements are of type {@link L}.
     */
    protected StreamListener[] additionalListenersCache;

    protected final Stream<? extends StreamListener> source;

    protected final ResourceId resourceId;

    protected final TupleInfo outputInfo;

    protected CustomHashSet<L> additionalListeners;

    /**
     * @param source     Can be <code>null</code>
     * @param id
     * @param outputInfo
     */
    public <SL extends StreamListener, S extends Stream<SL>> AbstractStream(S source, ResourceId id,
                                                                            TupleInfo outputInfo) {
        this.resourceId = id;
        this.outputInfo = outputInfo;

        this.localContext = new LocalContext();

        this.source = source;
        if (this.source != null) {
            Stream wildcardGenericsWorkaround = this.source;
            wildcardGenericsWorkaround.setListener(this);

            this.sourceProducesDStream = this.source.producesDStream();
        }
        else {
            this.sourceProducesDStream = false;
        }
    }

    public LocalContext getLocalContext() {
        return localContext;
    }

    public L getListener() {
        return listener;
    }

    public void setListener(L listener) {
        this.listener = listener;
    }

    public Set<L> getAdditionalListeners() {
        return additionalListeners;
    }

    public void addAdditionalListener(L anotherListener) {
        if (additionalListeners == null) {
            additionalListeners = new CustomHashSet<L>();
        }

        additionalListeners.add(anotherListener);
        additionalListenersCache =
                additionalListeners.toArray(new StreamListener[additionalListeners.size()]);
    }

    public TupleInfo getOutputInfo() {
        return outputInfo;
    }

    public ResourceId getResourceId() {
        return resourceId;
    }

    public Stream<? extends StreamListener> getSource() {
        return source;
    }

    /**
     * <p/> Does not store the contents after processing - i.e clears the {@link
     * com.tibco.cep.query.stream.context.LocalContext}. </p>
     *
     * @param context
     * @throws Exception
     */
    public final void process(Context context) throws Exception {
        final DefaultQueryContext qc = context.getQueryContext();
        final QueryWatcher queryWatcher = qc.getQueryWatcher();
        final LocalContext sendersContext = context.getLocalContext();

        Step step = null;

        try {
            Run run = queryWatcher.getCurrentRun();
            if (run != null) {
                step = run.appendNewStep(getResourceId());
            }

            //-----------

            qc.removeIfStreamInCurrentCycle(this);

            //-----------

            doProcessing(context);

            //-----------

            //Change the context now.
            context.setLocalContext(localContext);

            try {
                if (listener != null) {
                    listener.process(context);
                }

                if (additionalListeners != null) {
                    for (StreamListener additionalListener : additionalListenersCache) {
                        additionalListener.process(context);
                    }
                }
            }
            finally {
                // Re-instate the original Context.
                context.setLocalContext(sendersContext);
            }

            //-----------

            if (step != null) {
                step.recordEnd();
            }
        }
        catch (Throwable t) {
            if ((t instanceof KnownResource) == false) {
                throw new CustomException(getResourceId(), t);
            }

            throw (Exception) t;
        }
        finally {
            endProcessing(context);
        }
    }

    /**
     * Do actual work here in Sub-class. If there are any Tuples to be sent either alive or dead or
     * both, then place them in the {@link LocalContext}. If not, send <code>null</code>s.
     *
     * @param context
     * @throws Exception
     */
    protected void doProcessing(Context context) throws Exception {
    }

    /**
     * <p/> Invoked after all processing is done and method is about to return - regardless of
     * whether the main processing step resulted in errors or not. </p> <p/> {@link #localContext}
     * gets cleared here. </p>
     *
     * @param context
     */
    protected void endProcessing(Context context) {
        localContext.clear();
    }

    public boolean producesDStream() {
        return sourceProducesDStream;
    }

    public void stop() throws Exception {
        if (listener != null) {
            try {
                listener.stop();
            }
            catch (Exception e) {
                Logger logger = Registry.getInstance().getComponent(Logger.class);
                logger.log(Logger.LogLevel.ERROR, e);
            }
        }
        listener = null;

        if (additionalListeners != null) {
            for (StreamListener additionalListener : additionalListenersCache) {
                try {
                    additionalListener.stop();
                }
                catch (Exception e) {
                    Logger logger = Registry.getInstance().getComponent(Logger.class);
                    logger.log(Logger.LogLevel.ERROR, e);
                }
            }
        }
        additionalListeners = null;
        additionalListenersCache = null;

        localContext.clear();

        resourceId.discard();
    }
}
