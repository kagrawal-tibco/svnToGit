package com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.util.ExpandCollapseHandle;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.util.HTMLRenderer;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.util.HTMLTag;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.util.TableTreeUtils;
import com.tibco.cep.dashboard.psvr.biz.BizSessionRequest;

/**
 * @author rajesh
 *
 *         To change the template for this generated type comment go to Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class TableTree implements HTMLRenderer {

	public static final int WIDTH_FULL = 0;
	public static final int WIDTH_NATURAL = 1;

	static final String TBL_FOOTER_ROW_TYPE = "table_footer_row";
	static final String TBL_HEADER_ROW_TYPE = "table_header_row";
	static final String FILTER_ROW_TYPE = "filter_row";
	static final String GROUP_ROW_TYPE = "group_row";
	static final String COL_HEADER_ROW_TYPE = "col_header_row";
	private TableFilterRow filterRow;
	private TableGroupRows groupRows;
	private String tablePath = "0";
	public static final String PATH_SEPARATOR = "_";
	static final String ID_SEPARATOR = "*";

	static final String TABLE_GENERAL_PROPS = " cellspacing='0' cellpadding='0' ";
	private boolean sortable = true;
	// private static final String TABLE_GENERAL_PROPS = " cellspacing='0' cellpadding='0' width='100%' ";
	public static final String MAIN_TABLE_ID_PREFIX = "tblTreeMain";
	public static final String DATA_FRAME_ID_PREFIX = "ifrmMain";
	private static final String COL_HEADER_ROW_ID_PREFIX = "colHeaderRow";
	private String style = "tableStyle";

	private String deployPath;
	private String tableTreeId;
	private TableColumnHeaders columnHeaders;
	private TableHeader header;
	private TableRows rows;
	private TableColumns columns;
	private NestedTableTree[] nestedTables;
	private TableFooter footer;
	private BizSessionRequest httpServletRequest;
	private ExpandCollapseHandle expandCollapseHandle;

	private int numColumns;

	private boolean expandCollapseCell = true;
	private String rowStyle = "rowStyle";

	private String evenRowStyle = "evenRowStyle";
	private String oddRowStyle = "oddRowStyle";

	private String ascendingStyle = "ascendingColumnStyle";
	private String descendingStyle = "descendingColumnStyle";

	private boolean isAlternateRowStyle = true;
	private boolean lazyLoading = true;
	private String selectionMode = TableTreeConstants.TABLE_SELECTION_SELECT_ROW;

	private String backgroundColor = "#FFFFFF";

	private int widthType = WIDTH_NATURAL;
	private Map tableParameters;

	private boolean expanded = true;
	private int tableIndex;

	private Map mapCustomAttributes = new HashMap();

	private boolean grouped = false;

	private TableCellRenderer cellRenderer = null;
	private int startRowIndex = 0;

	/**
     *
     */
	public TableTree(BizSessionRequest httpServletRequest, String tableTreeId, int startRowIndex, int numColumns, String tablePath) {
		super();
		this.httpServletRequest = httpServletRequest;
		this.numColumns = numColumns;
		this.tableTreeId = tableTreeId;
		this.tablePath = tablePath;
		this.startRowIndex = startRowIndex;
	}

	/**
     *
     */
	public TableTree(BizSessionRequest httpServletRequest, String tableTreeId, int startRowIndex, int numColumns, String tablePath, int tableIndex) {
		this(httpServletRequest, tableTreeId, startRowIndex, numColumns, tablePath);
		this.tablePath = tablePath + PATH_SEPARATOR + tableIndex;
		this.tableIndex = tableIndex;
	}

	public StringBuffer getHTML() {
		StringBuffer buffer = new StringBuffer();
		renderTable(buffer);
		return buffer;
	}

	public static String getRelativeImagePath() {
		return "drilldown/images";
	}

	public static String getRelativeCSSPath() {
		return "drilldown/css";
	}

	public static String getRelativeJSPath() {
		return "drilldown/js";
	}

	public String getImagePath() {
		return getDeployPath() + "/" + getRelativeImagePath();
	}

	public String getCSSPath() {
		return getDeployPath() + "/" + getRelativeCSSPath();
	}

	public String getJSPath() {
		return getDeployPath() + "/" + getRelativeJSPath();
	}

	/**
	 * @return
	 */
	public TableColumnHeaders getColumnHeaders() {
		return columnHeaders;
	}

	/**
	 * @return
	 */
	public TableColumns getColumns() {
		return columns;
	}

	/**
	 * @return
	 */
	public TableColumn getColumn(int colIndex) {
		try {
			return columns.get(colIndex);
		} catch (NullPointerException e) {
		}
		return new TableColumn(this, colIndex);
	}

	/**
	 * @return
	 */
	public TableHeader getHeader() {
		return header;
	}

	/**
	 * @return
	 */
	public int getNumColumns() {
		return numColumns;
	}

	/**
	 * @return
	 */
	public int getNumRows() {
		return rows.rows.size();
	}

	/**
	 * @return
	 */
	public TableRows getRows() {
		return rows;
	}

	public String getTableTreeId() {
		return tableTreeId;
	}

	/**
	 * @return
	 */
	private String getTableTreeId_Path() {
		return tableTreeId + PATH_SEPARATOR + tablePath;
	}

	/**
	 * @param headers
	 */
	public void setColumnHeaders(TableColumnHeaders headers) {
		columnHeaders = headers;
	}

	/**
	 * @param columns
	 */
	public void setColumns(TableColumns columns) {
		this.columns = columns;
	}

	/**
	 * @param header
	 */
	public void setHeader(TableHeader header) {
		this.header = header;
	}

	/**
	 * @param rows
	 */
	public void setRows(TableRows rows) {
		this.rows = rows;
	}

	/**
	 * @param string
	 */
	public void setTableTreeId(String string) {
		tableTreeId = string;
	}

	public String computeId(String prefix, int index) {
		return prefix + ID_SEPARATOR + getTableTreeId_Path() + ID_SEPARATOR + index;
	}

	private void renderTable(StringBuffer buffer) {
		// if (isOutFirstTime(httpServletRequest))
		// {
		outMainTable_Start(buffer);

		// }
		if (nestedTables == null) {
			if (columns != null) {
				outTableColumns(buffer);
			}
			outTHEAD_Start(buffer);

			if (header != null) {
				outTableHeader(buffer);
			}

			if (columnHeaders != null) {
				outColumnHeaders(buffer);
			}

			if (filterRow != null) {
				outFilterRow(buffer);
			}

			outTHEAD_End(buffer);

			outTableBody(buffer);
			/*
			 * if (rows != null) { outTableRows(buffer); }
			 *
			 * if (groupRows != null) { outTableGroupRows(buffer); }
			 *
			 * if (footer != null) { outTableFooter(buffer); }
			 */
		} else {
			outTHEAD_Start(buffer);
			if (header != null) {
				outTableHeader(buffer);
			}
			outTHEAD_End(buffer);
			outNestedTables(buffer);
		}
		outMainTable_End(buffer);

		if (isOutFirstTime(httpServletRequest)) {
			// if(isLazyLoading())
			{
				outHiddenDataIFrame(buffer);
			}

		}
	}

	/**
	 * @param buffer
	 */
	private void outTHEAD_End(StringBuffer buffer) {
		buffer.append("</THEAD>");
	}

	/**
	 * @param buffer
	 */
	private void outTHEAD_Start(StringBuffer buffer) {
		buffer.append("<THEAD>");
	}

	/**
	 * @param buffer
	 */
	private void outTableColumns(StringBuffer buffer) {
		if (columns != null) {
			buffer.append(columns.getHTML());
		}
	}

	/**
	 * @param buffer
	 */
	private void outTableGroupRows(StringBuffer buffer) {
		if (groupRows != null) {
			buffer.append(groupRows.getHTML());
		}
	}

	/**
	 * @param buffer
	 */
	private void outHiddenDataIFrame(StringBuffer buffer) {
		buffer.append("<!--Data IFrame Start--><iframe ");
		buffer.append("id='" + getFrameId() + "' ");
		buffer.append("name='" + getFrameId() + "' ");
		buffer.append("style='width:0px; height:0px; border: 0px' src='about:blank'></iframe>");
		// buffer.append("style='width:300px; height:100px; border: 1px' src='about:blank'></iframe>");
	}

	/**
	 * @return
	 */
	String getFrameId() {
		return DATA_FRAME_ID_PREFIX + ID_SEPARATOR + getTableTreeName();
	}

	/**
	 * @param buffer
	 */
	private void outMainTable_End(StringBuffer buffer) {
		buffer.append("</table><!--Main Table End-->");
	}

	private void outTableBody(StringBuffer buffer) {
		HTMLTag tagTBody = new HTMLTag("tbody");
		if (!expanded) {
			tagTBody.addStyle("display", "none");
		}
		StringBuffer contentBuffer = new StringBuffer();
		if (rows != null) {
			outTableRows(contentBuffer);
		}

		if (groupRows != null) {
			outTableGroupRows(contentBuffer);
		}

		if (footer != null) {
			outTableFooter(contentBuffer);
		}
		// outTableRows(contentBuffer);
		// outTableFooter(contentBuffer);
		tagTBody.setContent(contentBuffer.toString());
		buffer.append(tagTBody.toString());
	}

	/**
	 * @param httpServletRequest
	 * @param buffer
	 */
	private void outTableRows(StringBuffer buffer) {
		if (rows != null) {
			// tagTBody.setContent(rows.getHTML().toString());
			buffer.append(rows.getHTML());
			// buffer.append(tagTBody.toString());
		}
	}

	/**
	 * @param httpServletRequest
	 * @param buffer
	 */
	private void outColumnHeaders(StringBuffer buffer) {
		/*
		 * if(columnHeaders!=null){ buffer.append("<TR id='" + computeId(COL_HEADER_ROW_ID_PREFIX) + "' sortable='true' class='"+ columnHeaders.getUnSortedStyle() +"' c_type='"+ COL_HEADER_ROW_TYPE +"'>");
		 * buffer.append("<TD class='expandCollapseColumn'></TD>"); buffer.append("<TD class='rowheaderColumn'></TD>"); buffer.append(columnHeaders.getHTML()); if (widthType == WIDTH_NATURAL) { addRightFillerCell(buffer); }
		 * buffer.append("</TR>"); }
		 */

		if (columnHeaders != null) {
			HTMLTag tagTR = new HTMLTag("tr");
			tagTR.setId(computeId(COL_HEADER_ROW_ID_PREFIX));
			tagTR.addAttribute("sortable", "" + sortable);
			tagTR.setStyleClass(columnHeaders.getUnSortedStyle());
			tagTR.addCustomAttribute("type", COL_HEADER_ROW_TYPE);
			if (!expanded) {
				tagTR.addStyle("display", "none");
			}
			tagTR.toStringTagStart(buffer);

			if (isExpandCollapseCell()) {
				HTMLTag tagTDExCollCell = new HTMLTag("td");
				tagTDExCollCell.setStyleClass("treeCellLine");
				tagTDExCollCell.addStyle("background-color", getBackgroundColor());
				buffer.append(tagTDExCollCell);
			}
			HTMLTag tagTDRowHdrCell = new HTMLTag("td");
			tagTDRowHdrCell.setStyleClass("rowheaderColumn");
			buffer.append(tagTDRowHdrCell);

			buffer.append(columnHeaders.getHTML());
			if (widthType == WIDTH_NATURAL) {
				addRightFillerCell(buffer);
			}
			buffer.append("</TR>");
		}
	}

	private void outFilterRow(StringBuffer buffer) {
		if (filterRow != null) {
			buffer.append("<TR class='" + filterRow.getRowStyle() + "' c_type='" + FILTER_ROW_TYPE + "' >");
			buffer.append("<TD class='expandCollapseColumn'></TD>");
			buffer.append("<TD class='rowheaderColumn'></TD>");
			buffer.append(filterRow.getHTML());
			if (widthType == WIDTH_NATURAL) {
				addRightFillerCell(buffer);
			}
			buffer.append("</TR>");
		}
	}

	/**
	 * @param httpServletRequest
	 * @param buffer
	 */
	private void outTableHeader(StringBuffer buffer) {
		if (header != null) {
			/*
			 * buffer.append("<TR c_type='"+ TBL_HEADER_ROW_TYPE + "'>"); buffer.append("<TD class = 'tdHeader' colspan = '" + (getNumColumns() + 2) + "' >"); buffer.append(header.getHTML()); buffer.append("</TD>"); if
			 * (widthType == WIDTH_NATURAL) { addRightFillerCell(buffer); } buffer.append("</TR>");
			 */
			buffer.append(header.getHTML());
		}
	}

	void addRightFillerCell(StringBuffer buffer) {
		buffer.append(getRightFillerCell().toString());
	}

	HTMLTag getRightFillerCell() {
		HTMLTag tagTD = new HTMLTag("td");
		tagTD.addAttribute("width", "100%");
		// tagTD.addAttribute("background", getBackgroundColor());
		tagTD.setStyleClass("ttRightFillerCell");
		tagTD.addStyle("background-color", getBackgroundColor());
		// tagTD.addStyle("visibility", "hidden");
		return tagTD;
	}

	int getColspan() {
		return getNumColumns() + 3;
	}

	/**
	 * @param buffer
	 */
	private void outMainTable_Start(StringBuffer buffer) {
		buffer.append("<!--Main Table Start--><table ");
		TableTreeUtils.outAttribute(buffer, "id", computeId(MAIN_TABLE_ID_PREFIX));
		buffer.append(TABLE_GENERAL_PROPS);
		TableTreeUtils.outAttribute(buffer, "sortable", "" + sortable);
		outStyle(buffer);
		outSortStyles(buffer);
		outCustomAttribute_ExpandCollapseImage(buffer);
		// TableTreeConstants.TABLE_SELECTION_SELECT_ROW
		TableTreeUtils.outAttribute(buffer, CustomAttributeConstants.SELECTION_MODE, getSelectionMode());
		TableTreeUtils.outAttribute(buffer, CustomAttributeConstants.NUM_COLUMNS, "" + getNumColumns());
		TableTreeUtils.outAttribute(buffer, "c_imagePath", "" + getImagePath());
		TableTreeUtils.outAttribute(buffer, "c_widthType", "" + getWidthType());
		TableTreeUtils.outAttribute(buffer, "c_expandCollapseCell", "" + isExpandCollapseCell());
		if (widthType == WIDTH_FULL) {
			TableTreeUtils.outAttribute(buffer, "width", "100%");
		}
		TableTreeUtils.outAttributes(mapCustomAttributes, buffer);
		buffer.append(">");
	}

	private void outNestedTables(StringBuffer buffer) {
		for (int i = 0; i < nestedTables.length; i++) {
			NestedTableTree nestedTable = nestedTables[i];
			buffer.append(nestedTable.getHTML());
		}
	}

	/**
	 * @param buffer
	 */
	private void outTableFooter(StringBuffer buffer) {
		if (footer != null) {
			/*
			 * buffer.append("<TR c_type='"+ TBL_HEADER_ROW_TYPE + "'>"); buffer.append("<TD class = 'tdHeader' colspan = '" + (getNumColumns() + 2) + "' >"); buffer.append(header.getHTML()); buffer.append("</TD>"); if
			 * (widthType == WIDTH_NATURAL) { addRightFillerCell(buffer); } buffer.append("</TR>");
			 */
			buffer.append(footer.getHTML());
		}
	}

	/**
	 * @return
	 */
	public boolean isExpanded() {
		return false;
	}

	/**
	 * @return
	 */
	public String getSelectionMode() {
		return selectionMode;
	}

	private void outSortStyles(StringBuffer buffer) {
		buffer.append(" descendingStyle='" + descendingStyle + "'");
		buffer.append(" ascendingStyle='" + ascendingStyle + "'");

	}

	/**
	 * @param httpServletRequest
	 * @return
	 */
	private boolean isOutFirstTime(BizSessionRequest httpServletRequest) {
		return httpServletRequest.getParameter(TableTreeConstants.KEY_ID) == null;
	}

	/**
	 * @param rows
	 */
	public void setGroupRows(TableGroupRows groupRows) {
		this.groupRows = groupRows;
	}

	public String getEvenRowStyle() {
		return evenRowStyle;
	}

	public void setEvenRowStyle(String evenRowStyle) {
		this.evenRowStyle = evenRowStyle;
	}

	public String getOddRowStyle() {
		return oddRowStyle;
	}

	public void setOddRowStyle(String oddRowStyle) {
		this.oddRowStyle = oddRowStyle;
	}

	public String getRowStyle() {
		return rowStyle;
	}

	public void setRowStyle(String rowStyle) {
		this.rowStyle = rowStyle;
	}

	public boolean isAlternateRowStyle() {
		return isAlternateRowStyle;
	}

	public void setAlternateRowStyle(boolean isAlternateRowStyle) {
		this.isAlternateRowStyle = isAlternateRowStyle;
	}

	/**
	 * @return
	 */
	public String getTableTreeName() {
		return tableTreeId;
	}

	public String getTablePath() {
		return tablePath;
	}

	public int getPathLength() {
		StringTokenizer tokenizer = new StringTokenizer(tablePath, PATH_SEPARATOR);
		return tokenizer.countTokens();
	}

	public int getDepth() {
		return getPathLength() / 2;
	}

	public String computeId(String prefix) {
		return prefix + ID_SEPARATOR + getTableTreeId_Path();
	}

	/**
	 * @param tableTreePath
	 * @param parentRowIndex
	 * @return
	 */
	public static String appendPath(String tableTreePath, int parentRowIndex) {
		return tableTreePath + PATH_SEPARATOR + parentRowIndex;
	}

	private void outStyle(StringBuffer buffer) {
		if (style != null) {
			buffer.append(" class=\"" + style + "\" ");
		}
	}

	public String getAscendingStyle() {
		return ascendingStyle;
	}

	public void setAscendingStyle(String ascendingStyle) {
		this.ascendingStyle = ascendingStyle;
	}

	public String getDescendingStyle() {
		return descendingStyle;
	}

	public void setDescendingStyle(String descendingStyle) {
		this.descendingStyle = descendingStyle;
	}

	/**
	 * @param row
	 */
	public void setFilterRow(TableFilterRow filterRow) {
		this.filterRow = filterRow;
	}

	/**
	 * @param selectionMode
	 *            The selectionMode to set.
	 */
	public void setSelectionMode(String selectionMode) {
		this.selectionMode = selectionMode;
	}

	/**
	 * @param deployPath
	 */
	public void setDeployPath(String deployPath) {
		this.deployPath = deployPath;
	}

	/**
	 * @return
	 */
	public String getDeployPath() {
		if (deployPath == null) {
			return httpServletRequest.getContextPath();
		}
		return deployPath;
	}

	private void outCustomAttribute_ExpandCollapseImage(StringBuffer buffer) {
		try {
			HashMap mapImages = expandCollapseHandle.getConfigurator().getExpandCollapseImageMap();
			HashMap mapOutImages = new HashMap(mapImages.size());
			String imageDirectory = expandCollapseHandle.getConfigurator().getExpandCollapseImagePath();
			for (int i = 0; i < 12; i++) {
				Integer key = new Integer(i);
				String value = (String) mapImages.get(key);
				String imgKey = "excolimg_" + i;
				String imgValue = imageDirectory + "/" + value;
				mapOutImages.put(imgKey, imgValue);
			}
			TableTreeUtils.outAttributes(mapOutImages, buffer);
		} catch (NullPointerException npe) {
		}
	}

	/**
	 * @param handle
	 */
	public void setExpandCollapseHandle(ExpandCollapseHandle expandCollapseHandle) {
		this.expandCollapseHandle = expandCollapseHandle;
	}

	/**
	 * @return
	 */
	public String getBackgroundColor() {
		return backgroundColor;
	}

	/**
	 * @param string
	 */
	public void setBackgroundColor(String string) {
		backgroundColor = string;
	}

	/**
	 * @param i
	 */
	public void setWidthType(int i) {
		if (i == WIDTH_FULL || i == WIDTH_NATURAL) {
			widthType = i;
		} else {
			throw new IllegalArgumentException("WidthType can be either TableTree.WIDTH_FULL or TableTree.WIDTH_NATURAL");
		}
	}

	/**
	 * @return
	 */
	public int getWidthType() {
		return widthType;
	}

	public boolean isExpandCollapseCell() {
		return expandCollapseCell;
	}

	public void setExpandCollapseCell(boolean expandCollapseCell) {
		this.expandCollapseCell = expandCollapseCell;
	}

	public boolean isLazyLoading() {
		return lazyLoading;
	}

	public void setLazyLoading(boolean lazyLoading) {
		this.lazyLoading = lazyLoading;
	}

	/**
	 * @param parameters
	 */
	public void setTableParameters(Map parameters) {
		this.tableParameters = parameters;
	}

	/**
	 * @return Returns the tableParameters.
	 */
	public Map getTableParameters() {
		return tableParameters;
	}

	/**
	 * @return Returns the nestedTables.
	 */
	public NestedTableTree[] getNestedTables() {
		return nestedTables;
	}

	/**
	 * @param nestedTables
	 *            The nestedTables to set.
	 */
	public void setNestedTables(NestedTableTree[] nestedTables) {
		this.nestedTables = nestedTables;
	}

	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}

	public int getTableIndex() {
		return tableIndex;
	}

	public BizSessionRequest getHttpServletRequest() {
		return httpServletRequest;
	}

	/**
	 * @param string
	 */
	public void attachCommand(String command) {
		List listCommands = (List) httpServletRequest.getAttribute(TableTreeConstants.TableTree_COMMANDS);
		if (listCommands == null) {
			listCommands = new ArrayList();
			httpServletRequest.setAttribute(TableTreeConstants.TableTree_COMMANDS, listCommands);
		}
		listCommands.add(command);
	}

	public void addCustomAttribute(String attrib, String value) {
		mapCustomAttributes.put("c_" + attrib, value);
	}

	public boolean isGrouped() {
		return grouped;
	}

	public void setGrouped(boolean grouped) {
		this.grouped = grouped;
	}

	/**
	 * @param footer2
	 */
	public void setFooter(TableFooter footer) {
		this.footer = footer;
	}

	public TableCellRenderer getCellRenderer() {
		return cellRenderer;
	}

	public void setCellRenderer(TableCellRenderer cellRenderer) {
		this.cellRenderer = cellRenderer;
	}

	public boolean isSortable() {
		return sortable;
	}

	public void setSortable(boolean sortable) {
		this.sortable = sortable;
	}

	/**
	 * @return Returns the startRowIndex.
	 */
	public int getStartRowIndex() {
		return startRowIndex;
	}
}
