package com.tibco.cep.loadbalancer.impl.client;

import com.tibco.cep.common.exception.LifecycleException;
import com.tibco.cep.common.exception.RecoveryException;
import com.tibco.cep.common.resource.Id;
import com.tibco.cep.common.resource.ResourceProvider;
import com.tibco.cep.impl.service.AbstractService;
import com.tibco.cep.loadbalancer.client.ClientAdmin;
import com.tibco.cep.loadbalancer.impl.CommonConstants;
import com.tibco.cep.loadbalancer.impl.client.core.DefaultActualMember;
import com.tibco.cep.loadbalancer.membership.MembershipPublisher;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.util.Helper;
import com.tibco.cep.util.annotation.LogCategory;

import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.tibco.cep.util.Helper.*;

/*
* Author: Ashwin Jayaprakash / Date: Jun 25, 2010 / Time: 3:23:49 PM
*/
@LogCategory("loadbalancer.be.client.admin")
public class DefaultClientAdmin extends AbstractService implements ClientAdmin {
    protected final ReentrantLock lock;

    protected HashMap<RuleSession, DefaultClient> rsAndClients;

    protected Logger logger;

    public DefaultClientAdmin(Id resourceId) {
        super(ClientMaster.getResourceProvider(), resourceId);

        this.lock = new ReentrantLock();
    }

    @Override
    public void start() throws LifecycleException {
        super.start();

        rsAndClients = new HashMap<RuleSession, DefaultClient>();
        logger = $logger(resourceProvider, getClass());

        logger.log(Level.INFO, "Started client admin [" + resourceId + "]");
    }

    public DefaultClient getClientFor(RuleSession ruleSession) throws LifecycleException {
        DefaultClient client = null;

        lock.lock();
        try {
            client = rsAndClients.get(ruleSession);

            if (client == null) {
                client = new DefaultClient();

                Id id = Helper.$id(resourceProvider, ClientConstants.NAME_CLIENT, "rulesession", ruleSession);
                client.setResourceId(id);
                client.setResourceProvider(resourceProvider);

                MembershipPublisher membershipPublisher = $instantiate(MembershipPublisher.class, CommonConstants.DEF_MEMBERSHIP_PUBLISHER);

                id = $id(resourceProvider, ClientConstants.NAME_MEMBERSHIP_PUBLISHER, "rulesession", ruleSession);
                membershipPublisher.setId(id);

                membershipPublisher.setResourceProvider(resourceProvider);

                //-------------

                id = $id(resourceProvider, ClientConstants.NAME_MEMBER, "service", this, "rulesession", ruleSession);

                DefaultActualMember member = new DefaultActualMember(id);
                member.setResourceProvider(resourceProvider);

                //-------------

                client.setMember(member);
                client.setMembershipPublisher(membershipPublisher);

                client.start();

                //------------------

                rsAndClients.put(ruleSession, client);
            }
        }
        finally {
            lock.unlock();
        }

        return client;
    }

    @Override
    public void stop() throws LifecycleException {
        logger.log(Level.INFO, "Stopping client admin [" + resourceId + "]");

        lock.lock();
        try {
            for (DefaultClient client : rsAndClients.values()) {
                try {
                    client.stop();
                }
                catch (Exception e) {
                    logger.log(Level.SEVERE, "Error occurred while stopping client [" + client.getResourceId() + "]",
                            e);
                }
            }

            rsAndClients.clear();
        }
        finally {
            lock.unlock();
        }

        logger.log(Level.INFO, "Stopped client admin [" + resourceId + "]");

        super.stop();
    }

    @Override
    public DefaultClientAdmin recover(ResourceProvider resourceProvider, Object... params) throws RecoveryException {
        super.recover(resourceProvider, params);

        return this;
    }
}