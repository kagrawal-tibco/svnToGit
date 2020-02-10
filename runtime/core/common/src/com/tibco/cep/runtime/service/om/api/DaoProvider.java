/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.om.api;

import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.backingstore.GenericBackingStore;
import com.tibco.cep.runtime.service.om.api.invm.LocalCache;

import java.util.Collection;


public interface DaoProvider {
    <K, E extends Entity> EntityDao<K, E> createEntityDao(Class<E> entityClass, EntityDaoConfig daoConfig);

    <K, E extends Entity> EntityDao<K, E> createEntityDao(
            Class<E> entityClass,
            EntityDaoConfig daoConfig,
            boolean overwrite);

    <K, V> ControlDao<K, V> createControlDao(Class<K> keyClass, Class<V> valueClass,
                                             ControlDaoType daoType, Object... additionalProps);

    <K, E extends Entity> EntityDao<K, E> getEntityDao(Class<E> entityClass);

    <K, E extends Entity> EntityDao<K, E> getEntityDao(String cacheName);

    <K, V> ControlDao<K, V> getControlDao(String daoName); 

    LocalCache newLocalCache();

    Collection<? extends EntityDao> getAllEntityDao();

    Collection<? extends ControlDao> getAllControlDao();

    InvocationService getInvocationService();

    void init(Cluster cluster) throws Exception;
    
    void recoverCluster(Cluster cluster) throws Exception;

    GroupMemberMediator newGroupMemberMediator();

    void start() throws Exception;

    GenericBackingStore getBackingStore();

    void stop();

    void modelChanged();

}
