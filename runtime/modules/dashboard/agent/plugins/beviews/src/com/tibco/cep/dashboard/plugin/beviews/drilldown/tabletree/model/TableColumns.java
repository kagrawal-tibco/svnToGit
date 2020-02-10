package com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.model;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.util.HTMLRenderer;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.util.TableTreeUtils;

/**
 * @author rajesh
 *
 *         To change the template for this generated type comment go to Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class TableColumns implements HTMLRenderer {

	public static final String ID_PREFIX = "tableTree_COLGROUP";
	private TableTree table;

	protected List columns = new LinkedList();

	/**
     *
     */
	/*
	 * public TableColumns(int numColumns) { for (int i=0; i< numColumns; i++) { addColumn(new TableColumn(table,i)); } }
	 *
	 * /**
	 */

	public TableColumns(TableTree tableTree) {
		table = tableTree;
	}

	/**
     *
     */
	public TableColumns(TableTree table, List list) {

		this.table = table;
		int index = 0;
		for (Iterator itList = list.iterator(); itList.hasNext(); index++) {
			Object element = (Object) itList.next();
			if (element instanceof TableColumn) {
				addColumn((TableColumn) element);
			} else {
				if (element == null) {
					addColumn(new TableColumn(table, index));
				} else {
					addColumn(new TableColumn(table, index, element.toString()));
				}
			}
		}
	}

	public void clearColumns() {
		columns.clear();
	}

	public void addColumn(TableColumn Column) {
		columns.add(Column);
	}

	public TableColumn get(int colIndex) {
		return (TableColumn) columns.get(colIndex);
	}

	public void removeColumn(TableColumn Column) {
		columns.remove(Column);
	}

	public void removeColumn(int index) {
		columns.remove(index);
	}

	public Iterator iterator() {
		if (columns != null) {
			return columns.iterator();
		}
		return null;
	}

	public StringBuffer getHTML() {
		StringBuffer buffer = new StringBuffer();
		// render(buffer);
		return buffer;
	}

	public String getId() {
		return table.computeId(ID_PREFIX);
	}

	/**
	 * @param buffer
	 */

	private void render(StringBuffer buffer) {
		buffer.append("<COLGROUP ");
		TableTreeUtils.outAttribute(buffer, "ID", getId());
		buffer.append(">");
		renderDefaultColumns(buffer);
		Iterator iterator = iterator();
		if (iterator != null) {
			while (iterator.hasNext()) {
				TableColumn tableColumn = (TableColumn) iterator.next();
				buffer.append(tableColumn.getHTML());
			}
		}
		buffer.append("</COLGROUP>");
		return;
	}

	private void renderDefaultColumns(StringBuffer buffer) {
		buffer.append("<COL class='expandCollapseColumn'>");
		buffer.append("<COL class='rowheaderColumn'>");
	}
}
