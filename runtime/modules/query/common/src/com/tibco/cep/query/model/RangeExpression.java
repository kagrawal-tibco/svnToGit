package com.tibco.cep.query.model;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Oct 17, 2007
 * Time: 5:51:50 PM
 * To change this template use File | Settings | File Templates.
 */
public interface RangeExpression extends BinaryExpression {

    /**
     * Returns the lower boundary expression for the range
     * @return Expression
     */
    Expression  getLowerBound();

    /**
     * Returns the upper boundary expression for the range
     * @return Expression
     */
    Expression  getUpperBound();
}
