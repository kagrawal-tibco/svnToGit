package com.tibco.cep.runtime.model.element;

/*
* Created by IntelliJ IDEA.
* User: nleong
* Date: Apr 7, 2004
* Time: 4:20:54 PM
*
* Copyright (c) 2004  TIBCO Software Inc.
* Contact: Nick Leong (nleong@tibco.com)
*/

/**
 * Property that contains one <code>int</code>.
 *
 * @version 2.0.0
 * @.category public-api
 * @since 2.0.0
 */
public interface PropertyAtomInt extends PropertyAtom, Property.PropertyInt {


    public final static int DEFAULT_VALUE = 0;


    /**
     * Gets the current value of this property.
     *
     * @return the current value of this property.
     * @.category public-api
     * @since 2.0.0
     */
    int getInt();


    /**
     * Gets the value that property had at the given time.
     *
     * @param time a time expressed in number of milliseconds since the epoch.
     * @return an <code>int</code> value.
     * @throws PropertyException if the value at the given time is unknown.
     * @.category public-api
     * @since 2.0.0
     */
    int getInt(long time) throws PropertyException;


    /**
     * Gets the value of this property at the given history index.
     *
     * @param idx index in the history of this property.
     * @return the value of this property at the given index.
     * @throws PropertyException if the index is invalid.
     * @.category public-api
     * @see Property#getHistoryPolicy()
     * @see Property#getHistorySize()
     * @since 2.0.0
     */
    int getIntAtIdx(int idx) throws PropertyException;


    /**
     * Sets the current value of this property.
     *
     * @param value the value to set
     * @return true if a new entry was added in the history for this value.
     * @.category public-api
     * @see Property#getHistoryPolicy()
     * @since 2.0.0
     */
    boolean setInt(int value);


    /**
     * Sets the value of this property with a given time.
     *
     * @param value the value to set.
     * @param time  the time to associate with the value.
     * @return true if a new entry was added in the history for this value.
     * @.category public-api
     * @see Property#getHistoryPolicy()
     * @since 2.0.0
     */
    boolean setInt(int value, long time);


    /**
     * Get the current int value as double of this property.
     *
     * @return the value of this property
     */
    double getDouble();


    /**
     * Get the int value of this property for a given time.
     *
     * @param time at this instance of time
     * @return the int value as double of this property
     * @throws PropertyException if the value is unknown
     */
    double getDouble(long time) throws PropertyException;

    //++x,--x,x++,x--,*=,/=,%=,+=,-=
    int preIncrement();
    int preDecrement();
    int postIncrement();
    int postDecrement();
    int multAssign(int mult);
    int divAssign(int div);
    int modAssign(int mod);
    int addAssign(int add);
    int subAssign(int sub);
}
