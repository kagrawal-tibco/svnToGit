//package com.tibco.rta.model.impl;
//
//import java.util.ArrayList;
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Map;
//
//import com.tibco.rta.model.Attribute;
//import com.tibco.rta.model.DuplicateSchemaElementException;
//import com.tibco.rta.model.FunctionDescriptor.FunctionParam;
//import com.tibco.rta.model.Measurement;
//import com.tibco.rta.model.MetricFunctionDescriptor;
//import com.tibco.rta.model.RtaSchema;
//import com.tibco.rta.model.mutable.MutableMetricDescriptor;
//
//public class MetricDescriptorImpl extends MetadataElementImpl implements MutableMetricDescriptor {
//	
//	private static final long serialVersionUID = -8600619934706766839L;
//
//	protected MetricFunctionDescriptor functionDescriptor;
//	
//	protected Map<String, Map<String,String>> functionParamBinding = new LinkedHashMap<String, Map<String, String>>();
//
//	
//	public MetricDescriptorImpl() {
//	}
//	
//	public MetricDescriptorImpl(String name) {
//		super(name);
//	}
//	
//	public MetricDescriptorImpl(String name, RtaSchema ownerSchema) {
//		super(name, ownerSchema);
//	}
//	
//	@Override
//	public MetricFunctionDescriptor getMetricFunctionDescriptor() {
//		return functionDescriptor;
//	}
//	
//	@Override
//	public String getFunctionParamBinding(String paramName, Measurement measurement) {
//		Map <String,String> pToA = functionParamBinding.get(measurement.getName());
//		return pToA.get(paramName);
//	}
//
//	@Override
//	public void addBinding(FunctionParam functionParam, Measurement measurement, Attribute attribute) throws DuplicateSchemaElementException {
//		Map <String,String> pToA = functionParamBinding.get(measurement.getName());
//		if (pToA == null) {
//			pToA = new LinkedHashMap<String,String>();
//			functionParamBinding.put(measurement.getName(), pToA);
//		} 
//	        //Check if it is already present in the map
//		if (! pToA.containsKey(functionParam.getName())) {
//			pToA.put(functionParam.getName(), attribute.getName());
//		} else {
//			throw new DuplicateSchemaElementException(String.format("Parameter %s already bound to attribute %s", functionParam.getName(), pToA.containsKey(functionParam.getName())));
//		}
//		
//	}
//
//	@Override
//	public void setMetricFunctionDescriptor(
//			MetricFunctionDescriptor functionDescriptor) {
//		this.functionDescriptor = functionDescriptor;
//		
//	}
//
//	@Override
//	public List<String> getMeasurementNames() {
//		return new ArrayList<String>(functionParamBinding.keySet());
//	}
//
////	@Override
////	public boolean equals(Object obj) {
////		MetricDescriptorImpl cObj = (MetricDescriptorImpl) obj;
////		return super.equals(cObj);
////	}
//
//}
