package com.tibco.cep.webstudio.client.decisiontable;


/**
 * 
 * @author sasahoo
 *
 */
public class DecisionTableRuleError {

	protected String message;
	protected int errorType;
	protected String columnId;
	protected String rowId;
	protected String columnName;

	/**
	 * @param message
	 * @param rowId
	 * @param columnId
	 * @param type
	 * @param columnName
	 */
	public DecisionTableRuleError(String message, String rowId, String columnId, int type, String columnName) {
		this.message = message;
		this.errorType = type;
		this.rowId = rowId;
		this.columnId = columnId;
		this.columnName = columnName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getErrorType() {
		return errorType;
	}

	public void setErrorType(int errorType) {
		this.errorType = errorType;
	}

	public String getColumnId() {
		return columnId;
	}

	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}

	public String getRowId() {
		return rowId;
	}

	public void setRowId(String rowId) {
		this.rowId = rowId;
	}
	
	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
}