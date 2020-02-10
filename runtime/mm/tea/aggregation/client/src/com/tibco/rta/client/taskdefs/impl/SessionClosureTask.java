package com.tibco.rta.client.taskdefs.impl;

import com.tibco.rta.client.taskdefs.AbstractClientTask;
import com.tibco.rta.client.transport.MessageTransmissionStrategy;
import com.tibco.rta.common.service.ServiceType;
import com.tibco.rta.client.util.RtaSessionUtil;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 9/7/13
 * Time: 12:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class SessionClosureTask extends AbstractClientTask {

    public SessionClosureTask(MessageTransmissionStrategy messageTransmissionStrategy) {
        super(messageTransmissionStrategy);
    }

    @Override
    public Object perform() throws Throwable {
        String endpoint = RtaSessionUtil.getEndpoint(ServiceType.SESSION, messageTransmissionStrategy.getOwnerConnection(), true);
        setBaseProps(ServiceType.SESSION.getServiceURI());

        messageTransmissionStrategy.transmit(endpoint, getTaskName(), properties, "");
        return null;
    }

    @Override
    public String getTaskName() {
        return "closeSession";
    }
}
