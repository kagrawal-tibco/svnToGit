package com.tibco.cep.query.api.impl.local;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.tibco.cep.query.api.QueryConnection;
import com.tibco.cep.query.api.QueryDriver;
import com.tibco.cep.query.api.QueryException;
import com.tibco.cep.query.service.QuerySession;
import com.tibco.cep.query.stream.impl.rete.service.RegionManager;

/*
* Author: Ashwin Jayaprakash Date: Mar 24, 2008 Time: 1:37:12 PM
*/
public class QueryDriverImpl implements QueryDriver {
    protected Map<String, Parameter> parameters;

    /**
     * @param parameters Keys are URLs of the query engine to connect to.
     */
    public QueryDriverImpl(Map<String, Parameter> parameters) {
        this.parameters = new HashMap<String, Parameter>(parameters);
    }

    /**
     * Attempts to connect to a query engine.
     *
     * @param url  URL of the query engine to connect to.
     * @param info Ignored.
     * @return QueryConnection.
     * @throws com.tibco.cep.query.api.QueryException
     *          if the connection could not be established.
     */
    public QueryConnection connect(String url, Properties info) throws QueryException {
        Parameter parameter = parameters.get(url);

        if (parameter == null) {
            throw new QueryException("Invalid URL: " + url);
        }

        QuerySession querySession = parameter.getQuerySession();
        RegionManager regionManager = parameter.getRegionManager();

        return new QueryConnectionImpl(querySession, regionManager);
    }

    public void discard() {
        parameters.clear();
        parameters = null;
    }

    //-----------

    public static class Parameter {
        protected RegionManager regionManager;

        protected QuerySession querySession;

        public Parameter(RegionManager regionManager, QuerySession querySession) {
            this.regionManager = regionManager;
            this.querySession = querySession;
        }

        public RegionManager getRegionManager() {
            return regionManager;
        }

        public QuerySession getQuerySession() {
            return querySession;
        }
    }
}