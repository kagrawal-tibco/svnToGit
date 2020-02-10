/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 *  All Rights Reserved.
 *
 *  This software is confidential and proprietary information of
 *  TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.cluster;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import com.tibco.cep.common.resource.Id;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.service.cluster.CacheAgent;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.agent.AgentBuilder;
import com.tibco.cep.runtime.service.cluster.agent.AgentManager;
import com.tibco.cep.runtime.service.cluster.agent.AgentRuntimePolicyManager;
import com.tibco.cep.runtime.service.cluster.agent.DefaultAgentTuple;
import com.tibco.cep.runtime.service.cluster.gmp.GroupMember;
import com.tibco.cep.runtime.service.cluster.gmp.GroupMemberServiceListener;
import com.tibco.cep.runtime.service.cluster.gmp.GroupMembershipService;
import com.tibco.cep.runtime.service.cluster.om.RuleFunctionService;
import com.tibco.cep.runtime.service.cluster.system.ControlKey;
import com.tibco.cep.runtime.service.cluster.system.DefaultControlKey;
import com.tibco.cep.runtime.service.cluster.util.WorkManager;
import com.tibco.cep.runtime.service.cluster.util.WorkManagerFactory;
import com.tibco.cep.runtime.service.om.api.ControlDao;
import com.tibco.cep.runtime.service.om.api.ControlDaoType;
import com.tibco.cep.runtime.service.om.api.MapChangeListener;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.util.SystemProperty;

/*
* Author: Suresh Subramani / Date: Nov 4, 2010 / Time: 12:01:28 PM
*/

/**
 * Manages the Agent
 */
