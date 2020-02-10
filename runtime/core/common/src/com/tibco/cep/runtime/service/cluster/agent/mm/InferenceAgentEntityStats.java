/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.cluster.agent.mm;

import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import com.tibco.cep.runtime.service.om.api.EntityDaoConfig;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: May 18, 2008
 * Time: 10:37:08 AM
 * To change this template use File | Settings | File Templates.
 */
public class InferenceAgentEntityStats implements InferenceAgentEntityStatsMBean{
    private volatile long numAssertedFromChannel =0L;
    private volatile long numAssertedFromAgents =0L;
    private volatile long numModifiedFromChannel=0L;
    private volatile long numModifiedFromAgents=0L;
    private volatile long numRetractedFromChannel=0L;
    private volatile long numRetractedFromAgents=0L;
    private volatile long numRecovered=0L;

    private volatile long timePreRTC=0L;
    private volatile long timeInRTC=0L;
    private volatile long timePostRTC=0L;

    private volatile long numPreRTC=0L;
    private volatile long numInRTC=0L;
    private volatile long numPostRTC=0L;

    private volatile long numMissesInL1Cache=0L;
    private volatile long numHitsInL1Cache=0L;
    private Class entityClz;
    private EntityDaoConfig.CacheMode cacheMode;

    protected InferenceAgentEntityStats(Class entityClz, String objectName, EntityDaoConfig.CacheMode cacheMode) {
        this.entityClz=entityClz;
        this.cacheMode=cacheMode;
        registerMBean(objectName);
    }

    private void registerMBean(String objectName) {
        try {
            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
            //ObjectName name = new ObjectName("com.tibco.cep.runtime.service.om.impl.coherence.cluster:type=CacheClusterMBean");
//            ObjectName name = new ObjectName("BusinessEvents.Cluster:type=CacheClusterMBean");
            ObjectName name = new ObjectName(objectName);
            //ObjectName name = new ObjectName("com.tibco.be:service=ClusterCache,name=" + cacheName);
            //InferenceAgent,service=Agents" + ",id=" + this.getAgentId());
            //ObjectName name = new ObjectName("com.tibco.be:cluster=" + this.getClusterName());
            if(!mbs.isRegistered(name))
                mbs.registerMBean(this, name);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String getCacheMode() {
        return cacheMode.name().toUpperCase();
    }

    public long getNumRecovered() {
        return numRecovered;
    }

    public void incrementNumRecovered() {
        ++this.numRecovered;
    }

    public long getNumAssertedFromChannel() {
        return numAssertedFromChannel;
    }

    public void incrementNumAssertedFromChannel() {
        ++this.numAssertedFromChannel;
    }

    public long getNumAssertedFromAgents() {
        return numAssertedFromAgents;
    }

    public void incrementNumAssertedFromAgents() {
        ++this.numAssertedFromAgents;
    }

    public double getAvgTimePreRTC() {
        if (numPreRTC > 0) {
            return timePreRTC/ (numPreRTC*1.0);
        } else {
            return 0.0;
        }
    }

    public double getAvgTimeInRTC() {
        if (numInRTC > 0) {
            return timeInRTC/ (numInRTC*1.0);
        } else {
            return 0.0;
        }
    }

    public double getAvgTimePostRTC() {
        if (numPostRTC > 0) {
            return timePostRTC/ (numPostRTC*1.0);
        } else {
            return 0.0;
        }
    }


    public void incrementPreRTC(long time) {
        timePreRTC += time;
        ++numPreRTC;
    }

    public void incrementInRTC(long time) {
        timeInRTC += time;
        ++numInRTC;
    }

    public void incrementPostRTC(long time) {
        timePostRTC += time;
        ++numPostRTC;
    }


    public long getNumHitsInL1Cache() {
        return numHitsInL1Cache;
    }

    public void incrementNumHitsInL1Cache() {
        ++this.numHitsInL1Cache;
    }

    public long getNumMissesInL1Cache() {
        return numMissesInL1Cache;
    }

    public void incrementNumMissesInL1Cache() {
        ++this.numMissesInL1Cache;
    }

    public long getNumModifiedFromChannel() {
        return numModifiedFromChannel;
    }

    public void incrementNumModifiedFromChannel() {
        ++this.numModifiedFromChannel;
    }

    public long getNumModifiedFromAgents() {
        return numModifiedFromAgents;
    }

    public void incrementNumModifiedFromAgents() {
        ++this.numModifiedFromAgents;
    }


    public long getNumRetractedFromChannel() {
        return numRetractedFromChannel;
    }

    public void incrementNumRetractedFromChannel() {
        ++this.numRetractedFromChannel;
    }

    public long getNumRetractedFromAgents() {
        return numRetractedFromAgents;
    }

    public void incrementNumRetractedFromAgents() {
        ++this.numRetractedFromAgents;
    }
    
    public String printStats() {
    	return 
    	//" NumAssertedFromAgents=" + numAssertedFromAgents + 
    	//" NumAssertedFromChannel=" + numAssertedFromChannel + 
    	" NumInRTC=" + numInRTC + 
    	// " NumPreRTC=" + numPreRTC + 
    	// " NumPostRTC=" + numPostRTC + 
    	" TimeInRTC=" + timeInRTC + "(avg=" + (timeInRTC/numInRTC) + ")" + 
    	" TimePreRTC=" + timePreRTC + "(avg=" + (timePreRTC/numInRTC) + ")" + 
    	" TimePostRTC=" + timePostRTC + "(avg=" + (timePostRTC/numInRTC)+ ")" +
    	//" NumHitsInL1Cache=" + numHitsInL1Cache + 
    	//" NumMissesInL1Cache=" + numMissesInL1Cache + 
    	//" NumModifiedFromAgents=" + numModifiedFromAgents + 
    	//" NumModifiedFromChannel=" + numModifiedFromChannel + 
    	//" NumRecovered=" + numRecovered + 
    	//" NumRetractedFromAgents=" + numRetractedFromAgents + 
    	//" NumRetractedFromChannel=" + numRetractedFromChannel +
    	"";
    }
}
