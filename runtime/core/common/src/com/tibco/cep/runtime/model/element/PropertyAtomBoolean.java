package com.tibco.cep.runtime.model.element;

/*
 * Created by IntelliJ IDEA. 
 * User: nleong
 * Date: Apr 7, 2004
 * Time: 4:42:45 PM
 *
 * Copyright (c) 2004  TIBCO Software Inc.
 * Contact: Nick Leong (nleong@tibco.com)
 */

/**
 * Property that contains one boolean value.
 *
 * @version 2.0.0
 * @.category public-api
 * @since 2.0.0
 */
public interface PropertyAtomBoolean extends PropertyAtom, Property.PropertyBoolean {


    public final static boolean DEFAULT_VALUE = false;


    /**
     * Gets the current boolean value of this <code>PropertyAtom</code>.
     *
     * @return the value of this <code>PropertyAtom</code>.
     * @.category public-api
     * @since 2.0.0
     */
    boolean getBoolean();


    /**
     * Gets the boolean value that this <code>PropertyAtom</code> had at the given time.
     *
     * @param time a time expressed in number of milliseconds since the epoch.
     * @return a boolean value.
     * @throws PropertyException if the value at the given time is unknown.
     * @.category public-api
     * @since 2.0.0
     */
    boolean getBoolean(long time) throws PropertyException;


    /**
     * Gets the boolean value of this <code>PropertyAtom</code> at the given history index.
     *
     * @param idx index in the history of this property.
     * @return the boolean value of this property at the given index.
     * @throws PropertyException if the index is invalid.
     * @.category public-api
     * @see Property#getHistoryPolicy()
     * @see Property#getHistorySize()
     * @since 2.0.0
     */
    boolean getBooleanAtIdx(int idx) throws PropertyException;


    /**
     * Sets the current value of this <code>PropertyAtom</code>.
     *
     * @param value the value to set
     * @return true if a new entry was added in the history for this value.
     * @.category public-api
     * @see Property#getHistoryPolicy()
     * @since 2.0.0
     */
    boolean setBoolean(boolean value);


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
    boolean setBoolean(boolean value, long time);

}
