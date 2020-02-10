package com.tibco.cep.runtime.service.cluster.events;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.util.TypeHelper;
import com.tibco.cep.runtime.service.om.api.EntityDaoConfig;

public class DefaultEventTableProvider implements EventTableProvider{

    Cluster cluster;
    ConcurrentMap<Class, EventTable> eventTableRegistry = new ConcurrentHashMap<Class, EventTable>();

    @Override
    public void init(Cluster cluster) throws Exception {
        this.cluster = cluster;

        Map<Class, EntityDaoConfig> daoConfigs = this.cluster.getMetadataCache().getEntityConfigurations();
        for (Class clz : daoConfigs.keySet()) {

            if (TypeHelper.$isTimeBased(clz)) {
                EntityDaoConfig config = daoConfigs.get(clz);
                if (config.getCacheMode() != EntityDaoConfig.CacheMode.Memory) {
                    EventTable et = newEventTable(clz);
                    et.init(cluster);
                }
            }
        }
    }

    @Override
    public void start() throws Exception {

        for(EventTable et : eventTableRegistry.values()) {
            et.start();
        }
    }

    public void stop() {
         for(EventTable et : eventTableRegistry.values()) {
            et.destroy();
        }
    }

    @Override
    public EventTable getEventTable(Class eventClass) {
        return eventTableRegistry.get(eventClass);
    }

    @Override
    public EventTable newEventTable(Class eventClass) throws Exception {
        EventTable t = new EventTableCache(eventClass);
        EventTable oldT = eventTableRegistry.putIfAbsent(eventClass, t);

        return (oldT == null) ? t : oldT;
    }

	@Override
	public void releaseEventsOwnedByAgent(int agentId) {
		for (Iterator<Map.Entry<Class,EventTable>> i = eventTableRegistry.entrySet().iterator(); i.hasNext() ;) {
			Map.Entry<Class, EventTable> entry = i.next();
			EventTable eventTable = entry.getValue();
			try {
				eventTable.releaseEvents(agentId);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
