package com.tibco.rta;

import com.tibco.rta.RtaConnection;
import com.tibco.rta.client.ServiceInvocationListener;
import com.tibco.rta.client.ServiceResponse;
import com.tibco.rta.service.transport.TransportTypes;

import java.util.Map;

/**
 * Internal interface, not meant to be exposed yet
 */
public interface RtaConnectionEx extends RtaConnection {

    /**
     * Invoke a service on the metric engine.
     * The service is identified using an abstract endpoint and operation
     * to be invoked on the service.
     * @param endpoint A url depending on the underlying transport
     * @param serviceOp The operation on the service
     * @param properties Any optional properties which will be sent over
     * @param payload Payload string
     * @return a response from the service.
     * @throws Exception
     */
    ServiceResponse invokeService(String endpoint, String serviceOp, Map<String, String> properties, String payload) throws Exception;

    /**
     * Invoke a service on the metric engine.
     * The service is identified using an abstract endpoint and operation
     * to be invoked on the service.
     * @param endpoint A url depending on the underlying transport
     * @param serviceOp The operation on the service
     * @param properties Any optional properties which will be sent over
     * @param payload Payload bytes
     * @return a response from the service.
     * @throws Exception
     */
    ServiceResponse invokeService(String endpoint, String serviceOp, Map<String, String> properties, byte[] payload) throws Exception;

    /**
     * Invoke a service on the metric engine.
     * The service is identified using an abstract endpoint and operation
     * to be invoked on the service.
     * @param endpoint A url depending on the underlying transport
     * @param serviceOp The operation on the service
     * @param properties Any optional properties which will be sent over
     * @param payload Payload string
     * @param factPublisherListener Callback notified upon receiving response
     * @return ServiceResponse response from service if any
     * @throws Exception
     */
    ServiceResponse invokeService(String endpoint, String serviceOp, Map<String, String> properties, String payload, ServiceInvocationListener factPublisherListener) throws Exception;

    /**
     * Invoke a service on the metric engine.
     * The service is identified using an abstract endpoint and operation
     * to be invoked on the service.
     * @param endpoint A url depending on the underlying transport
     * @param serviceOp The operation on the service
     * @param properties Any optional properties which will be sent over
     * @param payload Payload bytes
     * @param factPublisherListener Callback notified upon receiving response
     * @return ServiceResponse response from service if any
     * @throws Exception
     */
    ServiceResponse invokeService(String endpoint, String serviceOp, Map<String, String> properties, byte[] payload, ServiceInvocationListener factPublisherListener) throws Exception;

    /**
     *
     * @return
     */
    TransportTypes getTransportType();

    /**
     *
     * @return
     */
    String getScheme();

    /**
     *
     * @return
     */
    String getHost();

    /**
     *
     * @return
     */
    int getPort();

    /**
     *
     * @return
     */
    @Deprecated
    String getClientId();

    /**
     *
     */
    @Deprecated
    void setClientId(String clientId);

    /**
     * Whether this connection should be used to send any heartbeats to SPM engine.
     * @return
     */
    boolean shouldSendHeartbeat();
}
