package com.tibco.store.query.model.impl;

import com.tibco.store.query.model.QueryExpression;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 25/12/13
 * Time: 12:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class SimpleQueryExpression implements QueryExpression {

    private String tableName;

    private String operand;

    public SimpleQueryExpression(String tableName, String operand) {
        this.tableName = tableName;
        this.operand = operand;
    }

    public String getTableName() {
        return tableName;
    }

    public String getOperand() {
        return operand;
    }

    @Override
    public String toString() {
        return tableName + "." + operand;
    }
}
