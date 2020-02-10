package com.tibco.cep.query.stream.impl.rete.integ.standalone;

import com.tibco.cep.kernel.core.base.EntitySharingLevel;
import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.query.stream.cache.Cache;
import com.tibco.cep.query.stream.impl.rete.integ.AbstractSharedObjectSource;
import com.tibco.cep.runtime.service.om.api.EntityDao;

/*
* Author: Ashwin Jayaprakash / Date: Feb 23, 2010 / Time: 5:21:42 PM
*/

public class DefaultQueryTypeInfo extends AbstractSharedObjectSource implements QueryTypeInfo {
    protected Class type;

    protected QueryWorkingMemory workingMemory;

    protected QueryBaseHandle sharedDummyHandle;

    public DefaultQueryTypeInfo(EntityDao source, String cacheName,
                                Cache primaryCache, Cache deadPoolCache,
                                ClassLoader entityClassLoader, Class type,
                                QueryWorkingMemory workingMemory) {
        super(source, cacheName, primaryCache, deadPoolCache, entityClassLoader);

        this.type = type;
        this.workingMemory = workingMemory;
    }

    public void init() {
        this.sharedDummyHandle = new QueryBaseHandle(this);
    }

    public ClassLoader getEntityClassLoader() {
        return entityClassLoader;
    }

    public QueryWorkingMemory getWorkingMemory() {
        return workingMemory;
    }

    //following four methods are for concurrentWM so probably not needed here

    public EntitySharingLevel getLocalSharingLevel() {
        return EntitySharingLevel.DEFAULT;
    }

    public EntitySharingLevel getRecursiveSharingLevel() {
        return EntitySharingLevel.DEFAULT;
    }

    public void setLocalSharingLevel(EntitySharingLevel lev) {
    }

    public void setRecursiveSharingLevel(EntitySharingLevel lev) {
    }

    public Class getType() {
        return type;
    }

    @Override
    public void discard() {
        super.discard();

        type = null;
        workingMemory = null;

        sharedDummyHandle.discard();
        sharedDummyHandle = null;
    }

    // -----------

    @Override
    public Entity handleDownloadedEntity(Object o, Long id) {
        Entity entity = (Entity) o;

        //Use the same handle for all isntances of the same type.
        entity.start(sharedDummyHandle);

        return entity;
    }

    // -----------

    /**
     * @throws UnsupportedOperationException
     */
    public boolean hasRule() {
        throw new UnsupportedOperationException();
    }
}
