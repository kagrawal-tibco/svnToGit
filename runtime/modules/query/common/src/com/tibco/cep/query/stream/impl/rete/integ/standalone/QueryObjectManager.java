/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.query.stream.impl.rete.integ.standalone;

import com.tibco.cep.kernel.core.base.AbstractElementHandle;
import com.tibco.cep.kernel.core.base.AbstractEventHandle;
import com.tibco.cep.kernel.core.base.BaseObjectManager;
import com.tibco.cep.kernel.core.base.RtcOperationList;
import com.tibco.cep.kernel.model.entity.Element;
import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.kernel.model.knowledgebase.DuplicateExtIdException;
import com.tibco.cep.kernel.model.knowledgebase.Handle;
import com.tibco.cep.kernel.model.knowledgebase.TypeInfo;
import com.tibco.cep.kernel.model.knowledgebase.WorkingMemory;
import com.tibco.cep.query.stream.cache.Cache;
import com.tibco.cep.query.stream.cache.SharedObjectSource;
import com.tibco.cep.query.stream.cache.SharedObjectSourceRepository;
import com.tibco.cep.query.stream.impl.rete.service.AgentService;
import com.tibco.cep.query.stream.monitor.CustomRuntimeException;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.runtime.model.element.impl.ManagedObjectLockType;
import com.tibco.cep.runtime.model.element.impl.ManagedObjectManager;
import com.tibco.cep.runtime.service.cluster.system.IDEncoder;
import com.tibco.cep.runtime.service.cluster.system.MetadataCache;
import com.tibco.cep.runtime.service.cluster.system.ObjectTable;
import com.tibco.cep.runtime.service.cluster.system.ObjectTable.Tuple;

import java.util.concurrent.ConcurrentHashMap;

/*
 * Author: Ashwin Jayaprakash Date: Mar 7, 2008 Time: 2:55:25 PM
 */

public class QueryObjectManager extends BaseObjectManager implements SharedObjectSourceRepository {
    private ConcurrentHashMap<Class, SharedObjectSource> cachedSources;

    protected DelegateRepository delegateRepository;

    protected QueryWorkingMemory workingMemory;

    protected MetadataCache metadataCache;

    protected ObjectTable objectTable;

    protected AgentService agentService;

    protected String regionName;

    protected ResourceId resourceId;

    public QueryObjectManager(ResourceId parentId, String regionName, AgentService agentService,
                              Cache primaryCache, Cache deadPoolCache, boolean standalone) {
        super(regionName);

        this.regionName = regionName;

        this.resourceId = new ResourceId(parentId, getClass().getName());

        this.agentService = agentService;

        if (standalone) {
            this.delegateRepository =
                    new StandaloneDelegateRepository(this.resourceId, agentService, primaryCache,
                            deadPoolCache);
        } else {
            this.delegateRepository =
                    new DefaultDelegateRepository(this.resourceId, agentService, primaryCache,
                            deadPoolCache);
        }

        this.cachedSources = new ConcurrentHashMap<Class, SharedObjectSource>();
    }

    public ResourceId getResourceId() {
        return resourceId;
    }

    public Cache getDeadPoolCache() {
        return delegateRepository.getDeadPoolCache();
    }

    public Cache getPrimaryCache() {
        return delegateRepository.getPrimaryCache();
    }

    private SharedObjectSource fillCache(Class klass) {
        String klassName = klass.getName();
        SharedObjectSource source = delegateRepository.getSource(klassName);
        cachedSources.put(klass, source);

        return source;
    }

    /**
     * @param id
     * @param ignoredClass We ignore this parameter because the BE object model incorrectly gives
     *                     the parent class' Class instead of the actual Class name when there are
     *                     hierarchies. This means one extra lookup in the ObjectIdTable so, things
     *                     will be slower.
     * @return
     */
    @Override
    public Element getElement(long id, Class ignoredClass) {
        try {
            Tuple entityTuple = getObjectTableCache().getById(id);

            int typeId = entityTuple.getTypeId();

            Class clazz = getMetadataCache().getClass(typeId);

            return blindGetElement(id, clazz);
        }
        catch (Exception e) {
            throw new CustomRuntimeException(resourceId, e);
        }
    }

