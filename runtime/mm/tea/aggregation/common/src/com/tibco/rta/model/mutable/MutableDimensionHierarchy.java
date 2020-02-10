package com.tibco.rta.model.mutable;

import com.tibco.rta.model.Dimension;
import com.tibco.rta.model.DimensionHierarchy;
import com.tibco.rta.model.DuplicateSchemaElementException;
import com.tibco.rta.model.IllegalSchemaException;
import com.tibco.rta.model.Measurement;
import com.tibco.rta.model.RetentionPolicy;
import com.tibco.rta.model.UndefinedSchemaElementException;

/**
 * This defines the hierarchy of {@code Dimension}s on which aggregations should be performed
 * 
 */
public interface MutableDimensionHierarchy extends MutableMetadataElement, DimensionHierarchy {


    /**
     * Inserts new dimension after the reference dimension.
     *
     *
     * @param refDimension the dimension to add new dimension after.
     *                     It can take a null value.
     *                     If null and no dimensions exist, it is same as {@link #setRootDimension(Dimension)}
     *                     else throw IllegalArgumentException
     * @param newDimension the new dimension to add.
     * @throws IllegalSchemaException If the owner schema of this hierarchy and the child dimension are different
     * @throws IllegalArgumentException If the new child and the reference dimension are equal.
     * @throws IllegalArgumentException If refDimension is null and hierarchy is not empty
     */
    void addDimensionAfter(Dimension refDimension, Dimension newDimension) throws IllegalSchemaException;

	/**
	 * Removes the dimension.
	 *
	 * @param dimElement the dim element
	 */
    Dimension removeDimension(String name) throws IllegalSchemaException;
	

    /**
     * Set root dimension for the hierarchy.
     * If a root exists, it will be overwritten with the new one.
     * @param dimension the root dimension.
     */
	void setRootDimension(Dimension dimension);
	
	/**
	 * 
	 * @param measurement
	 * @throws UndefinedSchemaElementException
	 * @throws DuplicateSchemaElementException
	 */
    void addMeasurement(MutableMeasurement measurement) throws UndefinedSchemaElementException, DuplicateSchemaElementException;
    
    /**
     * Remove a measurement from this hierarchy.
     * @param measurement the measurement to remove.
     */
    void removeMeasurement(Measurement measurement);
    
    /**
     * Add a measurement to exclude from being computed for this dimension.
     * @param dimension the dimension for which to exclude computation of a measurement.
     * @param measurement the associated measurement to exclude.
     */
    void addExcludeMeasurement(Dimension dimension, Measurement measurement);
    
    /**
     * Remove a previously set exclude measurement.
     * @param dimension the dimension for which to remove the exclude computation of a measurement.
     * @param measurement the associated measurement to exclude.
     */
    void removeExcludeMeasurement(Dimension dimension, Measurement measurement);
    
    /**
     * Sets the computation status for a given dimension. If false, will not compute the metric for this dimension.
     * @param dimension the dimension for which to not compute measurements.
     * @param toCompute set to false if you do not wich to compute measurements for this dimension.
     */
    void setComputeForLevel(Dimension dimension, boolean toCompute);
    
    /**
     * Set a retention policy for this hierarchy.
     * 
     * @param retentionPolicy the retention policy to set for this hierarchy.
     */
    void setRetentionPolicy(RetentionPolicy retentionPolicy);
        
    /**
     * enable computation for this hierarchy 
     * @param isEnabled is false if computation for this hierarchy is disabled
     */
    void setEnabled(boolean isEnabled);
    
    
}