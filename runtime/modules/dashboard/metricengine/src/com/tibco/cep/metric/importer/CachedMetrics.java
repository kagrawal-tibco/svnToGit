package com.tibco.cep.metric.importer;

import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.Metric;
import com.tibco.cep.runtime.model.element.MetricDVM;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.system.MetadataCache;
import com.tibco.cep.runtime.service.om.api.EntityDao;

import java.util.*;

public class CachedMetrics {

	private Cluster cluster;

	private List<Integer> typeIdList = new ArrayList<Integer>();

	private List<Class<Concept>> conceptClassList = new ArrayList<Class<Concept>>();
	private List<Class<Concept>> metricClassList = new ArrayList<Class<Concept>>();
	private List<Class<Concept>> metricDVMClassList = new ArrayList<Class<Concept>>();

	private Map<Class<Concept>, Integer> conceptTypeIdMap = new HashMap<Class<Concept>, Integer>();
	private Map<Class<Concept>, Integer> metricTypeIdMap = new HashMap<Class<Concept>, Integer>();
	private Map<Class<Concept>, Integer> metricDVMTypeIdMap = new HashMap<Class<Concept>, Integer>();

	public CachedMetrics(Cluster cacheCluster) {
		this.cluster = cacheCluster;
	}

	@SuppressWarnings("unchecked")
	public synchronized void getAllMetrics() throws Exception {
		// Go through metadata cache and find all objects of type metric and
		// metricDVM
		MetadataCache cache = cluster.getMetadataCache();
		Class<?>[] clazz = cache.getRegisteredTypes();
		for (Class<?> metricClass : clazz) {
			if (metricClass != null) {
				if (Metric.class.isAssignableFrom(metricClass)) {
					int typeId = cache.getTypeId(metricClass);
					typeIdList.add(typeId);
					metricTypeIdMap.put((Class<Concept>) metricClass, typeId);
					Class<Concept> conceptClass = (Class<Concept>) metricClass;
					metricClassList.add(conceptClass);
				} else if (MetricDVM.class.isAssignableFrom(metricClass)) {
					int typeId = cache.getTypeId(metricClass);
					typeIdList.add(typeId);
					Class<Concept> conceptClass = (Class<Concept>) metricClass;
					metricDVMTypeIdMap.put(conceptClass, typeId);
					metricDVMClassList.add(conceptClass);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public synchronized void getAllConcepts() throws Exception {
		// Go through metadata cache and find all objects of type concept
		MetadataCache cache = cluster.getMetadataCache();
		Class<?>[] clazz = cache.getRegisteredTypes();
		for (Class<?> conceptClass : clazz) {
			if (conceptClass != null) {
				if (Concept.class.isAssignableFrom(conceptClass)) {
					int typeId = cache.getTypeId(conceptClass);
					typeIdList.add(typeId);
					conceptTypeIdMap.put((Class<Concept>) conceptClass, typeId);
					conceptClassList.add((Class<Concept>) conceptClass);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public synchronized List<Concept> getMetrics() throws Exception {
		List<Concept> metrics = new ArrayList<Concept>();
		for (Class<?> metricClass : metricClassList) {
			for (Iterator metricIterator = getIterator(metricClass); metricIterator
					.hasNext();) {
				Map.Entry<Long, Concept> tuple = (Map.Entry<Long, Concept>) metricIterator
						.next();
				metrics.add(tuple.getValue());
			}
		}
		return metrics;
	}

	@SuppressWarnings("unchecked")
	public synchronized List<Concept> getMetricsDVM() throws Exception {
		List<Concept> metrics = new ArrayList<Concept>();
		for (Class<?> metricDVMClass : metricDVMClassList) {
			for (Iterator metricIterator = getIterator(metricDVMClass); metricIterator
					.hasNext();) {
				Map.Entry<Long, Concept> tuple = (Map.Entry<Long, Concept>) metricIterator
						.next();
				metrics.add(tuple.getValue());
			}
		}
		return metrics;
	}

	@SuppressWarnings("unchecked")
	public synchronized List<Concept> getConcepts() throws Exception {
		List<Concept> concepts = new ArrayList<Concept>();
		for (Class<?> conceptClass : conceptClassList) {
			for (Iterator conceptIterator = getIterator(conceptClass); conceptIterator
					.hasNext();) {
				Map.Entry<Long, Concept> tuple = (Map.Entry<Long, Concept>) conceptIterator
						.next();
				concepts.add(tuple.getValue());
			}
		}
		return concepts;
	}

	@SuppressWarnings("unchecked")
	private Iterator getIterator(Class entityClz) throws Exception {
		EntityDao provider = cluster.getMetadataCache().getEntityDao(entityClz);
		return provider.getAll().iterator();
	}
}
