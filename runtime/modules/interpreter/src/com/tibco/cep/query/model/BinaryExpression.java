/**
 * 
 */
package com.tibco.cep.query.model;

import com.tibco.cep.query.model.validation.Validatable;

/**
 * @author pdhar
 *
 */
public interface BinaryExpression extends Expression, Validatable {
	
	public static long validReturnTypeMasks = UnaryExpression.validReturnTypeMasks;
	
	/**
	 * @return Operator the Operator Context
	 */
	BinaryOperator getOperator();
	
	/**
	 * Get the left expression
	 * @return Expression 
	 */
	Expression getLeftExpression();
	
	/**
	 * @return RightExpressionContext the right hand side of the operator context
	 */
	Expression getRightExpression();
	


}
