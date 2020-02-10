package com.tibco.rta.service.transport.rest;

import com.tibco.rta.service.transport.ServiceEndpoint;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 20/2/14
 * Time: 3:43 PM
 * To change this template use File | Settings | File Templates.
 */
public enum RESTServiceEndpoints implements ServiceEndpoint {

    ADMIN_SERVICE("/service/admin/*"),

    METRICS_SERVICE("/service/metric"),

    //All rule ops
    RULE_SERVICE("/service/rules/*"),

    //All query ops
    QUERY_SERVICE("/service/query/*"),

    SESSION_SERVICE("/service/session/*"),

    METRIC_INTROSPECTION_SERVICE("/service/metric/introspect"),

    /**
     * A dummy service
     */
    ASYNC_SERVICE("/service/async");

    private String serviceURI;

    RESTServiceEndpoints(String serviceURI) {
        this.serviceURI = serviceURI;
    }

    public String getServiceURI() {
        return serviceURI;
    }
}
