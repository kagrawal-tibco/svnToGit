package com.tibco.rta.service.persistence;

import com.tibco.rta.common.service.StartStopService;
import com.tibco.rta.common.service.session.ServerSession;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 15/4/13
 * Time: 2:51 PM
 * To change this template use File | Settings | File Templates.
 */
public interface SessionPersistenceService extends StartStopService {

    /**
     * Persist session delta. Find session delta and persist it.
     *
     * @param serverSession
     */
    void persist(ServerSession<?> serverSession) throws Exception;

    /**
     * Cleanup session delta.
     *
     * @param serverSession
     */
    void cleanup(ServerSession<?> serverSession) throws Exception;

    /**
     * Recover session data.
     *
     */
    ServerSession<?>[] recover() throws Exception;
}
