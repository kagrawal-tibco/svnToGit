package com.tibco.rta.runtime.action.catalog;

import com.tibco.rta.RtaCommand;
import com.tibco.rta.common.service.session.ServerSessionRegistry;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 27/2/13
 * Time: 5:38 PM
 * A convenience class to be used by action handlers.
 */
public final class ActionCatalog {

    /**
     *
     * @return
     */
    public static List<String> getAllServerSessions() {
        return ServerSessionRegistry.INSTANCE.getServerSessionNames();
    }

    /**
     *
     * @param sessionName
     * @param command
     * @throws Exception
     */
    public static void sendToSession(String sessionName, RtaCommand command) throws Exception {
        ServerSessionRegistry.INSTANCE.sendToNamedSession(sessionName, command);
    }

    /**
     *
     * @param sessionNames
     * @param command
     * @throws Exception
     */
    public static void sendToSessions(List<String> sessionNames, RtaCommand command) throws Exception {
        for (String sessionName : sessionNames) {
            sendToSession(sessionName, command);
        }
    }

    /**
     * Send command to all registered sessions.
     * @param command
     * @throws Exception
     */
    public static void sendToAllSessions(RtaCommand command) throws Exception {
        ServerSessionRegistry.INSTANCE.sendToAllSessions(command);
    }
}
