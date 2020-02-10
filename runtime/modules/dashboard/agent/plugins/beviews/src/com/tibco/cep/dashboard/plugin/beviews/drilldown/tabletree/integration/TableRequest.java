package com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.integration;

import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.model.TableTree;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.model.TableTreeConstants;
import com.tibco.cep.dashboard.psvr.biz.BizSessionRequest;

/**
 * @author rajesh
 * 
 */
public class TableRequest {

	private BizSessionRequest request;
	private String customRowPath;

	public TableRequest(BizSessionRequest request) {
		this.request = request;
	}

	/**
	 * @return
	 */
	public boolean isExpanded() {
		return getBooleanValue(TableTreeConstants.KEY_EXPANDED);
	}

	/**
	 * @return
	 */
	public boolean isLoaded() {
		return getBooleanValue(TableTreeConstants.KEY_LOADED);
	}

	/**
	 * @return
	 */
	public boolean getBooleanValue(String param) {
		try {
			return new Boolean(request.getParameter(param)).booleanValue();
		} catch (Exception e) {
			return true;
		}
	}

	/**
	 * @return
	 */
	public int getParentRowIndex() {
		try {
			return Integer.parseInt(request.getParameter(TableTreeConstants.KEY_ROW_INDEX));
		} catch (Exception e) {

		}
		return -1;
	}

	/**
	 * @return
	 */
	public int getParentRowCount() {
		try {
			return Integer.parseInt(request.getParameter(TableTreeConstants.KEY_ROW_COUNT));
		} catch (Exception e) {

		}
		return 0;
	}

	/**
	 * @return
	 */
	public BizSessionRequest getBizRequest() {
		return request;
	}

	/**
	 * @return
	 */
	public String getContextPath() {
		return request.getContextPath();
	}

	/**
	 * @return
	 */
	public String getTableTreePath() {
		String tableTreePath = request.getParameter(TableTreeConstants.KEY_PATH);
		if (tableTreePath == null || tableTreePath.length() == 0) {
			tableTreePath = TableTreeConstants.ROOT_TABLE_PATH;
		}
		return tableTreePath;
	}

	/**
	 * @return
	 */
	public String getParentTableTreePath2() {
		String tableTreePath = getTableTreePath();
		try {
			int lastIndex = tableTreePath.lastIndexOf(TableTree.PATH_SEPARATOR);
			tableTreePath = tableTreePath.substring(0, lastIndex);
			lastIndex = tableTreePath.lastIndexOf(TableTree.PATH_SEPARATOR);
			tableTreePath = tableTreePath.substring(0, lastIndex);
		} catch (Exception e) {

		}
		// System.out.println("tableTreePath :: " + tableTreePath);
		return tableTreePath;
	}

	/**
	 * @return
	 */
	public int getSortedColIndex() {
		String sortColIndex = request.getParameter(TableTreeConstants.KEY_SORT_COLUMN_INDEX);
		try {
			return Integer.parseInt(sortColIndex);
		} catch (NullPointerException npe) {

		} catch (NumberFormatException nfe) {

		}
		return -1;
	}

	/**
	 * @return
	 */
	public int getSortedState() {
		String sortState = request.getParameter(TableTreeConstants.KEY_SORT_STATE);
		try {
			return Integer.parseInt(sortState);
		} catch (NullPointerException npe) {

		} catch (NumberFormatException nfe) {

		}
		return 0;
	}

	public void setRowPath(String path) {
		customRowPath = path;
	}

	/**
	 * @return
	 */
	public String getRowPath() {
		String tableTreePath = getTableTreePath();
		try {
			String rowPath = null;
			if (customRowPath != null) {
				rowPath = customRowPath;
			} else {
				rowPath = request.getParameter(TableTreeConstants.KEY_ROW_PATH);
			}
			if (rowPath != null && rowPath.length() > 0) {
				tableTreePath = tableTreePath + TableTree.PATH_SEPARATOR + rowPath;
			}
			// System.out.println("tableTreePath : " + tableTreePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tableTreePath;
	}
}
