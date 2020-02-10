package com.tibco.rta.common.service;

import com.tibco.rta.service.transport.ServiceEndpoint;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 30/10/12
 * Time: 6:06 AM
 * Abstract factory to instantiate {@link StartStopService} specific to transport endpoints.
 */
public abstract class AbstractServiceProviderFactory<E extends ServiceEndpoint> {
    

    /**
     *
     * @param serviceEndpoint
     * @return
     */
    protected abstract StartStopService getServiceProviderInstance(E serviceEndpoint) throws Exception;
}
