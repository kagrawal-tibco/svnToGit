package com.tibco.rta.client.transport;

import com.tibco.rta.Fact;
import com.tibco.rta.RtaConnectionEx;
import com.tibco.rta.client.ServiceResponse;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 17/3/13
 * Time: 3:41 AM
 * Strategy for message transmission to metric engine.
 */
public interface MessageTransmissionStrategy {

    /**
     * Perform initialization activity
     *
     * @throws Exception
     */
    public void init() throws Exception;

    /**
     * @throws Exception
     */
    public void shutdown() throws Exception;

    /**
     * @param item
     * @param <T>
     */
    public <T extends Fact> boolean enqueueForTransmission(T item);

    /**
     * Take queued item. This is an optional operation.
     *
     * @param <T>
     */
    public <T> T takeQueued();

    /**
     * @return
     */
    public RtaConnectionEx getOwnerConnection();

    /**
     *
     * @param connection
     */
    public <R extends RtaConnectionEx> void setOwnerConnection(R connection);

    /**
     *
     */
    public ServiceResponse<?> transmit(String endpoint, String serviceOp, Map<String, String> properties, String payload) throws Exception;

    /**
     *
     */
    public ServiceResponse<?> transmit(String endpoint, String serviceOp, Map<String, String> properties, byte[] payload) throws Exception;
}
