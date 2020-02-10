package com.tibco.cep.query.stream.core;

import com.tibco.cep.query.stream.context.Context;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.CustomCollection;

/*
 * Author: Ashwin Jayaprakash Date: Feb 13, 2008 Time: 5:55:39 PM
 */

/**
 * @see com.tibco.cep.query.stream.core.IStream
 */
public class ISubStream extends AbstractStream<SubStream> implements SubStream<SubStreamOwner> {
    protected SubStreamOwner owner;

    public ISubStream(Stream source, ResourceId id) {
        super(source, id, source.getOutputInfo());
    }

    public SubStreamOwner getOwner() {
        return owner;
    }

    public void setOwner(SubStreamOwner owner) {
        this.owner = owner;
    }

    @Override
    public boolean producesDStream() {
        return false;
    }

    /**
     * @return <code>false</code> always!
     */
    public boolean canDiscard() {
        return false;
    }

    public void cycleStart(Context context) throws Exception {
        if (listener != null) {
            listener.cycleStart(context);
        }
    }

    @Override
    protected void doProcessing(Context context) throws Exception {
        super.doProcessing(context);

        CustomCollection<? extends Tuple> newTuples = context.getLocalContext().getNewTuples();
        localContext.setNewTuples(newTuples);
    }

    public void cycleEnd(Context context) throws Exception {
        if (listener != null) {
            listener.cycleEnd(context);
        }
    }

    @Override
    public void stop() throws Exception {
        super.stop();

        owner = null;
    }
}
