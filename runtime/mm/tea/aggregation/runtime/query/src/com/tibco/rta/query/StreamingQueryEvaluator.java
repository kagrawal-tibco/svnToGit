package com.tibco.rta.query;

import java.util.List;

import com.tibco.rta.MetricKey;
import com.tibco.rta.query.filter.eval.FilterEvaluator;
import com.tibco.rta.query.filter.eval.FilterEvaluatorFactory;
import com.tibco.rta.runtime.model.MetricNode;

public class StreamingQueryEvaluator {

	QueryDef queryDef;
	FilterEvaluator filterEvaluator;
	QueryByKeyDef keyFilter;
	MetricKey metricKey;

	
	String schemaName;
	String cubeName;
	String hierarchyName;
	String queryName;
	QueryType queryType;
	int batchSize;
	boolean isFilterBasedQuery;
	
	public StreamingQueryEvaluator(QueryDef queryDef) {
		
		this.queryDef = queryDef;
		this.queryName = queryDef.getName();
		this.queryType = queryDef.getQueryType();
		this.batchSize = queryDef.getBatchSize();
		this.isFilterBasedQuery = (queryDef instanceof QueryByFilterDef);
		
		if (isFilterBasedQuery) {
			QueryByFilterDef filterQuery = (QueryByFilterDef) queryDef;
			filterEvaluator = FilterEvaluatorFactory
					.createEvaluator(((QueryByFilterDef) queryDef).getFilter());
			schemaName = filterQuery.getSchemaName();
			cubeName = filterQuery.getCubeName();
			hierarchyName = filterQuery.getHierarchyName();
		} else if (queryDef instanceof QueryByKeyDef) {
			keyFilter = (QueryByKeyDef) queryDef;
			metricKey = keyFilter.getQueryKey();
			schemaName = metricKey.getSchemaName();
			cubeName = metricKey.getCubeName();
			hierarchyName = metricKey.getDimensionHierarchyName();
		}
	}
	
	public QueryDef getQueryDef() {
		return queryDef;
	}

	public void setQueryDef(QueryDef queryDef) {
		this.queryDef = queryDef;
	}
	
	public boolean eval(MetricNode node) throws Exception {

		MetricKey key = (MetricKey) node.getKey();

		boolean passed = false;

		passed = schemaCheck(schemaName, cubeName, hierarchyName, key);
		if (passed) {
			if (isFilterBasedQuery) {
				if (filterEvaluator != null) {
					passed = filterEvaluator.eval(node);
				} else {
					//do not filter anything.
					passed = true;
				} 
			} else if (keyFilter != null) {
				passed = dimensionLevelCheck(node, metricKey);
				if (passed) {
					passed = dimensionValueCheck(node, metricKey);
				}
			}
		}

		return passed;
	}

	private boolean dimensionValueCheck(MetricNode node, MetricKey suppliedKey) {

		MetricKey key = (MetricKey) node.getKey();
		boolean passed = true;
		List<String> dimNames = suppliedKey.getDimensionNames();

		for (String dimName : dimNames) {

			// get the value from the metrics key
			Object value = key.getDimensionValue(dimName);
			
			// and from the supplied key
			Object suppliedVal = suppliedKey.getDimensionValue(dimName);
			
			if (suppliedVal != null) {
				suppliedVal = suppliedVal + "";
				value = value + "";
				if (!suppliedVal.equals(value)) {
					passed = false;
					break;
				}
			} else if (value != null) {				
				passed = false;
				break;
			}
		}

		return passed;

	}

	private boolean schemaCheck(String schemaName, String cubeName,
			String hierarchyName, MetricKey key) {
		return key.getSchemaName().equals(schemaName)
				&& key.getCubeName().equals(cubeName)
				&& key.getDimensionHierarchyName().equals(hierarchyName);
	}

	private boolean dimensionLevelCheck(MetricNode node, MetricKey metricKey) {
		boolean passed = false;
		String dimensionName = metricKey.getDimensionLevelName();
		if (dimensionName != null) {
			MetricKey nodeKey = (MetricKey) node.getKey();

			if (dimensionName.equals(nodeKey.getDimensionLevelName())) {
				passed = true;
			}

		} else {
			// any dimension level
			passed = true;
		}
		return passed;
	}
}
