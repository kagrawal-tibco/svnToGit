package com.tibco.rta.model.mutable;

import com.tibco.rta.model.Attribute;
import com.tibco.rta.model.DataType;
import com.tibco.rta.model.Dimension;
import com.tibco.rta.model.DuplicateSchemaElementException;
import com.tibco.rta.model.RetentionPolicy;
import com.tibco.rta.model.RtaSchema;
import com.tibco.rta.model.TimeDimension;
import com.tibco.rta.model.TimeUnits;
import com.tibco.rta.model.TimeUnits.Unit;
import com.tibco.rta.model.UndefinedSchemaElementException;


/**
 * This is a logical collection of all related metrics artifacts like cubes and dimensions.
 * 
 * A {@code RtaSchema} can have multiple {@link MutableCube}s.
 * Each {@code Cube} defines a {@link MutableMeasurement} and one or more {@link MutableDimensionHierarchy}s
 * <p>
 * Each {@code DimensionHierarchy} has an ordered list of {@link Dimension}s.
 * <p>
 * A {@code Dimension} is the attribute of the measurement over which a metric is aggregated. 
 * 
 *
 */
public interface MutableRtaSchema extends MutableMetadataElement, RtaSchema {
    
	/**
     * Create a new attribute on this measurement.
     * Attribute is a property of the measurement and will be used
     * by facts to specify values to be used for computing metric.
     *
     * @param name     Unique name for the attribute.
     * @param dataType The datatype for legal values of this attribute
     * @return newly created attribute
     * @throws DuplicateSchemaElementException
     *          if an attribute with same name already exists.
	 * @throws UndefinedSchemaElementException 
     */
    Attribute newAttribute(String name, DataType dataType) throws DuplicateSchemaElementException, UndefinedSchemaElementException;
    
    Attribute removeAttribute(String name);

    /**
     * Creates a new dimension and binds it with an attribute of the measurement.
     *
     * @param name      the name of the dimension. Optional parameter
     * @param attribute the attribute to bind it with
     * @return the dimension created
     * @throws DuplicateSchemaElementException
     *          If a dimension with same name already exists in this measurement.
     * @throws UndefinedSchemaElementException 
     */
    Dimension newDimension(String name, Attribute attribute) throws DuplicateSchemaElementException, UndefinedSchemaElementException;


    /**
     * Creates a new dimension and binds it with an attribute of the measurement.
     * The name of the dimension will be the same as that of its attribute.
     *
     * @param attribute the attribute to bind it with
     * @return the dimension created
     * @throws DuplicateSchemaElementException
     *          If a dimension with same name already exists in this measurement.
     * @throws UndefinedSchemaElementException 
     */
    Dimension newDimension(Attribute attribute) throws DuplicateSchemaElementException, UndefinedSchemaElementException;

    /**
     * New time dimension.
     *
     * @param name the name
     * @return the time dimension
     * @throws UndefinedSchemaElementException 
     */
    TimeDimension newTimeDimension(String name, Attribute associatedAttribute, Unit unit, int frequency) throws DuplicateSchemaElementException, UndefinedSchemaElementException;

    /**
     * Removes the dimension from the measurement.
     *
     * @param dimension the dimension
     */
    Dimension removeDimension(String name);
    
    /**
     * Define the measurement for this schema. Every cube in the schema
     * will have a single measurement. e.g : Sales of car.
     *
     * @param name
     * @return the newly created measurement.
     * @throws UndefinedSchemaElementException 
     */
	MutableMeasurement newMeasurement(String name) throws DuplicateSchemaElementException, UndefinedSchemaElementException;

	
	MutableMeasurement removeMeasurement(String name);
    
    /**
     * Create a new {@link MutableCube} using the specified name.
     *
     * @param name the name
     * @return the cube
     * @throws DuplicateSchemaElementException
     *          If a cube exists with this name in the schema.
     * @throws UndefinedSchemaElementException 
     */
    MutableCube newCube(String name) throws DuplicateSchemaElementException, UndefinedSchemaElementException;

    /**
     * Removes the cube.
     *
     * @param cube the cube
     * @return Return the removed cube
     */
    MutableCube removeCube(String name);
    
    void addRetentionPolicy (String qualifier, TimeUnits.Unit timUnit, int multiplier,
    		String purgeTimeOfDay, long purgeFrequencyPeriod);
    
    void removeRetentionPolicy(String qualifier);

    /**
     * Set display name to schema
     *
     * @param display name of schema
     * @return 
     */    
    void setDisplayName(String name);
    
}
