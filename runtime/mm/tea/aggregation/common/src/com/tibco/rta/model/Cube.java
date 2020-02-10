package com.tibco.rta.model;

import java.util.Collection;
import java.util.List;


/**
 * This represents a collection of {@link DimensionHierarchy}s.
 * Loose equivalent of olap cube.
 */
public interface Cube extends MetadataElement {

    /**
     * Gets a dimension hierarchy with this name in the {@link Cube}
     * @param name
     * @return the dimension hierarchy or null if no such hierarchy exists.
     */
    <T extends DimensionHierarchy> T getDimensionHierarchy(String name);


    /**
     * Return all the hierarchies in this cube as a {@link List}
     * @return
     */
    <T extends DimensionHierarchy> Collection<T> getDimensionHierarchies();

}