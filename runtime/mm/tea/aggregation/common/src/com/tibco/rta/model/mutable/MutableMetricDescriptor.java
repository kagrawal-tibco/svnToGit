package com.tibco.rta.model.mutable;

import com.tibco.rta.model.Attribute;
import com.tibco.rta.model.DuplicateSchemaElementException;
import com.tibco.rta.model.Measurement;
import com.tibco.rta.model.MetricFunctionDescriptor;
import com.tibco.rta.model.MetricFunctionsRepository;

/**
 * Allows defining an implementation for the actual metric and may
 * be provided by clients.
 * <p>
 *     Clients need to implement {@link com.tibco.rta.runtime.metric.MetricFunction} interface
 *     and provide their own implementation for metric computation.
 *     For instance : 95th percentile.
 * </p>
 * @see com.tibco.rta.runtime.metric.AbstractMetricFunction
 * @see MetricFunctionsRepository
 */
public interface MutableMetricDescriptor extends MutableMetadataElement, MetricFunctionDescriptor {

	/**
	 * Add a function binding to the measurement.
	 * 
	 * @param functionParam
	 * @param measurement
	 * @param attribute
	 * @throws DuplicateSchemaElementException
	 */
	void addBinding(FunctionParam functionParam, Measurement measurement, Attribute attribute) throws DuplicateSchemaElementException ;

	/**
	 * Set a function descriptor
	 * @param functionDescriptor associated function descriptor.
	 * 
	 */
	void setMetricFunctionDescriptor (MetricFunctionDescriptor functionDescriptor);

}
