package com.tibco.cep.studio.ui.editors.domain.data;


/**
 * 
 * @author smarathe
 *
 */
public class DBTreeValues {
	
	Object value;
	String tableName;
	String columnName;
	
	public DBTreeValues(Object value, String tableName,String columnName) {
		this.value = value;
		this.tableName = tableName;
		this.columnName = columnName;
	}
	
	
	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

}
