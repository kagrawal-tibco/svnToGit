/*
 * Copyright (c) TIBCO Software Inc 2010.
 * All rights reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 *
 * Author: Suresh Subramani (suresh.subramani@tibco.com)
 * Date  : 27/8/2010
 */

package com.tibco.cep.runtime.service.cluster.events.notification;

import java.io.ByteArrayOutputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.agent.AgentManager;
import com.tibco.cep.runtime.service.cluster.agent.InferenceAgent;
import com.tibco.cep.runtime.service.cluster.agent.AgentManager.AgentTuple;
import com.tibco.cep.runtime.service.cluster.txn.RtcKey;
import com.tibco.cep.runtime.service.cluster.txn.RtcTransaction;
import com.tibco.cep.runtime.service.cluster.util.SingleValueLiteMap;
import com.tibco.cep.runtime.service.om.api.ControlDao;
import com.tibco.cep.runtime.service.om.api.ControlDaoType;
import com.tibco.cep.runtime.service.om.api.DaoProvider;
import com.tibco.cep.runtime.session.BEManagedThread;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Nov 16, 2007
 * Time: 11:46:14 AM
 * To change this template use File | Settings | File Templates.
 */
public class ClusterEntityMediator extends BEManagedThread implements EntityMediator, ControlDao.ChangeListener, AgentManager.AgentListener {
    protected Cluster cluster;
    //private List otherAgents = Collections.synchronizedList(new ArrayList());
    private List<AgentManager.AgentTuple> otherAgents = new CopyOnWriteArrayList<AgentManager.AgentTuple>();
    private boolean started = false;
    private boolean mediationOn = false;
    private final boolean callListenerAsync;
    private final ClusterEntityListener lsnr;
    private Object startMediatorSyncObj = new Object();
    int excludeAgentId = Integer.MIN_VALUE;
    boolean shutdownInProgress = false;
    //Filter thisFilter;
    boolean allowPublish;

    public ClusterEntityMediator(Cluster cluster, ClusterEntityListener listener, boolean allowPublish) throws Exception {
        super("Entity-Mediator-" + (listener != null ? listener.getListenerName() : "<No-Listener>"),
                cluster.getRuleServiceProvider(),
                cluster.getClusterConfig().getClusterQueueSize());

        this.setAgentThreadType();
        this.cluster = cluster;

        this.lsnr = listener;

        if (listener != null) {
            this.cluster.getAgentManager().addAgentListener(this);
            loadAgents();
            this.callListenerAsync = listener.requireAsyncInvocation();
            //this.thisFilter = lsnr.getEntityFilter();
        } else {
            this.callListenerAsync = false;
        }

        this.allowPublish = allowPublish;
    }

    private void loadAgents() throws Exception {
        Collection<AgentManager.AgentTuple> agents = cluster.getAgentManager().getAgents();
        for (AgentManager.AgentTuple agentTuple : agents) {
            otherAgents.add(agentTuple);
        }
    }

    public synchronized void activate() {
        if (!started) {
            try {
                this.start();
            } catch (IllegalStateException ex) {
            }
            this.started = true;
        }
    }

    public boolean isListenerEnabled() {
        return (lsnr != null);
    }

    public boolean isPublisherEnabled() {
        return allowPublish;
    }

    public boolean isMediationRunning() {
        return isRunning();
    }

    public void resumeMediation() {
        resumeThread();
    }

    public void shutdownMediation() {
        shutdown();
    }

    public void suspendMediation() {
        suspendThread();
    }

    public void startMediator(int excludeAgentId) throws Exception {
        this.excludeAgentId = excludeAgentId;
        synchronized (startMediatorSyncObj) {
            if (!mediationOn) {
                for(AgentManager.AgentTuple agent : otherAgents) {
                    startListener(agent);
                }
                mediationOn = true;
                //start();
            }
        }
    }

