/*
 * Copyright (c) TIBCO Software Inc 2010.
 * All rights reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 *
 * Author: Suresh Subramani (suresh.subramani@tibco.com)
 * Date  : 27/8/2010
 */

package com.tibco.cep.runtime.service.cluster.om;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.tibco.cep.kernel.core.base.AbstractElementHandle;
import com.tibco.cep.kernel.core.base.AbstractEventHandle;
import com.tibco.cep.kernel.core.base.BaseHandle;
import com.tibco.cep.kernel.core.base.BaseHandleWrapper;
import com.tibco.cep.kernel.core.base.BaseObjectManager;
import com.tibco.cep.kernel.core.base.BaseTimeManager;
import com.tibco.cep.kernel.core.base.ElementHandleMap;
import com.tibco.cep.kernel.core.base.EntityHandleMap;
import com.tibco.cep.kernel.core.base.EntitySharingLevel;
import com.tibco.cep.kernel.core.base.EventHandleMap;
import com.tibco.cep.kernel.core.base.ExtIdHandle;
import com.tibco.cep.kernel.core.base.ExtIdMap;
import com.tibco.cep.kernel.core.base.ObjectHandleMap;
import com.tibco.cep.kernel.core.base.RtcOperationList;
import com.tibco.cep.kernel.core.base.WorkingMemoryImpl;
import com.tibco.cep.kernel.model.entity.Element;
import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.kernel.model.entity.NamedInstance;
import com.tibco.cep.kernel.model.knowledgebase.DuplicateException;
import com.tibco.cep.kernel.model.knowledgebase.DuplicateExtIdException;
import com.tibco.cep.kernel.model.knowledgebase.Handle;
import com.tibco.cep.kernel.model.knowledgebase.TypeInfo;
import com.tibco.cep.kernel.model.knowledgebase.WorkingMemory;
import com.tibco.cep.kernel.service.Filter;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.model.TypeManager.TypeDescriptor;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.impl.ManagedObjectLockType;
import com.tibco.cep.runtime.model.element.impl.ManagedObjectManager;
import com.tibco.cep.runtime.model.element.impl.StateMachineConceptImpl;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.model.event.impl.SimpleEventImpl;
import com.tibco.cep.runtime.service.cluster.CacheAgent;
import com.tibco.cep.runtime.service.cluster.agent.InferenceAgent;
import com.tibco.cep.runtime.service.cluster.gmp.GroupMembershipService;
import com.tibco.cep.runtime.service.cluster.gmp.QuorumStatus;
import com.tibco.cep.runtime.service.cluster.scheduler.AgentTimeManager;
import com.tibco.cep.runtime.service.cluster.system.IDEncoder;
import com.tibco.cep.runtime.service.cluster.txn.RtcTransactionProperties;
import com.tibco.cep.runtime.service.om.api.EntityDao;
import com.tibco.cep.runtime.service.om.api.EntityDaoConfig;
import com.tibco.cep.runtime.service.om.exception.OMException;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionEx;
import com.tibco.cep.runtime.session.impl.PreprocessContext;
import com.tibco.cep.runtime.session.impl.RuleServiceProviderImpl;
import com.tibco.cep.runtime.session.locks.LockManager;
import com.tibco.cep.runtime.session.sequences.SequenceManager;


