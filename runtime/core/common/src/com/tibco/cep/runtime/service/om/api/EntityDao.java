/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.om.api;

import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.util.annotation.Optional;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: suresh
 * Date: Jun 28, 2010
 * Time: 2:20:35 PM
 * To change this template use File | Settings | File Templates.
 */

/**
 * Primary interface that maps a Concept/Event to the data access layer.
 */
public interface EntityDao<K, E extends Entity> extends FilterableMap, Invoker  {
    String getName();

    Class<E> getEntityClass();

    void start(boolean overwrite) throws Exception;
    
    void waitUntilReady(boolean recover) throws Exception;

    void put(E e) throws Exception;

    EntityDaoConfig getConfig();

    void putAll(Map<K, E> collection) throws Exception;

    void updatePutStats(long putTime);

    Collection<E> getAll();

    Collection<E> getAll(Collection<K> keys);

    E getByPrimaryKey(K k);

    Entity get(K k);

    Result<E> getIfVersionGreater(K k, int version);

    void updateGetStats(long getTime);
    
    int clear(String filter);
    
    void removeAll(Set<K> keys);

    void updateRemoveStats(long removeTime);

    boolean lock(K key, long timeoutMillis);

    boolean unlock(K key);

    /**
     * The actual internal store, like Coherence NamedCache.
     *
     * @return
     */
    @Optional
    Object getInternal();

    interface Result<E extends Entity> {
        int getVersion();

        boolean isDeleted();

        /**
         * This can return null.
         *
         * @return
         */
        E getResult();
    }
}
