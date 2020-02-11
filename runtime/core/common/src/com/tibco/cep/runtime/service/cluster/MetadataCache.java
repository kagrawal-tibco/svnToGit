/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.cluster;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.eclipse.emf.common.util.EList;

import com.tibco.be.model.rdf.RDFTnsFlavor;
import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.be.noop.kit.LocalMap;
import com.tibco.be.util.config.CddTools;
import com.tibco.be.util.config.cdd.AgentClassConfig;
import com.tibco.be.util.config.cdd.AgentConfig;
import com.tibco.be.util.config.cdd.ArtifactConfig;
import com.tibco.be.util.config.cdd.ClusterConfig;
import com.tibco.be.util.config.cdd.EntityConfig;
import com.tibco.be.util.config.cdd.LiveViewAgentClassConfig;
import com.tibco.be.util.config.cdd.ProcessingUnitConfig;
import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.element.PropertyDefinition;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.model.TypeManager.TypeDescriptor;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.impl.LocalStateTimeoutEvent;
import com.tibco.cep.runtime.model.element.impl.StateMachineConceptImpl;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.model.event.TimeEvent;
import com.tibco.cep.runtime.model.event.impl.AdvisoryEventImpl;
import com.tibco.cep.runtime.service.cluster.backingstore.CacheAsideBackingStore;
import com.tibco.cep.runtime.service.cluster.system.ControlKey;
import com.tibco.cep.runtime.service.cluster.system.IMetadataCache;
import com.tibco.cep.runtime.service.loader.BEClassLoader;
import com.tibco.cep.runtime.service.om.api.ControlDao;
import com.tibco.cep.runtime.service.om.api.ControlDaoType;
import com.tibco.cep.runtime.service.om.api.EntityDao;
import com.tibco.cep.runtime.service.om.api.EntityDaoConfig;
import com.tibco.cep.runtime.service.om.api.EntityDaoConfig.CacheMode;
import com.tibco.cep.runtime.service.om.api.EntityDaoConfigCreator;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.util.SystemProperty;
import com.tibco.cep.util.annotation.Optional;
import com.tibco.xml.data.primitive.ExpandedName;

/**
 * Created by IntelliJ IDEA. User: apuneet Date: Nov 14, 2007 Time: 8:00:25 AM Modified: Suresh
 */
public class MetadataCache implements IMetadataCache {
	//TODO:6 change it to a meaningful value
    private static final String MIN_VALUE = "MIN_VALUE";

	protected static Logger logger = LogManagerFactory.getLogManager().getLogger(MetadataCache.class);

    Cluster cluster;

    ControlDao<String, byte[]> masterCache;

    Map<String, Integer> typeIdDao;// = new HashMap<String, Integer>();
    
    Map<String, Integer> lockTableDao;

    ControlDao<String, Object> recoveryCoordinatorDao;

    MetadataDescriptor[] metadataDescriptors = new MetadataDescriptor[0];

    Map<Class, MetadataDescriptor> classToMetadata = new HashMap<Class, MetadataDescriptor>();

    Map<String, MetadataDescriptor> classNameToMetadata = new HashMap<String, MetadataDescriptor>();

    Map<Class, EntityDaoConfig> entityDaoConfigs = new HashMap<Class, EntityDaoConfig>();
    
    boolean hasStarted = false;

    // Whether to recover individual spaces, or through metaspace (applies to SharedNothing only) 
    boolean recoverSpaces = Boolean.parseBoolean(System.getProperty(SystemProperty.DATAGRID_RECOVERY_DISTRIBUTED.getPropertyName(), "false"));

    int numberOfThreads = Integer.parseInt(System.getProperty(SystemProperty.DATAGRID_RECOVERY_THREADS.getPropertyName(), "5"));

    ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
//    ASDaoProvider daoProvider;
    public MetadataCache() {
        
    }

