package com.tibco.xdc;

/*
 * Author: Ashwin Jayaprakash / Date: 1/11/13 / Time: 5:44 PM
 */
public abstract class Common {
    public static final String PROP_XDC_EMS_SERVER_URL = "tibco.be.xdc.ems.server.url";

    public static final String PROP_XDC_RELAY_Q_NAME = "tibco.be.xdc.q.relay";

    public static final String PROP_XDC_COLLECTOR_OR_RECEIVER = "tibco.be.xdc.mode";

    private Common() {
    }

    public static boolean isCollector() {
        return System.getProperty(PROP_XDC_COLLECTOR_OR_RECEIVER, "").trim().equalsIgnoreCase("collector");
    }
    
    public static boolean isReceiver() {
        return System.getProperty(PROP_XDC_COLLECTOR_OR_RECEIVER, "").trim().equalsIgnoreCase("receiver");
    }

    public enum MsgHeaders {
        space_name,
        put_or_take,
        tuple_bytes
    }
}
