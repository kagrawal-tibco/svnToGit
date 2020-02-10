package com.tibco.rta;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.tibco.rta.impl.FactKeyImpl;

import java.io.Serializable;

/**
 * 
 * A unique identity of a node.
 * <p>
 * 
 * For a {@code MetricNode} a key is the unique combination of its schema name, cube name, dimension
 * hierarchy name, and all the dimensions names and their values that make up the node.
 * 
 * For a {@code Fact} the key is a unique string. Note that there can exist multiple facts with exactly identical
 * data in all its attributes.
 *
 */
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = FactKeyImpl.class, name = "key")})
public interface Key extends Serializable, Comparable {

}
