package com.tibco.cep.runtime.model.element;

/*
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: May 18, 2004
 * Time: 6:48:20 PM
 * To change this template use Options | File Templates.
 */

/**
 * Base for properties that contain or refer to one concept.
 *
 * @version 2.0.0
 * @.category public-api
 * @since 2.0.0
 */
public interface PropertyAtomConcept extends PropertyAtom, Property.PropertyConcept {
    public static final long NULL = 0;

    /**
     * Gets the current concept instance of this property.
     *
     * @return the concept instance of this property.
     */
    Concept getConcept();

//    /**
//     * Gets the concept instance of this property at a given time.
//     * @param time at this instance of time
//     * @return the concept instance of this property
//     * @exception com.tibco.cep.runtime.model.element.PropertyException if the value is unknown
//     */
//    Concept getConcept(long time) throws PropertyException;


    /**
     * Gets the current concept instance id of this property.
     * Id 0 is a special value meaning that the current value in the property is null.
     *
     * @return the concept instance id of this property.
     * @.category public-api
     * @since 2.0.0
     */
    long getConceptId();


    /**
     * Gets the concept instance id of this property at the given time.
     * Id 0 is a special value meaning the current value in the property is null.
     *
     * @param time a time expressed in number of milliseconds since the epoch.
     * @return the concept instance id of this property.
     * @throws PropertyException if the value at that time is unknown.
     * @.category public-api
     * @since 2.0.0
     */
    long getConceptId(long time) throws PropertyException;

//    /**
//     * Get the concept instance of this property at a given index.
//     * @param idx int Index of the value needed
//     * @return the concept instance of this property
//     * @exception PropertyException if the value is unknown
//     */
//    Concept getConceptAtIdx(int idx) throws PropertyException;


    /**
     * Gets the concept instance id of this property at the given history index.
     * Id 0 is a special value meaning the current value in the property is null.
     *
     * @param idx index in the history of this property.
     * @return the concept instance id of this property.
     * @throws PropertyException if the index provided is currently invalid.
     * @.category public-api
     * @since 2.0.0
     */
    long getConceptIdAtIdx(int idx) throws PropertyException;
}
