package com.tibco.cep.query.stream.core;

import com.tibco.cep.query.stream.context.Context;
import com.tibco.cep.query.stream.monitor.KnownResource;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.tuple.TupleInfo;
import com.tibco.cep.query.stream.util.CustomCollection;

/*
 * Author: Ashwin Jayaprakash Date: Oct 3, 2007 Time: 2:52:12 PM
 */

public interface Source extends KnownResource {
    public ResourceId getResourceId();

    public TupleInfo getOutputInfo();

    public Stream getInternalStream();

    public void sendNewTuple(Context context, Tuple tuple) throws Exception;

    public void sendNewTuples(Context context, CustomCollection<? extends Tuple> tuples)
            throws Exception;

    public void stop() throws Exception;
}
