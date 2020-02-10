package com.tibco.cep.bemm.monitoring.metric.collector.jmx.handler;

import com.tibco.cep.bemm.common.jmx.JMXConnectionContext;
import com.tibco.cep.bemm.monitoring.metric.collector.MetricTypeHandler;

public abstract class MetricTypeJMXHandler extends MetricTypeHandler<JMXConnectionContext> {
	
    static {
        com.tibco.cep.Bootstrap.ensureBootstrapped();
    }
    	
	MetricTypeJMXHandler(String metricType) {
		super(metricType);
	}
}