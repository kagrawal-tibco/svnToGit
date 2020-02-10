package com.tibco.cep.loadbalancer.impl.server;

import com.tibco.cep.common.exception.LifecycleException;
import com.tibco.cep.common.exception.RecoveryException;
import com.tibco.cep.common.resource.Id;
import com.tibco.cep.common.resource.ResourceProvider;
import com.tibco.cep.impl.service.AbstractService;
import com.tibco.cep.loadbalancer.impl.CommonConstants;
import com.tibco.cep.loadbalancer.impl.server.core.DefaultKernel;
import com.tibco.cep.loadbalancer.membership.MembershipChangeProvider;
import com.tibco.cep.loadbalancer.server.BeServerAdmin;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.util.annotation.LogCategory;

import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.tibco.cep.util.Helper.*;

/*
* Author: Ashwin Jayaprakash / Date: Jun 3, 2010 / Time: 11:49:23 AM
*/

@LogCategory("loadbalancer.be.server.admin")
public class DefaultBeServerAdmin extends AbstractService implements BeServerAdmin {
    protected final ReentrantLock lock;

    protected HashMap<RuleSession, DefaultServer> rsAndServers;

    protected Logger logger;

    public DefaultBeServerAdmin(Id resourceId) {
        super(ServerMaster.getResourceProvider(), resourceId);

        this.lock = new ReentrantLock();
    }

    @Override
    public void start() throws LifecycleException {
        super.start();

        rsAndServers = new HashMap<RuleSession, DefaultServer>();
        logger = $logger(resourceProvider, getClass());

        logger.log(Level.INFO, "Started server admin [" + resourceId + "]");
    }

    public DefaultServer getServerFor(RuleSession ruleSession) throws LifecycleException {
        DefaultServer server = null;

        lock.lock();
        try {
            server = rsAndServers.get(ruleSession);

            if (server == null) {
                server = new DefaultServer();

                Id id = $id(resourceProvider, ServerConstants.NAME_SERVER, "rulesession", ruleSession);
                server.setResourceId(id);
                server.setResourceProvider(resourceProvider);

                id = $id(resourceProvider, ServerConstants.NAME_KERNEL, "rulesession", ruleSession);
                DefaultKernel kernel = new DefaultKernel();
                kernel.setId(id);
                kernel.setResourceProvider(resourceProvider);

                server.setKernel(kernel);

                MembershipChangeProvider changeProvider =
                        $instantiate(MembershipChangeProvider.class, CommonConstants.DEF_MEMBERSHIP_PROVIDER);

                id = $id(resourceProvider, ServerConstants.NAME_MEMBERSHIP_CHANGE_PROVIDER, "rulesession", ruleSession);
                changeProvider.setId(id);

                server.setMembershipChangeProvider(changeProvider);

                server.start();

                //------------------

                rsAndServers.put(ruleSession, server);
            }
        }
        finally {
            lock.unlock();
        }

        return server;
    }

    @Override
    public void stop() throws LifecycleException {
        logger.log(Level.INFO, "Stopping server admin [" + resourceId + "]");

        lock.lock();
        try {
            for (DefaultServer server : rsAndServers.values()) {
                try {
                    server.stop();
                }
                catch (Exception e) {
                    logger.log(Level.SEVERE, "Error occurred while stopping server [" + server.getResourceId() + "]",
                            e);
                }
            }

            rsAndServers.clear();
        }
        finally {
            lock.unlock();
        }

        logger.log(Level.INFO, "Stopped server admin [" + resourceId + "]");

        super.stop();
    }

    @Override
    public DefaultBeServerAdmin recover(ResourceProvider resourceProvider, Object... params) throws RecoveryException {
        super.recover(resourceProvider, params);

        return this;
    }
}
