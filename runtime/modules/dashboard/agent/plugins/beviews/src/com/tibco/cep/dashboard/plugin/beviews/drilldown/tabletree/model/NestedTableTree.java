package com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.model;

import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.util.ExpandCollapseHandle;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.util.HTMLRenderer;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.util.HTMLTag;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.util.JSFunction;

/**
 * @author rajesh
 *
 */
public class NestedTableTree implements HTMLRenderer {
	private static final String ID_PREFIX = "tableTree_NestedTable";
	private static final String EX_COLL_ID_PREFIX = ID_PREFIX + "_EX_COLL";
	private static final String ROW_ID_PREFIX = ID_PREFIX + "_ROW";
	private static final String NESTED_TABLE_ROW_TYPE = "nestedtable_row";
	private static final String NESTED_TD_ID_PREFIX = ID_PREFIX + "_CELL";

	// Constructor params
	private TableTree nestedTable;
	private TableTree parentTable;
	private int nestedTableIndex;
	private int parentRowIndex;
	private int nestedTableCount;

	// Get Set Methods
	private ExpandCollapseHandle expandCollapseHandle;
	private String bgColor;
	private int parentRowCount = -1;

	/**
	 *
	 */
	public NestedTableTree(TableTree parentTable, TableTree nestedTable, int parentRowIndex, int nestedTableCount, int nestedTableIndex) {
		this.parentTable = parentTable;
		this.nestedTable = nestedTable;
		this.parentRowIndex = parentRowIndex;
		this.nestedTableCount = nestedTableCount;
		this.nestedTableIndex = nestedTableIndex;
	}

	/**
	 * @param nested_table_row_prefix2
	 * @return
	 */
	private String computeId(String prefix) {
		return parentTable.computeId(prefix, parentRowIndex) + TableTree.PATH_SEPARATOR + nestedTableIndex;
	}

	/**
	 * @param tagTR
	 */
	private void outNestedTableExpandCollapseCell(HTMLTag tagTR) {
		HTMLTag tagTD = tagTR.addTag("td");
		tagTD.addAttribute("valign", "top");
		tagTD.addStyle("background-color", getBgColor());
		tagTD.addAttribute("width", "16");
		tagTD.setId(computeId(EX_COLL_ID_PREFIX));
		if (nestedTable.isExpandCollapseCell()) {
			boolean isFirst = nestedTableIndex == 0;
			boolean isLast = nestedTableIndex == nestedTableCount - 1;
			if (!isLast) {
				tagTD.setStyleClass("treeCellLine");
			}

			outExpandCollapseHandler(tagTD, isFirst, isLast);
		}
	}

	/**
	 * @param buffer
	 * @param httpServletRequest
	 */
	private void outExpandCollapseHandler(HTMLTag tagTD, boolean isFirst, boolean isLast) {
		isFirst = false;
		if (expandCollapseHandle != null) {
			String subPath = parentRowIndex + TableTree.PATH_SEPARATOR + nestedTableIndex;
			expandCollapseHandle.setRowIndex(nestedTableIndex);
			expandCollapseHandle.setRowParam(null);
			expandCollapseHandle.setFirst(isFirst);
			expandCollapseHandle.setLast(isLast);
			expandCollapseHandle.setSubPath(subPath);
			expandCollapseHandle.setOnClick(getExColOnClickHandler(subPath));
			tagTD.setContent(expandCollapseHandle.getHTML().toString());
		}
	}

	private String getExColOnClickHandler(String subPath) {
		JSFunction onClickFunction = new JSFunction("expandCollapseTree");
		onClickFunction.setReturn(true);
		onClickFunction.addStringParam(parentTable.getTableTreeName());
		onClickFunction.addStringParam(parentTable.getTablePath());
		onClickFunction.addStringParam(subPath);
		onClickFunction.addStringParam(ROW_ID_PREFIX);
		onClickFunction.addNonStringParam(String.valueOf(nestedTableIndex));
		onClickFunction.addStringParam(nestedTable.getTablePath());
		return onClickFunction.toString();
	}

	public StringBuffer getHTML() {
		updateNestedTableGroupByMenu();
		HTMLTag tagTR = new HTMLTag("tr");
		tagTR.setId(computeId(ROW_ID_PREFIX));
		if (nestedTable.isExpanded()) {
			tagTR.addAttribute("c_loaded", "true");
			tagTR.addAttribute("c_expanded", "true");
			tagTR.addAttribute("c_type", NESTED_TABLE_ROW_TYPE);
		} else {
			tagTR.addAttribute("c_loaded", "false");
			tagTR.addAttribute("c_expanded", "false");
			tagTR.addAttribute("c_type", NESTED_TABLE_ROW_TYPE);
		}

		if (!nestedTable.isGrouped()) {
			HTMLTag tagTDTreeLine = tagTR.addTag("td");
			tagTDTreeLine.addAttribute("style", "background-color:" + getBgColor());
			if (parentRowIndex + 1 < parentRowCount) {
				tagTDTreeLine.addAttribute("class", "treeCellLine");
			}
		}
		outNestedTableExpandCollapseCell(tagTR);

		HTMLTag tagTDNestedTable = tagTR.addTag("td");
		tagTDNestedTable.setId(nestedTable.computeId(NESTED_TD_ID_PREFIX));
		tagTDNestedTable.addAttribute("colspan", "" + (parentTable.getNumColumns() + 1));
		tagTDNestedTable.setContent(nestedTable.getHTML().toString());
		return new StringBuffer(tagTR.toString());
	}

	/**
	 * @return Returns the bgColor.
	 */
	public String getBgColor() {
		return bgColor;
	}

	/**
	 * @param bgColor
	 *            The bgColor to set.
	 */
	public void setBgColor(String bgColor) {
		this.bgColor = bgColor;
	}

	/**
	 * @return Returns the expandCollapseHandle.
	 */
	public ExpandCollapseHandle getExpandCollapseHandle() {
		return expandCollapseHandle;
	}

	/**
	 * @param expandCollapseHandle
	 *            The expandCollapseHandle to set.
	 */
	public void setExpandCollapseHandle(ExpandCollapseHandle expandCollapseHandle) {
		this.expandCollapseHandle = expandCollapseHandle;
	}

	/**
	 * @param i
	 */
	public void setParentRowCount(int count) {
		this.parentRowCount = count;
	}

	private void updateNestedTableGroupByMenu() {
		String subPath = parentRowIndex + TableTree.PATH_SEPARATOR + nestedTableIndex;
		expandCollapseHandle.setSubPath(subPath);
		if (nestedTable.getHeader().isGroupByEnabled() == true) {
			nestedTable.getHeader().getGroupByMenu().addShowMenuParameters(subPath);
			nestedTable.getHeader().getGroupByMenu().addShowMenuParameters(expandCollapseHandle.getLink());
		}
	}

	/**
	 * @return
	 */
	public TableTree getNestedTable() {
		return nestedTable;
	}

	/**
	 * @param i
	 */
	public void setParentRowIndex(int i) {
		parentRowIndex = i;
	}
}
