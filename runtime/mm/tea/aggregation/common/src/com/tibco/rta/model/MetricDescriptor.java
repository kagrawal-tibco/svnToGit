//package com.tibco.rta.model;
//
//import java.util.List;
//
///**
// * Allows defining an implementation for the actual metric and may
// * be provided by clients.
// * <p>
// *     Clients need to implement {@link com.tibco.rta.runtime.metric.MetricFunction} interface
// *     and provide their own implementation for metric computation.
// *     For instance : 95th percentile.
// * </p>
// * @see com.tibco.rta.runtime.metric.AbstractMetricFunction
// * @see MetricFunctionsRepository
// */
//public interface MetricDescriptor extends MetadataElement {
//	
//
//	MetricFunctionDescriptor getMetricFunctionDescriptor();
//	
//
//
//    /**
//     * Get the appropriate attribute when a parameter is passed.
//     * @see #bindFunctionParamToFactAttribute(String, String)
//     * @param paramName
//     * @return
//     */
//	String getFunctionParamBinding(String paramName, Measurement measurement);
//
//
//
//	List<String> getMeasurementNames();
//	
//
//}
