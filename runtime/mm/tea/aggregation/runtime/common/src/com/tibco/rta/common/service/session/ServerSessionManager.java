package com.tibco.rta.common.service.session;

import com.tibco.rta.common.service.session.ServerSession;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 13/2/13
 * Time: 10:46 AM
 * To change this template use File | Settings | File Templates.
 */
public class ServerSessionManager {

    public static final ThreadLocal<ServerSession> serverSessions = new ThreadLocal<ServerSession>();

    public static ServerSession getCurrentServerSession() {
        return serverSessions.get();
    }

    public static void resetServerSession() {
        serverSessions.remove();
    }
}
