package com.tibco.cep.runtime.model.element;


import java.util.Calendar;

/*
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: May 31, 2004
 * Time: 2:31:43 AM
 * To change this template use Options | File Templates.
 */

/**
 * Property that contains zero, one, or multiple <code>PropertyAtomDateTime</code>.
 *
 * @version 2.0.0
 * @.category public-api
 * @since 2.0.0
 */
public interface PropertyArrayDateTime extends PropertyArray, Property.PropertyDateTime {


    /**
     * Creates a <code>PropertyAtomDateTime</code> with the given <code>Calendar</code>
     * and appends it to the array.
     *
     * @param value the <code>Calendar</code> to add.
     * @return the index at which the value was inserted.
     * @.category public-api
     * @since 2.0.0
     */
    int add(Calendar value);


    /**
     * Creates a <code>PropertyAtomDateTime</code> with the given <code>Calendar</code>
     * and inserts it into the array at the specified position.
     * This method shifts the <code>PropertyAtomDateTime</code> currently at
     * that position (if any) and any subsequent <code>PropertyAtomDateTime</code> to the right
     * (i.e. adds one to their indices).
     *
     * @param index the position at which to insert.
     * @param value the <code>Calendar</code> to add.
     * @throws IndexOutOfBoundsException - if the index is less than zero or more than the size of the array.
     * @.category public-api
     * @since 2.0.0
     */
    void add(int index, Calendar value);


    /**
     * Removes the first <code>PropertyAtomDateTime</code> with the given value
     * that is found in this array.
     * This method shifts all the subsequent array elements to the left
     * (i.e. substracts one to their indices).
     * It returns null if no matching value is found.
     *
     * @param value the <code>Calendar</code>to search for in the array.
     * @return the removed <code>PropertyAtomDateTime</code>, or null if none matched.
     * @.category public-api
     * @since 2.0.0
     */
    PropertyAtomDateTime removePropertyAtom(Calendar value);


    /**
     * Sets the <code>PropertyAtomDateTime</code> at the given index to the given <code>Calendar</code>.
     * If <code>index == length</code>, this method will call <code>add(index, value)</code> instead.
     *
     * @param index the index at which the value is to be set.
     * @param value the <code>Calendar</code> to set at the index.
     * @throws IndexOutOfBoundsException - if the index is less than zero or more than the current size of the array.
     * @.category public-api
     * @since 2.0.0
     */
    void set(int index, Calendar value);


}
