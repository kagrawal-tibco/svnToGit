package com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.integration;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.model.NestedTableTree;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.model.TableCell;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.model.TableCells;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.model.TableColumn;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.model.TableColumnHeader;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.model.TableColumnHeaders;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.model.TableColumns;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.model.TableFooter;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.model.TableGroupByField;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.model.TableHeader;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.model.TableRow;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.model.TableRowHeader;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.model.TableRowHeaderConfigurator;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.model.TableRows;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.model.TableTree;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.model.TableTreeConstants;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.model.TableTreeExpandCollapseHandle;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.model.TableTreeExpandCollapseHandleConfigurator;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.popupmenu.model.MenuProperty;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.util.ExpandCollapseHandle;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.util.ExpandCollapseHandleConfigurator;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.util.HREFLink;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.util.JSFunction;

/**
 * @author rajesh
 * 
 */
public class TableTreeCreator {

	private TableRequest tableRequest;

	public TableTreeCreator(TableRequest tableRequest) {
		super();
		this.tableRequest = tableRequest;
	}

	/**
	 * @param name
	 * @param model
	 * @param startRowIndex
	 * @return
	 * @throws TableModelException
	 */
	private TableTree createTableTree(String name, TableModel model, int startRowIndex) throws TableModelException {
		// Create Table Tree Object
		// Params needed from Controller are: HttpServletRequest, Name
		// Params needed from Integrator are : numColumns
		// Params computed are : path, nestedtableIndex
		// TableTree tableTree = new TableTree();

		// now create the table tree
		String tableTreePath = tableRequest.getTableTreePath();
		// TABLE_TREE_WIDGET_ID is now table name
		TableTree tableTree = new TableTree(tableRequest.getBizRequest(), name, startRowIndex, model.getColumnCount(), tableTreePath);
		attachModelToTableTree(tableTree, model);
		return tableTree;
	}

	/**
	 * @param model
	 * @param tableTree
	 * @throws TableModelException
	 */
	private void attachModelToTableTree(TableTree tableTree, TableModel model) throws TableModelException {
		tableTree.setCellRenderer(model.getCellRenderer());
		tableTree.setBackgroundColor(model.getBackgroundColor());
		tableTree.setTableParameters(model.getParameters());
		tableTree.setSortable(model.isSortRequired());
	}

	TableTree createInitialTableTree(String name, TableModel model, TableTreeController controller) throws TableModelException {
		GroupByRequest groupByRequest = new GroupByRequest();
		TableTree tableTree = createTableTree(name, model, 0);

		attachTableHeader(controller, model, tableTree, groupByRequest, false);

		attachTableColumns(model, tableTree);

		attachTableColumnHeaders(controller, model, tableTree, groupByRequest);

		attachTableRows(model, controller, tableTree);

		attachTableFooter(model, controller, tableTree, groupByRequest);

		// Params needed from Controller are: HttpServletRequest
		// Params needed from Integrator are : Cell Data, Renderer(Default is
		// Column Renderer)
		// Params computed are : None
		// TableFooter tableFooter = new TableFooter();
		return tableTree;
	}

	/**
	 * @param tableTreeName
	 * @param nestedTableModels
	 * @param controller
	 * @param grouped
	 * @return
	 * @throws TableModelException
	 */
	public TableTree createNestedTables(String tableTreeName, TableModel parentTableModel, TableModel[] nestedTableModels, TableTreeController controller, boolean grouped) throws TableModelException {
		GroupByRequest groupByRequest = null;
		if (grouped) {
			groupByRequest = getNestedTableGroupByRequest();
		} else {
			groupByRequest = getParentTableGroupByRequest();
		}
		String tableTreePath = tableRequest.getTableTreePath();

		int parentRowIndex = tableRequest.getParentRowIndex();

		if (grouped) {
			parentRowIndex = 0;
		}

		int parentRowCount = tableRequest.getParentRowCount();

		TableTree parentTable = createTableTree(tableTreeName, parentTableModel, 0);

		NestedTableTree[] nestedTableTrees = new NestedTableTree[nestedTableModels.length];

		String nestedTablePath = TableTree.appendPath(tableTreePath, parentRowIndex);
		for (int nestedTableIndex = 0; nestedTableIndex < nestedTableModels.length; nestedTableIndex++) {
			TableModel nestedTableModel = nestedTableModels[nestedTableIndex];

			TableTree nestedTable = new TableTree(tableRequest.getBizRequest(), tableTreeName, 0, nestedTableModel.getColumnCount(), nestedTablePath, nestedTableIndex);
			attachModelToTableTree(nestedTable, nestedTableModel);

			attachTableHeader(controller, nestedTableModel, nestedTable, groupByRequest, grouped);
			NestedTableTree nestedTableTree = new NestedTableTree(parentTable, nestedTable, parentRowIndex, nestedTableModels.length, nestedTableIndex);
			nestedTable.setGrouped(grouped);
			nestedTableTree.setExpandCollapseHandle(getNestedTableExpandCollapseHandle(controller, parentTableModel, nestedTableModel, parentTable, nestedTable, groupByRequest));
			nestedTableTree.setBgColor(parentTableModel.getBackgroundColor());
			nestedTableTree.setParentRowCount(parentRowCount);
			nestedTableTrees[nestedTableIndex] = nestedTableTree;
		}
		parentTable.setNestedTables(nestedTableTrees);
		return parentTable;
	}

	public TableTree createExpandedTable(String name, TableModel model, TableTreeController controller, GroupByRequest groupByRequest) throws TableModelException {

		TableTree tableTree = createTableTree(name, model, 0);

		// attachTableHeader(model, tableTree);

		attachTableColumns(model, tableTree);

		attachTableColumnHeaders(controller, model, tableTree, groupByRequest);

		attachTableRows(model, controller, tableTree);

		attachTableFooter(model, controller, tableTree, groupByRequest);

		// Params needed from Controller are: HttpServletRequest
		// Params needed from Integrator are : Cell Data, Renderer(Default is
		// Column Renderer)
		// Params computed are : None
		// TableFooter tableFooter = new TableFooter();
		return tableTree;
	}

