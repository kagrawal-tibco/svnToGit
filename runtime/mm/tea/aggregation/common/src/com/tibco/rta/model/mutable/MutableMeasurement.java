package com.tibco.rta.model.mutable;

import com.tibco.rta.model.DataType;
import com.tibco.rta.model.Measurement;
import com.tibco.rta.model.MetricFunctionDescriptor;


/**
 * Measurement is a representation of a quantity being measured.
 * <p>
 * e.g :
 * <li>
 * Sales of car in a year
 * </li>
 * <li>
 * Growth of Population of country in last x years.
 * </li>
 * </p>
 */
public interface MutableMeasurement extends MutableMetadataElement, Measurement {
	
	/**
	 * Associate a function descriptor with this measurement.
	 * 
	 * @param functionDescriptor
	 */
    void setMetricFunctionDescriptor(MetricFunctionDescriptor functionDescriptor);
    
    /**
     * Add a function binding to this measurement.
     * @param paramName the parameter to map to in the function descriptor.
     * @param attributeName the attribute from the fact to use for this computation.
     */
    void addFunctionParamBinding(String paramName, String attributeName);

    /**
     * set the unit for this measurement.
     * @param unit the unit for this measurement.
     */    
    void setUnitOfMeasurement(String unitOfMeasurement);

    /**
     * Add dependency of measurement
     * @param dependency
     */
    void addDependency(Measurement dependency);

    /**
     * Set DataType for the measurement
     * @param dataType
     */
    void setDataType(DataType dataType);
        
}

