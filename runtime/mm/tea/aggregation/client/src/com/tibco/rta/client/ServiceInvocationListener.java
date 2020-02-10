package com.tibco.rta.client;

/**
 * Implementations interested in receiving callback upon
 * service invocation would need to implement this interface.
 * <p>
 *     Clients may implement this interface.
 * </p>
 */
public interface ServiceInvocationListener {

    /**
     * Callback invoked when fact is published with metric engine.
     * @param serviceResponse Response received from service.
     */
    public void serviceInvoked(ServiceResponse serviceResponse);
}
