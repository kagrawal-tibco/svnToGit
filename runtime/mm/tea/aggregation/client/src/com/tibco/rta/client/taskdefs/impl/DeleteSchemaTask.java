package com.tibco.rta.client.taskdefs.impl;

import com.tibco.rta.client.taskdefs.AbstractClientTask;
import com.tibco.rta.client.transport.MessageTransmissionStrategy;
import com.tibco.rta.common.service.ServiceType;
import com.tibco.rta.client.util.RtaSessionUtil;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 4/2/13
 * Time: 4:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class DeleteSchemaTask extends AbstractClientTask {

    public DeleteSchemaTask(MessageTransmissionStrategy messageTransmissionStrategy) {
        super(messageTransmissionStrategy);
    }

    @Override
    public Object perform() throws Exception {
        String endpoint = RtaSessionUtil.getEndpoint(ServiceType.ADMIN, messageTransmissionStrategy.getOwnerConnection());
        setBaseProps(ServiceType.ADMIN.getServiceURI());

        messageTransmissionStrategy.transmit(endpoint, getTaskName(), properties, "");
        return null;
    }

    @Override
    public String getTaskName() {
        return "removeSchema";
    }
}
