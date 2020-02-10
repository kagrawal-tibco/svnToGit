package com.tibco.rta.model;

import com.tibco.rta.Fact;

/**
 * This defines a property of a {@code Measurement}
 * <p>
 * {@code Dimension}s are defined using these attributes. Note that there could exist
 * more than one {@code Dimension} for an attribute or none for some other.
 * <p>
 * A {@code Fact} is an instance of a {@code Measurement} and hence it will provide the 
 * values of the {@code Attribute}s
 * @see Fact
 * @see Measurement
 */
public interface Attribute extends MetadataElement {

    /**
     * Attribute values will be constrained by the data type.
     *
     * @return the {@link DataType} associated with this attribute
     */
    DataType getDataType();
}
