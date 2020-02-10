package com.tibco.rta.service.transport.http;

import com.tibco.rta.service.transport.ServiceEndpoint;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 29/10/12
 * Time: 4:25 PM
 * To change this template use File | Settings | File Templates.
 */
public enum HTTPServiceEndpoints implements ServiceEndpoint {

    ADMIN_SERVICE("/service/admin"),

    METRICS_SERVICE("/service/metrics"),

    RULE_SERVICE("/service/rules"),

    QUERY_SERVICE("/service/query"),

    METRIC_INTROSPECTION_SERVICE("/service/metric/introspect"),

    RUNTIME_SERVICE("/service/runtime"),

    /**
     * A dummy service
     */
    ASYNC_SERVICE("/service/async");

    private String serviceURI;

    HTTPServiceEndpoints(String serviceURI) {
        this.serviceURI = serviceURI;
    }

    public static HTTPServiceEndpoints getByURI(String serviceURI) {
        HTTPServiceEndpoints[] commandActions = HTTPServiceEndpoints.values();
        for (HTTPServiceEndpoints commandAction : commandActions) {
            if (commandAction.serviceURI.equals(serviceURI)) {
                return commandAction;
            }
        }
        return null;
    }

    public String getServiceURI() {
        return serviceURI;
    }
}
