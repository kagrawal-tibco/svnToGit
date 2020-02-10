package com.tibco.be.ws.functions.util;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 8/6/12
 * Time: 11:05 AM
 * To change this template use File | Settings | File Templates.
 */
public enum TIMEEVENT_ATTR_NAMES {

    ATTR_ID("id", "long"),

    ATTR_TTL("ttl", "long"),

    ATTR_CLOSURE("closure", "String"),

    ATTR_INTERVAL("interval", "long"),

    ATTR_SCHEDULED_TIME("scheduledTime", "DateTime");

    private String literal;
    
    private String dataType;

    private TIMEEVENT_ATTR_NAMES(String literal, String dataType) {
        this.literal = literal;
        this.dataType = dataType;
    }

    private static final TIMEEVENT_ATTR_NAMES[] VALUES_ARRAY =
            new TIMEEVENT_ATTR_NAMES[]{
                    ATTR_ID,
                    ATTR_CLOSURE,
                    ATTR_TTL,
                    ATTR_INTERVAL,
                    ATTR_SCHEDULED_TIME
            };

    public static TIMEEVENT_ATTR_NAMES getByLiteral(String literal) {
        for (TIMEEVENT_ATTR_NAMES extensions : VALUES_ARRAY) {
            if (extensions.toString().equalsIgnoreCase(literal)) {
                return extensions;
            }
        }
        return null;
    }

    public String getLiteral() {
        return literal;
    }

    public String toString() {
        return literal;
    }

    public String getDataType() {
        return dataType;
    }
}
