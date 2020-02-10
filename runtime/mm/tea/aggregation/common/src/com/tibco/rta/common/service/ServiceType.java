package com.tibco.rta.common.service;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 6/11/12
 * Time: 10:38 AM
 * Enum to be used on client side so that we do not directly need to depend on the instance of
 * the actual service.
 */
public enum ServiceType {

    ADMIN ("/service/admin"),

    METRICS ("/service/metrics"),

    RULE ("/service/rules"),

    QUERY ("/service/query"),

    METRICS_INTROSPECTION ("/service/metric/introspect"),

    ASYNC ("/service/async"),

    SESSION ("/service/session"),

    HEARTBEAT ("/service/heartbeat"),

    ENGINE ("/service/runtime");

    private String serviceURI;

    ServiceType(String serviceURI) {
        this.serviceURI = serviceURI;
    }

    public static ServiceType getByURI(String serviceURI) {
        ServiceType[] commandActions = ServiceType.values();
        for (ServiceType commandAction : commandActions) {
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
