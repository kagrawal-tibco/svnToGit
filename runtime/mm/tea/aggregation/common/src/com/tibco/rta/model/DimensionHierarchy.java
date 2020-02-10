package com.tibco.rta.model;

import java.util.Collection;

/**
 * This defines the hierarchy of {@code Dimension}s on which aggregations should be performed
 * 
 */
public interface DimensionHierarchy extends MetadataElement {


    /**
     * Gets the owning cube for this hierarchy
     * @return a cube instance
     */
    <T extends Cube> T getOwnerCube();
    
    
    /**
     * Return the dimension at the specified level in the hierarchy
     * @param level
     * @return the dimension at this level
     */
	Dimension getDimension(int level);

	/**
	 * Return the ordered list of dimensions in this hierarchy.
	 * @return the ordered list of dimensions in this hierarchy.
	 */
	
	<T extends Dimension> Collection<T> getDimensions();


    /**
     * Return level for a dimension.
     * @param dimensionNm The dimension for which to obtain the level in the hierarchy. The topmost level is 0.
     * @return the Level associated with the dimension
     */
 	int getLevel(String dimensionNm);
 	
    /**
     * Return the entire length of hierarchy.
     * @return the total number of dimensions in hierarchy.
     */
    int getDepth();
    
    /**
     * Return  the associated measurement for this hierarchy.
     * @param name name of the measurement.
     */
    <T extends Measurement> T getMeasurement(String name);
    
    /**
     * Return  the associated measurements for this hierarchy. 
     */
    <T extends Measurement> Collection<T> getMeasurements();
    
    /**
     * Returns a collection of excluded measurements for a given level.
     * @param level Level for which to obtain the excluded measurements.
     * @return a collection of excluded measurements for a given level.
     */
    <T extends Measurement> Collection<T> getExcludedMeasurements(int level);
    
    /**
     * Returns a collection of excluded measurements for a given dimension.
     * @param dimension Dimension for which to obtain the excluded measurements.
     * @return a collection of excluded measurements for a given dimension.
     */
    
    <T extends Measurement> Collection<T> getExcludedMeasurements(Dimension dimension);

    /**
     * Returns the dimension associated with the name.
     * @param dimName the dimension name for which to obtain the dimension
     * @return the associated dimension.
     */
	Dimension getDimension(String dimName);
	
	/**
	 * Returns true if the root computation of all nodes is required.
	 * @return true if the root computation of all nodes is required, false otherwise.
	 */
	boolean computeRoot();

	/**
	 * Return a collection of measurements for a given level.
	 * @param level Level for which to obtain measurements.
	 * @return a collection of measurements for a given level.
	 */
	Collection<Measurement> getMeasurementsForLevel(int level);

	/**
	 * Returns true if computation is desired at this level.
	 * @param level the level for which to obtain its compute setting.
	 * @return true if to compute measurements at this level, false otherwise.
	 */
	boolean getComputeForLevel(int level);

	/**
	 * Return the associated retention policy for this dimension.
	 * @return the associated retention policy for this dimension.
	 */
	RetentionPolicy getRetentionPolicy();
	
	
	/**
	 * Is computation for this hierarchy is enabled/ disabled ? If false, its disabled
	 * 
	 * @return false if its disabled.
	 */
	boolean isEnabled();


    /**
     * Returns true if measurement is excluded at this dimension level
     * @param level Level for which to obtain the excluded measurements.
     * @return name of measurement
     */
	boolean isExcluded(int level, String measurementName);

	
	/**
	 * Returns an ordered list of attribute names corresponding to its ordered dimensions.
	 * @return a list of ordered attribute names
	 */
	Collection<String> getDimensionAttribNames();
}