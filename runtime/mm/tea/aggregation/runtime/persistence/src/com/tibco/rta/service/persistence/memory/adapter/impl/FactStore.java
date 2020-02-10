package com.tibco.rta.service.persistence.memory.adapter.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import com.tibco.rta.Fact;
import com.tibco.rta.Key;
import com.tibco.rta.model.RtaSchema;
import com.tibco.rta.runtime.model.MetricNode;
import com.tibco.rta.runtime.model.RtaNode;
import com.tibco.rta.service.persistence.memory.InMemoryConstant;

public class FactStore {

	// metric node to fact nodes mapping for leaf metric nodes.
	private Map<Key, LinkedHashSet<Key>> childFacts = Collections.synchronizedMap(new LinkedHashMap<Key, LinkedHashSet<Key>>());

	private Map<Key, FactMemoryTuple> factStore = new HashMap<Key, FactMemoryTuple>();

	private RtaSchema ownerSchema;

	public FactStore(RtaSchema ownerSchema) {
		this.ownerSchema = ownerSchema;
	}

	public void save(FactMemoryTuple fact) {
		factStore.put(fact.getKey(), fact);
	}

	public void delete(FactMemoryTuple fact) {
		factStore.remove(fact.getKey());
	}

	public void addFact(RtaNode node, FactMemoryTuple fact) {
		if (node instanceof MetricNode) {
			MetricNode metricNode = (MetricNode) node;
			LinkedHashSet<Key> facts = childFacts.get(metricNode.getKey());
			if (facts == null) {
				facts = new LinkedHashSet<Key>();
				childFacts.put(metricNode.getKey(), facts);
			}
			facts.add(fact.getKey());
			factStore.put(fact.getKey(), fact);
		}
	}

	public void deleteFact(RtaNode node, FactMemoryTuple fact) {
		if (node instanceof MetricNode) {
			MetricNode metricNode = (MetricNode) node;
			LinkedHashSet<Key> facts = childFacts.get(metricNode.getKey());
			if (facts != null) {
				facts.remove(fact.getKey());
				factStore.remove(fact.getKey());
			}
		}
	}

	public RtaSchema getOwnerSchema() {
		return ownerSchema;
	}

	public Iterator<Fact> getChildFactsIterator(RtaNode node) {
		Set<Fact> result = new HashSet<Fact>();
		for (Key key : childFacts.get(node.getKey())) {
			result.add((Fact) factStore.get(key).getWrappedObject());
		}
		return result.iterator();
	}

	public long purgeFacts(long purgeOlderThan) {
		long purgeCount = 0;
		long deleteTime = System.currentTimeMillis() - purgeOlderThan;
		Collection<Key> c = new LinkedList<Key>(factStore.keySet());
		for (Key key : c) {
			FactMemoryTuple fact = factStore.get(key);
			if ((Long) fact.getProperty(InMemoryConstant.CREATED_DATE_TIME_FIELD) < deleteTime) {
				delete(fact);
				purgeCount = purgeCount + 1;
			}
		}
		return purgeCount;
	}

	public int getSize() {
		return factStore.size();
	}
}
