package com.tibco.cep.decision.table.constraintpane;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ColumnFilter {

	private String columnName = null;	
	private boolean isRangeFilter = false;
	private Object[] range = new Object[2];
	private List<Object> items = new ArrayList<Object>();

	public ColumnFilter(String columnName) {
		this.columnName = columnName;
	}
	
	public String getColumnName() {
		return columnName;
	}
	public boolean isRangeFilter() {
		return isRangeFilter;
	}
	public Object[] getRange() {
		return new Object[] {range[0], range[1]};
	}
	public void setRange(Object min, Object max) {
		isRangeFilter = true;		
		range[0] = min;
		range[1] = max;
	}

	public Object[] getItems() {
		return this.items.toArray(new Object[items.size()]);
	}
	public void addItem(Object item) {
		this.items.add(item);
	}
	public void addItems(Collection<Object> items) {
		this.items.addAll(items);
	}
}
