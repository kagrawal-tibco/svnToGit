/**
 *
 */
package com.tibco.be.bemm.functions;

public interface RemoteMetricsCollector {
    public static enum COLLECTOR_TYPE {
        JMX, HAWK, CACHE
    }

    public static enum STATUS {
        OK, PROPERTIES_PARSE_ERROR, IO_ERROR, CONFIG_ERROR, DISCONNECTED, NOT_INITIALIZED
    }

    public void subscribe(String name,String type, String receivingEventURI);

    public void unsubscribe(String type);

    public Object snapshot(String name,String type, String receivingEventURI);
    
    public void destroy();
}