    public void init(Cluster cluster) throws Exception {
        this.cluster = cluster;
        
		ClusterProvider clusterProvider = cluster.getClusterProvider();
//		daoProvider = clusterProvider.getDaoProvider();

        this.masterCache = clusterProvider.
                createControlDao(String.class, byte[].class, ControlDaoType.Master, cluster);

       /* this.typeIdDao = clusterProvider.
                createControlDao(String.class, Integer.class, ControlDaoType.TypeIds, cluster);*/
        
        //this.typeIdDao = defaultStore.createOrGetStoreControlDao(String.class, Integer.class, ControlDaoType.TypeIds.getAlias(), cluster);
        this.typeIdDao = new LocalMap(ControlDaoType.TypeIds.getAlias(), ControlDaoType.TypeIds);
        //TODO - What in case of only cluster config?
        
        //this.lockTableDao = defaultStore.createStoreControlDao(String.class, Integer.class, "LockTable", cluster);

        this.recoveryCoordinatorDao = clusterProvider.
                createControlDao(String.class, Object.class, ControlDaoType.RecoveryCoordinator, cluster);

        initEntityDaoConfiguration();
    }

    private void initEntityDaoConfiguration() {
        BEClassLoader clzLoader = (BEClassLoader) cluster.getRuleServiceProvider().getClassLoader();
        Set<Map.Entry> entities = clzLoader.getRegisteredEntities();

        for (Map.Entry entry : entities) {
            TypeManager.TypeDescriptor type = (TypeManager.TypeDescriptor) entry.getValue();
            if (type.getImplClass() != null) {
                if ((type.getType() != TypeManager.TYPE_RULE) && (type.getType() != TypeManager.TYPE_RULEFUNCTION)) {
                    Class clz = type.getImplClass();
                    EntityDaoConfigCreator.createAndPutIfAbsent(cluster, clz, entityDaoConfigs);
                }
            }
        }

        Class clz = StateMachineConceptImpl.StateTimeoutEvent.class;
        //TODO:6 changed from c+m to c
//        entityDaoConfigs.put(clz, EntityDaoConfigCreator.createDefault(cluster, clz, CacheMode.CacheAndMemory));
        entityDaoConfigs.put(clz, EntityDaoConfigCreator.createDefault(cluster, clz, CacheMode.Cache));


        clz = AdvisoryEventImpl.class;
        //it may have been cache+memory previously but since it has ttl 0 it should be ok for it to be memory-only
        entityDaoConfigs.put(clz, EntityDaoConfigCreator.createDefault(cluster, clz, CacheMode.Memory));

        clz = LocalStateTimeoutEvent.class;
        entityDaoConfigs.put(clz, EntityDaoConfigCreator.createDefault(cluster, clz, CacheMode.Memory));
    }

    public void start() throws Exception {
        logger.log(Level.INFO, "Starting [%s]", getClass().getSimpleName());

        coordinateClusterRecovery();
        
        loadTypes();

        hasStarted = true;
        
        logger.log(Level.INFO, "Started [%s]", getClass().getSimpleName());
    }
    
    public boolean hasStarted() {
        return hasStarted;
    }

    protected void coordinateClusterRecovery() throws Exception {
        MetadataRecoveryStateKey sharedStateKey = new MetadataRecoveryStateKey();
        MetadataRecoverState recoverState = MetadataRecoverState.unknown;

        for ( ;recoverState != MetadataRecoverState.recovered; ) {
            recoverState = fetchSharedState(sharedStateKey);

            logger.log(Level.INFO, "Current cluster-wide metadata recovery state [%s]", recoverState.name());

            switch (recoverState) {
                case recovered: {
                    logger.log(Level.INFO, "Metadata recovery already performed by another agent");
                    setTypeIdFromRecovered();
                    break;
                }

                case recovering: {
                    logger.log(Level.INFO,
                            "Metadata recovery being performed by another agent. Waiting for it to complete...",
                            recoverState);

                    boolean lockAcquired = recoveryCoordinatorDao.lock(sharedStateKey.toString(), 10000);
                    if (lockAcquired) {
                        recoveryCoordinatorDao.unlock(sharedStateKey.toString());
                    }
                    Thread.sleep(5000);
                    continue;
                }

                case unknown:
                case recovery_failed:
                default: {
                    boolean lockAcquired = recoveryCoordinatorDao.lock(sharedStateKey.toString(), 10000);
                    if (lockAcquired == false) {
                        logger.log(Level.INFO, "Cluster-wide shared metadata lock could not be acquired. Retrying...");
                        continue;
                    }

                    try {
                        recoverState = fetchSharedState(sharedStateKey);

                        // Uh oh, someone has already done the recovery.
                        if (recoverState == MetadataRecoverState.recovered) {
                            continue;
                        } else {
                            recoveryCoordinatorDao.put(sharedStateKey.toString(), MetadataRecoverState.recovering);
                            boolean success = recover();
                            if (success) {
                            	recoverState = MetadataRecoverState.recovered;
                            	recoveryCoordinatorDao.put(sharedStateKey.toString(), recoverState);
                            } else {
                            	recoveryCoordinatorDao.remove(sharedStateKey.toString());
                            }
                        }
                    } finally {
                        recoveryCoordinatorDao.unlock(sharedStateKey.toString());
                    }
                }
            }
        }
    }

