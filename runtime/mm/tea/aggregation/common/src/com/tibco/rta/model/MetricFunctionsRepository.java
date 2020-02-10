package com.tibco.rta.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.tibco.rta.model.serialize.impl.FunctionsCatalogDeserializer;

/**
 * Represents a repository of Metric Functions. Functions in this repository are referenced by RtaSchema
 *  
 */

public class MetricFunctionsRepository {

	public static MetricFunctionsRepository INSTANCE = new MetricFunctionsRepositoryImpl();

	protected Map<String, MetricFunctionDescriptor> functions = new LinkedHashMap<String, MetricFunctionDescriptor>();

    @SuppressWarnings("unchecked")
	private MetricFunctionsRepository() {
		// load from .function.catalog
		FunctionsCatalogDeserializer fCatalogDeserializer = new FunctionsCatalogDeserializer();
		try {
			functions = fCatalogDeserializer.deserialize();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns the function descriptor given a function name
	 * @param name Name for which the descriptor is desired.
	 * @return the corresponding descriptor
	 */
	public MetricFunctionDescriptor getFunctionDescriptor(String name) {
		return functions.get(name);
	}

	/**
	 * Returns a list of names of function descriptors.
	 * @return A list of names associated function descriptors.
	 */
	public List<String> getMetricFunctionDescriptorNames() {
		return new ArrayList<String>(functions.keySet());
	}

	/**
	 * Returns a list of metric function  descriptors.
	 * @return A list of metric function descriptors.
	 */	
	public List<MetricFunctionDescriptor> getMetricFunctionDescriptors() {
		return new ArrayList<MetricFunctionDescriptor>(functions.values());
	}

	
	public static class MetricFunctionsRepositoryImpl extends
			MetricFunctionsRepository {
		private MetricFunctionsRepositoryImpl(){}
		
		public void setMetricFunctionDescritpors(
				Map<String, MetricFunctionDescriptor> fMap) {
			functions = fMap;
		}
	}
}

