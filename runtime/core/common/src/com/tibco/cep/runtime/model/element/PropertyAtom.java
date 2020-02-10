package com.tibco.cep.runtime.model.element;


import java.io.DataInput;
import java.io.DataOutput;
import java.util.Iterator;

import com.tibco.xml.data.primitive.XmlTypedValue;

/*
* Created by IntelliJ IDEA.
* User: nleong
* Date: Apr 7, 2004
* Time: 3:04:47 PM
*
* Copyright (c) 2004  TIBCO Software Inc.
* Contact: Nick Leong (nleong@tibco.com)
*
*/

/**
 * Base for atom properties (properties that contains one value).
 *
 * @version 2.0.0
 * @.category public-api
 * @since 2.0.0
 */
public interface PropertyAtom extends Property {


    /**
     * Gets the current value of this <code>PropertyAtom</code>.
     *
     * @return the <code>Object</code> value of this <code>PropertyAtom</code>
     */
    Object getValue();


    /**
     * Gets the previous value in the history of this <code>PropertyAtom</code>.
     *
     * @return the previous value of this <code>PropertyAtom</code> if any, else null.
     */
    Object getPreviousValue();


    /**
     * Get the value this <code>PropertyAtom</code> had at the given time.
     *
     * @param time a time expressed in number of milliseconds since the epoch.
     * @return the value of this <code>PropertyAtom</code> at the given time, if known
     * @throws PropertyException if the value of this <code>PropertyAtom</code> at the given time is unknown.
     * @see com.tibco.cep.runtime.model.element.Property#getHistoryPolicy()
     * @see Property#getHistorySize()
     */
    Object getValue(long time) throws PropertyException;


    /**
     * Gets the time at which the value at the given history index was set.
     *
     * @param idx index in the history of this property.
     * @return the timestamp at the given index.
     * @.category public-api
     * @see Property#getHistoryPolicy()
     * @see Property#getHistorySize()
     * @since 2.0.0
     */
    long getTimeAtIdx(int idx) throws PropertyException;


    /**
     * Gets the value of this <code>PropertyAtom</code> at the given history index.
     *
     * @param idx index in the history of this property.
     * @return the value of this property at the given index.
     * @see com.tibco.cep.runtime.model.element.Property#getHistoryPolicy()
     * @see Property#getHistorySize()
     */
    Object getValueAtIdx(int idx) throws PropertyException;


    /**
     * Gets the timestamp of the oldest value in the current history of this property.
     * Note that since the history is a ring, the return value may change over time.
     *
     * @return the oldest timestamp in the current history of this property.
     * @.category public-api
     * @see com.tibco.cep.runtime.model.element.Property#getHistoryPolicy()
     * @see Property#getHistorySize()
     * @since 2.0.0
     */
    //TODO this method has a very misleading name!!!
    public long howOld();


    /**
     * Gets the timestamp of the latest value in the current history of this property.
     *
     * @return the latest timestamp in the current history of this property.
     * @.category public-api
     * @see com.tibco.cep.runtime.model.element.Property#getHistoryPolicy()
     * @see Property#getHistorySize()
     * @since 2.0.0
     */
    public long howCurrent();


    /**
     * Gets the number of values known to the current history of this property, between stime and etime.
     * Passing 0 for stime and etime returns the total number of values known in the current history.
     * This method fails if stime is older than the oldest timestamp in the history.
     *
     * @param stime a time expressed in ms since the epoch.
     * @param etime a time expressed in ms since the epoch, greater than or equal to stime.
     * @return the number of values known to the current history of this property, between stime and etime.
     * @throws PropertyException if stime is older than the oldest history timestamp.
     * @.category public-api
     * @see Property#getHistoryPolicy()
     * @see Property#getHistorySize()
     * @since 2.0.0
     */
    public int howMany(long stime, long etime) throws PropertyException;


    /**
     * Sets the current value of this <code>PropertyAtom</code>.
     *
     * @param obj the value to set
     * @return true if a new entry was added in the history for this value.
     * @.category public-api
     * @see Property#getHistoryPolicy()
     * @since 2.0.0
     */
    boolean setValue(Object obj);


