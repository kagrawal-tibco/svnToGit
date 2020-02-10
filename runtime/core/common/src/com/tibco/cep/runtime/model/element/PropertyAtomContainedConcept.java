package com.tibco.cep.runtime.model.element;

/*
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Sep 27, 2004
 * Time: 11:49:39 PM
 * To change this template use File | Settings | File Templates.
 */

/**
 * Property that contains one <code>Concept</code>.
 *
 * @version 2.0.0
 * @.category public-api
 * @since 2.0.0
 */
public interface PropertyAtomContainedConcept extends PropertyAtomConcept, Property.PropertyContainedConcept {


    final static ContainedConcept DEFAULT_VALUE = null;


    /**
     * Gets the current <code>ContainedConcept</code> of this property.
     *
     * @return the <code>ContainedConcept</code> instance of this property.
     * @.category public-api
     * @since 2.0.0
     */
    ContainedConcept getContainedConcept();


    /**
     * Gets the instance id of the current <code>ContainedConcept</code> of this property.
     * Id 0 is a special value meaning the current value in the property is null.
     *
     * @return the concept instance id of this property.
     * @.category public-api
     * @since 2.0.0
     */
    long getContainedConceptId();


    /**
     * Gets the instance id of the <code>ContainedConcept</code> of this property at the given time.
     * Id 0 is a special value meaning the current value in the property is null.
     *
     * @param time the time to associate with the value.
     * @return the containedConcept instance id of this property
     * @throws PropertyException if the value at that time is unknown.
     * @.category public-api
     */
    long getContainedConceptId(long time) throws PropertyException;


    /**
     * Gets the instance id of the <code>ContainedConcept</code> at the given index
     * in the current history of this property .
     * Id 0 is a special value meaning the current value in the property is null.
     *
     * @param idx index in the history of this property.
     * @return the <code>ContainedConcept</code> instance id of this property
     * @throws PropertyException if the value is unknown
     * @.category public-api
     */
    long getContainedConceptIdAtIdx(int idx) throws PropertyException;


    /**
     * Sets the current value of this <code>PropertyAtomContainedConcept</code> to the given instance.
     *
     * @param instance the <code>ContainedConcept</code> instance to set.
     * @return true if a new entry was added in the history for this value.
     * @.category public-api
     * @see Property#getHistoryPolicy()
     * @since 2.0.0
     */
    boolean setContainedConcept(ContainedConcept instance);


    /**
     * Sets the current value of this <code>PropertyAtomContainedConcept</code> to the given instance
     * with the given time.
     *
     * @param instance the <code>ContainedConcept</code> instance to set.
     * @return true if a new entry was added in the history for this value.
     * @.category public-api
     * @see Property#getHistoryPolicy()
     * @since 2.0.0
     */
    boolean setContainedConcept(ContainedConcept instance, long time);
}
