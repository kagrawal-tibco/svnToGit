/**
 * 
 */
package com.tibco.store.query.model.impl;

import com.tibco.store.persistence.model.MemoryTuple;
import com.tibco.store.query.model.BinaryOperator;
import com.tibco.store.query.model.QueryExpression;

/**
 * @author ssinghal
 *
 */
public class NEqualsPredicate extends EqualsPredicate{
	
	 public NEqualsPredicate(SimpleQueryExpression leftExpression,
             QueryExpression rightExpression,
             BinaryOperator op) {
				super(leftExpression, rightExpression, op);
				if (op != BinaryOperator.NEQ) {
				 throw new IllegalArgumentException("Only NEQ operator allowed");
			}
	 	}

	@Override
	public boolean eval(MemoryTuple input) {
		return !super.eval(input);
	}
	
	@Override
	public String toString() {
		String pattern = "( %s != %s )";
		return toString(pattern);
	}
	
}
