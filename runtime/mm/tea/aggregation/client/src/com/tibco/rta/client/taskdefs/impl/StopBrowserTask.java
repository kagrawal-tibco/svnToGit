package com.tibco.rta.client.taskdefs.impl;

import com.tibco.rta.client.taskdefs.AbstractClientTask;
import com.tibco.rta.client.transport.MessageTransmissionStrategy;
import com.tibco.rta.common.service.ServiceType;
import com.tibco.rta.client.util.RtaSessionUtil;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 20/5/13
 * Time: 3:00 PM
 * Stop browser for snapshot based queries.
 */
public class StopBrowserTask extends AbstractClientTask {

    private ServiceType serviceType;

    private String taskName;

    public StopBrowserTask(MessageTransmissionStrategy messageTransmissionStrategy, String taskName) {
        super(messageTransmissionStrategy);
        this.taskName = taskName;
    }

    @Override
    public Object perform() throws Exception {
        String endpoint = RtaSessionUtil.getEndpoint(serviceType, messageTransmissionStrategy.getOwnerConnection(), true);
        setBaseProps(ServiceType.QUERY.getServiceURI());

        messageTransmissionStrategy.transmit(endpoint, getTaskName(), properties, "");
        return null;
    }

    @Override
    public String getTaskName() {
        return taskName;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }
}
