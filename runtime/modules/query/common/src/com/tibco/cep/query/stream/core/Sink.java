package com.tibco.cep.query.stream.core;

import com.tibco.cep.query.stream.monitor.KnownResource;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.tuple.TupleInfo;

/*
 * Author: Ashwin Jayaprakash Date: Feb 15, 2008 Time: 4:56:10 PM
 */

public interface Sink extends KnownResource {
    public TupleInfo getOutputInfo();

    public Stream getInternalStream();

    public ResourceId getResourceId();
}