public class DefaultAgentManager implements AgentManager, MapChangeListener<String, AgentManager.AgentTuple>,
        GroupMemberServiceListener {

    private RuleServiceProvider rsp;

    private CacheAgent[] localAgents;

    private Cluster cluster;

    private ControlDao<String, AgentTuple> agentTable;

    private ConcurrentHashMap<AgentListener, AgentListener> listeners; //HashSet of listener

    protected AgentRuntimePolicyManager policyManager;

    protected WorkManager mgr;

    protected Logger logger;

    protected boolean parallelStartup;
    
    public DefaultAgentManager() {
    	
    }

    @Override
    public void init(String clusterName, RuleServiceProvider rsp, Cluster cluster) throws Exception {
    	 this.rsp = rsp;
         this.logger = cluster.getRuleServiceProvider().getLogger(DefaultAgentManager.class);
         this.localAgents = AgentBuilder.getInstance().build(rsp, clusterName);
         listeners = new ConcurrentHashMap<AgentListener, AgentListener>();
        this.cluster = cluster;
        
//        ASClusterProvider provider = (ASClusterProvider) cluster.getClusterProvider();

        agentTable = cluster.getClusterProvider().
                createControlDao(String.class, AgentTuple.class, ControlDaoType.AgentTable, cluster);

        for (CacheAgent ca : localAgents) {
            ca.init(this);
        }

        Properties properties = cluster.getRuleServiceProvider().getProperties();
        parallelStartup = Boolean.parseBoolean(properties.getProperty(SystemProperty.ENGINE_STARTUP.getPropertyName(), Boolean.FALSE.toString()));
    }

    public void start() throws Exception {
        logger.log(Level.INFO, "Starting [%s]", getClass().getSimpleName());

        mgr = WorkManagerFactory
                .createUnpausable(cluster.getClusterName(), null, null, "AgentMgr", 1, cluster.getRuleServiceProvider());
        mgr.start();

        //----------------

        policyManager = new AgentRuntimePolicyManager(cluster, this);
        addAgentListener(policyManager);

        agentTable.start();
        agentTable.registerListener(this);

        //----------------

        for (CacheAgent ca : localAgents) {
            ca.start(CacheAgent.AgentState.REGISTERED);
        }

        policyManager.syncGlobalView(); // GMP Lock from here

        policyManager.tryActivateLocalAgents(); // GMP Lock from here

        //----------------

        cluster.getGroupMembershipService().setFirstGroupMemberServiceListener(this);

        logger.log(Level.INFO, "Started [%s]", getClass().getSimpleName());
    }

    /**
     * Wait for {@link #start()}
     *
     * @return
     */
    @Override
    public ControlDao<String, AgentTuple> getAgentTable() {
        return agentTable;
    }

    /**
     * Wait for {@link #start()}
     *
     * @return
     */
    @Override
    public CacheAgent[] getLocalAgents() {
        return localAgents;
    }

    @Override
    public void registerAgent(CacheAgent agent) {
        DefaultAgentTuple tuple = new DefaultAgentTuple(agent);
        agentTable.put(""+tuple.getAgentId(), tuple);
    }

    @Override
    public AgentTuple getAgentAsTuple(int agentId) {
        return agentTable.get(""+agentId);
    }

    @Override
    public void deregisterAgent(CacheAgent cacheAgent) {
        agentTable.remove(""+cacheAgent.getAgentId());
    }

    @Override
    public int getNextAgentId() throws Exception {
        GroupMembershipServiceImpl gmp = (GroupMembershipServiceImpl) cluster.getGroupMembershipService();

        if (!parallelStartup) {
            gmp.lock();
        }
        try {
            ControlKey lastIdKey = DefaultControlKey.AgentManagerLastIdKey;
            ControlDao masterDao = gmp.getMasterDao();

            Integer lastId = (Integer) masterDao.get(lastIdKey);

            lastId = (lastId == null) ? 0 : lastId + 1;
            masterDao.put(lastIdKey, lastId);

            return lastId;
        }
        finally {
            if (!parallelStartup) {
                gmp.unlock();
            }
        }
    }

    @Override
    public void updateAgent(CacheAgent agent) {
        DefaultAgentTuple tuple = new DefaultAgentTuple(agent);
        agentTable.put(""+tuple.getAgentId(), tuple);
    }

    public Cluster getCluster() {
        return cluster;
    }

    public RuleServiceProvider getRuleServiceProvider() {
        return this.rsp;
    }


    @Override
    public Collection<AgentTuple> getAgents() {
        return agentTable.values();
    }

    @Override
    public void addAgentListener(AgentListener listener) {
        listeners.putIfAbsent(listener, listener);
    }

    @Override
    public void removeAgentListener(AgentListener listener) {
        listeners.remove(listener);
    }

    @Override
    public final synchronized void onPut(String key, final AgentTuple value, boolean isLocal) {
        for (; ;) {
            try {
                mgr.submitJob(new Runnable() {
                    @Override
                    public void run() {
                        for (AgentListener listener : listeners.values()) {
                            listener.onNew(value);
                        }
                    }
                });

                break;
            }
            catch (InterruptedException e) {
            }
        }
    }

    @Override
    public final synchronized void onUpdate(String key, final AgentTuple oldValue, final AgentTuple newValue) {
        for (; ;) {
            try {
                mgr.submitJob(new Runnable() {
                    @Override
                    public void run() {
                        for (AgentListener listener : listeners.values()) {
                            listener.onChange(oldValue, newValue);
                        }
                    }
                });

                break;
            }
            catch (InterruptedException e) {
            }
        }
    }

    @Override
    public final synchronized void onRemove(String key, final AgentTuple value, boolean isLocal) {
        for (; ;) {
            try {
                mgr.submitJob(new Runnable() {
                    @Override
                    public void run() {
                        for (AgentListener listener : listeners.values()) {
                            listener.onExit(value);
                        }
                    }
                });

                break;
            }
            catch (InterruptedException e) {
            }
        }
    }

    //----------------

    @Override
    public void memberJoined(GroupMember member) {
    }

    /**
     * This is a special listener - the first. Perform all work synchronously.
     *
     * @param member
     */
    @Override
    public void memberLeft(final GroupMember member) {
        Id leftMemberId = member.getMemberId();
        LinkedList<AgentTuple> agentsOnThatMember = new LinkedList<AgentTuple>();

        GroupMembershipService gmp = cluster.getGroupMembershipService();

        boolean success = false;
        try {
            if (!parallelStartup) {
                gmp.lock();
            }
            try {
                for (AgentTuple agentTuple : agentTable.values()) {
                    Id memberId = agentTuple.getMemberId();

                    if (memberId.equals(leftMemberId)) {
                        agentsOnThatMember.add(agentTuple);
                    }
                }

                logger.log(Level.INFO, "Processing member [%s] left event. It had [%d] agents",
                        member.getMemberId(), agentsOnThatMember.size());

                for (AgentTuple agentTuple : agentsOnThatMember) {
                    agentTable.remove(""+agentTuple.getAgentId());
                }
            }
            finally {
                if (!parallelStartup) {
                    gmp.unlock();
                }
                success = true;
            }
        }
        finally {
            if (!success) {
                policyManager.purgeAll();
            }
        }
    }

    @Override
    public void memberStatusChanged(GroupMember member, Status oldStatus, Status newStatus) {
    }

    @Override
    public void suspendAgents() throws Exception {
        for (int i = 0; i < localAgents.length; i++) {
            try {
                boolean stateChanged = localAgents[i].suspend();
                if (stateChanged) {
                    logger.log(Level.WARN, "Agent " + localAgents[i].getAgentName() + " suspended operations.");
                }
            }
            catch (Exception e) {
                logger.log(Level.WARN, "Cannot suspend agent " + localAgents[i].getAgentName());
                throw e;
            }
        }
    }

    @Override
    public void resumeAgents() throws Exception {
        for (int i = 0; i < localAgents.length; i++) {
            try {
                boolean stateChanged = localAgents[i].resume();
                if (stateChanged) {
                    logger.log(Level.WARN, "Agent " + localAgents[i].getAgentName() + " resumed operations.");
                }
            }
            catch (Exception e) {
                logger.log(Level.WARN, "Cannot resume agent " + localAgents[i].getAgentName());
                throw e;
            }
        }
    }

    @Override
    public RuleFunctionService getRuleFunctionService() {
        for (int i = 0; i < localAgents.length; i++) {
            if (localAgents[i] instanceof RuleFunctionService) {
                return (RuleFunctionService) localAgents[i];
            }
        }
        return null;
    }
}
