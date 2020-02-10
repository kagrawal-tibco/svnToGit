package com.tibco.cep.query.model;

import com.tibco.cep.query.model.validation.Validatable;

/**
 */
public interface FunctionIdentifier extends Identifier, Validatable {

    /**
     * Returns the array of function argument Expressions from left to right
     * 
     * @return Expression[]
     */
    Expression[] getArguments() throws Exception;

    /**
     * Return the function argument at a specified index
     * 
     * @param index
     * @return Expression
     */
    Expression getArgument(int index) throws Exception;

    /**
     * Returns the number of arguments
     * 
     * @return int
     */
    int getArgumentCount() throws Exception;

}
