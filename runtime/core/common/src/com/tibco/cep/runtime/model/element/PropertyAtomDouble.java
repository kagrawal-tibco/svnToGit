package com.tibco.cep.runtime.model.element;

/*
* Created by IntelliJ IDEA.
* User: nleong
* Date: Apr 7, 2004
* Time: 4:29:24 PM
*
* Copyright (c) 2004  TIBCO Software Inc.
* Contact: Nick Leong (nleong@tibco.com)
*/

/**
 * Property that contains one <code>double</code>.
 *
 *
 * @version 2.0.0
 * @.category public-api
 * @since 2.0.0
 */
public interface PropertyAtomDouble extends PropertyAtom, Property.PropertyDouble {


    public final static double DEFAULT_VALUE = 0.0;


    /**
     * Gets the current value of this property.
     *
     * @return the current value of this property.
     * @.category public-api
     * @since 2.0.0
     */
    double getDouble();


    /**
     * Gets the value that property had at the given time.
     *
     * @param time a time expressed in number of milliseconds since the epoch.
     * @return a <code>double</code> value.
     * @throws PropertyException if the value at the given time is unknown.
     * @.category public-api
     * @since 2.0.0
     */
    double getDouble(long time) throws PropertyException;


    /**
     * Sets the current value of this <code>PropertyAtom</code>.
     *
     * @param value the value to set
     * @return true if a new entry was added in the history for this value.
     * @.category public-api
     * @see Property#getHistoryPolicy()
     * @since 2.0.0
     */
    boolean setDouble(double value);


    /**
     * Sets the value of this <code>PropertyAtom</code> with a given time.
     *
     * @param value the value to set.
     * @param time  the time to associate with the value.
     * @return true if a new entry was added in the history for this value.
     * @.category public-api
     * @see Property#getHistoryPolicy()
     * @since 2.0.0
     */
    boolean setDouble(double value, long time);


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
    double getDoubleAtIdx(int idx) throws PropertyException;


    /**
     * Gets the current double value of this property as an int.
     *
     * @return the value of this property
     */
    int getInt();


    /**
     * Gets the double value of this property for a given time.
     *
     * @param time at this instance of time
     * @return the double value as int of this property
     * @throws PropertyException if the value is unknown
     */
    int getInt(long time) throws PropertyException;

    //++x,--x,x++,x--,*=,/=,%=,+=,-=
    double preIncrement();
    double preDecrement();
    double postIncrement();
    double postDecrement();
    double multAssign(double mult);
    double divAssign(double div);
    double modAssign(double mod);
    double addAssign(double add);
    double subAssign(double sub);
}
