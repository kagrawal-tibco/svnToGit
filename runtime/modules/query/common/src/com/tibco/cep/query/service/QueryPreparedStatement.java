package com.tibco.cep.query.service;

import java.util.Calendar;
import java.util.Map;

import com.tibco.cep.query.api.QueryException;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Oct 1, 2007
 * Time: 9:38:03 PM
 * To change this template use File | Settings | File Templates.
 */
public interface QueryPreparedStatement extends QueryStatement{
    /**
     * Clears the current parameter values immediately.
     */
    public void clearParameters() throws QueryException;

    /**
     * Sets the designated parameter to the given Java boolean value.
     * @param parameterIndex
     * @param x
     */
    public void setBoolean(int parameterIndex, boolean x) throws QueryException;

    /**
     * Sets the designated parameter to the given Java byte value.
     * @param parameterIndex
     * @param x
     */
    public void setByte(int parameterIndex, byte x) throws QueryException;

    /**
     * Sets the designated parameter to the given Java array of bytes.
     * @param parameterIndex
     * @param x
     */
    void setBytes(int parameterIndex, byte[] x) throws QueryException;

    /**
     * Sets the designated parameter using the given Calendar object.
     * @param label
     * @param cal
     */
    void setDateTime(String label, Calendar cal) throws QueryException;

    /**
     * Sets the designated parameter to the given Java double value.
     * @param label
     * @param x
     */
    void setDouble(String label, Double x) throws QueryException;

    /**
     * Sets the designated parameter to the given Java float value.
     * @param parameterIndex
     * @param x
     */
    void setFloat(int parameterIndex, float x) throws QueryException;

    /**
     * Sets the designated parameter to the given Java int value.
     * @param parameterIndex
     * @param x
     */
    void setInt(int parameterIndex, int x) throws QueryException;

    /**
     * Sets the designated parameter to the given Java long value.
     * @param parameterIndex
     * @param x
     */
    void setLong(int parameterIndex, long x) throws QueryException;

    /**
     * Sets the designated parameter to the given Java short value.
     * @param parameterIndex
     * @param x
     */
    void setShort(int parameterIndex, long x) throws QueryException;

    /**
     * Sets the designated parameter to the given Java String value.
     * @param label
     * @param x
     */
    void setString(String label, String x) throws QueryException;

    /**
     * Sets the designated parameter to the given object.
     * @param label
     * @param x
     */
    void setObject(String label, Object x) throws QueryException;

    /**
     * Sets the designated parameter to NULL.
     * @param label
     * @throws QueryException
     */
    void setNull(String label) throws QueryException;


    /**
     * Bind the variable parameters to the query
     * @param params
     */
    public void bindParameters(Map<String, Object> params) throws QueryException;

}