    /**
     * Sets the value of this <code>PropertyAtom</code> with a given time.
     *
     * @param obj  the value to set.
     * @param time the time to associate with the value.
     * @return true if a new entry was added in the history for this value.
     * @.category public-api
     * @see com.tibco.cep.runtime.model.element.Property#getHistoryPolicy()
     * @since 2.0.0
     */
    boolean setValue(Object obj, long time);


    /**
     * Gets the <code>String</code> representation of the value of this <code>PropertyAtom</code>.
     *
     * @return the <code>String</code> value of this <code>PropertyAtom</code>.
     * @.category public-api
     * @since 2.0.0
     */
    java.lang.String getString();


    /**
     * Gets the <code>String</code> representation of the value of this <code>PropertyAtom</code> at the given time.
     *
     * @param time a time expressed in number of milliseconds since the epoch.
     * @return the <code>String</code> value of this <code>PropertyAtom</code> at the given time.
     * @throws PropertyException if time is older than the oldest history timestamp.
     * @.category public-api
     * @see Property#getHistoryPolicy()
     * @see Property#getHistorySize()
     * @since 2.0.0
     */
    java.lang.String getString(long time) throws PropertyException;


    /**
     * Gets the <code>String</code> representation of the value of this <code>PropertyAtom</code> at the given history index.
     *
     * @param idx an index in the history of this property.
     * @return the <code>String</code> value of this <code>PropertyAtom</code> at the given history index.
     * @throws PropertyException if idx is not a valid history index.
     * @.category public-api
     * @see com.tibco.cep.runtime.model.element.Property#getHistoryPolicy()
     * @see Property#getHistorySize()
     * @since 2.0.0
     */
    String getStringAtIdx(int idx) throws PropertyException;


    /**
     * Gets an iterator on the values in the history (in proper sequence and
     * History format), starting at the last stored value in the history.
     *
     * @return history iterator
     * @see com.tibco.cep.runtime.model.element.Property#getHistoryPolicy()
     * @see Property#getHistorySize()
     */
    Iterator historyIterator();


    /**
     * Returns a iterator of the values in the history (in proper sequence and
     * History format), starting at the last stored value in the history.
     *
     * @return history iterator
     * @see com.tibco.cep.runtime.model.element.Property#getHistoryPolicy()
     * @see Property#getHistorySize()
     */
    Iterator backwardHistoryIterator();


    /**
     * Returns a iterator of the values in the history (in proper sequence and
     * History format), starting at the first stored value in the history.
     *
     * @return history iterator
     * @see com.tibco.cep.runtime.model.element.Property#getHistoryPolicy()
     * @see Property#getHistorySize()
     */
    Iterator forwardHistoryIterator();


    /**
     * Sets <code>interval[0]</code> to <code>t1</code> and <code>interval[1]</code> to <code>t2</code>
     * where <code>t1</code> and <code>t2</code> are timestamps in the current property history
     * and <code>t1 &lt;=time &lt;= t2<code>.
     *
     * @param time     a time in ms since the epoch
     * @param interval a <code>long[]</code> of length 2 or greater.
     * @.category public-api
     * @since 2.0.0
     */
    void getHistoryTimeInterval(long time, long[] interval) throws PropertyException;


    /**
     * Gets the property value in terms of the corresponding XML type
     * Used by the Mapper
     *
     * @return an XmlTypedValue
     */
    XmlTypedValue getXMLTypedValue();


    /**
     * Sets the property value in terms of the corresponding XML type
     * Used by the mapper
     *
     * @param value
     * @return a boolean
     */
    boolean setValue(XmlTypedValue value) throws Exception;


    /**
     * Sets the property value in terms of XML String Values
     * Used by the contenthandler
     *
     * @param value
     * @return a boolean
     */
    boolean setValue(String value) throws Exception;


    /**
     * Returns true if the property is set.
     *
     * @return true if the property is set, false otherwise
     * @.category public-api
     * @since 2.0.0
     */
    boolean isSet();


     public void writeToDataOutput(DataOutput output) throws Exception;

    public void readFromDataInput(DataInput input) throws Exception;

    /**
     * History item (time + value) stored in the property.
     */
    public class History {


        /**
         * Stores the creation timestamp of this <code>History</code> in ms since the epoch.
         */
        public long time;

        /**
         * Stores the property value.
         */
        public Object value;
    }


}
