/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.be.functions.coherence.query;

import static com.tibco.be.model.functions.FunctionDomain.ACTION;
import static com.tibco.be.model.functions.FunctionDomain.CONDITION;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.tangosol.net.NamedCache;
import com.tangosol.util.Filter;
import com.tangosol.util.InvocableMap;
import com.tangosol.util.processor.AbstractProcessor;
import com.tibco.be.functions.coherence.filters.CoherenceFilterFunctions;
import com.tibco.be.functions.coherence.query.backingstore.converters.CoherenceBackingStoreConverterFunctions;
import com.tibco.be.model.functions.Enabled;
import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.cep.runtime.model.event.AdvisoryEvent;
import com.tibco.cep.runtime.model.event.AdvisoryEventDictionary;
import com.tibco.cep.runtime.model.event.impl.AdvisoryEventImpl;
import com.tibco.cep.runtime.service.cluster.CacheClusterProvider;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.agent.InferenceAgent;
import com.tibco.cep.runtime.service.cluster.backingstore.BackingStore;
import com.tibco.cep.runtime.service.cluster.backingstore.GenericBackingStore;
import com.tibco.cep.runtime.service.cluster.om.DistributedCacheBasedStore;
import com.tibco.cep.runtime.service.om.api.EntityDao;
import com.tibco.cep.runtime.session.BEManagedThread;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.impl.RuleSessionImpl;

@com.tibco.be.model.functions.BEPackage(
		catalog = "Coherence",
        category = "Coherence.Query",
        enabled = @Enabled(property="TIBCO.BE.function.catalog.coherence",value=false),
        synopsis = "Functions for querying the Cache")
public class CoherenceQueryFunctions {
    static ThreadLocal currentContext = new ThreadLocal();

    @com.tibco.be.model.functions.BEFunction(
        name = "C_CurrentContext",
        synopsis = "Return the current batch execution context.",
        signature = "String C_CurrentContext()",
        params = {
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "java.lang.String", desc = "Current batch context."),
        version = "2.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Return the current batch execution context.",
        cautions = "none",
        fndomain = {ACTION, CONDITION},
        reevaluate = true,
        example = ""
    )

    public static String C_CurrentContext() {
        return CoherenceQueryFunctionsDelegate.C_CurrentContext();
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "C_QueryIDs",
        synopsis = "Execute a query against a named cache.  Query operates only on cache memory, not backing store.",
        signature = "long[] C_QueryIDs(String cacheName, Object filter)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "cacheName", type = "String", desc = "Name of the targeted cache."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "filter", type = "Object", desc = "Conditional expression to drive selection process.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "long[]", desc = "List of IDs that satisfy the query."),
        version = "2.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Execute a query against a named cache.  Query operates only on cache memory, not backing store.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )

