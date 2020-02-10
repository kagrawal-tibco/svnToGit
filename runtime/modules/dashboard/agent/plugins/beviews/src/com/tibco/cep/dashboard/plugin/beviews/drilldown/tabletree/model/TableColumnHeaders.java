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
public class TableColumnHeaders implements HTMLRenderer {
	private TableTree table;

	private String unSortedStyle = "normalColumnStyle";
	private String ascendingStyle = "ascendingColumnStyle";
	private String descendingStyle = "descendingColumnStyle";

	private String ascendingImage;
	private String descendingImage;
	private String unSortedImage;

	protected List columnHeaders = new LinkedList();

	/**
     *
     */
	public TableColumnHeaders(TableTree table) {
		this.table = table;
		initImages();
	}

	/**
     *
     */
	private void initImages() {
		ascendingImage = table.getImagePath() + "/up_arrow_gray.gif";
		descendingImage = table.getImagePath() + "/dwn_arrow_gray.gif";
		unSortedImage = table.getImagePath() + "/no_arrow_gray.gif";
	}

	public TableColumnHeaders(TableTree table, List list) {
		super();
		this.table = table;
		initImages();
		if (list != null) {
			int index = 0;
			TableColumnHeader tableColumnHeader;
			for (Iterator iterator = list.iterator(); iterator.hasNext(); index++) {
				Object element = iterator.next();
				if (element instanceof TableColumnHeader) {
					tableColumnHeader = (TableColumnHeader) element;
					tableColumnHeader.setIndex(index);
				} else {
					tableColumnHeader = new TableColumnHeader(table, index);
					tableColumnHeader.setText(element.toString());
				}
				addColumn(tableColumnHeader);
			}
		}
	}

	public void clearColumns() {
		columnHeaders.clear();
	}

	public void addColumn(TableColumnHeader columnHeader, int index) {
		columnHeaders.add(index, columnHeader);
		columnHeader.setIndex(index);
	}

	public void addColumn(TableColumnHeader columnHeader) {
		columnHeaders.add(columnHeader);
		columnHeader.setIndex(columnHeaders.size() - 1);
	}

	public void removeColumn(TableColumnHeader columnHeader) {
		columnHeaders.remove(columnHeader);
	}

	public void removeColumn(int index) {
		columnHeaders.remove(index);
	}

	public Iterator iterator() {
		if (columnHeaders != null) {
			return columnHeaders.iterator();
		}
		return null;
	}

	public StringBuffer getHTML() {
		StringBuffer buffer = new StringBuffer();

		Iterator iterator = iterator();
		if (iterator != null) {
			while (iterator.hasNext()) {
				TableColumnHeader columnHeader = (TableColumnHeader) iterator.next();
				buffer.append(columnHeader.getHTML());
			}
		}
		return buffer;
	}

	public String getAscendingImage() {
		return ascendingImage;
	}

	public void setAscendingImage(String ascendingImage) {
		this.ascendingImage = ascendingImage;
	}

	public String getAscendingStyle() {
		return ascendingStyle;
	}

	public void setAscendingStyle(String ascendingStyle) {
		this.ascendingStyle = ascendingStyle;
	}

	public List getColumnHeaders() {
		return columnHeaders;
	}

	public void setColumnHeaders(List columnHeaders) {
		this.columnHeaders = columnHeaders;
	}

	public String getDescendingImage() {
		return descendingImage;
	}

	public void setDescendingImage(String descendingImage) {
		this.descendingImage = descendingImage;
	}

	public String getDescendingStyle() {
		return descendingStyle;
	}

	public void setDescendingStyle(String descendingStyle) {
		this.descendingStyle = descendingStyle;
	}

	public String getUnSortedImage() {
		return unSortedImage;
	}

	public void setUnSortedImage(String unSortedImage) {
		this.unSortedImage = unSortedImage;
	}

	public String getUnSortedStyle() {
		return unSortedStyle;
	}

	public void setUnSortedStyle(String unSortedStyle) {
		this.unSortedStyle = unSortedStyle;
	}

}