/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Oct 24, 2006
 * Time: 5:01:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class DefaultDistributedCacheBasedStore extends BaseObjectManager implements DistributedCacheBasedStore {
    static final int DEFAULT_INITIAL_CAPACITY = 4096;

    //Properties m_omConfig;
    RuleSessionEx m_session;
    private CacheStoreFactory customCacheStoreFactory = null;
    private CacheStore.CacheIdGenerator m_cacheIdGenerator;
    boolean bInRecovery = false;
    //WorkManager jobThreadPool;
    boolean isStarted = false;
    AgentTimeManager agentTimeManager;
    protected Logger logger;

    //------------

    //Maps for memory only mode and cache+memory
    private Object p_sharedEventHandleMap;

    private Object p_sharedElementHandleMap;

    private Object p_sharedExtIdHandleMap;

    //------------
    private Object p_eventHandleMap;

    private Object p_elementHandleMap;

    private Object p_extIdHandleMap;

    ConcurrentHashMap<Long, BaseHandleWrapper> get_p_eventHandleMap() {
        if (p_eventHandleMap instanceof ConcurrentHashMap) {
            return (ConcurrentHashMap<Long, BaseHandleWrapper>) p_eventHandleMap;
        }

        ConcurrentHashMap<Long, BaseHandleWrapper> map =
                ((ThreadLocal<ConcurrentHashMap<Long, BaseHandleWrapper>>) p_eventHandleMap).get();
        if (map == null) {
            map = new ConcurrentHashMap<Long, BaseHandleWrapper>();
            ((ThreadLocal) p_eventHandleMap).set(map);
        }

        return map;
    }

    ConcurrentHashMap<String, BaseHandle> get_p_extIdHandleMap() {
        if (p_extIdHandleMap instanceof ConcurrentHashMap) {
            return (ConcurrentHashMap<String, BaseHandle>) p_extIdHandleMap;
        }

        ConcurrentHashMap<String, BaseHandle> map = ((ThreadLocal<ConcurrentHashMap<String, BaseHandle>>) p_extIdHandleMap).get();
        if (map == null) {
            map = new ConcurrentHashMap<String, BaseHandle>();
            ((ThreadLocal<ConcurrentHashMap<String, BaseHandle>>) p_extIdHandleMap).set(map);
        }

        return map;
    }

    ConcurrentHashMap<Long, BaseHandleWrapper> get_p_elementHandleMap() {
        if (p_elementHandleMap instanceof ConcurrentHashMap) {
            return (ConcurrentHashMap<Long, BaseHandleWrapper>) p_elementHandleMap;
        }

        ConcurrentHashMap<Long, BaseHandleWrapper> map =
                ((ThreadLocal<ConcurrentHashMap<Long, BaseHandleWrapper>>) p_elementHandleMap).get();
        if (map == null) {
            map = new ConcurrentHashMap<Long, BaseHandleWrapper>();
            ((ThreadLocal) p_elementHandleMap).set(map);
        }

        return map;
    }

    ConcurrentHashMap<String, BaseHandle> get_extidmap_for_class(Class clazz) {
        ConcurrentHashMap<String, BaseHandle> map = null;
        if (ManagedObjectManager.isOn()) {
            if (isThreadLocal(clazz)) {
                //this is cache only (SN) and thread local
                map = ((ThreadLocal<ConcurrentHashMap<String, BaseHandle>>) p_extIdHandleMap).get();
                if (map == null) {
                    map = new ConcurrentHashMap<String, BaseHandle>();
                    ((ThreadLocal) p_extIdHandleMap).set(map);
                }
            } else {
                //this is a shared basehandle ( memory only )
                //look in the new map
                map = (ConcurrentHashMap<String, BaseHandle>) p_sharedExtIdHandleMap;
            }
        } else {
            //Not shared nothing
            map = (ConcurrentHashMap<String, BaseHandle>) p_extIdHandleMap;
        }

        return map;
    }

    BaseHandleWrapper get_basehandle_wrapper_from_element(long id) {
        ConcurrentHashMap<Long, BaseHandleWrapper> map = get_elementmap_for_class(id);
        if (map != null) {
            return map.get(id);
        }
        return null;
    }

    ConcurrentHashMap<Long, BaseHandleWrapper> get_elementmap_for_class(long id) {

        ConcurrentHashMap<Long, BaseHandleWrapper> map = null;
        //First find the type of the element
        if (ManagedObjectManager.isOn()) {
            int typeId = IDEncoder.decodeTypeId(id);
            if (!getCacheAgent().getCluster().getMetadataCache().isValidTypeId(typeId)) {
                return null;
            }
            Class elementClz = getCacheAgent().getCluster().getMetadataCache().getClass(typeId);

            if (isThreadLocal(elementClz)) {
                //this is cache only (SN) and thread local
                map = ((ThreadLocal<ConcurrentHashMap<Long, BaseHandleWrapper>>) p_elementHandleMap).get();
                if (map == null) {
                    map = new ConcurrentHashMap<Long, BaseHandleWrapper>();
                    ((ThreadLocal) p_elementHandleMap).set(map);
                }
            } else {
                //this is a shared element ( memory only )
                //look in the new map
                map = (ConcurrentHashMap<Long, BaseHandleWrapper>) p_sharedElementHandleMap;
            }
        } else {
            //Not shared nothing
            map = (ConcurrentHashMap<Long, BaseHandleWrapper>) p_elementHandleMap;
        }

        return map;
    }

    BaseHandleWrapper get_basehandle_wrapper_from_event(long id)  {
        ConcurrentHashMap<Long, BaseHandleWrapper> map = get_eventmap_for_class(id);
        if (map!=null) {
            return map.get(id);
        }
        return null;
    }

    ConcurrentHashMap<Long, BaseHandleWrapper>  get_eventmap_for_class(long id) {

        ConcurrentHashMap<Long, BaseHandleWrapper> map = null;
        //First find the type of the element
        if (ManagedObjectManager.isOn()) {
            int typeId = IDEncoder.decodeTypeId(id);
            if (!getCacheAgent().getCluster().getMetadataCache().isValidTypeId(typeId)) {
                return null;
            }
            Class elementClz = getCacheAgent().getCluster().getMetadataCache().getClass(typeId);
            if (isThreadLocal(elementClz)) {
                //this is cache only (SN) and threadlocal
                map = ((ThreadLocal<ConcurrentHashMap<Long, BaseHandleWrapper>>) p_eventHandleMap).get();
                if (map == null) {
                    map = new ConcurrentHashMap<Long, BaseHandleWrapper>();
                    ((ThreadLocal) p_eventHandleMap).set(map);
                }
            } else {
                //this is a shared element ( memory only )
                //look in the new map
                map = (ConcurrentHashMap<Long, BaseHandleWrapper>) p_sharedEventHandleMap;
            }
        } else {
            //Not shared nothing
            map = (ConcurrentHashMap<Long, BaseHandleWrapper>) p_eventHandleMap;
        }

        return map;
    }

    ConcurrentHashMap<Long, BaseHandleWrapper> get_elementmap_for_class(Class clazz) {

        ConcurrentHashMap<Long, BaseHandleWrapper> map = null;
        //First find the type of the element
        if (ManagedObjectManager.isOn()) {
            if (isThreadLocal(clazz)) {
                //this is cache only (SN) and thread local
                map = ((ThreadLocal<ConcurrentHashMap<Long, BaseHandleWrapper>>) p_elementHandleMap).get();
                if (map == null) {
                    map = new ConcurrentHashMap<Long, BaseHandleWrapper>();
                    ((ThreadLocal) p_elementHandleMap).set(map);
                }
            } else {
                //this is a shared element ( memory only )
                //look in the new map
                map = (ConcurrentHashMap<Long, BaseHandleWrapper>) p_sharedElementHandleMap;
            }
        } else {
            //Not shared nothing
            map = (ConcurrentHashMap<Long, BaseHandleWrapper>) p_elementHandleMap;
        }

        return map;
    }

    ConcurrentHashMap<Long, BaseHandleWrapper>  get_eventmap_for_class(Class clazz) {

        ConcurrentHashMap<Long, BaseHandleWrapper> map = null;
        //First find the type of the element
        if (ManagedObjectManager.isOn()) {
            if (isThreadLocal(clazz)) {
                //this is cache only (SN) and threadlocal
                map = ((ThreadLocal<ConcurrentHashMap<Long, BaseHandleWrapper>>) p_eventHandleMap).get();
                if (map == null) {
                    map = new ConcurrentHashMap<Long, BaseHandleWrapper>();
                    ((ThreadLocal) p_eventHandleMap).set(map);
                }
            } else {
                //this is a shared element ( memory only )
                //look in the new map
                map = (ConcurrentHashMap<Long, BaseHandleWrapper>) p_sharedEventHandleMap;
            }
        } else {
            //Not shared nothing
            map = (ConcurrentHashMap<Long, BaseHandleWrapper>) p_eventHandleMap;
        }

        return map;
    }

    //------------

    /**
     * Cache Types *
     */
    public final static int CACHE_TYPE_MASTER = 0;
    public final static int CACHE_TYPE_METADATA = 1;
    public final static int CACHE_TYPE_CONCEPT = 2;
    public final static int CACHE_TYPE_EVENT = 3;
    public final static int CACHE_TYPE_RECOVERY = 4;
    public final static int CACHE_TYPE_CACHEIDGENERATOR = 5;


    /**
     * End of Cache Types
     */


//    private HashMap m_entitySettings = new HashMap();
//    boolean hasCustomSettings=false;
//    boolean isActive=false;


    protected InferenceAgent mCacheAgent;
    boolean omInMemoryMode = false;

    public DefaultDistributedCacheBasedStore(CacheAgent agent) {
        super(agent.getAgentName());
        mCacheAgent = (InferenceAgent) agent;
        agentTimeManager = new AgentTimeManager();
    }


    @Override
    public void init() throws Exception {
        //mCacheAgent = (InferenceAgent) agent;
        m_session = (RuleSessionEx) mCacheAgent.getRuleSession();
        //p_eventHandleMap = new ConcurrentHashMap<Long, BaseHandleWrapper>(DEFAULT_INITIAL_CAPACITY);
        //p_elementHandleMap = new ConcurrentHashMap<Long, BaseHandleWrapper>(DEFAULT_INITIAL_CAPACITY);
        //p_extIdHandleMap = new ConcurrentHashMap<String, BaseHandle>(DEFAULT_INITIAL_CAPACITY);

        ExtIdMap extIdMap = null;
        if(Boolean.parseBoolean(System.getProperty("be.engine.kernel.unifiedExtIdMap", "false"))) {
            extIdMap = new ExtIdMap();
        }
        m_eventHandleMap = new EventHandleMap(m_workingMemory, extIdMap);
        m_elementHandleMap = new ElementHandleMap(m_workingMemory, extIdMap);
        m_entityHandleMap = new EntityHandleMap(m_workingMemory, extIdMap);
        m_objectHandleMap = new ObjectHandleMap(m_workingMemory);

        agentTimeManager.init(mCacheAgent);
        omInMemoryMode = m_session.getRuleServiceProvider().getProperties().getProperty("be.objectmanager.inmemorymode", "false").equalsIgnoreCase("true");
        logger = m_session.getRuleServiceProvider().getLogger(DistributedCacheBasedStore.class);

        //Suresh moved it to InferenceAgent, along with ScheduleWork method.
        /**
        jobThreadPool = WorkManagerFactory.create(mCacheAgent.getCluster().getClusterName(),
                mCacheAgent.getAgentName(), mCacheAgent.getAgentId(), "AdhocWorkManager",
                1, m_session.getRuleServiceProvider());

        jobThreadPool.start();
        */

        p_eventHandleMap = ManagedObjectManager.isOn()
                ? new ThreadLocal<ConcurrentHashMap<Long, BaseHandleWrapper>>()
                : new ConcurrentHashMap<Long, BaseHandleWrapper>();

        p_elementHandleMap = ManagedObjectManager.isOn()
                ? new ThreadLocal<ConcurrentHashMap<Long, BaseHandleWrapper>>()
                : new ConcurrentHashMap<Long, BaseHandleWrapper>();

        p_extIdHandleMap = ManagedObjectManager.isOn()
                ? new ThreadLocal<ConcurrentHashMap<String, BaseHandle>>()
                : new ConcurrentHashMap<String, BaseHandle>();

        if (ManagedObjectManager.isOn()) {
            //init the shared handle maps
            p_sharedEventHandleMap = new ConcurrentHashMap<Long, BaseHandleWrapper>();
            p_sharedElementHandleMap = new ConcurrentHashMap<Long, BaseHandleWrapper>();
            p_sharedExtIdHandleMap = new ConcurrentHashMap<String, BaseHandle>();
        }
    }

    @Override
    public void start() throws Exception {
    }

    @Override
    public void stop() throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void shutdown() throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public InferenceAgent getInferenceAgent() {
        return mCacheAgent;
    }

    public boolean isObjectStore() {
        return true;
    }

    public LockManager getLockManager() {
        return mCacheAgent.getCluster().getLockManager();
    }

    public SequenceManager getSequenceManager() {
        return mCacheAgent.getCluster().getSequenceManager();
    }

    /**
     * @param id
     * @param entityClass
     * @throws Exception
     */
    public void refreshEntity(long id, Class entityClass, int version) throws Exception {
        mCacheAgent.refreshEntity(id, entityClass, version);
    }

    public void refreshEntity(long id, int typeId, int version) throws Exception {
        mCacheAgent.refreshEntity(id, typeId, version);
    }

    /**
     * @param id
     * @param extId
     * @param type
     * @return
     * @throws DuplicateExtIdException
     * @throws DuplicateException
     */
//    public BaseHandle loadEvictedElementHandle(long id, String extId, Class type) throws DuplicateExtIdException, DuplicateException {
//        BaseHandle h = m_elementHandleMap.add(id, extId, type);
//        ((CacheHandle) h).removeRef();
//        h.evict();
//        return h;
//    }

    /**
     * @param id
     * @param extId
     * @param type
     * @return
     * @throws DuplicateExtIdException
     * @throws DuplicateException
     */
    public BaseHandle loadEvictedEventHandle(long id, String extId, Class type) throws DuplicateExtIdException, DuplicateException {
        BaseHandle h = m_eventHandleMap.add(id, extId, type);
        ((CacheHandle) h).removeRef();
        h.evict();
        return h;
    }

    public RuleSession getRuleSession() {
        return m_session;
    }

    /**
     * @param entityURI
     * @return
     * @throws Exception
     */
    public String getEntityCacheName(String entityURI) throws Exception {
        return mCacheAgent.getEntityCacheName(entityURI);
    }

    /**
     * @param entityURI
     * @return
     * @throws Exception
     */
    public EntityDao getEntityDao(String entityURI) throws Exception {
        return mCacheAgent.getEntityDao(entityURI);
    }

    public RtcTransactionProperties getRtcTransactionProperties() {
        return mCacheAgent.getRtcTransactionProperties();
    }

    /**
     * @param IDs
     * @param provider
     * @return
     */
    public Collection loadObjects(Set IDs, EntityDao provider) throws Exception {
        return mCacheAgent.loadObjects(IDs, provider);
    }

    protected boolean isInRTCCycle() {
        WorkingMemoryImpl wm = (WorkingMemoryImpl)  m_session.getWorkingMemory();
        return WorkingMemoryImpl.executingInside(wm);
    }

    /**
     * @return
     * @throws Exception
     */
    public CacheStore.CacheIdGenerator getCacheIdGenerator() throws Exception {
        if (m_cacheIdGenerator != null) {
            return m_cacheIdGenerator;
        }
        if (customCacheStoreFactory != null) {
            m_cacheIdGenerator = customCacheStoreFactory.getCacheIdGenerator(this.getRuleSession());
            return m_cacheIdGenerator;
        }
        return null;
    }


    public CacheStoreFactory getCustomCacheStoreFactory() {
        return customCacheStoreFactory;
    }

    public void flushCaches() throws Exception {
        mCacheAgent.flushCaches();
    }

    public synchronized void registerType(TypeManager.TypeDescriptor entityType) {
    }


    public synchronized void registerType(Class entityClass) {
    }


    public synchronized void init(WorkingMemory workingMemory) throws Exception {
        m_workingMemory = (WorkingMemoryImpl) workingMemory;
        //super.init(workingMemory);
    }


    public Element getNamedInstance(String uri, Class entityClz) {
        return mCacheAgent.getNamedInstance(uri, entityClz);
    }

    public void addInstance(Concept cept) {
        mCacheAgent.addInstance(cept);
    }

    public void createElement(Element element) throws DuplicateExtIdException {
        try {
            mCacheAgent.createElement(element);
        } catch (DuplicateExtIdException die) {
            throw die;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    public void createEvent(Event event) throws DuplicateExtIdException {
        try {
            mCacheAgent.createEvent(event);
        } catch (DuplicateExtIdException die) {
            throw die;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    public void deleteEntity(Handle handle) {
        try {
            mCacheAgent.deleteEntity(handle);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    public void removeInstance(Concept cept) {
        System.out.println("removeInstancet(Concept cept) Not Supported");
    }

    public void addEvent(Event evt) {
        System.out.println("addEvent(Event e) Not Supported");
    }

    public void removeEvent(Event e) {
        System.out.println("removeEvent(Event e) Not Supported");
    }


    protected AbstractElementHandle getNewElementExtHandle(com.tibco.cep.kernel.model.entity.Element obj, AbstractElementHandle _next, TypeInfo _typeInfo) {
        throw new UnsupportedOperationException("getNewElementExtHandle should not be used");
    }

    protected AbstractElementHandle getNewElementHandle(com.tibco.cep.kernel.model.entity.Element obj, AbstractElementHandle _next, TypeInfo _typeInfo) {
        throw new UnsupportedOperationException("getNewElementHandle should not be used");
    }

    public AbstractElementHandle getNewElementExtHandle(com.tibco.cep.kernel.model.entity.Element obj, AbstractElementHandle _next, boolean forceSet) {
        if (obj instanceof StateMachineConceptImpl)
            return new CacheStateMachineConceptExHandle((WorkingMemoryImpl) m_session.getWorkingMemory(), obj, forceSet);
        else return new CacheConceptExHandle((WorkingMemoryImpl) m_session.getWorkingMemory(), obj, forceSet);
    }

    public AbstractElementHandle getNewElementHandle(com.tibco.cep.kernel.model.entity.Element obj, AbstractElementHandle _next, boolean forceSet) {
        if (obj instanceof StateMachineConceptImpl)
            return new CacheStateMachineConceptHandle((WorkingMemoryImpl) m_session.getWorkingMemory(), obj, forceSet);
        else return new CacheConceptHandle((WorkingMemoryImpl) m_session.getWorkingMemory(), obj, forceSet);
    }

    public void reset() {
        super.reset();
        get_p_elementHandleMap().clear();
        get_p_eventHandleMap().clear();
        get_p_extIdHandleMap().clear();

        if (ManagedObjectManager.isOn()) {
            //clear the shared maps holding mem-only data
            ((ConcurrentHashMap<String, BaseHandle>) p_sharedExtIdHandleMap).clear();
            ((ConcurrentHashMap<Long, BaseHandleWrapper>) p_sharedElementHandleMap).clear();
            ((ConcurrentHashMap<Long, BaseHandleWrapper>) p_sharedEventHandleMap).clear();
        }
    }

    //this is for shared nothing.
    // The BETransactionHook will call this method to clear the thread local maps
    public void resetThreadLocalMaps() {
        super.reset();
        if (ManagedObjectManager.isOn()) {
            get_p_elementHandleMap().clear();
            get_p_eventHandleMap().clear();
            get_p_extIdHandleMap().clear();
        }
    }

    /**
     * Overridden handle methods from BaseObjectManager *
     */

    public BaseHandle getElementHandle(long id) {
        BaseHandleWrapper wrap = get_basehandle_wrapper_from_element(id);
        return wrap == null ? null : wrap.getBaseHandle();
    }

    public BaseHandle getElementHandle(String extId,Class elementClass) {
        if (extId != null && extId.length() > 0) {
            BaseHandle ret = get_extidmap_for_class(elementClass).get(extId);
            if(ret instanceof AbstractElementHandle) return ret;
        }
        return null;
    }

    public BaseHandle getEventHandle(long id) {
        BaseHandleWrapper wrap = get_basehandle_wrapper_from_event(id);
        return wrap == null ? null : wrap.getBaseHandle();
    }

    public BaseHandle getEventHandle(String extId,Class eventClass) {
         if (extId != null && extId.length() > 0) {
             BaseHandle ret = get_extidmap_for_class(eventClass).get(extId);
             if(ret instanceof AbstractEventHandle) return ret;
         }
         return null;
    }

    public int numOfElement() {
        int size = get_p_elementHandleMap().size();
        if (ManagedObjectManager.isOn()) {
            size += ((ConcurrentHashMap<Long, BaseHandleWrapper>)p_sharedElementHandleMap).size();
        }
        return size;
    }

    public int numOfEvent() {
        int size = get_p_eventHandleMap().size();
        if (ManagedObjectManager.isOn()) {
            size += ((ConcurrentHashMap<Long, BaseHandleWrapper>)p_sharedEventHandleMap).size();
        }
        return size;
    }

    public List getObjects() {
        List retList = new LinkedList();
        Enumeration<BaseHandleWrapper> ite = get_p_elementHandleMap().elements();
        while (ite.hasMoreElements()) {
            BaseHandleWrapper wrapper = ite.nextElement();
            Object o = wrapper.getBaseHandle().getObject();
            if (o != null)
                retList.add(o);
        }
        ite = get_p_eventHandleMap().elements();
        while (ite.hasMoreElements()) {
            BaseHandleWrapper wrapper = ite.nextElement();
            Object o = wrapper.getBaseHandle().getObject();
            if (o != null)
                retList.add(o);
        }

        //Also add the shared maps ( SN mode )
        if (ManagedObjectManager.isOn()) {
            //sharedelementmap
            ite = ((ConcurrentHashMap<Long, BaseHandleWrapper>)p_sharedElementHandleMap).elements();
            while (ite.hasMoreElements()) {
                BaseHandleWrapper wrapper = ite.nextElement();
                Object o = wrapper.getBaseHandle().getObject();
                if (o != null)
                    retList.add(o);
            }

            //sharedeventmap
            ite = ((ConcurrentHashMap<Long, BaseHandleWrapper>)p_sharedEventHandleMap).elements();
            while (ite.hasMoreElements()) {
                BaseHandleWrapper wrapper = ite.nextElement();
                Object o = wrapper.getBaseHandle().getObject();
                if (o != null)
                    retList.add(o);
            }
        }
        //
        return retList;
    }


    public List getObjects(Filter filter) {
        if (filter == null) return getObjects();
        List retList = new LinkedList();
        Enumeration<BaseHandleWrapper> ite = get_p_elementHandleMap().elements();
        while (ite.hasMoreElements()) {
            BaseHandleWrapper wrapper = ite.nextElement();
            Object o = wrapper.getBaseHandle().getObject();
            if ((o != null) && (filter.evaluate(o)))
                retList.add(o);
        }
        ite = get_p_eventHandleMap().elements();
        while (ite.hasMoreElements()) {
            BaseHandleWrapper wrapper = ite.nextElement();
            Object o = wrapper.getBaseHandle().getObject();
            if ((o != null) && (filter.evaluate(o)))
                retList.add(o);
        }

        //Also add the shared maps ( SN mode )
        if (ManagedObjectManager.isOn()) {
            //sharedelementmap
            ite = ((ConcurrentHashMap<Long, BaseHandleWrapper>)p_sharedElementHandleMap).elements();
            while (ite.hasMoreElements()) {
                BaseHandleWrapper wrapper = ite.nextElement();
                Object o = wrapper.getBaseHandle().getObject();
                if ((o != null) && (filter.evaluate(o)))
                    retList.add(o);
            }

            //sharedeventmap
            ite = ((ConcurrentHashMap<Long, BaseHandleWrapper>)p_sharedEventHandleMap).elements();
            while (ite.hasMoreElements()) {
                BaseHandleWrapper wrapper = ite.nextElement();
                Object o = wrapper.getBaseHandle().getObject();
                if ((o != null) && (filter.evaluate(o)))
                    retList.add(o);
            }
        }
        //
        return retList;
    }

    public BaseTimeManager getTimeManager() {
        return agentTimeManager;
    }

    public List getHandles() {
        List retList = new LinkedList();
        retList.addAll(super.getHandles());
        retList.addAll(get_p_eventHandleMap().values());
        retList.addAll(get_p_elementHandleMap().values());

        //Also add the other maps that were created for SN
        if (ManagedObjectManager.isOn()) {
            retList.addAll(((ConcurrentHashMap<Long, BaseHandleWrapper>)p_sharedEventHandleMap).values());
            retList.addAll(((ConcurrentHashMap<Long, BaseHandleWrapper>)p_sharedElementHandleMap).values());
        }
        return retList;
    }

    public List getTimerFiredEventHandles() {
        List retList = new LinkedList();
        Iterator ite = getEventHandleIterator();
        while (ite.hasNext()) {
            AbstractEventHandle h = (AbstractEventHandle) ((BaseHandleWrapper)ite.next()).getBaseHandle();
            if (h.isTimerFired())
                retList.add(h);
        }
        return retList;
    }

    private void _removeExtIdEntry(String extId, BaseHandle h) {
        if ((extId != null) && (extId.length() > 0)) {
        	if(h == null) {
        		get_p_extIdHandleMap().remove(extId);
        	} else {
                get_extidmap_for_class(h.getTypeInfo().getType()).remove(extId,h);
        	}
//        	} else if(!get_p_extIdHandleMap().remove(extId, h) && logger.isEnabledFor(Level.DEBUG)) {
//                //this happens when _in the same rule_ an object is deleted and then a new object with the same extId is created.
//                logger.log(Level.DEBUG, "_removeExtIdEntry handle to remove did not match stored handle for extId %s handle to delete: %s", extId, h.printInfo());
//            }
        }
    }

    protected void _addExtIdEntry(String extId, BaseHandle h) throws DuplicateExtIdException {
        BaseHandle h1 = get_extidmap_for_class(h.getTypeInfo().getType()).putIfAbsent(extId,h);

        //h1 != null means the old entry in the map was not replaced
        if (h1 != null) {
            if (h1 != h && !h1.isRetracted_OR_isMarkedDelete()) {
                throw new DuplicateExtIdException("Attempt to assert duplicate extIds " + h.printInfo() + ", existing entry " + h1.printInfo());
            }
            if(logger.isEnabledFor(Level.DEBUG)) {
                logger.log(Level.DEBUG, "_addExtIdEntry: replacing existing handle retracted or marked delete %s new handle %s", h1.printInfo(), h.printInfo());
            }
            //if the previous handle is deleted then replace it
            get_extidmap_for_class(h.getTypeInfo().getType()).put(extId,h);
        }
    }

    private BaseHandle getExtAddEventHandle(Event event, boolean forceSet) throws DuplicateExtIdException {
        if (!event.isLoadedFromCache())
            createEvent(event);

        return new BaseHandleInitWrapper().initExtEventHandle(event, forceSet, this);
    }

    private BaseHandle getSimpleAddEventHandle(Event event, boolean forceSet) {
        return new BaseHandleInitWrapper().initSimpleEventHandle(event, forceSet, this);
    }

    private BaseHandle getExtAddElementHandle(final Element element, final boolean forceSet) throws DuplicateExtIdException {
        if (!element.isLoadedFromCache())
            createElement(element);

        return new BaseHandleInitWrapper().initExtElementHandle(element, forceSet, this);
    }

    private BaseHandle getSimpleAddElementHandle(final Element element, final boolean forceSet) throws DuplicateExtIdException {
        return new BaseHandleInitWrapper().initSimpleElementHandle(element, forceSet, this);
    }

    public BaseHandle getAddHandle(Object object) throws DuplicateExtIdException {
        return getAddHandle(object, false);
    }

    private BaseHandle getAddHandle(Object object, boolean forceSet) throws DuplicateExtIdException {
        if(object instanceof Event) {
            return getAddEventHandle((Event)object, forceSet);
        } else if(object instanceof Element) {
            return getAddElementHandle((Element)object, forceSet);
        } else {
            return super.getAddHandle(object);
        }
    }

    public boolean handleExists(Object object) {
        if (object instanceof Concept)      {
            return get_elementmap_for_class(((Concept)object).getClass()).containsKey(( (Concept) object).getId());
        }
        else if (object instanceof Event) {
            return get_eventmap_for_class(((Event)object).getClass()).containsKey(( (Event) object).getId());
        } else
            throw new RuntimeException("Unknown Type " + object);
    }

    public BaseHandle createHandle(Object object) throws DuplicateExtIdException {
        return getAddHandle(object, true);
    }

    public BaseHandle getHandle(Object object) {

        if (object instanceof Entity) {
            Entity en = (Entity) object;
            if (en.isLoadedFromCache()) {
                try {
                    return getAddHandle(object);
                } catch (DuplicateExtIdException dex) {
                    logger.log(Level.WARN, dex, "DuplicateExtId in getHandle %s", en);
                    // Should never happen for objects loaded from the cache
                    return null;
                }
            }
        }

        if (object instanceof Event) {
            BaseHandleWrapper wrap = get_eventmap_for_class(((Event)object).getClass()).get(((Event) object).getId());
            return wrap == null ? null : wrap.getBaseHandle();
        } else if (object instanceof Element) {
            BaseHandleWrapper wrap = get_elementmap_for_class(((Element)object).getClass()).get(((Element) object).getId());
            return wrap == null ? null : wrap.getBaseHandle();
        } else {
            return super.getHandle(object);
        }
    }


    // protected methods -  called by the WorkingMemory.  should be called in a synchronized workingMemory!
    protected Iterator getNamedInstanceIterator() {  //should be used only in hot deploy
        return new _NamedInstanceIterator();
    }

    protected Iterator getEventHandleIterator() {   //should be used only in hot deploy
        return get_p_eventHandleMap().values().iterator();
    }

    protected Iterator getElementHandleIterator() {  //should be used only in hot deploy
        return get_p_elementHandleMap().values().iterator();
    }

    protected BaseHandle addHandle(Object object) throws DuplicateExtIdException, DuplicateException {
        return getAddHandle(object);
    }

    protected AbstractElementHandle getElementHandle(Element element) {
        try {
            if (element.isLoadedFromCache()) {
                return getAddElementHandle(element);
            } else {
                BaseHandleWrapper wrap = get_elementmap_for_class(element.getClass()).get(element.getId());
                return (AbstractElementHandle) (wrap == null ? null : wrap.getBaseHandle());
            }
        } catch (DuplicateExtIdException dex) {
            return null;
        }
    }

    protected AbstractEventHandle getEventHandle(Event event) {
        try {
            if (event.isLoadedFromCache()) {
                return getAddEventHandle(event);
            } else {
                BaseHandleWrapper wrap = get_eventmap_for_class(event.getClass()).get(event.getId());
                return (AbstractEventHandle) (wrap == null ? null : wrap.getBaseHandle());
            }
        } catch (DuplicateExtIdException dex) {
            return null;
        }
    }


    public AbstractElementHandle getAddElementHandle(Element element) throws DuplicateExtIdException {
        return getAddElementHandle(element, false);
    }

    public AbstractEventHandle getAddEventHandle(Event event) throws DuplicateExtIdException {
        return getAddEventHandle(event, false);
    }

    public AbstractElementHandle getAddElementHandle(Element element, boolean forceSet) throws DuplicateExtIdException {
        if ((element.getExtId() != null) && (element.getExtId().length() > 0)) {
            return (AbstractElementHandle) getExtAddElementHandle(element, forceSet);
        } else {
            return (AbstractElementHandle) getSimpleAddElementHandle(element, forceSet);
        }
    }

    public AbstractEventHandle getAddEventHandle(Event event, boolean forceSet) throws DuplicateExtIdException {
        if ((event.getExtId() != null) && (event.getExtId().length() > 0)) {
            return (AbstractEventHandle) getExtAddEventHandle(event, forceSet);
        } else {
            return (AbstractEventHandle) getSimpleAddEventHandle(event, forceSet);
        }
    }

//    protected BaseHandle removeHandle(Object object) {
//        if(object instanceof Event) {
//            Event event= (Event) object;
//            BaseHandleWrapper wrap =get_p_eventHandleMap().remove(event.getId());
//            if(wrap != null) {
//                BaseHandle h = wrap.getBaseHandle();
//                wrap = null;
//                if (h != null) {
//                    if ((event.getExtId() != null) && (event.getExtId().length() > 0)) {
//                        _removeExtIdEntry(event, h);
//                    }
//                    deleteEntity(h);
//                    h.setRetracted();
//                    return h;
//                }
//            }
//        }
//        else if(object instanceof Element) {
//            Element element= (Element) object;
//            BaseHandleWrapper wrap = get_p_elementHandleMap().remove(element.getId());
//            if(wrap != null) {
//                BaseHandle h= wrap.getBaseHandle();
//                wrap = null;
//                if (h != null) {
//                    if ((element.getExtId() != null)&& (element.getExtId().length() > 0)) {
//                        _removeExtIdEntry(element, h);
//                    }
//                    deleteEntity(h);
//                    h.setRetracted();
//                    return h;
//                }
//            }
//        }
//        else {
//            return super.removeHandle(object);
//        }
//        return null;
//    }

    @Override
    public void removeHandleForCacheOnly(Handle handle, Object ref) {
    	if(ref != null) {
	        if(ref instanceof Event) {
	            Event event= (Event) ref;
	            //add to id map before extId map and remove from id map after extId map
	            //to avoid another thread adding to id map and then seeing old entry still in extId map 
	            _removeExtIdEntry(event.getExtId(), (BaseHandle) handle);
	            get_eventmap_for_class(event.getClass()).remove(event.getId(), handle);
	        }
	        else if(ref instanceof Element) {
	            Element element= (Element) ref;
	            _removeExtIdEntry(element.getExtId(), (BaseHandle) handle);
	            get_elementmap_for_class(element.getClass()).remove(element.getId(), handle);
	        }
	        else if(ref instanceof Entity) {
	            removeEntityHandle(((Entity) ref).getId());
	        } else {
	            removeObjectHandle(ref);
	        }
		} else {
			if(handle instanceof ExtIdHandle) {
				_removeExtIdEntry(((ExtIdHandle)handle).getExtId(), ((ExtIdHandle) handle).getBaseHandle());
			}
			if(handle instanceof CacheHandle) {
	    		if(handle instanceof AbstractEventHandle) {
	    			get_eventmap_for_class(handle.getTypeInfo().getType())
	    					.remove(((CacheHandle)handle).getId());
	    		} else if(handle instanceof AbstractElementHandle) {
	    			get_elementmap_for_class(handle.getTypeInfo().getType())
					.remove(((CacheHandle)handle).getId());
	    		}
			}
		}
    }

//    protected BaseHandle cleanupHandle(Object object) {
//        return removeHandleForCacheOnly(null, object);
//    }

    @Override
    protected void removeElementHandle(AbstractElementHandle handle) {
        if (handle instanceof ExtIdHandle) {
            _removeExtIdEntry(((ExtIdHandle)handle).getExtId(), handle);
        }
        BaseHandleWrapper ret = get_elementmap_for_class(handle.getTypeInfo().getType()).remove(handle.getElementId());

        //previous implementation took only the id as the argument so it would have
        //marked what was in the map instead of the handle that was passed in.
        if(ret != null) {
            BaseHandle h = ret.getBaseHandle();
            h.setRetracted();
            deleteEntity(h);
        }
    }

    @Override
    protected void removeEventHandle(AbstractEventHandle handle) {
        if (handle instanceof ExtIdHandle) {
            _removeExtIdEntry(((ExtIdHandle)handle).getExtId(), handle);
        }
        BaseHandleWrapper ret = get_eventmap_for_class(handle.getTypeInfo().getType()).remove(handle.getEventId());

        //previous implementation took only the id as the argument so it would have
        //marked what was in the map instead of the handle that was passed in.
        if(ret != null) {
            BaseHandle h = ret.getBaseHandle();
            h.setRetracted();
            deleteEntity(h);
        }
    }


    /**
     * End of overridden methods
     */
    protected AbstractEventHandle getNewEventExtHandle(com.tibco.cep.kernel.model.entity.Event obj, AbstractEventHandle _next, TypeInfo _typeInfo) {
        throw new UnsupportedOperationException("getNewEventExtHandle Not Supported");
    }

    protected AbstractEventHandle getNewEventHandle(com.tibco.cep.kernel.model.entity.Event obj, AbstractEventHandle _next, TypeInfo _typeInfo) {
        throw new UnsupportedOperationException("getNewEventHandle Not Supported");
    }

//    protected AbstractElementHandle getRemoteNewElementExtHandle(String clusterName, Element obj, AbstractElementHandle _next) {
//        throw new UnsupportedOperationException("getNewElementHandle Not Supported");
//    }
//
//    protected AbstractElementHandle getRemoteNewElementHandle(String clusterName, Element obj, AbstractElementHandle _next) {
//        return new RemoteCacheConceptHandle(clusterName, (WorkingMemoryImpl) m_session.getWorkingMemory(), obj, _next);
//    }

    protected AbstractElementHandle getRemoteNewElementHandle(String clusterName, Element obj, AbstractElementHandle _next) {
        return new RemoteCacheConceptHandle(clusterName, (WorkingMemoryImpl) m_session.getWorkingMemory(), obj);
    }

    public AbstractEventHandle getNewEventExtHandle(com.tibco.cep.kernel.model.entity.Event obj, AbstractEventHandle _next, boolean forceSet) {
        return new CacheEventExHandle((WorkingMemoryImpl) m_session.getWorkingMemory(), obj, forceSet);
    }

    public AbstractEventHandle getNewEventHandle(com.tibco.cep.kernel.model.entity.Event obj, AbstractEventHandle _next, boolean forceSet) {
        return new CacheEventHandle((WorkingMemoryImpl) m_session.getWorkingMemory(), obj, _next, forceSet);
    }

    public void applyChanges(RtcOperationList rtcList) {
        try {
            mCacheAgent.applyChanges(rtcList, true, true);
        } catch (Exception ex) {
            throw handleException(ex, 0).rtex;
        }
    }

    public boolean isCacheServer() {
        return ((RuleServiceProviderImpl) m_session.getRuleServiceProvider()).isCacheServerMode();
    }

    public void recover() throws OMException {
    }

    public Entity getEntity(String extId) {
        BaseHandle chandle = getEntityHandle(extId);
        if ((chandle == null) || (chandle.getObject() == null)) {
            Entity en = getElementFromStore(extId);
            if (isInRTCCycle()) {
                if (en instanceof Element) {
                    try {
                        CacheHandle h = (CacheHandle) this.getAddElementHandle((Element) en, true);
                        en = (Entity) h.getObject();
                        //clearRef(h);
                    } catch (Exception ex) {
                        throw handleException(ex, 0).rtex;
                    }
                    return en;
                } else if (en instanceof Event) {
                    try {
                        CacheHandle h = (CacheHandle) this.getAddEventHandle((Event) en, true);
                        en = (Entity) h.getObject();
                        //clearRef(h);
                    } catch (DuplicateExtIdException dex) {
                        dex.printStackTrace();
                    }
                }
            }
            return en;
        } else {
            if (!chandle.isRetracted_OR_isMarkedDelete())
                return (Entity) chandle.getObject();
            else
                return null;
        }
    }

    public Element getElement(String extId) {
        return getElement(extId, null);
    }

    //don't check cache for mem only mode
    private boolean localOnly(Class entityClass) {
    	if(entityClass != null) {
    		// is for BPMN the entityClass is JobContext.class which does not have a DaoConfig
    		// and this function is for performance reasons only and does not impact other use cases.
    		EntityDaoConfig daoConfig = getCacheAgent().getCluster().getMetadataCache().getEntityDaoConfig(entityClass);
    		if(daoConfig != null) {
    			return daoConfig.getCacheMode() == EntityDaoConfig.CacheMode.Memory;
    		}
    	}
    	return false;
    }

    private boolean isThreadLocal(Class entityClass) {
        return entityClass != null && getCacheAgent().getCluster().getMetadataCache().getEntityDaoConfig(entityClass).getCacheMode() == EntityDaoConfig.CacheMode.Cache;
    }

    private boolean localOnly(Class entityClass, boolean overrideIfNull) {
        return entityClass == null && overrideIfNull || localOnly(entityClass);
    }

//    public Element getRemoteElement(String clusterName,String extId) {
//        BaseHandle chandle = getRemoteElementHandle(clusterName,extId);
//        if(chandle == null) {
//            Entity en= getRemoteElementFromStore(clusterName,extId);
//            if (en instanceof Element) {
//                try {
//                    CacheHandle h = (CacheHandle) this.getRemoteAddElementHandle(clusterName, (Element) en);
//                    en = (Entity) h.getObject();
//                    // clearRef(h);
//                } catch (Exception ex) {
//                    throw new RuntimeException(ex);
//                }
//                return (Element) en;
//            }
//            return null;
//        } else {
//            if (!chandle.isRetracted_OR_isMarkedDelete())
//                return (Element) chandle.getObject();
//            else
//                return null;
//        }
//    }

    public Event getEvent(String extId)  {
        return getEvent(extId, null);
    }

//    public Element getElementById(long id, Class entityClz, boolean returnDeleted) throws Exception {
//        BaseHandle d = getElementHandle(id);
//        if (d != null) {
//            Element entity = (Element) mCacheAgent.getEntityById(id, entityClz);
//            if (entity != null) {
//                d = getAddElementHandle(entity);
//                clearRef((CacheHandle) d);
//            }
//            return entity;
//        } else {
//            if (!returnDeleted) {
//                if (d.isRetracted_OR_isMarkedDelete()) {
//                    return null;
//                }
//            }
//            return (Element) d.getObject();
//        }
//    }

//    public Event getEventById(long id, Class entityClz, boolean returnDeleted) throws Exception {
//        BaseHandle d = getEventHandle(id);
//        if (d != null) {
//            Event entity = (Event) mCacheAgent.getEntityById(id, entityClz);
//            if (entity != null) {
//                d = getAddEventHandle(entity);
//                clearRef((CacheHandle) d);
//            }
//            return entity;
//        } else {
//            if (!returnDeleted) {
//                if (d.isRetracted_OR_isMarkedDelete()) {
//                    return null;
//                }
//            }
//            return (Event) d.getObject();
//        }
//    }

    public Event getEvent(long id) {
        return getEvent(id, null, false);
    }

    public Event getEvent(long id, Class eventClz, boolean eventClzIsAccurate) {
        BaseHandle h = getEventHandle(id);
        if (h == null || h.getObject() == null) {  //handle not found
            Event evt = null;
        	//get it from id, if possible..
            if ((eventClz == null || !eventClzIsAccurate)
            		&& !getCacheAgent().getCluster().getClusterConfig().useObjectTable()) 
            {
                int typeId = IDEncoder.decodeTypeId(id);
                if (!getCacheAgent().getCluster().getMetadataCache().isValidTypeId(typeId)) {
                    return null;
                }
                eventClz = getCacheAgent().getCluster().getMetadataCache().getClass(typeId);
                if(eventClz != null) eventClzIsAccurate = true;
            }
            try {
                if (eventClz != null && eventClzIsAccurate) {
                    if(!localOnly(eventClz)) evt = (Event) mCacheAgent.getEntityById(id, eventClz);
                } else {
                    evt = (Event) mCacheAgent.getEntityById(id);
                }

                return checkPreProcCheckRtcEvent(id, evt);
            } catch (Exception ex) {
                throw handleException(ex, 0).rtex;
            }
        } else {
            if (!h.isRetracted_OR_isMarkedDelete()) {
                return (Event) h.getObject();
            } else
                return null;
        }
    }

//    public boolean isDeleted(Object object) {
//        try {
//            if (object instanceof Entity) {
//                return mCacheAgent.isDeleted((Entity) object);
//            }
//        } catch (Exception ex) {
//            throw handleException(ex, 0).rtex;
//        }
//        return false;
//    }

    public boolean isLenient() {
        return mCacheAgent.getAgentConfig().isLenient();
    }

//    protected void clearRef(CacheHandle cacheHandle) {
//        if (!isInRTCCycle())
//            cacheHandle.removeRef();
//        else {
//            WorkingMemoryImpl wm = (WorkingMemoryImpl) m_session.getWorkingMemory();
//            cacheHandle.touch(wm);
//        }
//    }

//    protected void clearRef(CacheHandle cacheHandle, Entity en) {
//        if (!isInRTCCycle())
//            cacheHandle.removeRef();
//        else {
//            cacheHandle.setRef(en);
//            WorkingMemoryImpl wm = (WorkingMemoryImpl) ((RuleSessionEx) m_session).getWorkingMemory();
//            cacheHandle.touch(wm);
//        }
//    }

    public Element getElement(long id) {
        return getElement(id, false);
    }
    public Element getElement(long id, boolean ignoreRetractedOrMarkedDelete) {
        return getElement(id, null, false, ignoreRetractedOrMarkedDelete);
    }

//    public Element getRemoteElement(String clusterName,long id) {
//        BaseHandle h = getElementHandle(id);
//        if(h == null) {  //handle not found
//            try {
//                Element element= getRemoteElementFromStore(clusterName,id);
//                if (element != null) {
//                        try {
//                            CacheHandle h1= (CacheHandle) this.getAddElementHandle((Element) element);
//                            element = (Element) h1.getObject();
//                            clearRef(h1);
//                        } catch (Exception ex) {
//                            throw new RuntimeException(ex);
//                        }
//                    return element;
//                } else {
//                    return null;
//                }
//            } catch (Exception ex) {
//                throw new RuntimeException(ex);
//            }
//        } else {
//            if (!h.isRetracted_OR_isMarkedDelete())
//                return (Element) h.getObject();
//            else
//                return null;
//        }
//    }

//    public Entity getElementFromStore(Class entityClass, String extId) {
//        try {
//            return mCacheAgent.getEntityIndexedByExtId(extId, entityClass);
//        }
//        catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }

    public Entity getElementFromStore(String extId) {
        Entity entity = null;
        if (ManagedObjectManager.isOn()) {
            entity = (Element) ManagedObjectManager.fetchByExtId(extId, null, ManagedObjectLockType.READLOCK);
            //BE-26436: When SN, if entity was deleted, return null
            if(entity != null && mCacheAgent.isHandleDeleted(entity.getId())) {
            	return null;
            }
        }
        else {
        	int retries = 0;
        	while (true) {
	            try {
	                return (Entity) mCacheAgent.getElementByExtId(extId);
	            } catch (Exception ex) {
	                ExceptionWrapper result = handleException(ex, retries);
	                if (result.succeeded) {
	                	retries = result.retries;
	                	continue;
	                } else {
	                	throw result.rtex;
	                }
	            }
        	}
        }
        return entity;
    }
    
    public Entity getElementFromStore(String extId, Class entityClass) {
        Entity entity = null;
        if (ManagedObjectManager.isOn()) {
            entity = (Entity) ManagedObjectManager.fetchByExtId(extId, entityClass, ManagedObjectLockType.READLOCK);
            //BE-26436: When SN, if entity was deleted, return null
            if(entity != null && mCacheAgent.isHandleDeleted(entity.getId())) {
            	return null;
            }
        }
        else {
        	int retries = 0;
        	while (true) {
	            try {
	                return (Entity) mCacheAgent.getElementByUri(extId, entityClass);
	            } catch (Exception ex) {
	                ExceptionWrapper result = handleException(ex, retries);
	                if (result.succeeded) {
	                	retries = result.retries;
	                	continue;
	                } else {
	                	throw result.rtex;
	                }
	            }
        	}
        }
        return entity;
    }

//    public Entity getRemoteElementFromStore(String clusterName, String extId) {
//        try {
//            return (Entity) mCacheAgent.getRemoteElementByExtId(clusterName, extId);
//        } catch (Exception ex) {
//            throw new RuntimeException(ex);
//        }
//    }

    public Element getElementFromStore(long id) {
    	int retries = 0;
    	while (true) {
	        try {
	            return (Element) mCacheAgent.getEntityById(id);
	        } catch (Exception ex) {
                ExceptionWrapper result = handleException(ex, retries);
                if (result.succeeded) {
                	retries = result.retries;
                	continue;
                } else {
                	throw result.rtex;
                }
	        }
    	}
    }

    public Element getElementFromStore(long id, Class entityClz) {
        Element entity = null;
        if (ManagedObjectManager.isOn()) {
            entity = (Element) ManagedObjectManager.fetchById(id, entityClz, ManagedObjectLockType.READLOCK);
            //BE-26436: When SN, if entity was deleted, return null
            if(entity != null && mCacheAgent.isHandleDeleted(entity.getId())) {
            	return null;
            }
        }
        else {
        	int retries = 0;
        	while (true) {
	            try {
	                return (Element) mCacheAgent.getEntityById(id, entityClz);
	            } catch (Exception ex) {
	                ExceptionWrapper result = handleException(ex, retries);
	                if (result.succeeded) {
	                	retries = result.retries;
	                	continue;
	                } else {
	                	throw result.rtex;
	                }
	            }
        	}
        }
        return entity;
    }

//    public Element getRemoteElementFromStore(String clusterName, long id) {
//        try {
//            return (Element) mCacheAgent.getRemoteElementById(clusterName, id);
//        } catch (Exception ex) {
//            throw new RuntimeException(ex);
//        }
//    }

//    public Element getElement(long id, Class elementClz) {
//        return getElement(id, elementClz, false);
//    }

    public Element getElement(long id, Class elementClz, boolean isClzAccurate, boolean ignoreRetractedOrMarkedDelete) {
        BaseHandle h = getElementHandle(id);
        if (h == null || h.getObject() == null) {  //handle not found
            Element element = null;
            // get it from id, if possible..
            if ((elementClz == null || !isClzAccurate) && !getCacheAgent().getCluster().getClusterConfig().useObjectTable()) {
                int typeId = IDEncoder.decodeTypeId(id);
                if (!getCacheAgent().getCluster().getMetadataCache().isValidTypeId(typeId)) {
                    return null;
                }
                elementClz = getCacheAgent().getCluster().getMetadataCache().getClass(typeId);
                if(elementClz != null) isClzAccurate = true;
            }

            if (elementClz != null && isClzAccurate) {
                if (!localOnly(elementClz)) element = getElementFromStore(id, elementClz);
            } else {
                element = getElementFromStore(id);
            }

            try {
                return checkPreProcCheckRtcElement(id, element);
            } catch (DuplicateExtIdException dup) {
                throw new RuntimeException(dup);
            }
        } else {
            if (!h.isRetracted_OR_isMarkedDelete() || ignoreRetractedOrMarkedDelete)
                return (Element) h.getObject();
            else
                return null;
        }
    }

    protected Concept getConceptFromCache(CacheConceptHandle handle) {
    	int retries = 0;
    	while (true) {
	        try {
	        	if (localOnly(handle.getTypeInfo().getType())) {
	                return null; // don't look in the cache.
	            }
	
	            if (ManagedObjectManager.isOn()) {
	                return (Concept) ManagedObjectManager.fetchById(handle.getId(), handle.getTypeInfo().getType(),
	                        ManagedObjectLockType.READLOCK);
	            } else { 
	                return (Concept) mCacheAgent.getEntityById(handle.getElementId(), handle.getTypeInfo().getType());
	            }
	        } catch (Exception ex) {
                ExceptionWrapper result = handleException(ex, retries);
                if (result.succeeded) {
                	retries = result.retries;
                	continue;
                } else {
                	throw result.rtex;
                }
	        }
    	}
    }

    protected Concept getConceptFromCache(CacheConceptExHandle handle) {
    	int retries = 0;
    	while (true) {
	        try {
	        	if (localOnly(handle.getTypeInfo().getType())) {
	                return null; // don't look in the cache.
	            }
	
	            if (ManagedObjectManager.isOn()) {
	                return (Concept) ManagedObjectManager.fetchById(handle.getId(), handle.getTypeInfo().getType(),
	                        ManagedObjectLockType.READLOCK);
	            } else {
	                return (Concept) mCacheAgent.getEntityById(handle.getElementId(), handle.getTypeInfo().getType());
	            }
	        } catch (Exception ex) {
                ExceptionWrapper result = handleException(ex, retries);
                if (result.succeeded) {
                	retries = result.retries;
                	continue;
                } else {
                	throw result.rtex;
                }
	        }
    	}
    }

    protected Event getEventFromCache(CacheEventHandle handle) {
    	int retries = 0;
    	while (true) {
	        try {
	        	if (localOnly(handle.getTypeInfo().getType())) {
	                return null; // don't look in the cache.
	            }
	
	            if (ManagedObjectManager.isOn()) {
	                return (Event) ManagedObjectManager.fetchById(handle.getEventId(), handle.getTypeInfo().getType(),
	                        ManagedObjectLockType.READLOCK);
	            } else {
	                Event evt = (Event) mCacheAgent.getEntityById(handle.getEventId(), handle.getTypeInfo().getType());
	                if (evt instanceof SimpleEvent) {
	                    ((SimpleEventImpl) evt).setLoadedFromCache();
	                }
	                return evt;
	            }
	        } catch (Exception ex) {
                ExceptionWrapper result = handleException(ex, retries);
                if (result.succeeded) {
                	retries = result.retries;
                	continue;
                } else {
                	throw result.rtex;
                }
	        }
    	}
    }

    protected Event getEventFromCache(CacheEventExHandle handle) {
    	int retries = 0;
    	while (true) {
	        try {
	        	if (localOnly(handle.getTypeInfo().getType())) {
	                return null; // don't look in the cache.
	            }
	
	            if (ManagedObjectManager.isOn()) {
	                return (Event) ManagedObjectManager.fetchById(handle.getEventId(), handle.getTypeInfo().getType(),
	                        ManagedObjectLockType.READLOCK);
	            } else {
	                Event evt = (Event) mCacheAgent.getEntityById(handle.getEventId(), handle.getTypeInfo().getType());
	                if (evt instanceof SimpleEvent) {
	                    ((SimpleEventImpl) evt).setLoadedFromCache();
	                }
	                return evt;
	            }
	        } catch (Exception ex) {
                ExceptionWrapper result = handleException(ex, retries);
                if (result.succeeded) {
                	retries = result.retries;
                	continue;
                } else {
                	throw result.rtex;
                }
	        }
    	}
    }

    public InferenceAgent getCacheAgent() {
        return mCacheAgent;
    }

    public long getNumHandlesRecovered() {
        return mCacheAgent.getNumHandlesRecovered();
    }

    public long getNumHandlesError() {
        return mCacheAgent.getNumHandlesError();
    }

    public long getNumHandlesInStore() {
        return 0;
    }

    public ServiceInfo getServiceInfo() {
        return new DistributedServiceInfoImpl(mCacheAgent);
    }

    public EntitySharingLevel getLocalSharingLevel(Class entityCls) {
        EntityDaoConfig ec = getCacheAgent().getEntityConfig(entityCls);
        if (ec != null) {
	        if(!ec.isDeployed()) return EntitySharingLevel.UNUSED;

	        switch (ec.getCacheMode()) {
	            case Cache:
	                return EntitySharingLevel.UNSHARED;
	            case Memory:
	            case CacheAndMemory:
	                return EntitySharingLevel.SHARED;
	        }
        }
        return EntitySharingLevel.UNSHARED;
    }

    class _NamedInstanceIterator implements Iterator {
        Iterator ret = null;

        _NamedInstanceIterator() {
            List ni = new ArrayList();
            Iterator all_elements = get_p_elementHandleMap().values().iterator();
            while (all_elements.hasNext()) {
                Object obj = all_elements.next();
                if (obj instanceof NamedInstance) {
                    ni.add(obj);
                }
            }
            ret = ni.iterator();
        }


        public boolean hasNext() {
            return ret.hasNext();
        }

        public Object next() {
            return ret.next();
        }

        public void remove() {
            throw new RuntimeException("NamedInstanceIterator.remove() is not implemented, should use ElementHandleMap.remove() to remove an instance");
        }
    }
    
//    public Event getEvent(String extId)  {
//        BaseHandle ehandle = getEventHandle(extId);
//        if ((ehandle == null) || (ehandle.getObject() == null)) {
//            try {
//                Object obj= mCacheAgent.getEventByExtId(extId);
//                if (obj == null) {
//                    obj=getEntityFromPreprocess(extId);
//                }
//                if (obj instanceof Event) {
//                    Event evt= (Event) obj;
//                    if (evt instanceof SimpleEvent) {
//                        ((SimpleEventImpl) evt).setLoadedFromCache();
//                    }
//                    if (isInRTCCycle()) {
//                        CacheHandle h= (CacheHandle) this.getAddEventHandle(evt, true);
//                        evt = (Event)h.getObject();
//                        //clearRef(h);
//                    }
//                    return (Event) evt;
//                } else {
//                    return null;
//                }
//            } catch (Exception ex) {
//                throw new RuntimeException(ex);
//            }
//        }
//        else {
//            if (!ehandle.isRetracted_OR_isMarkedDelete())
//                return (Event) ehandle.getObject();
//            else 
//                return null;
//        }
//    	  return getEventByClzExtId(null, extId);
//     }

    protected ExceptionWrapper handleException(Throwable throwable, int retries) {
    	// Allow only 3 tries when failures continue even through Quorum is met.
    	if (++retries > 3) {
            return new ExceptionWrapper(new RuntimeException("Operation will be aborted due to exceeding retries.", throwable), false, retries);
    	}
    	
        GroupMembershipService gmp = m_session.getRuleServiceProvider().getCluster().getGroupMembershipService();

        QuorumStatus quorumStatus = gmp.checkQuorum();
        if (!quorumStatus.isQuorum()) {
            logger.log(Level.ERROR, "Error occurred in [" + getClass().getSimpleName() + "] possibly due to quorum violation. Will wait for quorum.", throwable);
            gmp.waitForQuorum();
            
            return new ExceptionWrapper(new RuntimeException("Quorum seems to have been restored. Pending operation will be retried/aborted.", throwable), true, retries);
        }

        try {
            // Sleep a second in handling cases of repetitive exceptions.
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        
        if (throwable.getMessage() != null && (throwable.getMessage().contains("remote_client_timed_out") ||
        		throwable.getMessage().contains("metaspace_invalid"))) {
        	// Give it another try and hope that the space connection was restored.
        	return new ExceptionWrapper(new RuntimeException("Remote client connection was reset. Pending operation will be retried/aborted.", throwable), true, retries);
        }
        
        return new ExceptionWrapper(new RuntimeException("Operation will be aborted due to a problem.", throwable), false, retries);
    }

    public Element getElementByUri(String extId, String uri) {
        TypeManager tm = m_session.getRuleServiceProvider().getTypeManager();
        if (tm != null) {
            TypeDescriptor td = tm.getTypeDescriptor(uri);
            if (td != null) {
				return getElement(extId, td.getImplClass());
            }
        }
        return null;
    }

    public Event getEventByUri(String extId, String uri) {
        TypeManager tm = m_session.getRuleServiceProvider().getTypeManager();
        if (tm != null) {
            TypeDescriptor td = tm.getTypeDescriptor(uri);
            if (td != null) {
				return getEvent(extId, td.getImplClass());
            }
        }
        return null;
    }

    public Element getElementByUri(long id, String uri) {
        TypeManager tm = m_session.getRuleServiceProvider().getTypeManager();
        if (tm != null) {
            TypeDescriptor td = tm.getTypeDescriptor(uri);
            if (td != null) {
                Class clz = td.getImplClass();
                return getElement(id, clz, false, false);
            }
        }
        return null;
    }

    public Event getEventByUri(long id, String uri) {
        TypeManager tm = m_session.getRuleServiceProvider().getTypeManager();
        if (tm != null) {
            TypeDescriptor td = tm.getTypeDescriptor(uri);
            if (td != null) {
                Class clz = td.getImplClass();
                return getEvent(id, clz, false);
            }
        }
        return null;
    }

    /**
     * @param entityClass Can be <code>null</code>.
     * @param extId
     * @return
     */
    public Element getElement(String extId, Class entityClass) {
    	Element el = null;
        BaseHandle chandle = getElementHandle(extId, entityClass);
        if (chandle == null || chandle.getObject() == null) {
            Entity en = null;
            int retries = 0;
            while(true) {
	            try {
	                if (!localOnly(entityClass, omInMemoryMode)) {
	                    en = (entityClass == null) ? getElementFromStore(extId) : getElementFromStore(extId, entityClass);
	                }
	                el = checkPreProcCheckRtcElement(extId, en);
	                break;
	            } catch (Exception ex) {
	                ExceptionWrapper result = handleException(ex, retries);
	                if (result.succeeded) {
	                	retries = result.retries;
	                	continue;
	                } else {
	                	throw result.rtex;
	                }
	            }
            }
        } else {
            if (!chandle.isRetracted_OR_isMarkedDelete())
            	el = (Element) chandle.getObject();
            else
                return null;
        }
        
        //BE-26575, BE-27120:  return null when entity class doesn't match
        if(el != null && entityClass != null && !el.getClass().equals(entityClass))
        	return null;
        else 
        	return el;
    }

    @Override
    public Event getEvent(String extId, Class eventClass)  {
        if (ManagedObjectManager.isOn()) {
            Event e = (Event) ManagedObjectManager.fetchByExtId(extId, eventClass, ManagedObjectLockType.READLOCK);
            if (e != null) {
                return e;
            }
        }

        BaseHandle ehandle = getEventHandle(extId,eventClass);
        if (ehandle == null || ehandle.getObject() == null) {
            Entity en = null;
            int retries = 0;
            while (true) {
	            try {
	                if (!localOnly(eventClass, omInMemoryMode)) {
	                    en = eventClass == null? mCacheAgent.getEventByExtId(extId) :
	                        mCacheAgent.getEventByUri(extId, eventClass);
	                }
	                return checkPreProcCheckRtcEvent(extId, en);
	            } catch (Exception ex) {
	                ExceptionWrapper result = handleException(ex, retries);
	                if (result.succeeded) {
	                	retries = result.retries;
	                	continue;
	                } else {
	                	throw result.rtex;
	                }
	            }
            }
        } else {
            if (!ehandle.isRetracted_OR_isMarkedDelete())
                return (Event) ehandle.getObject();
            else
                return null;
        }
    }

    private Element checkPreProcCheckRtcElement(String extId, Entity en) throws DuplicateExtIdException {
        Element el = null;
        if (en == null) {
            en = getElementFromPreprocess(extId);
        }

        if (en instanceof Element) {
            el = (Element)en;
            if (isInRTCCycle()) {
//            try {
                CacheHandle h = (CacheHandle) this.getAddElementHandle(el, true);
                el = (Element) h.getObject();
//              clearRef(h);
//            } catch (Exception ex) {
//              throw new RuntimeException(ex);
//            }
            }
        }
        return el;
    }

    private Element checkPreProcCheckRtcElement(long id, Entity en) throws DuplicateExtIdException {
        if (en == null) {
            en=getElementFromPreprocess(id);
        }

        Element el = null;
        if (en instanceof Element) {
            el = (Element)en;
            if (isInRTCCycle()) {
//            try {
                CacheHandle h = (CacheHandle) this.getAddElementHandle(el, true);
                el = (Element) h.getObject();
//              clearRef(h);
//            } catch (Exception ex) {
//               throw new RuntimeException(ex);
//            }
            }
        }
        return el;
    }

    private Event checkPreProcCheckRtcEvent(String extId, Entity en) throws DuplicateExtIdException {
        Event ev = null;
        if (en == null) {
            en=getEventFromPreprocess(extId);
        }
        if (en instanceof Event) {
            ev = (Event)en;
            if (ev instanceof SimpleEvent) {
                ev.setLoadedFromCache();
            }
            if (isInRTCCycle()) {
//              try {
                Handle h = this.getAddEventHandle(ev, true);
                ev = (Event)h.getObject();
//              } catch (DuplicateExtIdException e) {
//                  throw new RuntimeException(e);
//              }
            }
        }
        return ev;
    }

    private Event checkPreProcCheckRtcEvent(long id, Entity en) throws DuplicateExtIdException {
        Event ev = null;
        if (en == null) {
            en=getEventFromPreprocess(id);
        }
        if (en instanceof Event) {
            ev = (Event)en;
            if (ev instanceof SimpleEvent) {
                ev.setLoadedFromCache();
            }
            if (isInRTCCycle()) {
//              try {
                CacheHandle h= (CacheHandle) this.getAddEventHandle(ev, true);
                ev = (Event)h.getObject();
//              } catch (DuplicateExtIdException e) {
//                  throw new RuntimeException(e);
//              }
            }
        }
        return ev;
    }

    protected Element getElementFromPreprocess(String extId) {
        return PreprocessContext.getElementFromPreprocess(extId);
    }
    protected Event getEventFromPreprocess(String extId) {
        return PreprocessContext.getEventFromPreprocess(extId);
    }
    protected Element getElementFromPreprocess(long id) {
        return PreprocessContext.getElementFromPreprocess(id);
    }
    protected Event getEventFromPreprocess(long id) {
        return PreprocessContext.getEventFromPreprocess(id);
    }


    protected static class BaseHandleInitWrapper implements BaseHandleWrapper {
    	
        protected BaseHandle handle = null;

        // DefaultDistributedCacheBasedStore only explicitly calls init...Handle()
        // Callers to getBaseHandle() will only call it after the initial call to init...Handle()
        public synchronized BaseHandle getBaseHandle() {
            return handle;
        }

        public synchronized BaseHandle initExtElementHandle(Element element, boolean forceSet, DefaultDistributedCacheBasedStore cstore) throws DuplicateExtIdException {
            Long id = element.getId();
            BaseHandleWrapper wrapper = cstore.get_elementmap_for_class(element.getClass()).putIfAbsent(id, this);

            if (wrapper == null) {
                BaseHandle h = cstore.getNewElementExtHandle(element, null,forceSet);
                try {
                    cstore._addExtIdEntry(element.getExtId(), h);
                } catch (DuplicateExtIdException dupEx) {
                    cstore.get_elementmap_for_class(element.getClass()).remove(id);
                    throw dupEx;
                }

                //replace the wrapper with the handle
                cstore.get_elementmap_for_class(element.getClass()).put(id, h);
                handle = h;
            } else {
            	handle = wrapper.getBaseHandle();
            }
            return handle;
        }

        public synchronized BaseHandle initSimpleElementHandle(Element element, boolean forceSet, DefaultDistributedCacheBasedStore cstore) {
            Long id = element.getId();
            BaseHandleWrapper wrapper = cstore.get_elementmap_for_class(element.getClass()).putIfAbsent(id, this);

            if (wrapper == null) {
                BaseHandle h = cstore.getNewElementHandle(element, null,forceSet);
                //replace the wrapper with the handle
                cstore.get_elementmap_for_class(element.getClass()).put(id, h);
                handle = h;
            } else {
            	handle = wrapper.getBaseHandle();
            }
            return handle;
        }

        public synchronized BaseHandle initExtEventHandle(Event event, boolean forceSet, DefaultDistributedCacheBasedStore cstore) throws DuplicateExtIdException {
            Long id = event.getId();
            BaseHandleWrapper wrapper = cstore.get_eventmap_for_class(event.getClass()).putIfAbsent(id, this);

            if (wrapper == null) {
                BaseHandle h= cstore.getNewEventExtHandle(event,null,forceSet);
                try {
                    cstore._addExtIdEntry(event.getExtId(), h);
                } catch (DuplicateExtIdException dupEx) {
                    cstore.get_eventmap_for_class(event.getClass()).remove(id);
                    throw dupEx;
                }

                //replace the wrapper with the handle
                cstore.get_eventmap_for_class(event.getClass()).put(id, h);
                handle = h;
            } else {
            	handle = wrapper.getBaseHandle();
            }
            return handle;
        }

        public synchronized BaseHandle initSimpleEventHandle(Event event, boolean forceSet, DefaultDistributedCacheBasedStore cstore) {
            Long id = event.getId();
            BaseHandleWrapper wrapper = cstore.get_eventmap_for_class(event.getClass()).putIfAbsent(id, this);

            if (wrapper == null) {
                BaseHandle h= cstore.getNewEventHandle(event,null,forceSet);
                //replace the wrapper with the handle
                cstore.get_eventmap_for_class(event.getClass()).put(id, h);
                handle = h;
            } else {
            	handle = wrapper.getBaseHandle();
            }
            return handle;
        }
    }
    
    protected class ExceptionWrapper {
    	RuntimeException rtex;
    	boolean succeeded;
    	int retries;
    	
    	ExceptionWrapper(RuntimeException rteThrown, boolean successState, int retryCount) {
    		rtex = rteThrown;
    		succeeded = successState;
    		retries = retryCount;
    	}
    }
}
