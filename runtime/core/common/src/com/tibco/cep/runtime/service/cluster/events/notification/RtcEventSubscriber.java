/*
 * Copyright (c) TIBCO Software Inc 2010.
 * All rights reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 *
 * Author: Suresh Subramani (suresh.subramani@tibco.com)
 * Date  : 26/8/2010
 */

package com.tibco.cep.runtime.service.cluster.events.notification;


import java.util.Iterator;

import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.runtime.service.cluster.CacheAgent;
import com.tibco.cep.runtime.service.cluster.agent.InferenceAgent;
import com.tibco.cep.runtime.service.cluster.events.EventTable;
import com.tibco.cep.runtime.service.cluster.events.EventTuple;
import com.tibco.cep.runtime.service.cluster.filters.AvailableEventFilter;
import com.tibco.cep.runtime.service.om.api.EntityDao;
import com.tibco.cep.runtime.service.om.api.Filter;
import com.tibco.cep.runtime.session.BEManagedThread;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: May 5, 2008
 * Time: 10:43:50 AM
 * To change this template use File | Settings | File Templates.
 */
public class RtcEventSubscriber extends BEManagedThread implements ClusterEventQueueListener {
    InferenceAgent cacheAgent;

    public RtcEventSubscriber(InferenceAgent cacheAgent) {
        super(cacheAgent.getAgentName() + "-eventQueueListener", cacheAgent.getCluster().getRuleServiceProvider(), 1000);
        this.setAgentThreadType();
        this.cacheAgent = cacheAgent;
    }

    public void startListener() throws Exception {
        Iterator allEventSubs = cacheAgent.getEventSubscriptions().iterator();
        while (allEventSubs.hasNext()) {
            Class eventClz = (Class) allEventSubs.next();
            EventTable queue = cacheAgent.getCluster().getEventTableProvider().getEventTable(eventClz);
            if (queue != null) {
	            queue.addEventQueueListener(this);
	        }
	    }
    }

    public String getListenerName() {
        return cacheAgent.getAgentName();
    }

    public Filter getEventFilter() {
        return new AvailableEventFilter();
    }

    protected Class getEntityClass(int typeId) throws Exception {
        return cacheAgent.getCluster().getMetadataCache().getClass(typeId);
    }

    protected Object getObject(long id, Class entityClz) throws Exception {
        EntityDao entityDao = cacheAgent.getCluster().getMetadataCache().getEntityDao(entityClz);
        return entityDao.getByPrimaryKey(id);
    }

    public void onEvent(EventTuple tuple, byte opcode, EventTable eventQueue) {
        //System.out.println("onEvent invoked for " + tuple.getId());
        if (opcode != EVENT_DELETED) {
            if (tuple.isAvailable()) {
                //System.out.println("onEvent invoked for " + tuple.getId() + " , tuple available");
                while (true) {
                    //System.out.println("onEvent invoked for " + tuple.getId() + " , scheduling assert, rtcSub status=" + isShuttingDown());
                    try {
                        this.schedule(new AssertEvent(tuple, eventQueue, false));
                        return;
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            } else {
                //System.out.println("onEvent invoked for " + tuple.getId() + " , tuple not available, agent=" + tuple.getAgentId());
            }
        }
    }

    public boolean isActive() {
        return cacheAgent.getAgentState() == CacheAgent.AgentState.ACTIVATED;
    }

    class AssertEvent implements Runnable {
        EventTuple tuple;
        EventTable eventQueue;
        boolean loadOnly;


        AssertEvent(EventTuple tuple, EventTable eventQueue, boolean loadOnly) {
            this.tuple = tuple;
            this.eventQueue = eventQueue;
            this.loadOnly = loadOnly;
        }

        public void run() {
            if (tuple.isAvailable()) {
                try {
                    boolean acquired = eventQueue.acquireEvent(tuple.getId(), cacheAgent.getAgentId());
                    if (acquired) {
                        //System.out.println("Acquired event id=" + tuple.getId())  ;
                        Event evt = eventQueue.getOwnerEvent(tuple);
                        if (evt != null) {
                            cacheAgent.assertEventFromCache(evt, true);
                        }
                    } else {
                        //System.out.println("Could not Acquire event id=" + tuple.getId())  ;
                    }
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class ReleaseEvent implements Runnable {
        int agentId;
        EventTable eventQueue;

        ReleaseEvent(int agentId, EventTable eventQueue) {
            this.agentId = agentId;
            this.eventQueue = eventQueue;
        }

        public void run() {
            try {
                eventQueue.releaseEvents(agentId);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

}
