package com.tibco.cep.query.stream.core;

import com.tibco.cep.query.stream.context.Context;

/*
 * Author: Ashwin Jayaprakash Date: Jan 10, 2008 Time: 4:51:41 PM
 */

/**
 * <p>SubStreams can only have another Substream as a listener. This narrows the contract on the
 * listener type.</p> <p>SubStreams are different from plain Streams in that the {@link
 * #process(com.tibco.cep.query.stream.context.Context)} method gets invoked more than once inside a
 * cycle - which is marked by a {@link #cycleStart(com.tibco.cep.query.stream.context.Context)} and
 * {@link #cycleEnd(com.tibco.cep.query.stream.context.Context)}.</p>
 */
public interface SubStream<SO extends SubStreamOwner> extends Stream<SubStream> {
    public void setOwner(SO owner);

    public SO getOwner();

    public void cycleStart(Context context) throws Exception;

    public void cycleEnd(Context context) throws Exception;

    public boolean canDiscard();
}
