/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.be.functions.coherence;

import com.tangosol.net.CacheFactory;
import com.tangosol.net.NamedCache;
import com.tangosol.util.ValueExtractor;

import static com.tibco.be.model.functions.FunctionDomain.*;

import com.tibco.be.model.functions.Enabled;
import com.tibco.be.model.util.ModelNameUtil;
import com.tibco.cep.kernel.core.base.BaseObjectManager;
import com.tibco.cep.kernel.model.entity.Element;
import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.kernel.service.ObjectManager;
import com.tibco.cep.runtime.model.TypeManager.TypeDescriptor;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.ContainedConcept;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.model.event.impl.SimpleEventImpl;
import com.tibco.cep.runtime.service.cluster.agent.InferenceAgent;
import com.tibco.cep.runtime.service.cluster.om.BulkConceptCacheReader;
import com.tibco.cep.runtime.service.cluster.om.BulkConceptRetriever;
import com.tibco.cep.runtime.service.cluster.om.DistributedCacheBasedStore;
import com.tibco.cep.runtime.service.cluster.txn.RtcTransactionProperties;
import com.tibco.cep.runtime.service.om.api.invm.LocalCache;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.cep.runtime.session.impl.PreprocessContext;
import com.tibco.cep.runtime.session.impl.RuleSessionImpl;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


@com.tibco.be.model.functions.BEPackage(
		catalog = "Coherence",
        category = "Coherence",
        enabled = @Enabled(property="TIBCO.BE.function.catalog.coherence",value=false),
        synopsis = "Functions for querying the Cache")
public class CoherenceFunctions {

