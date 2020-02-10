package com.tibco.cep.bemm.monitoring.metric.collector;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import com.tibco.cep.bemm.common.ConnectionContext;
import com.tibco.cep.bemm.common.service.logging.LogManagerFactory;
import com.tibco.cep.bemm.common.service.logging.Logger;

public abstract class MetricTypeHandler<C extends ConnectionContext<?>> {

	protected static Logger LOGGER = LogManagerFactory.getLogManager().getLogger(MetricTypeHandler.class);
	
	protected String metricType;
	
	public MetricTypeHandler(String metricType) {
		this.metricType = metricType;
	}
		
	public String getType() {
		return metricType;
	}	

	public abstract MetricsContext initMetricsContext(C connContext) throws IOException;	

	public abstract Collection<Map<String, Object>> getMetrics(C connContext, MetricsContext metricsContext) throws IOException;
		
	protected double limit(double d,int decimalPlaces){
		double i = Math.pow(10, decimalPlaces);
		return Math.round(d*i)/i;
	}

}