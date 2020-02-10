package com.tibco.store.query.model.impl;

import com.tibco.store.query.model.BinaryExpression;

public class BinaryExpressionImpl implements BinaryExpression {

    private String tableName;

	private String leftOperand;

	public BinaryExpressionImpl(String tableName,
                                String leftOperand) {
        if (tableName == null) {
            throw new IllegalArgumentException("Cannot have operand qualified without table name");
        }
        this.tableName = tableName;
		this.leftOperand = leftOperand;
	}

    @Override
    public String getTableName() {
        return tableName;
    }

    @Override
	public String getLeftOperand() {
		return leftOperand;
	}
}
