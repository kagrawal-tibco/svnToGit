package com.tibco.cep.runtime.model.element;

/*
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Aug 23, 2004
 * Time: 6:23:02 PM
 * To change this template use Options | File Templates.
 */

/**
 * Property that contains one reference to a <code>Concept</code>.
 *
 * @version 2.0.0
 * @.category public-api
 * @since 2.0.0
 */
public interface PropertyAtomConceptReference extends PropertyAtomConcept, Property.PropertyConceptReference {


    final static Concept DEFAULT_VALUE = null;


    /**
     * Sets the current value of this <code>PropertyAtomConceptReference</code> to a reference to the given instance.
     *
     * @param instance the <code>Concept</code> instance to refer to.
     * @return true if a new entry was added in the history for this value.
     * @.category public-api
     * @see Property#getHistoryPolicy()
     * @since 2.0.0
     */
    boolean setConcept(Concept instance);


    /**
     * Sets the current value of this <code>PropertyAtomConceptReference</code> to a reference to the given instance,
     * at the given time.
     *
     * @param instance the <code>Concept</code> instance to refer to.
     * @param time  the time to associate with the value.
     * @return true if a new entry was added in the history for this value.
     * @.category public-api
     * @see Property#getHistoryPolicy()
     * @since 2.0.0
     */
    boolean setConcept(Concept instance, long time);
}