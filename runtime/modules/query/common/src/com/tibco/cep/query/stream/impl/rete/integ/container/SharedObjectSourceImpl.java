package com.tibco.cep.query.stream.impl.rete.integ.container;

import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.query.stream.cache.Cache;
import com.tibco.cep.query.stream.impl.rete.integ.AbstractSharedObjectSource;
import com.tibco.cep.runtime.service.om.api.EntityDao;

/*
* Author: Ashwin Jayaprakash Date: Mar 24, 2008 Time: 5:18:32 PM
*/

public class SharedObjectSourceImpl extends AbstractSharedObjectSource {
    protected SharedObjectSourceImpl(EntityDao source, String cacheName,
                                     Cache primaryCache, Cache deadPoolCache,
                                     ClassLoader entityClassLoader) {
        super(source, cacheName, primaryCache, deadPoolCache, entityClassLoader);
    }

    /**
     * @param o
     * @param id
     * @return "o" as-is.
     */
    @Override
    public Entity handleDownloadedEntity(Object o, Long id) {
        //Do nothing here. The real work will be done in the Primary cache impl.
        return (Entity) o;
    }
}
