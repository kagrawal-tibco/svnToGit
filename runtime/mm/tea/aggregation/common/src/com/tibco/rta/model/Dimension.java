package com.tibco.rta.model;

/**
 * An attribute of a measurement on which aggregation is performed.
 * 
 * <p>
 *     Every dimension will map to an {@link Attribute} of a measurement.
 * </p>
 */
public interface Dimension extends MetadataElement {

    /**
     * Dimension will be associated with an attribute of the schema.
     *
     * @return the attribute on which this dimension is based.
     */
    Attribute getAssociatedAttribute();
    
    /**
     * Dimensions in the hierarchy might represent assets in the system. If so, get this dimension's associated asset name in the model.
     * 
     * @return the associated asset name
     */
    String getAssociatedAssetName();

}