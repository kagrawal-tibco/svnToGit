package com.tibco.cep.query.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.tibco.cep.query.service.ContinuousQueryExecutionPolicy;
import com.tibco.cep.query.service.QueryResultSetListener;

/*
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Mar 18, 2008
 * Time: 8:19:12 PM
 */

public class ContinuousQueryExecutionPolicyImpl
        extends QueryExecutionPolicyImpl
        implements ContinuousQueryExecutionPolicy {

    protected Map<String, QueryResultSetListener> listeners;


    public ContinuousQueryExecutionPolicyImpl() {
        this.listeners = new LinkedHashMap<String, QueryResultSetListener>();
    }


    public List<QueryResultSetListener> getListeners() {
        return new ArrayList<QueryResultSetListener>(this.listeners.values());
    }

    public void registerListener(String name, QueryResultSetListener listener) {
        if (this.listeners.containsKey(name)) {
            throw new IllegalArgumentException("Key already in use: " + name);
        }
        if (this.listeners.containsValue(listener)) {
            throw new IllegalArgumentException("Listener already registered.");
        }
        this.listeners.put(name, listener);
    }


    public void unregisterListener(String name) {
        this.listeners.remove(name);
    }


}
