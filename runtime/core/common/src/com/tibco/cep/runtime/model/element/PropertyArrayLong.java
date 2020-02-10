package com.tibco.cep.runtime.model.element;

/*
* Created by IntelliJ IDEA.
* User: nleong
* Date: May 31, 2004
* Time: 2:32:23 AM
* To change this template use Options | File Templates.
*/

/**
 * Property that contains zero, one, or multiple <code>PropertyAtomLong</code>.
 *
 * @version 2.0.0
 * @.category public-api
 * @since 2.0.0
 */
public interface PropertyArrayLong extends PropertyArray, Property.PropertyLong {


    /**
     * Creates a <code>PropertyAtomLong</code> with the given <code>long</code>
     * and appends it to the array.
     *
     * @param value the <code>long</code> to add.
     * @return the index at which the value was inserted.
     * @.category public-api
     * @since 2.0.0
     */
    int add(long value);


    /**
     * Creates a <code>PropertyAtomLong</code> with the given <code>long</code>
     * and inserts it into the array at the specified position.
     * This method shifts the <code>PropertyAtomLong</code> currently at
     * that position (if any) and any subsequent <code>PropertyAtomLong</code> to the right
     * (i.e. adds one to their indices).
     *
     * @param index the position at which to insert.
     * @param value the <code>long</code> to add.
     * @throws IndexOutOfBoundsException - if the index is less than zero or more than the size of the array.
     * @.category public-api
     * @since 2.0.0
     */
    void add(int index, long value);


    /**
     * Removes the first <code>PropertyAtomLong</code> with the given value
     * that is found in this array.
     * This method shifts all the subsequent array elements to the left
     * (i.e. substracts one to their indices).
     * It returns null if no matching value is found.
     *
     * @param value the <code>long</code>to search for in the array.
     * @return the removed <code>PropertyAtomLong</code>, or null if none matched.
     * @.category public-api
     * @since 2.0.0
     */
    PropertyAtomLong removePropertyAtom(long value);


    /**
     * Sets the <code>PropertyAtomLong</code> at the given index to the given <code>long</code>.
     * If <code>index == length</code>, this method will call <code>add(index, value)</code> instead.
     *
     * @param index the index at which the value is to be set.
     * @param value the <code>long</code> to set at the index.
     * @throws IndexOutOfBoundsException - if the index is less than zero or more than the current size of the array.
     * @.category public-api
     * @since 2.0.0
     */
    void set(int index, long value);


}
