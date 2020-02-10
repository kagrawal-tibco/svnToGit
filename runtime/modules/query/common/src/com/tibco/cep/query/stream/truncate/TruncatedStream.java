package com.tibco.cep.query.stream.truncate;

import java.util.Iterator;

import com.tibco.cep.query.stream.context.Context;
import com.tibco.cep.query.stream.context.LocalContext;
import com.tibco.cep.query.stream.core.AbstractStream;
import com.tibco.cep.query.stream.core.Stream;
import com.tibco.cep.query.stream.expression.ExpressionEvaluator;
import com.tibco.cep.query.stream.monitor.Flags;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.tuple.TupleInfo;
import com.tibco.cep.query.stream.util.AppendOnlyQueue;
import com.tibco.cep.query.stream.util.ArrayPool;
import com.tibco.cep.query.stream.util.CustomCollection;
import com.tibco.cep.query.stream.util.CustomHashSet;

/*
 * Author: Ashwin Jayaprakash Date: Nov 7, 2007 Time: 2:14:36 PM
 */

/**
 * <p/> The output type is the same as the Source. </p> <p/> The same Tuple can go through over and
 * over, if it is within the limit set. </p>
 */
public class TruncatedStream extends AbstractStream {
    protected int firstX;

    protected int firstXOffset;

    protected final CustomHashSet<Number> tupleIdsThatWentThruAtleastOnce;

    private boolean initFirstXBeforeUse;

    private AppendOnlyQueue<Tuple> truncatedNewTuples;

    private AppendOnlyQueue<Tuple> selectedDeadTuples;

    protected ExpressionEvaluator firstXEval;

    protected ExpressionEvaluator firstXOffsetEval;

    /**
     * @param source       Uses this stream's {@link Stream#getOutputInfo()}.
     * @param id
     * @param firstX
     * @param firstXOffset
     */
    public TruncatedStream(Stream source, ResourceId id, int firstX, int firstXOffset) {
        this(source, id, source.getOutputInfo(), firstX, firstXOffset);
    }

    /**
     * @param id
     * @param outputInfo
     * @param firstX
     * @param firstXOffset
     */
    public TruncatedStream(ResourceId id, TupleInfo outputInfo, int firstX, int firstXOffset) {
        this(null, id, outputInfo, firstX, firstXOffset);
    }

    /**
     * @param id
     * @param outputInfo
     * @param firstX
     * @param firstXOffset
     */
    public TruncatedStream(ResourceId id, TupleInfo outputInfo,
                           ExpressionEvaluator firstX, ExpressionEvaluator firstXOffset) {
        this(id, outputInfo, Integer.MAX_VALUE, 0);

        this.firstXEval = firstX;
        this.firstXOffsetEval = firstXOffset;
        this.initFirstXBeforeUse = true;
    }

    /**
     * @param source       Can be <code>null</code>.
     * @param id
     * @param firstX
     * @param firstXOffset
     */
    public TruncatedStream(Stream source, ResourceId id,
                           ExpressionEvaluator firstX, ExpressionEvaluator firstXOffset) {
        this(source, id, source.getOutputInfo(), Integer.MAX_VALUE, 0);

        this.firstXEval = firstX;
        this.firstXOffsetEval = firstXOffset;
        this.initFirstXBeforeUse = true;
    }

    /**
     * @param source       Can be <code>null</code>.
     * @param id
     * @param outputInfo   Cannot be <code>null</code>.
     * @param firstX
     * @param firstXOffset
     */
    protected TruncatedStream(Stream source, ResourceId id, TupleInfo outputInfo,
                              int firstX, int firstXOffset) {
        super(source, id, outputInfo);

        //Assume that we don't have to.
        this.initFirstXBeforeUse = false;

        this.firstX = firstX;
        this.firstXOffset = firstXOffset;

        if (sourceProducesDStream) {
            this.tupleIdsThatWentThruAtleastOnce = new CustomHashSet<Number>();
        }
        else {
            this.tupleIdsThatWentThruAtleastOnce = null;
        }
    }

    public int getFirstX(Context context) {
        initIfRequired(context);

        return firstX;
    }

    public int getFirstXOffset(Context context) {
        initIfRequired(context);

        return firstXOffset;
    }

    private void initIfRequired(Context context) {
        if (initFirstXBeforeUse) {
            firstX = firstXEval
                    .evaluateInteger(context.getGlobalContext(), context.getQueryContext(), null);

            firstXOffset = firstXOffsetEval
                    .evaluateInteger(context.getGlobalContext(), context.getQueryContext(), null);

            initFirstXBeforeUse = false;
        }
    }

    @Override
    protected void doProcessing(Context context) throws Exception {
        super.doProcessing(context);

        //Hope that Jit inlines this code and the CPU's will branch prediction will optimize this away.
        initIfRequired(context);

        final LocalContext lc = context.getLocalContext();
        CustomCollection<? extends Tuple> deadTuples = lc.getDeadTuples();
        CustomCollection<? extends Tuple> newTuples = lc.getNewTuples();

        if (truncatedNewTuples == null) {
            ArrayPool arrayPool = context.getQueryContext().getArrayPool();

            selectedDeadTuples = new AppendOnlyQueue<Tuple>(arrayPool);
            truncatedNewTuples = new AppendOnlyQueue<Tuple>(arrayPool);
        }

        if (deadTuples != null) {
            for (Tuple t : deadTuples) {
                /*
                 * Send only those Tuples that made it into the live-stream at
                 * least once.
                 */
                if (tupleIdsThatWentThruAtleastOnce.remove(t.getId())) {
                    selectedDeadTuples.add(t);
                }
                else {
                    if (Flags.TRACK_TUPLE_REFS) {
                        // End of the road for this Tuple.
                        t.decrementRefCount();
                    }
                }
            }

            deadTuples = selectedDeadTuples.isEmpty() ? null : selectedDeadTuples;
        }
        localContext.setDeadTuples(deadTuples);

        if (newTuples != null) {
            CustomCollection truncatedTuples = null;

            if (firstXOffset == 0 && newTuples.size() <= firstX) {
                truncatedTuples = newTuples;
            }
            else {
                truncatedTuples = truncatedNewTuples;
                final int total = firstX + firstXOffset;

                Iterator<? extends Tuple> iter = newTuples.iterator();
                int ctr = 0;
                while (ctr < total && iter.hasNext()) {
                    Tuple t = iter.next();

                    if (ctr >= firstXOffset) {
                        truncatedTuples.add(t);

                        if (tupleIdsThatWentThruAtleastOnce != null) {
                            tupleIdsThatWentThruAtleastOnce.add(t.getId());
                        }
                    }

                    ctr++;
                }
            }

            truncatedTuples = (truncatedTuples == null || truncatedTuples.isEmpty()) ? null
                    : truncatedTuples;
            localContext.setNewTuples(truncatedTuples);
        }
    }

    @Override
    protected void endProcessing(Context context) {
        super.endProcessing(context);

        if (truncatedNewTuples != null) {
            truncatedNewTuples.clear();
            selectedDeadTuples.clear();
        }
    }

    @Override
    public void stop() throws Exception {
        super.stop();

        if (truncatedNewTuples != null) {
            truncatedNewTuples.clear();
            truncatedNewTuples = null;

            selectedDeadTuples.clear();
            selectedDeadTuples = null;
        }

        if (tupleIdsThatWentThruAtleastOnce != null) {
            tupleIdsThatWentThruAtleastOnce.clear();
        }
    }
}
