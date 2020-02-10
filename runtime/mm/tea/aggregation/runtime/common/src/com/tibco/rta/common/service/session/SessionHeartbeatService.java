package com.tibco.rta.common.service.session;

import com.tibco.rta.common.service.impl.AbstractStartStopServiceImpl;
import com.tibco.rta.service.heartbeat.HeartbeatService;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 1/4/13
 * Time: 10:27 AM
 * To change this template use File | Settings | File Templates.
 */
public class SessionHeartbeatService extends AbstractStartStopServiceImpl implements HeartbeatService {

    @Override
    public void processHeartbeat(String sessionId) throws Exception {
        ServerSessionRegistry.INSTANCE.updateHeartbeat(sessionId, System.currentTimeMillis());
    }
}
