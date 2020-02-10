/**
 * 
 */
package com.tibco.be.bemm.functions;

import java.util.HashMap;
import java.util.Map;

import com.tibco.be.functions.java.util.MapHelper;

/**
 * @author anpatil
 *
 */
public class MetricTypeHandlerFactory {

	private static MetricTypeHandlerFactory instance;
	
	static synchronized MetricTypeHandlerFactory getInstance(){
		if (instance == null) {
			instance = new MetricTypeHandlerFactory();
		}
		return instance;
	}
	
	private Map<String, Class<? extends MetricTypeHandler>> cache;
	
	private MetricTypeHandlerFactory(){
		cache = new HashMap<String, Class<? extends MetricTypeHandler>>();
		//Machine level metrics
		cache.put("cluster/machine/cpustats", HAWKCPUMetricHandler.class);
		cache.put("cluster/machine/memory", HAWKMemoryMetricHandler.class);
		cache.put("cluster/machine/swap", HAWKSwapMetricHandler.class);
		
		//Java Process JMX Metrics 
		cache.put("cluster/machine/process/cpustats", JMXCPUMetricHandler.class);
		cache.put("cluster/machine/process/cpustats_ibm", JMXCPUMetricHandlerIBM.class);
		cache.put("cluster/machine/process/rthreads", JMXThreadMetricHandler.class);
		cache.put("cluster/machine/process/dthreads", JMXThreadMetricHandler.class);
		cache.put("cluster/machine/process/memory", JMXMemoryMetricHandler.class);
		cache.put("cluster/machine/process/gc", JMXGarbageCollectorMetricHandler.class);
		//BE JMX Metrics
		cache.put("cluster/machine/process/query/lcachestats", JMXQueryAgentMetricHandler.class);
		cache.put("cluster/machine/process/query/inentitystat", JMXQueryAgentMetricHandler.class);
		cache.put("cluster/machine/process/query/entitystats", JMXQueryAgentMetricHandler.class);
		cache.put("cluster/machine/process/query/ssqestats", JMXQueryExecutionMetricHandler.class);
		cache.put("cluster/machine/process/query/cqestats", JMXQueryExecutionMetricHandler.class);
		//Cache Object Metric 
		cache.put("cluster/mcacheobjects/mcacheobjectstats", CachedObjectsMetricHandler.class);
	}
	
	MetricTypeHandler getHandler(String type){
		if(type != null){
			if(type.equals("cluster/machine/process/cpustats")){
				String vmVendor = System.getProperty("java.vm.vendor");
				if(vmVendor != null && vmVendor.toLowerCase().startsWith("ibm")){
					type = type+"_ibm";
				}
			}
		}
		Class<? extends MetricTypeHandler> clazz = cache.get(type);
		if (clazz == null){
			String configMapID = DashboardHelper.getConfigMapID(type);
			String key = MapHelper.get(configMapID, "key");
			clazz = cache.get(key);
			if (clazz == null) {
				throw new RuntimeException("No handler found for "+type);
			}
		}
		try {
			MetricTypeHandler handler = clazz.newInstance();
			handler.type = type;
			return handler;
		} catch (InstantiationException e) {
			throw new RuntimeException("could not create an instance of "+clazz.getName()+" for "+type);
		} catch (IllegalAccessException e) {
			throw new RuntimeException("could not access "+clazz.getName()+" for "+type);
		}
	}
	
}
