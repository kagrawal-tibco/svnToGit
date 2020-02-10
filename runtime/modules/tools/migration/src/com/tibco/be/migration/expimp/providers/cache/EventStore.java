package com.tibco.be.migration.expimp.providers.cache;

import java.util.Properties;

import com.tangosol.io.ExternalizableLite;
import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.event.Event;
import com.tibco.cep.runtime.session.RuleServiceProvider;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Feb 21, 2008
 * Time: 11:06:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class EventStore extends EntityStore {

    public EventStore(RuleServiceProvider rsp, String masterCacheName, Entity entity, Properties cacheConfig) throws Exception {
        super(rsp, masterCacheName, entity, cacheConfig);
    }

    public EventStore(RuleServiceProvider rsp, String masterCacheName, Class implClass, Properties cacheConfig) throws Exception {
        super(rsp, masterCacheName, implClass, cacheConfig);
    }

    public Event getModelEvent(Event event) {
        return (Event) getModelEntity();
    }

    public void put(com.tibco.cep.kernel.model.entity.Event event) {
        Thread.currentThread().setContextClassLoader((ClassLoader) getTypeManager());
        this.m_addedObjects.put(new Long(event.getId()), (ExternalizableLite) event);
    }

    public com.tibco.cep.kernel.model.entity.Event getEvent(long id) {
        Thread.currentThread().setContextClassLoader((ClassLoader) getTypeManager());
        return (com.tibco.cep.kernel.model.entity.Event) entityCache.get(new Long(id));
    }

    public void remove(long id) {
        Thread.currentThread().setContextClassLoader((ClassLoader) getTypeManager());
        this.m_deletedObjects.add(new Long(id));
    }
}
