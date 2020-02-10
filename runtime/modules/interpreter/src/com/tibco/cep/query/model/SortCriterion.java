package com.tibco.cep.query.model;

/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Oct 22, 2007
 * Time: 4:36:00 PM
 * To change this template use File | Settings | File Templates.
 */
public interface SortCriterion extends QueryContext {

    enum Direction {
        ASC,
        DESC,
    }

    /**
     * @return Direction or null;
     */
    Direction getDirection();


    /**
     * @return Expression part of the sort criterion.
     */
    Expression getExpression();


    /**
     * @return Bindvariable or Integer between 1 and Integer.MAX_VALUE
     */
    Object getLimitFirst();


    /**
     * @return Bindvariable or Integer between 1 and Integer.MAX_VALUE
     */
    Object getLimitOffset();

    /**
     * @return true iif this object uses a non-default value in one of the Limit parameters
     */
    boolean usesNonDefaultLimit();

}
