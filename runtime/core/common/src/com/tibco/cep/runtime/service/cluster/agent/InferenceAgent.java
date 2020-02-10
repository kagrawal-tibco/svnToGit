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

package com.tibco.cep.runtime.service.cluster.agent;

import com.tibco.be.util.BEProperties;
import com.tibco.be.util.XiSupport;
import com.tibco.cep.kernel.core.base.BaseHandle;
import com.tibco.cep.kernel.core.base.RtcOperationList;
import com.tibco.cep.kernel.core.base.WorkingMemoryImpl;
import com.tibco.cep.kernel.core.rete.ReteListener;
import com.tibco.cep.kernel.core.rete.ReteWM;
import com.tibco.cep.kernel.model.entity.Element;
import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.kernel.model.entity.NamedInstance;
import com.tibco.cep.kernel.model.knowledgebase.DuplicateExtIdException;
import com.tibco.cep.kernel.model.knowledgebase.Handle;
import com.tibco.cep.kernel.model.knowledgebase.WorkingMemory;
import com.tibco.cep.kernel.model.rule.Rule;
import com.tibco.cep.kernel.model.rule.RuleFunction;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.channel.Channel;
import com.tibco.cep.runtime.channel.ChannelManager;
import com.tibco.cep.runtime.channel.CommandChannel;
import com.tibco.cep.runtime.config.Configuration;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.model.TypeManager.TypeDescriptor;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.VersionedObject;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.cep.runtime.model.element.impl.StateMachineConceptImpl;
import com.tibco.cep.runtime.model.event.CommandEvent;
import com.tibco.cep.runtime.model.event.CommandListener;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.model.event.TimeEvent;
import com.tibco.cep.runtime.model.event.impl.SimpleEventImpl;
import com.tibco.cep.runtime.model.event.impl.TimeEventImpl;
import com.tibco.cep.runtime.model.process.ObjectBean;
import com.tibco.cep.runtime.scheduler.impl.WorkerBasedController;
import com.tibco.cep.runtime.service.ServiceRegistry;
import com.tibco.cep.runtime.service.basic.AsyncWorkerServiceWatcher;
import com.tibco.cep.runtime.service.cluster.CacheAgent;
import com.tibco.cep.runtime.service.cluster.agent.mm.InferenceAgentReteNetwork;
import com.tibco.cep.runtime.service.cluster.agent.mm.InferenceAgentStatsManager;
import com.tibco.cep.runtime.service.cluster.agent.tasks.*;
import com.tibco.cep.runtime.service.cluster.backingstore.BackingStore;
import com.tibco.cep.runtime.service.cluster.backingstore.DBJobGroupManager;
import com.tibco.cep.runtime.service.cluster.events.EventTable;
import com.tibco.cep.runtime.service.cluster.events.EventTuple;
import com.tibco.cep.runtime.service.cluster.events.notification.*;
import com.tibco.cep.runtime.service.cluster.filters.AvailableEventFilter;
import com.tibco.cep.runtime.service.cluster.om.*;
import com.tibco.cep.runtime.service.cluster.scheduler.impl.DefaultSchedulerCache;
import com.tibco.cep.runtime.service.cluster.system.EntityIdMask;
import com.tibco.cep.runtime.service.cluster.system.MetadataCache;
import com.tibco.cep.runtime.service.cluster.system.ObjectTable;
import com.tibco.cep.runtime.service.cluster.system.ObjectTable.Tuple;
import com.tibco.cep.runtime.service.cluster.txn.RtcTransaction;
import com.tibco.cep.runtime.service.cluster.txn.RtcTransactionManager;
import com.tibco.cep.runtime.service.cluster.txn.RtcTransactionProperties;
import com.tibco.cep.runtime.service.cluster.txn.RtcTransactionSubscriber_V2;
import com.tibco.cep.runtime.service.cluster.util.WorkManager;
import com.tibco.cep.runtime.service.cluster.util.WorkManagerFactory;
import com.tibco.cep.runtime.service.cluster.util.WorkPool;
import com.tibco.cep.runtime.service.management.agent.AgentMBeansManager;
import com.tibco.cep.runtime.service.om.DirectDistributedCacheConnect;
import com.tibco.cep.runtime.service.om.FastLocalCache;
import com.tibco.cep.runtime.service.om.api.ControlDao;
import com.tibco.cep.runtime.service.om.api.ControlDaoType;
import com.tibco.cep.runtime.service.om.api.EntityDao;
import com.tibco.cep.runtime.service.om.api.EntityDaoConfig;
import com.tibco.cep.runtime.service.om.api.invm.InProgressTxnKeeper;
import com.tibco.cep.runtime.service.om.api.invm.InVMServiceBuilder;
import com.tibco.cep.runtime.service.om.api.invm.LocalCache;
import com.tibco.cep.runtime.service.om.impl.invm.NoOpLocalCache;
import com.tibco.cep.runtime.service.om.impl.invm.NoOpTxnKeeper;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.impl.RuleServiceProviderImpl;
import com.tibco.cep.runtime.session.impl.RuleSessionImpl;
import com.tibco.cep.runtime.session.impl.RuleSessionStopWatchKeeper;
import com.tibco.cep.runtime.session.locks.LockManager;
import com.tibco.cep.runtime.session.locks.LockManagerViewer;
import com.tibco.cep.runtime.session.locks.LockRecorder;
import com.tibco.cep.runtime.util.AssertSMTimeoutEventTask;
import com.tibco.cep.runtime.util.ProcessInfo;
import com.tibco.cep.runtime.util.SystemProperty;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiSerializer;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.io.*;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Constructor;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Mar 17, 2008
 * Time: 1:25:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class InferenceAgent extends AbstractCacheAgent
        implements RuleFunctionService, InferenceAgentMBean, CommandListener {

    public static final String PROP_INFERENCE_AGENT_CLASS_NAME = "be.engine.agent.inference.className";

    protected DistributedCacheBasedStore objectStore;

    protected LocalCache localCache;

    ControlDao<? extends Object, ? extends Object> txnCache;
    long txnId = 0;
    boolean activated = false;
    boolean bInRecovery = false;
    WorkManager recoveryManager;
    WorkManager jobThreadPool; //AdhocWorkManager - moved from DefaultDistributedCacheBasedStore.

    long numHandlesRecovered = 0L;
    long numHandlesError = 0L;
    protected RuleSession ruleSession;
    EntityMediator entityMediator = null;
    RtcEventSubscriber m_eventSubscriber;

    ClusterEntityListener clusterEntityListener;

    //AgentTimeQueue agentTimeQueue;
    protected AgentActionManager actionMgr;
    boolean isMultiEngineMode = false;

    public int[] topicsOfInterest;
    WorkManager mWorkPool;
    WorkManager dbThreadPool;
    long numTransactionsPublished = 0, numTransactionsSubscribed = 0;
    public volatile long timeTransactionsPublished = 0;
    volatile long timeTransactionsSubscribed = 0;
    String[] rulesLoaded = new String[0];
    public InferenceAgentStatsManager agentStats;
    private ThreadLocal deletedIds = new ThreadLocal();
    protected InProgressTxnKeeper inProgressTxnKeeper;
    protected BulkConceptCacheReader bulkConceptCacheReader;
    protected DirectDistributedCacheConnect dcUser;
    protected BulkConceptRetriever bulkConceptRetriever;

    protected String agentCommandKey;
    protected CommandChannel agentCommandChannel;

    protected ThreadLocal m_transactionProperties = new ThreadLocal();

    protected RuleFunction[] registeredRuleFunctions;
    protected boolean[] deleteSubscriptions = new boolean[0];
    protected boolean[] changeSubscriptions = new boolean[0];
    protected boolean use2xMode = false;

    /**
     *
     * @param config
     * @param rsp
     * @throws Exception
     */
    public InferenceAgent(AgentConfiguration config, RuleServiceProvider rsp, CacheAgent.Type type) throws Exception {
        super(config, type );
        initActionManager();
        this.isMultiEngineMode = rsp.isMultiEngineMode();
        BEProperties properties = (BEProperties) rsp.getProperties();

        this.objectStore = DistributedCacheBasedStoreProvider.getInstance().newDistributedCacheBasedStore(this, properties);
        this.ruleSession = rsp.getRuleRuntime().createRuleSession(config.getAgentName(), objectStore);

        if (isCacheServer()) {
            initCacheserverAgent();
        }
        else {
            initInferenceAgent();
        }
    }

    protected void initActionManager() {
    	actionMgr = new ThreadLocalAgentActionManager();
    }

    private void initCacheserverAgent() throws Exception {
        inProgressTxnKeeper = new NoOpTxnKeeper();
        localCache = new NoOpLocalCache();
    }

    private void initInferenceAgent() throws Exception{
        InVMServiceBuilder builder = new InVMServiceBuilder();
        Configuration c = ((RuleSessionImpl) ruleSession).getConfiguration();
        builder.build(c, this);
        localCache = builder.getLocalCache();
        inProgressTxnKeeper = builder.getTxnKeeper();
        builder.discard();

        if (objectStore instanceof FastLocalCache) {
            FastLocalCache fastLocalCache = (FastLocalCache) objectStore;

            this.bulkConceptCacheReader = new BulkConceptCacheReader(this, cluster, fastLocalCache);

            logger.log(Level.WARN, BulkConceptCacheReader.class.getSimpleName() + " in use.");
        } else {
            this.bulkConceptCacheReader = null;
        }

        //-------------

        if (objectStore instanceof DirectDistributedCacheConnect) {
            this.dcUser = (DirectDistributedCacheConnect) objectStore;

            this.localCache = new NoOpLocalCache();

            this.logger.log(Level.INFO,
                    "Initialized [" + DirectDistributedCacheConnect.class.getSimpleName() + "].");
            this.logger.log(Level.INFO, "Overriding Local Cache setting with [" +
                    localCache.getClass().getName() + "].");
        } else {
            this.dcUser = null;
        }
    }

    @Override
    public void init(AgentManager agentManager) throws Exception {
        super.init(agentManager);

        //-------------

        this.bulkConceptRetriever = new BulkConceptRetriever(this, cluster, localCache);
    }

    public LocalCache getLocalCache() {
        return localCache;
    }

    /**
     * returns the logger from the ruleserviceprovider
     *
     * @param clazz
     * @return
     */
    public Logger getLogger(Class clazz) {
        return ruleSession.getRuleServiceProvider().getLogger(clazz);
    }

    public BulkConceptRetriever getBulkConceptRetriever() {
        return bulkConceptRetriever;
    }

    //Suresh TODO - REMOVE this - base already has it.
    protected void printInitializedInfo() {
        if (logger.isEnabledFor(Level.INFO)) {
            logger.log(Level.INFO, "---------------------------------Agent: " + this.getAgentName() + " Initialization Info-----------------------------------");
            logger.log(Level.INFO, "--Name=" + this.getAgentName());
            logger.log(Level.INFO, "--Cluster Name=" + this.getCluster().getClusterName());
            logger.log(Level.INFO, "--CACHESERVER Mode=" + this.isCacheServer());
            logger.log(Level.INFO, "--ID=" + this.getAgentId());
            logger.log(Level.INFO, "--2XMode=" + this.use2xMode);
            if (!this.isCacheServer()) {
                //logger.log(Level.INFO,"-----Details-----" +
                //        AgentCapability.toString(getAgentCapabilities(), cluster.getClusterConfig().isCacheAside()));
                logger.log(Level.INFO,"--MaxActive=" + this.getMaxActive());
                logger.log(Level.INFO,"--Priority=" + this.getPriority());
                logger.log(Level.INFO,"--ConcurrentRETE Enabled=" + this.getAgentConfig().isConcurrent());
                logger.log(Level.INFO,"--ParallelWrites Enabled=" + this.getAgentConfig().isEnableParallelOps());
                logger.log(Level.INFO,"--DuplicateCheck Enabled=" + this.getAgentConfig().isDuplicateCheckOn());
                logger.log(Level.INFO,"--L1CacheSize=" + this.getAgentConfig().getL1CacheSize());
                logger.log(Level.INFO,"--Cache ThreadPool=" + this.getAgentConfig().getThreadCount());
                logger.log(Level.INFO,"--Cache QueueSize=" + this.getAgentConfig().getCacheOpsQueueSize());

                if (cluster.getClusterConfig().isCacheAside()) {
                    logger.log(Level.INFO,"--DB ThreadPool=" + this.getAgentConfig().getDBThreadCount());
                    logger.log(Level.INFO,"--DB QueueSize=" + this.getAgentConfig().getDBOpsQueueSize());
                }

                logger.log(Level.INFO, "--System Subscriptions [Used to keep agents in sync]");
                for (int i = 0; i < this.deleteSubscriptions.length; i++) {
                    int type_id = i + MetadataCache.BE_TYPE_START;
                    Class entityClz = cluster.getMetadataCache().getClass(type_id);
                    if (deleteSubscriptions[i])
                        logger.log(Level.INFO, "  -- " + entityClz);
                }

                logger.log(Level.INFO, "--User Subscriptions [Used to keep agents in sync]");
                for (int i = 0; i < this.changeSubscriptions.length; i++) {
                    int type_id = i + MetadataCache.BE_TYPE_START;
                    Class entityClz = cluster.getMetadataCache().getClass(type_id);
                    if (changeSubscriptions[i])
                        logger.log(Level.INFO, "  -- " + entityClz + " : Preprocessor=" + this.getEntityConfig(entityClz).getRuleFunctionUri());
                }
            }
        }
    }

    /**
     * @return Can be <code>null</code>.
     */
    public BulkConceptCacheReader getBulkConceptCacheReader() {
        return bulkConceptCacheReader;
    }

    @Override
    public void onRegister() throws Exception {
        agentIdRelatedRegistration();
    }
    
    /*
     * Create AgentTxn cache only if c+m entities are subscribed for change or delete
     */
    public void initTxnCache() {
        if (isCacheServer()) {
			return;
		}
        if (!use2xMode) {
        	int numRegistered = cluster.getTopicRegistry().numRegistered();
        	if(txnCache == null && numRegistered > 0) {
	            //TxnCache key:RtcKey.class, byte[].
	            txnCache = cluster.getDaoProvider().createControlDao(byte[].class, byte[].class, ControlDaoType.AgentTxn$AgentId, getAgentName(), new Integer(getAgentId()).toString());
	            txnCache.start();
	            //Update agentTuple so that change listener will register with the new cache
	            cluster.getAgentManager().updateAgent(this);
        	}
        }
    }

    /**
     * @return
     */
    public String getType() {
        return type.name();
    }

    protected boolean isInRTCCycle() {
        WorkingMemoryImpl wm = (WorkingMemoryImpl) ((RuleSessionImpl) ruleSession).getWorkingMemory();
        return WorkingMemoryImpl.executingInside(wm);
    }

    /**
     * @param agentAction
     */
    public void addAgentAction(AgentAction agentAction) {
        actionMgr.addAction(agentAction);
    }

    public void clearAgentActions() {
    	actionMgr.removeAll();
    }

    /**
     * @return
     */
    public RuleSession getRuleSession() {
        return this.ruleSession;
    }

    /**
     * @return
     */
    public EntityMediator getEntityMediator() {
        return this.entityMediator;
    }

    /**
     * @param entityURI
     * @return
     * @throws Exception
     */
    public String getEntityCacheName(String entityURI) throws Exception {
        TypeManager.TypeDescriptor td = cluster.getRuleServiceProvider().getTypeManager().getTypeDescriptor(entityURI);
        if (td != null) {
            EntityDao dao = cluster.getDaoProvider().getEntityDao(td.getImplClass());
            if (dao != null)
                return dao.getName();
        }
        return null;
    }

    /**
     * @param keys
     * @param provider
     * @return
     */
    public Collection loadObjects(Set keys, EntityDao provider) throws Exception {
        ArrayList ret = new ArrayList();
        Set load = new HashSet();
        boolean isElement = ConceptImpl.class.isAssignableFrom(provider.getEntityClass());
        Iterator allRequestedIDs = keys.iterator();
        while (allRequestedIDs.hasNext()) {
            Long ID = (Long) allRequestedIDs.next();
            if (!this.isDeleted(ID)) {
                if (!isElement) {
                    LocalCache.Entry entry = localCache.getEntry(ID);
                    if (entry != null) {
                        if (!entry.isPendingRemoval()) {
                            ret.add(ID);
                        }
                    } else {
                        load.add(ID);
                    }
                } else {
                    load.add(ID);
                }
            }
        }

        Collection<Entity> allObjects = provider.getAll(load);
        for (Entity e : allObjects) {
            localCache.directPut(e);
            ret.add(e.getId());
        }
        return ret;
    }

    /**
     * Loads the Concept Id's provided, from the cluster forcibly. Then puts them into the L1Cache.
     *
     * @param conceptIds
     * @param provider
     * @return The concepts that were available in the cluster and then placed into the L1Cache.
     * @throws Exception
     */
    public Collection<Concept> loadConcepts(Set<Long> conceptIds, EntityDao provider)
            throws Exception {
        Collection<Entity> entries = provider.getAll(conceptIds);

        LinkedList<Concept> loadedConcepts = new LinkedList<Concept>();

        for (Entity e : entries) {
            Concept concept = (Concept) e;
            localCache.directPut(concept);
            loadedConcepts.add(concept);
        }

        return loadedConcepts;
    }

    /**
     * @throws Exception
     */
    public void flushCaches() throws Exception {
        cluster.flushAll();
    }

    //public void printL1CacheToLog() ;

    public void purgeDeletedObjects() {
        cluster.getObjectTableCache().purgeDeletedObjects();
    }

    private void registerMBean() {
        try {
            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
            //ObjectName name = new ObjectName("com.tibco.cep.runtime.service.om.impl.coherence.cluster:type=CacheClusterMBean");
            //ObjectName name = new ObjectName("BusinessEvents.Cluster:type=CacheClusterMBean");
            ObjectName name = new ObjectName("com.tibco.be:type=Agent,agentId=" + this.getAgentId()
                    + ",subType=AgentStat");
            //ObjectName name = new ObjectName("com.tibco.be:service=ClusterCache,name=" + cacheName);
            //InferenceAgent,service=Agents" + ",id=" + this.getAgentId());
            //ObjectName name = new ObjectName("com.tibco.be:cluster=" + this.getClusterName());
            mbs.registerMBean(this, name);

            // RETE MBean
            /*
            name = new ObjectName("com.tibco.be:type=Agent,agentId=" + this.getAgentId() +
                ",subType=ReteWM,service=ReteNetwork-" + Thread.currentThread().getId());
            mbs.registerMBean(this, name);
            */
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        RtcTransactionManager.init(logger);
    }

    @Override
    public int getMaxActive() {
        if (isMultiEngineMode) {
            return this.agentConfig.getMaxActive();
        }

        return super.getMaxActive();
    }

    @Override
    public int getPriority() {
        if (isMultiEngineMode) {
            return this.agentConfig.getPriority();
        }

        return super.getPriority();
    }

    public Element getNamedInstance(String uri, Class entityClz) {
        String key = "";
        if (this.isMultiEngineMode) {
            final String agentKey = this.agentConfig.getAgentKey();
            key = agentKey == null ? uri : agentKey + "." + uri;
        } else {
            key = uri;
        }
        Element sc = objectStore.getElement(key, entityClz);
        if (sc == null) {
            long id = ruleSession.getRuleServiceProvider().getIdGenerator().nextEntityId(entityClz);
            try {
                int typeId = cluster.getMetadataCache().getTypeId(entityClz);
                // Special processing for case where complete ontology is not deployed on each engine
                if (typeId < 0) {
                    logger.log(Level.WARN, "TypeId not found for " + entityClz);
                    return null;
                }
                Constructor<Element> cons = entityClz.getConstructor(new Class[]{long.class, String.class});
                sc = cons.newInstance(new Object[]{new Long(id), key});
                ruleSession.assertObject(sc, false);
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new RuntimeException(ex);
            }
        }
        return sc;
    }

    /**
     * An efficient implementation for creating or updating an object based on extId. This should ideally be
     * called by the preprocessor to upsert an object to the cache and then enter the RETE for rule evaluation
     *
     * @param entity
     */

    public void createElement(Element entity) throws DuplicateExtIdException, Exception {
        if (agentConfig.isDuplicateCheckOn()) {
            if (this.getAgentState() == CacheAgent.AgentState.ACTIVATED) {
                if ((entity.getExtId() != null) && (entity.getExtId().length() > 0)) {
                    Tuple tuple = null;
                    if (!cluster.getClusterConfig().useObjectTable()) {
                        TypeDescriptor td = ruleSession.getRuleServiceProvider().getTypeManager().getTypeDescriptor(entity.getClass());
                        if (td != null) {
                            String uri = td.getURI();
                            if (uri != null) {
                                //put the extid / type in a the cache; so that its available to underlying store implementation
                                cluster.getObjectTableCache().putExtIdToTypeCache(entity.getExtId(), uri);
                            }
                        }
                    }
                    tuple = cluster.getObjectTableCache().getByExtId(entity.getExtId());
                    if (tuple != null && !tuple.isDeleted() && tuple.getId() != entity.getId()) {
                        BaseHandle h = getElementOrEventHandle(tuple.getId(), tuple.getTypeId());
                        if (h != null) {
                            if (h.isRetracted_OR_isMarkedDelete()) {
                                return;
                            } else {
                                if (!isHandleDeleted(tuple.getId())) {
                                    throw new DuplicateExtIdException("Attempt to assert duplicate entity " + entity + " with extId=" + entity.getExtId());
                                }
                            }
                        } else {
                            if (!isHandleDeleted(tuple.getId())) {
                                throw new DuplicateExtIdException("Attempt to assert duplicate entity " + entity + " with extId=" + entity.getExtId());
                            }
                        }
                    }
                }
            }
        }
    }

    protected BaseHandle getElementOrEventHandle(long id, int typeId) {
        Class entityClz = cluster.getMetadataCache().getClass(typeId);
        if (Concept.class.isAssignableFrom(entityClz)) {
            return (BaseHandle) objectStore.getElementHandle(id);
        } else {
            return (BaseHandle) objectStore.getEventHandle(id);
        }
    }

    /**
     * @param entity
     * @throws DuplicateExtIdException
     * @throws Exception
     */
    public void createEvent(Event entity) throws DuplicateExtIdException, Exception {
        if (agentConfig.isDuplicateCheckOn()) {
            if (this.getAgentState() == CacheAgent.AgentState.ACTIVATED) {
                if ((entity.getExtId() != null) && (entity.getExtId().length() > 0)) {
                    Tuple tuple = null;
                    if (!cluster.getClusterConfig().useObjectTable()) {
                        TypeDescriptor td = ruleSession.getRuleServiceProvider().getTypeManager().getTypeDescriptor(entity.getClass());
                        if (td != null) {
                            String uri = td.getURI();
                            if (uri != null) {
                                // Put the extid / uri in the cache; so that its available to underlying store implementation
                                cluster.getObjectTableCache().putExtIdToTypeCache(entity.getExtId(), uri);
                            }
                        }
                    }
                    tuple = cluster.getObjectTableCache().getByExtId(entity.getExtId());
                    if (tuple != null) {
                        if (tuple.getId() != entity.getId()) {
                            BaseHandle h = getElementOrEventHandle(tuple.getId(), tuple.getTypeId());
                            if (h != null) {
                                if (h.isRetracted_OR_isMarkedDelete()) {
                                    return;
                                } else {
                                    if (!isHandleDeleted(tuple.getId())) {
                                        throw new DuplicateExtIdException("Attempt to assert duplicate entity " + entity + " with extId=" + entity.getExtId());
                                    }
                                }
                            } else {
                                if (!isHandleDeleted(tuple.getId())) {
                                    throw new DuplicateExtIdException("Attempt to assert duplicate entity " + entity + " with extId=" + entity.getExtId());
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void deleteEntity(Handle handle) {
        if (handle == null) return;
        if (!(handle instanceof CacheHandle)) return;
        CacheHandle ch = (CacheHandle) handle;
        Object ref = ch.getRef();
        if (!(ref instanceof Entity)) return;
        Long id = ((Entity) ref).getId();
        getDeletedIds().put(id, id);
    }

    public boolean isDeleted(Entity entry) throws Exception {
        if (this.isMultiEngineMode) {
            if (!localCache.isPendingRemoval(entry.getId())) {
                Tuple tuple = cluster.getObjectTableCache().getById(entry.getId());
                if (tuple != null) {
                    return tuple.isDeleted();
                }
            } else {
                return true;
            }
        }
        return false;
    }

    public boolean isDeleted(long id) throws Exception {
        if (this.isMultiEngineMode) {
            if (!localCache.isPendingRemoval(id)) {
                Tuple tuple = cluster.getObjectTableCache().getById(id);
                if (tuple != null) {
                    return tuple.isDeleted();
                }
            } else {
                return true;
            }
        }
        return false;
    }

    protected void markSaved(long id) {
        localCache.markSaved(id);
    }

    protected void replaceElement(Element entry) throws Exception {
        if (this.getAgentState() == CacheAgent.AgentState.ACTIVATED)
            localCache.directPut(entry);
    }

    protected void removeElement(long id) throws Exception {
        if (this.getAgentState() == CacheAgent.AgentState.ACTIVATED) {
            if (localCache.isPendingRemoval(id)) return;
            ((RuleSessionImpl) ruleSession).cleanupElementHandle(id);
            localCache.directRemove(id);
        }
    }

    protected void removeEvent(long id) throws Exception {
        synchronized (((RuleSessionImpl) ruleSession).getWorkingMemory()) {
            if (localCache.isPendingRemoval(id)) return;
            ((RuleSessionImpl) ruleSession).cleanupEventHandle(id);
            localCache.directRemove(id);
        }
    }

    public void assertEventFromCache(Event entry, boolean loadOnly) throws Exception {
        synchronized (((RuleSessionImpl) ruleSession).getWorkingMemory()) {
            //TODO AA is this necessary here
            if (localCache.isPendingRemoval(entry.getId())) return;
            if (loadOnly) {
                ((RuleSessionImpl) ruleSession).loadObject(entry);
            } else {
                if (entry instanceof SimpleEvent) {
                    ((SimpleEventImpl) entry).setRecovered(true);
                }
                ((RuleSessionImpl) ruleSession).assertObject(entry, true);
            }
            localCache.directPut(entry);
        }
    }

    protected void evict(long id) {
        synchronized (((RuleSessionImpl) ruleSession).getWorkingMemory()) {
            localCache.directRemove(id);
        }
    }

    public ControlDao<? extends Object, ? extends Object> getTxnCache() {
        return txnCache;
    }

    @Override
    public String getTransactionCacheName() {
        if (isCacheServer()) return null;
        return txnCache != null? txnCache.getName() : null;
    }

    public long getNumHandlesRecovered() {
        return numHandlesRecovered;
    }

    public long getNumHandlesError() {
        return numHandlesError;
    }

    public long getNumHandlesInStore() {
        return 0;
    }

    public List getEventSubscriptions() {
        ArrayList ret = new ArrayList();
        MetadataCache mdCache = this.cluster.getMetadataCache();
        Iterator allClasses = mdCache.getEntityConfigurations().keySet().iterator();
        while (allClasses.hasNext()) {
            Class entityClz = (Class) allClasses.next();
            // Nodes should also listen to rule-based time events from other nodes, to provide failover
            if (SimpleEvent.class.isAssignableFrom(entityClz) || TimeEvent.class.isAssignableFrom(entityClz)) {
                EntityDaoConfig config = mdCache.getEntityDaoConfig(entityClz);
                if (config != null) {
                    if (config.getCacheMode() == EntityDaoConfig.CacheMode.CacheAndMemory) {
                        ret.add(entityClz);
                    }
                }
            }
        }
        return ret;
    }

    protected void registerEventListeners() throws Exception {
        if (this.use2xMode) return;

        final String agentNameStr = "Agent " + this.getAgentName() + "-" + this.getAgentId();
        logger.log(Level.INFO, agentNameStr + ": Starting Listeners for Simple Events");

        m_eventSubscriber = new RtcEventSubscriber(this);

//        Iterator allEventSubscriptions = this.getEventSubscriptions().iterator();
//        while (allEventSubscriptions.hasNext()) {
//            Class eventClz = (Class) allEventSubscriptions.next();
//            EventTable eventQueue = cluster.getEventTableProvider().getEventTable(eventClz);
//            if (eventQueue != null) {
//                eventQueue.addEventQueueListener(m_eventSubscriber);
//        	  }
//        }
        
        m_eventSubscriber.startListener();
    }

    protected void onShutdown() throws Exception {
        if (isCacheServer()) {
            return;
        }

        final RuleServiceProviderImpl rspImpl = (RuleServiceProviderImpl) cluster.getRuleServiceProvider();
//        rspImpl.suspendChannels(ruleSession);

        final String agentNameStr = "Agent " + this.getAgentName() + "-" + this.getAgentId();
        if (logger.isEnabledFor(Level.INFO)) {
            logger.log(Level.INFO, agentNameStr + ": Unbinding RuleSession");
        }

        rspImpl.unbindRuleSession(ruleSession);

        if (logger.isEnabledFor(Level.INFO)) {
            logger.log(Level.INFO, agentNameStr + ": Waiting for Controllers to Finish");
        }
        ((RuleSessionImpl) ruleSession).getTaskController().shutdown();

        if (null != entityMediator) {
            if (logger.isEnabledFor(Level.INFO)) {
                logger.log(Level.INFO, agentNameStr + ": Shutdown Of Listeners");
            }
            if (agentConfig.listenToAgents()) {
                entityMediator.shutdownMediation();
            }
        }

//        if (null != agentTimeQueue) {
//            if (logger.isEnabledFor(Level.INFO)) {
//                logger.log(Level.INFO,"Agent " + this.getAgentName() + "-" + this.getAgentId() + ": Stopping the TimeEventQueue");
//            }
//
//            this.agentTimeQueue.shutdown();
//        }

        if (null != m_eventSubscriber) {
            if (logger.isEnabledFor(Level.INFO)) {
                logger.log(Level.INFO, agentNameStr + ": Stopping SimpleEventQueue");
            }

            this.m_eventSubscriber.shutdown();

            if (logger.isEnabledFor(Level.INFO)) {
                logger.log(Level.INFO, agentNameStr + ": Stopped SimpleEventQueue");
            }
        }

        if (logger.isEnabledFor(Level.INFO)) {
            logger.log(Level.INFO, agentNameStr + ": Setting Rule Session to INACTIVE");
        }

//        ruleSession.stop();

        if (logger.isEnabledFor(Level.INFO)) {
            logger.log(Level.INFO, agentNameStr + ": Shutdown Of Workpool threads");
        }

        if (mWorkPool != null) {
            mWorkPool.shutdown();
        }

        if (dbThreadPool != null) {
            dbThreadPool.shutdown();
        }

        if (logger.isEnabledFor(Level.INFO)) {
            logger.log(Level.INFO, agentNameStr + ": RuleSession Shutdown In Progress");
        }

        ruleSession.stopAndShutdown();

        if (logger.isEnabledFor(Level.INFO)) {
            logger.log(Level.INFO, agentNameStr + ": RuleSession Shutdown");
        }

        rspImpl.postUnbindRuleSession(ruleSession);
    }

    /**
     * @param typeId
     * @return
     */
    public RuleFunction getRegisteredRuleFunction(int typeId) {
        return registeredRuleFunctions[typeId - MetadataCache.BE_TYPE_START];
    }

    /**
     * @param typeId
     * @return
     */
    public boolean isRegisteredForDelete(int typeId) {
        return deleteSubscriptions[typeId - MetadataCache.BE_TYPE_START];
    }

    /**
     * @param typeId
     * @return
     */
    public boolean isRegisteredForChange(int typeId) {
        return changeSubscriptions[typeId - MetadataCache.BE_TYPE_START];
    }

    protected void encodeTopic() {
    	int numRegistered = cluster.getMetadataCache().getRegisteredTypes().length;
        registeredRuleFunctions = new RuleFunction[numRegistered];
        changeSubscriptions = new boolean[numRegistered];
        deleteSubscriptions = new boolean[numRegistered];

        HashSet<Integer> typeIdsOfInterest = new HashSet();
        for(Class entityClz : (Collection<Class>)((RuleSessionImpl) ruleSession).getAllScopeTypes()) {
            if(entityClz == null) continue;
                // Check to see if the rule has been written on base types
                // If yes, then turn all the bits on
            if (ConceptImpl.class.getName().equals(entityClz.getName())) {
                typeIdsOfInterest.addAll(cluster.getMetadataCache().getRegisteredConceptTypes());
            } else if (SimpleEventImpl.class.getName().equals(entityClz.getName())) {
                typeIdsOfInterest.addAll(cluster.getMetadataCache().getRegisteredSimpleEventTypes());
            } else if (TimeEventImpl.class.getName().equals(entityClz.getName())) {
                typeIdsOfInterest.addAll(cluster.getMetadataCache().getRegisteredTimeEventTypes());
            } else {
                int typeId = cluster.getMetadataCache().getTypeId(entityClz);
                // Special processing for case where complete ontology is not deployed on each engine
                if (typeId < 0) {
                    logger.log(Level.WARN, "TypeId not found for " + entityClz);
                } else {
                    typeIdsOfInterest.add(typeId);
                }
            }
        }

        HashSet<Integer> deleteTopics = new HashSet();
        HashSet<Integer> changeTopics = new HashSet();
        HashSet<Integer> subscriptions = new HashSet();
        
        boolean hasStateModel = hasStateModel();

        for(Integer typeId : (List<Integer>) cluster.getMetadataCache().getRegisteredConceptTypes()) {
            encodeTopic_OneTypeId(typeId, typeIdsOfInterest, deleteTopics, changeTopics, subscriptions, hasStateModel);
        }
        for(Integer typeId : (List<Integer>) cluster.getMetadataCache().getRegisteredSimpleEventTypes()) {
            encodeTopic_OneTypeId(typeId, typeIdsOfInterest, deleteTopics, changeTopics, subscriptions, hasStateModel);
        }
        for(Integer typeId : (List<Integer>) cluster.getMetadataCache().getRegisteredTimeEventTypes()) {
            encodeTopic_OneTypeId(typeId, typeIdsOfInterest, deleteTopics, changeTopics, subscriptions, hasStateModel);
        }

        if (subscriptions.size() > 0) {
            topicsOfInterest = cluster.getTopicRegistry().encodeTopic(subscriptions);
        }
        cluster.getTopicRegistry().register(this, changeTopics, deleteTopics);
    }
    
    /*
     * If StateModel are defined return true, false otherwise
     */
    private boolean hasStateModel() {
        for(Integer typeId : (List<Integer>) cluster.getMetadataCache().getRegisteredConceptTypes()) {
        	Class entityClz=cluster.getMetadataCache().getClass(typeId);
        	TypeDescriptor typeDescriptor = cluster.getRuleServiceProvider().getTypeManager().getTypeDescriptor(entityClz);
        	if(typeDescriptor.getType() == TypeManager.TYPE_STATEMACHINE) {
        		return true;
        	}
        }
        return false;
    }

    private void encodeTopic_OneTypeId(Integer typeId, Set<Integer> typeIdsOfInterest, Set<Integer> deleteTopics, Set<Integer> changeTopics, Set<Integer> subscriptions, boolean hasStateModel) {
        Class entityClz=cluster.getMetadataCache().getClass(typeId);
        EntityDaoConfig entityConfig=getEntityConfig(entityClz);
        RuleFunction rf=null;

        String rfURI= entityConfig.getRuleFunctionUri();
        if ((rfURI != null) && (rfURI.trim().length() > 0)){
            rf= ((RuleSessionImpl) ruleSession).getRuleFunction(rfURI);
        }

        if (entityConfig.getCacheMode() == EntityDaoConfig.CacheMode.CacheAndMemory) {
        	if((entityClz.equals(StateMachineConceptImpl.StateTimeoutEvent.class) && hasStateModel) || !entityClz.equals(StateMachineConceptImpl.StateTimeoutEvent.class)) {
                deleteTopics.add(typeId);
                deleteSubscriptions[typeId-MetadataCache.BE_TYPE_START]=true;
                subscriptions.add(typeId);
                registeredRuleFunctions[typeId-MetadataCache.BE_TYPE_START]=rf;
        	}
        }

        if (entityConfig.getCacheMode() == EntityDaoConfig.CacheMode.CacheAndMemory 
        		&& entityConfig.isSubscribed() 
        		&& typeIdsOfInterest.contains(typeId)) {
            
        	//Add only cacheAndMemory with subscription enabled explicitly
        	subscriptions.add(typeId);

            if (rf == null && entityConfig.getCacheMode() == EntityDaoConfig.CacheMode.Cache) {
                return; // No point registering interest in cache-only entity with no preprocessor
            }

            changeTopics.add(typeId);
            changeSubscriptions[typeId-MetadataCache.BE_TYPE_START]=true;
            registeredRuleFunctions[typeId-MetadataCache.BE_TYPE_START]=rf;
        }
    }

    protected void onActivate(boolean reactivate) throws Exception {

        if (isCacheServer()) return;

        final RuleServiceProviderImpl rspImpl = (RuleServiceProviderImpl) cluster.getRuleServiceProvider();
        final String agentNameStr = "Agent " + this.getAgentName() + "-" + this.getAgentId();

        logger.log(Level.INFO, agentNameStr + ": Binding RuleSession to Channels");
        rspImpl.bind(ruleSession);

        // This enables TIBRV or TOPIC destinations to be ready before any messages are published 
        // as soon as Rule session is started. This allows system to process messages published
        // in startup functions (however no messages are disseminated until channels are ACTIVATED)
        logger.log(Level.INFO, agentNameStr + ": Starting Channels in SUSPEND mode");
        rspImpl.startChannels(ruleSession, ChannelManager.SUSPEND_MODE);

        // Start rule sessions as well as Startup rule functions
        logger.log(Level.INFO, agentNameStr + ": Setting RuleSession to ACTIVE");
        if(!reactivate) ruleSession.start(true);
        else ((RuleSessionImpl)ruleSession).resume();

        // Activate Channels
        logger.log(Level.INFO, agentNameStr + ": Starting Channels in ACTIVE mode");
        rspImpl.startChannels(ruleSession, ChannelManager.ACTIVE_MODE);
        
        //((RuleServiceProviderImpl) cluster.getRuleServiceProvider()).resumeChannels(ruleSession);

        logger.log(Level.INFO, agentNameStr + ": Activating Event Subscriber");
        if (m_eventSubscriber != null) {
        	if(!reactivate) m_eventSubscriber.start();
        	else m_eventSubscriber.resumeThread();
        }
        if (entityMediator!= null) {
            entityMediator.activate();
        }

        this.logger.log(Level.INFO, "Agent %s - %s: Activated", this.getAgentName(), this.getAgentId());

    }

    protected void onDeactivate() throws Exception {

        if (isCacheServer()) return;

        if (logger.isEnabledFor(Level.INFO)) {
            this.logger.log(Level.INFO, "Agent %s - %s: DeActivated", this.getAgentName(), this.getAgentId());
        }

        //Bala: upon de-activation, release the events owned by this agent.
        cluster.getEventTableProvider().releaseEventsOwnedByAgent(getAgentId());

        //dont know why below code is there, should not be a problem to delete it
        /*Object o = ((RuleServiceProviderImpl) ruleSession.getRuleServiceProvider()).started;
        synchronized (o) {
            o.notifyAll();
        }*/
    }

    protected void onInit() throws Exception {
        this.objectStore.init();

        ((RuleSessionImpl)this.ruleSession).init(false);

        final String agentNameStr = "Agent " + this.getAgentName() + "-" + this.getAgentId();

        mWorkPool = WorkManagerFactory.create(cluster.getClusterName(), getAgentName(), getAgentId(),
                "WorkPool", agentConfig.getThreadCount(), agentConfig.getCacheOpsQueueSize(), ruleSession.getRuleServiceProvider());
        mWorkPool.start();

        jobThreadPool = WorkManagerFactory.create(cluster.getClusterName(),this.getAgentName(), this.getAgentId(),
                "AdhocWorkManager", 1, ruleSession.getRuleServiceProvider());

        jobThreadPool.start();


        if (isCacheServer()) {
            printInitializedInfo();
            return;
        }

        registerMBean();

        //TODO: Remove the Hack


        logger.log(Level.INFO, agentNameStr + ": Initializing");

        final Set<Rule> deployedRules = ((RuleSessionImpl) this.ruleSession).getWorkingMemory()
                .getRuleLoader().getDeployedRules();
        if (deployedRules != null) {
            rulesLoaded = new String[deployedRules.size()];
            Iterator itr = deployedRules.iterator();
            int j = 0;
            while (itr.hasNext()) {
                Rule rule = (Rule) itr.next();
                rulesLoaded[j++] = rule.getUri();
            }
        }


        logger.log(Level.INFO, agentNameStr + ": Connecting Channels");
        ((RuleServiceProviderImpl) cluster.getRuleServiceProvider()).connectChannels(ruleSession);


        //encodeTopic();
        //((RuleSessionImpl) ruleSession).start(false);

        if (dcUser != null) {
            dcUser.init(cluster.getMetadataCache(), cluster.getObjectTableCache());
        }

        printInitializedInfo();
    }

    private void agentIdRelatedRegistration() {
        //registerMBean();

        //Registers all of the Agent MBeans for this InferenceAgent.
        // These Agent MBeans expose some of the methods previously exposed in Hawk
        new AgentMBeansManager(this).registerAgentMBeans();

        logger.log(Level.INFO, getAgentName() + ": Initializing Rule Session, Working Memory [Concurrent=" + agentConfig.isConcurrent() + "]");
        if (agentConfig.isEnableParallelOps()) {
            if (mWorkPool == null) {
                mWorkPool = WorkManagerFactory.create(
                        cluster.getClusterName(), getAgentName(), getAgentId(),
                        getAgentName() + "$CacheWriter",
                        getAgentConfig().getThreadCount(),
                        getAgentConfig().getCacheOpsQueueSize(),
                        getRuleSession().getRuleServiceProvider());
                mWorkPool.start();
            } else {
                //todo Ashwin Merge repair
                //mWorkPool.getJobQueue().clear();
            }

            if (cluster.getClusterConfig().isHasBackingStore() && cluster.getClusterConfig().isCacheAside()) {
                if (dbThreadPool == null) {
                    dbThreadPool = WorkManagerFactory.create(
                            cluster.getClusterName(), getAgentName(), getAgentId(),
                            getAgentName() + "$DBWriter",
                            getAgentConfig().getDBThreadCount(),
                            getAgentConfig().getDBOpsQueueSize(),
                            getRuleSession().getRuleServiceProvider(),
                            new DBJobGroupManager(getAgentConfig().getDBOpsBatchSize(), cluster, this));
                    dbThreadPool.start();
                } else {
                    //todo Ashwin Merge repair
                    //dbThreadPool.getJobQueue().clear();
                }
            }
        }

        new InferenceAgentReteNetwork(this); //This registers the Network.

        agentStats = new InferenceAgentStatsManager(this);
    }

    private void startClusterEntityListener() throws Exception {

        if (this.use2xMode) return;

        final String agentName = getAgentName() + "-" + getAgentId();

        CacheChangeListener changeListener = null;

        ServiceRegistry registry = ServiceRegistry.getSingletonServiceRegistry();
        CacheChangeListenerService changeListenerService = registry.getService(CacheChangeListenerService.class);

        if (null != changeListenerService) {
            Map<RuleSession, CacheChangeListener> listenerMap = changeListenerService.getAllListeners();
            if (listenerMap != null) {
                changeListener = listenerMap.get(ruleSession);
            }
        }
        String msg = "Agent [" + agentName + "] has been registered to listen to Cache changes.";

        if (changeListener == null) {
            if (topicsOfInterest != null) {
                clusterEntityListener =    new RtcTransactionSubscriber_V2(this, topicsOfInterest);

            } else {
                logger.log(Level.INFO, "No Topics, Cache Listener not registered");
            }
        }

//TODO: 4.AS migration. This code is probably never used. Retiring it.
//        else {
//            ForwardingRtcTxnSubscriber forwardingRtcTxnSubscriber =
//                    new ForwardingRtcTxnSubscriber(this, changeListener);
//            CacheChangeListener.Config config = forwardingRtcTxnSubscriber.init();
//
//            clusterEntityListener = forwardingRtcTxnSubscriber;
//
//            String topicsStr = createTopicsString("<all>", config.getInterestedTypeIds());
//
//            logger.log(Level.INFO, msg + " Using " + CacheChangeListener.class.getSimpleName()
//                    + ": " + changeListener.getId()
//                    + " with Type Ids: " + topicsStr);
//        }

        //--------------

        boolean listen = (agentConfig.listenToAgents() && clusterEntityListener != null);
        ClusterEntityListener cel = (listen) ? clusterEntityListener : null;

        entityMediator = new ClusterEntityMediator(cluster, cel, agentConfig.isPublishTxn());
        entityMediator.startMediator(getAgentId());

    }

    private String createTopicsString(String defaultString, int[] topics) {
        String topicsStr = defaultString;

        if (topics != null) {
            topicsStr = "[";

            for (int topicId : topics) {
                //Class entityClz=cluster.getMetadataCache().getClass(topicId);
                topicsStr = topicsStr + " " + topicId;
            }

            topicsStr = topicsStr + "]";
        }
        return topicsStr;
    }

    protected void createSMTimeoutHandler() {
        String schedulerKey = cluster.getClusterName() + "." + cluster.getClusterConfig().getSiteId() + ".$smtimeouts";
        long pollInterval = ((BEProperties) cluster.getRuleServiceProvider().getProperties()).getLong("be.engine.cluster.smtimeout.pollInterval", 10000L);
        long refreshAhead = ((BEProperties) cluster.getRuleServiceProvider().getProperties()).getLong("be.engine.cluster.smtimeout.refreshAhead", 10000L);
        long intw = (pollInterval > refreshAhead ? pollInterval : refreshAhead);
        cluster.getSchedulerCache().createScheduler(schedulerKey, intw, intw, true, DefaultSchedulerCache.MODE_INFERENCEAGENT);
    }

    /**
     * @throws Exception
     */
    public void scheduleSMTimeout(long sm_id, String property_name, int count, long scheduledTime) throws Exception {
        String schedulerKey = cluster.getClusterName() + "." + cluster.getClusterConfig().getSiteId() + ".$smtimeouts";
        String tupleKey = sm_id + "." + property_name + "." + count;
        //System.err.println("###### Scheduling State Timeout for " + sm_id + ", state= " + property_name + ", time = " + scheduledTime);
        cluster.getSchedulerCache().schedule(schedulerKey, tupleKey, new SMTimeoutTask(scheduledTime));
    }

    /**
     * Schedule a job on a AdhocWorkManager.
     * @param runnable
     */
    public void scheduleWork(Runnable runnable) {
        try {
            jobThreadPool.submitJob(runnable);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * @param sm_id
     * @param property_name
     * @param count
     * @throws Exception
     */
    public void removeScheduledSMTimeout(long sm_id, String property_name, int count) throws Exception {
        String schedulerKey = cluster.getClusterName() + "." + cluster.getClusterConfig().getSiteId() + ".$smtimeouts";
        String tupleKey = sm_id + "." + property_name + "." + count;
        //System.err.println("####### Removing State Timeout for " + sm_id + ", state= " + property_name);
        cluster.getSchedulerCache().remove(schedulerKey, tupleKey);
    }

    /**
     * @param sm_id
     * @param propertyName
     * @param count
     */
    public void assertCacheSMTimeout(long sm_id, String propertyName, int count, long scheduledTime) {
    	//System.out.println("#####  Recvd On Time event" + key);
    	try {
    		Concept parentConcept = AssertSMTimeoutEventTask.getParentConcept(ruleSession, sm_id, propertyName, count);
    		if (parentConcept != null) {
    			//cache mode
    			long sme_id = ruleSession.getRuleServiceProvider().getIdGenerator().nextEntityId(StateMachineConceptImpl.StateTimeoutEvent.class);
    			StateMachineConceptImpl.StateTimeoutEvent ste = new StateMachineConceptImpl.StateTimeoutEvent(sme_id, sm_id, propertyName, scheduledTime, count);
    			//System.err.println("####### FIRE-NOTIFICATION State Timeout for " + sm_id + ", state= " + propertyName);
    			AssertSMTimeoutEventTask.processTask(ruleSession, ste, parentConcept);
    		}
    	} catch (Exception ex) {
    		ex.printStackTrace();
    	}
    }

    protected CommandChannel getCommandChannel() {
        String commandChannel = agentConfig.getCommandChannel();
        if (commandChannel != null) {
            CommandChannel channel = (CommandChannel) ruleSession.getRuleServiceProvider().getChannelManager().getChannel(commandChannel);
            if (channel != null) {
                return channel;
            }
        } else {
            ChannelManager channel_mgr = ruleSession.getRuleServiceProvider().getChannelManager();
            Iterator allChannels = channel_mgr.getChannels().iterator();
            while (allChannels.hasNext()) {
                Channel channel = (Channel) allChannels.next();
                if (channel instanceof CommandChannel) {
                    return (CommandChannel) channel;
                }
            }
        }
        return null;
    }

    protected void registerCommand() throws Exception {
        AgentCommandFactory.registerCommands(ruleSession.getRuleServiceProvider());
        agentCommandKey = cluster.getClusterName() + "." + getAgentName();
        agentCommandChannel = getCommandChannel();
        if (agentCommandChannel != null) {
            if (isCacheServer())
                agentCommandChannel.registerCommand(agentCommandKey, null);
            else
                agentCommandChannel.registerCommand(agentCommandKey, this);
        }
        // test
        agentCommandChannel.sendCommand(agentCommandKey, new AgentPingCommand(), false);
    }

    public void onCommand(CommandEvent cmd) {
        try {
            if (cmd instanceof AgentCommand) {
                ((AgentCommand) cmd).execute(this);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Deprecated
    //This method is no longer used
    protected void sendTimeEventCommand(String agentGroup, int typeId, String extId, String closure) throws Exception {
        AgentScheduleCommand cmd = new AgentScheduleCommand(typeId, extId, closure);

        String key = cluster.getClusterName() + "." + agentGroup;
        agentCommandChannel.sendCommand(key, cmd, true);
    }

    protected void onPrepareToActivate(boolean reactivate) throws Exception {
        final String agentNameStr = "Agent " + this.getAgentName() + "-" + this.getAgentId();

        logger.log(Level.INFO, agentNameStr + ": Prepare To Activate");

        if (isCacheServer()) return;

        if(!reactivate) {
	        ruleSession.reset();
	        ((RuleSessionImpl) ruleSession).resetSession();
	        encodeTopic();
        }
        
        startClusterEntityListener();
        
        if(!reactivate) {
	        registerEventListeners();
	        this.agentStats.start();
        }
        
        recover();
        logger.log(Level.INFO, agentNameStr + ": Clearing local caches");
        localCache.clear();
        if(!reactivate) {
        	createSMTimeoutHandler();

	        //---Suresh:TODO :- AJ what is Scheduling Pending Events mean?
	        logger.log(Level.INFO, agentNameStr + ": Scheduling Pending Events");
	
	        boolean enableMetrics = Boolean.parseBoolean(System.getProperty(
	        		SystemProperty.METRIC_PUBLISH_ENABLE.getPropertyName(), Boolean.FALSE.toString()));
	        if (enableMetrics) {
	            //Stop watchkeeper will provide shared stop watches
	            //For ThreadLocal watches, the last argument should be true - turning it off for now
	            RuleSessionStopWatchKeeper keeper = new RuleSessionStopWatchKeeper(getCluster().getClusterName(),
	                    ProcessInfo.getProcessIdentifier(), getAgentName(), getAgentId(), false);
	
	            ReteListener reteListener = keeper.init();
	            ((ReteWM) ((RuleSessionImpl) ruleSession).getWorkingMemory()).addReteListener(reteListener);
	        }
        }
        
        logger.log(Level.INFO, agentNameStr + ": Ready to Activate");
    }

    protected void onPrepareToDeActivate() throws Exception {
        if (isCacheServer()) {
            return;
        }

        final String agentNameStr = "Agent " + this.getAgentName() + "-" + this.getAgentId();
        if (logger.isEnabledFor(Level.INFO)) {
            logger.log(Level.INFO, agentNameStr + ": Prepare to DeActivate");
        }

        final RuleServiceProviderImpl rspImpl = (RuleServiceProviderImpl) cluster.getRuleServiceProvider();

        if (logger.isEnabledFor(Level.INFO)) {
            logger.log(Level.INFO, agentNameStr + ": Unbinding RuleSession");
        }

        rspImpl.unbindRuleSession(ruleSession);

        if (logger.isEnabledFor(Level.INFO)) {
            logger.log(Level.INFO, agentNameStr + ": Waiting for Controllers to Finish");
        }

        if (null != entityMediator) {
            entityMediator.shutdownMediation();
        }

        ((RuleSessionImpl)ruleSession).suspend();
        
        // Shutdown the cluster listener
        //this.clusterEntityListener.
        if (this.isMultiEngineMode) {
            if (m_eventSubscriber != null) {
                // Amitabh: On-deactivation, do not shutdown Event Subscriber, just suspend it
                //this.m_eventSubscriber.shutdown();
                if (this.m_eventSubscriber.isActive()) {
                    if (logger.isEnabledFor(Level.INFO)) {
                        logger.log(Level.INFO, agentNameStr + ": Suspending eventSubscriber");
                    }
                    this.m_eventSubscriber.suspendThread();
                }
            }
        }
        
        rspImpl.postUnbindRuleSession(ruleSession);
    }

    protected void onResume() throws Exception {


        final String agentNameStr = "Agent " + this.getAgentName() + "-" + this.getAgentId();
        if (isCacheServer()) {
            logger.log(Level.INFO, agentNameStr + ": Resumed");
            return;
        }

        logger.log(Level.INFO, agentNameStr + ": Resuming Channel Threads");

        ruleSession.getTaskController().resume();

        logger.log(Level.INFO, agentNameStr + ": Resuming Cluster Threads");

        if (!this.use2xMode) {
            if (entityMediator != null) {
                if (agentConfig.listenToAgents())
                    entityMediator.resumeMediation();
            }

            if (this.m_eventSubscriber != null) {
                m_eventSubscriber.resumeThread();
            }
        }
        logger.log(Level.INFO, agentNameStr + ": Resumed");
    }

    /**
     * Deactivate an external class loaded into the classloader of every
     * inference agent
     *
     * @throws Exception
     */
    @Deprecated
    protected void deActivateExternalClass() throws Exception {
        throw new RuntimeException("Please use HotDeployer.deActivateExternalClass method instead of this");
    }

    protected void onSuspend() throws Exception {

        final String agentNameStr = "Agent " + this.getAgentName() + "-" + this.getAgentId();

        if (isCacheServer()) {
            logger.log(Level.INFO, agentNameStr + ": Suspended");
            return;
        }
        logger.log(Level.INFO, agentNameStr + ": Suspend Request");

        logger.log(Level.INFO, agentNameStr + ": Suspending Channel Threads");
        ruleSession.getTaskController().suspend();

        logger.log(Level.INFO, agentNameStr + ": Suspending Cluster Threads");
        if (!this.use2xMode) {
            if (entityMediator != null) {
                if (agentConfig.listenToAgents())
                    entityMediator.suspendMediation();
            }

            if (this.m_eventSubscriber != null) {
                m_eventSubscriber.suspendThread();
            }
        }
        logger.log(Level.INFO, agentNameStr + ": Waiting for all threads to suspend");
        waitForThreadsToSuspend();


        logger.log(Level.INFO, agentNameStr + ": Suspended");
    }

    protected void waitForThreadsToSuspend() {
        while (true) {
            if (entityMediator != null) {
                if (agentConfig.listenToAgents()) {
                    if (entityMediator.isMediationRunning()) {
                        continue;
                    }
                }
            }

            if (this.m_eventSubscriber != null) {
                if (m_eventSubscriber.isRunning()) {
                    continue;
                }
            }

//            if (agentTimeQueue != null) {
//                if (agentTimeQueue.isRunning()) {
//                    continue;
//                }
//            }

            if (ruleSession.getTaskController().isRunning()) {
                continue;
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            return;
        }
    }

//    private void notifyAgent(SimpleEvent se) {
//        try {
//          System.err.println("####### Adding EVENT ########");
//            EntityDao entityProvider = cluster.getMetadataCache().getEntityDao(se.getClass());
//            entityProvider.put(se);
//            EventTable eventQueue = cluster.getEventTableProvider().getEventTable(se.getClass());
//            eventQueue.addEvent(new EventTuple(se.getId()));
//        } catch (Exception ex) {
//            throw new RuntimeException(ex);
//        }
//    }


    @Override
    public Object invokeFunction(String functionURI, Object[] args) {
        return ((RuleSessionImpl) ruleSession).invokeFunction(functionURI, args, false);
    }


    private boolean isConstant(Entity entity) {
        BEProperties props = (BEProperties) ((RuleSessionImpl) ruleSession).getRuleServiceProvider().getProperties();
        boolean val = props.getBoolean(entity.getClass().getName() + ".constant", false);
        return val;
    }

    protected void refreshConstant(long id, Class entityClz) throws Exception {
        throw new UnsupportedOperationException("Wrong InferenceAgent Configuration...");
    }

    public boolean isHandleDeleted(Long id) {
        return getDeletedIds().containsKey(id);
    }

    public void refreshEntity(long id, Class entityClass, int version) throws Exception {
        throw new UnsupportedOperationException("Incorrect configuration: NewInferenceAgent class should be used ");
    }

    public void refreshEntity(long id, int typeId, int version) throws Exception {
        throw new UnsupportedOperationException("Incorrect configuration: NewInferenceAgent class should be used ");
    }
    /*
     * Only pass accurate values for entityClz, otherwise pass null or call the other method without the class argument.
     */
    public Entity getEntityById(long id, Class entityClz) throws Exception {

        final boolean bIsDebug = logger.isEnabledFor(Level.DEBUG);
        if (bIsDebug) {
            if (EntityIdMask.isMasked(id)) {
                logger.log(Level.DEBUG, "Agent " + this.getAgentName() + "-" + this.getAgentId() + ": getEntityById called with " + EntityIdMask.getEntityId(id) + ", site=" + EntityIdMask.getMaskedId(id) + ", class=" + entityClz);
            } else {
                logger.log(Level.DEBUG, "Agent " + this.getAgentName() + "-" + this.getAgentId() + ": getEntityById called with " + id + ", class=" + entityClz);
            }
        }
        try {
            // Lookup in the local cache
            if (isHandleDeleted(id)) {
                if (bIsDebug) {
                    logger.log(Level.DEBUG, "Agent " + this.getAgentName() + "-" + this.getAgentId() + ": deleted within rtc, returning NULL " + id);
                }
                return null;
            }

            LocalCache.Entry cacheEntry = localCache.getEntry(id);
            EntityDao entityProvider = cluster.getMetadataCache().getEntityDao(entityClz);
            int typeId = cluster.getMetadataCache().getTypeId(entityProvider.getEntityClass());

            if (cacheEntry == null) {
	            Entity element = (Entity) entityProvider.get(new Long(id));
	            if (bIsDebug) {
	                logger.log(Level.DEBUG, "Agent " + this.getAgentName() + "-" + this.getAgentId() + ": Object Found in Cache " + element);
	            }

	            if (element == null) {
	                if (bIsDebug) {
	                    logger.log(Level.DEBUG, "Agent " + this.getAgentName() + "-" + this.getAgentId() + ": Object Not Found in Cache " + element);
	                }
	                if (ConceptImpl.class.isAssignableFrom(entityClz)) {
	                    Tuple tuple = cluster.getObjectTableCache().getById(id);
	                    if (tuple != null) {
	                        if (tuple.isDeleted()) {
	                            if (bIsDebug) {
	                                logger.log(Level.DEBUG, "Agent " + this.getAgentName() + "-" + this.getAgentId() + ": entry marked as deleted " + id);
	                            }
	                            return null;
	                        }
	                        throw new Exception("Database inconsistent: Object for id " + id + " exists in ObjectTable but not in base table");
	                    } else {
	                        if (bIsDebug) {
	                            logger.log(Level.DEBUG, "Agent " + this.getAgentName() + "-" + this.getAgentId() + ":: TUPLE for ID = " + id + "[" + entityClz + "] Not Found in ObjectTable");
	                        }
	                    }
	                }
	            }
	            agentStats.incrementNumMissesInL1Cache(typeId);
	            if (element != null) {
	                if (this.getAgentState() == CacheAgent.AgentState.ACTIVATED) {
	                    localCache.directPut(element);
	                }
	            }

	            if (bIsDebug) {
	                logger.log(Level.DEBUG, "Agent " + this.getAgentName() + "-" + this.getAgentId() + ":: Returning Object " + element);
	            }

	            return element;
            } else {

                if (bIsDebug) {
                    logger.log(Level.DEBUG, "Agent " + this.getAgentName() + "-" + this.getAgentId() + ": Object Found in L1Cache " + cacheEntry);
                }

                if (cacheEntry.isPendingRemoval()) {
                    if (bIsDebug) {
                        logger.log(Level.DEBUG, "Agent " + this.getAgentName() + "-" + this.getAgentId() + ": Object Found in L1Cache, Pending Removal " + cacheEntry.getRef());
                    }
                    return null;
                }

                if (!cacheEntry.isSaved()) {
                    Entity ref = cacheEntry.getRef();
                    if (bIsDebug) {
                        logger.log(Level.DEBUG, "Agent " + this.getAgentName() + "-" + this.getAgentId() + ": Object Found in L1Cache, Pending Write " + ref);
                    }

                    return ref;
                }

                Entity entry = cacheEntry.getRef();

                if (entry instanceof VersionedObject) {
                    boolean isConst = isConstant(entry);
                    if (isMultiEngineMode && !isConst) {

                        if (isInRTCCycle()) {
                            if (bIsDebug) {
                                logger.log(Level.DEBUG, "############# In getEntityById, WITHIN RTC, RETURNING LOCAL IN L1CACHE =" + entry + "," + ((VersionedObject) entry).getVersion());
                            }
                            return entry;
                        }

                        if (bIsDebug) {
                            logger.log(Level.DEBUG, "############# In getEntityById, VERSION FOUND IN L1CACHE =" + entry + "," + ((VersionedObject) entry).getVersion());
                        }


                        if (entityProvider != null) {
                            //Entity en = (Entity) entityProvider.getIfVersionGreater(entry.getId(), ((VersionedObject) entry).getVersion());
                            EntityDao.Result<Entity> result = entityProvider.getIfVersionGreater(entry.getId(), ((VersionedObject) entry).getVersion());
                            Entity en = result.getResult();
                            //Entity en=null;
                            if (en != null) {
                                if (bIsDebug) {
                                    logger.log(Level.DEBUG, "############# In getEntityById, CACHE-VERSION > L1Cache, Replacing " + en + ", new version=" + ((VersionedObject) en).getVersion());
                                }
                                agentStats.incrementNumMissesInL1Cache(typeId);
                                if (this.getAgentState() == CacheAgent.AgentState.ACTIVATED) {
                                    localCache.directPut(en);
                                }
                                return en;
                            } else {
                                if (bIsDebug) {
                                    logger.log(Level.DEBUG, "############# In getEntityById, CACHE-VERSION <= L1Cache, KEEPING THE SAME COPY");
                                }
                                agentStats.incrementNumHitsInL1Cache(typeId);
                            }
                        }
                    } else {

                        agentStats.incrementNumHitsInL1Cache(typeId);
                        if (bIsDebug) {
                            logger.log(Level.DEBUG, "############# In getEntityById, Returning " + entry);
                        }

                        return entry;
                    }
                }
                return entry;
            }
        } finally {
            //pendingQueue.release(id);
        }
    }

    /**
     * @param ids
     * @return See {@link com.tibco.cep.runtime.service.cluster.system.ObjectTable#getByIds(java.util.Collection)}
     * @throws IOException
     */
    public Collection<Tuple> getEntityTuplesById(Collection<Long> ids)
            throws IOException {
        ObjectTable objectTable = cluster.getObjectTableCache();

        return objectTable.getByIds(ids);
    }

    /**
     * @param extIds
     * @return See {@link com.tibco.cep.runtime.service.cluster.system.ObjectTable#getByExtIds(java.util.Collection)}}
     * @throws IOException
     */
    public Collection<Tuple> getEntityTuplesByExtId(
            Collection<String> extIds)
            throws IOException {
        ObjectTable objectTable = cluster.getObjectTableCache();

        return objectTable.getByExtIds(extIds);
    }

//    public Entity getRemoteElementById(String clusterName, long id) throws Exception {
//        // Lookup in the local cache
//        ClusterEntityProvider.EntityTuple tuple = cluster.getObjectTableCache().getRemoteById(clusterName, id);
//        if (tuple != null) {
//            ClusterEntityProvider entityProvider = cluster.getRemoteEntityProvider(clusterName, tuple.getTypeId());
//            if (entityProvider != null) {
//                return (Entity) entityProvider.get(tuple.getId());
//            }
//        }
//        return null;
//    }

    public Entity getEntityById(long id) throws Exception {
        final boolean debug = logger.isEnabledFor(Level.DEBUG);

        if (debug) {
            if (EntityIdMask.isMasked(id)) {
                logger.log(Level.DEBUG, "Agent " + this.getAgentName() + "-" + this.getAgentId() + ": getEntityById called with " + EntityIdMask.getEntityId(id) + ", site=" + EntityIdMask.getMaskedId(id));
            } else {
                logger.log(Level.DEBUG, "Agent " + this.getAgentName() + "-" + this.getAgentId() + ": getEntityById called with " + id);
            }
        }

        try {

            if (isHandleDeleted(id)) {
                if (debug) {
                    if (EntityIdMask.isMasked(id)) {
                        logger.log(Level.DEBUG, "Agent " + this.getAgentName() + "-" + this.getAgentId() + ": deleted within rtc, returning NULL " + EntityIdMask.getEntityId(id) + ", site=" + EntityIdMask.getMaskedId(id));
                    } else {
                        logger.log(Level.DEBUG, "Agent " + this.getAgentName() + "-" + this.getAgentId() + ": deleted within rtc, returning NULL " + id);
                    }
                }

                return null;
            }

            LocalCache.Entry cacheEntry = localCache.getEntry(id);
            if (cacheEntry == null) {

                if (debug) {
                    if (EntityIdMask.isMasked(id)) {
                        logger.log(Level.DEBUG, "Agent " + this.getAgentName() + "-" + this.getAgentId() + ": entry not found in L1Cache " + EntityIdMask.getEntityId(id) + ", site=" + EntityIdMask.getMaskedId(id));
                    } else {
                        logger.log(Level.DEBUG, "Agent " + this.getAgentName() + "-" + this.getAgentId() + ": entry not found in L1Cache " + id);
                    }
                }

                Tuple tuple = cluster.getObjectTableCache().getById(id);
                if (tuple != null) {
                    if (tuple.isDeleted()) {
                        if (debug) {
                            if (EntityIdMask.isMasked(id)) {
                                logger.log(Level.DEBUG, "Agent " + this.getAgentName() + "-" + this.getAgentId() + ": entry marked as deleted " + EntityIdMask.getEntityId(id) + ", site=" + EntityIdMask.getMaskedId(id));
                            } else {
                                logger.log(Level.DEBUG, "Agent " + this.getAgentName() + "-" + this.getAgentId() + ": entry marked as deleted " + id);
                            }
                        }
                        return null;
                    }
                    // create the handle
                    //this.objectStore.getEventHandle()
                    EntityDao entityProvider = cluster.getMetadataCache().getEntityDao(tuple.getTypeId());
                    if (entityProvider != null) {
                        Entity element = (Entity) entityProvider.get(new Long(id));

                        if (debug) {
                            logger.log(Level.DEBUG, "Agent " + this.getAgentName() + "-" + this.getAgentId() + ": got element from cache " + element);
                        }

                        if (element == null) {
                            throw new Exception("Database inconsistent: Object for Id " + id + " exists in ObjectTable but not in base table");
                        }

                        if (this.getAgentState() == CacheAgent.AgentState.ACTIVATED) {
                            if (element != null)
                                localCache.directPut(element);
                        }
                        agentStats.incrementNumMissesInL1Cache(cluster.getMetadataCache().getTypeId(entityProvider.getEntityClass()));

                        return element;
                    } else {
                        if (debug) {
                            if (EntityIdMask.isMasked(id)) {
                                logger.log(Level.DEBUG, "Agent " + this.getAgentName() + "-" + this.getAgentId() + ": Object Not Found in Cache: EntityProvider is NULL " + EntityIdMask.getEntityId(id) + ", site=" + EntityIdMask.getMaskedId(id));
                            } else {
                                logger.log(Level.DEBUG, "Agent " + this.getAgentName() + "-" + this.getAgentId() + ": Object Not Found in Cache: EntityProvider is NULL " + id);
                            }
                        }
                        return null;
                    }
                }
            } else {

                if (debug) {
                    logger.log(Level.DEBUG, "Agent " + this.getAgentName() + "-" + this.getAgentId() + ": Object Found in L1Cache " + cacheEntry);
                }

                if (cacheEntry.isPendingRemoval()) {
                    if (debug) {
                        logger.log(Level.DEBUG, "Agent " + this.getAgentName() + "-" + this.getAgentId() + ": Object Found in L1Cache, Pending Removal " + cacheEntry.getRef());
                    }
                    return null;
                }

                if (!cacheEntry.isSaved()) {
                    Entity ref = cacheEntry.getRef();
                    if (debug) {
                        logger.log(Level.DEBUG, "Agent " + this.getAgentName() + "-" + this.getAgentId() + ": Object Found in L1Cache, Pending Write " + ref);
                    }

                    return ref;
                }

                Entity entry = cacheEntry.getRef();

                if (entry instanceof VersionedObject) {

                    boolean isConst = isConstant(entry);
                    if (isMultiEngineMode && !isConst) {

                        if (isInRTCCycle()) {
                            if (debug) {
                                logger.log(Level.DEBUG, "############# In getEntityById, WITHIN RTC, RETURNING LOCAL IN L1CACHE =" + entry + "," + ((VersionedObject) entry).getVersion());
                            }
                            return entry;
                        }

                        if (debug) {
                            logger.log(Level.DEBUG, "############# In getEntityById, VERSION FOUND IN L1CACHE =" + entry + "," + ((VersionedObject) entry).getVersion());
                        }
                        EntityDao entityProvider = cluster.getMetadataCache().getEntityDao(entry.getClass());
                        if (entityProvider != null) {
                            //Entity en = (Entity) entityProvider.getIfVersionGreater(entry.getId(), ((VersionedObject) entry).getVersion());
                            EntityDao.Result<Entity> result = entityProvider.getIfVersionGreater(entry.getId(), ((VersionedObject) entry).getVersion());
                            Entity en = result.getResult();
                            //Entity en=null;
                            if (en != null) {
                                if (debug) {
                                    logger.log(Level.DEBUG, "############# In getEntityById, CACHE-VERSION > L1Cache, Replacing " + en + ", new version=" + ((VersionedObject) en).getVersion());
                                }
                                agentStats.incrementNumMissesInL1Cache(cluster.getMetadataCache().getTypeId(entityProvider.getEntityClass()));
                                if (this.getAgentState() == CacheAgent.AgentState.ACTIVATED) {
                                    localCache.directPut(en);
                                }
                                return en;
                            } else {
                                if (debug) {
                                    logger.log(Level.DEBUG, "############# In getEntityById, CACHE-VERSION <= L1Cache, KEEPING THE SAME COPY");
                                }
                            }
                        }
                    } else {
                        EntityDao entityProvider = cluster.getMetadataCache().getEntityDao(entry.getClass());
                        agentStats.incrementNumHitsInL1Cache(cluster.getMetadataCache().getTypeId(entityProvider.getEntityClass()));
                        if (debug) {
                            logger.log(Level.DEBUG, "############# In getEntityById, Returning " + entry);
                        }
                        return entry;
                    }
                }
                return entry;
            }
            return null;
        } catch (Exception ex) {
            throw ex;
        } finally {
            //pendingQueue.release(id);
        }
    }

    /**
     * @param extId       The cache should be indexed on extId.
     * @param entityClass
     * @return
     * @throws Exception
     */
    public Entity getEntityIndexedByExtId(String extId, Class entityClass) throws Exception {
        throw new UnsupportedOperationException();
    }

    public Entity getElementByExtId(String extId) throws Exception {
        int tryCount = 0;

        if ((extId != null) && (extId.length() > 0)) {
            // Re-try is not likely to resolve this issue, but leaving it in for historic reasons
            while (tryCount < 3) {
                Tuple tuple = cluster.getObjectTableCache().getByExtId(extId);
                if ((tuple != null) && !tuple.isDeleted() && !isHandleDeleted(tuple.getId())) {
                    Entity en = getEntityById(tuple.getId(), cluster.getMetadataCache().getClass(tuple.getTypeId()));
                    if (en == null) {
                        if (tryCount == 2) {
                            throw new Exception("Database inconsistent: Object for extId " + extId + " exists in ObjectTable but not in base table");
                        } else {
                            if (tryCount == 0) {
                                logger.log(Level.WARN, "Object (Type=%s, ExtId=%s, Id=%s) found in ObjectTable but not in base table. Possible race condition. Please ensure proper locking. Rechecking...", tuple.getTypeId(), extId, tuple.getId());
                            }
                            tryCount++;
                            //Try after sleep
                            Thread.sleep(1000);
                        }
                    } else {
                        return en;
                    }
                } else {
                    return null;
                }
            }
        }
        return null;
    }

//    public Entity getRemoteElementByExtId(String clusterName, String extId) throws Exception {
//        // Lookup in the local cache
//        if ((extId != null) && (extId.length() > 0)) {
//            ClusterEntityProvider.EntityTuple tuple = cluster.getObjectTableCache().getRemoteByExtId(clusterName, extId);
//            if (tuple != null) {
//                ClusterEntityProvider entityProvider = cluster.getRemoteEntityProvider(clusterName, tuple.getTypeId());
//                if (entityProvider != null) {
////                    return getEntityById(tuple.getId(), cluster.getMetadataCache().getClass(tuple.getTypeId()));
//                    return (Entity) entityProvider.get(tuple.getId());
//                }
//            }
//        }
//        return null;
//    }

    public Entity getEventByExtId(String extId) throws Exception {
        // Lookup in the local cache
        if ((extId != null) && (extId.length() > 0)) {
            Tuple tuple = cluster.getObjectTableCache().getByExtId(extId);
            if ((tuple != null) && !tuple.isDeleted() && !isHandleDeleted(tuple.getId())) {
                Entity en = getEntityById(tuple.getId(), cluster.getMetadataCache().getClass(tuple.getTypeId()));
                if (en == null) {
                    throw new Exception("Database inconsistent: Object for extId " + extId + " exists in ObjectTable but not in base table");
                }
                return en;
            }
        }
        return null;
    }

    public void addInstance(Concept cept) {
        try {
            EntityDaoConfig entityConfig = getEntityConfig(cept);
            if (entityConfig.isCacheEnabled()) {
                //((ConceptImpl) cept).incrementVersion();
                RtcTransaction txn = new RtcTransaction(++this.txnId, cluster, getRtcTransactionProperties(), cept);
                txn.recordNewConcept(cept, cluster.getMetadataCache().getTypeId(cept.getClass()), null);
                localCache.directPut(cept);
                RtcTransactionManager.publish(this, txn, null, null, null, null, false, mWorkPool, dbThreadPool);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }


    /**
     * @return
     */
    public int getNumEventThreads() {
        return ((RuleSessionImpl) ruleSession).getTaskController().threadCount();
    }

    /**
     * @return
     */
    public String[] getRuleUris() {
        return rulesLoaded;
    }

    /**
     * @return
     */
    public long getTxnCommitCount() {
        return this.numTransactionsPublished;
    }

    /**
     * @return
     */
    public long getTxnReceiveCount() {
        return this.numTransactionsSubscribed;
    }

    /**
     * @return
     */
    public double getAvgReceiveTime() {
        if (numTransactionsSubscribed > 0)
            return timeTransactionsSubscribed / numTransactionsSubscribed;
        else
            return 0.0;
    }

    /**
     * @return
     */
    public double getAvgTxnCommitTime() {
        if (numTransactionsPublished > 0)
            return timeTransactionsPublished / numTransactionsPublished;
        else
            return 0.0;
    }

    /**
     * @return
     */
    public double getHitRatio() {
        if (localCache.getNumRequests() > 0)
            return (localCache.getNumHits() / (localCache.getNumRequests() * 1.0)) * 100.00;
        else
            return 0;
    }

    /**
     * @return
     */
    public long getL1CacheMaxSize() {
        return agentConfig.getL1CacheSize();
    }

    /**
     * @return
     */
    public long getL1CacheSize() {
        return localCache.size();
    }

    public long getCacheQueueRemainingCapacity() {
        if (mWorkPool != null) {
            return mWorkPool.getJobQueue().remainingCapacity();
        } else {
            return 0L;
        }
    }

    public long getDBOpsQueueRemainingCapacity() {
        if (dbThreadPool != null) {
            return dbThreadPool.getJobQueue().remainingCapacity();
        } else {
            return 0L;
        }
    }

    /**
     * @return
     */
    public boolean isReadOnly() {
        return agentConfig.isReadOnly();
    }

    /**
     * @param handle
     * @return
     */
    protected EntityDaoConfig getEntityConfig(BaseHandle handle) {
        MetadataCache mdCache = this.cluster.getMetadataCache();

        if (handle.getTypeInfo() != null)
            return mdCache.getEntityDaoConfig(handle.getTypeInfo().getType());
        else
            throw new RuntimeException("TypeInfo in Handle is Null .... " + handle.printInfo());
    }

    public double getJobRate() {
        return ruleSession.getTaskController().getJobRate();
    }

    public long getNumJobs() {
        return ruleSession.getTaskController().getNumJobsProcessed();
    }

    /**
     * @param entity
     * @return
     */
    protected EntityDaoConfig getEntityConfig(Entity entity) {
        MetadataCache mdCache = cluster.getMetadataCache();
        return mdCache.getEntityDaoConfig(entity.getClass());

    }

    protected void printObject(String msg, Object element) {
        if (element instanceof Concept) {
            ExpandedName rootNm = ((Concept) element).getExpandedName();
            XiNode node = XiSupport.getXiFactory().createElement(rootNm);
            ((Concept) element).toXiNode(node, false);

            if (element instanceof VersionedObject) {
                msg = msg + ", Version: " + ((VersionedObject) element).getVersion();
            }

            logger.log(Level.DEBUG, msg + " = " + XiSerializer.serialize(node));
        }
    }

    /**
     * @param entityClz
     * @return
     */
    public EntityDaoConfig getEntityConfig(Class entityClz) {
        MetadataCache mdCache = this.cluster.getMetadataCache();
        return mdCache.getEntityDaoConfig(entityClz);
    }

    public RtcTransactionProperties getRtcTransactionProperties() {
        RtcTransactionProperties rtcTxnProps = (RtcTransactionProperties) m_transactionProperties.get();
        if (rtcTxnProps == null) {
            rtcTxnProps = agentConfig.getDefaultRtcTransactionProperties();
            m_transactionProperties.set(rtcTxnProps);
        }
        return rtcTxnProps;
    }

    protected void unsetRtcTransactionProperties() {
        RtcTransactionProperties rtcTxnProps = (RtcTransactionProperties) m_transactionProperties.get();
        if (rtcTxnProps != null) {
            RtcTransactionProperties defaultRtcTxnProps = agentConfig.getDefaultRtcTransactionProperties();
            rtcTxnProps.autoCacheCommit = defaultRtcTxnProps.autoCacheCommit;
            rtcTxnProps.cacheTransactionConcurrency = defaultRtcTxnProps.cacheTransactionConcurrency;
            rtcTxnProps.cacheTransactionIsolation = defaultRtcTxnProps.cacheTransactionIsolation;
            rtcTxnProps.updateCache = defaultRtcTxnProps.updateCache;
        }
        return;
    }

    protected void applyChanges_SimpleEvent(BaseHandle handle, RtcTransaction rtcTransaction, boolean removeHandles) throws Exception {
        SimpleEventImpl se = (SimpleEventImpl) handle.getObject();
        EntityDaoConfig entityConfig = getEntityConfig(handle);
        int typeId = cluster.getMetadataCache().getTypeId(se.getClass());

        if (handle.isRtcAsserted_AND_Deleted()) {
            if (entityConfig.isCacheEnabled()) {
                localCache.directRemove(se.getId());
                if (se.isLoadedFromCache()) {
                    //rtcTransaction.recordOp(RtcTransaction.OP_DEL, e,typeId);
                    rtcTransaction.recordDeleteEvent(se, typeId);
                } else {
                    agentStats.incrementNumAsserted(typeId);
                    rtcTransaction.recordAckOnlyEvent(se, typeId);
                }
            } else {
                if (se.getContext() != null) {
                    rtcTransaction.recordAckOnlyEvent(se, typeId);
                }
            }
            agentStats.incrementNumRetracted(typeId);
        } else if (handle.isRtcDeleted()) {
            if (entityConfig.isCacheEnabled()) {
//                inProgressTxnKeeper.acquire(se.getId());
                localCache.remove(se);
                rtcTransaction.recordDeleteEvent(se, typeId);
            } else {
                //do like in-memory OM and only acknowledge when the event is consumed
                rtcTransaction.recordAckOnlyEvent(se, typeId);
            }
            agentStats.incrementNumRetracted(typeId);
        } else if (handle.isRtcAsserted()) {
            if (!se.isRecovered() && !se.isLoadedFromCache()) {
                if (entityConfig.isCacheEnabled()) {
//                    inProgressTxnKeeper.acquire(se.getId());
                    localCache.put(se);
                    rtcTransaction.recordNewEvent(se, typeId, getAgentId());
                 } else {
                    //do like in-memory OM and only acknowledge when the event is consumed
                    //rtcTransaction.recordAckOnlyEvent(se,typeId);
                }
            }
            if (entityConfig.isCacheEnabled() && removeHandles) {
                removeRef(handle, entityConfig);
            }
            agentStats.incrementNumAsserted(typeId);
        } else {
            if (entityConfig.isCacheEnabled() && removeHandles) {
                removeRef(handle, entityConfig);
            }
        }
    }

    protected void applyChanges_TimeEvent(BaseHandle handle, RtcTransaction rtcTransaction, boolean removeHandles) throws Exception {
        TimeEvent te = (TimeEvent) handle.getObject();
        EntityDaoConfig entityConfig = getEntityConfig(handle);
        int typeId = cluster.getMetadataCache().getTypeId(te.getClass());

        if (handle.isRtcAsserted_AND_Deleted()) {
            if (entityConfig.isCacheEnabled()) {
                localCache.directRemove(te.getId());
                if (te.isLoadedFromCache()) {
                     rtcTransaction.recordDeleteEvent(te, typeId);
                } else {
                    agentStats.incrementNumAsserted(typeId);
                }
            }
            agentStats.incrementNumRetracted(typeId);
        } else if (handle.isRtcDeleted()) {
            if (entityConfig.isCacheEnabled()) {
                //is this check necessary?
                if (!te.isRepeating()) {
//                    inProgressTxnKeeper.acquire(se.getId());
                    localCache.remove(te);
                    rtcTransaction.recordDeleteEvent(te, typeId);
                }
            }
            agentStats.incrementNumRetracted(typeId);
        } else if (handle.isRtcScheduled()) {
            if (entityConfig.isCacheEnabled()) {
//                inProgressTxnKeeper.acquire(se.getId());
                //rtcTransaction.recordOp(RtcTransaction.OP_NEW, e,typeId);
                localCache.put(te);
                rtcTransaction.recordNewEvent(te, typeId, getAgentId());

                if (removeHandles) removeRef(handle, entityConfig);
            }
        } else if (handle.isRtcAsserted()) {
            if (entityConfig.isCacheEnabled()) {
                if (!te.isLoadedFromCache()) {
//                  inProgressTxnKeeper.acquire(te.getId());
                    localCache.put(te);
                    rtcTransaction.recordNewEvent(te, typeId, getAgentId());
                }
                if (removeHandles) removeRef(handle, entityConfig);
            }
            agentStats.incrementNumAsserted(typeId);
        } else {
            if (entityConfig.isCacheEnabled() && removeHandles) {
                removeRef(handle, entityConfig);
            }
        }
    }

    protected void applyChanges_ObjectTuple(BaseHandle handle, RtcTransaction rtcTransaction, int[] mutableDirtyBitArray, boolean removeHandles) throws Exception {
    	boolean isRtcDeleted = false;
    	ObjectBean c =  (ObjectBean) handle.getObject();
//    	int typeId = cluster.getMetadataCache().getTypeId(c.getType());
    	int typeId = c.getType().hashCode();
    	
    	if (handle.isRtcDeleted()) {
    		//agentStats.incrementNumRetracted(typeId);
    		isRtcDeleted = true;
    		rtcTransaction.recordDeleteObjectTuple(c, typeId);

    	} else if (handle.isRtcAsserted()) {
    		//agentStats.incrementNumAsserted(typeId);
    		rtcTransaction.recordNewObjectTuple(c, typeId, null);
    		
    	} else if (handle.isRtcModified()) {
    		//agentStats.incrementNumModified(typeId);
    		rtcTransaction.recordModifyObjectTuple(c, typeId, mutableDirtyBitArray, handle.getRTCStatus(), handle.isRtcAnyModificationButReverseRef());

    	}
    	if (removeHandles || isRtcDeleted ) {
    		((CacheHandle) handle).removeRef();
        	handle.rtcClearAll();
    	}
    }
    
    
    protected void applyChanges_Concept(BaseHandle handle, RtcTransaction rtcTransaction, int[] mutableDirtyBitArray, boolean removeHandles) throws Exception {
        ConceptImpl c = (ConceptImpl) handle.getObject();
        EntityDaoConfig entityConfig = getEntityConfig(handle);
        int typeId = cluster.getMetadataCache().getTypeId(c.getClass());

        if (!entityConfig.isCacheEnabled()) {
            return;
        }
        //BE-26530: Concept with TTL=0 is excluded from Rtc transaction
		if (entityConfig.getConceptTTL() == 0) {
			agentStats.incrementNumAsserted(typeId);
			if (removeHandles)
				removeRef(handle, entityConfig);
	        c.clearPersistenceModified();
			return;
		}

        if (handle.isRtcAsserted_AND_Deleted()) { //assert and delete in same rtc
            agentStats.incrementNumAsserted(typeId);
            agentStats.incrementNumRetracted(typeId);
            if (c.getVersion() > 0) {
//               inProgressTxnKeeper.acquire(c.getId());
                localCache.remove(c);
                rtcTransaction.recordDeleteConcept(c, typeId);
            }
        } else if (handle.isRtcDeleted()) {
            agentStats.incrementNumRetracted(typeId);
            if (entityConfig.isCacheEnabled()) {
//                inProgressTxnKeeper.acquire(c.getId());
                localCache.remove(c);
                rtcTransaction.recordDeleteConcept(c, typeId);
            }
        } else if (handle.isRtcAsserted()) {
            if (c.getVersion() == 0) { // New Concept created by this
//               inProgressTxnKeeper.acquire(c.getId());
                agentStats.incrementNumAsserted(typeId);
                rtcTransaction.recordNewConcept(c, typeId, null);
                localCache.put(c);
            } else { // Retrieved from the cache
                if (mutableDirtyBitArray != null) {
//                    inProgressTxnKeeper.acquire(c.getId());
                    agentStats.incrementNumModified(typeId);
                    // Don't send a subscription update for a reverse ref change since it won't affect the outcome of a condition
                    rtcTransaction.recordModifyConcept(c, typeId, mutableDirtyBitArray, handle.getRTCStatus(), handle.isRtcAnyModificationButReverseRef());
                    localCache.put(c);
                }
            }
        } else if (handle.isRtcAnyModification()) {
            agentStats.incrementNumModified(typeId);
            if (entityConfig.isCacheEnabled()) {
//                inProgressTxnKeeper.acquire(c.getId());
                // Don't send a subscription update for a reverse ref change since it won't affect the outcome of a condition
                rtcTransaction.recordModifyConcept(c, typeId, mutableDirtyBitArray, handle.getRTCStatus(), handle.isRtcAnyModificationButReverseRef());
                localCache.put(c);
            }
        }
        if (removeHandles) removeRef(handle, entityConfig);
        c.clearPersistenceModified();
    }

    public void applyChanges(RtcOperationList rtcList, boolean removeHandles, boolean releaseLocks) throws Exception {
        try {
        	LockManager lockMgr = cluster.getLockManager();
            Collection<AgentAction> actions = actionMgr.removeAll();
//			Collection<LockManager.LockData> locks = Collections.EMPTY_LIST;
//			if (lockMgr.hasLockDataStuckToThread()) {
//				locks = lockMgr.takeLockDataStuckToThread();
//			}
           
            Collection<LockManager.LockData> locks = null;
            if (releaseLocks)  {
            	locks = lockMgr.takeLockDataStuckToThread();
            }

            if (rtcList.isEmpty()) {
                if (((actions != null) && (actions.size() > 0)) ||
                        ((locks != null) && (locks.size() > 0))) {
                    RtcTransactionManager.publish(this, null, rtcList.getTrigger(), null, locks, actions, agentConfig.isEnableParallelOps(), mWorkPool, dbThreadPool);
                }
                return;
            }
            if (!rtcList.hasOps()) {
                this.logger.log(Level.DEBUG, "InferenceAgent: No Ops");

                Iterator ite = rtcList.iterator();
                while (ite.hasNext()) {
                    BaseHandle handle = (BaseHandle) ite.next();
                    if (removeHandles && (handle.getTypeInfo() != null)) {
                        removeRef(handle);
                    }

                    handle.rtcClearAll();
                }
                if (((actions != null) && (actions.size() > 0)) || ((locks != null) && (locks.size() > 0))) {
                    RtcTransactionManager.publish(this, null, rtcList.getTrigger(), null, locks, actions, agentConfig.isEnableParallelOps(), mWorkPool, dbThreadPool);
                }
                return;
            }

            if (this.isReadOnly()) {
                Iterator ite = rtcList.iterator();
                while (ite.hasNext()) {
                    BaseHandle handle = (BaseHandle) ite.next();
                    if (removeHandles && (handle.getTypeInfo() != null)) {
                        removeRef(handle);
                    }
                    handle.rtcClearAll();
                }
                if (((actions != null) && (actions.size() > 0)) || ((locks != null) && (locks.size() > 0))) {
                    RtcTransactionManager.publish(this, null, rtcList.getTrigger(), null, locks, actions, agentConfig.isEnableParallelOps(), mWorkPool, dbThreadPool);
                }
                return;
            }

            int rtcEntries = 0;
            long startTime = System.currentTimeMillis();
            this.logger.log(Level.DEBUG, "InferenceAgent: Applying Changes... ");
            try {
                Iterator ite = rtcList.RtcListEntryIterator();

                RtcTransaction rtcTransaction = new RtcTransaction(++txnId, cluster, getRtcTransactionProperties(), rtcList.getTrigger());
                while (ite.hasNext()) {
                    RtcOperationList.RtcListEntry rtcEntry = (RtcOperationList.RtcListEntry) ite.next();
                    BaseHandle handle = rtcEntry.handle;
                    if (handle.getTypeInfo() != null) {
                        Object obj = handle.getObject();
                        if (obj instanceof SimpleEvent) {
                            applyChanges_SimpleEvent(handle, rtcTransaction, removeHandles);
                        } else if (obj instanceof TimeEvent) {
                            applyChanges_TimeEvent(handle, rtcTransaction, removeHandles);
                        } else if (obj instanceof Concept) {
                            applyChanges_Concept(handle, rtcTransaction, rtcEntry.mutableDirtyBitArray, removeHandles);
                        } else if(obj instanceof ObjectBean) {
                        	applyChanges_ObjectTuple(handle, rtcTransaction, rtcEntry.mutableDirtyBitArray, removeHandles);
                        } else {
                        	removeRef(handle);
                        }
                    }
                    if (handle != null) {
                        handle.rtcClearAll();
                    }
                    rtcEntries++;
                }

                /* unsafe to use rtcList  or getTransactionProperties() after this point since an error handler callback clears it */

                if (rtcTransaction.hasOperations() || rtcTransaction.getAckEvents().size() > 0) {
                    Collection<Integer> includedTypes = rtcTransaction.getTypeIds();
                    if (includedTypes.size() > 0) {
                        int[] topic = cluster.getTopicRegistry().encodeTopic(includedTypes);
                        RtcTransactionManager.publish(this, rtcTransaction, null,topic, locks, actions, agentConfig.isEnableParallelOps(), mWorkPool, dbThreadPool);
                        ++numTransactionsPublished;
                    } else {
                        RtcTransactionManager.publish(this, rtcTransaction, null, null, locks, actions, agentConfig.isEnableParallelOps(), mWorkPool, dbThreadPool);
                        ++numTransactionsPublished;
                    }
                } else {

                    this.logger.log(Level.DEBUG, "DefaultDistributedCacheBasedStore: No Operations....");

                    if (((actions != null) && (actions.size() > 0)) || ((locks != null) && (locks.size() > 0))) {
                        this.logger.log(Level.DEBUG, "DefaultDistributedCacheBasedStore:Releasing Locks If Held....");
                        RtcTransactionManager.publish(this, rtcTransaction, null, null, locks, actions, agentConfig.isEnableParallelOps(), mWorkPool, dbThreadPool);
                    }
                }
            } finally {
                getDeletedIds().clear();
            }

            this.logger.log(Level.DEBUG, "DistributedCache: Submitted %s Changes. Time Taken = %d",
                            rtcEntries, (System.currentTimeMillis()-startTime));
        } finally {
            unsetRtcTransactionProperties();
        }
    }

    /**
     * @param key
     */

    public void onTimeEvent(Object key) {
        //System.out.println("#####  Recvd On Time event" + key);
        try {
            Long id = (Long) key;
            EntityDao entityProvider = cluster.getMetadataCache().getEntityDao(StateMachineConceptImpl.StateTimeoutEvent.STATETIMEOUTEVENT_TYPEID);
            StateMachineConceptImpl.StateTimeoutEvent se = (StateMachineConceptImpl.StateTimeoutEvent) entityProvider.get(id);

            if (se != null) {
                // this.objectStore.getAddHandle(se);
                ConceptImpl parent = (ConceptImpl) objectStore.getElement(se.sm_id);
                if (parent != null) {
                    if (parent.isMarkedDeleted()) {
                        return;
                    }
                    ruleSession.assertObject(se, true);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    /**
     * @return
     */
    public String getCurrentState() {
        return this.getAgentState().name();
    }

    private void removeRef(BaseHandle handle) throws Exception {
        EntityDaoConfig config = getEntityConfig(handle);
        removeRef(handle, config);
    }

    private void clearReferences(BaseHandle handle) {
        if (handle instanceof CacheHandle) {
            Object obj = ((CacheHandle) handle).getRef();
            if (obj instanceof ConceptImpl) {
                ((ConceptImpl) obj).clearAllReferences();
            }
        }
    }

    private void removeRef(BaseHandle handle, EntityDaoConfig config) throws Exception {
        RuleSessionImpl session = (RuleSessionImpl) this.getRuleSession();

        if (config != null) {
            switch (config.getCacheMode()) {
                case CacheAndMemory:
                    clearReferences(handle);
                    ((CacheHandle) handle).removeRef();
//                    if (config.isConstant()) {
//                        refreshConstant(((CacheHandle) handle).getId(), handle.getTypeInfo().getType());
//                    }
                    //l1Cache.directPut(((Entity) handle.getObject()));
                    break;
                case Cache:
                    //Object obj=handle.getObject();
                    ((WorkingMemoryImpl) session.getWorkingMemory()).evictHandle(handle);
                    clearReferences(handle);
                    Object entity = ((CacheHandle) handle).getRef();

                    if (entity != null) {
                        ((CacheHandle) handle).removeRef();
                    }
                    if (!agentConfig.keepHandles())
                        this.objectStore.removeHandleForCacheOnly(handle, entity);

//                    if (config.isConstant()) {
//                        refreshConstant(((CacheHandle) handle).getId(), handle.getTypeInfo().getType());
//                    }

                    break;
                case Memory:
                    break;
            }
        } else {
            ((CacheHandle) handle).removeRef();
        }
        handle.rtcClearAll();
    }


    public void recover() throws Exception {

        if (!agentConfig.recoverOnStartup()) {
            logger.log(Level.INFO, "Skipping recovery...");
            return;
        }

        bInRecovery = true;
        RuleSessionImpl session = (RuleSessionImpl) this.getRuleSession();

        Thread.currentThread().setContextClassLoader((ClassLoader) session.getRuleServiceProvider().getTypeManager());


        long startTime = System.currentTimeMillis();
        numHandlesRecovered = 0;


        WorkPool recoveryJobs = new WorkPool();
        try {
            this.logger.log(Level.INFO, "DefaultDistributedCacheBasedStore:Recovering objects/events from the caches....");

            if (!session.cacheServer) {
                for (EntityDao c : cluster.getDaoProvider().getAllEntityDao()) {
                    int num_e = 0;
                    int typeId = cluster.getMetadataCache().getTypeId(c.getEntityClass());

                    if (typeId == StateMachineConceptImpl.StateTimeoutEvent.STATETIMEOUTEVENT_TYPEID) {
                        continue;
                    }
                    if (this.isMultiEngineMode && Event.class.isAssignableFrom(c.getEntityClass())) {
                        continue;
                    }

                    EntityDaoConfig entityConfig = this.cluster.getMetadataCache().getEntityDaoConfig(c.getEntityClass());

                    // Only do recovery if the store is a traditional store
                    BackingStore backingStore = cluster.getRecoveryBackingStore();
                    boolean traditionalStore = false;
                    if (backingStore != null) {
                    	traditionalStore = true;
                    }

                    if (entityConfig.getCacheMode() == EntityDaoConfig.CacheMode.CacheAndMemory) {
                        boolean loadFromCache = true; // Always load from cache
                        boolean loadFromDB = entityConfig.hasBackingStore() && traditionalStore && (entityConfig.isCacheLimited() || entityConfig.isEvictOnUpdate() || !cluster.getRecoveryManager().isLoaded(typeId));

                        this.logger.log(Level.INFO, "Recovery[%s]= LoadFromCache=%s, LoadFromDB=%s",
                                c.getEntityClass().getName(), loadFromCache, loadFromDB);

                        // Phase 0: Recover objects from the cache
                        long counter = 0;
                        long loadtime = System.currentTimeMillis();
                        if (loadFromCache) {
                            String agentKey = this.agentConfig.getAgentKey();

                            Collection<Entity> recoverObjects = c.getAll();
                            for (Entity entity : recoverObjects) {
                                counter++;
                                entity.setLoadedFromCache();

                                //System.err.println("##### Recovering " + entity);
                                if (this.isMultiEngineMode && entity instanceof NamedInstance && agentKey != null && !entity.getExtId().startsWith(agentKey)) {
                                    continue;
                                }
                                boolean handleExists = objectStore.handleExists(entity);
                                if (!handleExists) {
                                    entity.setLoadedFromCache();
                                    CacheHandle handle = null;
                                    try {
                                        Handle h = objectStore.createHandle(entity);
                                        handle = (CacheHandle) ((RuleSessionImpl) this.ruleSession).loadObject(h);
                                        handle.removeRef();
                                        this.agentStats.incrementNumRecovered(typeId);
                                        numHandlesRecovered++;
                                        num_e++;
                                    } catch (DuplicateExtIdException dex) {
                                        dex.printStackTrace();
                                        if (handle != null) {
                                            handle.removeRef();
                                        }
                                    }
                                } else { // handle exists in DefaultDistributedCacheBasedStore but may not be in RETE
                                    BaseHandle h = (BaseHandle) objectStore.getHandle(entity);
                                    if (!h.isInRete()) { // handle not in rete
                                        CacheHandle handle = (CacheHandle) ((RuleSessionImpl) this.ruleSession).loadObject(h);
                                        this.agentStats.incrementNumRecovered(typeId);
                                        numHandlesRecovered++;
                                        num_e++;
                                    }
                                }
                            }
                            this.logger.log(Level.DEBUG, "Recovery [" + c.getEntityClass().getName() + "]= LoadFromCacheCount=" + counter + ", LoadFromCacheTime=" + (System.currentTimeMillis()-loadtime));
                        }

                        // Phase 1: Recover objects from the underlying store
                        counter = 0;
                        loadtime = System.currentTimeMillis();
                        if (loadFromDB) {
                            Iterator recoverObjects = cluster.getRecoveryManager().getObjects(typeId);
                            while (recoverObjects.hasNext()) {
                                    counter++;
                                Entity entity = (Entity) recoverObjects.next();
                                if (entity instanceof NamedInstance && !((ConceptImpl) entity).getExtId().startsWith(this.agentConfig.getAgentKey())) {
                                    continue;
                                }
                                boolean handleExists = objectStore.handleExists(entity);
                                if (!handleExists) {
                                    entity.setLoadedFromCache();
                                    CacheHandle handle = null;
                                    try {
                                        Handle h = objectStore.createHandle(entity);
                                        handle = (CacheHandle) ((RuleSessionImpl) this.ruleSession).loadObject(h);
                                        handle.removeRef();
                                        this.agentStats.incrementNumRecovered(typeId);
                                        numHandlesRecovered++;
                                        num_e++;
                                    } catch (DuplicateExtIdException dex) {
                                        dex.printStackTrace();
                                        if (handle != null) {
                                            handle.removeRef();
                                        }
                                    }
                                } else { // handle exists in DefaultDistributedCacheBasedStore but may not be in RETE
                                    BaseHandle h = (BaseHandle) objectStore.getHandle(entity);
                                    if (!h.isInRete()) { // handle not in rete
                                        CacheHandle handle = (CacheHandle) ((RuleSessionImpl) this.ruleSession).loadObject(h);
                                        this.agentStats.incrementNumRecovered(typeId);
                                        numHandlesRecovered++;
                                        num_e++;
                                    }
                                }
                            }
                            this.logger.log(Level.DEBUG, "Recovery [" + c.getEntityClass().getName() + "]: LoadFromDBCount=" + counter + ", LoadFromDBTime=" + (System.currentTimeMillis()-loadtime));
                        }
                        this.logger.log(Level.INFO, "Element recovery: type=%s, handles loaded=%d",
                                c.getEntityClass().getName(), num_e);
                    }
                }

                if (this.isMultiEngineMode) {
                    recoverEvents(recoveryJobs);
                }

                if (mWorkPool != null) {
                    recoveryJobs.submit(mWorkPool);
                    recoveryJobs.waitForCompletion();
                    if (recoveryJobs.hasException()) {
                        throw new RuntimeException(recoveryJobs.getException());
                    }
                } else {
                    WorkManager recovery = WorkManagerFactory.create(cluster.getClusterName(), getAgentName(), getAgentId(),
                            getAgentName() + "$Recovery",
                            getAgentConfig().getThreadCount(),
                            getRuleSession().getRuleServiceProvider());
                    recovery.start();
                    recoveryJobs.submit(recovery);
                    recoveryJobs.waitForCompletion();
                    recovery.shutdown();
                    if (recoveryJobs.hasException()) {
                        throw new RuntimeException(recoveryJobs.getException());
                    }
                }

                this.logger.log(Level.INFO, "Number of handles loaded   = %d", this.numHandlesRecovered);
                this.logger.log(Level.INFO, "Number of handles in error = %d", this.numHandlesError);

                this.logger.log(Level.DEBUG, "Agent recovery: done.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            bInRecovery = false;
        }
        long endTime = System.currentTimeMillis();
        this.logger.log(Level.INFO, "-------- Agent recovery time: %s seconds -------",
                ((endTime - startTime) / 1000.00));

        bInRecovery = false;
    }

    public void printL1CacheContents(String filePathAndName) throws IOException {
        File file = new File(filePathAndName);
        file.getParentFile().mkdirs();

        OutputStream streamToClose = null;

        try {
            FileOutputStream fos = new FileOutputStream(file);
            streamToClose = fos;

            BufferedOutputStream bos = new BufferedOutputStream(fos);
            streamToClose = bos;

            PrintStream ps = new PrintStream(bos);
            streamToClose = ps;

            logger.log(Level.WARN, "Starting to write L1Cache contents to file: " + file.getAbsolutePath());

            localCache.printContents(ps);

            logger.log(Level.WARN, "Successfully wrote L1Cache contents to file: " + file.getAbsolutePath());

            streamToClose.flush();
            streamToClose.close();
            streamToClose = null;
        }
        catch (IOException e) {
            if (streamToClose != null) {
                try {
                    streamToClose.flush();
                    streamToClose.close();
                }
                catch (IOException e1) {
                    logger.log(Level.WARN,
                            "[" + InferenceAgent.class.getSimpleName() + "-printL1CacheContents]",
                            "Error occurred while writing L1Cache contents to file: " +
                                    file.getAbsolutePath(), e1);
                }
            }
            throw e;
        }
    }

    public void printLockManagerDetails() {
        LockManager lockManager = cluster.getLockManager();

        ConcurrentMap<Object, LockRecorder.DoubleLevelLockRecord> records =
                lockManager.getLockRecords();

        if (records != null) {
            LockManagerViewer viewer = new LockManagerViewer();
            StringBuilder builder = viewer.collectEverything(records, lockManager);

            logger.log(Level.WARN, builder.toString());
        } else {
            logger.log(Level.WARN, "Lock recording might not have been turned on.");
        }
    }

    /**
     * @return
     */
    public long getTotalLocksHeldCount() {
        LockManager lockManager = cluster.getLockManager();
        Map records = lockManager.getLockRecords();
        
        if (records != null) {
        	return records.size();
        } else {
        	return 0;
        }
    }
    
    /**
     * @return
     */
    public long getLocalLocksHeldCount() {
    	LockManager lockManager = cluster.getLockManager();
    	return LockManagerViewer.getLocksCountByLevel(lockManager.getLockRecords(), LockManager.LockLevel.LEVEL1);
    }
    
    /**
     * @return
     */
    public long getClusterWideLocksHeldCount() {
    	LockManager lockManager = cluster.getLockManager();
    	return LockManagerViewer.getLocksCountByLevel(lockManager.getLockRecords(), LockManager.LockLevel.LEVEL2);
    }
    
    public void printAsynchronousWorkerDetails() {
        ServiceRegistry serviceRegistry = ServiceRegistry.getSingletonServiceRegistry();
        AsyncWorkerServiceWatcher serviceWatcher = serviceRegistry.getWorkManagerWatcher();
        StringBuilder stats = null;

        if (serviceWatcher != null) {
            stats = serviceWatcher.collectStats();
        }

        if (stats != null) {
            logger.log(Level.WARN, stats.toString());
        } else {
            logger.log(Level.WARN, "Asynchronous worker watcher service might not have been enabled. Or," +
                    " there were no details to capture.");
        }
    }

    public void printMemoryInfo(String filePath) throws IOException {
        FileWriter fw = null;
        String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss.SSSZ";
        final SimpleDateFormat FORMAT = new SimpleDateFormat(DATETIME_FORMAT);
        GregorianCalendar calendar = (GregorianCalendar) GregorianCalendar.getInstance();

        try {
            fw = new FileWriter(filePath);
            PrintWriter pw = new PrintWriter(fw);
            Map<String, TypeStats> typeMap = new HashMap<String, TypeStats>();
            for (Iterator it = objectStore.getHandles().iterator(); it.hasNext();) {
                final Handle h = (Handle) it.next();
                final String clazz = h.getTypeInfo().getType().getName();
                TypeStats stats = null;
                if (!typeMap.containsKey(clazz)) {
                    stats = new TypeStats(clazz);
                } else {
                    stats = typeMap.get(clazz);
                }
                stats.incrementNumHandles();
                if (((BaseHandle) h).isInRete()) {
                    stats.incrementNumRete();
                }
                if (h.hasRef()) {
                    stats.incrementNumRefs();
                    Entity e = (Entity) h.getObject();
                    if (e != null && localCache.hasEntity(e.getId())) {
                        stats.incrementNumL1Cache();
                    }
                }
                typeMap.put(clazz, stats);
            }
            //typeClazz+","+numHandles+","+numRefs+","+numL1Cache+","+numRete;
//            fw.write("\"Classname\"\t\t,\"Num. Handles\"\t\t,\"Num. Refs\"\t\t,\"Num. Refs in L1Cache\"\t\t,\"Num. Handles in WM\"\n");
            pw.format("\"%-100s\",\"%20s\",\"%20s\",\"%20s\",\"%32s\"\n", "Classname", "Num. Handles in WM", "Num. Refs", "Num. Handles in Rete", "Timestamp");
            for (Iterator ite = typeMap.entrySet().iterator(); ite.hasNext();) {
                Map.Entry<String, TypeStats> entry = (Map.Entry<String, TypeStats>) ite.next();
                TypeStats stats = entry.getValue();
                pw.format("\"%-100s\",\"%20d\",\"%20d\",\"%20d\",\"%32s\"\n"
                        , stats.getTypeClazz()
                        , stats.getNumHandles()
                        , stats.getNumRefs()
                        , stats.getNumRete()
                        , FORMAT.format(calendar.getTime()));
//                fw.write(entry.getValue().toString());
            }
        } catch (IOException e) {
            this.logger.log(Level.ERROR, e, "");
            throw e;
        } finally {
            if (fw != null) {
                fw.close();
            }
        }
    }

    public void refreshLocalConcept(Concept cept) {
        LocalCache.Entry lc = localCache.getEntry(cept.getId());
        if (lc != null && !lc.isPendingRemoval()) {
            if (((ConceptImpl) lc.getRef()).getVersion() != ((ConceptImpl) cept).getVersion()) {
                if (logger.isEnabledFor(Level.DEBUG)) {
                    logger.log(Level.DEBUG, "Id=" + cept.getId() + " L1Cache.version=" + ((ConceptImpl) lc.getRef()).getVersion() + " != Cache.version=" + ((ConceptImpl) cept).getVersion());
                }
                WorkingMemory wm = ((RuleSessionImpl) getRuleSession()).getWorkingMemory();
                synchronized (wm) {
//                    localCache.directPut(cept);
                }
            }
        }
    }

    public Map<Long, Long> getDeletedIds() {
        Map<Long, Long> ret = (Map<Long, Long>) deletedIds.get();
        if (ret == null) {
            ret = new ConcurrentHashMap<Long, Long>();
            deletedIds.set(ret);
        }
        return ret;
    }

    public void assertObject(Object object, CommandEvent.CommandContext ctx) throws Exception {
        getRuleSession().getTaskController().processTask(WorkerBasedController.DEFAULT_POOL_NAME, new AssertCommandTask(getRuleSession(), ctx, object));
    }

    public InferenceAgentStatsManager getAgentStatsManager() {
        return this.agentStats;
    }

    class TypeStats {
        String typeClazz;
        long numHandles;
        long numRefs;
        long numL1Cache;
        long numRete;

        TypeStats(String clazz) {
            this.typeClazz = clazz;
            this.numHandles = this.numL1Cache = this.numRefs = this.numRete = 0;
        }

        TypeStats(long numHandles, long numL1Cache, long numRefs, long numRete, String typeClazz) {
            this.numHandles = numHandles;
            this.numL1Cache = numL1Cache;
            this.numRefs = numRefs;
            this.numRete = numRete;
            this.typeClazz = typeClazz;
        }

        public long getNumHandles() {
            return numHandles;
        }

        public long incrementNumHandles() {
            return ++this.numHandles;
        }

        public long getNumL1Cache() {
            return numL1Cache;
        }

        public long incrementNumL1Cache() {
            return ++this.numL1Cache;
        }

        public long getNumRefs() {
            return numRefs;
        }

        public long getNumRete() {
            return numRete;
        }

        public long incrementNumRete() {
            return ++this.numRete;
        }

        public long incrementNumRefs() {
            return ++this.numRefs;
        }

        public String getTypeClazz() {
            return typeClazz;
        }

        public String toString() {
            return "\"" + typeClazz + "\"\t\t,\"" + numHandles + "\"\t,\"" + numRefs + "\"\t,\"" + numL1Cache + "\"\t,\"" + numRete + "\"\n";
        }
    }

    protected void recoverEvents(WorkPool workPool) throws Exception {
        Iterator allEventSubscriptions = this.getEventSubscriptions().iterator();
        while (allEventSubscriptions.hasNext()) {
            Class eventClz = (Class) allEventSubscriptions.next();
            EventTable eventQueue = cluster.getEventTableProvider().getEventTable(eventClz);
            workPool.addJob(new RecoverEvents(eventQueue));
        }
    }

    protected void recoverEvent(EventTable eventQueue) throws Exception {
        if (eventQueue == null) return;
        int num_e = 0;
        int recoverHandlesPageSize = Integer.parseInt(ruleSession.getRuleServiceProvider().getProperties().getProperty("be.engine.coherence.recoverHandlesPageSize", "0"));
//        Iterator recEvents =  eventQueue.getEntityDao().getAll(recoverHandlesPageSize);
//
//        // make the events available for recovery
//        while (recEvents.hasNext()) {
//            Map.Entry<Long,Event> cacheEntry = (Map.Entry<Long,Event>) recEvents.next();
//            if(!eventQueue.containsKey(cacheEntry.getKey())) {
//                EntityConfiguration config= (EntityConfiguration) getEntityConfig(eventQueue.getEventClz());
//                if(config.getCacheMode() != EntityConfiguration.CACHE_MODE_CACHEONLY) {
//                    eventQueue.recoverEvent((Long) cacheEntry.getKey());
//                }
//            }
//        }
        Iterator allEvents = eventQueue.getHandles(new AvailableEventFilter(), recoverHandlesPageSize);
        while (allEvents.hasNext()) {
            Map.Entry cacheEntry = (Map.Entry) allEvents.next();
            if (eventQueue.acquireEvent((Long) cacheEntry.getKey(), this.getAgentId())) {
                Event evt = eventQueue.getOwnerEvent((EventTuple) cacheEntry.getValue());
                if (evt != null) {
                    try {
                        evt.setLoadedFromCache();
                        CacheHandle handle = (CacheHandle) ((RuleSessionImpl) ruleSession).loadObject(evt);
                        if (handle != null) {
                            handle.removeRef();
                        }
                        num_e++;
                        numHandlesRecovered++;
                    } catch (DuplicateExtIdException dex) {
                        dex.printStackTrace();
                    }
                }
            }
        }
        this.logger.log(Level.INFO, "Event recovery: type=%s, handles acquired=%d,",
                eventQueue.getEventClass().getName(), num_e);
    }

    ////////////////////////////////////////// RETE MBean

    public boolean getConcurrent() {
        return this.getAgentConfig().isConcurrent();
    }

    public long getTotalNumberRulesFired() {
        return ((ReteWM) ((RuleSessionImpl) ruleSession).getWorkingMemory()).getTotalNumberRulesFired();
    }

    public void saveReteNetworkToXML() {
        ((ReteWM) ((RuleSessionImpl) ruleSession).getWorkingMemory()).saveReteNetworkToXML();
    }

    public void saveReteNetworkToXML(String filename) {
        ((ReteWM) ((RuleSessionImpl) ruleSession).getWorkingMemory()).saveReteNetworkToXML(filename);
    }

    public void saveReteNetworkToString() {
        ((ReteWM) ((RuleSessionImpl) ruleSession).getWorkingMemory()).saveReteNetworkToString();
    }

    public void saveReteNetworkToString(String filename) {
        ((ReteWM) ((RuleSessionImpl) ruleSession).getWorkingMemory()).saveReteNetworkToString(filename);
    }

    /////////////////////////////////////////////////////


    class RecoverEvents implements Runnable {
        EventTable eventQueue;

        RecoverEvents(EventTable eventQueue) {
            this.eventQueue = eventQueue;
        }

        public void run() {
            try {
                recoverEvent(eventQueue);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    class AssertCommandTask implements Runnable {
        RuleSession session;
        Object object;
        CommandEvent.CommandContext ctx;

        AssertCommandTask(RuleSession session,
                          CommandEvent.CommandContext ctx,
                          Object object) {
            this.session = session;
            this.object = object;
            this.ctx = ctx;
        }

        public void run() {
            try {
                ((RuleSessionImpl) session).assertObject(object, true);
                ctx.acknowledge();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    /**
     * @param extId       extID to lookup
     * @param entityClass the class to which it belongs
     * @return
     * @throws Exception
     */
    public Entity getElementByUri(String extId, Class entityClass) throws Exception {

        TypeDescriptor td = ruleSession.getRuleServiceProvider().getTypeManager().getTypeDescriptor(entityClass);
        if (td != null) {
            String uri = td.getURI();
            //put the extid / uri in the cache; so that its available to underlying store implementation
            if (uri != null) {
                cluster.getObjectTableCache().putExtIdToTypeCache(extId, uri);
            }
        }
        return getElementByExtId(extId);
    }

    /**
     * @param extId       extID to lookup
     * @param entityClass the class to which it belongs
     * @return
     * @throws Exception
     */
    public Entity getEventByUri(String extId, Class entityClass) throws Exception {

        TypeDescriptor td = ruleSession.getRuleServiceProvider().getTypeManager().getTypeDescriptor(entityClass);
        if (td != null) {
            String uri = td.getURI();
            //put the extid / uri in the cache; so that its available to underlying store implementation
            if (uri != null) {
                cluster.getObjectTableCache().putExtIdToTypeCache(extId, uri);
            }
        }
        return getEventByExtId(extId);
    }
    
    @Override
    public void entitiesAdded() {
    	agentStats.entitiesAdded();
    	encodeTopic();
    	//EntityMediator & EntityListener are initialized only when c+m entities subscribe for changes
    	//In case of hot deployment, cdd is not reloaded hence new c+m config is not applicable
    	//If EntityListener is not initialized skip adding entities to listener
    	if(clusterEntityListener != null) {
    		clusterEntityListener.entitiesAdded();
    	}
    	//update registerEventListeners() if hotdeploy of new events is added
    }
    
    @Override
    public void entitiesChanged(Collection<Class<Entity>> changedClasses) {
    	localCache.entitiesChanged(changedClasses);
    }
    
    //to be used by RtcTransactionManager.
    //this is passed to some RTCTransactionManager functions for the cacheWriter argument
    public WorkManager getPostRTCWorkPool() {
        return mWorkPool;
    }
}