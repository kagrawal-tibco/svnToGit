package com.tibco.cep.studio.ui.editors.domain.data;

import java.util.List;

/**
 * 
 * @author smarathe
 *
 */

public class DBTreeColumns {

	private List<DBTreeValues> valuesList;
	private String columnName;
	private String tableName;
	private DBTreeTables parent;
	
	public DBTreeTables getParent() {
		return parent;
	}

	public void setParent(DBTreeTables parent) {
		this.parent = parent;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public DBTreeColumns() {
		
	}
	
	public DBTreeColumns(String columnName, String tableName) {
		this.columnName = columnName;
		this.tableName = tableName;
	}
	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public List<DBTreeValues> getValuesList() {
		return valuesList;
	}

	public void setValuesList(List<DBTreeValues> valuesList) {
		this.valuesList = valuesList;
	}
	
}
