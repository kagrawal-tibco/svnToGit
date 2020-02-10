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
 * Date: 11/3/14
 * Time: 2:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class FetchServerConfigurationTask extends AbstractClientTask {

    public FetchServerConfigurationTask(MessageTransmissionStrategy messageTransmissionStrategy) {
        super(messageTransmissionStrategy);
    }

    @Override
    public Object perform() throws Throwable {
        String endpoint = RtaSessionUtil.getEndpoint(ServiceType.ENGINE, messageTransmissionStrategy.getOwnerConnection(), true);

        setBaseProps(ServiceType.ENGINE.getServiceURI());

        ServiceResponse serviceResponse = messageTransmissionStrategy.transmit(endpoint, getTaskName(), properties, "");

        if (serviceResponse.getResponseProperties().get(ERROR) != null) {
            throw new InvalidQueryException((String) serviceResponse.getResponseProperties().get(ERROR));
        }

        byte[] payload = (byte[]) serviceResponse.getPayload();
        //Deserialize
        return ModelJSONDeserializer.INSTANCE.deserializeServerConfig(payload);
    }

    @Override
    public String getTaskName() {
        return "getRuntimeConfiguration";
    }
}
