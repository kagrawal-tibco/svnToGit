package com.tibco.cep.query.model;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Oct 28, 2007
 * Time: 6:46:00 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ArrayExpression extends BinaryExpression {

    /**
     * Returns the array Identifier
     * @return Identifier
     */
    Expression getArrayIdentifierExpression();

    /**
     * Returns the array index
     * @return int
     */
    Expression getArrayIndexExpression();

    /**
     * 
     * @return Operator
     */
    BinaryOperator getOperator();

}