	/**
	 * @param tableTreeName
	 * @param tableModel
	 * @param controller
	 * @param bTableExpanded
	 * @return
	 * @throws TableModelException
	 */
	public TableTree createFullTable(String name, TableModel model, TableTreeController controller, GroupByRequest groupByRequest, boolean bTableExpanded) throws TableModelException {
		TableTree tableTree = createTableTree(name, model, 0);

		if (!TableTreeConstants.CMD_SORT.equalsIgnoreCase(tableRequest.getBizRequest().getParameter(TableTreeConstants.KEY_CMD))) {
			attachTableHeader(controller, model, tableTree, groupByRequest, false);
		}

		attachTableColumns(model, tableTree);

		attachTableColumnHeaders(controller, model, tableTree, groupByRequest);

		attachTableRows(model, controller, tableTree);

		attachTableFooter(model, controller, tableTree, groupByRequest);

		tableTree.setExpanded(bTableExpanded);

		tableTree.setGrouped(groupByRequest.isGrouped());
		// Params needed from Controller are: HttpServletRequest
		// Params needed from Integrator are : Cell Data, Renderer(Default is
		// Column Renderer)
		// Params computed are : None
		// TableFooter tableFooter = new TableFooter();
		return tableTree;
	}

	/**
	 * @param tableTreeName
	 * @param tableModel
	 * @param controller
	 * @return
	 * @throws TableModelException
	 */
	public TableTree createHeaderTable(String name, TableModel model, TableTreeController controller) throws TableModelException {
		GroupByRequest groupByRequest = new GroupByRequest();
		TableTree tableTree = createTableTree(name, model, 0);
		attachTableHeader(controller, model, tableTree, groupByRequest, false);
		return tableTree;
	}

	public TableTree createGroupByTable(String tableTreeName, TableModel parentTableModel, TableModel[] nestedTableModels, TableTreeController controller) throws TableModelException {
		TableTree parentTable = createNestedTables(tableTreeName, parentTableModel, nestedTableModels, controller, true);
		// GroupByRequest groupByRequest = getParentTableGroupByRequest();
		// attachTableHeader(controller, parentTableModel, parentTable, groupByRequest);
		return parentTable;
	}

	/**
	 * @param tableTreeName
	 * @param tableModel
	 * @param controller
	 * @param groupByRequest
	 * @param currentTableSize
	 * @return
	 * @throws TableModelException
	 */
	public TableTree createRowSetTable(String name, TableModel model, TableTreeController controller, GroupByRequest groupByRequest, int currentTableSize) throws TableModelException {
		TableTree tableTree = createTableTree(name, model, currentTableSize);

		attachTableRows(model, controller, tableTree);

		if (controller.isPaginationInAppendMode()) {
			tableTree.attachCommand(getUpdateFooterFunctionInAppendMode(name, model, tableTree).toString());
		} else {
			tableTree.attachCommand(getUpdateFooterFunctionInPrevNextMode(name, model, tableTree).toString());
		}
		return tableTree;
	}

	/**
	 * @param name
	 * @param model
	 * @param tableTree
	 * @return
	 * @throws TableModelException
	 */
	private JSFunction getUpdateFooterFunctionInAppendMode(String name, TableModel model, TableTree tableTree) throws TableModelException {
		JSFunction footerUpdateFunction = new JSFunction("updateFooterInAppendMode");
		footerUpdateFunction.addStringParam(name);
		footerUpdateFunction.addStringParam(tableTree.getTablePath());
		// String commandType = tableRequest.getServletRequest().getParameter(TableTreeConstants.KEY_CMD);
		footerUpdateFunction.addNonStringParam(String.valueOf(model.getTotalRowCount()));
		/*
		 * if (TableTreeConstants.CMD_TABLE_ALL_ROW_SET.equalsIgnoreCase(commandType)) { //Mark all are displayed footerUpdateFunction.addNonStringParam(String.valueOf(model.getTotalRowCount())); } else { //Mark sub set is
		 * displayed footerUpdateFunction.addNonStringParam(String.valueOf(model.getDisplayedCount())); }
		 */
		// Mark sub set is displayed
		footerUpdateFunction.addNonStringParam(String.valueOf(model.getDisplayedCount()));
		// Show All Threshold Parameter
		footerUpdateFunction.addNonStringParam(String.valueOf(model.getShowAllThreshold()));
		return footerUpdateFunction;
	}

	/**
	 * @param name
	 * @param model
	 * @param tableTree
	 * @return
	 * @throws TableModelException
	 */
	private JSFunction getUpdateFooterFunctionInPrevNextMode(String name, TableModel model, TableTree tableTree) throws TableModelException {
		JSFunction footerUpdateFunction = new JSFunction("updateFooterInPrevNextMode");
		footerUpdateFunction.addStringParam(name);
		footerUpdateFunction.addStringParam(tableTree.getTablePath());
		footerUpdateFunction.addNonStringParam(String.valueOf(model.getTotalRowCount()));
		footerUpdateFunction.addNonStringParam(String.valueOf(model.getDisplayedCount()));
		footerUpdateFunction.addNonStringParam(String.valueOf(model.getDisplayedSpan()[0]));
		footerUpdateFunction.addNonStringParam(String.valueOf(model.getDisplayedSpan()[1]));
		return footerUpdateFunction;
	}

	/**
	 * @param name
	 * @param model
	 * @param controller
	 * @return
	 * @throws TableModelException
	 */
	public TableTree createRowTable(String name, TableModel model, TableTreeController controller) throws TableModelException {
		TableTree tableTree = createTableTree(name, model, 0);
		attachTableRows(model, controller, tableTree);
		return tableTree;
	}

