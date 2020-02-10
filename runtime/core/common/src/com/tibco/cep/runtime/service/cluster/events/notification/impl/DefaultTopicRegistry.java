/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.cluster.events.notification.impl;


import java.util.Arrays;
import java.util.Collection;

import com.tibco.cep.runtime.service.cluster.CacheAgent;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.agent.InferenceAgent;
import com.tibco.cep.runtime.service.cluster.events.notification.TopicRegistry;
import com.tibco.cep.runtime.service.cluster.system.MetadataCache;
import com.tibco.cep.runtime.service.om.api.ControlDao;
import com.tibco.cep.runtime.service.om.api.ControlDaoType;
import com.tibco.cep.util.BitMapHelper;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Feb 26, 2009
 * Time: 11:44:57 AM
 * To change this template use File | Settings | File Templates.
 */
public class DefaultTopicRegistry implements ControlDao.ChangeListener, TopicRegistry {
    Cluster cluster;
    ControlDao<Integer, int[][]> topicRegistrations;
    boolean[] changeTopics;
    boolean[] deleteTopics;
    BitMapHelper topicEncoderDecoder;


    /**
     * @param cluster
     * @throws Exception
     */

    public DefaultTopicRegistry() {
        topicEncoderDecoder = new BitMapHelper(MetadataCache.BE_TYPE_START);
    }

    public void init(Cluster cluster) throws Exception {

        this.cluster = cluster;


        //REPLICATED_CACHE_UNLIMITED
        topicRegistrations = cluster.getDaoProvider().createControlDao(Integer.class, int[][].class, ControlDaoType.Topics);


    }

    public void start() throws Exception {
    	changeTopics = new boolean[cluster.getMetadataCache().size()];
        deleteTopics = new boolean[cluster.getMetadataCache().size()];
        topicRegistrations.start();
        topicRegistrations.registerListener(this);
        //topicRegistrations.addMapListener(this);
        loadRegistrations();
    }

    protected void loadRegistrations() throws Exception {
        java.util.Iterator allRegistrations = topicRegistrations.keySet().iterator();
        while (allRegistrations.hasNext()) {
            Object agentId = allRegistrations.next();
            int[][] types = (int[][]) topicRegistrations.get(agentId);
            loadRegistration(types);
        }
    }

    protected void loadRegistration(int[][] types) {
    	//loadRegistration can be triggered by remote agents
    	//they may have already hotdeployed and therefore have more typeids than are known locally
    	int maxTypeId = cluster.getMetadataCache().getRegisteredTypes().length;
    	for (int check : types[0]) {
    		check = check - MetadataCache.BE_TYPE_START;
    		if(maxTypeId < check) maxTypeId = check;
    	}
    	for (int check : types[1]) {
    		check = check - MetadataCache.BE_TYPE_START;
    		if(maxTypeId < check) maxTypeId = check;
    	}
    	
    	checkArrays(maxTypeId);
    	
        for (int i = 0; i < types[0].length; i++) {
            changeTopics[types[0][i] - MetadataCache.BE_TYPE_START] = true;
        }

        for (int i = 0; i < types[1].length; i++) {
            deleteTopics[types[1][i] - MetadataCache.BE_TYPE_START] = true;
        }
        
        //Delayed AgentTxn Cache Initialization, If topics are registered for c+m entities, initialize AgentTxn Cache for an agent
    	CacheAgent[] cacheAgents = cluster.getAgentManager().getLocalAgents();
    	for(CacheAgent cacheAgent : cacheAgents) {
    		if(cacheAgent instanceof InferenceAgent && ((InferenceAgent)cacheAgent).getTransactionCacheName() == null) {
    			InferenceAgent agent = ((InferenceAgent)cacheAgent);
    			agent.initTxnCache();
    		}
    	}
    }

    public void register(CacheAgent agent, int[] topicsChange, int[] topicsDelete) {
        int[][] tmp = new int[2][];
        tmp[0] = topicsChange;
        tmp[1] = topicsDelete;
        topicRegistrations.put(agent.getAgentId(), tmp);
    }

    public void register(CacheAgent agent, Collection<Integer> topicsChange, Collection<Integer> topicsDelete) {
        int[][] tmp = new int[2][];

        tmp[0] = new int[topicsChange.size()];
        int ii = 0;
        for(int typeId : topicsChange) {
            tmp[0][ii++] = typeId;
        }

        tmp[1]= new int[topicsDelete.size()];
        ii = 0;
        for (int typeId : topicsDelete) {
            tmp[1][ii++] = typeId;
        }
        
        topicRegistrations.put(agent.getAgentId(), tmp);
    }

    public boolean isRegisteredForChange(int typeId) {
        return changeTopics[typeId - MetadataCache.BE_TYPE_START];
    }

    public boolean isRegisteredForDelete(int typeId) {
        return deleteTopics[typeId - MetadataCache.BE_TYPE_START];
    }

//    public void entryDeleted(MapEvent event) {
//        //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    public void entryInserted(MapEvent event) {
//        int[][] topics = (int[][]) event.getNewValue();
//        loadRegistration(topics);
//    }
//
//    public void entryUpdated(MapEvent event) {
//        int[][] topics = (int[][]) event.getNewValue();
//        loadRegistration(topics);
//    }

    protected void shutdown() {
        //topicRegistrations.release();
        topicRegistrations.discard();
    }

    //The above 



    public void onPut(Object key, Object value) {
        int[][] topics = (int[][]) value;
        loadRegistration(topics);
    }


    public void onUpdate(Object key, Object oldValue, Object newValue) {
        int[][] topics = (int[][]) newValue;
        loadRegistration(topics);

    }


    public void onRemove(Object key, Object value) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public int[] encodeTopic(Collection<Integer> typeIds) {
        return topicEncoderDecoder.encode2BitMapVector(typeIds);
    }
    
    protected void checkArrays(int numTypes) {
    	if(changeTopics.length < numTypes) {
    		changeTopics = Arrays.copyOf(changeTopics, numTypes);
    	}
    	if(deleteTopics.length < numTypes) {
    		deleteTopics = Arrays.copyOf(deleteTopics, numTypes);
    	}
    }
    
    public int numRegistered() {
    	int registeredEntityCount = 0;
    	for(boolean registered : changeTopics) {
    		if(registered) registeredEntityCount++;
    	}
    	for(boolean registered : deleteTopics) {
    		if(registered) registeredEntityCount++;
    	}
    	return registeredEntityCount;
    }
}


