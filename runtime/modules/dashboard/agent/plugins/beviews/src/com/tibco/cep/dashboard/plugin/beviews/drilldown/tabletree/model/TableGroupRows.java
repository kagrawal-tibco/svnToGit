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
public class TableGroupRows implements HTMLRenderer {

	private TableTree table;

	protected List groupRows = new LinkedList();

	/**
     *
     */
	public TableGroupRows(TableTree table) {
		this.table = table;
	}

	/**
     *
     */
	public TableGroupRows(TableTree table, List list) {

		this.table = table;

		for (Iterator itList = list.iterator(); itList.hasNext();) {
			Object element = (Object) itList.next();
			if (element instanceof TableGroupRow) {
				addGroupRow((TableGroupRow) element);
			}
		}
	}

	public void clearGroupRows() {
		groupRows.clear();
	}

	public void addGroupRow(TableGroupRow groupRow) {
		groupRows.add(groupRow);
	}

	public void removeGroupRow(TableGroupRow groupRow) {
		groupRows.remove(groupRow);
	}

	public void removeGroupRow(int index) {
		groupRows.remove(index);
	}

	public Iterator iterator() {
		if (groupRows != null) {
			return groupRows.iterator();
		}
		return null;
	}

	public StringBuffer getHTML() {
		StringBuffer buffer = new StringBuffer();
		Iterator iterator = iterator();
		int rowIndex = 0;
		if (iterator != null) {
			while (iterator.hasNext()) {
				TableGroupRow groupRow = (TableGroupRow) iterator.next();
				groupRow.setGroupRowCount(groupRows.size());
				groupRow.setGroupRowIndex(rowIndex);
				buffer.append(groupRow.getHTML());
				rowIndex++;
			}
		}
		return buffer;
	}
}
