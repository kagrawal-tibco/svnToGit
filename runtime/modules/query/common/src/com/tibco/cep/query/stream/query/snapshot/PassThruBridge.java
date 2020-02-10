package com.tibco.cep.query.stream.query.snapshot;

import com.tibco.cep.query.stream.context.Context;
import com.tibco.cep.query.stream.core.Stream;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.CustomCollection;

/*
* Author: Ashwin Jayaprakash Date: Mar 20, 2008 Time: 4:49:07 PM
*/

/**
 * <p>Bridge that just keeps streaming the tuples synchronously to the {@link #batchResultReceiver}.
 * Does not store anything. This should be used only if the predecessor of this bridge does
 * <b>not</b> produce a delete-stream.</p> <p>This also needs a {@link
 * com.tibco.cep.query.stream.io.StreamedSink} instead of the usual {@link
 * com.tibco.cep.query.stream.io.StaticSink} .</p>
 */
public class PassThruBridge extends Bridge {
    /**
     * @param source
     * @param id
     */
    public PassThruBridge(Stream source, ResourceId id) {
        super(source, id);
    }

    @Override
    protected void doProcessing(Context context) throws Exception {
        super.doProcessing(context);

        batchResultReceiver.process(context);
    }

    @Override
    protected CustomCollection<? extends Tuple> getFinalCollectedTuples(Context context) {
        return null;
    }

    @Override
    protected void clearFinalCollectedTuples(Context context) {
    }
}
