package com.tibco.rta.client.taskdefs.impl;

import com.tibco.rta.client.ServiceResponse;
import com.tibco.rta.client.taskdefs.AbstractClientTask;
import com.tibco.rta.client.transport.MessageTransmissionStrategy;
import com.tibco.rta.common.service.ServiceType;
import com.tibco.rta.client.util.RtaSessionUtil;
import com.tibco.rta.model.serialize.impl.ModelJSONDeserializer;
import com.tibco.rta.util.InvalidQueryException;

import static com.tibco.rta.util.ServiceConstants.ERROR;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 4/2/13
 * Time: 2:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class FetchResultsTask extends AbstractClientTask {

    private ServiceType serviceType;

    private String taskName;

    public FetchResultsTask(MessageTransmissionStrategy messageTransmissionStrategy, String taskName) {
        super(messageTransmissionStrategy);
        this.taskName = taskName;
    }

    @Override
    public Object perform() throws Exception {
        String endpoint = RtaSessionUtil.getEndpoint(serviceType, messageTransmissionStrategy.getOwnerConnection(), true);

        setBaseProps(serviceType.getServiceURI());

        ServiceResponse serviceResponse = messageTransmissionStrategy.transmit(endpoint, getTaskName(), properties, "");

        if (serviceResponse.getResponseProperties().get(ERROR) != null) {
            throw new InvalidQueryException((String) serviceResponse.getResponseProperties().get(ERROR));
        }

        byte[] payload = (byte[]) serviceResponse.getPayload();
        //Deserialize
        return ModelJSONDeserializer.INSTANCE.deserializeQueryResults(payload);

    }

    @Override
    public String getTaskName() {
        return taskName;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }
}
