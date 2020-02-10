package com.tibco.cep.runtime.model.element;

/*
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Sep 27, 2004
 * Time: 9:12:18 PM
 * To change this template use File | Settings | File Templates.
 */

/**
 * Represents a concept contained in another concept.  Runtime instance of this class may have or may not have a parent.
 *
 * @version 2.0.0
 * @.category public-api
 * @since 2.0.0
 */
//TODO document
public interface ContainedConcept extends Concept {


    /**
     * Gets the parent of this contained concept.
     * A <code>ContainedConcept</code> can only have one parent.
     *
     * @return the parent of this contained concept.
     * @.category public-api
     * @since 2.0.0
     */
    Concept getParent();


    /**
     * Sets the parent of the contained concept.
     * A <code>ContainedConcept</code> can only have one parent.
     *
     * @param instance the parent.
     * @.category public-api
     * @since 2.0.0
     */
    void setParent(Concept instance);
}
