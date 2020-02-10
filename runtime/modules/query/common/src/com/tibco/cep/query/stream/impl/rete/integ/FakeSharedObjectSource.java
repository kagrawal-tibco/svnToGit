package com.tibco.cep.query.stream.impl.rete.integ;

import com.tibco.cep.kernel.core.base.EntitySharingLevel;
import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.kernel.model.knowledgebase.WorkingMemory;
import com.tibco.cep.query.stream.cache.Cache;
import com.tibco.cep.query.stream.cache.SharedObjectSource;
import com.tibco.cep.query.stream.impl.rete.integ.standalone.QueryTypeInfo;

/*
* Author: Ashwin Jayaprakash Date: Apr 9, 2009 Time: 2:00:38 PM
*/
public class FakeSharedObjectSource implements SharedObjectSource, QueryTypeInfo {
    protected Cache primaryCache;

    protected Cache deadPoolCache;

    public FakeSharedObjectSource(Cache primaryCache, Cache deadPoolCache) {
        this.primaryCache = primaryCache;
        this.deadPoolCache = deadPoolCache;
    }

    public Object fetch(Object key, boolean refreshFromSource) {
        return null;
    }

    public void purge(Object key) {
    }

    public void softPurge(Object key) {
    }

    public void discard() {
    }

    public Object fetchValueOrInternalEntryRef(Object key, boolean refreshFromSource) {
        return null;
    }

    public FakeSharedObjectSource getInternalSource() {
        return this;
    }

    public Cache getPrimaryCache() {
        return primaryCache;
    }

    public Cache getDeadPoolCache() {
        return deadPoolCache;
    }

    @Override
    public Entity handleDownloadedEntity(Object o, Long id) {
        Entity entity = (Entity) o;

        return entity;
    }

    //--------------

    @Override
    public Class getType() {
        return null;
    }

    @Override
    public boolean hasRule() {
        return false;
    }

    @Override
    public WorkingMemory getWorkingMemory() {
        return null;
    }

    @Override
    public EntitySharingLevel getLocalSharingLevel() {
        return EntitySharingLevel.DEFAULT;
    }

    @Override
    public void setLocalSharingLevel(EntitySharingLevel lev) {
    }

    @Override
    public EntitySharingLevel getRecursiveSharingLevel() {
        return EntitySharingLevel.DEFAULT;
    }

    @Override
    public void setRecursiveSharingLevel(EntitySharingLevel lev) {
    }
}
