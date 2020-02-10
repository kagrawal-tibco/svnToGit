package com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.model;

import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.util.HTMLRenderer;

/**
 * @author rajesh
 *
 */
public class TableRowMenu implements HTMLRenderer {

	private String userParam = "";

	private String rowId = "";

	private String tableTreeId = "";

	/**
     *
     */
	public TableRowMenu(String userParam) {
		this.userParam = userParam;
	}

	public StringBuffer getHTML() {
		return null;
	}

	String getJSPopupMethod() {
		return "showMenu('" + tableTreeId + "', '" + rowId + "', '" + userParam + "')";
	}

	/**
	 * @return
	 */
	public String getRowId() {
		return rowId;
	}

	/**
	 * @return
	 */
	public String getTableTreeId() {
		return tableTreeId;
	}

	/**
	 * @return
	 */
	public String getUserParam() {
		return userParam;
	}

	/**
	 * @param string
	 */
	public void setRowId(String string) {
		rowId = string;
	}

	/**
	 * @param string
	 */
	public void setTableTreeId(String string) {
		tableTreeId = string;
	}

	/**
	 * @param string
	 */
	public void setUserParam(String string) {
		userParam = string;
	}

}