    /**
     * @param key
     * @param txn
     * @param agent
     * @throws Exception
     */
    public void publish(RtcKey key, RtcTransaction txn, InferenceAgent agent) throws Exception {
        SingleValueLiteMap liteMap = new SingleValueLiteMap();
        liteMap.put(key, serializeTxn(txn));

        agent.getTxnCache().putAll(liteMap);
    }

    protected byte[] serializeTxn(RtcTransaction txn) {
        ByteArrayOutputStream bufStream = new ByteArrayOutputStream(32 * 4);
        DataOutput buf = new DataOutputStream(bufStream);
        Iterator writeIterator = txn.writeToCacheOps(buf);
        while (writeIterator.hasNext()) {
            writeIterator.next();
        }
        return bufStream.toByteArray();
    }

    public void shutdown() {
        // Cancel the listeners
        try {
            shutdownInProgress = true;
            clearJobs();
            stopListeners();
            //interrupt();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            super.shutdown();
        }
    }

    private void startListener(AgentManager.AgentTuple agentTuple) throws Exception {
        if (agentTuple.getAgentId() == excludeAgentId) {
            return;
        }
        String cacheName = agentTuple.getTransactionCacheName();
        if (cacheName != null) {
            //ControlDao cache = cluster.getDaoProvider().getControlDao(agentTuple.getTransactionCacheName());
            ControlDao cache = fetchControlDao(agentTuple);

            if (cache != null) {
//TODO:Bala:Why is filter required? Comment out for now
//              cache.registerListener(this, thisFilter);
            	logger.log(Level.INFO, String.format("Agent %s-%s Registered listener to cache %s", agentTuple.getAgentName(), String.valueOf(agentTuple.getAgentId()), cache.getName()));
                cache.registerListener(this);
            }
        }
    }

    private ControlDao fetchControlDao(AgentTuple agentTuple) {
        DaoProvider daoProvider = cluster.getDaoProvider();

        ControlDao cache = daoProvider.createControlDao(byte[].class, byte[].class,
                ControlDaoType.AgentTxn$AgentId, agentTuple.getAgentName(), new Integer(agentTuple.getAgentId()).toString());

        return cache;
    }

    private void stopListener(AgentManager.AgentTuple agentTuple) throws Exception {
        ControlDao cache = fetchControlDao(agentTuple);
        if (cache != null) {
        	logger.log(Level.INFO, String.format("Agent %s-%s Unregistered listener to cache %s", agentTuple.getAgentName(), String.valueOf(agentTuple.getAgentId()), cache.getName()));
            cache.unregisterListener(this);
        }
    }

    private void stopListeners() throws Exception {

        for(AgentManager.AgentTuple agentTuple : otherAgents) {
            stopListener(agentTuple);
        }
    }

    public void onNew(AgentManager.AgentTuple agent) {
        if (shutdownInProgress) {
            return;
        }
        otherAgents.add(agent);
        if (mediationOn) {
            try {
                startListener(agent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void onExit(AgentManager.AgentTuple agent) {
        try {
            otherAgents.remove(agent);
            stopListener(agent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onChange(AgentManager.AgentTuple oldagent, AgentManager.AgentTuple newagent) {
    	
    	//An inference agent just created a transaction cache based on new topic registration, start the listener
    	if(mediationOn 
    			&& newagent.getTransactionCacheName() != null 
    			&& !newagent.getTransactionCacheName().equals(oldagent.getTransactionCacheName())) {
    		try {
				startListener(newagent);
			} catch (Exception e) {
				logger.log(Level.ERROR, "Error occurred while registering listener for agent %s", e, newagent.getAgentName());
			}
    	}
    }

    public void onPut(Object key, Object value) {
        if (callListenerAsync) {
            try {
                this.schedule(new TxnJob(value));
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        } else {
            lsnr.onEntity(value);
        }
    }

    public void onUpdate(Object key, Object oldValue, Object newValue) {
    }

    public void onRemove(Object key, Object value) {
    }

    class TxnJob implements Runnable {
        Object obj;

        TxnJob(Object obj) {
            this.obj = obj;
        }

        public void run() {
            lsnr.onEntity(obj);
        }
    }
}
