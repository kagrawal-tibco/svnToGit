/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.be.functions.coherence.query;

import com.tangosol.net.CacheFactory;
import com.tangosol.net.NamedCache;
import com.tangosol.util.Filter;
import com.tangosol.util.InvocableMap;
import com.tangosol.util.InvocableMap.Entry;
import com.tangosol.util.filter.AlwaysFilter;
import com.tangosol.util.processor.AbstractProcessor;
import com.tibco.be.functions.cluster.ClusterFunctions;
import com.tibco.be.functions.coherence.CoherenceFunctions;
import com.tibco.be.functions.coherence.filters.CoherenceFilterFunctions;
import com.tibco.be.functions.coherence.query.backingstore.converters.CoherenceBackingStoreConverterFunctions;

import static com.tibco.be.model.functions.FunctionDomain.*;

import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.kernel.service.ObjectManager;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.cep.runtime.model.event.AdvisoryEvent;
import com.tibco.cep.runtime.model.event.AdvisoryEventDictionary;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.model.event.impl.AdvisoryEventImpl;
import com.tibco.cep.runtime.model.event.impl.SimpleEventImpl;
import com.tibco.cep.runtime.service.cluster.CacheClusterProvider;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.agent.InferenceAgent;
import com.tibco.cep.runtime.service.cluster.backingstore.BackingStore;
import com.tibco.cep.runtime.service.cluster.backingstore.GenericBackingStore;
import com.tibco.cep.runtime.service.cluster.om.BulkEntityDeleter;
import com.tibco.cep.runtime.service.cluster.om.DistributedCacheBasedStore;
import com.tibco.cep.runtime.service.cluster.util.WorkManager;
import com.tibco.cep.runtime.service.om.api.EntityDao;
import com.tibco.cep.runtime.service.om.impl.coherence.cluster.util.RuleFunctionComparator;
import com.tibco.cep.runtime.session.BEManagedThread;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.cep.runtime.session.impl.RuleSessionImpl;

import java.util.*;

public class CoherenceQueryFunctionsDelegate {
    static ThreadLocal currentContext = new ThreadLocal();

    

