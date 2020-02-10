package com.tibco.cep.runtime.model.element;

/*
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Sep 28, 2004
 * Time: 12:11:10 AM
 * To change this template use File | Settings | File Templates.
 */

/**
 * Property that contains zero, one, or multiple <code>PropertyAtomContainedConcept</code>.
 *
 * @version 2.0.0
 * @.category public-api
 * @since 2.0.0
 */
public interface PropertyArrayContainedConcept extends PropertyArrayConcept, Property.PropertyContainedConcept {


    /**
     * Creates a <code>PropertyAtomContainedConcept</code> with the given <code>ContainedConcept</code>
     * and appends it to the array.
     *
     * @param instance the <code>ContainedConcept</code> to add.
     * @return the index at which the instance was inserted.
     * @throws ContainedConceptException if the <code>ContainedConcept</code> instance already belongs
     *                                   to a different parent.
     * @.category public-api
     * @since 2.0.0
     */
    int add(ContainedConcept instance);


    /**
     * Creates a <code>PropertyAtomContainedConcept</code> with the given <code>ContainedConcept</code>
     * and inserts it into the array at the specified position.
     * This method shifts the <code>PropertyAtomContainedConcept</code> currently at
     * that position (if any) and any subsequent <code>PropertyAtomContainedConcept</code> to the right
     * (i.e. adds one to their indices).
     *
     * @param index    the position at which to insert.
     * @param instance the <code>ContainedConcept</code> to add.
     * @throws ContainedConceptException if the <code>ContainedConcept</code> instance already belongs
     *                                   to a different parent.
     * @.category public-api
     * @since 2.0.0
     */
    void add(int index, ContainedConcept instance);


    /**
     * Returns the index of the <code>PropertyAtomContainedConcept</code> in this array that has the given
     * <code>ContainedConcept</code>, if any, otherwise creates a <code>PropertyAtomContainedConcept</code>
     * that refers to the instance and appends it to the end of the array.
     *
     * @param instance the <code>ContainedConcept</code> to search for in this array, or which will be added.
     * @return the index at which the instance is found or inserted.
     * @throws ContainedConceptException - if the <code>ContainedConcept</code> instance already belongs
     *                                   to a different parent.
     * @.category public-api
     * @since 2.0.0
     */
    int put(ContainedConcept instance);


    /**
     * Removes the first <code>PropertyAtomContainedConcept</code> with the given instance
     * that is found in this array.
     * This method shifts all the subsequent array elements to the left
     * (i.e. substracts one to their indices).
     *
     * @param instance the <code>ContainedConcept</code>to search for in the array.
     * @return the removed <code>PropertyAtomDateTime</code>, or null if none matched.
     * @.category public-api
     * @since 2.0.0
     */
    PropertyAtomContainedConcept remove(ContainedConcept instance);


    /**
     * Finds the first <code>PropertyAtomContainedConcept</code> with the given instance in this array.
     *
     * @param instance the <code>ContainedConcept</code> instance to search for in the array.
     * @return the index at which the instance was found, or -1 if it was not found.
     * @.category public-api
     * @since 2.0.0
     */
    int getPropertyAtomContainedConceptIndex(ContainedConcept instance);


    /**
     * Finds the first <code>PropertyAtomContainedConcept</code> with the given instance in this array.
     *
     * @param instance the <code>ContainedConcept</code> instance to search for in the array.
     * @return the <code>PropertyAtomContainedConcept</code> that was found, or null if none was not found.
     * @.category public-api
     * @since 2.0.0
     */
    PropertyAtomContainedConcept getPropertyAtomContainedConcept(ContainedConcept instance);


    /**
     * Sets the <code>PropertyContainedConcept</code> at the given index to the given <code>ContainedConcept</code>.
     * If <code>index == length</code>, this method will call <code>add(index, value)</code> instead.
     *
     * @param index the index at which the concept is to be set.
     * @param value the <code>ContainedConcept</code> to set at the index.
     * @throws IndexOutOfBoundsException - if the index is less than zero or more than the current size of the array.
     * @.category public-api
     * @since 2.0.0
     */
    void set(int index, ContainedConcept value);


    /**
     * Removes the first occurrence of <code>PropertyContainedConcept</code> which has the given instance,
     * and returns it.
     * This method shifts all the subsequent array elements to the left (i.e. substracts one to their indices).
     * It returns null if no matching <code>PropertyContainedConcept</code> is found.
     *
     * @param instance the instance to search for.
     * @return the removed <code>PropertyContainedConcept</code> if any, else null.
     * @.category public-api
     * @since 2.0.0
     */
    PropertyAtomContainedConcept removePropertyAtom(ContainedConcept instance);
}
