package com.tibco.cep.runtime.model.element;

/*
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Aug 23, 2004
 * Time: 6:32:36 PM
 * To change this template use Options | File Templates.
 */

/**
 * Property that contains zero, one, or multiple <code>PropertyAtomConceptReference</code>.
 *
 * @version 2.0.0
 * @.category public-api
 * @since 2.0.0
 */
public interface PropertyArrayConceptReference extends PropertyArrayConcept, Property.PropertyConceptReference {


    /**
     * Creates a <code>PropertyAtomConceptReference</code> with with a reference to the given instance,
     * and appends it to the end of the array.
     * This method doesn't check for duplicates (i.e. existence of references to the same instance" in the array).
     *
     * @param instance the target <code>Concept</code> of the reference to append to this array.
     * @return the index at which the reference is inserted.
     * @.category public-api
     * @since 2.0.0
     */
    int add(Concept instance);


    /**
     * Creates a <code>PropertyAtomConceptReference</code> and inserts it at the specified position in the array.
     * Shifts the <code>PropertyAtomConceptReference </code> currently at that position (if any)
     * and any subsequent <code>PropertyAtomConceptReference</code> to the right (i.e. adds one to their indices).
     *
     * @param index    at which the reference is to be inserted
     * @param instance the target <code>Concept</code> of the reference to append to this array.
     * @throws IndexOutOfBoundsException if index is less than zero or more than the current size of the array.
     * @.category public-api
     * @since 2.0.0
     */
    void add(int index, Concept instance);


    /**
     * Returns the index of the <code>PropertyAtomConceptReference</code> in this array that refers to the given
     * <code>Concept</code>, if any, otherwise creates a <code>PropertyAtomConceptReference</code>
     * that refers to the instance and appends it to the end of the array.
     *
     * @param instance the <code>Concept</code> to search for in this array, or to which a new reference will be added.
     * @return the index at which the instance is found or inserted.
     * @.category public-api
     * @since 2.0.0
     */
    int put(Concept instance);


    /**
     * Removes the first <code>PropertyAtomConceptReference</code> referring to the given instance
     * that is found in this array.
     *
     * @param instance the <code>Concept</code>to search for in the array.
     * @return the first <code>PropertyAtomConceptReference</code> referring to the given instance
     *         that is found in this array, or null if none was found.
     * @.category public-api
     * @since 2.0.0
     */
    PropertyAtomConceptReference remove(Concept instance);


    /**
     * Finds the first <code>PropertyAtomConceptReference</code> referring to the given instance in this array.
     *
     * @param instance the <code>Concept</code> instance to search for in the array.
     * @return the index at which the instance was found, or -1 if it was not found.
     */
    int getPropertyAtomConceptReferenceIndex(Concept instance);


    /**
     * Finds the first <code>PropertyAtomConceptReference</code> referring to the given instance in this array.
     *
     * @param instance the <code>Concept</code> instance to search for in the array.
     * @return the <code>PropertyAtomConceptReference</code> that was found, or null if none was not found.
     * @.category public-api
     * @since 2.0.0
     */
    PropertyAtomConceptReference getPropertyAtomConceptReference(Concept instance);


    /**
     * Sets the <code>PropertyConceptReference</code> at the given index to a reference to the given <code>Concept</code>.
     * If <code>index == length</code>, this method will call <code>add(index, value)</code> instead.
     *
     * @param index the index at which the reference is to be set.
     * @param value the target <code>Concept</code> for the  new  reference value.
     * @throws IndexOutOfBoundsException - if the index is less than zero or more than the current size of the array.
     * @.category public-api
     * @since 2.0.0
     */
    void set(int index, Concept value);


    /**
     * Removes the first occurrence of <code>PropertyConceptReference</code> which is a reference to the given instance,
     * and returns it.
     * This method shifts all the subsequent array elements to the left (i.e. substracts one to their indices).
     * It returns null if no <code>PropertyConceptReference</code> is found.
     *
     * @param instance the instance to search for.
     * @return the removed <code>PropertyConceptReference</code> if any, else null.
     * @.category public-api
     * @since 2.0.0
     */
    PropertyAtomConceptReference removePropertyAtom(Concept instance);
}
