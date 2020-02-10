package com.tibco.cep.webstudio.client.decisiontable.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 
 * @author sasahoo
 *
 */
public class TableRuleSet {

	private Table table;
	private String tableType;
	private List<TableColumn> columnList;
	private List<TableRule> tableRules;
	private long lastRow = -1;
	private int pages = 1;
	private int currentPage = 1;
	private boolean showAll = false;
	
	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public long getLastRow() {
		return lastRow;
	}

	public void setLastRow(long lastRow) {
		this.lastRow = lastRow;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	/**
	 * @param table
	 * @param tableType
	 */
	public TableRuleSet(Table table, String tableType) {
		this.table = table;
		this.tableType = tableType;
	}

	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}

	public List<TableColumn> getColumns() {
		return columnList;
	}

	public void setColumns(List<TableColumn> columnList) {
		this.columnList = columnList;
	}

	public String getTableType() {
		return tableType;
	}

	public void setTableType(String tableType) {
		this.tableType = tableType;
	}
	
	public List<TableRule> getTableRules() {
		return tableRules;
	}

	public void setTableRules(List<TableRule> tableRules) {
		this.tableRules = tableRules;
	}

	public boolean isShowAll() {
		return showAll;
	}

	public void setShowAll(boolean showAll) {
		this.showAll = showAll;
	}
	
	public void addColumn(TableColumn column) {
		ColumnType columnType = ColumnType.get(column.getColumnType());
		switch(columnType) {
		case CONDITION:
		case CUSTOM_CONDITION:
			List<TableColumn> condColumns = getConditionColumns();
			this.columnList.add(condColumns.size(), column);			
			break;
		case ACTION:
		case CUSTOM_ACTION:
			this.columnList.add(column);
			break;
		}
	}
	
	public List<TableColumn> getConditionColumns() {
		List<TableColumn> condColumns = new ArrayList<TableColumn>();
		if (columnList != null) {
			Iterator<TableColumn> itr = columnList.iterator();
			while (itr.hasNext()) {
				TableColumn column = itr.next();
				ColumnType columnType = ColumnType.get(column.getColumnType());
				if (ColumnType.CONDITION.equals(columnType) ||  ColumnType.CUSTOM_CONDITION.equals(columnType)) {
					condColumns.add(column);
				}
			}
		}
		return condColumns;
	}

	public List<TableColumn> getActionColumns() {
		List<TableColumn> actColumns = new ArrayList<TableColumn>();
		if (columnList != null) {
			Iterator<TableColumn> itr = columnList.iterator();
			while (itr.hasNext()) {
				TableColumn column = itr.next();
				ColumnType columnType = ColumnType.get(column.getColumnType());
				if (ColumnType.ACTION.equals(columnType) ||  ColumnType.CUSTOM_ACTION.equals(columnType)) {
					actColumns.add(column);
				}
			}
		}
		return actColumns;
	}

}
