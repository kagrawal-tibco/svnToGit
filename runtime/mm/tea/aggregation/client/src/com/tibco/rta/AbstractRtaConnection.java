package com.tibco.rta;

import com.tibco.rta.annotations.GuardedBy;
import com.tibco.rta.impl.DefaultRtaSession;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 21/3/13
 * Time: 9:59 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractRtaConnection implements RtaConnectionEx {

    protected static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_CLIENT.getCategory());

    protected String connectionUrl;

    @GuardedBy("mainLock")
    protected String clientId;

    protected String scheme;

    protected String host;

    protected int port;

    protected String username;

    protected String password;

    /**
     * Sessions created from this connection.
     */
    protected Set<DefaultRtaSession> sessions = new HashSet<DefaultRtaSession>();

    private final ReentrantLock mainLock = new ReentrantLock();

    @Override
    public String getScheme() {
        return scheme;
    }

    @Override
    public String getHost() {
        return host;
    }

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public String getClientId() {
        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();

        try {
            return clientId;
        } finally {
            if (mainLock.isHeldByCurrentThread()) {
                mainLock.unlock();
            }
        }
    }

    @Override
    public void setClientId(String clientId) {
        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();

        try {
            this.clientId = clientId;
        } finally {
            if (mainLock.isHeldByCurrentThread()) {
                mainLock.unlock();
            }
        }
    }
}
