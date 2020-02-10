package com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.model;

import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.util.HTMLRenderer;

/**
 * @author rajesh
 *
 *         To change the template for this generated type comment go to Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class TableFilterRow implements HTMLRenderer {

	private String filterImgSrc;
	private static final String ID_PREFIX = "tableTree_FilterRow";
	private static final String INPUT_CELL_ID_PREFIX = ID_PREFIX + "_CELL_INPUT";
	private static final String INPUT_ID_PREFIX = ID_PREFIX + "_INPUT";
	private static final String IMG_CELL_ID_PREFIX = ID_PREFIX + "_CELL_IMG";
	private static final String IMG_ID_PREFIX = ID_PREFIX + "_IMG";

	private TableTree table;

	/**
     *
     */
	public TableFilterRow(TableTree table) {
		this.table = table;
		filterImgSrc = table.getImagePath() + "/filterFunnel.gif";
	}

	/**
     *
     */
	public TableFilterRow(TableTree table, String imageSrc) {
		this.table = table;
		this.filterImgSrc = imageSrc;
	}

	public StringBuffer getHTML() {
		StringBuffer buffer = new StringBuffer();
		render(buffer);
		return buffer;
	}

	private void render(StringBuffer buffer) {
		for (int i = 0; i < table.getNumColumns(); i++) {
			renderCell(buffer, i);
		}
	}

	private void renderCell(StringBuffer buffer, int cellIndex) {
		buffer.append("<TD>");
		buffer.append("<TABLE cellspacing='0' cellpadding='0' width='100%' height='100%'>");
		buffer.append("<TR>");
		renderInputCell(buffer, cellIndex);
		renderImageCell(buffer, cellIndex);
		buffer.append("</TR>");
		buffer.append("</TABLE>");
		buffer.append("</TD>");
	}

	private void renderImageCell(StringBuffer buffer, int cellIndex) {
		buffer.append("<TD align='center' width='100%' class='filterCell' id= '" + table.computeId(IMG_CELL_ID_PREFIX, cellIndex) + "'>");
		buffer.append("&nbsp;");
		buffer.append("<IMG");
		buffer.append(" src='" + filterImgSrc + "'");
		buffer.append(" class='filterImage'");
		buffer.append(" id= '" + table.computeId(IMG_ID_PREFIX, cellIndex) + "'");
		buffer.append(" onclick= " + getFilterEvent("doFilter") + "");
		buffer.append("/>");
		buffer.append("</TD>");
	}

	private void renderInputCell(StringBuffer buffer, int cellIndex) {
		buffer.append("<TD align='left' valign='center' class='filterCell' id= '" + table.computeId(INPUT_CELL_ID_PREFIX, cellIndex) + "'>");
		buffer.append("<INPUT");
		buffer.append(" width='100%'");
		buffer.append(" type='text'");
		buffer.append(" class='filterInput'");
		buffer.append(" id= '" + table.computeId(INPUT_ID_PREFIX, cellIndex) + "'");
		buffer.append(" onkeypress= " + getFilterEvent("filterInput_KeyDown") + "");
		buffer.append("/>");
		buffer.append("</TD>");
	}

	/**
	 * @param cellIndex
	 * @return
	 */
	private String getFilterEvent(String methodName) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("\"return " + methodName + "(");
		buffer.append("'" + table.getTableTreeName() + "'");
		buffer.append(", '" + table.getTablePath() + "'");
		buffer.append(", " + table.getNumColumns() + "");
		buffer.append(");\"");
		return buffer.toString();
	}

	/**
	 * @return
	 */
	public String getRowStyle() {
		return "filterRow";
	}
}
