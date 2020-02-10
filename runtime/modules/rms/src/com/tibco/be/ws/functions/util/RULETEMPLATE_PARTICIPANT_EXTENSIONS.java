package com.tibco.be.ws.functions.util;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 8/4/12
 * Time: 8:44 AM
 * To change this template use File | Settings | File Templates.
 */
public enum RULETEMPLATE_PARTICIPANT_EXTENSIONS {

    CONCEPT("concept"),

    EVENT("event"),

    TIMEEVENT("time"),

    SCORECARD("scorecard"),

    RULEFUNCTION("rulefunction");

    private String literal;

    private RULETEMPLATE_PARTICIPANT_EXTENSIONS(String literal) {
        this.literal = literal;
    }

    private static final RULETEMPLATE_PARTICIPANT_EXTENSIONS[] VALUES_ARRAY =
        new RULETEMPLATE_PARTICIPANT_EXTENSIONS[] {
             CONCEPT,
             EVENT,
             SCORECARD,
             RULEFUNCTION
        };

    public static RULETEMPLATE_PARTICIPANT_EXTENSIONS getByLiteral(String literal) {
        for (RULETEMPLATE_PARTICIPANT_EXTENSIONS extensions : VALUES_ARRAY) {
            if (extensions.toString().equalsIgnoreCase(literal)) {
                return extensions;
            }
        }
        return CONCEPT;
    }

    public String getLiteral() {
        return literal;
    }

    public String toString() {
        return literal;
    }

}
