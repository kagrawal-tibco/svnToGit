package com.tibco.cep.runtime.service.om.impl.datastore;

import com.tibco.cep.kernel.core.base.BaseTimeManager;
import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.kernel.model.knowledgebase.Handle;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.service.om.exception.OMException;
import com.tibco.cep.runtime.service.om.exception.OMFetchException;
import com.tibco.cep.runtime.service.time.BETimeManager;
import com.tibco.cep.util.ResourceManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: nbansal
 * Date: Aug 6, 2006
 * Time: 12:07:27 AM
 * To change this template use File | Settings | File Templates.
 */
public class EventLogger  {

    private LRUCache eventCache;
    private HashSet newEventHandles;
    private HashSet deletedevents;
    private PersistentStore om;
    private HashSet toWriteEventHandles;
    private HashSet todeleteevents;
    private ArrayList ackEvents;
    private ArrayList toackevents;
    DataStore ds;

    public EventLogger(PersistentStore _om) {
        om = _om;
    }

    public void init() throws Exception {
        Properties props = om.omConfig;
//        int cachesize = Integer.parseInt(props.getProperty("omPropEventSize", "1000"));
        int cachesize = getCacheSize(props);
        if(om.m_logger.isEnabledFor(Level.INFO))
        	 om.m_logger.log(Level.INFO, "Event cachesize = %s", cachesize);

        eventCache = new LRUCache(cachesize);
        newEventHandles = new HashSet();
        deletedevents = new HashSet();
        ackEvents = new ArrayList();
        ds = om.dbFactory().createEventDataStore(om.omConfig, om.defaultSerializer);
        ds.init();
    }

    private int getCacheSize(Properties props) {
        String prop = props.getProperty("be.engine.om.eventcache.maxsize." + om.session.getName());
        int value = -1;
        if(prop != null && prop.length() > 0) {
            try {
                value = Integer.parseInt(prop);
            } catch (NumberFormatException npe) {}
        }
        if(value >= 0) return value;

        prop = props.getProperty("be.engine.om.eventcache.defaultmaxsize");
        if(prop != null && prop.length() > 0) {
            try {
                value = Integer.parseInt(prop);
            } catch (NumberFormatException npe) {}
        }
        if(value >= 0) return value;
        prop = props.getProperty("omPropCacheSize", "1000");
        return Integer.parseInt(prop);
    }

    public void start() throws Exception {

    }

    public void shutdown() throws Exception {
        if(ds != null) {
            ds.close();
            ds = null;
        }
    }

    public void stop() throws Exception {
    }

    public Event getEvent(long id) {
        Long key = new Long(id);
        Event e = (Event) eventCache.get(key);
        if(e == null) {  //search in the DB
            try {
                e = (Event) ds.fetch(null, key, null);
            } catch (OMFetchException e1) {
                e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                return null;
            }
            if(e != null)
                eventCache.put(key, e);
        }
        return e;
    }

    void ackEvent(SimpleEvent e) {
        ackEvents.add(e);
    }

    void addEvent(Event e, Handle h) {
        if(newEventHandles.add(h)) {
            om.incrementOutstandingOps();
        }
    }

//    public void addEvent(Event e) {
//        Handle h = om.getHandle(e);
//        addEvent(e, h);
//    }

    void removeEvent(Event e, Handle h) {
        if(!newEventHandles.remove(h)) {
            deletedevents.add(e);
            om.incrementOutstandingOps();
        }
    }

//    public void removeEvent(Event e) {
//        Handle h = om.getHandle(e);
//        removeEvent(e, h);
//    }

    int numOfEventToAck() {
        return ackEvents.size();
    }

    public void prepareCheckpoint() {
        toWriteEventHandles = newEventHandles;
        todeleteevents = deletedevents;
        toackevents = ackEvents;
        if (om.m_logger.isEnabledFor(Level.INFO)) {
            if (toWriteEventHandles.size() > 0) om.m_logger.log(Level.INFO, "Num of events to write = %s", toWriteEventHandles.size());
            if (todeleteevents.size() > 0) om.m_logger.log(Level.INFO, "Num of events to delete = %s", todeleteevents.size());
            if (toackevents.size() > 0) om.m_logger.log(Level.INFO, "Num of events to ack = %s", toackevents.size());
        }
        ackEvents = new ArrayList();
        newEventHandles = new HashSet();
        deletedevents = new HashSet();
    }

    public void doCheckpoint(DBTransaction txn) throws OMException {
        Iterator it = todeleteevents.iterator();
        DefaultSerializer.SerializeEntityWrapper serializeWrapper = new DefaultSerializer.SerializeEntityWrapper();

        while(it.hasNext()) {
            Event e = (Event) it.next();
            Long key = new Long(e.getId());
            if(om.immediateDeletePolicy) {
                ds.delete(txn, key);
            } else {
                serializeWrapper.entity = e;
                serializeWrapper.checkpointTime = om.checkpointTime;
                serializeWrapper.wasDeleted = true;
                ds.modify(txn, key, serializeWrapper);
                serializeWrapper.entity = null;
            }
              eventCache.remove(key);
        }
        todeleteevents = null;

        it = toWriteEventHandles.iterator();
        while(it.hasNext()) {
            PersistentHandle h = (PersistentHandle) it.next();
            Event e = (Event) h.getObject();
            Long key = new Long(e.getId());
            serializeWrapper.entity = e;
            serializeWrapper.checkpointTime = om.checkpointTime;
            serializeWrapper.wasDeleted = false;
            ds.add(txn, key, serializeWrapper);
            eventCache.put(key, e);
            h.removeRef();
            serializeWrapper.entity = null;
        }
        toWriteEventHandles = null;
    }

    public int ackEvents() {
        int num = toackevents.size();
        if(num == 0) return 0;
        long startTime = System.currentTimeMillis();
        if(om.m_logger.isEnabledFor(Level.DEBUG)) {
            om.m_logger.log(Level.DEBUG,ResourceManager.getInstance().formatMessage("be.om.events.acking", Integer.toString(toackevents.size())));
        }
        Iterator it = toackevents.iterator();
        while(it.hasNext()) {
            SimpleEvent evt = (SimpleEvent) it.next();
            evt.acknowledge();
//            evt.delete();
        }
        long total = System.currentTimeMillis() - startTime;
        om.m_logger.log(Level.INFO,ResourceManager.getInstance().formatMessage("be.om.events.acked", Integer.toString(toackevents.size()), Long.toString(total)));
        toackevents = null;
        return num;
    }

    public void recover() throws OMException {
        final int[] num = new int[1];
        num[0] = 0;
        om.m_logger.log(Level.INFO,ResourceManager.getInstance().getMessage("be.om.events.recovering"));
        ds.readAll(new DataStore.ReadCallback() {
            public void readObj(Object obj) throws Exception {
                PersistentHandle h = (PersistentHandle) om.session.loadObject(obj);
                om.session.getRuleServiceProvider().getIdGenerator().setMinEntityId(((Event)obj).getId());
                num[0]++;
                if(num[0]%1000 == 0) {
                    om.m_logger.log(Level.INFO,ResourceManager.getInstance().formatMessage("be.om.event.loaded",new Long(num[0])));
                }
                eventCache.put(new Long(((Event)obj).getId()), obj);
                //possible race if load object above fires the event before this gets called
                ((BETimeManager)om.session.getTimeManager()).recordRecoveredStateTimeoutEvent(h);
                h.removeRef();
            }
        });
        om.m_logger.log(Level.INFO,ResourceManager.getInstance().formatMessage("be.om.event.recovered", new Long(num[0])));

    }
}
