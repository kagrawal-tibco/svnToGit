package com.tibco.cep.studio.ui.editors.domain.data;

import java.util.List;

/**
 * 
 * @author smarathe
 *
 */

public class DBTreeTables {

	public DBTreeTables() {
		
	}
	
	public DBTreeTables(String tableName) {
		this.tableName = tableName;
	}
	

	public DBTreeTables(String tableName, List<DBTreeColumns> columnList) {
		this.tableName = tableName;
		this.columnList = columnList;
	}
	
	private List<DBTreeColumns> columnList;
	private String tableName;
	
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public List<DBTreeColumns> getColumnList() {
		return columnList;
	}

	public void setColumnList(List<DBTreeColumns> columnList) {
		this.columnList = columnList;
	}
	
}
