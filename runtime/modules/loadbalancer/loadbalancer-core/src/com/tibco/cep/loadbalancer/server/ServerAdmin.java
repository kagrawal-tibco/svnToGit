package com.tibco.cep.loadbalancer.server;

import com.tibco.cep.common.exception.LifecycleException;
import com.tibco.cep.service.Service;

/*
* Author: Ashwin Jayaprakash / Date: Jun 10, 2010 / Time: 4:04:31 PM
*/

public interface ServerAdmin<R> extends Service {
    /**
     * Starts the server when called the first time.
     *
     * @param r
     * @return
     * @throws LifecycleException
     */
    Server getServerFor(R r) throws LifecycleException;
}
