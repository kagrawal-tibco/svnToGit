package com.tibco.cep.query.service;

/*
* Author: Ashwin Jayaprakash Date: Jul 30, 2008 Time: 3:40:27 PM
*/
public enum QueryProperty {
    LOCALCACHE_MAXELEMENTS("be.agent.query.localcache.maxelements"),
    LOCALCACHE_EVICTSECONDS("be.agent.query.localcache.evictseconds"),
    LOCALCACHE_PREFETCHAGGRESSIVE("be.agent.query.localcache.prefetchaggressive"),
    QUERYDATAPROVIDER_CLASSNAME("be.agent.query.querydataprovider.classname"),
    CONTINUOUS_QUERY_MAX_THREADS("be.agent.query.continuous.threadpoolsize"),
    ALLOW_CONTINUOUS_QUERY("be.agent.query.continuous.allow"),
    FILTER_OPTIMIZER("be.agent.query.enable.filter.optimizer"),
    SHOW_BQL_CONSOLE("be.agent.query.console"),
    NAME_DYNAMIC_QUERY_SESSION("be.agent.query.internal.dynamic");

    //-------------

    private final String propName;

    QueryProperty(String propName) {
        this.propName = propName;
    }

    public String getPropName() {
        return propName;
    }
}
