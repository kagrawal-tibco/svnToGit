package com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.model;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.util.HTMLRenderer;

/**
 * @author rajesh
 *
 */
public class TableCells implements HTMLRenderer {

	private TableTree table;

	private int rowIndex;

	protected List cells = new LinkedList();

	/**
     *
     */
	public TableCells() {
		super();
	}

	/**
     *
     */
	public TableCells(TableTree table, List list, int rowIndex) {

		this.table = table;
		int index = 0;
		for (Iterator itList = list.iterator(); itList.hasNext(); index++) {
			Object element = itList.next();
			if (element instanceof TableCell) {
				TableCell cell = (TableCell) element;
				addCell(cell);
			} else {
				if (element == null) {
					addCell(new TableCell(table, "nullCell", rowIndex, index));
				} else {
					addCell(new TableCell(table, element.toString(), rowIndex, index));
				}
			}
		}
	}

	/**
	 * @param table
	 * @param rowIndex
	 */
	public TableCells(TableTree table, int rowIndex) {
		this.table = table;
		this.rowIndex = rowIndex;
	}

	public void clearCells() {
		cells.clear();
	}

	public void addCell(TableCell cell) {
		cells.add(cell);
	}

	public void removeCell(TableCell cell) {
		cells.remove(cell);
	}

	public void removeCell(int index) {
		cells.remove(index);
	}

	public Iterator iterator() {
		if (cells != null) {
			return cells.iterator();
		}
		return null;
	}

	public StringBuffer getHTML() {
		StringBuffer buffer = new StringBuffer();
		Iterator iterator = iterator();
		int cellIndex = 0;
		if (iterator != null) {
			while (iterator.hasNext()) {
				TableCell tableCell = (TableCell) iterator.next();
				tableCell.setRowIndex(rowIndex);
				tableCell.setCellIndex(cellIndex++);
				buffer.append(tableCell.getHTML());
			}
		}
		return buffer;
	}

	/**
	 * @param rowId
	 */
	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}

}
