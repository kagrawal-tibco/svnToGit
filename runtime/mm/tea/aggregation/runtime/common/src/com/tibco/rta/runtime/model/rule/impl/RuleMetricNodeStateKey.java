package com.tibco.rta.runtime.model.rule.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import com.tibco.rta.Key;
import com.tibco.rta.MetricKey;
import com.tibco.rta.common.registry.ModelRegistry;
import com.tibco.rta.impl.MetricKeyImpl;
import com.tibco.rta.model.Cube;
import com.tibco.rta.model.Dimension;
import com.tibco.rta.model.DimensionHierarchy;
import com.tibco.rta.model.RtaSchema;
import com.tibco.rta.model.TimeDimension;

public class RuleMetricNodeStateKey implements Key {

	private static final long serialVersionUID = -1606553268510180834L;
	
	protected String actionName;
	protected String ruleName;
	protected MetricKey metricKey;

	public RuleMetricNodeStateKey(String ruleName, String actionName, MetricKey metricKey) {
		this.actionName = actionName;
		this.ruleName = ruleName;		
		this.metricKey = cloneMetricKeyWithoutTimeDimension(metricKey);
	}
	
	private MetricKey cloneMetricKeyWithoutTimeDimension(MetricKey metricKey){
		MetricKeyImpl original = (MetricKeyImpl) metricKey;
		MetricKeyImpl cloned = new MetricKeyImpl();
		cloned.setSchemaName(original.getSchemaName());
		cloned.setCubeName(original.getCubeName());
		cloned.setDimensionHierarchyName(original.getDimensionHierarchyName());
		cloned.setDimensionLevelName(original.getDimensionLevelName());
		// Clone value map.
		Map<String, Object> newValueMap = (Map)((LinkedHashMap)original.getDimensionValuesMap()).clone();
		cloned.setDimensionValuesMap(newValueMap);
		
		// Set time dimensions to zero.
		RtaSchema schema = ModelRegistry.INSTANCE.getRegistryEntry(original.getSchemaName());
		Cube cube = schema.getCube(original.getCubeName());
		DimensionHierarchy dh = cube.getDimensionHierarchy(original.getDimensionHierarchyName());
		for (Dimension dim : dh.getDimensions()) {
			if (dim instanceof TimeDimension) {
				// Set Time dimension to zero.
				cloned.addDimensionValueToKey(dim.getName(), 0);
			}
		}
		return cloned;		
	}

	public String getActionName() {
		return actionName;
	}

	public String getRuleName() {
		return ruleName;
	}

	public MetricKey getMetricKey() {
		return metricKey;
	}
	
	public boolean equals(Object object) {
		if (!(object instanceof RuleMetricNodeStateKey)) {
			return false;
		}
		RuleMetricNodeStateKey key2 = (RuleMetricNodeStateKey) object;

		return key2.ruleName.equals(ruleName)
			&& key2.metricKey.equals(metricKey)
			&& key2.actionName.equals(actionName);
	}

	public int hashCode() {
		return ruleName.hashCode() + metricKey.hashCode() + actionName.hashCode();
	}

	public String toString() {
		return metricKey.toString() + ", " + ruleName + ", " + actionName;
	}

	@Override
	public int compareTo(Object other) {
		return toString().compareTo(other.toString());
	}

}