    public static long[] C_QueryIDs(String cacheName, Object filter) {
        return CoherenceQueryFunctionsDelegate.C_QueryIDs(cacheName, filter);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "C_QueryConcepts",
        synopsis = "Execute a query against a named cache.  Query operates only on cache memory, not backing store.",
        signature = "Concept[] C_QueryConcepts(String cacheName, Object filter, boolean queryOnly)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "cacheName", type = "String", desc = "Name of the targeted cache"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "filter", type = "Object", desc = "Conditional expression to drive selection process"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "queryOnly", type = "boolean", desc = "Should be set to true if the returned concepts are used in the current rule body")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Concept[]", desc = "List of Concepts that satisfy the query"),
        version = "2.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Execute a query against a named cache.  Query operates only on cache memory, not backing store.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static Concept[] C_QueryConcepts(String cacheName, Object filter, boolean queryOnly) {
       return CoherenceQueryFunctionsDelegate.C_QueryConcepts(cacheName, filter, queryOnly);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "C_QueryAction",
        synopsis = "Schedule assertion of objects from the cache to the working memory.  Query operates only on cache memory, not backing store. Typically used in a preprocessor to load multiple concepts from cache to the Rete network. Then to make objects loaded by this function trigger rules, use the value of closure in a rule's join condition using the format Coherence.Query.C_CurrentContext == String $1<closure>$1.",
        signature = "void C_QueryAction(String cacheName, Object filter, int batchsize, String closure)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "cacheName", type = "String", desc = "Name of the targeted cache."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "filter", type = "Object", desc = "Conditional expression to drive selection process."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "batchSize", type = "int", desc = "The number of objects retrieved from the cache per invocation."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "closure", type = "String", desc = "Current context string identifier.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "2.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Schedule assertion of objects from the cache to the working memory.  Query operates only on cache memory, not backing store. Typically used in a preprocessor to load multiple concepts from cache to the Rete network. Then to make objects loaded by this function trigger rules, use the value of closure in a rule's join condition using the format Coherence.Query.C_CurrentContext == String $1<closure>$1.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static void C_QueryAction(String cacheName, Object filter, int batchSize, String closure) {
    	CoherenceQueryFunctionsDelegate.C_QueryAction(cacheName, filter, batchSize, closure);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "C_StoreQueryAction",
        synopsis = "Schedule reevaluation of objects from the underlying datastore to the working memory.",
        signature = "void C_StoreQueryAction(String workManagerID, String query, Object[] args, String className, int batchSize, String closure)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "workManagerID", type = "ID", desc = "of the work manager. The job will be scheduled in this workManager"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "query", type = "String", desc = "representing the query expression"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "args", type = "Object[]", desc = "Query expression arguments"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "entityURI", type = "String", desc = "URI of the entity type to schedule for reevaluation"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "batchSize", type = "int", desc = "he number of objects retrieved from the cache per invocation"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "closure", type = "String", desc = "Current context string identifier")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "2.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Schedule reevaluation of objects from the underlying datastore to the working memory.",
        cautions = "none",
        enabled=@Enabled(property="TIBCO.BE.function.catalog.coherence.query.ext",value=false),
        fndomain = {ACTION},
        example = ""
    )
    public static void C_StoreQueryAction(String workManagerID, String query, Object[] args, String entityURI, int batchSize, String closure) {
    	CoherenceQueryFunctionsDelegate.C_StoreQueryAction(workManagerID, query, args, entityURI, batchSize, closure);
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "C_CacheInvoke",
        synopsis = "Invoke a rule function for all matched objects in cache memory for the passed filter.",
        signature = "Object[] C_CacheInvoke(String cacheName, Object filter, String ruleFunction)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "cacheName", type = "String", desc = "Name of the targeted cache"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "filter", type = "Object", desc = "Conditional expression to drive selection process"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "ruleFunction", type = "String", desc = "Rule function URI")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object[]", desc = "Array of all the values returned by the rule function."),
        version = "2.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Invoke a rule function for all matched objects in cache memory for passed filter.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static Object[] C_CacheInvoke(String cacheName, Object filter, String ruleFunction) {
        return CoherenceQueryFunctionsDelegate.C_CacheInvoke(cacheName, filter, ruleFunction);
    }
    @com.tibco.be.model.functions.BEFunction(
        name = "C_QueryEvents",
        synopsis = "Execute a query against a named cache.  Query operates only on cache memory, not backing store. For the extractor used in the left hand side of the filter condition, use Coherence.Extractors.C_EventPropertyGetter.",
        signature = "Event[] C_QueryEvents(String cacheName, Object filter, boolean queryOnly)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "cacheName", type = "String", desc = "Name of the targeted cache"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "filter", type = "Object", desc = "Conditional expression to drive selection process."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "queryOnly", type = "boolean", desc = "Set to true if the returned objects are used in the current rule body.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Event[]", desc = "List of Events matching the query."),
        version = "2.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Execute a query against a named cache.  Query operates only on cache memory, not backing store. For the extractor used in the left hand side of the filter condition, use Coherence.Extractors.C_EventPropertyGetter.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )

    public static Event[] C_QueryEvents(String cacheName, Object filter, boolean queryOnly) {
        return CoherenceQueryFunctionsDelegate.C_QueryEvents(cacheName, filter, queryOnly);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "C_QueryEvents_Order",
        synopsis = "Execute a query against a named cache. The resultset can be ordered using a rule function. Query operates only on cache memory, not backing store.  For the extractor used in the left hand side of the filter condition, use Coherence.Extractors.C_EventPropertyGetter.",
        signature = "Event[] C_QueryEvents_Order(String cacheName, Object filter, String ruleFunction)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "cacheName", type = "String", desc = "Name of the targeted cache"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "filter", type = "Object", desc = "Conditional expression to drive selection process"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "orderByRuleFunction", type = "URI", desc = "of the RuleFunction used for ordering of the result set.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Event[]", desc = "List of Events matching the query"),
        version = "2.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Execute a query against a named cache. The resultset can be ordered using a rule function. Query operates only on cache memory, not backing store. For the extractor used in the left hand side of the filter condition, use Coherence.Extractors.C_EventPropertyGetter.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )

    public static Event[] C_QueryEvents_Order(String cacheName, Object filter, String orderByRuleFunction) {
       return CoherenceQueryFunctionsDelegate.C_QueryEvents_Order(cacheName, filter, orderByRuleFunction);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "C_QueryAndLoadConcepts",
        synopsis = "Execute a query against a named cache and load result set into WM.  Query operates only on cache memory, not backing store.",
        signature = "Concept[] C_QueryAndLoadConcepts(String cacheName, Object filter, boolean loadContained)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "cacheName", type = "String", desc = "Name of the targeted cache"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "filter", type = "Object", desc = "Conditional expression to drive selection process"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "loadContained", type = "boolean", desc = "If true load the entire complex concepts; if false load only the root concepts")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Concept[]", desc = "Array of Concepts that satisfy the query"),
        version = "2.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Execute a query against a named cache and load results into WM.  Query operates only on cache memory, not backing store.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )

    public static Concept[] C_QueryAndLoadConcepts(String cacheName, Object filter, boolean loadContained) {
        return CoherenceQueryFunctionsDelegate.C_QueryAndLoadConcepts(cacheName, filter, loadContained);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "C_CacheOnlyMode_QueryConcepts",
        synopsis = "Execute a query against a named cache. Optimized for cache only mode.  Query operates only on cache memory, not backing store.",
        signature = "Concept[] C_CacheOnlyMode_QueryConcepts(String cacheName, Object filter, boolean queryOnly)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "cacheName", type = "String", desc = "Name of the targeted cache."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "filter", type = "Object", desc = "Conditional expression to drive selection process."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "queryOnly", type = "boolean", desc = "Set to true if the returned concepts are only used in the current rule body.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Concept[]", desc = "Array of Concepts that satisfy the query."),
        version = "2.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Execute a query against a named cache. Optimized for cache only mode.  Query operates only on cache memory, not backing store.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static Concept[] C_CacheOnlyMode_QueryConcepts(String cacheName, Object filter, boolean queryOnly) {
        return CoherenceQueryFunctionsDelegate.C_CacheOnlyMode_QueryConcepts(cacheName, filter, queryOnly);
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

    @com.tibco.be.model.functions.BEFunction(
        name = "C_CacheOnlyMode_DeleteConcepts",
        synopsis = "Execute a query deletion on a cache. Used for cache only mode.  Query operates only on cache memory, not backing store.",
        signature = "int C_CacheOnlyMode_DeleteConcepts(String cacheName, Object filter)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "cacheName", type = "String", desc = "Name of the targeted cache"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "filter", type = "Object", desc = "<code>null</code> if all the instances in the cache have to be deleted.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "<code>-1</code> if there was an error. 0 or higher otherwise to denote the number of\ninstances that were deleted"),
        version = "3.0.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Execute a query deletion on a cache. Used for cache only mode.  Query operates only on cache memory, not backing store.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static int C_CacheOnlyMode_DeleteConcepts(String cacheName, Object filter) {
        return CoherenceQueryFunctionsDelegate.C_CacheOnlyMode_DeleteConcepts(cacheName, filter);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "C_CacheOnlyMode_DeleteEntities",
        synopsis = "Execute a query deletion on a cache. For cache only mode.  Query operates only on cache memory, not backing store.",
        signature = "int C_CacheOnlyMode_DeleteEntities(String cacheName, long[] ids)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "cacheName", type = "String", desc = "Name of the targeted cache."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "ids", type = "long[]", desc = "List of entity (Concept or Event) Ids to be deleted.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "-1 if there was an error. 0 or higher otherwise to denote the number of\ninstances that were deleted."),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Execute a query deletion on a cache. For cache only mode.  Query operates only on cache memory, not backing store.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static int C_CacheOnlyMode_DeleteEntities(String cacheName, long[] ids) {
        return CoherenceQueryFunctionsDelegate.C_CacheOnlyMode_DeleteEntities(cacheName, ids);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "C_KeyIterator",
        synopsis = "Returns an Iterator for KeySet on the cache. Use this iterator in next and hasNext.\nThe iterator is a snapshot of the current set, and is not modifiable nor are any changes to the backing map\nreflected by this iterator.",
        signature = "Object C_KeyIterator(String cacheName, Object filter)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "cacheName", type = "String", desc = "Name of the targeted cache."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "filter", type = "Object", desc = "Conditional expression to drive selection process.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Iterator as an object."),
        version = "3.0.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns an Iterator for KeySet on the cache. Use this iterator in next and hasNext.\nThe iterator is a snapshot of the current set, and is not modifiable nor are any changes to the backing map\nreflected by this iterator.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )

    public static Object C_KeyIterator(String cacheName, Object filter) {
        return CoherenceQueryFunctionsDelegate.C_KeyIterator(cacheName, filter);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "C_KeyHasNext",
        synopsis = "Returns a boolean value to check if iterator has more values",
        signature = "boolean C_KeyHasNext(Object iterator)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "iterator", type = "Object", desc = "The Iterator on which hasNext is called.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "true if iterator has more values"),
        version = "3.0.1",
        see = "C_KeyIterator",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static boolean C_KeyHasNext(Object iterator) {
        return CoherenceQueryFunctionsDelegate.C_EntryHasNext(iterator);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "C_KeyNextValue",
        synopsis = "Returns the next value for iterator.",
        signature = "long C_KeyNextValue(Object iterator)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "iterator", type = "Object", desc = "The Iterator on which the next is called.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "long", desc = "The key of the entry - Use load/getxxx after that."),
        version = "3.0.1",
        see = "C_KeyIterator",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the next value for iterator.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )

    public static long C_KeyNextValue(Object iterator) {
       return CoherenceQueryFunctionsDelegate.C_KeyNextValue(iterator);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "C_EntryIterator",
        synopsis = "Returns an Iterator for EntrySet on the cache. Use this iterator in next and hasNext.\nThe iterator is a snapshot of the current set, and is not modifiable nor are any changes to the backing map\nreflected by this iterator",
        signature = "Object C_EntryIterator(String cacheName, Object filter)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "cacheName", type = "String", desc = "Name of the targeted cache"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "filter", type = "Object", desc = "Conditional expression to drive selection process")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Iterator", desc = "as an Object"),
        version = "3.0.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns an Iterator for EntrySet on the cache. Use this iterator in next and hasNext.\nThe iterator is a snapshot of the current set, and is not modifiable nor are any changes to the backing map\nreflected by this iterator",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )

    public static Object C_EntryIterator(String cacheName, Object filter) {
        return CoherenceQueryFunctionsDelegate.C_EntryIterator(cacheName, filter);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "C_EntryHasNext",
        synopsis = "Returns a boolean value to check if iterator has more values",
        signature = "boolean C_EntryHasNext(Object iterator)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "iterator", type = "Object", desc = "The Iterator on which hasNext is called.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "true if iterator has more values"),
        version = "3.0.1",
        see = "C_KeyIterator",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns a boolean value to check if iterator has more values.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static boolean C_EntryHasNext(Object iterator) {
        return CoherenceQueryFunctionsDelegate.C_EntryHasNext(iterator);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "C_EntryNextValue",
        synopsis = "Returns the next value for iterator.",
        signature = "Entity C_EntryNextValue(Object iterator)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "iterator", type = "Object", desc = "The Iterator on which next is called.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Entity", desc = "The next value of iterator."),
        version = "3.0.1",
        see = "C_KeyIterator",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the next value of iterator.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )

    public static Entity C_EntryNextValue(Object iterator) {
        return CoherenceQueryFunctionsDelegate.C_EntryNextValue(iterator);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "C_EvictConcepts",
        synopsis = "Removes/evicts concepts from the cache (only). If a write-behind database is configured, then the database will not be affected.",
        signature = "void C_EvictConcepts(String cacheName, long[] ids)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "cacheName", type = "String", desc = "Name of the targeted cache")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "4.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Removes/evicts concepts from the cache (only). If a write-behind database is configured, then the database will not be affected.",
        cautions = "none",
        enabled=@Enabled(property="TIBCO.BE.function.catalog.cluster.evictConceptsFunction",value=false),
        fndomain = {ACTION},
        example = ""
    )
    public static void C_EvictConcepts(String cacheName, long[] ids) {
        CoherenceQueryFunctionsDelegate.C_EvictConcepts(cacheName, ids);
    }
}
