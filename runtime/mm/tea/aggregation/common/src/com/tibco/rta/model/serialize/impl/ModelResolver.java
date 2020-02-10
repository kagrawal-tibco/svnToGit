package com.tibco.rta.model.serialize.impl;

import com.tibco.rta.model.MetricFunctionDescriptor;
import com.tibco.rta.model.MetricFunctionsRepository;

public class ModelResolver {
	
	public MetricFunctionDescriptor resolveMetricFunctionDescriptor(String functionName) {
		return MetricFunctionsRepository.INSTANCE.getFunctionDescriptor(functionName);
	}
}
