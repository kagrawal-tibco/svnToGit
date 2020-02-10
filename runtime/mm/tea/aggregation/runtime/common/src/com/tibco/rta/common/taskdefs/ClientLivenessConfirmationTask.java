package com.tibco.rta.common.taskdefs;

import com.tibco.rta.common.service.session.ServerSession;
import com.tibco.rta.util.ServiceConstants;

import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 13/6/13
 * Time: 3:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class ClientLivenessConfirmationTask implements Task {

    /**
     * The session to check liveness for.
     */
    private ServerSession<?> serverSession;

    public ClientLivenessConfirmationTask(ServerSession<?> serverSession) {
        this.serverSession = serverSession;
    }

    @Override
    public Object perform() throws Throwable {
        Properties properties = new Properties();
        properties.setProperty(ServiceConstants.SESSION_ID, serverSession.getSessionId());
        properties.setProperty(ServiceConstants.REQUEST_CLIENT_CONFIRM, "true");
        return serverSession.requestClientConfirmation(properties);
    }

    @Override
    public String getTaskName() {
        return "ClientLivenessTask";  //To change body of implemented methods use File | Settings | File Templates.
    }
}
