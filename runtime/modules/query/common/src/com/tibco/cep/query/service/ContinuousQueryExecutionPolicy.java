package com.tibco.cep.query.service;

import java.util.List;

/*
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Mar 18, 2008
 * Time: 8:16:32 PM
 */

public interface ContinuousQueryExecutionPolicy extends QueryExecutionPolicy {


    List<QueryResultSetListener> getListeners();


    void registerListener(String name, QueryResultSetListener listener);


    void unregisterListener(String name);


}
