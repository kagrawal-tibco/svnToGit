package com.tibco.cep.studio.tester.ui.utils;

/**
 * 
 * @author sasahoo
 *
 */
public class TestDataResource {

	private int row;
	private int col;
	private String table;
	private String colName;
	private Object value;
	
	/**
	 * @param table
	 * @param colName
	 * @param value
	 * @param row
	 * @param col
	 */
	public TestDataResource(String table, String colName,Object value,int row,int col){
		this.table = table;
		this.colName = colName;
		this.value = value;
		this.row = row;
		this.col = col;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String getColName() {
		return colName;
	}

	public void setColName(String colName) {
		this.colName = colName;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
}
