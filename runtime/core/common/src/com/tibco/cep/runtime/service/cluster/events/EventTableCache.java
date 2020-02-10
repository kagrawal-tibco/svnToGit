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

import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.events.notification.ClusterEventQueueListener;
import com.tibco.cep.runtime.service.cluster.filters.AcquireEvent;
import com.tibco.cep.runtime.service.cluster.filters.AgentEventsFilter;
import com.tibco.cep.runtime.service.cluster.filters.ReleaseEvent;
import com.tibco.cep.runtime.service.cluster.filters.TransferEvent;
import com.tibco.cep.runtime.service.om.api.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: May 4, 2008
 * Time: 2:55:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class EventTableCache implements EventTable {
    Cluster cluster;
    //NamedCache EVENTTABLE;
    ControlDao eventControlDao;
    ConcurrentMap<ClusterEventQueueListener, EventQueueDispatcher> lsnrs = new ConcurrentHashMap<ClusterEventQueueListener, EventQueueDispatcher>();
    EntityDao entityProvider = null;
    boolean started = false;
    Class eventClz;
    static Logger m_logger = LogManagerFactory.getLogManager().getLogger(EventTableCache.class);

    /**
     *
     * @param eventClz
     */

    public EventTableCache(Class eventClz) {
        this.eventClz = eventClz;
    }

    public void init(Cluster cluster) {
        this.cluster = cluster;
    }

    public Class getEventClass() {
        return eventClz;
    }

    public EntityDao getEntityProvider() {
        return entityProvider;
    }


    protected void printEventTable() {
        m_logger.log(Level.DEBUG, "PRINT EVENT TABLE FOR ALL AGENTS");
        java.util.Iterator allEvents = eventControlDao.entrySet().iterator();
        while (allEvents.hasNext()) {
            Map.Entry entry = (Map.Entry) allEvents.next();
            Object id = entry.getKey();
            EventTuple value = (EventTuple) entry.getValue();
            m_logger.log(Level.DEBUG, "ID=" + id + ", Tuple[" + value);
        }
    }

    protected void printEventTable(int agentId) {
        m_logger.log(Level.DEBUG, "PRINT EVENT TABLE FOR AGENT ID..." + agentId);
        java.util.Iterator allEvents = eventControlDao.entrySet(new AgentEventsFilter(agentId), 0).iterator();
        while (allEvents.hasNext()) {
            Map.Entry entry = (Map.Entry) allEvents.next();
            Object id = entry.getKey();
            EventTuple value = (EventTuple) entry.getValue();
            m_logger.log(Level.DEBUG, "ID=" + id + ", Tuple[" + value);
        }
    }

    public synchronized void addEventQueueListener(ClusterEventQueueListener lsnr) {
        EventQueueDispatcher dispatcher = new EventQueueDispatcher(lsnr);
        EventQueueDispatcher oldDispatcher = lsnrs.putIfAbsent(lsnr, dispatcher);

        if (oldDispatcher != dispatcher) {
            if (oldDispatcher != null) {
                stopListener(oldDispatcher);
            }
            lsnrs.remove(lsnr);
            lsnrs.put(lsnr, dispatcher);
        }
        //TODO: Bala: Should it be if !started?
        if (started) {
            startListener(dispatcher);
        }
    }

    public void start() throws Exception {
        if (!started) {
//            eventControlDao = cluster.getCache(EntityCacheName.getCacheName(EntityCacheName.DISTRIBUTED_CACHE_UNLIMITED_NOBACKINGSTORE,
//                    cluster.getClusterName(), "", eventClz.getName() + "$EventQueue"));

            eventControlDao = cluster.getDaoProvider().createControlDao(Long.class, EventTuple.class, ControlDaoType.EventQueue$EventClass,
                    eventClz.getName());
            eventControlDao.start();

            entityProvider = cluster.getMetadataCache().getEntityDao(eventClz);

            for (EventQueueDispatcher dispatcher : lsnrs.values()) {
                startListener(dispatcher);
            }
            started = true;
        }
    }

    private void startListener(EventQueueDispatcher dispatcher) {
        m_logger.log(Level.DEBUG, "Starting EventTable Listener on event dispatcher-" + dispatcher);
        //eventControlDao.addMapListener(dispatcher, new MapEventFilter((Filter) dispatcher.lsnr.getEventFilter()), false);
        eventControlDao.registerListener(dispatcher);//, dispatcher.lsnr.getEventFilter());
    }

    private void stopListener(EventQueueDispatcher dispatcher) {
        eventControlDao.unregisterListener(dispatcher);
    }

    public java.util.Iterator getHandles(Filter filter, int pageSize) throws Exception {
//    	System.err.println("###### SIZE = " + eventControlDao.size() + eventControlDao.getName());
        return eventControlDao.entrySet(filter, pageSize).iterator();
//        if (pageSize <= 0) {
//            return new CacheEntityIterator(eventControlDao, (Filter) filter);
//        } else {
//            return new CacheEntityPagedIterator(eventControlDao, (Filter) filter, pageSize);
//        }
    }

    public void destroy() {
        if (started) {
            eventControlDao.discard();
            eventControlDao = null;
            started = false;
        }
    }

    public EventTuple getById(long id) throws Exception {
        return (EventTuple) eventControlDao.get(new Long(id));
    }


    public void addEvent(EventTuple event) throws Exception {
        eventControlDao.put(event.getId(), event);
    }

    public Event getOwnerEvent(EventTuple eventTuple) throws Exception {
        return (Event) entityProvider.get(eventTuple.getId());
    }

    public EventWriter createEventWriter() {
        return new EventWriterImpl();
    }


    public void consumeEvent(Long id) {
        eventControlDao.remove(id);
        //eventControlDao.invoke(id, new ConditionalRemove(AlwaysFilter.INSTANCE));
    }

    public boolean acquireEvent(long id, int agentId) {
//        System.out.println("Before Update for Acquire events..");
//        printEventTable();
        Boolean ret = false;
        final Long key = new Long(id);
        if (eventControlDao.containsKey(key)) {
            Invocable.Result result;
            try {
                result = eventControlDao.invokeWithKey(id,new AcquireEvent(agentId));
                ret = result == null ? Boolean.FALSE : (Boolean) result.getResult();
                if (ret != null)
                    return ret.booleanValue();
                else
                    return false;
            } catch (Exception e) {
                //e.printStackTrace();
                m_logger.log(Level.DEBUG, "Result is null. Ignore expection, printed for valuable debug information.", e);
                return false;

            }
//        System.out.println("After Update for Acquire events..");
//        printEventTable();

        } else {
            // acquire the event during recovery
            // ensure only one agent gets the lock

            try {
                if (eventControlDao.lock(key, -1)) {
                    Map p = new HashMap();
                    p.put(key, new EventTuple(id, EventTuple.RECOVEREDEVENT));
                    eventControlDao.putAll(p);
                    ret = true;
                } else {
                    ret = false;
                }
            } finally {
                eventControlDao.unlock(key);
                return ret;
            }
        }
    }

    @Deprecated
    public Map acquireEvent(Set keys, int agentId) throws Exception {
        Map ret = (Map) eventControlDao.invoke(keys, new AcquireEvent(agentId));
        if (ret != null)
            return ret;
        else
            return null;
    }

    public boolean transferEvent(long id, int fromAgent, int toAgent) throws Exception {
        Invocable.Result result = eventControlDao.invokeWithKey(id, new TransferEvent(fromAgent, toAgent));
        if (result != null && result.getResult() != null)
            return ((Boolean)result.getResult()).booleanValue();
        else
            return false;
    }

    public void releaseEvents(int agentId) throws Exception {
//        System.out.println("Before Update for Release events..");
//        printEventTable();
        eventControlDao.invoke(new AgentEventsFilter(agentId),new ReleaseEvent(agentId));
//        System.out.println("After Update for Release events..");
//        printEventTable();
    }

    public boolean containsKey(Object key) {
        return eventControlDao.containsKey(key);
    }

//    public boolean recoverEvent(Long eventId) {
//        return acquireEvent(eventId, EventTuple.RECOVEREDEVENT);
//    }

    public void consumeEvent(Collection<Long> value) {
        eventControlDao.removeAll(value);
    }


    public void addAllEvents(Map<Long, ? extends EventTuple> allETs) {
        //To change body of implemented methods use File | Settings | File Templates.
        eventControlDao.putAll(allETs);
    }


    //Suresh TODO : From 4.0.0, this code/interface method is never used
//    public TransactionMap getLocalTransactionEventTable() {
//        return CacheFactory.getLocalTransaction(eventControlDao);
//    }

    public class EventWriterImpl implements EventWriter {
        HashMap idMap = new HashMap();

        protected EventWriterImpl() {
        }

        public void addEvent(EventTuple tuple) {
            idMap.put(tuple.getId(), tuple);
        }

        public void commit() {
            if (idMap.size() > 0) {
                eventControlDao.putAll(idMap);
            }
            idMap.clear();
        }
    }


    class EventQueueDispatcher implements ControlDao.ChangeListener {
        ClusterEventQueueListener lsnr;

        EventQueueDispatcher(ClusterEventQueueListener lsnr) {
            this.lsnr = lsnr;
        }



        public void onPut(Object key, Object value) {
            lsnr.onEvent((EventTuple) value, ClusterEventQueueListener.EVENT_ASSERTED, EventTableCache.this);
        }

        public void onUpdate(Object key, Object oldValue, Object newValue) {
            lsnr.onEvent((EventTuple) newValue, ClusterEventQueueListener.EVENT_MODIFIED, EventTableCache.this);
        }

        public void onRemove(Object key, Object value) {
            lsnr.onEvent((EventTuple) value, ClusterEventQueueListener.EVENT_DELETED, EventTableCache.this);
        }
    }
}
