package com.tibco.cep.query.stream._join_.index;

/*
* Author: Ashwin Jayaprakash Date: Aug 4, 2008 Time: 2:46:28 PM
*/

public enum Operator {
    ALL,

    EQUALS,
    NOT_EQUALS,

    LESS_THAN,
    LESS_THAN_EQUALS,

    GREATER_THAN,
    GREATER_THAN_EQUALS;

    //--------

    public static Operator findReverse(Operator operator) {
        switch (operator) {
            case ALL:
                return ALL;

            case EQUALS:
                return EQUALS;
            case NOT_EQUALS:
                return NOT_EQUALS;

            case LESS_THAN:
                return GREATER_THAN;

            case LESS_THAN_EQUALS:
                return GREATER_THAN_EQUALS;

            case GREATER_THAN:
                return LESS_THAN;

            case GREATER_THAN_EQUALS:
                return LESS_THAN_EQUALS;
        }

        throw new UnsupportedOperationException(operator.name());
    }
}
