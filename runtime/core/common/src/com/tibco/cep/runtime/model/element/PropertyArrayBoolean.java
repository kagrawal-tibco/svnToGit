package com.tibco.cep.runtime.model.element;

/*
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: May 31, 2004
 * Time: 2:30:11 AM
 * To change this template use Options | File Templates.
 */

/**
 * Property that contains zero, one, or multiple <code>PropertyAtomBoolean</code>.
 *
 * @version 2.0.0
 * @.category public-api
 * @since 2.0.0
 */
public interface PropertyArrayBoolean extends PropertyArray, Property.PropertyBoolean {


    /**
     * Creates a <code>PropertyAtomBoolean</code> with the given value and appends it to the end of the array
     *
     * @param value of the <code>PropertyAtomBoolean</code> to create.
     * @return the index at which it is inserted.
     * @.category public-api
     * @since 2.0.0
     */
    int add(boolean value);


    /**
     * Creates a <code>PropertyAtomBoolean</code> with the given value and inserts it at the specified position
     * in the array.
     * Shifts the <code>PropertyAtomBoolean</code> currently at that position (if any)
     * and any subsequent <code>PropertyAtomBoolean</code> to the right (i.e. adds one to their indices).
     *
     * @param index at which the specified <code>PropertyAtomBoolean</code> is to be inserted.
     * @param value of the <code>PropertyAtomBoolean</code> to be inserted.
     * @throws IndexOutOfBoundsException if index is invalid.
     * @.category public-api
     * @since 2.0.0
     */
    void add(int index, boolean value);


    /**
     * Sets the <code>PropertyAtomBoolean</code> at index the given index to the given value.
     * If <code>index == length</code>, this method will call <code>add(index, value)</code> instead.
     *
     * @param index at which the specified value is to be set.
     * @param value to set.
     * @throws IndexOutOfBoundsException if the index is invalid.
     * @.category public-api
     * @since 2.0.0
     */
    void set(int index, boolean value);


    /**
     * Removes the first occurrence of <code>PropertyAtomBoolean</code> in this array which has the given value,
     * and returns it.
     * This method shifts all the subsequent array elements to the left (i.e. substracts one to their indices).
     * It returns null if no <code>PropertyAtomBoolean</code> is found.
     *
     * @param value to search for.
     * @return the removed <code>PropertyAtomBoolean</code>.
     * @.category public-api
     * @since 2.0.0
     */
    PropertyAtomBoolean removePropertyAtom(boolean value);

}