    private MetadataRecoverState fetchSharedState(MetadataRecoveryStateKey sharedStateKey) {
        MetadataRecoverState recoverState = (MetadataRecoverState) recoveryCoordinatorDao.get(sharedStateKey);

        if (recoverState == null) {
            recoverState = MetadataRecoverState.unknown;
        }

        return recoverState;
    }

    protected boolean recover() {
    	try {
	        logger.log(Level.INFO, "Attempting to recover cluster-wide metadata ");
	
	        CacheAsideBackingStore store = (CacheAsideBackingStore) cluster.getBackingStore();
	
	        Map<String, Integer> typeRegistry = null;
//	        if (store != null && store instanceof BackingStore) {
	            typeRegistry = store.getClassRegistry();
//	        }
	
	        loadClasses(typeRegistry);
	
	        store.saveClassRegistry(typeRegistry);

	
	        logger.log(Level.INFO, "Cluster-wide metadata recovery completed");
	        return true;
    	}
    	catch (Exception e) {
	        logger.log(Level.WARN, e, "Cluster-wide metadata recovery failed");
    		return false;
    	}
    }

    public void reloadTypes() throws Exception {
        BEClassLoader clzLoader = (BEClassLoader) cluster.getRuleServiceProvider().getClassLoader();
        Set<Map.Entry> entries = clzLoader.getRegisteredEntities();
        ArrayList<TypeDescriptor> newEntries = new ArrayList();

        for (Map.Entry entry : entries) {
            TypeDescriptor td = (TypeDescriptor) entry.getValue();
            Class implClass = td.getImplClass();
            if (implClass != null && (td.getType() != TypeManager.TYPE_RULE) && (td.getType() != TypeManager.TYPE_RULEFUNCTION)) {
                MetadataDescriptor md = classToMetadata.get(implClass);
                if (md == null) {
                    newEntries.add(td);
                } else {
                    reloadType(td, md, false);
                }
            }
        }
        int oldLen = metadataDescriptors.length;
        metadataDescriptors = Arrays.copyOf(metadataDescriptors, oldLen + newEntries.size());
        CacheAsideBackingStore store = (CacheAsideBackingStore) cluster.getBackingStore();
        for (TypeDescriptor td : newEntries) {
            reloadType(td, null, true);
            if (store != null) {
                Class clz = td.getImplClass();
                store.saveClassRegistration(clz.getName(), getTypeId(clz));
            }
        }
    }

    //md is null when isNew is true
    private void reloadType(TypeDescriptor td, MetadataDescriptor md, boolean isNew) throws Exception {
    	Class clz = td.getImplClass();
    	int typeId;
    	if (isNew) {
    		typeId = registerType(clz);
    	} else {
    		typeId = md.entityTypeId;
    	}
    	
    	EntityDao entityDao = createEntityDaoAndRegister(clz, td.getURI(), typeId);
        if (entityDao != null) {
            entityDao.start(!isNew);
        }
    }

