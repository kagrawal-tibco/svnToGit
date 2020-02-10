package com.tibco.store.query.model.impl;

import com.tibco.store.query.exec.PredicateTreeVisitor;
import com.tibco.store.query.model.UnaryExpression;
import com.tibco.store.query.model.UnaryOperator;
import com.tibco.store.query.model.UnaryPredicate;

public class UnaryPredicateImpl implements UnaryPredicate {

	protected UnaryExpression exp;

    protected UnaryOperator op;

	protected UnaryPredicateImpl(UnaryExpression exp, UnaryOperator op) {
		this.exp = exp;
		this.op = op;
	}

	@Override
	public <V extends PredicateTreeVisitor> void accept(V predicateTreeVisitor) {
		predicateTreeVisitor.visit(this);
	}

	@Override
	public UnaryOperator getOperator() {		
		return op;
	}

	@Override
	public UnaryExpression getExpression() {
		return exp;
	}

}
