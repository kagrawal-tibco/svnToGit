package com.tibco.be.functions.cluster;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.tibco.be.model.util.ModelNameUtil;
import com.tibco.cep.kernel.core.base.BaseObjectManager;
import com.tibco.cep.kernel.model.entity.Element;
import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.kernel.service.ObjectManager;
import com.tibco.cep.runtime.model.TypeManager.TypeDescriptor;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.ContainedConcept;
import com.tibco.cep.runtime.model.element.VersionedObject;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.cep.runtime.model.element.impl.ManagedObjectLockType;
import com.tibco.cep.runtime.model.element.impl.ManagedObjectManager;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.model.event.impl.SimpleEventImpl;
import com.tibco.cep.runtime.service.cluster.agent.InferenceAgent;
import com.tibco.cep.runtime.service.cluster.om.BulkConceptCacheReader;
import com.tibco.cep.runtime.service.cluster.om.BulkConceptRetriever;
import com.tibco.cep.runtime.service.cluster.om.DistributedCacheBasedStore;
import com.tibco.cep.runtime.service.cluster.txn.RtcTransactionProperties;
import com.tibco.cep.runtime.service.om.api.EntityDao;
import com.tibco.cep.runtime.service.om.api.EntityDaoConfig;
import com.tibco.cep.runtime.service.om.api.invm.LocalCache;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.cep.runtime.session.impl.PreprocessContext;
import com.tibco.cep.runtime.session.impl.RuleSessionImpl;

/*
* Author: Ashwin Jayaprakash / Date: Nov 10, 2010 / Time: 10:47:57 AM
*/
public abstract class DataGridFunctionsProvider {

    protected DataGridFunctionsProvider() {
    }