    protected void loadTypes() throws Exception {
        long startTime = System.currentTimeMillis();
        logger.log(Level.INFO, "Attempting to load types and initialize [%s]s", EntityDao.class.getSimpleName());
        int maxTypeId = getLastTypeId();

        metadataDescriptors = new MetadataDescriptor[(maxTypeId - BE_TYPE_START + 1)];
        Iterator allTypes = new TreeMap<String, Integer>(typeIdDao).entrySet().iterator();

        List<Callable<Object>> startList = new LinkedList<Callable<Object>>();
        List<Callable<Object>> readyList = new LinkedList<Callable<Object>>();

        while (allTypes.hasNext()) {
            Map.Entry entry = (Map.Entry) allTypes.next();
            String uri = (String) entry.getKey();
            TypeManager.TypeDescriptor td = cluster.getRuleServiceProvider().getTypeManager().getTypeDescriptor(uri);
            int typeId = (Integer) entry.getValue();
            Class entityClass = getEntityClassFor(td, uri);
            
            AgentClassConfig agentClassConfig = onlyLiveViewAgent();
            boolean joinSpace = (agentClassConfig != null) ? joinSpace(agentClassConfig, uri) : true;
            
            if (entityClass != null) {
                final EntityDao entityDao = createEntityDaoAndRegister(entityClass, uri, typeId);
                if (entityDao != null && joinSpace) {
                    Callable<Object> callstart = new Callable<Object>() {
                        @Override
                        public Object call() throws Exception {
                            try {
                                entityDao.start(false);
                            } catch (Exception asex) {
                                logger.log(Level.ERROR, (asex.getCause() == null) ? asex:asex.getCause(), 
                                        "Initialization of entity [%s] failed. Shutting down this node.", 
                                        entityDao.getName());
                                System.exit(-1);
                            }
                            return null;
                        }
                    };
                    startList.add(callstart);
                    
                    Callable<Object> callready = new Callable<Object>() {
                        @Override
                        public Object call() throws Exception {
                            try {
                                entityDao.waitUntilReady(recoverSpaces);
                            } catch (Exception asex) {
                                logger.log(Level.ERROR, (asex.getCause() == null) ? asex:asex.getCause(), 
                                        "Readiness of entity [%s] failed. Shutting down this node.", 
                                        entityDao.getName());
                                System.exit(-1);
                            }
                            return null;
                        }
                    };
                    readyList.add(callready);
                }
            }
        }

        executorService.invokeAll(startList);
        logger.log(Level.INFO, "Created %d types and initialized [%s]s with %d threads in %d secs", 
                startList.size(), EntityDao.class.getSimpleName(), numberOfThreads, 
                (System.currentTimeMillis() - startTime) / 1000L);
//TODO:6 removed for now, perhaps not required
//        if (recoverSpaces == false) {
//           daoProvider.recoverCluster(cluster);
//        }

        executorService.invokeAll(readyList);
        logger.log(Level.INFO, "Loaded %d types and recovered [%s]s with %d threads in %d secs", 
                readyList.size(), EntityDao.class.getSimpleName(), numberOfThreads,
                (System.currentTimeMillis() - startTime) / 1000L);
        
        executorService.shutdown();
    }

    private Class getEntityClassFor(TypeManager.TypeDescriptor td, String uri) {
        if (td == null) {
            Class entityClass = null;
            if (uri.equals(LocalStateTimeoutEvent.class.getName())) {
                entityClass = LocalStateTimeoutEvent.class;
            } else if (uri.equals(StateMachineConceptImpl.StateTimeoutEvent.class.getName())) {
                entityClass = StateMachineConceptImpl.StateTimeoutEvent.class;
            } else if (uri.equals(AdvisoryEventImpl.class.getName())) {
                entityClass = AdvisoryEventImpl.class;
            }
            return entityClass;
        } else {
            return td.getImplClass();
        }
    }
    
    private AgentClassConfig onlyLiveViewAgent() {
    	RuleServiceProvider rsp = cluster.getRuleServiceProvider();
    	Properties properties = rsp.getProperties();
        ClusterConfig cc = (ClusterConfig) properties.get(SystemProperty.CLUSTER_CONFIG.getPropertyName());
        final String pucId = properties.getProperty(SystemProperty.PROCESSING_UNIT_ID.getPropertyName());

        ProcessingUnitConfig puc = (ProcessingUnitConfig) CddTools.findById(
        		(EList<? extends ArtifactConfig>) cc.getProcessingUnits().getProcessingUnit(), pucId);
        
        AgentClassConfig liveviewAgentConfig = null;
        for (AgentConfig agentConfig : puc.getAgents().getAgent()) {
        	AgentClassConfig configClass = agentConfig.getRef();
        	if (!(configClass instanceof LiveViewAgentClassConfig)) {
        		return null;
        	}
        	liveviewAgentConfig = configClass;
        }
        return liveviewAgentConfig;
    }
    