	/**
	 * @param tableTreeName
	 * @param requestTableModel
	 * @param nestedTableModels
	 * @param controller
	 * @param b
	 * @return
	 * @throws TableModelException
	 */
	public TableTree createNestedTablesWithColumnRows(String tableTreeName, TableModel parentTableModel, TableModel[] nestedTableModels, TableTreeController controller, boolean grouped) throws TableModelException {
		GroupByRequest groupByRequest = null;
		if (grouped) {
			groupByRequest = getNestedTableGroupByRequest();
		} else {
			groupByRequest = getParentTableGroupByRequest();
		}
		String tableTreePath = tableRequest.getTableTreePath();

		int parentRowIndex = tableRequest.getParentRowIndex();

		int parentRowCount = tableRequest.getParentRowCount();

		TableTree parentTable = createTableTree(tableTreeName, parentTableModel, 0);

		NestedTableTree[] nestedTableTrees = new NestedTableTree[nestedTableModels.length];

		String nestedTablePath = TableTree.appendPath(tableTreePath, parentRowIndex);
		for (int nestedTableIndex = 0; nestedTableIndex < nestedTableModels.length; nestedTableIndex++) {
			TableModel nestedTableModel = nestedTableModels[nestedTableIndex];

			TableTree nestedTable = new TableTree(tableRequest.getBizRequest(), tableTreeName, 0, nestedTableModel.getColumnCount(), nestedTablePath, nestedTableIndex);
			attachModelToTableTree(nestedTable, nestedTableModel);
			attachTableColumnHeaders(controller, nestedTableModel, nestedTable, groupByRequest);

			attachTableRows(nestedTableModel, controller, nestedTable);

			// attachTableHeader(controller, nestedTableModel, nestedTable, groupByRequest);
			NestedTableTree nestedTableTree = new NestedTableTree(parentTable, nestedTable, parentRowIndex, nestedTableModels.length, nestedTableIndex);
			nestedTable.setGrouped(grouped);
			nestedTableTree.setExpandCollapseHandle(getNestedTableExpandCollapseHandle(controller, parentTableModel, nestedTableModel, parentTable, nestedTable, groupByRequest));
			nestedTableTree.setBgColor(parentTableModel.getBackgroundColor());
			nestedTableTree.setParentRowCount(parentRowCount);
			nestedTableTrees[nestedTableIndex] = nestedTableTree;
		}
		parentTable.setNestedTables(nestedTableTrees);
		return parentTable;
	}

	/**
	 * @param model
	 * @param controller
	 * @param tableTree
	 * @param groupByRequest
	 * @throws TableModelException
	 */
	private void attachTableFooter(TableModel model, TableTreeController controller, TableTree tableTree, GroupByRequest groupByRequest) throws TableModelException {
		if (model.getTotalRowCount() <= model.getDisplayedCount()) {
			// All Rows displayed. No need to show the footer.
			return;
		}
		if (controller.isPaginationInAppendMode()) {
			tableTree.setFooter(createFooterInAppendMode(model, controller, tableTree, groupByRequest));
		} else {
			tableTree.setFooter(createFooterInPrevNextMode(model, controller, tableTree, groupByRequest));
		}
	}

	/**
	 * @param model
	 * @param controller
	 * @param tableTree
	 * @param groupByRequest
	 * @return
	 * @throws TableModelException
	 */
	private TableFooter createFooterInPrevNextMode(TableModel model, TableTreeController controller, TableTree tableTree, GroupByRequest groupByRequest) throws TableModelException {
		TableFooter footer = new TableFooter(tableTree, controller.isPaginationInAppendMode(), model.getTotalRowCount(), model.getPageSizeCount(), model.getDisplayedSpan());
		footer.setShowNextUrl(getCommandLink(model, controller, tableTree, groupByRequest, TableTreeConstants.CMD_TABLE_NEXT_ROW_SET));
		footer.setShowPrevUrl(getCommandLink(model, controller, tableTree, groupByRequest, TableTreeConstants.CMD_TABLE_PREV_ROW_SET));
		return footer;
	}

	/**
	 * @param model
	 * @param controller
	 * @param tableTree
	 * @param groupByRequest
	 * @return
	 * @throws TableModelException
	 */
	private TableFooter createFooterInAppendMode(TableModel model, TableTreeController controller, TableTree tableTree, GroupByRequest groupByRequest) throws TableModelException {
		TableFooter footer = new TableFooter(tableTree, controller.isPaginationInAppendMode(), model.getTotalRowCount(), model.getPageSizeCount(), model.getDisplayedSpan());
		footer.setShowAppendUrl(getCommandLink(model, controller, tableTree, groupByRequest, TableTreeConstants.CMD_TABLE_APPEND_ROW_SET));
		return footer;
	}