    public String getCacheName(String entityPath) {
        try {
            RuleSession session = RuleSessionManager.getCurrentRuleSession();
            ObjectManager om = session.getObjectManager();
            if (om instanceof DistributedCacheBasedStore) {
                DistributedCacheBasedStore cs_om = (DistributedCacheBasedStore) om;
                //String clzName= ModelNameUtil.modelPathToGeneratedClassName(entityPath);
                return cs_om.getEntityCacheName(entityPath);
            }
            else {
                throw new RuntimeException("Distributed Cache Object Manager is not defined for this Rule Session");
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getClassName(String entityPath) {
        try {
            return ModelNameUtil.modelPathToGeneratedClassName(entityPath);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public abstract void setIndex(String cacheName, Object property, boolean isOrdered);

    public void setEnableCacheUpdate(boolean updateCache) {
        try {
            RuleSession ruleSession = RuleSessionManager.getCurrentRuleSession();
            if (ruleSession != null) {
                DistributedCacheBasedStore store = (DistributedCacheBasedStore) ruleSession.getObjectManager();
                RtcTransactionProperties prop = store.getRtcTransactionProperties();
                if (store.getCacheAgent().getCluster().getClusterConfig().isHasBackingStore()) {
                    prop.updateCache = updateCache;
                }
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setTransactionProperties(boolean autoCommit, int concurrency, int isolation) {
        try {
            RuleSession ruleSession = RuleSessionManager.getCurrentRuleSession();
            if (ruleSession != null) {
                DistributedCacheBasedStore store = (DistributedCacheBasedStore) ruleSession.getObjectManager();
                RtcTransactionProperties prop = store.getRtcTransactionProperties();
                //if (store.getCacheAgent().getCluster().isBackingStoreEnabled()) {
                //    prop.updateCache=updateCache;
                //}
                prop.autoCacheCommit = autoCommit;
                if (!autoCommit) {
                    if (isolation != -1) {
                        prop.cacheTransactionIsolation = isolation;
                    }
                    if (concurrency != -1) {
                        prop.cacheTransactionConcurrency = concurrency;
                    }
                }
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void flush() {
        RuleSession session = RuleSessionManager.getCurrentRuleSession();
        if (session != null) {
            BaseObjectManager om = (BaseObjectManager) session.getObjectManager();
            if (om instanceof DistributedCacheBasedStore) {
                try {
                    ((DistributedCacheBasedStore) om).flushCaches();
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public void cacheLoadEntity(Entity entity) {
        try {
            RuleSession session = RuleSessionManager.getCurrentRuleSession();
            ObjectManager om = session.getObjectManager();
            if (om instanceof DistributedCacheBasedStore) {
                InferenceAgent agent = (InferenceAgent) ((DistributedCacheBasedStore) om).getCacheAgent();
                EntityDaoConfig econfig = agent.getEntityConfig(entity.getClass());
	            if (!(entity instanceof VersionedObject && ((VersionedObject)entity).getVersion() == 0)) {
	                entity.setLoadedFromCache();
				}
                LocalCache localCache =agent.getLocalCache();
                localCache.put(entity);
                ((RuleSessionImpl) session).reloadFromCache(entity);	                
            }
            else {
                throw new RuntimeException("Distributed Cache Object Manager is not defined for this Rule Session");
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void cacheReevaluate(Entity entity) {
        try {
            if (entity != null) {
                PreprocessContext pc = PreprocessContext.getContext();
                if (pc != null) {
                    pc.reevaluateObject(entity);
                }
                else {
                    throw new RuntimeException("CacheReevaluate Only Allowed in Preprocessor");
                }
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Entity cacheGetEntityById(long id, String uri, boolean load) {
        try {
            RuleSession session = RuleSessionManager.getCurrentRuleSession();
            ObjectManager om = session.getObjectManager();
            if (om instanceof DistributedCacheBasedStore) {
                Class entityClz =
                        session.getRuleServiceProvider().getTypeManager().getTypeDescriptor(uri).getImplClass();
                if (Concept.class.isAssignableFrom(entityClz)) {
                    Entity entity = ((DistributedCacheBasedStore) om).getElement(id, entityClz);
                    if (entity != null) {
                        if (load) {
                            ((RuleSessionImpl) session).reloadFromCache(entity);
                        }
                    }
                    return entity;
                }
                else {
                    Event entity = ((DistributedCacheBasedStore) om).getEvent(id, entityClz, false);
                    if (entity != null) {
                        if (entity instanceof SimpleEvent) {
                            ((SimpleEventImpl) entity).setLoadedFromCache();
                        }
                        if (load) {
                            ((RuleSessionImpl) session).reloadFromCache(entity);
                        }
                    }
                    return entity;
                }
            }
            else {
                throw new RuntimeException("Distributed Cache Object Manager is not defined for this Rule Session");
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Concept cacheLoadConceptById(long id, boolean includeContained) {
        try {
            RuleSession session = RuleSessionManager.getCurrentRuleSession();
            ObjectManager om = session.getObjectManager();
            if (om instanceof DistributedCacheBasedStore) {
                DistributedCacheBasedStore cs_om = (DistributedCacheBasedStore) om;
                Element element = cs_om.getElement(id);
                if (element != null) {
                    boolean isDeleted = ((ConceptImpl) element).isMarkedDeleted();
                    if (!isDeleted) {
                        if (includeContained) {
                            List children = element.getChildren();
                            if (children != null) {
                                children.add(element);
                                ((RuleSessionImpl) session).reloadFromCache(children);
                            }
                            else {
                                List toLoad = new ArrayList();
                                toLoad.add(element);
                                ((RuleSessionImpl) session).reloadFromCache(toLoad);
                            }
                        }
                        else {
                            List toLoad = new ArrayList();
                            toLoad.add(element);
                            ((RuleSessionImpl) session).reloadFromCache(toLoad);
                        }
                    }
                    else {
                        return null;
                    }
                }
                return (Concept) element;
            }
            else {
                throw new RuntimeException("Distributed Cache Object Manager is not defined for this Rule Session");
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Event cacheLoadEventById(long id) {
        try {
            RuleSession session = RuleSessionManager.getCurrentRuleSession();
            ObjectManager om = session.getObjectManager();
            if (om instanceof DistributedCacheBasedStore) {
                DistributedCacheBasedStore cs_om = (DistributedCacheBasedStore) om;
                Event event = cs_om.getEvent(id);
                if (event != null) {
                    ((RuleSessionImpl) session).reloadFromCache(event);
                }
                return event;
            }
            else {
                throw new RuntimeException("Distributed Cache Object Manager is not defined for this Rule Session");
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Event cacheLoadEventByExtId(String extId) {
        try {
            RuleSession session = RuleSessionManager.getCurrentRuleSession();
            ObjectManager om = session.getObjectManager();
            if (om instanceof DistributedCacheBasedStore) {
                DistributedCacheBasedStore cs_om = (DistributedCacheBasedStore) om;
                Event event = cs_om.getEvent(extId);
                if (event != null) {
                    ((RuleSessionImpl) session).reloadFromCache(event);
                }
                return event;
            }
            else {
                throw new RuntimeException("Distributed Cache Object Manager is not defined for this Rule Session");
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static final ConcurrentHashMap<String, Class> indexedClassNameAndClasses = new ConcurrentHashMap<String, Class>();

    protected Concept loadConceptUsingExtId(String extId, boolean includeContained, String className) {
        try {
            if (null == extId) {
                return null;
            }

        	RuleSession session = RuleSessionManager.getCurrentRuleSession();
	        ObjectManager om = session.getObjectManager();
	        if (om instanceof DistributedCacheBasedStore) {
	            DistributedCacheBasedStore cs_om = (DistributedCacheBasedStore) om;

                Class entityClass = fqnToClass(className, session);

	            //BaseHandle h=cs_om.getElementHandle(extId);
	            Element element = null;
	            if (entityClass == null) {
	                element = cs_om.getElement(extId);
	            } else {
	                element = cs_om.getElement(extId, entityClass);
	            }
	            if (element != null) {
	                boolean isDeleted = ((ConceptImpl) element).isMarkedDeleted();
	                if (!isDeleted) {
	                    if (includeContained) {
	
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
	                        }
	                        else {
	                            ((RuleSessionImpl) session).reloadFromCache(element);
	                        }
	                    }
	                    else {
	                        ((RuleSessionImpl) session).reloadFromCache(element);
	                    }
	                }
	                else {
	                    return null;
	                }
	            }
	            return (Concept) element;
	        }
	        else {
                throw new RuntimeException("Distributed Cache Object Manager is not defined for this Rule Session");
	        }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected static Class fqnToClass(String className, RuleSession session) throws ClassNotFoundException {
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

        return entityClass;
    }

    public Concept cacheLoadConceptIndexedByExtId(String extId, boolean includeContained, String className) {
        return loadConceptUsingExtId(extId, includeContained, className);
    }

    public Concept cacheLoadConceptByExtId(String extId, boolean includeContained) {
        return loadConceptUsingExtId(extId, includeContained, null);
    }

    public void cacheLoadParent(Concept cept, boolean recursive) {
        try {
            loadParent(cept, recursive);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void loadParent(Concept cept, boolean recursive) {
        try {
            if (cept instanceof ContainedConcept) {
                RuleSession session = RuleSessionManager.getCurrentRuleSession();
                ObjectManager om = session.getObjectManager();
                if (om instanceof DistributedCacheBasedStore) {
                    ContainedConcept childConcept = (ContainedConcept) cept;
                    Concept parent = childConcept.getParent();
                    if (parent != null) {
                        ((RuleSessionImpl) session).reloadFromCache(parent);
                        if (recursive) {
                            loadParent(parent, recursive);
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // The function doc only claims to fetch from cache so this functions does not check any local maps for memory-only or modified data 
    public Concept[] cacheLoadConceptsByExtIdByUri(String[] extIds, String uri) {
        RuleSession session = RuleSessionManager.getCurrentRuleSession();
        ObjectManager om = session.getObjectManager();
        
        if (om instanceof DistributedCacheBasedStore == false) {
            throw new RuntimeException("Distributed Cache Object Manager is not defined for this Rule Session");
        }

        InferenceAgent agent = (InferenceAgent) ((DistributedCacheBasedStore) om).getCacheAgent();

        if(extIds != null && uri != null) {
            try {
                EntityDao dao = agent.getEntityDao(uri);
                if(dao != null) {
                    EntityDaoConfig daoConfig = agent.getEntityConfig(dao.getEntityClass());
                    //cacheLoadConceptsByExtId only looks in the cache so ignore memory-only objects
                    if(daoConfig.isCacheEnabled()) {
                        if(!ManagedObjectManager.isOn()) {
                            Object[] result = cacheLoadConceptsByExtId(extIds, uri);
                            if(result != null) return (Concept[])result[0];
                            else return null;
                        } else {
                            List<? extends Entity> result = ManagedObjectManager.getManagedObjectSpi().fetchByExtIds(Arrays.asList(extIds), dao.getEntityClass(), ManagedObjectLockType.READLOCK);
                            if(result != null) {
                                int foundSize = result.size();
                                if (foundSize > 0) {
                                    ((RuleSessionImpl) session).reloadFromCache(result);
                                    return result.toArray(new Concept[foundSize]);
                                }
                            }
                        }
                    }
                }
            } catch (RuntimeException e) {
                throw e;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
    
    public Object[] cacheLoadConceptsByExtId(String[] extIds) {
        return cacheLoadConceptsByExtId(extIds, null);
    }
    
    // When uri is specified all the extIds are for concepts of the same type otherwise they can be of many types
    protected Object[] cacheLoadConceptsByExtId(String[] extIds, String uri) {
        RuleSession session = RuleSessionManager.getCurrentRuleSession();
        ObjectManager om = session.getObjectManager();

        if (om instanceof DistributedCacheBasedStore == false) {
            throw new RuntimeException("Distributed Cache Object Manager is not defined for this Rule Session");
        }

        //----------

        Object[] retVal = new Object[2];

        if (extIds != null && extIds.length > 0) {
            DistributedCacheBasedStore csOM = (DistributedCacheBasedStore) om;
            InferenceAgent inferenceAgent = (InferenceAgent) csOM.getCacheAgent();

            BulkConceptRetriever retriever = inferenceAgent.getBulkConceptRetriever();

            List<String> extIdList = Arrays.asList(extIds);

            try {
                BulkConceptRetriever.Result result = retriever.retrieve(extIdList, uri);

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

    public boolean lock(String key, long timeout, boolean localOnly) {
    	return lockImpl(key, timeout, localOnly);
    }

    public static boolean lockImpl(String key, long timeout, boolean localOnly) {
        try {
            RuleSession session = RuleSessionManager.getCurrentRuleSession();
            if (session != null) {
                boolean isConcurrent = ((BaseObjectManager) session.getObjectManager()).isConcurrent();
                if (!isConcurrent) {
                    PreprocessContext preContext = PreprocessContext.getContext();
                    if (preContext == null) {
                        //throw new RuntimeException("Operation Lock only allowed in pre-processor");
                    }
                }
                if (((RuleSessionImpl) session).lock(key, timeout, localOnly)) {
                    return true;
                }
                else {
                    return false;
                }
            }
            else {
                throw new RuntimeException("Operation Lock not allowed outside of a rule session");
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void unLock(String key, boolean localOnly) {
    	unLockImpl(key, localOnly);
    }
    public static void unLockImpl(String key, boolean localOnly) {
        try {
            RuleSession session = RuleSessionManager.getCurrentRuleSession();
            if (session != null) {
                boolean isConcurrent = ((BaseObjectManager) session.getObjectManager()).isConcurrent();
                if (!isConcurrent) {
                    PreprocessContext preContext = PreprocessContext.getContext();
                    if (preContext == null) {
                        throw new RuntimeException("Operation UnLock only allowed in pre-processor");
                    }
                }
                ((RuleSessionImpl) session).unlock(key, localOnly);
            }
            else {
                throw new RuntimeException("Operation UnLock not allowed outside of a rule session");
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Concept cacheLoadConceptByExtIdByUri(String extId, boolean includeContained, String uri) {
        try {
            RuleSession session = RuleSessionManager.getCurrentRuleSession();
            TypeDescriptor td = session.getRuleServiceProvider().getTypeManager().getTypeDescriptor(uri);
            if (td == null) {
                throw new RuntimeException("cant find type: " + uri);
            }
            String className = td.getImplClass().getName();
            return loadConceptUsingExtId(extId, includeContained, className);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Event cacheLoadEventByExtIdByUri(String extId, String uri) {
        try {
            RuleSession session = RuleSessionManager.getCurrentRuleSession();
            ObjectManager om = session.getObjectManager();
            if (om instanceof DistributedCacheBasedStore) {
                DistributedCacheBasedStore cs_om = (DistributedCacheBasedStore) om;
                Event event = cs_om.getEventByUri(extId, uri);
                if (event != null) {
                    ((RuleSessionImpl) session).reloadFromCache(event);
                }
                return event;
            }
            else {
                throw new RuntimeException("Distributed Cache Object Manager is not defined for this Rule Session");
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

	abstract public int evictCache(String cacheName, Object filter, boolean deleteFromPersistence);

}