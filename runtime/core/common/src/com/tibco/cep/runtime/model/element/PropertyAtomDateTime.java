package com.tibco.cep.runtime.model.element;


import java.util.Calendar;

/*
 * Created by IntelliJ IDEA. 
 * User: nleong
 * Date: Apr 7, 2004
 * Time: 4:35:53 PM
 *
 * Copyright (c) 2004  TIBCO Software Inc.
 * Contact: Nick Leong (nleong@tibco.com)
 */

/**
 * Property that contains one DateTime i.e. <code>Calendar</code>.
 *
 * @version 2.0.0
 * @.category public-api
 * @since 2.0.0
 */
public interface PropertyAtomDateTime extends PropertyAtom, Property.PropertyDateTime {


    final static public Calendar DEFAULT_VALUE = null;


    /**
     * Gets the current value of this property.
     *
     * @return the current value of this property.
     * @.category public-api
     * @since 2.0.0
     */
    Calendar getDateTime();


    /**
     * Gets the value that property had at the given time.
     *
     * @param time a time expressed in number of milliseconds since the epoch.
     * @return a <code>Calendar</code> value.
     * @throws PropertyException if the value at the given time is unknown.
     * @.category public-api
     * @since 2.0.0
     */
    Calendar getDateTime(long time) throws PropertyException;


    /**
     * Gets the value of this <code>PropertyAtomDateTime</code> at the given history index.
     *
     * @param idx index in the history of this property.
     * @return the value of this property at the given index.
     * @throws PropertyException if the index is invalid.
     * @.category public-api
     * @see Property#getHistoryPolicy()
     * @see Property#getHistorySize()
     * @since 2.0.0
     */
    Calendar getDateTimeAtIdx(int idx) throws PropertyException;


    /**
     * Sets the current value of this <code>PropertyAtom</code>.
     *
     * @param value the value to set
     * @return true if a new entry was added in the history for this value.
     * @.category public-api
     * @see Property#getHistoryPolicy()
     * @since 2.0.0
     */
    boolean setDateTime(Calendar value);


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
    boolean setDateTime(Calendar value, long time);
}
