package com.tibco.rta.runtime.model.impl;

import com.tibco.rta.Fact;
import com.tibco.rta.Metric;
import com.tibco.rta.MetricKey;
import com.tibco.rta.MetricValueDescriptor;
import com.tibco.rta.impl.BaseMetricImpl;
import com.tibco.rta.impl.MetricKeyImpl;
import com.tibco.rta.impl.MultiValueMetricImpl;
import com.tibco.rta.impl.SingleValueMetricImpl;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.model.DimensionHierarchy;
import com.tibco.rta.model.Measurement;
import com.tibco.rta.model.MetricFunctionDescriptor;
import com.tibco.rta.model.impl.MetricValueDescriptorImpl;
import com.tibco.rta.runtime.model.MutableMetricNode;

public class NodeFactory {

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_COMMON.getCategory());
	
	public static MutableMetricNode newMetricNode (MetricKey key, Fact fact, DimensionHierarchy hierarchy, int level, boolean isStrict) {
		MetricNodeImpl metricNode = new MetricNodeImpl(key);

		if (level > -1) {
			MetricKey parentKey = KeyFactory.createParentMetricNodeKey(key, hierarchy);
			metricNode.setParentKey(parentKey);
		}
		long createdTime = System.currentTimeMillis();
		metricNode.setCreatedTime(createdTime);
		metricNode.setLastModifiedTime(createdTime);
		return metricNode;
	}
	
	public static Metric getOrCreateMetric (MutableMetricNode metricNode, Fact fact, DimensionHierarchy hierarchy, int level, Measurement measurement) {
		
		Metric metric = metricNode.getMetric(measurement.getName());
		if (metric == null) {
			MetricFunctionDescriptor md = measurement.getMetricFunctionDescriptor();

			if (md.isMultiValued()) {
				metric = new MultiValueMetricImpl();
			} else {
				metric = new SingleValueMetricImpl();
			}
			
			//clone the key, and set it on the metric
			MetricKey nodeKey = (MetricKey) metricNode.getKey();
			MetricKeyImpl key = new MetricKeyImpl(nodeKey);
			metric.setKey(key);
			
			String dimName;
			if (level == -1) {
				dimName = "root";
			} else {
				dimName= hierarchy.getDimension(level).getName();
			}
			//set its descriptor
			MetricValueDescriptor descriptor = new MetricValueDescriptorImpl(
					dimName, hierarchy.getName(), hierarchy.getOwnerCube()
					.getName(), hierarchy.getOwnerSchema()
					.getName(), measurement.getName());
			metric.setDescriptor(descriptor);
            //Also set created timestamp
            ((BaseMetricImpl)metric).setCreatedTime(metricNode.getCreatedTime());

			metricNode.setMetric(measurement.getName(),	metric);
			metricNode.setContext(measurement.getName(), new RtaNodeContextImpl());
		}
		return metric;
	}
}