	/**
	 * @param model
	 * @param controller
	 * @param tableTree
	 * @throws TableModelException
	 */
	private void attachTableRows(TableModel model, TableTreeController controller, TableTree tableTree) throws TableModelException {
		// Params needed from Controller are: None
		// Params needed from Integrator are : None
		// Params computed are : None
		TableRows tableRows = new TableRows(tableTree);

		TableRowHeaderConfigurator rowHeaderConfigurator = new TableRowHeaderConfigurator("rhselected.jpg", "rhunselected.jpg", "rhselected_hover.jpg", "rhunselected_hover.jpg", "rhselected_down.jpg",
				"rhunselected_down.jpg", tableTree.getImagePath());

		// Params needed from Controller are: None
		// Params needed from Integrator are : Row Expand Params
		// Params computed are : None
		for (int rowIndex = 0; rowIndex < model.getRowCount(); rowIndex++) {
			// Params needed from Controller are: HttpServletRequest, Action Name
			// Params needed from Integrator are : NestedTable Param for Row Expansion
			// Params computed are : None
			// ExpandCollapseHandle expandCollapseHandle = new TableTreeExpandCollapseHandle();
			ExpandCollapseHandleConfigurator expandCollapseHandleConfigurator = new TableTreeExpandCollapseHandleConfigurator(tableTree, getRowExpandedLink(controller, model, model.getParameters(), model
					.getNestedTableParam(rowIndex), tableTree.getStartRowIndex() + rowIndex, tableTree.getStartRowIndex() + model.getRowCount()));
			ExpandCollapseHandle expandCollapseHandle = new TableTreeExpandCollapseHandle(tableTree, expandCollapseHandleConfigurator);

			// Params needed from Controller are: None
			// Params needed from Integrator are : Row Menu Parameters,
			// Params computed are : Row Menu Id
			TableRowHeader tableRowHeader = new TableRowHeader(tableTree, rowHeaderConfigurator);

			// Assuming for each row there will be same menu under one table
			String rowHeaderMenuId = tableTree.computeId("ROW_MENU");
			TableMenu rowMenu = model.getRowMenu(rowIndex, rowHeaderMenuId);
			if (rowMenu != null) {
				rowMenu.addShowMenuParameters(tableTree.getTableTreeName());
				rowMenu.addShowMenuParameters(tableTree.getTablePath());
				rowMenu.addShowMenuParameters(String.valueOf(tableTree.getStartRowIndex() + rowIndex));

				attachMenu(rowMenu);

				tableRowHeader.setMenuId(rowHeaderMenuId);
				tableRowHeader.setRowMenu(true);
				tableRowHeader.setUserRowMenu(rowMenu);
			} else {
				tableRowHeader.setRowMenu(false);
				tableRowHeader.setUserRowMenu(null);
			}
			// Params needed from Controller are: None
			// Params needed from Integrator are : None
			// Params computed are : rowIndex
			TableCells tableCells = new TableCells(tableTree, rowIndex);

			// Params needed from Controller are: None
			// Params needed from Integrator are : Cell Data, Renderer(Default is
			// Column Renderer)
			// Params computed are : None
			for (int colIndex = 0; colIndex < model.getColumnCount(); colIndex++) {
				TableCell tableCell = new TableCell(tableTree, model.getCellValueAt(rowIndex, colIndex), rowIndex, colIndex);
				tableCell.setCellRenderer(model.getCellRenderer(rowIndex, colIndex));
				tableCells.addCell(tableCell);
			}

			TableRow tableRow = new TableRow(tableTree, expandCollapseHandle, tableRowHeader, tableCells);
			Map mapAdditionalRowParams = model.getAdditionalRowParameters(rowIndex);

			Iterator itEntries = mapAdditionalRowParams.entrySet().iterator();
			while (itEntries.hasNext()) {
				Map.Entry entry = (Map.Entry) itEntries.next();
				tableRow.addAttribute(entry.getKey().toString(), entry.getValue().toString());
			}

			tableRows.addRow(tableRow);
		}

		tableTree.setRows(tableRows);
	}

	/**
	 * @param model
	 * @param tableTree
	 * @param controller
	 * @param groupByRequest
	 * @throws TableModelException
	 */
	private void attachTableColumnHeaders(TableTreeController controller, TableModel model, TableTree tableTree, GroupByRequest groupByRequest) throws TableModelException {
		if (model.getRowCount() == 0) {
			return;
		}
		// Params needed from Controller are: None
		// Params needed from Integrator are : None
		// Params computed are : None
		TableColumnHeaders tableColumnHeaders = new TableColumnHeaders(tableTree);

		// Params needed from Controller are: None
		// Params needed from Integrator are : Column Title, Sortable (Will use
		// true by default)
		// Params computed are : None
		int sortColIndex = tableRequest.getSortedColIndex();
		int sortState = tableRequest.getSortedState();
		for (int colIndex = 0; colIndex < model.getColumnCount(); colIndex++) {
			TableColumnHeader tableColumnHeader = new TableColumnHeader(tableTree, colIndex);
			tableColumnHeader.setText(model.getColumnNames().get(colIndex).toString());
			tableColumnHeader.setSortable(model.isColumnSortable(colIndex));
			tableColumnHeader.setServerSortLink(getSortLink(controller, model, tableTree, colIndex, groupByRequest));
			if (colIndex == sortColIndex) {
				tableColumnHeader.setSortedState(sortState);
			}
			tableColumnHeader.setFirstSortOrder(model.getFirstSortOrder());
			tableColumnHeaders.addColumn(tableColumnHeader);
		}
		tableTree.setColumnHeaders(tableColumnHeaders);
	}

	/**
	 * @param model
	 * @param tableTree
	 * @throws TableModelException
	 */
	private void attachTableColumns(TableModel model, TableTree tableTree) throws TableModelException {
		// Created Automatically
		// Params needed from Controller are: None
		// Params needed from Integrator are : None
		// Params computed are : None
		TableColumns tableColumns = new TableColumns(tableTree);

		// Params needed from Controller are: None
		// Params needed from Integrator are : Alignment (Will use left as
		// default alignment), Renderer(Will use text as default renderer), style (will use default style)
		// Params computed are : None
		for (int colIndex = 0; colIndex < model.getColumnCount(); colIndex++) {
			TableColumn tableColumn = new TableColumn(tableTree, colIndex, model.getColumnAlignment(colIndex), model.getColumnStyle(colIndex));
			tableColumn.setCellRenderer(model.getColumnCellsRenderer(colIndex));
			tableColumns.addColumn(tableColumn);
		}

		tableTree.setColumns(tableColumns);
	}

	/**
	 * @param model
	 * @param tableTree
	 * @param controller
	 * @param isGroupHeader
	 * @return
	 * @throws TableModelException
	 */
	private void attachTableHeader(TableTreeController controller, TableModel model, TableTree tableTree, GroupByRequest groupByRequest, boolean isGroupHeader) throws TableModelException {
		// Create Table Header Object
		// Params needed from Controller are: HttpServletRequest
		// Params needed from Integrator are : Header Title, Header Menu
		// Parameters
		// Params computed are : MenuId
		TableRowHeaderConfigurator headerConfigurator = null;
		headerConfigurator = new TableRowHeaderConfigurator("rhselected.jpg", "rhunselected.jpg", "rhselected_hover.jpg", "rhunselected_hover.jpg", "rhselected_down.jpg", "rhunselected_down.jpg", tableTree.getImagePath());
		TableHeader tableHeader = new TableHeader(tableTree, headerConfigurator);
		tableHeader.setText(model.getHeaderTitle());
		tableHeader.setFilterText(model.getTimeFilterMetricText());
		String headerMenuId = tableTree.computeId("HEADER_MENU");
		;
		tableHeader.setHeaderMenu(true);
		tableHeader.setMenuId(headerMenuId);
		TableMenu tableMenu = model.getHeaderMenu(headerMenuId);
		tableMenu.addShowMenuParameters(getRefreshLink(controller, model, tableTree, groupByRequest));
		tableMenu.addShowMenuParameters(tableTree.getTableTreeName());
		tableMenu.addShowMenuParameters(tableTree.getTablePath());

		if (!groupByRequest.isGrouped()) {
			tableMenu.addMenuItem(new MenuProperty("refreshtable", "Refresh"));
		}

		attachMenu(tableMenu);

		tableHeader.setUserHeaderMenu(tableMenu);
		tableTree.setHeader(tableHeader);

		attachTableGroupBy(model, tableHeader, tableTree, controller, groupByRequest, isGroupHeader);

		return;
	}

