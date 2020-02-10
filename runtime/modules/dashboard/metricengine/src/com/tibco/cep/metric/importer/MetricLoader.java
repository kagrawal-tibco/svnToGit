package com.tibco.cep.metric.importer;

import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.service.cluster.CacheClusterProvider;
import com.tibco.cep.runtime.service.cluster.Cluster;

import java.util.ArrayList;
import java.util.List;

public class MetricLoader {

	private Cluster cluster;
	private CachedMetrics cachedMetrics;

	public MetricLoader() {
		this.cluster = CacheClusterProvider.getInstance().getCacheCluster();
		cachedMetrics = new CachedMetrics(cluster);
	}

	public void init() throws Exception {
		cachedMetrics.getAllMetrics();
		cachedMetrics.getAllConcepts();
	}

	public List<Concept> fetchAllMetrics() throws Exception {
		List<Concept> allMetrics = new ArrayList<Concept>();
		synchronized (this) {
			allMetrics.addAll(cachedMetrics.getMetrics());
		}
		return allMetrics;
	}

	public List<Concept> fetchMetrics() throws Exception {
		return cachedMetrics.getMetrics();
	}

	public List<Concept> fetchMetricsDVM() throws Exception {
		return cachedMetrics.getMetricsDVM();
	}

	public List<Concept> fetchConcepts() throws Exception {
		return cachedMetrics.getConcepts();
	}
}
