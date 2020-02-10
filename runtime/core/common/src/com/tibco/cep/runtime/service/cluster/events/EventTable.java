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

package com.tibco.cep.runtime.service.cluster.events;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.events.notification.ClusterEventQueueListener;
import com.tibco.cep.runtime.service.om.api.EntityDao;
import com.tibco.cep.runtime.service.om.api.Filter;

/*
* Author: Ashwin Jayaprakash Date: Apr 23, 2009 Time: 11:22:56 AM
*/
public interface EventTable {
    void init(Cluster cluster);

    void start() throws Exception;

    Class getEventClass();

    EntityDao getEntityProvider();

    void addEventQueueListener(ClusterEventQueueListener lsnr);

    Iterator getHandles(Filter genericFilter, int pageSize) throws Exception;

    EventTuple getById(long id) throws Exception;

    void addEvent(EventTuple event) throws Exception;

    Event getOwnerEvent(EventTuple eventTuple) throws Exception;

    EventWriter createEventWriter();

    void consumeEvent(Long id);

    boolean acquireEvent(long id, int agentId);

    Map acquireEvent(Set keys, int agentId) throws Exception;

    boolean transferEvent(long id, int fromAgent, int toAgent) throws Exception;

    void releaseEvents(int agentId) throws Exception;

    boolean containsKey(Object key);

//    boolean recoverEvent(Long eventId);

    void consumeEvent(Collection<Long> value);

    void addAllEvents(Map<Long, ? extends EventTuple> allETs);

    void destroy();

    //Suresh TODO:From 4.0.0, this code/interface method is never used
   // Object getLocalTransactionEventTable();

    //-------------

    public interface EventWriter {
        void addEvent(EventTuple tuple);

        void commit();
    }
}
