package com.tibco.cep.query.stream.impl.rete.integ.container;

import java.lang.ref.Reference;

import com.tibco.cep.kernel.core.base.BaseObjectManager;
import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.query.stream.cache.Cache;
import com.tibco.cep.query.stream.monitor.ResourceId;

/*
* Author: Ashwin Jayaprakash Date: Mar 24, 2008 Time: 5:18:32 PM
*/

/**
 * The Primary cache that actually delegates calls to the real Object manager.
 */
public class PrimaryCacheOMDelegator implements Cache {
    protected BaseObjectManager baseOM;

    protected ResourceId resourceId;

    public PrimaryCacheOMDelegator(ResourceId parentId, BaseObjectManager realOM) {
        this.resourceId = new ResourceId(parentId, getClass().getName());
        this.baseOM = realOM;
    }

    public Object put(Object key, Object value) {
        //Do nothing.

        return null;
    }

    public Object get(Object key) {
        final Long id = (Long) key;

        Entity entity = baseOM.getElement(id);

        if (entity == null) {
            entity = baseOM.getEvent(id);

            if (entity == null) {
                baseOM.getObject(key);
            }
        }

        return entity;
    }

    public boolean isGetInternalEntrySupported() {
        return false;
    }

    /**
     * Not implemented!
     *
     * @param key
     * @return <code>null</code>.
     */
    public Reference<InternalEntry> getInternalEntry(Object key) {
        return null;
    }

    public Object remove(Object key) {
        //Do nothing.
        return null;
    }

    public int size() {
        return -1;
    }

    //-----------

    public void start() throws Exception {
    }

    public void stop() throws Exception {
        baseOM = null;

        resourceId.discard();
        resourceId = null;
    }

    public ResourceId getResourceId() {
        return resourceId;
    }
}