    @com.tibco.be.model.functions.BEFunction(
        name = "C_CacheName",
        synopsis = "Returns the cache name for the specified <code> entityPath</code>",
        signature = "String C_CacheName(String entityPath)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "entityPath", type = "String", desc = "Full path of the entity model")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = ""),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the cache name for the specified <code> entityPath</code>",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = "<br/> String cName = CoherenceFunctions.C_CacheName($1/Concepts/Customer$1);<br/><br/>"
    )
    public static String C_CacheName(String entityPath) {
        try {
            RuleSession session = RuleSessionManager.getCurrentRuleSession();
            ObjectManager om= session.getObjectManager();
            if (om instanceof DistributedCacheBasedStore) {
                DistributedCacheBasedStore cs_om = (DistributedCacheBasedStore) om;
                //String clzName= ModelNameUtil.modelPathToGeneratedClassName(entityPath);
                return cs_om.getEntityCacheName(entityPath);
            } else {
                throw new RuntimeException("Coherence Object Manager is not defined for this Rule Session");
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "C_ClassName",
        synopsis = "Returns the generated class name",
        signature = "String C_ClassName(String entityPath)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "entityPath", type = "String", desc = "Full path of the entity model")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = ""),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the generated class name",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static String C_ClassName(String entityPath) {
        try {
            return ModelNameUtil.modelPathToGeneratedClassName(entityPath);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "C_Index",
        synopsis = "Creates an index on the property",
        signature = "void C_Index(String cacheName, Object property, boolean isOrdered)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "cacheName", type = "String", desc = "Returned by C_CacheName"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "property", type = "Object", desc = "Returned by C_XXXAtomGetter"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "isOrdered", type = "boolean", desc = "true if the contents of the indexed information should be ordered; false otherwise")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Creates an index on the property",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static void C_Index(String cacheName, Object property, boolean isOrdered) {
        try {
            NamedCache cache= CacheFactory.getCache(cacheName,
                RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider().getClassLoader());
            if (property instanceof ValueExtractor) {
                cache.addIndex((ValueExtractor) property, isOrdered, null);
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "C_EnableCacheUpdate",
        synopsis = "Set to true if the current RTC changes be replicated to the cache else RTC changes will lead to cache eviction instead of updates",
        signature = "void C_EnableCacheUpdate(boolean updateCache)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "updateCache", type = "true", desc = "if the current RTC changes be replicated to the cache else RTC changes will lead to cache eviction instead of updates")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Set to true if the current RTC changes be replicated to the cache else RTC changes will lead to cache eviction instead of updates",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static void C_EnableCacheUpdate(boolean updateCache) {
        try {
            RuleSession ruleSession=RuleSessionManager.getCurrentRuleSession();
            if (ruleSession != null) {
                DistributedCacheBasedStore store= (DistributedCacheBasedStore) ruleSession.getObjectManager();
                RtcTransactionProperties prop=store.getRtcTransactionProperties();
                if (store.getCacheAgent().getCluster().getClusterConfig().isHasBackingStore()) {
                    prop.updateCache=updateCache;
                }
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "C_TransactionProperties",
        synopsis = "Set the transaction properties for the current RTC",
        signature = "void C_TransactionProperties(boolean autoCommit, int concurrency, int isolation)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "autoCommit", type = "true", desc = "if the cache updates are applied within a transaction boundary"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "concurrency", type = "CONCUR_PESSIMISTIC=1,", desc = "CONCUR_OPTIMISTIC=2"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "isolation", type = "TRANSACTION_GET_COMMITTED", desc = "= 1, TRANSACTION_REPEATABLE_GET = 2, TRANSACTION_SERIALIZABLE = 3")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Set the transaction properties for the current RTC",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static void C_TransactionProperties(boolean autoCommit, int concurrency, int isolation) {
        try {
            RuleSession ruleSession=RuleSessionManager.getCurrentRuleSession();
            if (ruleSession != null) {
                DistributedCacheBasedStore store= (DistributedCacheBasedStore) ruleSession.getObjectManager();
                RtcTransactionProperties prop=store.getRtcTransactionProperties();
//                if (store.getCacheAgent().getCluster().isBackingStoreEnabled()) {
//                    prop.updateCache=updateCache;
//                }
                prop.autoCacheCommit=autoCommit;
                if (!autoCommit) {
                    if (isolation != -1)
                        prop.cacheTransactionIsolation=isolation;
                    if (concurrency != -1)
                        prop.cacheTransactionConcurrency=concurrency;
                }
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "C_Flush",
        synopsis = "Flushes the write behind queue for all the distributed cache engines",
        signature = "void C_Flush()",
        params = {
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Flushes the write behind queue for all the distributed cache engines",
        cautions = "none",
        enabled=@Enabled(property="TIBCO.BE.function.catalog.coherence.ext",value=false),
        fndomain = {ACTION},
        example = ""
    )
    public static void C_Flush() {
        RuleSession session = RuleSessionManager.getCurrentRuleSession();
        if (session != null) {
            BaseObjectManager om = (BaseObjectManager) session.getObjectManager();
            if (om instanceof DistributedCacheBasedStore) {
                try {
                    ((DistributedCacheBasedStore)om).flushCaches();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "C_CacheLoadEntity",
        synopsis = "Load the entity into RETE",
        signature = "void C_CacheLoadEntity(Entity entity)",
        params = {
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Load the entity into RETE",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static void C_CacheLoadEntity (Entity entity) {
        try {
            RuleSession session = RuleSessionManager.getCurrentRuleSession();
            ObjectManager om = session.getObjectManager();
            if (om instanceof DistributedCacheBasedStore) {
                entity.setLoadedFromCache();
                InferenceAgent agent = (InferenceAgent) ((DistributedCacheBasedStore) om).getCacheAgent();
                LocalCache localCache =agent.getLocalCache();
                localCache.put(entity);

                ((RuleSessionImpl) session).reloadFromCache(entity);
            } else {
                throw new RuntimeException("Coherence Object Manager is not defined for this Rule Session");
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "C_CacheReevaluate",
        synopsis = "Reload the entity into RETE and reevaluate all rules",
        signature = "void C_CacheReevaluate(Entity entity)",
        params = {
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Loads the entity into RETE and reevaluates all rules (allowed only in preprocessor)",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static void C_CacheReevaluate (Entity entity) {
        try {
            if (entity != null) {
                PreprocessContext pc = PreprocessContext.getContext();
                if (pc != null) {
                    pc.reevaluateObject(entity);
                }else {
                    throw new RuntimeException("C_CacheReevaluate Only Allowed in Preprocessor");
                }
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "C_CacheGetEntityById",
        synopsis = "Get an entity from the cache and load the entity into RETE",
        signature = "Concept C_CacheGetEntityById(long id, String uri, boolean load)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "id", type = "long", desc = "Identifier for the targeted entity"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "uri", type = "String", desc = "URI of the class")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Entity", desc = ""),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Get an entity from the cache and load the entity into RETE",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static Entity C_CacheGetEntityById (long id, String uri, boolean load) {
        try {
            RuleSession session = RuleSessionManager.getCurrentRuleSession();
            ObjectManager om = session.getObjectManager();
            if (om instanceof DistributedCacheBasedStore) {
                Class entityClz=session.getRuleServiceProvider().getTypeManager().getTypeDescriptor(uri).getImplClass();
                if (Concept.class.isAssignableFrom(entityClz)) {
                    Entity entity=((DistributedCacheBasedStore) om).getElement(id, entityClz);
                    if (entity != null) {
                        if (load)
                            ((RuleSessionImpl) session).reloadFromCache(entity);
                    }
                    return entity;
                } else {
                    Event entity=((DistributedCacheBasedStore) om).getEvent(id, entityClz, false);
                    if (entity != null) {
                        if (entity instanceof SimpleEvent) {
                            ((SimpleEventImpl) entity).setLoadedFromCache();
                        }
                        if (load)
                            ((RuleSessionImpl) session).reloadFromCache(entity);
                    }
                    return entity;
                }
            } else {
                throw new RuntimeException("Coherence Object Manager is not defined for this Rule Session");
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "C_CacheLoadConceptById",
        synopsis = "Retrieve from the cache the concept whose identifier is specified",
        signature = "Concept C_CacheLoadConceptById(long id, boolean includeContained)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "id", type = "long", desc = "Identifier for the targeted concept"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "includeContained", type = "boolean", desc = "If true load the entire complex concept; if false load only the root concept")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Concept", desc = ""),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Retrieve from the cache the concept whose identifier is specified",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static Concept C_CacheLoadConceptById (long id, boolean includeContained) {
        try {
            RuleSession session = RuleSessionManager.getCurrentRuleSession();
            ObjectManager om = session.getObjectManager();
            if (om instanceof DistributedCacheBasedStore) {
                DistributedCacheBasedStore cs_om = (DistributedCacheBasedStore) om;
                Element element= cs_om.getElement(id);
                if (element != null) {
                    boolean isDeleted=((ConceptImpl) element).isMarkedDeleted();
                    if (!isDeleted) {
                        if (includeContained) {
                            List children = element.getChildren();
                            if (children != null) {
                                children.add(element);
                                ((RuleSessionImpl) session).reloadFromCache(children);
                            } else {
                                List toLoad = new ArrayList();
                                toLoad.add(element);
                                ((RuleSessionImpl) session).reloadFromCache(toLoad);
                            }
                        } else {
                            List toLoad = new ArrayList();
                            toLoad.add(element);
                            ((RuleSessionImpl) session).reloadFromCache(toLoad);
                        }
                    } else {
                        return null;
                    }
                }
                return (Concept) element;
            } else {
                throw new RuntimeException("Coherence Object Manager is not defined for this Rule Session");
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "C_CacheLoadEventById",
        synopsis = "Retrieve from the cache the event whose identifier is specified",
        signature = "Event C_CacheLoadEventById(long id)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "id", type = "long", desc = "Identifier for the targeted event")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Event", desc = ""),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Retrieve from the cache the event whose identifier is specified",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static Event C_CacheLoadEventById (long id) {
        try {
            RuleSession session = RuleSessionManager.getCurrentRuleSession();
            ObjectManager om= session.getObjectManager();
            if (om instanceof DistributedCacheBasedStore) {
                DistributedCacheBasedStore cs_om = (DistributedCacheBasedStore) om;
                Event event= cs_om.getEvent(id);
                if (event != null) {
                    ((RuleSessionImpl) session).reloadFromCache(event);
                }
                return event;
            } else {
                throw new RuntimeException("Coherence Object Manager is not defined for this Rule Session");
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "C_CacheLoadEventByExtId",
        synopsis = "Retrieve from the cache the event whose external identifier is specified",
        signature = "Event C_CacheLoadEventByExtId(String extId)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "extId", type = "String", desc = "External identifier for the targeted event")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Event", desc = ""),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Retrieve from the cache the event whose external identifier is specified",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static Event C_CacheLoadEventByExtId (String extId) {
        try {
            RuleSession session = RuleSessionManager.getCurrentRuleSession();
            ObjectManager om= session.getObjectManager();
            if (om instanceof DistributedCacheBasedStore) {
                DistributedCacheBasedStore cs_om = (DistributedCacheBasedStore) om;
                Event event= cs_om.getEvent(extId);
                if (event != null) {
                    ((RuleSessionImpl) session).reloadFromCache(event);
                }
                return event;
            } else {
                throw new RuntimeException("Coherence Object Manager is not defined for this Rule Session");
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static final ConcurrentHashMap<String, Class> indexedClassNameAndClasses =
            new ConcurrentHashMap<String, Class>();

    /**
     * @param extId
     * @param includeContained
     * @param className <code>null</code> if the indexed get should not be used.
     * @return
     */
    static Concept loadConceptUsingExtId(String extId, boolean includeContained, String className){
        try {
            if (null == extId) return null;

            RuleSession session = RuleSessionManager.getCurrentRuleSession();
            ObjectManager om= session.getObjectManager();
            if (om instanceof DistributedCacheBasedStore) {
                DistributedCacheBasedStore cs_om = (DistributedCacheBasedStore) om;

                Class entityClass = null;
                if (className != null) {
                    entityClass = indexedClassNameAndClasses.get(className);

                    if (entityClass == null) {
                        ClassLoader classLoader =
                                (ClassLoader) session.getRuleServiceProvider().getTypeManager();

                        entityClass = Class.forName(className, true, classLoader);

                        indexedClassNameAndClasses.put(className, entityClass);
                    }
                }
                //BaseHandle h=cs_om.getElementHandle(extId);
                Element element = null;
                if (entityClass == null) {
                    element = cs_om.getElement(extId);
                } else {
                    element = cs_om.getElement(extId, entityClass);
                }
                if (element != null) {
                    boolean isDeleted=((ConceptImpl) element).isMarkedDeleted();
                    if (!isDeleted) {
                        if(includeContained) {

                            InferenceAgent inferenceAgent = (InferenceAgent) cs_om.getCacheAgent();
                            BulkConceptCacheReader downloader =
                                    inferenceAgent.getBulkConceptCacheReader();
                            List children = null;
                            if (downloader == null) {
                                children = element.getChildren();
                            }
                            else {
                                children = downloader.deference((ConceptImpl) element);
                            }

                            if (children != null) {
                                children.add(element);
                                ((RuleSessionImpl) session).reloadFromCache(children);
                            } else
                                ((RuleSessionImpl) session).reloadFromCache(element);
                        } else
                            ((RuleSessionImpl) session).reloadFromCache(element);
                    } else {
                        return null;
                    }
                }
                return (Concept) element;
            } else {
                throw new RuntimeException("Coherence Object Manager is not defined for this Rule Session");
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "C_CacheLoadConceptIndexedByExtId",
        synopsis = "Retrieve from the cache the concept whose external identifier is specified.\nThis method relies on an index that should be added on the concept's extId. See <code>C_Index(String, Object, boolean)</code> and the extractor to be used for the index creation -\nC_EntityExtIdGetter().",
        signature = "Concept C_CacheLoadConceptIndexedByExtId(String extId, boolean includeContained,\nString className)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "extId", type = "String", desc = "External identifier for targeted concept"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "includeContained", type = "boolean", desc = "the root concept"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "className", type = "The", desc = "#C_ClassName(String)}.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Concept", desc = ""),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Retrieve from the cache the concept whose external identifier is specified.\nThis method relies on an index that should be added on the concept's extId. See <code>C_Index(String, Object, boolean)</code> and the extractor to be used for the index creation -\nC_EntityExtIdGetter().",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static Concept C_CacheLoadConceptIndexedByExtId(String extId, boolean includeContained,
                                                           String className) {
        return loadConceptUsingExtId(extId, includeContained, className);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "C_CacheLoadConceptByExtId",
        synopsis = "Retrieve from the cache the concept whose external identifier is specified",
        signature = "Concept C_CacheLoadConceptByExtId(String extId, boolean includeContained)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "extId", type = "String", desc = "External identifier for targeted concept"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "includeContained", type = "boolean", desc = "If true load the entire complex concept; if false load only the root concept")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Concept", desc = ""),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Retrieve from the cache the concept whose external identifier is specified",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static Concept C_CacheLoadConceptByExtId (String extId, boolean includeContained) {
        return loadConceptUsingExtId(extId, includeContained, null);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "C_CacheLoadParent",
        synopsis = "Loads from cache the parent concepts of the given concept.",
        signature = "void C_CacheLoadParent(Concept cept, boolean recursive)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "cept", type = "Concept", desc = "")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Loads from cache the parent concepts of the given concept.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static void C_CacheLoadParent (Concept cept, boolean recursive) {
        try {
            loadParent(cept, recursive);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void loadParent (Concept cept, boolean recursive) {
        try {
            if (cept instanceof ContainedConcept) {
                RuleSession session = RuleSessionManager.getCurrentRuleSession();
                ObjectManager om= session.getObjectManager();
                if (om instanceof DistributedCacheBasedStore) {
                    ContainedConcept childConcept= (ContainedConcept) cept;
                    Concept parent=childConcept.getParent();
                    if (parent != null) {
                        ((RuleSessionImpl) session).reloadFromCache(parent);
                        if (recursive)
                            loadParent(parent, recursive);
                    }
                }
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "C_CacheLoadConceptsByExtId",
        synopsis = "Bulk retrieval of Concepts from the Cache.",
        signature = "Object[] C_CacheLoadConceptsByExtId(String[] extIds)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "extIds", type = "an", desc = "array of Strings which r")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object[]", desc = ""),
        version = "3.0.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Retrieve Concepts from the Cache whose extIds are specified.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static Object[] C_CacheLoadConceptsByExtId(String[] extIds) {
        RuleSession session = RuleSessionManager.getCurrentRuleSession();
        ObjectManager om = session.getObjectManager();

        if (om instanceof DistributedCacheBasedStore == false) {
            throw new RuntimeException(
                    "Coherence Object Manager is not defined for this Rule Session");
        }

        //----------

        Object[] retVal = new Object[2];

        if (extIds != null && extIds.length > 0) {
            DistributedCacheBasedStore csOM = (DistributedCacheBasedStore) om;
            InferenceAgent inferenceAgent = (InferenceAgent) csOM.getCacheAgent();

            BulkConceptRetriever retriever = inferenceAgent.getBulkConceptRetriever();

            ArrayList<String> extIdList = new ArrayList<String>(extIds.length);
            for (String extId : extIds) {
                extIdList.add(extId);
            }

            try {
                BulkConceptRetriever.Result result = retriever.retrieve(extIdList, null);

                Map<String, Concept> found = result.getConceptsFound();
                int foundSize = found.size();
                if (foundSize > 0) {
                    Collection<Concept> concepts = found.values();

                    List<Concept> listOfConcepts = new ArrayList<Concept>(foundSize);
                    listOfConcepts.addAll(concepts);

                    retVal[0] = listOfConcepts.toArray(new Concept[foundSize]);

                    //Load these into the WM
                    ((RuleSessionImpl) session).reloadFromCache(listOfConcepts);
                }

                Set<String> notFound = result.getExtIdsNotFound();
                if (notFound.isEmpty() == false) {
                    retVal[1] = notFound.toArray(new String[notFound.size()]);
                }
            }
            catch (RuntimeException e) {
                throw e;
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return retVal;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "C_Lock",
        synopsis = "Locks the object represented by the key within a rule session",
        signature = "boolean C_Lock(String key, long timeout, boolean localOnly)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "key", type = "String", desc = "A key that uniquely identifies a lock."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "timeout", type = "long", desc = "Specify in milliseconds the time to wait for the lock."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "localOnly", type = "boolean", desc = "true if the lock is local to the local session, false if the lock is cluster wide")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = ""),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Locks the object within a rule session. This call will wait for <tt>timeout</tt>. The <tt> timeout </tt> value of zero indicates wait indefinitely",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static boolean C_Lock (String key, long timeout, boolean localOnly) {
        try {
            RuleSession session = RuleSessionManager.getCurrentRuleSession();
            if (session != null) {
                boolean isConcurrent=((BaseObjectManager) session.getObjectManager()).isConcurrent();
                if (!isConcurrent) {
                    PreprocessContext preContext = PreprocessContext.getContext();
                    if (preContext == null) {
                        //throw new RuntimeException("Operation C_Lock only allowed in pre-processor");
                    }
                }
                if (((RuleSessionImpl) session).lock(key, timeout, localOnly)) {
                    return true;
                } else {
                    return false;
                }
            } else {
                throw new RuntimeException("Operation C_Lock not allowed outside of a rule session");
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "C_UnLock",
        synopsis = "Unlocks the object represented by the key within a rule session",
        signature = "void C_UnLock(String key, boolean localOnly)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "key", type = "String", desc = "A key that uniquely identifies a lock"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "localOnly", type = "true", desc = "if the lock is local to the local session, false if the lock is cluster wide")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "UnLocks the object represented by the key within a rule session.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )

    public static void C_UnLock (String key, boolean localOnly) {
        try {
            RuleSession session = RuleSessionManager.getCurrentRuleSession();
            if (session != null) {
                boolean isConcurrent=((BaseObjectManager) session.getObjectManager()).isConcurrent();
                if (!isConcurrent) {
                    PreprocessContext preContext = PreprocessContext.getContext();
                    if (preContext == null) {
                        throw new RuntimeException("Operation C_UnLock only allowed in pre-processor");
                    }
                }
                ((RuleSessionImpl) session).unlock(key, localOnly);
            } else {
                throw new RuntimeException("Operation C_UnLock not allowed outside of a rule session");
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "C_CacheLoadConceptByExtIdByUri",
        synopsis = "Retrieve from the cache the concept whose external identifier is specified",
        signature = "Concept C_CacheLoadConceptByExtIdByUri(String extId, boolean includeContained, String uri)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "extId", type = "String", desc = "External identifier for targeted concept"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "includeContained", type = "boolean", desc = "If true load the entire complex concept; if false load only the root concept"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "uri", type = "String", desc = "Uri of the concept to load")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Concept", desc = ""),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Retrieve from the cache the concept whose external identifier is specified",
        cautions = "none",
        enabled=@Enabled(property="TIBCO.BE.function.catalog.getbyuri",value=false),
        fndomain = {ACTION},
        example = ""
    )
    public static Concept C_CacheLoadConceptByExtIdByUri (String extId, boolean includeContained, String uri) {
        try {
            RuleSession session = RuleSessionManager.getCurrentRuleSession();
            TypeDescriptor td = session.getRuleServiceProvider().getTypeManager().getTypeDescriptor(uri);
            if (td == null) {
                throw new RuntimeException("cant find type: " + uri);
            }
            String className = td.getImplClass().getName();
            return loadConceptUsingExtId(extId, includeContained, className);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "C_CacheLoadEventByExtIdByUri",
        synopsis = "Retrieve from the cache the event whose external identifier is specified",
        signature = "Event C_CacheLoadEventByExtIdByUri(String, String)(String extId, String uri)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "extId", type = "String", desc = "External identifier for the targeted event"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "uri", type = "String", desc = "URI of the event")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Event", desc = ""),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Retrieve from the cache the event whose external identifier is specified",
        cautions = "none",
        enabled=@Enabled(property="TIBCO.BE.function.catalog.getbyuri",value=false),
        fndomain = {ACTION},
        example = ""
    )
    public static Event C_CacheLoadEventByExtIdByUri (String extId, String uri) {
        try {
            RuleSession session = RuleSessionManager.getCurrentRuleSession();
            ObjectManager om= session.getObjectManager();
            if (om instanceof DistributedCacheBasedStore) {
                DistributedCacheBasedStore cs_om = (DistributedCacheBasedStore) om;
                Event event= cs_om.getEventByUri(extId, uri);
                if (event != null) {
                    ((RuleSessionImpl) session).reloadFromCache(event);
                }
                return event;
            } else {
                throw new RuntimeException("Coherence Object Manager is not defined for this Rule Session");
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
