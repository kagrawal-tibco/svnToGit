package com.tibco.rta.common.service.session;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 10/6/13
 * Time: 3:43 AM
 * To change this template use File | Settings | File Templates.
 */
public interface ServerSessionMBean {

    /**
     * Get session id of this server session.
     * @return session id
     */
    public String getSessionId();

    /**
     * Get name of this session. May be null.
     * @return name
     */
    public String getSessionName();

    public enum SessionStatus {
        ACTIVE, INACTIVE
    }

    /**
     * Return current status of the session.
     * @see SessionStatus
     * @return current status
     */
    public String getStatus();

    /**
     * Return timestamp of last client heartbeat received.
     * @return date for last client heartbeat.
     */
    public Date getLastHeartbeat();

    /**
     * Get the number of milliseconds for server to wait for client heartbeat before timing out the client session.
     * @return number of milliseconds
     */
    public long getHeartbeatAbsenceThresholdInterval();
}
