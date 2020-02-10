package com.tibco.cep.dashboard.plugin.beviews.drilldown.export;

import java.util.List;

import com.tibco.cep.dashboard.management.ExceptionHandler;
import com.tibco.cep.dashboard.management.MessageGenerator;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.integration.TableModel;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.integration.TableModelException;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.integration.TableRequest;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.model.TableTree;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.model.TableTreeConstants;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.utils.DrillDownConfiguration;
import com.tibco.cep.dashboard.plugin.beviews.export.ExportContentNode;
import com.tibco.cep.dashboard.plugin.beviews.export.TupleFieldExportData;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;

/**
 * @author rajesh
 * 
 */
public class DrilldownExportContentHolder extends TableTreeExportContentHolder {

	private static final long serialVersionUID = 8206050996997360847L;

	public DrilldownExportContentHolder(Logger logger, ExceptionHandler exceptionHandler, MessageGenerator messageGenerator) {
		super(logger, exceptionHandler, messageGenerator);
		root = null;
	}

	@Override
	public void saveContent(TableModel parentModel, TableModel[] childModels, TableTree tableTree, TableRequest tableRequest) throws TableModelException {
		String commandType = tableRequest.getBizRequest().getParameter(TableTreeConstants.KEY_CMD);
		if (commandType == null) {
			commandType = TableTreeConstants.CMD_FULL_TABLE;
		}
		logger.log(Level.DEBUG, "Command Type is " + commandType);
		if (TableTreeConstants.CMD_FULL_TABLE.equalsIgnoreCase(commandType)) {
			logger.log(Level.DEBUG, "Initializing the tree");
			// Reinitialize the content holder
			// Create the header node
			// Create the tuple nodes
			root = new ExportContentNode(tableTree.getTablePath(), ExportContentNode.TYPE_HEADER_ROW, parentModel.getHeaderTitle());
			for (int tupleIndex = 0; tupleIndex < parentModel.getRowCount(); tupleIndex++) {
				List<TupleFieldExportData> tupleData = getTupleData(parentModel, tupleIndex);
				root.add(new ExportContentNode(tableTree.getTablePath() + "_" + tupleIndex, ExportContentNode.TYPE_TUPLE_ROW, tupleData));
			}
		} else if (TableTreeConstants.CMD_ROW_EXPAND.equalsIgnoreCase(commandType)) {
			// Create the header nodes under the tuple node

			logger.log(Level.DEBUG, "Row Expanded by user");
			// Find the node for the tuple row expanded
			String expandedRowPath = tableRequest.getRowPath();
			ExportContentNode rowContentNode = findExportContentNodeByPath(expandedRowPath);

			// Add the headerTitles of each Child Table model as ExportContentNode
			for (int childModelIndex = 0; childModelIndex < childModels.length; childModelIndex++) {
				TableModel childModel = childModels[childModelIndex];
				String childTablePath = expandedRowPath + TableTree.PATH_SEPARATOR + childModelIndex;
				rowContentNode.add(new ExportContentNode(childTablePath, ExportContentNode.TYPE_HEADER_ROW, childModel.getHeaderTitle()));
			}
		} else if (TableTreeConstants.CMD_TABLE_EXPAND.equalsIgnoreCase(commandType)) {
			// Create the tuple nodes under the header or group node

			logger.log(Level.DEBUG, "Table Expanded by user");
			// Find the header or group node which has been expanded
			String expandedHeaderPath = tableTree.getTablePath();
			ExportContentNode headerContentNode = findExportContentNodeByPath(expandedHeaderPath);

			for (int tupleIndex = 0; tupleIndex < parentModel.getRowCount(); tupleIndex++) {
				List<TupleFieldExportData> tupleData = getTupleData(parentModel, tupleIndex);
				String tupleRowPath = expandedHeaderPath + TableTree.PATH_SEPARATOR + tupleIndex;
				headerContentNode.add(new ExportContentNode(tupleRowPath, ExportContentNode.TYPE_TUPLE_ROW, tupleData));
			}
		} else if (TableTreeConstants.CMD_REFRESH.equalsIgnoreCase(commandType)) {
			logger.log(Level.DEBUG, "Table refreshed by user");
			String refreshedHeaderPath = tableTree.getTablePath();
			ExportContentNode headerContentNode = findExportContentNodeByPath(refreshedHeaderPath);
			// replace the header node content
			headerContentNode.setUserObject(parentModel.getHeaderTitle());
			// Remove all children under it
			headerContentNode.removeAllChildren();

			if (tableRequest.isLoaded()) {
				// Add the tuple content Nodes
				for (int tupleIndex = 0; tupleIndex < parentModel.getRowCount(); tupleIndex++) {
					List<TupleFieldExportData> tupleData = getTupleData(parentModel, tupleIndex);
					String tupleRowPath = refreshedHeaderPath + TableTree.PATH_SEPARATOR + tupleIndex;
					headerContentNode.add(new ExportContentNode(tupleRowPath, ExportContentNode.TYPE_TUPLE_ROW, tupleData));
				}
			} else {
				// Done in the common code above this if-else
			}
		} else if (TableTreeConstants.CMD_GROUP_BY.equalsIgnoreCase(commandType)) {
			// create the group nodes under either the parent header or group node

			logger.log(Level.DEBUG, "Table grouped by user");
			// Find the table path which has been asked to group its tupleNodes
			String tableGroupedPath = tableTree.getTablePath();
			ExportContentNode headerContentNode = findExportContentNodeByPath(tableGroupedPath);

			// Remove the current children under this groupedTableNode
			headerContentNode.removeAllChildren();

			// Add the headerTitles of each Child Table model (representing group nodes) as ExportContentNode

			// Append the 0 to the path and then append index to each path we get
			tableGroupedPath += TableTree.PATH_SEPARATOR + "0";
			for (int childModelIndex = 0; childModelIndex < childModels.length; childModelIndex++) {
				TableModel childModel = childModels[childModelIndex];
				String childTablePath = tableGroupedPath + TableTree.PATH_SEPARATOR + childModelIndex;
				headerContentNode.add(new ExportContentNode(childTablePath, ExportContentNode.TYPE_GROUP_ROW, childModel.getHeaderTitle()));
			}
		} else if (TableTreeConstants.CMD_TABLE_APPEND_ROW_SET.equalsIgnoreCase(commandType)) {
			// create the tuple nodes appended to current tuple nodes under the header or group node
			logger.log(Level.DEBUG, "Next in Append Mode: records requested by user");
			String paginatedTablePath = tableTree.getTablePath();
			ExportContentNode headerContentNode = findExportContentNodeByPath(paginatedTablePath);

			int pathIndex = headerContentNode.getChildCount();

			// Add the tuple content Nodes
			for (int tupleIndex = 0; tupleIndex < parentModel.getRowCount(); tupleIndex++, pathIndex++) {
				List<TupleFieldExportData> tupleData = getTupleData(parentModel, tupleIndex);
				String tupleRowPath = paginatedTablePath + TableTree.PATH_SEPARATOR + pathIndex;
				headerContentNode.add(new ExportContentNode(tupleRowPath, ExportContentNode.TYPE_TUPLE_ROW, tupleData));
			}
		} else if (TableTreeConstants.CMD_TABLE_NEXT_ROW_SET.equalsIgnoreCase(commandType) || TableTreeConstants.CMD_TABLE_PREV_ROW_SET.equalsIgnoreCase(commandType)) {
			// create the tuple nodes appended to current tuple nodes under the header or group node
			logger.log(Level.DEBUG, "Next/Prev page requested by user");
			String paginatedTablePath = tableTree.getTablePath();
			ExportContentNode headerContentNode = findExportContentNodeByPath(paginatedTablePath);

			int pathIndex = 0;// headerContentNode.getChildCount();
			headerContentNode.removeAllChildren();

			// Add the tuple content Nodes
			for (int tupleIndex = 0; tupleIndex < parentModel.getRowCount(); tupleIndex++, pathIndex++) {
				List<TupleFieldExportData> tupleData = getTupleData(parentModel, tupleIndex);
				String tupleRowPath = paginatedTablePath + TableTree.PATH_SEPARATOR + pathIndex;
				headerContentNode.add(new ExportContentNode(tupleRowPath, ExportContentNode.TYPE_TUPLE_ROW, tupleData));
			}
		} else if (TableTreeConstants.CMD_SORT.equalsIgnoreCase(commandType)) {
			logger.log(Level.DEBUG, "Table sorted by user");
			String refreshedHeaderPath = tableTree.getTablePath();
			ExportContentNode headerContentNode = findExportContentNodeByPath(refreshedHeaderPath);
			// replace the header node content
			headerContentNode.setUserObject(parentModel.getHeaderTitle());
			// Remove all children under it
			headerContentNode.removeAllChildren();

			if (tableRequest.isLoaded()) {
				// Add the tuple content Nodes
				for (int tupleIndex = 0; tupleIndex < parentModel.getRowCount(); tupleIndex++) {
					List<TupleFieldExportData> tupleData = getTupleData(parentModel, tupleIndex);
					String tupleRowPath = refreshedHeaderPath + TableTree.PATH_SEPARATOR + tupleIndex;
					headerContentNode.add(new ExportContentNode(tupleRowPath, ExportContentNode.TYPE_TUPLE_ROW, tupleData));
				}
			} else {
				// Done in the common code above this if-else
			}
		} else if (TableTreeConstants.CMD_EXPAND_COLLAPSE.equalsIgnoreCase(commandType)) {
			logger.log(Level.DEBUG, "Table expand/collapsed by user, path=> " + tableRequest.getTableTreePath() + ", RemoveOnCollapse = " + DrillDownConfiguration.isRemoveOnCollapse());

			// Find the headerContentNode which is collapsed/expanded
			// expanded will be recd only when the RemoveOnCollapse is configured as false.
			ExportContentNode headerContentNode = findExportContentNodeByPath(tableRequest.getTableTreePath());
			if (DrillDownConfiguration.isRemoveOnCollapse()) {
				headerContentNode.removeAllChildren();
			} else {
				// Mark the node as deleted/undeleted
				headerContentNode.setDeleted(!tableRequest.isExpanded());
			}
		} else if (TableTreeConstants.CMD_EXPAND_ALL.equalsIgnoreCase(commandType)) {
			logger.log(Level.DEBUG, "Table fully expanded by user");
			// create the root level header nodes
			// under each header node add the tuple nodes
		}
	}
}
