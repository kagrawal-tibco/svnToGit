package com.tibco.cep.query.stream.partition.purge;

import com.tibco.cep.query.stream.context.Context;
import com.tibco.cep.query.stream.context.LocalContext;
import com.tibco.cep.query.stream.core.AbstractStream;
import com.tibco.cep.query.stream.core.SubStream;
import com.tibco.cep.query.stream.core.SubStreamOwner;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.partition.Window;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.CustomCollection;

/*
 * Author: Ashwin Jayaprakash Date: Nov 20, 2007 Time: 1:14:33 PM
 */

public class WindowSpy extends AbstractStream<SubStream> implements SubStream<SubStreamOwner> {
    protected final Window window;

    protected final boolean mainWindowProducesDStream;

    protected SubStreamOwner subStreamOwner;

    /**
     * @param id
     * @param source
     */
    public WindowSpy(ResourceId id, Window source) {
        // Does not have the usual Source.
        super(null, id, source.getOutputInfo());

        this.window = source;
        this.mainWindowProducesDStream = source.producesDStream();
    }

    public Window getWindow() {
        return window;
    }

    public void setOwner(SubStreamOwner owner) {
        this.subStreamOwner = owner;
    }

    public SubStreamOwner getOwner() {
        return subStreamOwner;
    }

    public void cycleStart(Context context) throws Exception {
        if (listener != null) {
            listener.cycleStart(context);
        }
    }

    @Override
    protected void doProcessing(Context context) throws Exception {
        super.doProcessing(context);

        final LocalContext lc = context.getLocalContext();

        CustomCollection<? extends Tuple> newTuples = lc.getNewTuples();
        CustomCollection<? extends Tuple> deadTuples = lc.getDeadTuples();

        localContext.setNewTuples(newTuples);
        localContext.setDeadTuples(deadTuples);
    }

    public void cycleEnd(Context context) throws Exception {
        if (listener != null) {
            listener.cycleEnd(context);
        }
    }

    public boolean canDiscard() {
        return window.canDiscard();
    }

    @Override
    public boolean producesDStream() {
        return mainWindowProducesDStream;
    }

    @Override
    public void stop() throws Exception {
        super.stop();

        if (resourceId != null) {
            resourceId.discard();
        }
    }
}
