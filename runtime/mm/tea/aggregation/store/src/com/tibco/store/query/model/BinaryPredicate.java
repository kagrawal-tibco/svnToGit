package com.tibco.store.query.model;

import com.tibco.store.persistence.model.MemoryTuple;
import com.tibco.store.query.model.impl.SimpleQueryExpression;

public interface BinaryPredicate<T extends MemoryTuple> extends RelationalPredicate {
	
	public BinaryOperator getBinaryOperator();
	
	public SimpleQueryExpression getLeftExpression();

    public QueryExpression getRightExpression();

    public abstract boolean eval(T input);
}
