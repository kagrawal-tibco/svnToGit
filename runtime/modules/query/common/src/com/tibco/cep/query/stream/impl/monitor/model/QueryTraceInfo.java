package com.tibco.cep.query.stream.impl.monitor.model;

import com.tibco.cep.query.stream.impl.monitor.jmx.QueryInfoImpl;

/**
 * Created by IntelliJ IDEA.
 * User: hlouro
 * Date: Jul 25, 2008
 * Time: 3:15:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class QueryTraceInfo {
    private QueryInfoImpl queryInfoObj;

    public QueryTraceInfo(QueryInfoImpl queryInfoObj) {
        this.queryInfoObj = queryInfoObj;
    }

    protected void enableTracing() throws Exception {
        queryInfoObj.enableTracing();
    }

}
