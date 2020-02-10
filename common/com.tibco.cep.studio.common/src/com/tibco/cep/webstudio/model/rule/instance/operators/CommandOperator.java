package com.tibco.cep.webstudio.model.rule.instance.operators;


import java.util.HashMap;
import java.util.Map;


public enum CommandOperator implements IBuilderOperator {

    SET_TO("set to"),

    SET_TO_FIELD("set to field"),

    INCREMENT_BY("increment by"),

    INCREMENT_BY_FIELD("increment by field"),

    DECREMENT_BY("decrement by"),

    DECREMENT_BY_FIELD("decrement by field"),

    SET_TO_NULL("set to null"),

    SET_TO_TRUE("set to true"),

    SET_TO_FALSE("set to false");

/*    SET_TO_DATE("set to date");*/

    private static Map<String, CommandOperator> valueToEnum = new HashMap<String, CommandOperator>();
    
    static {
        for (final CommandOperator op : CommandOperator.values()) {
            for (final String value : op.values) {
                valueToEnum.put(value, op);
            }
        }
    }


    private String[] values;

    CommandOperator(String... values) {
        if (values.length < 1) {
            throw new IllegalArgumentException();
        }
        this.values = values;
    }

    public static CommandOperator fromValue(String value) {
        return valueToEnum.get(value);
    }


    public String getValue() {
        return this.values[0];
    }

}
