package com.tibco.cep.query.stream.query.snapshot;

import com.tibco.cep.query.stream.context.Context;
import com.tibco.cep.query.stream.core.Stream;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.truncate.TruncatedStream;

/*
 * Author: Ashwin Jayaprakash Date: Mar 3, 2008 Time: 11:04:25 AM
 */

/**
 * To be used only where the Query is defined to have a Truncate-only segment.
 */
public class TruncatedBridge extends SimpleBridge {
    protected boolean continueInput;

    protected int firstX;

    protected int firstXOffset;

    private boolean initFirstX;

    /**
     * @param source
     * @param id
     */
    public TruncatedBridge(Stream source, ResourceId id) {
        super(source, id);

        this.continueInput = true;
        this.initFirstX = true;
    }

    /**
     * @param batchResultReceiver Has to be an instance of {@link TruncatedStream}.
     */
    @Override
    public void setup(Stream batchResultReceiver) {
        TruncatedStream ts = (TruncatedStream) batchResultReceiver;
        super.setup(ts);
    }

    @Override
    public TruncatedStream getBatchResultReceiver() {
        return (TruncatedStream) super.getBatchResultReceiver();
    }

    @Override
    protected void doProcessing(Context context) throws Exception {
        super.doProcessing(context);

        //
        if (initFirstX) {
            TruncatedStream ts = (TruncatedStream) batchResultReceiver;

            firstX = ts.getFirstX(context);
            firstXOffset = ts.getFirstXOffset(context);

            initFirstX = false;
        }

        if (collectedTuples.size() >= firstX + firstXOffset) {
            // Request stop here. Sufficient input received.
            continueInput = false;
        }
    }

    @Override
    public boolean canContinueStreamInput() {
        return continueInput;
    }
}