    private boolean joinSpace(AgentClassConfig agentClassConfig, String uri) {
    	boolean joinSpace = false;
    	
    	if (agentClassConfig instanceof LiveViewAgentClassConfig) {
    		LiveViewAgentClassConfig liveviewAgentConfig = (LiveViewAgentClassConfig) agentClassConfig;
    		for (EntityConfig ec : liveviewAgentConfig.getEntitySet().getEntity()) {
        		if (!ec.getUri().isEmpty() && ec.getUri().equals(uri)) {
        			joinSpace = true;
        		}
        	}
    	}
    	
    	return joinSpace;
    }

    /**
     * Register the Type and class to the TypeIdDao
     *
     * @param entityClass
     * @return the registered TypeId
     */
    public int registerType(Class entityClass) {
        TypeManager.TypeDescriptor typeDescriptor = cluster.getRuleServiceProvider().getTypeManager().getTypeDescriptor(entityClass);
        if (typeDescriptor == null) return -1;


        String uri = typeDescriptor.getURI();
        try {
            /*
            Cannot use typeIdDao.lock(uri, -1) - because AS does a put(k, null) on a lock. This would then return a null value
            when doing typeIdDao.get(uri).
            */
            masterCache.lock(MIN_VALUE, -1);
            //lockTableDao.lock();
            Integer typeIdObj = typeIdDao.get(uri);
            int typeId;
            if (typeIdObj == null) {
                typeId = this.getLastTypeId() + 1;
                typeIdDao.put(uri, typeId);
            } else {
                typeId = typeIdObj;
            }

            typeDescriptor.setTypeId(typeId);

            EntityDaoConfigCreator.createAndPutIfAbsent(cluster, entityClass, entityDaoConfigs);

            return typeId;
        } finally {
            masterCache.unlock(MIN_VALUE);
        }
    }

    /**
     * Made public since this is required for registering external concept classes if any.
     *
     * @param entityClass
     * @param uri
     * @param typeId
     * @return
     * @throws Exception 
     */
    
    public EntityDao createEntityDaoAndRegister(Class entityClass, String uri, int typeId) throws Exception {
        EntityDao entityDao = null;

        EntityDaoConfig daoConfig = entityDaoConfigs.get(entityClass);

        cluster.getBECacheProvider().createOrGetEntityDao(entityClass, daoConfig, true);

        MetadataDescriptor md = new MetadataDescriptor(uri, entityClass, typeId, entityDao);
        metadataDescriptors[typeId - BE_TYPE_START] = md;
        //metadataDescriptors.add(md);

        classToMetadata.put(entityClass, md);
        classNameToMetadata.put(entityClass.getName(), md);
        
        TypeDescriptor td = cluster.getRuleServiceProvider().getTypeManager().getTypeDescriptor(entityClass);
        if (td != null)	td.setTypeId(typeId);

        return entityDao;
    }
    
    protected void shutdown() {
        typeIdDao.clear();
    }

    //typeMap is classname -> typeId, typeIdDao is URI => typeId
    private void loadClasses(Map<String, Integer> backingStoreTypeMap) throws Exception {
        int lastTypeId;
        if (backingStoreTypeMap == null) {
            lastTypeId = getLastTypeId();
        } else {
            lastTypeId = getLastTypeId(backingStoreTypeMap);
        }

        BEClassLoader clzLoader = (BEClassLoader) cluster.getRuleServiceProvider().getClassLoader();
        Set<Map.Entry> entries = clzLoader.getRegisteredEntities();
        for (Map.Entry entry : entries) {
            TypeManager.TypeDescriptor type = (TypeManager.TypeDescriptor) entry.getValue();
            Class clz = type.getImplClass();
            if (clz != null) {
                if ((type.getType() != TypeManager.TYPE_RULE) && (type.getType() != TypeManager.TYPE_RULEFUNCTION)) {
                    String URI = getURI((ExpandedName) entry.getKey());
                    String clzName = clz.getName();
                    //typeMap is classname -> typeId, typeIdDao is URI => typeId
                    Integer objTypeId;
                    if (backingStoreTypeMap == null) {
                        objTypeId = typeIdDao.get(URI);
                    } else {
                        objTypeId = backingStoreTypeMap.get(clzName);
                    }

                    int typeId;
                    if (objTypeId == null) {
                        typeId = ++lastTypeId;
                        if (backingStoreTypeMap != null) {
                        	backingStoreTypeMap.put(clzName, typeId);
                        }
                        typeIdDao.put(URI, typeId);
                    } else {
                        typeId = objTypeId;
                        //recovering existing typeIds from backing store
                        //TODO:BALA:6 add-to-bsmap-as well????
//                        if (backingStoreTypeMap != null) typeIdDao.put(URI, typeId);
                        if (backingStoreTypeMap != null) {
                        	typeIdDao.put(URI, typeId);
                            //backingStoreTypeMap.put(URI, typeId);
                        }
                        
                    }

                    type.setTypeId(typeId);
                }
            }
        }

        addSystemTypes(typeIdDao);
        if (backingStoreTypeMap != null) {
			addSystemTypes(backingStoreTypeMap);
		}
    }
    
