/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.cluster.om;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.agent.InferenceAgent;
import com.tibco.cep.runtime.service.cluster.system.ObjectTable.Tuple;
import com.tibco.cep.runtime.service.om.api.EntityDao;
import com.tibco.cep.runtime.service.om.api.invm.LocalCache;

/*
* Author: Ashwin Jayaprakash Date: Nov 6, 2008 Time: 3:40:04 PM
*/

public class BulkConceptRetriever {
    protected InferenceAgent inferenceAgent;

    protected Cluster cluster;

    protected LocalCache localCache;

    public BulkConceptRetriever(InferenceAgent inferenceAgent, Cluster cluster, LocalCache localCache) {
        this.inferenceAgent = inferenceAgent;
        this.cluster = cluster;
        this.localCache = localCache;
    }

    public InferenceAgent getInferenceAgent() {
        return inferenceAgent;
    }

    public Cluster getCluster() {
        return cluster;
    }

    public LocalCache getLocalCache() {
        return localCache;
    }

    public void discard() {
        inferenceAgent = null;
        cluster = null;
        localCache = null;
    }

    //---------------

    /**
     * Retrieve afresh from the Cache and forcibly place it in the {@link #getLocalCache()}.
     * When URI is specified, all the conceptExtIds are of the same type, otherwise they can be of many types
     * @param conceptExtIds
     * @return
     * @throws Exception
     */
    public Result retrieve(Collection<String> conceptExtIds, String uri) throws Exception {
        Result result = new Result();

        if (uri != null && !inferenceAgent.getCluster().getClusterConfig().useObjectTable()) {
            inferenceAgent.getCluster().getObjectTableCache().putExtIdsToTypeCache(conceptExtIds, uri);
        }
        
        //Classify Ids.
        Map<Integer, SortedSet<Long>> typeIdAndConceptIdMap = classify(conceptExtIds, result);

        //-------------

        if (typeIdAndConceptIdMap != null) {
            //Download each Id category at once.
            for (Integer typeId : typeIdAndConceptIdMap.keySet()) {
                EntityDao provider = cluster.getMetadataCache().getEntityDao(typeId);

                SortedSet<Long> ids = typeIdAndConceptIdMap.get(typeId);

                load(provider, ids, result);
            }
        }

        //-------------

        Map<String, Concept> found = result.getConceptsFound();
        Set<String> notFound = result.getExtIdsNotFound();

        //Reconcile which ones were found and which ones were not.
        for (String extId : conceptExtIds) {
            if (found.containsKey(extId) == false) {
                notFound.add(extId);
            }
        }

        return result;
    }

    /**
     * @param allConceptExtIds
     * @param result
     * @return <code>null</code> if nothing was found. All extIds get added to {@link
     *         BulkConceptRetriever.Result#addExtIdNotFound(String)
     *         result}.
     * @throws IOException
     */
    private Map<Integer, SortedSet<Long>> classify(Collection<String> allConceptExtIds,
                                                   Result result) throws IOException {
        Collection<Tuple> entityTuples =
                inferenceAgent.getEntityTuplesByExtId(allConceptExtIds);

        if (entityTuples == null || entityTuples.isEmpty()) {
            for (String extIdNotFound : allConceptExtIds) {
                result.addExtIdNotFound(extIdNotFound);
            }

            return null;
        }

        //----------------

        Map<Integer, SortedSet<Long>> typeIdAndConceptIdMap =
                new HashMap<Integer, SortedSet<Long>>();

        for (Tuple tuple : entityTuples) {
        	if(tuple == null) continue;
            if(tuple.isDeleted()) {
                result.addExtIdNotFound(tuple.getExtId());
            } else {
                int typeId = tuple.getTypeId();
                long id = tuple.getId();

                accumulate(typeIdAndConceptIdMap, typeId, id);
            }
        }
        
        //in case all the returned tuples were marked deleted
        if(typeIdAndConceptIdMap.isEmpty()) return null;
        else return typeIdAndConceptIdMap;
    }

    private void accumulate(Map<Integer, SortedSet<Long>> typeIdAndConceptIdMap, Integer typeId,
                            Long conceptId) {
        SortedSet<Long> ids = typeIdAndConceptIdMap.get(typeId);

        if (ids == null) {
            ids = new TreeSet<Long>();
            typeIdAndConceptIdMap.put(typeId, ids);
        }

        ids.add(conceptId);
    }

    /**
     * @param provider
     * @param conceptIds
     * @param result
     */
    private void load(EntityDao provider, SortedSet<Long> conceptIds, Result result) {
        //Reload them afresh.
        Collection<Entity> results = provider.getAll(conceptIds);

        for (Entity entry : results) {
            Concept concept = (Concept) entry;
            concept.setLoadedFromCache();
            localCache.directPut(concept);

            result.addConceptFound(concept);
        }
    }

    //---------------

    public static class Result {
        protected final HashMap<String, Concept> conceptsFound;

        protected final HashSet<String> extIdsNotFound;

        public Result() {
            this.conceptsFound = new HashMap<String, Concept>();
            this.extIdsNotFound = new HashSet<String>();
        }

        protected void addConceptFound(Concept concept) {
            String extId = concept.getExtId();

            conceptsFound.put(extId, concept);
        }

        protected void addExtIdNotFound(String extId) {
            extIdsNotFound.add(extId);
        }

        /**
         * @return Map of extId and the concept.
         */
        public Map<String, Concept> getConceptsFound() {
            return conceptsFound;
        }

        public Set<String> getExtIdsNotFound() {
            return extIdsNotFound;
        }
    }
}
