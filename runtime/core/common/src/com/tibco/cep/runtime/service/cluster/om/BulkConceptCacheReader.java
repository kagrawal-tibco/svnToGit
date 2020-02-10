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
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.Property;
import com.tibco.cep.runtime.model.element.PropertyArrayContainedConcept;
import com.tibco.cep.runtime.model.element.PropertyAtomContainedConcept;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.agent.InferenceAgent;
import com.tibco.cep.runtime.service.cluster.system.ObjectTable.Tuple;
import com.tibco.cep.runtime.service.om.FastLocalCache;
import com.tibco.cep.runtime.service.om.api.EntityDao;

/*
* Author: Ashwin Jayaprakash Date: Oct 2, 2008 Time: 11:47:40 AM
*/

public class BulkConceptCacheReader {
    protected InferenceAgent inferenceAgent;

    protected Cluster cluster;

    protected FastLocalCache localCache;

    protected Logger logger;

    public BulkConceptCacheReader(InferenceAgent inferenceAgent, Cluster cluster, FastLocalCache localCache) {
        this.inferenceAgent = inferenceAgent;
        this.cluster = cluster;
        this.localCache = localCache;
        this.logger = inferenceAgent.getLogger(BulkConceptCacheReader.class);
    }

    public InferenceAgent getInferenceAgent() {
        return inferenceAgent;
    }

    public Cluster getCluster() {
        return cluster;
    }

    public FastLocalCache getLocalCache() {
        return localCache;
    }

    public void discard() {
        inferenceAgent = null;
        cluster = null;
        localCache = null;
    }

    //------------

    public List<Concept> deference(ConceptImpl concept) throws Exception {
        LinkedList<Concept> collector = new LinkedList<Concept>();

        dereferenceContainedConcepts(collector, concept);

        return collector;
    }

    /**
     * @param collector
     * @param concept
     * @throws Exception
     */
    protected void dereferenceContainedConcepts(Collection<Concept> collector, ConceptImpl concept)
            throws Exception {
        Property.PropertyContainedConcept[] properties = concept.getContainedConceptProperties();
        if (properties == null) {
            return;
        }

        //------------

        //Collect all Ids.

        LinkedList<Long> allConceptIds = new LinkedList<Long>();

        for (Property.PropertyContainedConcept property : properties) {
            if (property instanceof PropertyAtomContainedConcept) {
                PropertyAtomContainedConcept atomCC = (PropertyAtomContainedConcept) property;
                long id = atomCC.getConceptId();

                allConceptIds.add(id);
            } else if (property instanceof PropertyArrayContainedConcept) {
                PropertyArrayContainedConcept arr = (PropertyArrayContainedConcept) property;

                for (int j = 0; j < arr.length(); j++) {
                    PropertyAtomContainedConcept atomCC = (PropertyAtomContainedConcept) arr.get(j);
                    long id = atomCC.getConceptId();

                    allConceptIds.add(id);
                }
            }
        }

        //------------

        //Classify Ids.
        Map<Integer, SortedSet<Long>> typeIdAndConceptIdMap = classify(allConceptIds);

        //------------

        //Download each Id category at once.

        for (Integer typeId : typeIdAndConceptIdMap.keySet()) {
            EntityDao provider = cluster.getMetadataCache().getEntityDao(typeId);

            SortedSet<Long> ids = typeIdAndConceptIdMap.get(typeId);

            processWorkItems(collector, provider, ids);
        }
    }

    private Map<Integer, SortedSet<Long>> classify(Collection<Long> allConceptIds)
            throws IOException {
        Map<Integer, SortedSet<Long>> typeIdAndConceptIdMap =
                new HashMap<Integer, SortedSet<Long>>();

        Collection<Tuple> entityTuples =
                inferenceAgent.getEntityTuplesById(allConceptIds);

        if (entityTuples != null) {
            Iterator<Long> idsIter = allConceptIds.iterator();
            Iterator<Tuple> tuplesIter = entityTuples.iterator();

            for (; idsIter.hasNext() && tuplesIter.hasNext();) {
                Long id = idsIter.next();
                Tuple tuple = tuplesIter.next();

                if (tuple != null) {
                    int typeId = tuple.getTypeId();

                    accumulate(typeIdAndConceptIdMap, typeId, id);
                } else {
                    logger.log(Level.ERROR, "[" + getClass().getSimpleName() +
                            "] There is no record of the Concept with Id: " + id);
                }
            }
        } else {
            logger.log(Level.ERROR, "[" + getClass().getSimpleName() +
                    "] There is no record of Concepts with the following Ids: " + allConceptIds);
        }
        return typeIdAndConceptIdMap;
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

    private void processWorkItems(Collection<Concept> collector, EntityDao provider,
                                  SortedSet<Long> ids) throws Exception {
        //Bulk load all the concepts.
        Collection<Concept> loadedConcepts = loadAll(provider, ids);

        //Recursively unfold each one of them.
        for (Concept loadedConcept : loadedConcepts) {
            ConceptImpl c = (ConceptImpl) loadedConcept;

            collector.add(c);

            dereferenceContainedConcepts(collector, c);
        }
    }

    private Collection<Concept> loadAll(EntityDao provider,
                                        SortedSet<Long> conceptIds) {
        LinkedList<Concept> loadedConcepts = new LinkedList<Concept>();

        //Purge the current entries if there are any.
        localCache.removeEntities(conceptIds);

        //Reload them afresh.
        Collection<Entity> results = provider.getAll(conceptIds);

        //Set<Map.Entry<Long, Object>> entrySet = results.entrySet();
        for (Entity entry : results) {

            Concept concept = (Concept) entry;
            concept.setLoadedFromCache();
            localCache.putEntity(entry.getId(), concept);

            loadedConcepts.add(concept);
        }

        return loadedConcepts;
    }
}