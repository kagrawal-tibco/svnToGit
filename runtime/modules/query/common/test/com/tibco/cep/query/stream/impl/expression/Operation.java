package com.tibco.cep.query.stream.impl.expression;

/*
* Author: Ashwin Jayaprakash Date: Jun 9, 2008 Time: 5:07:56 PM
*/
public enum Operation {
    ADD(false),
    SUBTRACT(false),
    DIVIDE(false),
    MULTIPLY(false),

    GREATER(true),
    GREATER_OR_EQUAL(true),
    EQUAL(true),
    NOT_EQUAL(true),
    LESS(true),
    LESS_OR_EQUAL(true);

    //---------

    private static final long serialVersionUID = 1L;

    boolean comparator;

    Operation(boolean comparator) {
        this.comparator = comparator;
    }

    public boolean isComparator() {
        return comparator;
    }
}
