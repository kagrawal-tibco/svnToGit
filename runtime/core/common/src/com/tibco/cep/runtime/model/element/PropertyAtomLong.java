package com.tibco.cep.runtime.model.element;

/*
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: May 18, 2004
 * Time: 1:24:35 PM
 * To change this template use Options | File Templates.
 */

/**
 * Property that contains one long.
 *
 * @version 2.0.0
 * @.category public-api
 * @since 2.0.0
 */
public interface PropertyAtomLong extends PropertyAtom, Property.PropertyLong {


    public final static long DEFAULT_VALUE = 0L;


    /**
     * Gets the current value of this property.
     *
     * @return the current value of this property.
     * @.category public-api
     * @since 2.0.0
     */
    long getLong();


    /**
     * Gets the value that property had at the given time.
     *
     * @param time a time expressed in number of milliseconds since the epoch.
     * @return a <code>long</code> value.
     * @throws PropertyException if the value at the given time is unknown.
     * @.category public-api
     * @since 2.0.0
     */
    long getLong(long time) throws PropertyException;


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
    long getLongAtIdx(int idx) throws PropertyException;


    /**
     * Sets the current value of this property.
     *
     * @param value the value to set
     * @return true if a new entry was added in the history for this value.
     * @.category public-api
     * @see Property#getHistoryPolicy()
     * @since 2.0.0
     */
    boolean setLong(long value);


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
    boolean setLong(long value, long time);


    /**
     * Get the current long value as double of this property.
     *
     * @return the value of this property
     */
    double getDouble();


    /**
     * Get the long value as double of this property for a given time.
     *
     * @param time at this instance of time
     * @return the long value as double of this property
     * @throws PropertyException if the value is unknown
     */
    double getDouble(long time) throws PropertyException;

    //++x,--x,x++,x--,*=,/=,%=,+=,-=
    long preIncrement();
    long preDecrement();
    long postIncrement();
    long postDecrement();
    long multAssign(long mult);
    long divAssign(long div);
    long modAssign(long mod);
    long addAssign(long add);
    long subAssign(long sub);
}
