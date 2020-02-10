package com.tibco.be.ws.functions.util;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 24/5/12
 * Time: 3:14 PM
 * To change this template use File | Settings | File Templates.
 */
public enum EVENT_ATTR_NAMES {

    ATTR_ID("id"),

    ATTR_EXTID("extId"),

    ATTR_TTL("ttl"),

    ATTR_PAYLOAD("payload");

    private String literal;

    private EVENT_ATTR_NAMES(String literal) {
        this.literal = literal;
    }

    private static final EVENT_ATTR_NAMES[] VALUES_ARRAY =
        new EVENT_ATTR_NAMES[] {
                ATTR_ID,
                ATTR_EXTID,
                ATTR_TTL,
                ATTR_PAYLOAD
        };

    public static EVENT_ATTR_NAMES getByLiteral(String literal) {
        for (EVENT_ATTR_NAMES extensions : VALUES_ARRAY) {
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
}
