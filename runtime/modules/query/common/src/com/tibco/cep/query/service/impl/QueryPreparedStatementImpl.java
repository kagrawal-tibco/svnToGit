package com.tibco.cep.query.service.impl;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import com.tibco.cep.query.api.QueryException;
import com.tibco.cep.query.exec.QueryExecutionPlan;
import com.tibco.cep.query.service.Query;
import com.tibco.cep.query.service.QueryExecutionPolicy;
import com.tibco.cep.query.service.QueryPreparedStatement;
import com.tibco.cep.query.service.QueryResultSet;
import com.tibco.cep.query.service.QueryStatement;

/*
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Oct 2, 2007
 * Time: 12:36:29 PM
 */
public class QueryPreparedStatementImpl extends AbstractQueryStatementImpl implements QueryPreparedStatement {

    private Map<String, Object> bindVars;


    public QueryPreparedStatementImpl(Query query, QueryExecutionPlan executionPlan) throws Exception {
        super(query, executionPlan);
        this.bindVars = new HashMap<String, Object>();
    }

    /**
     * Clears the current parameter values immediately.
     */
    public void clearParameters() throws QueryException {
        this.bindVars.clear();
    }

    /**
     * Sets the designated parameter to the given Java boolean value.
     *
     * @param parameterIndex
     * @param x
     */
    public void setBoolean(int parameterIndex, boolean x) throws QueryException {
        //todo: box to expected type or fail
    }

    /**
     * Sets the designated parameter to the given Java byte value.
     *
     * @param parameterIndex
     * @param x
     */
    public void setByte(int parameterIndex, byte x) throws QueryException {
        //todo: box to expected type or fail
    }

    /**
     * Sets the designated parameter to the given Java array of bytes.
     *
     * @param parameterIndex
     * @param x
     */
    public void setBytes(int parameterIndex, byte[] x) throws QueryException {
        //todo: box to expected type or fail
    }

    /**
     * Sets the designated parameter using the given Calendar object.
     *
     * @param label
     * @param x
     */
    public void setDateTime(String label, Calendar x) throws QueryException {
        //todo: check expected type for this label
        if (null == x) {
            throw new QueryException(new IllegalArgumentException());
        }
        this.bindVars.put(label, x);
    }

    /**
     * Sets the designated parameter to the given Java double value.
     *
     * @param label
     * @param x
     */
    public void setDouble(String label, Double x) throws QueryException {
        //todo: box to expected type or fail
        if (null == x) {
            throw new QueryException(new IllegalArgumentException());
        }
        this.bindVars.put(label, x);
    }

    /**
     * Sets the designated parameter to the given Java float value.
     *
     * @param parameterIndex
     * @param x
     */
    public void setFloat(int parameterIndex, float x) throws QueryException {
        //todo: box to expected type or fail
    }

    /**
     * Sets the designated parameter to the given Java int value.
     *
     * @param parameterIndex
     * @param x
     */
    public void setInt(int parameterIndex, int x) throws QueryException {
        //todo: box to expected type or fail
    }

    /**
     * Sets the designated parameter to the given Java long value.
     *
     * @param parameterIndex
     * @param x
     */
    public void setLong(int parameterIndex, long x) throws QueryException {
        //todo: box to expected type or fail
    }

    /**
     * Sets the designated parameter to the given Java short value.
     *
     * @param parameterIndex
     * @param x
     */
    public void setShort(int parameterIndex, long x) throws QueryException {
        //todo: box to expected type or fail
    }

    /**
     * Sets the designated parameter to the given Java String value.
     *
     * @param label
     * @param x
     */
    public void setString(String label, String x) throws QueryException {
        //todo: box to expected type or fail
        if (null == x) {
            throw new QueryException(new IllegalArgumentException());
        }
        this.bindVars.put(label, x);
    }

    /**
     * Sets the designated parameter to the given object.
     *
     * @param label
     * @param x
     */
    public void setObject(String label, Object x) throws QueryException {
        //todo: find expected type, box to it or fail
        if ((x instanceof Number) && !(x instanceof Double)) {
            x = new Double(((Number) x).doubleValue());
        }
        this.bindVars.put(label, x);
    }

    /**
     * Sets the designated parameter to NULL.
     *
     * @param label
     * @throws com.tibco.cep.query.api.QueryException
     *
     */
    public void setNull(String label) throws QueryException {
        this.bindVars.remove(label);
    }

    /**
     * Bind the variable parameters to the query
     *
     * @param params
     */
    public void bindParameters(Map<String, Object> params) throws QueryException {
        if (params != null) {
            for (Map.Entry<String, Object> e: params.entrySet()) {
                this.setObject(e.getKey(), e.getValue());
            }
        } else {
            this.bindVars = new HashMap<String, Object>();
        }
    }


    /**
     * Executes the OQL query
     *
     * @return the current result as a QueryResultSet object or null if there are no more results
     * @throws com.tibco.cep.query.api.QueryException
     *
     */
    public QueryResultSet executeStatement(QueryExecutionPolicy policy) throws QueryException {
        this.setState(QueryStatement.QUERYSTATEMENT_STATE_OPEN);
        return this.resultSet;

    }
}
