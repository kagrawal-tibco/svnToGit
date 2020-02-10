package com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.model;

import java.util.HashMap;

import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.util.ExpandCollapseHandle;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.util.HTMLRenderer;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.util.HTMLTag;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.util.JSFunction;

/**
 * @author rajesh
 *
 *         To change the template for this generated type comment go to Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class TableRow implements HTMLRenderer {

	private static final String attPrefix = "c_";
	private static final String ID_PREFIX = "tableTree_Row";
	private static final String CELL_ROW_HDR_ID_PREFIX = ID_PREFIX + "_Header_Cell";
	private static final String CELL_EX_COLL_ID_PREFIX = ID_PREFIX + "_ExCol_Cell";

	private TableTree table;
	private ExpandCollapseHandle expandCollapseHandle;
	private TableRowHeader tableRowHeader;
	private TableCells tableCells;
	private Object rowParam;

	private int rowCount;
	private int rowIndex;

	private HashMap mapAttributes = new HashMap();

	/**
	 * @param vecTableCells
	 */
	public TableRow(TableTree table, ExpandCollapseHandle expandCollapseHandle, TableRowHeader tableRowHeader, TableCells tableCells, Object rowParam) {
		this.table = table;
		this.expandCollapseHandle = expandCollapseHandle;
		this.tableRowHeader = tableRowHeader;
		this.tableCells = tableCells;
		this.rowParam = rowParam;
	}

	/**
	 * @param vecTableCells
	 */
	public TableRow(TableTree table, ExpandCollapseHandle expandCollapseHandle, TableRowHeader tableRowHeader, TableCells tableCells) {
		this(table, expandCollapseHandle, tableRowHeader, tableCells, null);
	}

	/**
     *
     */
	public TableRow() {
		super();
	}

	public StringBuffer getHTML() {
		StringBuffer buffer = new StringBuffer();
		render(buffer);
		return buffer;
	}

	/**
     *
     */
	private void render(StringBuffer buffer) {
		HTMLTag tagTR = new HTMLTag("tr");
		tagTR.setId(table.computeId(ID_PREFIX, rowIndex));
		if (table.isAlternateRowStyle()) {
			if (rowIndex % 2 == 0) {
				tagTR.setStyleClass(table.getEvenRowStyle());
			} else {
				tagTR.setStyleClass(table.getOddRowStyle());
			}
		} else {
			if (table.getRowStyle() != null) {
				tagTR.setStyleClass(table.getRowStyle());
			}
		}

		tagTR.addCustomAttribute("loaded", "false");
		tagTR.addCustomAttribute("expanded", "false");
		tagTR.addCustomAttribute("type", "row");
		tagTR.addAttributes(mapAttributes);

		renderCells(tagTR);

		tagTR.toStringTagStart(buffer);
		tagTR.toStringTagChilds(buffer);
		outTableCells(buffer);

		if (table.getWidthType() == TableTree.WIDTH_NATURAL) {
			table.addRightFillerCell(buffer);
		}
		tagTR.toStringTagEnd(buffer);
		return;
	}

	/**
	 * @param tagTR
	 */
	private void renderCells(HTMLTag tagTR) {

		if (table.isExpandCollapseCell()) {
			HTMLTag tagTDExColl = tagTR.addTag("td");
			tagTDExColl.setId(table.computeId(CELL_EX_COLL_ID_PREFIX, rowIndex));
			tagTDExColl.setStyleClass("cellExColl");
			tagTDExColl.addStyle("background-color", table.getBackgroundColor());
			tagTDExColl.setContent(getExpandCollapseHandler());
		}

		HTMLTag tagTDRowHeader = tagTR.addTag("td");
		tagTDRowHeader.setId(table.computeId(CELL_ROW_HDR_ID_PREFIX, rowIndex));
		tagTDRowHeader.setStyleClass("rowHeader");
		if (tableRowHeader != null) {
			tableRowHeader.setRowIndex(rowIndex);
			tagTDRowHeader.setContent(tableRowHeader.getHTML().toString());
		}

	}

	/**
	 * @param buffer
	 * @param httpServletRequest
	 */
	private String getExpandCollapseHandler() {
		if (expandCollapseHandle != null) {
			expandCollapseHandle.setRowIndex(rowIndex);
			expandCollapseHandle.setRowParam(rowParam);
			// expandCollapseHandle.setFirst(rowIndex == 0);
			expandCollapseHandle.setFirst(false);
			expandCollapseHandle.setLast(rowIndex == rowCount - 1);
			expandCollapseHandle.setSubPath(String.valueOf(rowIndex));
			expandCollapseHandle.setOnClick(getExColOnClickHandler());
			return expandCollapseHandle.getHTML().toString();
		}
		return "";
	}

	private String getExColOnClickHandler() {
		JSFunction onClickFunction = new JSFunction("expandCollapseTree");
		onClickFunction.setReturn(true);
		onClickFunction.addStringParam(table.getTableTreeName());
		onClickFunction.addStringParam(table.getTablePath());
		onClickFunction.addStringParam(String.valueOf(rowIndex));
		onClickFunction.addStringParam(ID_PREFIX);
		onClickFunction.addNonStringParam(String.valueOf(rowIndex));
		return onClickFunction.toString();
	}

	/**
	 * @param buffer
	 */
	private void outTableCells(StringBuffer buffer) {
		if (tableCells != null) {
			tableCells.setRowIndex(rowIndex);
			buffer.append(tableCells.getHTML());
		}
	}

	/**
	 * @return
	 */
	public int getRowCount() {
		return rowCount;
	}

	/**
	 * @return
	 */
	public int getRowIndex() {
		return rowIndex;
	}

	/**
	 * @param i
	 */
	public void setRowCount(int i) {
		rowCount = i;
	}

	/**
	 * @param i
	 */
	public void setRowIndex(int i) {
		rowIndex = i;
	}

	public void addAttribute(String name, String value) {
		if (mapAttributes == null) {
			mapAttributes = new HashMap();
		}
		mapAttributes.put(attPrefix + name, value);
	}
}
