package com.tibco.cep.query.stream.context;

/*
 * Author: Ashwin Jayaprakash Date: Oct 1, 2007 Time: 3:12:59 PM
 */

public final class Context {
    protected final DefaultGlobalContext globalContext;

    protected final DefaultQueryContext queryContext;

    protected LocalContext localContext;

    public Context(DefaultGlobalContext globalContext, DefaultQueryContext sharedContext) {
        this.globalContext = globalContext;
        this.queryContext = sharedContext;
    }

    public LocalContext getLocalContext() {
        return localContext;
    }

    public void setLocalContext(LocalContext localContext) {
        this.localContext = localContext;
    }

    public DefaultGlobalContext getGlobalContext() {
        return globalContext;
    }

    public DefaultQueryContext getQueryContext() {
        return queryContext;
    }
}