    private void setTypeIdFromRecovered() throws Exception {

        BEClassLoader clzLoader = (BEClassLoader) cluster.getRuleServiceProvider().getClassLoader();
        Set<Map.Entry> entries = clzLoader.getRegisteredEntities();
        for (Map.Entry entry : entries) {
            TypeManager.TypeDescriptor type = (TypeManager.TypeDescriptor) entry.getValue();
            Class clz = type.getImplClass();
            if (clz != null) {
                if ((type.getType() != TypeManager.TYPE_RULE) && (type.getType() != TypeManager.TYPE_RULEFUNCTION)) {
                    String URI = getURI((ExpandedName) entry.getKey());
                    Integer objTypeId = typeIdDao.get(URI);
                    type.setTypeId(objTypeId);
                }
            }
        }

        addSystemTypes(typeIdDao);
    }

    protected String getURI(ExpandedName en) {
        return en.getNamespaceURI().substring(RDFTnsFlavor.BE_NAMESPACE.length());
    }

    protected void addSystemTypes(Map<String, Integer> typeMap) {
        if (typeMap == null) {
            return;
        }

        /** Hack all over the place for types that are internal
         *  Ideally these internal types should have an entry in the typeRegistry
         */
        typeMap.put(StateMachineConceptImpl.StateTimeoutEvent.class.getName(),
                StateMachineConceptImpl.StateTimeoutEvent.STATETIMEOUTEVENT_TYPEID);

        typeMap.put(AdvisoryEventImpl.class.getName(),
                AdvisoryEventImpl.ADVISORYEVENTIMPL_TYPEID);

        typeMap.put(LocalStateTimeoutEvent.class.getName(),
                LocalStateTimeoutEvent.LOCALSTATETIMEOUTEVENT_TYPEID);
    }

    protected int getLastTypeId(Map<String, Integer> typeMap) {
        int maxTypeId = BE_TYPE_INTERNAL_END;
        if (typeMap.size() > 0) {
            Collection<Integer> values = typeMap.values();
            for (int typeId : values) {
                if (typeId > maxTypeId) {
                    maxTypeId = typeId;
                }
            }
        }
        return maxTypeId;
    }

    /**
     * Get max type id of all registered entities.
     *
     * @return
     */
    public int getLastTypeId() {
        return getLastTypeId(typeIdDao);
    }


//    /**
//     * @param typeId
//     * @param id
//     * @param extId
//     * @return
//     * @throws Exception
//     */
//    public com.tibco.cep.kernel.model.entity.Entity createEntity(int typeId, Id id) throws Exception {
//        MetadataDescriptor md = metadataDescriptors[typeId - BE_TYPE_START];
//        //MetadataDescriptor md = metadataDescriptors.get(typeId - BE_TYPE_START);
////        Constructor cons = md.entityConstructorTwoArgs;
//        return (com.tibco.cep.kernel.model.entity.Entity) cons.newInstance(new Object[]{id, extId});
//    }

    /**
     * @return
     */
    public Class[] getRegisteredTypes() {
        Class[] classes = new Class[metadataDescriptors.length];
        int i = 0;
        for (MetadataDescriptor md : metadataDescriptors) {
            if (md != null && md.entityClass != null) {
                classes[i] = md.entityClass;
            }
            i++;
        }
        return classes;
    }

