package com.tibco.cep.query.stream.join;

import com.tibco.cep.query.stream.core.AbstractStream;
import com.tibco.cep.query.stream.core.Stream;
import com.tibco.cep.query.stream.monitor.ResourceId;

/*
 * Author: Ashwin Jayaprakash Date: Nov 29, 2007 Time: 2:43:00 PM
 */

/**
 * <p>
 * No direct source. Just refers to another Stream by a different
 * {@link ResourceId}.
 * </p>
 * <p>
 * Does not receive any Tuples, nor does it produce any of its own.
 * </p>
 */
public class ProxyStream extends AbstractStream {
    protected final Stream actualStream;

    public ProxyStream(ResourceId id, Stream actualStream) {
        super(null/* No direct Source. */, id, actualStream.getOutputInfo());

        this.actualStream = actualStream;
    }

    public Stream getActualStream() {
        return actualStream;
    }

    @Override
    public boolean producesDStream() {
        return actualStream.producesDStream();
    }
}
