package com.tibco.be.ws.functions.util;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 24/5/12
 * Time: 3:12 PM
 * To change this template use File | Settings | File Templates.
 */
public enum CONCEPT_ATTR_NAMES {

    ATTR_ID("id"),

    ATTR_EXTID("extId"),

    ATTR_PARENT("parent"),

    ATTR_LENGTH("length");

    private String literal;

    private CONCEPT_ATTR_NAMES(String literal) {
        this.literal = literal;
    }

    private static final CONCEPT_ATTR_NAMES[] VALUES_ARRAY =
        new CONCEPT_ATTR_NAMES[] {
                ATTR_ID,
                ATTR_EXTID,
                ATTR_PARENT,
                ATTR_LENGTH
        };

    public static CONCEPT_ATTR_NAMES getByLiteral(String literal) {
        for (CONCEPT_ATTR_NAMES extensions : VALUES_ARRAY) {
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
