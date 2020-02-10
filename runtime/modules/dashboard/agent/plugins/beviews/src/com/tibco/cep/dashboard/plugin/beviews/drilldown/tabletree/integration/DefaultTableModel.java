package com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.integration;

import java.util.List;
import java.util.Map;

import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.model.TableCellRenderer;

/**
 * @author rajesh
 * 
 */
public class DefaultTableModel implements TableModel {

	public List getColumnNames() throws TableModelException {
		return null;
	}

	public int getRowCount() throws TableModelException {
		return 0;
	}

	public int getColumnCount() throws TableModelException {
		return 0;
	}

	public TableModel[] getNestedTables() throws TableModelException {
		return null;
	}

	public String getBackgroundColor() throws TableModelException {
		return null;
	}

	public Map getParameters() throws TableModelException {
		return null;
	}

	public boolean isColumnSortable(int colIndex) throws TableModelException {
		return true;
	}

	public String getColumnAlignment(int colIndex) throws TableModelException {
		return TableModel.ALIGNMENT_LEFT;
	}

	public String getColumnStyle(int colIndex) throws TableModelException {
		return "columnStyle";
	}

	public TableCellRenderer getColumnCellsRenderer(int colIndex) throws TableModelException {
		return null;
	}

	public Map getNestedTableParam(int rowIndex) throws TableModelException {
		return null;
	}

	public TableMenu getRowMenu(int rowIndex, String menuId) throws TableModelException {
		return null;
	}

	public TableCellModel getCellValueAt(int rowIndex, int colIndex) throws TableModelException {
		return null;
	}

	public TableCellRenderer getCellRenderer(int rowIndex, int colIndex) throws TableModelException {
		return null;
	}

	public String getHeaderTitle() throws TableModelException {
		return null;
	}

	public TableMenu getHeaderMenu(String menuId) throws TableModelException {
		return null;
	}

	public Map getAdditionalRowParameters(int rowIndex) throws TableModelException {
		return null;
	}

	public Map getColumnFields() throws TableModelException {
		return null;
	}

	public String getGroupByValue() throws TableModelException {
		return null;
	}

	public String getGroupByField() throws TableModelException {
		return null;
	}

	public int getTotalRowCount() {
		return 0;
	}

	public int getDisplayedCount() throws TableModelException {
		return 0;
	}

	public int getPageSizeCount() {
		return 0;
	}

	public Map getColumnSortParameters(int colIndex) {
		return null;
	}

	public int getShowAllThreshold() {
		return 0;
	}

	public TableCellRenderer getCellRenderer() throws TableModelException {
		return null;
	}

	public boolean IsShowAll() {
		return true;
	}

	public int getFirstSortOrder() {
		return NO_ORDER;
	}

	public boolean isGroupByRequired() {
		return true;
	}

	public boolean isSortRequired() {
		return true;
	}

	public int[] getDisplayedSpan() throws TableModelException {
		return null;
	}

	public String getTimeFilterMetricText() {
		return null;
	}

}