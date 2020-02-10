package com.tibco.cep.runtime.service.om;

import java.util.Collection;

import com.tibco.cep.kernel.model.entity.Entity;

/*
* Author: Ashwin Jayaprakash Date: Oct 16, 2008 Time: 10:51:49 AM
*/
public interface FastLocalCache {
    void putEntity(Long id, Entity entity);

    Entity getEntity(Long id);

    void removeEntity(Long id);

    void removeEntities(Collection<Long> ids);
}
