package com.tibco.rta.model;

import java.util.Collection;

import com.tibco.rta.Fact;

/**
 * This is a logical collection of all related metrics artifacts like cubes and dimensions.
 * 
 * A {@code RtaSchema} can have multiple {@link Cube}s.
 * Each {@code Cube} defines a {@link Measurement} and one or more {@link DimensionHierarchy}s
 * <p>
 * Each {@code DimensionHierarchy} has an ordered list of {@link Dimension}s.
 * <p>
 * A {@code Dimension} is the attribute of the measurement over which a metric is aggregated. 
 * 
 *
 */
public interface RtaSchema extends MetadataElement {

    /**
     * Gets the attributes associated with this measurement
     *
     * @return the attributes
     */
    Collection<Attribute> getAttributes();
    
    /**
     * Gets the attribute in this measurement with this name.
     * @param name The name to search
     * @return the attribute
     */
    Attribute getAttribute(String name);

    /**
     * Gets the dimensions associated with this measurement
     *
     * @return the dimensions
     */
    Collection<Dimension> getDimensions();

    /**
     * Gets the dimension in this measurement with this name.
     * @param name The name to search
     * @return the dimension
     */
    Dimension getDimension(String name);

    /**
     * TODO documentation
     * @param <T>
     * @return
     */
    <T extends Measurement> Collection<T> getMeasurements();

    /**
     * TODO documentation
     * @param name
     * @param <T>
     * @return
     */
    <T extends Measurement> T getMeasurement(String name);
    
    /**
     * Gets all the cubes in the schema.
     *
     * @return the cubes
     */
    <T extends Cube> Collection<T> getCubes();
    
    /**
     * Gets a cube in this schema with this name or null if no such cube exists.
     *
     * @param name The name to search for.
     * @return the cube
     */
    <T extends Cube> T getCube(String name);

    /**
     * TODO Add documentation.
     * @return
     */
    long getVersion();
    
    /**
     * Create a new fact and associate it with this measurement.
     * @return the newly created fact.
     */
    Fact createFact();

    /**
     * Create a new fact and associate it with this measurement.
     * @param uid : A unique identifier for fact key.
     *              If null or empty value is passed, a uid is generated.
     * @return the newly created fact.
     */
    Fact createFact(String uid);

    /**
     * TODO add documentation.
     * @return
     */
    Collection<RetentionPolicy> getRetentionPolicies();

    /**
     * get display name of schema
     * @return display name of schema
     */
    String getDisplayName();

    /**
     * Convert this schema object to XML.
     * @return the xml string.
     */
    String toXML() throws Exception;

    /**
     * whether this schema is system schema or not
     * @return true if its system schema 
     */
    boolean isSystemSchema();
}