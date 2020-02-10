package com.tibco.cep.decision.table.command.impl;

/**
 * 
 * @author smarathe
 *
 */
public class ModifyCommandExpression {

	protected String expr;

	protected String cellValueExpr;

	protected String[] domainDesc;

	public ModifyCommandExpression(String expr) {
		super();
		this.expr = expr;
	}

	public ModifyCommandExpression(String expr, String cellValue,
			String[] domainDesc) {
		super();
		this.expr = expr;
		this.cellValueExpr = cellValue;
		this.domainDesc = domainDesc;
	}

	public String getExpr() {
		return expr;
	}

	public void setExpr(String expr) {
		this.expr = expr;
	}

	public String getCellValue() {
		return cellValueExpr;
	}

	public void setCellValue(String cellValue) {
		this.cellValueExpr = cellValue;
	}

	public String[] getDomainDesc() {
		return domainDesc;
	}

	public void setDomainDesc(String[] domainDesc) {
		this.domainDesc = domainDesc;
	}

}
