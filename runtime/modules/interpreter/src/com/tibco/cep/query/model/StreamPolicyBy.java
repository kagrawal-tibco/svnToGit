package com.tibco.cep.query.model;

/**
 * Represents a BY clause
 */
public interface StreamPolicyBy
    extends QueryContext {


    /**
     * Gets the Identifier's specified in the BY clause.
     * @return Identifier array.
     */
    Expression[] getExpressions();


}