    /**
     * @param id
     * @param ignoredClass We ignore this parameter because the BE object model incorrectly gives
     *                     the parent class' Class instead of the actual Class name when there are
     *                     hierarchies. This means one extra lookup in the ObjectIdTable so, things
     *                     will be slower.
     * @return
     */
    @Override
    public Event getEvent(long id, Class ignoredClass) {
        try {
            Tuple entityTuple = getObjectTableCache().getById(id);

            int typeId = entityTuple.getTypeId();

            Class clazz = getMetadataCache().getClass(typeId);

            return blindGetEvent(id, clazz);
        }
        catch (Exception e) {
            throw new CustomRuntimeException(resourceId, e);
        }
    }

    public Element blindGetElement(long id, Class clzz) {
        SharedObjectSource source = cachedSources.get(clzz);
        if (source == null) {
            source = fillCache(clzz);
        }

        return (Element) source.fetch(id, false);
    }

    public Event blindGetEvent(long id, Class eventClz) {
        SharedObjectSource source = cachedSources.get(eventClz);
        if (source == null) {
            source = fillCache(eventClz);
        }

        return (Event) source.fetch(id, false);
    }

    /**
     * @param wm Has to be of type {@link QueryWorkingMemory}.
     */
    @Override
    public void init(WorkingMemory wm) {
        workingMemory = (QueryWorkingMemory) wm;

        delegateRepository.init(workingMemory);
    }

    @Override
    public void start() throws Exception {
        delegateRepository.start();
    }

    @Override
    public void stop() throws Exception {
        delegateRepository.stop();
        delegateRepository = null;

        cachedSources.clear();
        cachedSources = null;

        workingMemory = null;

        agentService = null;
        metadataCache = null;
        objectTable = null;

        regionName = null;

        resourceId.discard();
        resourceId = null;
    }

    // -----------

    public SharedObjectSource getDefaultSource() {
        return delegateRepository.getDefaultSource();
    }

    public QueryTypeInfo getSource(String name) {
        return delegateRepository.getSource(name);
    }

    public void purgeObject(Object key, String sourceName) {
        delegateRepository.purgeObject(key, sourceName);
    }

    public void softPurgeObject(Object key, String sourceName) {
        delegateRepository.softPurgeObject(key, sourceName);
    }

    // -----------

    /**
     * AgentService gets initialized lazily.
     *
     * @return
     */
    private MetadataCache getMetadataCache() {
        if (metadataCache == null) {
            metadataCache = agentService.getMetadataCache();
        }

        return metadataCache;
    }

    /**
     * AgentService gets initialized lazily.
     *
     * @return
     */
    private ObjectTable getObjectTableCache() {
        if (objectTable == null) {
            objectTable = agentService.getObjectTableCache();
        }

        return objectTable;
    }

    /**
     * @param extId
     * @return
     */
    @Override
    public Event getEvent(String extId) {
        try {
            Tuple entityTuple = getObjectTableCache().getByExtId(extId);
            if (entityTuple == null) {
                return null;
            }

            int typeId = entityTuple.getTypeId();
            long reteId = entityTuple.getId();

            Class clazz = getMetadataCache().getClass(typeId);

            return blindGetEvent(reteId, clazz);
        }
        catch (Exception e) {
            throw new CustomRuntimeException(resourceId, e);
        }
    }

    /**
     * @param extId
     * @return
     */
    @Override
    public Element getElement(String extId) {
        try {
            Tuple entityTuple = getObjectTableCache().getByExtId(extId);
            if (entityTuple == null) {
                return null;
            }

            int typeId = entityTuple.getTypeId();
            long reteId = entityTuple.getId();

            Class clazz = getMetadataCache().getClass(typeId);

            return blindGetElement(reteId, clazz);
        }
        catch (Exception e) {
            throw new CustomRuntimeException(resourceId, e);
        }
    }

