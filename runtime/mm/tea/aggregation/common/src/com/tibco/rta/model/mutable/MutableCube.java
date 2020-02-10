package com.tibco.rta.model.mutable;

import com.tibco.rta.model.Cube;
import com.tibco.rta.model.DuplicateSchemaElementException;


/**
 * This represents a collection of {@link MutableDimensionHierarchy}s.
 * Loose equivalent of olap cube.
 */
public interface MutableCube extends MutableMetadataElement, Cube {

    /**
     * Create a new dimension hierarchy.
     *
     * @param name the name
     * @return the newly created dimension hierarchy
     * @throws DuplicateSchemaElementException If the hierarchy already exists.
     */
    MutableDimensionHierarchy newDimensionHierarchy(String name) throws DuplicateSchemaElementException;

    /**
     * Remove a {@link MutableDimensionHierarchy} with this name
     * @param name The hierarchy name to remove
     * @return The removed {@link MutableDimensionHierarchy}
     */
    MutableDimensionHierarchy removeDimensionHierarchy(String name);
    
}