package com.tibco.rta.client.taskdefs.impl;

import com.tibco.rta.client.taskdefs.AbstractClientTask;
import com.tibco.rta.client.transport.MessageTransmissionStrategy;
import com.tibco.rta.common.service.ServiceType;
import com.tibco.rta.impl.DefaultRtaSession;
import com.tibco.rta.client.util.RtaSessionUtil;

import static com.tibco.rta.util.ServiceConstants.SESSION_ID;
import static com.tibco.rta.util.ServiceConstants.SESSION_NAME;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 14/6/13
 * Time: 7:30 AM
 * To change this template use File | Settings | File Templates.
 */
public class HeartbeatTask extends AbstractClientTask {

    private DefaultRtaSession session;

    private String mainEndpoint;

    public HeartbeatTask(MessageTransmissionStrategy messageTransmissionStrategy) {
        super(messageTransmissionStrategy);
    }

    @Override
    public Object perform() throws Exception {
        setBaseProps(ServiceType.HEARTBEAT.getServiceURI());
        addProperty(SESSION_ID, session.getClientId());
        addProperty(SESSION_NAME, session.getName());

        String mainEndpoint = (this.mainEndpoint != null) ? this.mainEndpoint : RtaSessionUtil.getEndpoint(ServiceType.HEARTBEAT, session.getOwnerConnection());
        messageTransmissionStrategy.transmit(mainEndpoint, getTaskName(), properties, "");
//
//        String auxEndpoint = RtaSessionUtil.getEndpoint(ServiceType.QUERY, session.getOwnerConnection(), true);
//        messageTransmissionStrategy.transmit(auxEndpoint, getTaskName(), properties, "");

        return null;
    }

    public void setSession(DefaultRtaSession session) {
        this.session = session;
    }

    public void setEndpoint(String endpoint) {
        this.mainEndpoint = endpoint;
    }

    @Override
    public String getTaskName() {
        return "processHeartbeat";  //To change body of implemented methods use File | Settings | File Templates.
    }
}
