/**
 * 
 */
package com.tibco.cep.query.model;

import com.tibco.cep.query.model.impl.TypeInfoImpl;
import com.tibco.cep.query.model.validation.Validatable;


/**
 * @author pdhar
 *
 */
public interface UnaryExpression extends Expression, Validatable {
	
	public static long validReturnTypeMasks=  	Expression.validReturnTypeMasks
												| TypeInfoImpl.TYPE_CHAR
												| TypeInfoImpl.TYPE_STRING
												| TypeInfoImpl.TYPE_BOOLEAN
												| TypeInfoImpl.TYPE_LONG
												| TypeInfoImpl.TYPE_DOUBLE
												| TypeInfoImpl.TYPE_DATETIME;
	
    /**
     * @return UnaryOperator the Operator for the unary expression
     */
    UnaryOperator getOperator();


    /**
     * @return Expression argument that the UnaryOperator is applied to.
     */
    Expression getArgument();





}
