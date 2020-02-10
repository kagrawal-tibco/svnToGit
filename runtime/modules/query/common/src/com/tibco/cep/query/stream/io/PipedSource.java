package com.tibco.cep.query.stream.io;

import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.tuple.TupleInfo;

/*
 * Author: Ashwin Jayaprakash Date: Dec 5, 2007 Time: 11:15:13 AM
 */

public class PipedSource extends SourceImpl {
    protected final ResourceId originalSourceResourceId;

    protected final TupleInfo originalSourceOutputInfo;

    public PipedSource(ResourceId id, TupleInfo outputInfo, ResourceId pipeSourceResourceId,
                       TupleInfo pipeSourceOutputInfo) {
        super(id, outputInfo);

        this.originalSourceResourceId = pipeSourceResourceId;
        this.originalSourceOutputInfo = pipeSourceOutputInfo;
    }

    public TupleInfo getOriginalSourceOutputInfo() {
        return originalSourceOutputInfo;
    }

    public ResourceId getOriginalSourceResourceId() {
        return originalSourceResourceId;
    }
}
