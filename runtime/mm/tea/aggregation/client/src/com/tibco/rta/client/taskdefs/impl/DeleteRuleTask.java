package com.tibco.rta.client.taskdefs.impl;

import com.tibco.rta.client.ServiceResponse;
import com.tibco.rta.client.taskdefs.AbstractClientTask;
import com.tibco.rta.client.transport.MessageTransmissionStrategy;
import com.tibco.rta.common.service.ServiceType;
import com.tibco.rta.client.util.RtaSessionUtil;

import static com.tibco.rta.util.ServiceConstants.ERROR;

public class DeleteRuleTask extends AbstractClientTask {

    public DeleteRuleTask(MessageTransmissionStrategy messageTransmissionStrategy) {
        super(messageTransmissionStrategy);
    }

    @Override
    public Object perform() throws Exception {
        String endpoint = RtaSessionUtil.getEndpoint(ServiceType.RULE, messageTransmissionStrategy.getOwnerConnection());
        setBaseProps(ServiceType.RULE.getServiceURI());

        ServiceResponse<?> serviceResponse = messageTransmissionStrategy.transmit(endpoint, getTaskName(), properties, "");

        if (serviceResponse.getResponseProperties().get(ERROR) != null) {
            throw new Exception((String) serviceResponse.getResponseProperties().get(ERROR));
        }
        return null;
    }

    @Override
    public String getTaskName() {
        return "deleteRule";
    }
}
