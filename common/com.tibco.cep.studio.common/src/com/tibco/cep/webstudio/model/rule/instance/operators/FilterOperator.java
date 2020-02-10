package com.tibco.cep.webstudio.model.rule.instance.operators;

import java.util.HashMap;
import java.util.Map;


public enum FilterOperator implements IBuilderOperator {

    EQUALS("equals", "=="),

    NOT_EQUALS("not equal", "!="),

    EQUALS_IGNORE_CASE("equals (ignore case)"),

    NOT_EQUALS_IGNORE_CASE("not equal (ignore case)"),

    BETWEEN("between"),

    BETWEEN_INCLUSIVE("between (inclusive)"),

    CONTAINS("contains"),

    STARTS_WITH("starts with"),

    ENDS_WITH("ends with"),

    DOES_NOT_CONTAIN("does not contain"),

    LESS_THAN("less than", "<"),

    GREATER_THAN("greater than", ">"),

    IS_NULL("is null"),

    IS_NOT_NULL("is not null"),

    MATCHES_OTHER_FIELD("matches other field"),

    DIFFERS_FROM_FIELD("differs from field"),

    GREATER_THAN_FIELD("greater than field"),

    LESS_THAN_FIELD("less than field"),
    
    GREATER_THAN_OR_EQUAL_TO("greater than or equal to"),

    GREATER_THAN_OR_EQUAL_TO_FIELD("greater than or equal to field"),

    LESS_THAN_OR_EQUAL_TO("less than or equal to"),
    
    LESS_THAN_OR_EQUAL_TO_FIELD("less than or equal to field"),
    
    EQUALS_TRUE("is true"),
    
    EQUALS_FALSE("is false");
    
    private static Map<String, FilterOperator> valueToEnum = new HashMap<String, FilterOperator>();
    static {
        for (final FilterOperator fo : FilterOperator.values()) {
            for (final String v : fo.values) {
                valueToEnum.put(v, fo);
            }
        }
    }


    private String[] values;


    FilterOperator(String... values) {
        if (values.length < 1) {
            throw new IllegalArgumentException();
        }
        this.values = values;
    }

    public static FilterOperator fromValue(String value) {
        return valueToEnum.get(value);
    }


    public String getValue() {
        return this.values[0];
    }

}

