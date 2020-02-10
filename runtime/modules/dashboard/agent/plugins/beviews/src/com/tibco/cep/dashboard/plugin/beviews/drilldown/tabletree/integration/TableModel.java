package com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.integration;

import java.util.List;
import java.util.Map;

import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.model.TableCellRenderer;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.model.TableColumnHeader;

public interface TableModel {
	
	public static final int LOAD_INITIAL_ROW_SET = 0;
	
	public static final int LOAD_NEXT_ROW_SET = 1;
	
	public static final int LOAD_ALL_ROW_SET = 2;
	
	public static final int LOAD_PREV_ROW_SET = 3;
	
	public static final int LOAD_APPEND_ROW_SET = 4;

	public static final String ALIGNMENT_LEFT = "left";
	
	public static final String ALIGNMENT_RIGHT = "right";
	
	public static final String ALIGNMENT_CENTER = "middle";
	
	public static final int NO_ORDER = TableColumnHeader.SORTED_NONE;
	
	public static final int ASCENDING_ORDER = TableColumnHeader.SORTED_ASC;
	
	public static final int DESCENDING_ORDER = TableColumnHeader.SORTED_DESC;

	/**
	 * @return
	 */
	public List getColumnNames() throws TableModelException;

	/**
	 * @return
	 */
	public Map getColumnFields() throws TableModelException;

	/**
	 * @return
	 */
	public int getRowCount() throws TableModelException;

	/**
	 * @return
	 */
	public int getColumnCount() throws TableModelException;

	/**
	 * @return
	 */
	public TableModel[] getNestedTables() throws TableModelException;

	/**
	 * @return
	 */
	public String getBackgroundColor() throws TableModelException;

	/**
	 * @return
	 */
	public Map getParameters() throws TableModelException;

	/**
	 * @param colIndex
	 * @return
	 */
	public boolean isColumnSortable(int colIndex) throws TableModelException;

	/**
	 * @param colIndex
	 * @return
	 */
	public String getColumnAlignment(int colIndex) throws TableModelException;

	/**
	 * @param colIndex
	 * @return
	 */
	public String getColumnStyle(int colIndex) throws TableModelException;

	/**
	 * @param colIndex
	 * @return
	 */
	public TableCellRenderer getColumnCellsRenderer(int colIndex) throws TableModelException;

	/**
	 * @param rowIndex
	 * @return
	 */
	public Map getNestedTableParam(int rowIndex) throws TableModelException;

	/**
	 * @param rowIndex
	 * @param colIndex
	 * @return
	 */
	public TableCellModel getCellValueAt(int rowIndex, int colIndex) throws TableModelException;

	/**
	 * @return
	 */
	public TableCellRenderer getCellRenderer() throws TableModelException;

	/**
	 * @param rowIndex
	 * @param colIndex
	 * @return
	 */
	public TableCellRenderer getCellRenderer(int rowIndex, int colIndex) throws TableModelException;

	/**
	 * @return
	 */
	public String getHeaderTitle() throws TableModelException;

	/**
	 * @return
	 */
	public TableMenu getRowMenu(int rowIndex, String menuId) throws TableModelException;

	/**
	 * @return
	 */
	public Map getAdditionalRowParameters(int rowIndex) throws TableModelException;

	/**
	 * @return
	 */
	public TableMenu getHeaderMenu(String menuId) throws TableModelException;

	public String getGroupByField() throws TableModelException;

	public String getGroupByValue() throws TableModelException;

	/**
	 * @return
	 */
	public int getTotalRowCount();

	/**
	 * @return
	 * @throws TableModelException
	 */
	public int getDisplayedCount() throws TableModelException;

	/**
	 * @return
	 * @throws TableModelException
	 */
	public int[] getDisplayedSpan() throws TableModelException;

	/**
	 * @return
	 */
	public int getPageSizeCount();

	/**
	 * @param colIndex
	 * @return
	 */
	public Map getColumnSortParameters(int colIndex);

	/**
	 * Threshold above which the Show All button is disabled.
	 * 
	 * @return
	 */
	public int getShowAllThreshold();

	/**
	 * Identifies whether to show the "Show All" or "Show Maximum" button for pagination.
	 * 
	 * @return
	 */
	public boolean IsShowAll();

	/**
	 * @return
	 */
	public int getFirstSortOrder();

	/**
	 * @return
	 */
	public boolean isGroupByRequired();

	/**
	 * @return
	 */
	public boolean isSortRequired();

	/**
	 * Returns the Time Filter applied from either global settings or metric level
	 * 
	 * @return
	 */
	public String getTimeFilterMetricText();

}