    /**
     * @param typeId
     * @return
     */
    public List<Integer> getInheritedTypes(int typeId) {

        ArrayList<Integer> retList = new ArrayList<Integer>();
        Class c = metadataDescriptors[typeId - BE_TYPE_START].entityClass;
        for (int i = 0; i < metadataDescriptors.length; i++) {
            MetadataDescriptor md = metadataDescriptors[i];
            if (i != (typeId - BE_TYPE_START)) {
                if ((md != null) && (md.entityClass != null)) {
                    if (c.isAssignableFrom(md.entityClass)) {
                        retList.add(md.entityTypeId);
                    }
                }
            }
        }
        return retList;
    }

    /**
     * @param resourceURI
     * @return
     * @throws Exception
     */
    public List<Integer> getContainedTypes(String resourceURI) throws Exception {
        ArrayList<Integer> retList = new ArrayList();
        Ontology ontology = cluster.getRuleServiceProvider().getProject().getOntology();
        Entity entity = ontology.getEntity(resourceURI);
        if (entity instanceof com.tibco.cep.designtime.model.element.Concept) {
            com.tibco.cep.designtime.model.element.Concept cept =
                    (com.tibco.cep.designtime.model.element.Concept) entity;
            List allProperties = cept.getLocalPropertyDefinitions();
            if (allProperties != null) {
                for (int i = 0; i < allProperties.size(); i++) {
                    PropertyDefinition pd = (PropertyDefinition) allProperties.get(i);
                    if (pd.getType() == RDFTypes.CONCEPT_TYPEID) {
                        String childPath = pd.getConceptTypePath();
                        com.tibco.cep.designtime.model.element.Concept child = ontology.getConcept(childPath);
                        TypeManager.TypeDescriptor td = cluster.getRuleServiceProvider().getTypeManager()
                                .getTypeDescriptor(child.getFullPath());
                        retList.add(getTypeId(td.getImplClass()));
                    }
                }
            }
        }
        return retList;
    }

    /**
     * @return
     */
    public ArrayList getRegisteredConceptTypes() {
        ArrayList retList = new ArrayList();
        for (MetadataDescriptor md : metadataDescriptors) {
            if (md != null) {
                if (Concept.class.isAssignableFrom(md.entityClass)) {
                    retList.add(md.entityTypeId);
                }
            }
        }
        return retList;
    }

    public ArrayList getRegisteredSimpleEventTypes() {
        ArrayList retList = new ArrayList();
        for (MetadataDescriptor md : metadataDescriptors) {
            if (md != null) {
                if (SimpleEvent.class.isAssignableFrom(md.entityClass)) {
                    retList.add(md.entityTypeId);
                }
            }
        }
        return retList;
    }

    public ArrayList getRegisteredTimeEventTypes() {
        ArrayList retList = new ArrayList();
        for (MetadataDescriptor md : metadataDescriptors) {
            if (md != null) {
                if (TimeEvent.class.isAssignableFrom(md.entityClass)) {
                    retList.add(md.entityTypeId);
                }
            }
        }
        return retList;
    }

    /**
     * @param entityClz
     * @return
     * @throws Exception
     */
    public Integer getTypeId(Class entityClz) {
        if (entityClz == null) {
            return -1;
        }
        MetadataDescriptor md = classToMetadata.get(entityClz);
        if (md != null) {
            return md.entityTypeId;
        }
        return -1;
    }

    public int getTypeId(String clzName) throws Exception {
        if (clzName == null) {
            throw new RuntimeException("MetadataCache - getTypeId: Null parameter passed");
        }

        MetadataDescriptor md = classNameToMetadata.get(clzName);
        if (md == null) {
            throw new RuntimeException("Entity DAO doesnot exists for class:" + clzName);
        }
        return md.entityTypeId;
    }

    /**
     * @param typeId
     * @return
     * @throws Exception
     */
    public Class getClass(int typeId) {
        if ((typeId < 0) || (typeId >= metadataDescriptors.length + BE_TYPE_START)) {
            throw new RuntimeException("Invalid typeId specified.");
        }
        MetadataDescriptor md = metadataDescriptors[typeId - BE_TYPE_START];
        if (md == null) {
            return null;
            //throw new RuntimeException("Invalid typeId specified.");
        }
        return md.entityClass;
    }

