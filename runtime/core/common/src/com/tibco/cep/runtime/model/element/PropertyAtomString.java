package com.tibco.cep.runtime.model.element;

/*
 * Created by IntelliJ IDEA. 
 * User: nleong
 * Date: Apr 7, 2004
 * Time: 4:31:21 PM
 *
 * Copyright (c) 2004  TIBCO Software Inc.
 * Contact: Nick Leong (nleong@tibco.com)
 */

/**
 * Property that contains one <code>String</code>.
 *
 * @version 2.0.0
 * @.category public-api
 * @since 2.0.0
 */
public interface PropertyAtomString extends PropertyAtom, Property.PropertyString {


    public final static String DEFAULT_VALUE = null;


    /**
     * Gets the current value of this property.
     *
     * @return the current value of this property.
     * @.category public-api
     * @since 2.0.0
     */
    String getString();


    /**
     * Gets the value that property had at the given time.
     *
     * @param time a time expressed in number of milliseconds since the epoch.
     * @return a <code>String</code> value.
     * @throws PropertyException if the value at the given time is unknown.
     * @.category public-api
     * @since 2.0.0
     */
    String getString(long time) throws PropertyException;


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
    String getStringAtIdx(int idx) throws PropertyException;


    /**
     * Sets the current value of this property.
     *
     * @param value the value to set
     * @return true if a new entry was added in the history for this value.
     * @.category public-api
     * @see Property#getHistoryPolicy()
     * @since 2.0.0
     */
    boolean setString(java.lang.String value);


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
    boolean setString(java.lang.String value, long time);

    //+=
    String addAssign(String add);
}
