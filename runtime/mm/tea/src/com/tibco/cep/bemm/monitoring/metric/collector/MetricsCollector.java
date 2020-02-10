package com.tibco.cep.bemm.monitoring.metric.collector;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import com.tibco.cep.bemm.common.ConnectionContext;
import com.tibco.cep.bemm.model.Monitorable;

public interface MetricsCollector<C extends ConnectionContext<?>, H extends MetricTypeHandler<C>> {

    public static enum COLLECTOR_TYPE {
        JMX, HAWK, CACHE
    }

	/**
	 * @param Configuration
	 * @throws Exception 
	 */
	void init(Properties Configuration) throws Exception;

	/**
	 * @return
	 */
	List<H> getDefaultMetricHandlers();
	
	/**
	 * @param metricType
	 * @param className
	 */
	void addCustomMetricHandler(String metricType, String className);
	
	/**
	 * @param monitorableEntity
	 * @param metricType
	 * @return
	 */
	void register(Monitorable monitorableEntity, String metricType) throws IOException;

	/**
	 * @param monitorableEntity
	 */
	void unregister(Monitorable monitorableEntity);
	
	/**
	 * @param monitorableEntity
	 * @param metricType
	 */
	void unregister(Monitorable monitorableEntity, String metricType);
	
	/**
	 * @param metricCollectorCallback
	 */
	void setCallbackHandler(MetricsCollectorCallback metricCollectorCallback);
	
	/**
	 * @return collector type
	 */
	String getCollectorType();
	                    
}