    public static String C_CurrentContext() {
        try {
            return (String) currentContext.get();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

   

    public static long[] C_QueryIDs(String cacheName, Object filter) {
        RuleSession session = RuleSessionManager.getCurrentRuleSession();
        if (session != null) {
            Thread.currentThread().setContextClassLoader((ClassLoader) session.getRuleServiceProvider().getTypeManager());
            NamedCache cache = CacheFactory.getCache(cacheName, ((ClassLoader) session.getRuleServiceProvider().getTypeManager()));
            Set rs = cache.keySet((Filter) filter);
            int numResults = rs.size();
            long[] ret = new long[numResults];
            Iterator allKeys = rs.iterator();
            int i = 0;
            while (allKeys.hasNext()) {
                ret[i++] = ((Long) allKeys.next()).longValue();
            }
            return ret;
        }
        return null;
    }

    
    public static Concept[] C_QueryConcepts(String cacheName, Object filter, boolean queryOnly) {
        RuleSession session = RuleSessionManager.getCurrentRuleSession();

        if (session != null) {   //use key - to avoid deserialize, the object may be already exist in WM
            ClassLoader classLoader = (ClassLoader) session.getRuleServiceProvider().getTypeManager();
            NamedCache cache2 = CacheFactory.getCache(cacheName, classLoader);
            Set rs2 = cache2.entrySet((Filter) filter);
            int numResults2 = rs2.size();
            Concept[] ret2 = new Concept[numResults2];
            Iterator allConcepts2 = rs2.iterator();
            int j = 0;
            while (allConcepts2.hasNext()) {
                Concept ad2 = (Concept) ((Map.Entry) allConcepts2.next()).getValue();
                ad2.setLoadedFromCache();
                ret2[j++] = ad2;
            }
            return ret2;
        } else {
            throw new RuntimeException("Rule Session is NULL");
        }
    }

    
    public static void C_QueryAction(String cacheName, Object filter, int batchSize, String closure) {
        RuleSession session = RuleSessionManager.getCurrentRuleSession();
        if (session != null) {
            ObjectManager om = session.getObjectManager();
            if (om instanceof DistributedCacheBasedStore) {
                Thread.currentThread().setContextClassLoader((ClassLoader) session.getRuleServiceProvider().getTypeManager());
                NamedCache cache = CacheFactory.getCache(cacheName, (ClassLoader) session.getRuleServiceProvider().getTypeManager());
                if (cache != null) {
                    InferenceAgent agent = (InferenceAgent) ((DistributedCacheBasedStore) om).getCacheAgent();
                    agent.scheduleWork(new CoherenceQueryAction(cache, session, (Filter) filter, batchSize, closure));
                } else {
                    throw new RuntimeException("Cache Name " + cacheName + " Not Found");
                }
            } else {
                throw new RuntimeException("Function C_QueryAction Only Supported with Coherence OM");
            }
        } else {
            throw new RuntimeException("Session is NULL");
        }
    }

   
    public static void C_StoreQueryAction(String workManagerID, String query, Object[] args, String entityURI, int batchSize, String closure) {
        try {
            RuleSession session = RuleSessionManager.getCurrentRuleSession();
            if (session != null) {
                ObjectManager om = session.getObjectManager();
                if (om instanceof DistributedCacheBasedStore) {
                    EntityDao provider = ((DistributedCacheBasedStore) om).getCacheAgent().getEntityDao(entityURI);
                    if (provider == null) {
                        throw new RuntimeException("Provider for entity URI " + entityURI + "is NULL");
                    }
                    CacheStoreQueryAction work = new CacheStoreQueryAction(session, query, args, batchSize, provider, closure);
                    try {
                        WorkManager workManager = ClusterFunctions.getWorkManager(workManagerID);
                        if (workManager != null) {
                            workManager.submitJob(work);
                        } else {
                            throw new RuntimeException("Function C_StoreQueryAction: WorkManager " + workManagerID + " not registered");
                        }
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                    //((DistributedCacheBasedStore) om).scheduleWork(work);
                } else {
                    throw new RuntimeException("Function C_StoreQueryAction Only Supported with Coherence OM");
                }
            } else {
                throw new RuntimeException("Session is NULL");
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }


    
    public static Object[] C_CacheInvoke(String cacheName, Object filter, String ruleFunction) {
        RuleSession session = RuleSessionManager.getCurrentRuleSession();
        if(session == null) {
            throw new RuntimeException("Rule Session is NULL");
        }

        ClassLoader classLoader = (ClassLoader) session.getRuleServiceProvider().getTypeManager();

        ObjectManager store = session.getObjectManager();
        if (store instanceof DistributedCacheBasedStore) {
            NamedCache cache = CacheFactory.getCache(cacheName, classLoader);
            RuleFunctionInvocationAgent agent = new RuleFunctionInvocationAgent(ruleFunction);
            Map map =  cache.invokeAll((Filter) filter, agent);

            Object[] values = new Object[map.size()];
            int i = 0;
            for (Object o : map.values()) {
                values[i++] = o;
            }

            return values;
        }

        throw new RuntimeException("Coherence OM Not Registered, Not Possible");
    }

   

    public static Event[] C_QueryEvents(String cacheName, Object filter, boolean queryOnly) {
        RuleSession session = RuleSessionManager.getCurrentRuleSession();

        if (session != null) {   //use key - to avoid deserialize, the object may be already exist in WM
            ArrayList<Event> ret3 = new ArrayList<Event>();
            ClassLoader classLoader = (ClassLoader) session.getRuleServiceProvider().getTypeManager();
            NamedCache cache2 = CacheFactory.getCache(cacheName, classLoader);
            Set rs2 = cache2.entrySet((Filter) filter);
            //int numResults2 = rs2.size();
            //System.out.println("C_QueryEvents(size): " + numResults2);
            //Event [] ret2 = new Event[numResults2];
            Iterator allEvents2 = rs2.iterator();
            int i2 = 0;
            while (allEvents2.hasNext()) {
                Event ad2 = (Event) ((Map.Entry) allEvents2.next()).getValue();
                if (ad2 instanceof SimpleEvent) {
                    ((SimpleEventImpl) ad2).setLoadedFromCache();
                }
                ret3.add(ad2);
                //ret2[i2]= ad2;
                //i2++;
            }
            return ret3.toArray(new Event[ret3.size()]);
        } else {
            throw new RuntimeException("Rule Session is NULL");
        }
    }

    

    public static Event[] C_QueryEvents_Order(String cacheName, Object filter, String orderByRuleFunction) {
        if ((orderByRuleFunction == null) || (orderByRuleFunction.length() == 0)) {
            return C_QueryEvents(cacheName, filter, true);
        } else {
            RuleSession session = RuleSessionManager.getCurrentRuleSession();
            if (session != null) {   //use key - to aviod deserialize, the object may be already exist in WM
                RuleFunctionComparator rc = new RuleFunctionComparator(orderByRuleFunction);
                ArrayList<Event> ret3 = new ArrayList<Event>();
                ClassLoader classLoader = (ClassLoader) session.getRuleServiceProvider().getTypeManager();
                NamedCache cache2 = CacheFactory.getCache(cacheName, classLoader);
                Set rs2 = cache2.entrySet((Filter) filter, rc);
                Iterator allEvents2 = rs2.iterator();
                int i2 = 0;
                while (allEvents2.hasNext()) {
                    Event ad2 = (Event) ((Map.Entry) allEvents2.next()).getValue();
                    if (ad2 instanceof SimpleEvent) {
                        ((SimpleEventImpl) ad2).setLoadedFromCache();
                    }
                    ret3.add(ad2);
                    //ret2[i2]= ad2;
                    //i2++;
                }
                return ret3.toArray(new Event[ret3.size()]);
            } else {
                throw new RuntimeException("Rule Session is NULL");
            }
        }
    }

    
    public static Concept[] C_QueryAndLoadConcepts(String cacheName, Object filter, boolean loadContained) {
        try {
            RuleSession session = RuleSessionManager.getCurrentRuleSession();
            if (session == null) {
                throw new RuntimeException("No rule session found!");
            }
            Concept[] results = C_QueryConcepts(cacheName, filter, true);
            if (results != null) {
                for (int i = 0; i < results.length; i++) {
                    String extId = results[i].getExtId();
                    long id = results[i].getId();

                    if (extId != null) {
                        results[i] = CoherenceFunctions.C_CacheLoadConceptByExtId(extId, loadContained);
                    } else {
                        results[i] = CoherenceFunctions.C_CacheLoadConceptById(id, loadContained);
                    }
                }
            }
            return results;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    
    public static Concept[] C_CacheOnlyMode_QueryConcepts(String cacheName, Object filter, boolean queryOnly) {
        return C_QueryConcepts(cacheName, filter, queryOnly);
    }

    public static class RuleFunctionInvocationAgent extends AbstractProcessor {
        String ruleFunctionURI;
        transient Cluster cluster;
        transient RuleSession session;

        public RuleFunctionInvocationAgent() {
        }

        public RuleFunctionInvocationAgent(String ruleFunctionURI) {
            this.ruleFunctionURI = ruleFunctionURI;
        }

        public Object process(InvocableMap.Entry entry) {
            try {
                if(cluster == null){
                    cluster = CacheClusterProvider.getInstance().getCacheCluster();
                    session = CoherenceFilterFunctions.fetchRuleSession(cluster);
                }

                if (cluster != null) {
                    Object[] args = new Object[]{entry.getValue()};

                    return session.invokeFunction(ruleFunctionURI, args, false);
                } else {
                    throw new RuntimeException("Cluster Not Registered, Not Possible");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new RuntimeException(ex);
            }
        }
    }

    /**
     *
     */
    static class CoherenceQueryAction implements Runnable {
        NamedCache cache;
        RuleSession ruleSession;
        Filter filter;
        int numRows;
        String closure;

        public CoherenceQueryAction(NamedCache cache,
                                    RuleSession _ruleSession,
                                    Filter query,
                                    int numRows,
                                    String closure) {
            this.cache = cache;
            this.ruleSession = _ruleSession;
            this.filter = query;
            this.closure = closure;
            this.numRows = numRows;
        }

        public void run() {
            Set rs = cache.keySet((Filter) filter);
            Iterator allKeys = rs.iterator();

            try {
                int i = 0;
                //ArrayList keys= new ArrayList();
                while (allKeys.hasNext()) {
                    //keys.add(allKeys.next());
                    Long key = (Long) allKeys.next();
                    currentContext.set(closure);
                    ((RuleSessionImpl) ruleSession).reevaluateElements(new long[]{key.longValue()});
                    currentContext.set(null);

//                    if (++i == numRows) {
//                        Map allObjects= cache.getAll(keys);
//                        Iterator allValues= allObjects.values().iterator();
//                        while (allValues.hasNext()) {
//                            Object val= allValues.next();
//                            if (val instanceof ConceptAdapter) {
//                                Concept cept= ((ConceptAdapter) val).getObject();
//                                if (ruleSession.getRuleServiceProvider().getLogger().isDebug()) {
//                                    ruleSession.getRuleServiceProvider().getLogger().logDebug("In WorkerThread, asserting " + cept);
//                                }
//                                ((RuleSessionImpl)ruleSession).reevaluateElements(cept);
//                            } else {
//                                Event evt = ((EventAdapter) val).getEvent();
//                                if (ruleSession.getRuleServiceProvider().getLogger().isDebug()) {
//                                    ruleSession.getRuleServiceProvider().getLogger().logDebug("In WorkerThread, asserting " + evt);
//                                }
//                                ((RuleSessionImpl)ruleSession).retrieveObject(evt);
//                            }
//                        }
//                        i=0;
//                        keys.clear();
//                    }
                }
//                if (keys.size() > 0) {
//                    Map allObjects= cache.getAll(keys);
//                    Iterator allValues= allObjects.values().iterator();
//                    while (allValues.hasNext()) {
//                        Object val= allValues.next();
//                        if (val instanceof ConceptAdapter) {
//                            Concept cept= ((ConceptAdapter) val).getObject();
//                            if (ruleSession.getRuleServiceProvider().getLogger().isDebug()) {
//                                ruleSession.getRuleServiceProvider().getLogger().logDebug("In WorkerThread, asserting " + cept);
//                            }
//                            ((RuleSessionImpl)ruleSession).retrieveObject(cept);
//                        } else {
//                            Event evt = ((EventAdapter) val).getEvent();
//                            if (ruleSession.getRuleServiceProvider().getLogger().isDebug()) {
//                                ruleSession.getRuleServiceProvider().getLogger().logDebug("In WorkerThread, asserting " + evt);
//                            }
//                            ((RuleSessionImpl)ruleSession).retrieveObject(evt);
//                        }
//                    }
//                }
                ruleSession.assertObject(
                        new AdvisoryEventImpl(ruleSession.getRuleServiceProvider().getIdGenerator().nextEntityId(AdvisoryEventImpl.class),
                                null,
                                AdvisoryEvent.CATEGORY_BATCH,
                                AdvisoryEventDictionary.BATCH_StatusDone,
                                closure), true);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    static class CacheStoreQueryAction implements Runnable {
        RuleSession ruleSession;
        int batchSize;
        String closure;
        Object[] args;
        String query;
        boolean isElement;
        EntityDao provider;

        public CacheStoreQueryAction(RuleSession ruleSession,
                                     String query,
                                     Object[] args,
                                     int batchSize,
                                     EntityDao provider,
                                     String closure) {
            this.ruleSession = ruleSession;
            this.closure = closure;
            this.query = query;
            this.args = args;
            this.batchSize = batchSize;
            this.provider = provider;
            isElement = ConceptImpl.class.isAssignableFrom(provider.getEntityClass());
        }

        /**
         * @param error
         */
        private void error(String error) {
            // Close the Query
            try {
                ruleSession.assertObject(
                        new AdvisoryEventImpl(ruleSession.getRuleServiceProvider().getIdGenerator().nextEntityId(AdvisoryEventImpl.class),
                                closure,
                                AdvisoryEvent.CATEGORY_BATCH,
                                AdvisoryEventDictionary.BATCH_StatusError,
                                error), true);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        /**
         * @param numRows
         */
        private void done(int numRows) {
            // Close the Query
            try {
                ruleSession.assertObject(
                        new AdvisoryEventImpl(ruleSession.getRuleServiceProvider().getIdGenerator().nextEntityId(AdvisoryEventImpl.class),
                                closure,
                                AdvisoryEvent.CATEGORY_BATCH,
                                AdvisoryEventDictionary.BATCH_StatusDone,
                                String.valueOf(numRows)), true);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        public void run() {
            try {

                DistributedCacheBasedStore store = (DistributedCacheBasedStore) ruleSession.getObjectManager();
                InferenceAgent agent = (InferenceAgent) store.getCacheAgent();

                Cluster cluster = CacheClusterProvider.getInstance().getCacheCluster();

                BackingStore.ResultSetCache rs = null;
                GenericBackingStore genericBackingStore = cluster.getBackingStore();
                if( (genericBackingStore!=null) && genericBackingStore instanceof BackingStore ) {
                        rs = ((BackingStore)genericBackingStore).query(query, args);
                }
                if (rs != null) {
                    HashSet IDS = new HashSet(batchSize);
                    for (int i = 0; i < rs.size(); i++) {
                        Object[] row = (Object[]) rs.getRow(i);
                        long id = CoherenceBackingStoreConverterFunctions.getColumnAsLong(row[0]);
                        Object obj = agent.getEntityById(id);
                        if (obj != null)
                            IDS.add(id);
                        if (IDS.size() >= batchSize) {

                            //System.out.println("$$$$$ Reevaluating IDS =" + IDS);
                            InferenceAgent inferenceAgent = (InferenceAgent) store.getCacheAgent();
                            Collection reeval = inferenceAgent.loadObjects(IDS, provider);
                            if (reeval.size() > 0) {
                                currentContext.set(closure);
                                if (isElement) {
                                    ((RuleSessionImpl) ruleSession).reevaluateElements(reeval);
                                } else {
                                    ((RuleSessionImpl) ruleSession).reevaluateEvents(reeval);
                                }
                                currentContext.set(null);
                                // Hack to run the post-process
                                if (Thread.currentThread() instanceof BEManagedThread) {
                                    BEManagedThread parent = (BEManagedThread) Thread.currentThread();
                                    parent.executeEpilogue();
                                }
                            }
                            IDS.clear();
                        }
                    }
                    if (IDS.size() > 0) {

                        InferenceAgent inferenceAgent = (InferenceAgent) store.getCacheAgent();
                        Collection reeval = inferenceAgent.loadObjects(IDS, provider);
                        System.out.println("$$$$$ Reevaluating IDS =" + IDS);

                        if (reeval.size() > 0) {
                            currentContext.set(closure);
                            if (isElement) {
                                ((RuleSessionImpl) ruleSession).reevaluateElements(reeval);
                            } else {
                                ((RuleSessionImpl) ruleSession).reevaluateEvents(reeval);
                            }
                            currentContext.set(null);
                        }
                        IDS.clear();
                    }
                    done(rs.size());
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                error("Query = " + query + ", Args = " + args + ex.getMessage());
            } finally {
                currentContext.set(null);
            }
        }
    }

    
    public static int C_CacheOnlyMode_DeleteConcepts(String cacheName, Object filter) {
        int retCount = -1;

        RuleSession session = RuleSessionManager.getCurrentRuleSession();
        if (session != null) {
            ClassLoader contextCL =
                    (ClassLoader) session.getRuleServiceProvider().getTypeManager();
            ObjectManager om = session.getObjectManager();

            if (om instanceof DistributedCacheBasedStore) {
                DistributedCacheBasedStore cs_om = (DistributedCacheBasedStore) om;
                InferenceAgent inferenceAgent = (InferenceAgent) cs_om.getCacheAgent();
                if (null == filter) {
                	filter = AlwaysFilter.INSTANCE;
                }
                Filter cacheFilter = (Filter) filter;
                Logger logger = inferenceAgent.getLogger(CoherenceQueryFunctionsDelegate.class);

                BulkEntityDeleter entityDeleter = new BulkEntityDeleter(inferenceAgent);

                try {
                    retCount = entityDeleter.deleteItems(cacheName, cacheFilter, contextCL);
                }
                catch (Exception e) {
                    logger.log(Level.ERROR, CoherenceQueryFunctionsDelegate.class.getSimpleName() +
                            "-Error occurred while deleting Concepts", e);
                }
            }
        }

        return retCount;
    }

    
    public static int C_CacheOnlyMode_DeleteEntities(String cacheName, long[] ids) {
        int retCount = -1;

        RuleSession session = RuleSessionManager.getCurrentRuleSession();
        if (session != null) {
            ClassLoader contextCL =
                    (ClassLoader) session.getRuleServiceProvider().getTypeManager();
            ObjectManager om = session.getObjectManager();

            if (om instanceof DistributedCacheBasedStore) {
                DistributedCacheBasedStore cs_om = (DistributedCacheBasedStore) om;
                InferenceAgent inferenceAgent = (InferenceAgent) cs_om.getCacheAgent();
                Logger logger = inferenceAgent.getLogger(CoherenceQueryFunctionsDelegate.class);

                BulkEntityDeleter entityDeleter = new BulkEntityDeleter(inferenceAgent);

                try {
                    retCount = entityDeleter.deleteItems(cacheName, ids, contextCL);
                }
                catch (Exception e) {
                    logger.log(Level.ERROR, CoherenceQueryFunctionsDelegate.class.getSimpleName(),
                            "Error occurred while deleting entities", e);
                }
            }
        }

        return retCount;
    }

   

    public static Object C_KeyIterator(String cacheName, Object filter) {
        NamedCache cache = CacheFactory.getCache(cacheName,
                RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider().getClassLoader());
        if (cache == null) throw new RuntimeException("Invalid cache name specified - " + cacheName);
        Set rs = cache.keySet((Filter) filter);
        return rs.iterator();

    }

    
    public static boolean C_KeyHasNext(Object iterator) {
        if (!(iterator instanceof Iterator))
            throw new RuntimeException("Pass an iterator object returned from C_KeyIterator function");
        Iterator itr = (Iterator) iterator;
        return itr.hasNext();
    }

   

    public static long C_KeyNextValue(Object iterator) {
        if (!(iterator instanceof Iterator))
            throw new RuntimeException("Pass an iterator object returned from C_KeyIterator function");
        Iterator itr = (Iterator) iterator;
        return (Long) itr.next();
    }

    

    public static Object C_EntryIterator(String cacheName, Object filter) {
        NamedCache cache = CacheFactory.getCache(cacheName,
                RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider().getClassLoader());
        if (cache == null) throw new RuntimeException("Invalid cache name specified - " + cacheName);
        Set rs = cache.entrySet((Filter) filter);
        return rs.iterator();

    }

    
    public static boolean C_EntryHasNext(Object iterator) {
        if (!(iterator instanceof Iterator))
            throw new RuntimeException("Pass an iterator object return from C_KeyIterator function");
        Iterator itr = (Iterator) iterator;
        return itr.hasNext();
    }

    

    public static Entity C_EntryNextValue(Object iterator) {
        if (!(iterator instanceof Iterator))
            throw new RuntimeException("Pass an iterator object return from C_EntryIterator function");
        Iterator itr = (Iterator) iterator;
        Map.Entry entry = (Map.Entry) itr.next();
        final Entity entity = (Entity) entry.getValue();
        entity.setLoadedFromCache();
        return entity;
    }

    
    public static void C_EvictConcepts(String cacheName, long[] ids) {
        NamedCache cache = CacheFactory.getCache(cacheName);

        ArrayList<Long> list = new ArrayList<Long>(ids.length);
        for (long id : ids) {
            list.add(id);
        }

        cache.invokeAll(list, new AbstractProcessor() {
            @Override
            public Object process(Entry entry) {
                entry.remove(true);

                return null;
            }
        });
    }
}