	/**
	 * @param model
	 * @param tableHeader
	 * @param table
	 * @param controller
	 * @param groupByRequest
	 * @param isGroupHeader
	 * @throws TableModelException
	 */
	private void attachTableGroupBy(TableModel model, TableHeader tableHeader, TableTree table, TableTreeController controller, GroupByRequest groupByRequest, boolean isGroupHeader) throws TableModelException {
		if (!controller.isGroupByEnabled() || !model.isGroupByRequired() || model.getRowCount() <= 0) {
			return;
		}

		// Associate Group By Section
		// Params needed from Controller are: HttpServletRequest : Map of
		// GroupBy Field and Group By Field Value
		// Params needed from Integrator are : None
		// Params computed are : Field List using Column Headers, MenuId

		String groupByMenuId = table.computeId("GROUP_BY_MENU");
		TableMenu groupByMenu = getGroupByMenu(groupByMenuId, model, table, controller, groupByRequest, isGroupHeader);
		if (groupByMenu.getMenuItems().length == 0) {
			// No Group By menu items returned, hence no need to show the group by button here.
			return;
		}

		tableHeader.setGroupByEnabled(controller.isGroupByEnabled() && model.isGroupByRequired());
		groupByMenu.setReplaceOld(true);
		tableHeader.setGroupByMenu(groupByMenu);
		attachMenu(groupByMenu);

		Map mapColumnFields = model.getColumnFields();
		List listNestedGroupByFields = groupByRequest.getGroupByListForNestedTableHeaders();
		Iterator itCombinedGroupByFields = listNestedGroupByFields.iterator();
		List listGroupByFields = new ArrayList();
		while (itCombinedGroupByFields.hasNext()) {
			String groupFieldName = (String) itCombinedGroupByFields.next();
			String groupFieldDisplayName = mapColumnFields.get(groupFieldName).toString();
			StringBuffer fieldMenuParam = new StringBuffer();
			fieldMenuParam.append("" + GroupByRequest.GROUP_FIELD + "=");
			fieldMenuParam.append(groupFieldName);
			TableGroupByField tableGroupBy = new TableGroupByField(table, groupFieldName, groupFieldDisplayName, fieldMenuParam.toString());
			listGroupByFields.add(tableGroupBy);
		}
		tableHeader.setGroupByFields(listGroupByFields);

		return;
	}

	private TableMenu getGroupByMenu(String menuId, TableModel model, TableTree table, TableTreeController controller, GroupByRequest groupByRequest, boolean isGroupHeader) throws TableModelException {

		TableMenu tableMenu = new TableMenu(menuId, "groupbymenu", "GroupBy", true);

		Map columnFields = model.getColumnFields();

		List menuProperties = new ArrayList();
		Iterator itColumnFields = columnFields.entrySet().iterator();

		List listNestedGroupByFields = groupByRequest.getGroupByListForNestedTableHeaders();
		// Table Group By MenuItems : fieldList - (currentList + newField)
		List combinedGroupByList = groupByRequest.getGroupMenuFilterList();// getCombinedGroupByList();
		while (itColumnFields.hasNext()) {
			Map.Entry entry = (Entry) itColumnFields.next();
			String fieldName = (String) entry.getKey();
			String displayName = (String) entry.getValue();
			if (combinedGroupByList.contains(fieldName)) {
				continue;
			}
			if (fieldName.equals(groupByRequest.getGroupByField())) {
				continue;
			}
			StringBuffer fieldMenuParam = new StringBuffer();
			fieldMenuParam.append("" + GroupByRequest.GROUP_FIELD + "=");
			fieldMenuParam.append(fieldName);
			// Added Display Name to the Param to show the customized name on the header for grouped by fields.
			fieldMenuParam.append("&fieldTitle=" + displayName);
			MenuProperty menuProperty = new MenuProperty(fieldName, displayName, fieldMenuParam.toString());
			menuProperty.setBEnable(!listNestedGroupByFields.contains(fieldName));
			menuProperties.add(menuProperty);
			JSFunction enableMenuItem = new JSFunction("enableGroupByMenuItem");
			enableMenuItem.setReturn(false);
			enableMenuItem.addStringParam(menuId);
			enableMenuItem.addStringParam(fieldName);
			table.attachCommand(enableMenuItem.toString());
		}
		// MenuProperty[] menuProperties = new MenuProperty[columnFields.size()];
		tableMenu.addMenuItems((MenuProperty[]) menuProperties.toArray(new MenuProperty[0]));

		tableMenu.setShowMenuFunctionName("showGroupByMenu");

		String groupByLink = getGroupByLink(controller, table, model, groupByRequest);
		tableMenu.addShowMenuParameters(groupByLink);
		tableMenu.addShowMenuParameters(controller.getTableTreeName());
		tableMenu.addShowMenuParameters(table.getTablePath());
		tableMenu.addShowMenuParameters(getParentTableTreePath(table.getTablePath()));

		if (isGroupHeader == false) {
			if (table.getTablePath().equals(TableTreeConstants.ROOT_TABLE_PATH)) {
				tableMenu.addShowMenuParameters("0");
				tableMenu.addShowMenuParameters(getRefreshLink(controller, model, table, groupByRequest));
			} else {
				String tablePath = table.getTablePath(); // "0_0_0"
				String parentTablePath = getParentTableTreePath(table.getTablePath());// "0"
				String rowPath = "";
				// output to be "0_0"
				String[] paths = tablePath.split("_");
				String[] parentPaths = parentTablePath.split("_");
				for (int i = parentPaths.length; i < paths.length; i++) {
					if (rowPath.length() == 0) {
						rowPath = paths[i];
					} else {
						rowPath += "_" + paths[i];
					}
				}
				tableMenu.addShowMenuParameters(rowPath);
				tableMenu.addShowMenuParameters(getGroupByRestoreLink(controller, model, table, groupByRequest, parentTablePath, rowPath));
			}
		}

		// String subPath = parentRowIndex + TableTree.PATH_SEPARATOR + nestedTableIndex;
		// tableMenu.addShowMenuParameters(subPath);
		// expandCollapseHandle.setSubPath(subPath);
		// tableMenu.addShowMenuParameters(expandCollapseHandle.getLink());

		table.addCustomAttribute("groupByUrl", groupByLink);
		return tableMenu;
	}