    /**
     * @param typeId
     * @return
     * @throws Exception
     */
    public com.tibco.cep.kernel.model.entity.Entity newInstance(int typeId) throws Exception {
        if ((typeId < 0) || (typeId >= metadataDescriptors.length + BE_TYPE_START)) {
            throw new RuntimeException("Invalid typeId specified.");
        }
        MetadataDescriptor md = metadataDescriptors[typeId - BE_TYPE_START];
        if (md == null) {
            throw new RuntimeException("No metadata descriptor for typeId:" + typeId);
        }
        Constructor cons = md.entityConstructorNoArgs;
        return (com.tibco.cep.kernel.model.entity.Entity) cons.newInstance(new Object[]{});
    }

    /**
     * @param typeId
     * @return
     */
    public EntityDao<Long, com.tibco.cep.kernel.model.entity.Entity> getEntityDao(int typeId) {
        if ((typeId < 0) || (typeId >= metadataDescriptors.length + BE_TYPE_START)) {
            throw new RuntimeException("Invalid typeId specified.");
        }
        MetadataDescriptor md = metadataDescriptors[typeId - BE_TYPE_START];
        if (md == null) {
            return null;
            //throw new RuntimeException("Invalid typeId specified.");
        }
        return md.entityDao;
    }

    public boolean isValidTypeId(int typeId) {
        return typeId > BE_TYPE_START && typeId < BE_TYPE_START + metadataDescriptors.length;
    }

    public EntityDao getEntityDao(Class clazz) {
        if (clazz == null) {
            throw new RuntimeException("getEntityDao: Null parameter passed");
        }

        MetadataDescriptor md = classToMetadata.get(clazz);
        if (md == null) {
            throw new RuntimeException("Entity DAO doesnot exists for class:" + clazz.getName());
        }
        return md.entityDao;
    }

    @Deprecated
    public static byte[] makeDeletedTuple(int typeId) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(bos);
            dos.writeInt(typeId);
            dos.writeBoolean(true);
            dos.writeLong(System.currentTimeMillis());
            return bos.toByteArray();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public int size() {
        return metadataDescriptors.length;
    }

    public EntityDaoConfig getEntityDaoConfig(Class aClass) {
        return entityDaoConfigs.get(aClass);
    }

    public Map<Class, EntityDaoConfig> getEntityConfigurations() {
        return entityDaoConfigs;
    }

    //--------------

    protected static class MetadataDescriptor {
        protected String uri;

        @Optional
        protected Class entityClass = null;

        @Optional
        protected int entityTypeId = -1;

        @Optional
        protected EntityDao entityDao = null;

        @Optional
        protected Constructor entityConstructorNoArgs = null;

        protected MetadataDescriptor(String uri, int entityTypeId) {
            this.uri = uri;
            this.entityTypeId = entityTypeId;
        }

        protected MetadataDescriptor(String uri, Class entityClass, int entityTypeId) {
            this.uri = uri;
            this.entityClass = entityClass;
            this.entityTypeId = entityTypeId;
        }

        protected MetadataDescriptor(String uri, Class entityClass, int entityTypeId, EntityDao entityDao) {
            this.uri = uri;
            this.entityClass = entityClass;
            this.entityTypeId = entityTypeId;
            this.entityDao = entityDao;
//TODO:6 handle 2 arg ctor..
            try {
                this.entityConstructorNoArgs = entityClass.getConstructor(new Class[]{});
//                this.entityConstructorTwoArgs = entityClass.getConstructor(new Class[]{Long.class, String.class});
            } catch (NoSuchMethodException nsme) {
                //eat it.
            }
        }
    }

    public static class MetadataRecoveryStateKey implements ControlKey {
        protected String fixedKey = "$sharedState";

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof MetadataRecoveryStateKey)) {
                return false;
            }

            MetadataRecoveryStateKey that = (MetadataRecoveryStateKey) o;

            if (!fixedKey.equals(that.fixedKey)) {
                return false;
            }

            return true;
        }

        @Override
        public int hashCode() {
            return fixedKey.hashCode();
        }
    }

    public static enum MetadataRecoverState {
        unknown,
        recovering,
        recovered, recovery_failed
    }

	@Override
	public EntityDaoConfig getEntityDaoConfig(int typeId) {
		// TODO Auto-generated method stub
		return null;
	}
}
