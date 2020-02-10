package com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.model;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.util.HTMLRenderer;

/**
 * @author rajesh
 *
 *         To change the template for this generated type comment go to Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class TableRows implements HTMLRenderer {

	private TableTree table;

	protected List rows = new LinkedList();

	/**
     *
     */
	public TableRows(TableTree table) {
		this.table = table;
	}

	/**
     *
     */
	public TableRows(TableTree table, List list) {

		this.table = table;

		for (Iterator itList = list.iterator(); itList.hasNext();) {
			Object element = itList.next();
			if (element instanceof TableRow) {
				addRow((TableRow) element);
			}
		}
	}

	public void clearRows() {
		rows.clear();
	}

	public void addRow(TableRow Row) {
		rows.add(Row);
	}

	public void removeRow(TableRow Row) {
		rows.remove(Row);
	}

	public void removeRow(int index) {
		rows.remove(index);
	}

	public Iterator iterator() {
		if (rows != null) {
			return rows.iterator();
		}
		return null;
	}

	public StringBuffer getHTML() {
		StringBuffer buffer = new StringBuffer();
		Iterator iterator = iterator();
		int rowIndex = table.getStartRowIndex();
		int totalRowCount = table.getStartRowIndex() + rows.size();
		if (iterator != null) {
			while (iterator.hasNext()) {
				TableRow tableRow = (TableRow) iterator.next();
				tableRow.setRowCount(totalRowCount);
				tableRow.setRowIndex(rowIndex);
				buffer.append(tableRow.getHTML());
				rowIndex++;
			}
		}
		return buffer;
	}
}
