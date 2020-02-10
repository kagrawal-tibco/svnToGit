package com.tibco.cep.query.stream.core;

import com.tibco.cep.query.stream.context.Context;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.CustomCollection;

/*
 * Author: Ashwin Jayaprakash Date: Nov 7, 2007 Time: 11:31:01 AM
 */
/**
 * <p> The output type is the same as the Source. </p> A simple Adapter Stream that drops the dead
 * Tuples sent by the source Stream. Only the live Tuples are forwarded.
 */
public class IStream extends AbstractStream {
    public IStream(Stream source, ResourceId id) {
        super(source, id, source.getOutputInfo());
    }

    @Override
    protected void doProcessing(Context context) throws Exception {
        super.doProcessing(context);

        CustomCollection<? extends Tuple> newTuples = context.getLocalContext().getNewTuples();
        localContext.setNewTuples(newTuples);
    }

    @Override
    public boolean producesDStream() {
        return false;
    }
}
