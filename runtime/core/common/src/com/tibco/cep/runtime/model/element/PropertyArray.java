package com.tibco.cep.runtime.model.element;

/*
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: May 29, 2004
 * Time: 10:48:20 PM
 * To change this template use Options | File Templates.
 */

/**
 * Base for array properties, (properties that can contain multiple <code>PropertyAtom</code>).
 *
 * @version 2.0.0
 * @.category public-api
 * @since 2.0.0
 */
public interface PropertyArray extends Property {


    /**
     * Returns the length of this array.
     *
     * @return the length of this array.
     * @.category public-api
     * @since 2.0.0
     */
    int length();


    /**
     * Removes all the elements from this list. The list will be empty after this call returns.
     *
     * @.category public-api
     * @since 2.0.0
     */
    void clear();


    /**
     * Creates a {@link PropertyAtom} with the given value, and appends it to the end of the array.
     * This method does not check for duplicates (i.e. existence of the same value in the array).
     *
     * @param value the object to create the new <code>PropertyAtom</code> with.
     * @return the index at which the <code>PropertyAtom</code> was inserted.
     * @.category public-api
     * @since 2.0.0
     */
    int add(Object value);


    /**
     * Creates a <code>PropertyAtom</code> with the given value, and inserts it at the specified position in the array.
     * Shifts the element currently at that position (if any) and any subsequent elements to the right
     * (i.e. adds one to their indices).
     *
     * @param index the index at which the specified element is to be inserted.
     * @param value the value of the property atom to insert.
     * @throws IndexOutOfBoundsException if the index is invalid.
     * @.category public-api
     * @since 2.0.0
     */
    void add(int index, Object value);


    /**
     * Removes the <code>PropertyAtom</code> at the specified position in this list.
     * Shifts any subsequent elements to the left (i.e. subtracts one from their indices).
     *
     * @param index the index of the element to remove.
     * @return the element that was removed from the list.
     * @throws IndexOutOfBoundsException if index was invalid.
     * @.category public-api
     * @since 2.0.0
     */
    PropertyAtom remove(int index);


    /**
     * Returns the <code>PropertyAtom</code> at the specified position in the array.
     *
     * @param index the index of the <code>PropertyAtom</code> to return.
     * @return the element at the specified position in this array.
     * @throws IndexOutOfBoundsException if the index is invalid.
     * @.category public-api
     * @since 2.0.0
     */
    PropertyAtom get(int index);


    /**
     * Returns an array containing all of the elements in this <code>PropertyArray</code> in the correct order.
     *
     * @return an array containing all of the elements in this <code>PropertyArray</code> in the correct order.
     * @.category public-api
     * @since 2.0.0
     */
    PropertyAtom[] toArray();


    /**
     * Gets a <code>String</code> representation of the current value of this <code>PropertyArray</code>.
     *
     * @.category public-api
     * @since 2.0.0
     */
    java.lang.String getString();


    /**
     * Returns an array containing all of the elements in this <code>PropertyArray</code> in the correct order,
     * and whose runtime type is that of the specified array.
     * <p>If the result fits in the specified array, it is returned therein.
     * Otherwise, a new array is allocated with the runtime type of the specified array
     * and the size of this <code>PropertyArray</code>.</p>
     * <p>If the list fits in the specified array with room to spare
     * (i.e. if the array has more elements than the list),
     * the element in the array immediately following the end of the collection are set to null.
     * This is useful in determining the length of the list only if the caller knows that the list
     * does not contain any null element.</p>
     *
     * @param array the array into which the elements of the list are to be stored, if it is big enough;
     *              otherwise, a new array of the same runtime type will be allocated for this purpose.
     * @return an array containing the elements of this <code>PropertyArray</code>.
     * @throws ArrayStoreException - if the runtime type of a is not a supertype of the runtime
     *                             type of every element in this list.
     * @.category public-api
     * @since 2.0.0
     */
    PropertyAtom[] toArray(PropertyAtom[] array);


    /**
     * Creates a <code>PropertyAtom</code> without setting a value and appends it to the end of the array.
     *
     * @return the <code>PropertyAtom</code> element that was added.
     * @.category public-api
     * @since 2.0.0
     */
    PropertyAtom add();
}
