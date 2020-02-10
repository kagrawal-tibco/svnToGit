package com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.model;

import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.util.ExpandCollapseHandle;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.util.HTMLRenderer;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.util.HTMLTag;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.util.TableTreeUtils;

/**
 * @author rajesh
 *
 *         To change the template for this generated type comment go to Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class TableGroupRow implements HTMLRenderer {

	private static final String ID_PREFIX = "tableTree_GroupRow";
	private static final String GROUP_ROW_EX_COLL_ID_PREFIX = ID_PREFIX + "_EX_COLL";
	private static final String GROUPROW_TABLE_ID_PREFIX = ID_PREFIX + "_TABLE";
	private static final String GROUPROW_HEADER_ID_PREFIX = ID_PREFIX + "_HEADER";
	private TableTree table;
	private ExpandCollapseHandle expandCollapseHandle;
	private TableTree nestedTable;
	private TableHeader header;

	private int parentRowIndex;
	private int groupRowIndex;
	private int groupRowCount;
	private Object rowParam;

	/**
     *
     */
	public TableGroupRow(TableTree table, ExpandCollapseHandle expandCollapseHandle, int parentRowIndex, TableTree nestedTable, TableHeader header) {
		this(table, expandCollapseHandle, parentRowIndex, nestedTable, header, null);
	}

	/**
     *
     */
	public TableGroupRow(TableTree table, ExpandCollapseHandle expandCollapseHandle, int parentRowIndex, TableTree nestedTable, TableHeader header, Object rowParam) {
		this.table = table;
		this.expandCollapseHandle = expandCollapseHandle;
		this.parentRowIndex = parentRowIndex;
		this.nestedTable = nestedTable;
		this.header = header;
		this.rowParam = rowParam;
	}

	public StringBuffer getHTML() {
		StringBuffer buffer = new StringBuffer();
		render(buffer);
		return buffer;
	}

	private void render(StringBuffer buffer) {
		TableTreeUtils.outTagStart(buffer, "tr");
		TableTreeUtils.outAttribute(buffer, "id", computeId(ID_PREFIX));
		TableTreeUtils.outAttribute(buffer, "c_loaded", "false");
		TableTreeUtils.outAttribute(buffer, "c_expanded", "false");
		TableTreeUtils.outAttribute(buffer, "c_type", TableTree.GROUP_ROW_TYPE);
		TableTreeUtils.outTagStartEnd(buffer);

		TableTreeUtils.outTagStart(buffer, "td");
		TableTreeUtils.outAttribute(buffer, "style", "background-color:" + table.getBackgroundColor());
		TableTreeUtils.outAttribute(buffer, "class", "treeCellLine");
		TableTreeUtils.outTagStartEnd(buffer);
		TableTreeUtils.outTagEnd(buffer, "td");

		outExpandCollapseCell(buffer);

		TableTreeUtils.outTagStart(buffer, "td");
		TableTreeUtils.outAttribute(buffer, "class", "tdHeader");
		TableTreeUtils.outAttribute(buffer, "colspan", "" + (table.getNumColumns() + 1));
		TableTreeUtils.outTagStartEnd(buffer);

		// DIV for header
		TableTreeUtils.outTagStart(buffer, "DIV");
		TableTreeUtils.outAttribute(buffer, "id", nestedTable.computeId(GROUPROW_HEADER_ID_PREFIX));
		TableTreeUtils.outAttribute(buffer, "width", "100%");
		TableTreeUtils.outAttribute(buffer, "style", "display:''");
		TableTreeUtils.outTagStartEnd(buffer);

		if (table.getWidthType() == TableTree.WIDTH_NATURAL) {
			HTMLTag tagTable = new HTMLTag("table");
			tagTable.addTableGeneralProps();
			HTMLTag tagTR = tagTable.addTag("tr");
			HTMLTag tagTD = tagTR.addTag("td");
			// tagTD.addAttribute("width", "100%");
			tagTD.setStyleClass("tdHeader");
			tagTD.setContent(header.getHTML().toString());
			tagTR.addTag(table.getRightFillerCell());

			buffer.append(tagTable.toString());
		} else {
			buffer.append(header.getHTML());
		}

		TableTreeUtils.outTagEnd(buffer, "DIV");

		// DIV for Table
		TableTreeUtils.outTagStart(buffer, "DIV");
		TableTreeUtils.outAttribute(buffer, "id", nestedTable.computeId(GROUPROW_TABLE_ID_PREFIX));
		TableTreeUtils.outAttribute(buffer, "style", "display:'none'");
		TableTreeUtils.outAttribute(buffer, "width", "100%");
		TableTreeUtils.outTagStartEnd(buffer);
		buffer.append("test dummy data");
		TableTreeUtils.outTagEnd(buffer, "DIV");

		TableTreeUtils.outTagEnd(buffer, "td");

		TableTreeUtils.outTagEnd(buffer, "tr");
	}

	private void outExpandCollapseCell(StringBuffer buffer) {
		TableTreeUtils.outTagStart(buffer, "TD");
		TableTreeUtils.outAttribute(buffer, "valign", "top");
		TableTreeUtils.outAttribute(buffer, "style", "background-color:" + table.getBackgroundColor());
		TableTreeUtils.outAttribute(buffer, "width", "16");
		TableTreeUtils.outAttribute(buffer, "id", computeId(GROUP_ROW_EX_COLL_ID_PREFIX));
		boolean isFirst = groupRowIndex == 0;
		boolean isLast = groupRowIndex == groupRowCount - 1;
		if (groupRowCount > 1 && !isLast) {
			TableTreeUtils.outAttribute(buffer, "class", "treeCellLine");
		}
		TableTreeUtils.outTagStartEnd(buffer);
		outExpandCollapseHandler(buffer, isFirst, isLast);
		TableTreeUtils.outTagEnd(buffer, "TD");
	}

	private String computeId(String prefix) {
		return table.computeId(prefix, parentRowIndex) + TableTree.PATH_SEPARATOR + groupRowIndex;
	}

	/**
	 * @param buffer
	 * @param httpServletRequest
	 */
	private void outExpandCollapseHandler(StringBuffer buffer, boolean isFirst, boolean isLast) {
		isFirst = false;
		if (expandCollapseHandle != null) {
			String subPath = parentRowIndex + TableTree.PATH_SEPARATOR + groupRowIndex;
			expandCollapseHandle.setRowIndex(groupRowIndex);
			expandCollapseHandle.setRowParam(rowParam);
			expandCollapseHandle.setFirst(isFirst);
			expandCollapseHandle.setLast(isLast);
			expandCollapseHandle.setSubPath(subPath);
			expandCollapseHandle.setOnClick(getExColOnClickHandler(subPath));
			buffer.append(expandCollapseHandle.getHTML());
		}
	}

	private String getExColOnClickHandler(String subPath) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" onclick=\"return expandCollapseTree('");
		buffer.append(table.getTableTreeName());
		buffer.append("','");
		buffer.append(table.getTablePath());
		buffer.append("','");
		buffer.append(subPath);
		buffer.append("','");
		buffer.append(ID_PREFIX);
		buffer.append("','");
		buffer.append(groupRowIndex);
		buffer.append("','");
		buffer.append(nestedTable.getTablePath() + "');\" ");
		return buffer.toString();
	}

	/**
	 * @param i
	 */
	public void setParentRowIndex(int i) {
		parentRowIndex = i;
	}

	/**
	 * @param i
	 */
	public void setGroupRowIndex(int i) {
		groupRowIndex = i;
	}

	/**
	 * @param i
	 */
	public void setGroupRowCount(int i) {
		groupRowCount = i;
	}
}
