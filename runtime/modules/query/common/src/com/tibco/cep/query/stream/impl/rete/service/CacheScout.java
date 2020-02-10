package com.tibco.cep.query.stream.impl.rete.service;

import com.tibco.cep.query.stream.core.ControllableResource;
import com.tibco.cep.query.stream.impl.rete.GenericReteEntityHandle;
import com.tibco.cep.query.stream.impl.rete.query.ReteQuery;

/*
* Author: Ashwin Jayaprakash Date: Jul 30, 2008 Time: 1:43:53 PM
*/
public interface CacheScout extends ControllableResource {
    void startWarming(ReteQuery reteQuery);

    void stopWarming(ReteQuery reteQuery);

    boolean isAggressivePrefetchSupported();

    void prefetchNow(GenericReteEntityHandle handle);
}
