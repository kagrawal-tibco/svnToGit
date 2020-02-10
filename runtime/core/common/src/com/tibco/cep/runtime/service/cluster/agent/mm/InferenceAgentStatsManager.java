/*
 * Copyright (c) TIBCO Software Inc 2010.
 * All rights reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 *
 * Author: Suresh Subramani (suresh.subramani@tibco.com)
 * Date  : 11/8/2010
 */

package com.tibco.cep.runtime.service.cluster.agent.mm;

import java.util.Arrays;
import java.util.Collection;

import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.runtime.service.cluster.agent.InferenceAgent;
import com.tibco.cep.runtime.service.cluster.system.MetadataCache;
import com.tibco.cep.runtime.session.BEManagedThread;
import com.tibco.cep.runtime.session.impl.HotDeployListener;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: May 18, 2008
 * Time: 10:45:06 AM
 * To change this template use File | Settings | File Templates.
 */
public class InferenceAgentStatsManager implements HotDeployListener {
    InferenceAgent cacheAgent;
    InferenceAgentEntityStats[] stats;


    public InferenceAgentStatsManager(InferenceAgent cacheAgent) {
        this.cacheAgent = cacheAgent;

    }

    public void start() {

        Class[] entities = cacheAgent.getCluster().getMetadataCache().getRegisteredTypes();
        stats = new InferenceAgentEntityStats[entities.length];
        for (int i = 0; i < entities.length; i++) {
        	stats[i] = newStats(entities[i]);
        }
    }
    
    private InferenceAgentEntityStats newStats(Class cls) {
    	if (cls != null) {
            return new InferenceAgentEntityStats(cls,
                    "com.tibco.be:type=Agent,agentId=" + cacheAgent.getAgentId()
                            + ",subType=Entity,entityId=" + cls.getName(),
                    cacheAgent.getEntityConfig(cls).getCacheMode());
        } else {
        	return null;
        }
    }

    @Override
    public void entitiesAdded() {
    	Class[] entities = cacheAgent.getCluster().getMetadataCache().getRegisteredTypes();
    	InferenceAgentEntityStats[] newStats = Arrays.copyOf(stats, entities.length);
    	for(int ii = stats.length; ii < newStats.length; ii++) {
    		newStats[ii] = newStats(entities[ii]);
    	}
    	stats = newStats;
    }
    
    @Override
    public void entitiesChanged(Collection<Class<Entity>> changedClasses) {}
    
    /**
     * @param typeId
     */
    public void incrementNumRecovered(int typeId) {
        if (typeId < MetadataCache.BE_TYPE_START)
            return;
        stats[typeId - MetadataCache.BE_TYPE_START].incrementNumRecovered();
    }

    public void incrementNumAsserted(int typeId) {
        if (typeId < MetadataCache.BE_TYPE_START)
            return;
        if (BEManagedThread.isAgentThread())
            stats[typeId - MetadataCache.BE_TYPE_START].incrementNumAssertedFromAgents();
        else
            stats[typeId - MetadataCache.BE_TYPE_START].incrementNumAssertedFromChannel();
    }

    public void incrementPreRTC(int typeId, long time) {
        if (typeId < MetadataCache.BE_TYPE_START)
            return;
        stats[typeId - MetadataCache.BE_TYPE_START].incrementPreRTC(time);
    }

    public void incrementInRTC(int typeId, long time) {
        if (typeId < MetadataCache.BE_TYPE_START)
            return;
        stats[typeId - MetadataCache.BE_TYPE_START].incrementInRTC(time);
    }

    public void incrementPostRTC(int typeId, long time) {
        if (typeId < MetadataCache.BE_TYPE_START)
            return;
        stats[typeId - MetadataCache.BE_TYPE_START].incrementPostRTC(time);
    }

    public void incrementNumHitsInL1Cache(int typeId) {
        if (typeId < MetadataCache.BE_TYPE_START)
            return;
        stats[typeId - MetadataCache.BE_TYPE_START].incrementNumHitsInL1Cache();
    }


    public void incrementNumMissesInL1Cache(int typeId) {
        if (typeId < MetadataCache.BE_TYPE_START)
            return;
        stats[typeId - MetadataCache.BE_TYPE_START].incrementNumMissesInL1Cache();
    }

    public void incrementNumModified(int typeId) {
        if (typeId < MetadataCache.BE_TYPE_START)
            return;
        if (BEManagedThread.isAgentThread())
            stats[typeId - MetadataCache.BE_TYPE_START].incrementNumModifiedFromAgents();
        else
            stats[typeId - MetadataCache.BE_TYPE_START].incrementNumModifiedFromChannel();
    }

    public void incrementNumRetracted(int typeId) {
        if (typeId < MetadataCache.BE_TYPE_START)
            return;
        if (BEManagedThread.isAgentThread())
            stats[typeId - MetadataCache.BE_TYPE_START].incrementNumRetractedFromAgents();
        else
            stats[typeId - MetadataCache.BE_TYPE_START].incrementNumRetractedFromChannel();
    }

    public String printStats(int typeId) {
        if (typeId < MetadataCache.BE_TYPE_START)
            return "InvalidTypeId:" + typeId;
        return stats[typeId - MetadataCache.BE_TYPE_START].printStats();
    }
}