    /**
     * @param extId
     * @return
     */
    @Override
    public Entity getEntity(String extId) {
        try {
            Tuple entityTuple =  getObjectTableCache().getByExtId(extId);
            if (entityTuple == null) {
                return null;
            }

            int typeId = entityTuple.getTypeId();
            long reteId = entityTuple.getId();

            Class clazz = getMetadataCache().getClass(typeId);

            Entity entity = blindGetEvent(reteId, clazz);
            if (entity == null) {
                entity = blindGetElement(reteId, clazz);
            }

            return entity;
        }
        catch (Exception e) {
            throw new CustomRuntimeException(resourceId, e);
        }
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Element getElement(long id) {
        return doGetElement(id);
    }

    @Override
    public Element getElement(long id, boolean ignoreRetractedOrMarkedDelete) {
        return doGetElement(id);
    }

    private Element doGetElement(long id) {
        try {
        	if (ManagedObjectManager.isOn()) {
                return (Element) ManagedObjectManager.fetchById(id, null, ManagedObjectLockType.READLOCK);
        	} else {
        		int typeId;
        		if(!agentService.useObjectTable()) {
                    typeId = IDEncoder.decodeTypeId(id);
                } else {
		            Tuple entityTuple = getObjectTableCache().getById(id);
		            if (entityTuple == null) {
		                return null;
		            }
		
		            typeId = entityTuple.getTypeId();
		            id = entityTuple.getId();
                }
        		Class clazz = getMetadataCache().getClass(typeId);
	            return blindGetElement(id, clazz);
        	}
        }
        catch (Exception e) {
            throw new CustomRuntimeException(resourceId, e);
        }
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Event getEvent(long id) {
        try {
            Tuple entityTuple =  getObjectTableCache().getById(id);
            if (entityTuple == null) {
                return null;
            }

            int typeId = entityTuple.getTypeId();
            long reteId = entityTuple.getId();

            Class clazz = getMetadataCache().getClass(typeId);

            return blindGetEvent(reteId, clazz);
        }
        catch (Exception e) {
            throw new CustomRuntimeException(resourceId, e);
        }
    }

    // -----------

    /**
     * @param rtcList
     * @throws UnsupportedOperationException
     */
    @Override
    public void applyChanges(RtcOperationList rtcList) {
        throw new UnsupportedOperationException();
    }

    /**
     * @param obj
     * @param _next
     * @return
     * @throws UnsupportedOperationException
     */
    @Override
    protected AbstractElementHandle getNewElementExtHandle(Element obj,
                                                           AbstractElementHandle _next,
                                                           TypeInfo _typeInfo) {
        throw new UnsupportedOperationException();
    }

    /**
     * @param obj
     * @param _next
     * @return
     * @throws UnsupportedOperationException
     */
    @Override
    protected AbstractElementHandle getNewElementHandle(Element obj, AbstractElementHandle _next,
                                                        TypeInfo _typeInfo) {
        throw new UnsupportedOperationException();
    }

    /**
     * @param obj
     * @param _next
     * @return
     * @throws UnsupportedOperationException
     */
    @Override
    protected AbstractEventHandle getNewEventExtHandle(Event obj, AbstractEventHandle _next,
                                                       TypeInfo _typeInfo) {
        throw new UnsupportedOperationException();
    }

    /**
     * @param obj
     * @param _next
     * @return
     * @throws UnsupportedOperationException
     */
    @Override
    protected AbstractEventHandle getNewEventHandle(Event obj, AbstractEventHandle _next,
                                                    TypeInfo _typeInfo) {
        throw new UnsupportedOperationException();
    }

    /**
     * @param entity
     * @throws DuplicateExtIdException
     */
    public void createElement(Element entity) throws DuplicateExtIdException {
        throw new UnsupportedOperationException();
    }

    /**
     * @param event
     * @throws DuplicateExtIdException
     */
    public void createEvent(Event event) throws DuplicateExtIdException {
        throw new UnsupportedOperationException();
    }

    public void deleteEntity(Handle handle) {
        throw new UnsupportedOperationException();
    }

    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public void shutdown() throws Exception {
        throw new UnsupportedOperationException();
    }

    /**
     * @return
     * @throws UnsupportedOperationException
     */
    public boolean isObjectStore() {
        throw new UnsupportedOperationException();
    }
}