	private String getGroupByRestoreLink(TableTreeController controller, TableModel tableModel, TableTree table, GroupByRequest groupByRequest, String parentTablePath, String rowPath) throws TableModelException {

		HREFLink link = new HREFLink(tableRequest.getContextPath(), controller.getActionURL());
		link.addParameter(TableTreeConstants.KEY_CMD, TableTreeConstants.CMD_TABLE_EXPAND);
		link.addParameter(TableTreeConstants.KEY_ID, controller.getTableTreeName());
		link.addParameter(TableTreeConstants.KEY_PATH, table.getTablePath());
		link.addParameter(TableTreeConstants.KEY_BG_COLOR, tableModel.getBackgroundColor());
		link.addParameter(TableTreeConstants.KEY_ROW_INDEX, table.getTableIndex());
		link.addParameter(TableTreeConstants.KEY_ROW_PATH, rowPath);
		link.addParameter(TableTreeConstants.KEY_PARENT_TABLE_PATH, parentTablePath);
		link.addParameter(TableTreeConstants.KEY_PARENT_TABLE_COLUMN, tableModel.getColumnCount());
		link.addParameters(tableModel.getParameters());
		return link.toString();
	}

	private String getGroupByLink(TableTreeController controller, TableTree table, TableModel tableModel, GroupByRequest groupByRequest) throws TableModelException {
		/*
		 * // /executive/createtabletree.do? cmd=groupby&amp; tableTreeId=events&amp; tableTreePath=0_0_0&amp; bgcolor=%23B1BDC5&amp; typeid=009%404924%40009%404924&amp; parentinstanceid=1000&amp;
		 * parenttypeid=023%406148%40023%406148
		 */
		// Table Group By Menu URL : normal url with group by action + ((currentList + newField) as grouplist)
		HREFLink link = new HREFLink(tableRequest.getContextPath(), controller.getActionURL());
		link.addParameter(TableTreeConstants.KEY_CMD, TableTreeConstants.CMD_GROUP_BY);
		link.addParameter(TableTreeConstants.KEY_ID, controller.getTableTreeName());
		link.addParameter(TableTreeConstants.KEY_PATH, table.getTablePath());
		link.addParameter(TableTreeConstants.KEY_BG_COLOR, tableModel.getBackgroundColor());
		link.addParameter(TableTreeConstants.KEY_COLUMN_COUNT, tableModel.getColumnCount());
		link.addParameter(TableTreeConstants.KEY_ROW_INDEX, table.getTableIndex());
		link.addParameter(TableTreeConstants.KEY_ROW_PATH, table.getTableIndex());
		link.addParameters(tableModel.getParameters());
		groupByRequest.updateModelGroupFieldParam(link, tableModel.getGroupByValue());
		return link.toString();
	}

	/**
	 * @param controller
	 * @param rowCount
	 * @param rowIndex
	 * @param nestedTableParam
	 * @return
	 * @throws TableModelException
	 */
	private String getRowExpandedLink(TableTreeController controller, TableModel tableModel, Map tableParams, Map mapNestedTableParams, int rowIndex, int rowCount) throws TableModelException {
		HREFLink link = new HREFLink(tableRequest.getContextPath(), controller.getActionURL());
		link.addParameter(TableTreeConstants.KEY_CMD, TableTreeConstants.CMD_ROW_EXPAND);
		link.addParameter(TableTreeConstants.KEY_ID, controller.getTableTreeName());
		link.addParameter(TableTreeConstants.KEY_PATH, tableRequest.getTableTreePath());
		link.addParameter(TableTreeConstants.KEY_COLUMN_COUNT, tableModel.getColumnCount());
		link.addParameter(TableTreeConstants.KEY_BG_COLOR, tableModel.getBackgroundColor());
		link.addParameter(TableTreeConstants.KEY_ROW_COUNT, String.valueOf(rowCount));
		link.addParameters(tableParams);
		link.addParameters(mapNestedTableParams);
		return link.toString();
	}

	/**
	 * @param controller
	 * @param nestedTableParam
	 * @return
	 * @throws TableModelException
	 */
	private String getRefreshLink(TableTreeController controller, TableModel tableModel, TableTree table, GroupByRequest groupByRequest) throws TableModelException {
		// http://localhost:6161/executive/createtabletree.do?cmd=tableexpand&tableTreeId=events&parentTablePath=0&parentTableColumn=14&bgcolor=%23B1BDC5&tableTreePath=0_0_0&typeid=009%404924%40009%404924&parentinstanceid=1000&parenttypeid=023%406148%40023%406148&rowIndex=0&rowPath=0_0
		HREFLink link = new HREFLink(tableRequest.getContextPath(), controller.getActionURL());
		if (table.getTablePath() == "0") {
			link.addParameters(tableModel.getParameters());
		} else {
			link.addParameter(TableTreeConstants.KEY_CMD, TableTreeConstants.CMD_REFRESH);
			link.addParameter(TableTreeConstants.KEY_ID, controller.getTableTreeName());
			link.addParameter(TableTreeConstants.KEY_PATH, table.getTablePath());
			link.addParameter(TableTreeConstants.KEY_BG_COLOR, tableModel.getBackgroundColor());
			link.addParameter(TableTreeConstants.KEY_PARENT_TABLE_PATH, tableRequest.getBizRequest().getParameter(TableTreeConstants.KEY_PATH));
			String parentRow = tableRequest.getBizRequest().getParameter(TableTreeConstants.KEY_ROW_INDEX);
			if (parentRow != null) {
				parentRow = TableTree.appendPath(parentRow, table.getTableIndex());
			} else {
				parentRow = String.valueOf(table.getTableIndex());
			}

			link.addParameter(TableTreeConstants.KEY_ROW_PATH, parentRow);
			link.addParameters(tableModel.getParameters());
			groupByRequest.updateGroupFieldParam(link);
		}
		return link.toString();
	}

