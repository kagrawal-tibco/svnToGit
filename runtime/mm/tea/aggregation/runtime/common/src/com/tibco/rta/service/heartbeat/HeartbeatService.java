package com.tibco.rta.service.heartbeat;

import com.tibco.rta.common.service.StartStopService;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 29/3/13
 * Time: 5:05 PM
 * To change this template use File | Settings | File Templates.
 */
public interface HeartbeatService extends StartStopService {

    /**
     * Process heartbeat received from client.
     * @param sessionId The client session id.
     * @throws Exception
     */
    public void processHeartbeat(String sessionId) throws Exception;
}
