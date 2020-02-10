package com.tibco.store.query.model;

public interface BinaryExpression {

    /**
     * Every column will be qualified with table name.
     * @return
     */
    public String getTableName();

	//left operand will be string, as it will be columnName
	public String getLeftOperand();
	
//	public Object getRightOperand();
}