	/**
	 * @param controller
	 * @param colIndex
	 * @param groupByRequest
	 * @param nestedTableParam
	 * @return
	 * @throws TableModelException
	 */
	private String getSortLink(TableTreeController controller, TableModel tableModel, TableTree table, int colIndex, GroupByRequest groupByRequest) throws TableModelException {
		// http://localhost:6161/executive/createtabletree.do?cmd=tableexpand&tableTreeId=events&parentTablePath=0&parentTableColumn=14&bgcolor=%23B1BDC5&tableTreePath=0_0_0&typeid=009%404924%40009%404924&parentinstanceid=1000&parenttypeid=023%406148%40023%406148&rowIndex=0&rowPath=0_0
		HREFLink link = new HREFLink(tableRequest.getContextPath(), controller.getActionURL());
		if (table.getTablePath() == "0") {
			link.addParameter(TableTreeConstants.KEY_SORT_COLUMN_INDEX, String.valueOf(colIndex));
			link.addParameters(tableModel.getColumnSortParameters(colIndex));
		} else {
			link.addParameter(TableTreeConstants.KEY_CMD, TableTreeConstants.CMD_SORT);
			link.addParameter(TableTreeConstants.KEY_ID, controller.getTableTreeName());
			link.addParameter(TableTreeConstants.KEY_PATH, table.getTablePath());
			link.addParameter(TableTreeConstants.KEY_BG_COLOR, tableModel.getBackgroundColor());
			link.addParameter(TableTreeConstants.KEY_SORT_COLUMN_INDEX, String.valueOf(colIndex));
			link.addParameter(TableTreeConstants.KEY_PARENT_TABLE_PATH, tableRequest.getBizRequest().getParameter(TableTreeConstants.KEY_PARENT_TABLE_PATH));
			link.addParameter(TableTreeConstants.KEY_ROW_PATH, tableRequest.getBizRequest().getParameter(TableTreeConstants.KEY_ROW_PATH));
			link.addParameters(tableModel.getColumnSortParameters(colIndex));
			groupByRequest.updateModelGroupFieldParam(link, null);
		}
		// LOGGER.log(Level.INFO, "Sort Link created with Group filters ::" + link.toString());
		return link.toString();
	}

	private ExpandCollapseHandle getNestedTableExpandCollapseHandle(TableTreeController controller, TableModel parentModel, TableModel nestedModel, TableTree parentTable, TableTree nestedTable, GroupByRequest groupByRequest)
			throws TableModelException {
		HREFLink link = new HREFLink(tableRequest.getContextPath(), controller.getActionURL());
		link.addParameter(TableTreeConstants.KEY_ID, controller.getTableTreeName());
		link.addParameter(TableTreeConstants.KEY_PARENT_TABLE_PATH, tableRequest.getTableTreePath());
		link.addParameter(TableTreeConstants.KEY_PARENT_TABLE_COLUMN, parentModel.getColumnCount());
		link.addParameter(TableTreeConstants.KEY_BG_COLOR, parentModel.getBackgroundColor());
		link.addParameter(TableTreeConstants.KEY_PATH, nestedTable.getTablePath());
		link.addParameters(nestedModel.getParameters());
		// link.addParameters(getNewGroupByFieldMap(nestedModel, groupByRequest));
		if (groupByRequest.updateGroupFieldParam(link)) {
			link.addParameter(TableTreeConstants.KEY_CMD, TableTreeConstants.CMD_GROUP_BY);
		} else {
			link.addParameter(TableTreeConstants.KEY_CMD, TableTreeConstants.CMD_TABLE_EXPAND);
		}
		groupByRequest.updateModelGroupFieldParam(link, nestedModel.getGroupByValue());
		String groupRowExpandedLink = link.toString();
		ExpandCollapseHandleConfigurator expandCollapseHandleConfigurator = new TableTreeExpandCollapseHandleConfigurator(parentTable, groupRowExpandedLink);

		ExpandCollapseHandle collapseHandle = new TableTreeExpandCollapseHandle(parentTable, expandCollapseHandleConfigurator);
		nestedTable.setExpandCollapseHandle(collapseHandle);
		getOnlyNestedTableExpandCollapseHandle(controller, parentModel, nestedModel, parentTable, groupByRequest);
		return collapseHandle;
	}

	private void getOnlyNestedTableExpandCollapseHandle(TableTreeController controller, TableModel parentModel, TableModel nestedModel, TableTree nestedTable, GroupByRequest groupByRequest) throws TableModelException {
		HREFLink link = new HREFLink(tableRequest.getContextPath(), controller.getActionURL());
		link.addParameter(TableTreeConstants.KEY_CMD, TableTreeConstants.CMD_TABLE_EXPAND);
		link.addParameter(TableTreeConstants.KEY_ID, controller.getTableTreeName());
		link.addParameter(TableTreeConstants.KEY_PARENT_TABLE_PATH, tableRequest.getTableTreePath());
		link.addParameter(TableTreeConstants.KEY_PARENT_TABLE_COLUMN, parentModel.getColumnCount());
		link.addParameter(TableTreeConstants.KEY_BG_COLOR, parentModel.getBackgroundColor());
		link.addParameter(TableTreeConstants.KEY_PATH, nestedTable.getTablePath());
		link.addParameters(nestedModel.getParameters());
		groupByRequest.updateModelGroupFieldParam(link, nestedModel.getGroupByValue());
		String groupRowExpandedLink = link.toString();
		nestedTable.addCustomAttribute("tableExpandUrl", groupRowExpandedLink);
	}

