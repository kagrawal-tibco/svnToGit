package com.tibco.rta.model;


import java.util.Collection;

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
public interface Measurement extends MetadataElement {


    /**
     * Gets the metric descriptor.
     *
     * @return the metric descriptor or null if none exists.
     */
    <T extends MetricFunctionDescriptor> T getMetricFunctionDescriptor();

    /**
     * Get the attribute associated with this parameter.
     *
     * @param paramName  function parameter to bind to.
     * @return
     */
    String getFunctionParamBinding(String paramName);

    /**
     * Get unit of measurement.
     *
     * @return Unit of measurement
     */
    String getUnitOfMeasurement();

    /**
     * Return a collection of measurements which need to be computed
     * before this measurement can be computed.
     * <p>
     *     Measurements can be chained thus.
     *     If measurement A depends on measurement B and C
     *     then B and C will be evaluated before A.
     * </p>
     * @return
     */
    Collection<Measurement> getDependencies();
    
    /**
     * Get DataType of measurement
     *
     * @return DataType of measurement
     */
    DataType getDataType();
}

