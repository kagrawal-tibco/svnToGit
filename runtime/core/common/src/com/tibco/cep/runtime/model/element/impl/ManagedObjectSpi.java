package com.tibco.cep.runtime.model.element.impl;

import java.util.Collection;
import java.util.List;

import com.tibco.cep.kernel.core.rete.BeTransactionHook;

/*
* Author: Ashwin Jayaprakash / Date: 11/22/11 / Time: 2:39 PM
*/
public interface ManagedObjectSpi extends BeTransactionHook {
    boolean hasWriteLock(EntityImpl c);

    void readLockObject(EntityImpl c);

    void writeLockObject(EntityImpl c);

    void writeLockObject(long id);

    /**
     * @param id       Must be type encoded.
     * @param locktype
     * @return
     */
    EntityImpl fetchById(long id, Class entityClass, ManagedObjectLockType locktype);

    EntityImpl fetchByExtId(String extId, Class entityClass, ManagedObjectLockType locktype);
    List<EntityImpl> fetchByExtIds(Collection<String> extIds, Class entityClass, ManagedObjectLockType locktype);

    void insert(EntityImpl entity);

    void update(EntityImpl entity);

    void delete(long entityId, Class entityClass);
}