	/**
	 * @param model
	 * @param controller
	 * @param tableTree
	 * @param groupByRequest
	 * @return
	 * @throws TableModelException
	 */
	private String getCommandLink(TableModel model, TableTreeController controller, TableTree tableTree, GroupByRequest groupByRequest, String commandType) throws TableModelException {
		HREFLink link = new HREFLink(tableRequest.getContextPath(), controller.getActionURL());
		link.addParameter(TableTreeConstants.KEY_CMD, commandType);
		link.addParameter(TableTreeConstants.KEY_ID, controller.getTableTreeName());
		link.addParameter(TableTreeConstants.KEY_PATH, tableTree.getTablePath());
		link.addParameter(TableTreeConstants.KEY_BG_COLOR, model.getBackgroundColor());
		link.addParameters(model.getParameters());
		groupByRequest.updateModelGroupFieldParam(link, model.getGroupByValue());
		return link.toString();
	}

	/**
	 * @param tableMenu
	 */
	private void attachMenu(TableMenu tableMenu) {
		List menuList = (List) tableRequest.getBizRequest().getAttribute("menus");
		if (menuList == null) {
			menuList = new ArrayList();
			tableRequest.getBizRequest().setAttribute("menus", menuList);
		}
		if (!menuList.contains(tableMenu)) {
			menuList.add(tableMenu);
		}
	}

	/**
	 * @return
	 */
	private GroupByRequest getNestedTableGroupByRequest() {
		GroupByRequest groupByRequest = new GroupByRequest(tableRequest.getBizRequest());
		return groupByRequest;
	}

	/**
	 * @return
	 */
	private GroupByRequest getParentTableGroupByRequest() {
		return new GroupByRequest(tableRequest.getBizRequest());
	}

	/**
	 * @param tableTreePath
	 * @return
	 */
	public static String getParentTableTreePath(String tableTreePath) {
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

	/*
	 * Group By Thoughts
	 * 
	 * When applied thru menu
	 * 
	 * For current table
	 * 
	 * Model: GroupField: front of (currentList + newField)
	 * 
	 * Table Expand Link : No worry. - DONE Table Group By Field display : currentList + newField - DONE Table Group By MenuItems : fieldList - (currentList + newField) - DONE Table Group By Menu URL : normal url with group
	 * by action + ((currentList + newField) as grouplist) - DONE Table Refresh Link : normal url with group by action + currentList + (newField as newField)
	 * 
	 * 
	 * For nested tables Assume currentList as (currentList + newField) - top newField as rear of the currentList
	 * 
	 * GroupField: front of currentList
	 * 
	 * if (currentList.size() > 0) { currentList as currentList - rear
	 * 
	 * Table Expand Link : normal url with group by action + currentList + newField Table Group By Field display : currentList + newField Table Group By MenuItems : fieldList - (currentList + newField) Table Group By Menu URL
	 * : normal url with group by action + (currentList + newField) Table Refresh Link : normal url with group by action + currentList + (newField as newField) } else { Table Expand Link : normal url with table expand action
	 * Table Group By Field display : None Table Group By MenuItems : fieldList Table Group By Menu URL : normal url with group by action Table Refresh Link : normal url with refresh }
	 * 
	 * 
	 * When applied thru grouptable expand URL: normal url with group by action + currentList + newField
	 * 
	 * For current table
	 * 
	 * Model: GroupField: front of (currentList + newField)
	 * 
	 * Table Expand Link : No worry. Table Group By Field display : currentList + newField Table Group By MenuItems : fieldList - (currentList + newField) Table Group By Menu URL : normal url with group by action +
	 * ((currentList + newField) as grouplist) Table Refresh Link : normal url with group by action + currentList + (newField as newField)
	 * 
	 * 
	 * For nested tables Assume currentList as (currentList + newField) - top newField as rear of the currentList
	 * 
	 * GroupField: front of currentList
	 * 
	 * if (currentList.size() > 0) { currentList as currentList - rear
	 * 
	 * Table Expand Link : normal url with group by action + currentList + newField Table Group By Field display : currentList + newField Table Group By MenuItems : fieldList - (currentList + newField) Table Group By Menu URL
	 * : normal url with group by action + (currentList + newField) Table Refresh Link : normal url with group by action + currentList + (newField as newField) } else { Table Expand Link : normal url with table expand action
	 * Table Group By Field display : None Table Group By MenuItems : fieldList Table Group By Menu URL : normal url with group by action Table Refresh Link : normal url with refresh }
	 * 
	 * 
	 * 
	 * When applied thru refresh URL: normal url with group by action + currentList + (newField as newField)
	 * 
	 * For current table
	 * 
	 * Model: GroupField: front of (currentList + newField)
	 * 
	 * Table Expand Link : No worry. Table Group By Field display : currentList + newField Table Group By MenuItems : fieldList - (currentList + newField) Table Group By Menu URL : normal url with group by action +
	 * ((currentList + newField) as grouplist) Table Refresh Link : normal url with group by action + currentList + newField as groupby
	 * 
	 * 
	 * For nested tables Assume currentList as (currentList + newField) - top newField as rear of the currentList
	 * 
	 * GroupField: front of currentList
	 * 
	 * if (currentList.size() > 0) { currentList as currentList - rear
	 * 
	 * Table Expand Link : normal url with group by action + currentList + newField Table Group By Field display : currentList + newField Table Group By MenuItems : fieldList - (currentList + newField) Table Group By Menu URL
	 * : normal url with group by action + (currentList + newField) Table Refresh Link : normal url with group by action + currentList + newField as groupby } else { Table Expand Link : normal url with table expand action
	 * Table Group By Field display : None Table Group By MenuItems : fieldList Table Group By Menu URL : normal url with group by action Table Refresh Link : normal url with refresh }
	 */

}