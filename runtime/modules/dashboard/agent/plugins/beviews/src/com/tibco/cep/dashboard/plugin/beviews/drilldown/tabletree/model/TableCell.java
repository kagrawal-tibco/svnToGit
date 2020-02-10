package com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.integration.DefaultTableCellModel;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.integration.TableCellModel;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.util.HTMLRenderer;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.util.HTMLTag;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.util.JSFunction;

/**
 * @author rajesh
 *
 *         To change the template for this generated type comment go to Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class TableCell implements HTMLRenderer {

	private int collIndex;
	private int rowIndex;
	private TableTree table;
	private TableCellModel userObject = null;
	private String style;
	private Map attributes;
	private TableCellRenderer cellRenderer = null;

	/**
	 * @param table2
	 * @param cellValueAt
	 * @param rowIndex2
	 * @param colIndex
	 */
	public TableCell(TableTree table, Object cellValue, int rowIndex, int colIndex) {

		// this(table, cellValue.toString(), rowIndex, colIndex);
		if (cellValue instanceof TableCellModel) {
			this.userObject = (TableCellModel) cellValue;
		} else {
			this.userObject = new DefaultTableCellModel(cellValue);
		}
		this.table = table;
		this.rowIndex = rowIndex;
		this.collIndex = colIndex;
	}

	public StringBuffer getHTML() {
		StringBuffer buffer = new StringBuffer();
		renderTextCell(buffer);
		return buffer;
	}

	private void renderTextCell(StringBuffer buffer) {
		HTMLTag tagCell = new HTMLTag("td");
		tagCell.setId(computeId());

		String cellStyle = getStyle();
		if (cellStyle != null) {
			tagCell.setStyleClass(cellStyle);
		}

		tagCell.addAttributes(getAttributes());
		tagCell.addAttribute("title", userObject.getTooltip());

		tagCell.addAttribute("nowrap");

		JSFunction cellClick = new JSFunction("cell_Clicked");
		cellClick.setReturn(false);
		cellClick.addStringParam(table.getTableTreeName());
		cellClick.addStringParam(table.getTablePath());
		cellClick.addStringParam(rowIndex);
		cellClick.addStringParam(collIndex);
		tagCell.addAttribute("onclick", cellClick.toString());

		// PATCH I am doing a hard code conversion of a date to long form since that is safest to submit
		// Anand - CHANGED on 10/14/04 to support flawless conversion to date across any formatting START
		Object cellValue = userObject.getActualValue();
		if (cellValue instanceof Date) {
			cellValue = new Long(((Date) cellValue).getTime());
		}
		tagCell.addCustomAttribute("cellvalue", String.valueOf(cellValue));
		// Anand - CHANGED on 10/14/04 to support flawless conversion to date across any formatting END

		TableColumn tableColumn = table.getColumn(collIndex);
		tagCell.addAttribute("ALIGN", tableColumn.getTextAlign());
		TableCellRenderer cellRenderer = getCellRenderer();

		String cellContent = null;
		if (cellRenderer != null) {
			cellContent = cellRenderer.getHTML(userObject).toString();
		} else {
			cellContent = userObject.getDisplayValue().toString();
		}

		HTMLTag linkTag = getLinkTag();
		if (linkTag != null) {
			linkTag.setContent(cellContent);
			cellContent = linkTag.toString();
		}
		tagCell.setContent(cellContent);
		buffer.append(tagCell.toString());
	}

	private HTMLTag getLinkTag() {
		String drillableLink = userObject.getDrillableLink();
		if (drillableLink == null || drillableLink.trim().length() == 0) {
			return null;
		}
		HTMLTag linkTag = new HTMLTag("A");
		linkTag.addAttribute("href", drillableLink);
		// INFO added by Anand ono 04/26/2010 to avoid causing javascript links to launch in new window
		if (drillableLink.startsWith("javascript:") == false) {
			linkTag.addAttribute("target", "_blank");
		}
		return linkTag;
	}

	/**
	 * @return
	 */
	private TableCellRenderer getCellRenderer() {
		if (cellRenderer != null) {
			return cellRenderer;
		}

		TableCellRenderer columnRenderer = table.getColumn(collIndex).getCellRenderer();
		if (columnRenderer != null) {
			return columnRenderer;
		}

		return table.getCellRenderer();
	}

	/**
	 * @param buffer
	 */
	/*
	 * private void attachTableColumnAttributes(StringBuffer buffer) { TableColumn tableColumn = table.getColumn(collIndex); TableTreeUtils.outAttribute(buffer, "ALIGN", tableColumn.getTextAlign()); }
	 */

	/**
	 * @param string
	 */
	public void setRowIndex(int index) {
		rowIndex = index;
	}

	/**
	 * @param i
	 */
	public void setCellIndex(int i) {
		collIndex = i;
	}

	private String computeId() {
		String id = table.computeId("tableTree_Cell", rowIndex);
		;
		return id + TableTree.PATH_SEPARATOR + collIndex;
	}

	/**
	 * @return
	 */
	public String getStyle() {
		if (style == null) {
			return table.getColumn(collIndex).getStyle();
		}
		return style;
	}

	/**
	 * @param string
	 */
	public void setStyle(String string) {
		style = string;
	}

	public Map getAttributes() {
		return attributes;
	}

	public void setAttributes(Map attributes) {
		this.attributes = attributes;
	}

	public void addAttribute(String name, String value) {
		if (attributes == null) {
			attributes = new HashMap();
		}
		attributes.put(name, value);
	}

	/**
	 * @param cellRenderer
	 */
	public void setCellRenderer(TableCellRenderer cellRenderer) {
		this.cellRenderer = cellRenderer;
	}

}
