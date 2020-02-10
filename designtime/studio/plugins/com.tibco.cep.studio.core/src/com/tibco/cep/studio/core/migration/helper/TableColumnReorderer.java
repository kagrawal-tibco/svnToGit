package com.tibco.cep.studio.core.migration.helper;

import java.io.File;
import java.net.URI;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;

import com.tibco.cep.decision.table.model.dtmodel.Column;
import com.tibco.cep.decision.table.model.dtmodel.Columns;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.migration.helper.BEProjectConfigurationHandler.AbstractTable;
import com.tibco.cep.studio.core.migration.helper.BEProjectConfigurationHandler.ConfigColumn;
import com.tibco.cep.studio.core.migration.helper.BEProjectConfigurationHandler.TableConfigurationInfo;
import com.tibco.cep.studio.core.utils.ModelUtils;

/**
 * Carry out Table model reordering based on legacy configuration
 * settings for a table.
 * @author aathalye
 *
 */
public class TableColumnReorderer {
	
	private static final String CLASS = TableColumnReorderer.class.getName();
	
	/**
	 * 
	 * @param parentFile
	 * @param tableConfigurationInfo
	 */
	public static void reorder(File parentFile, TableConfigurationInfo tableConfigurationInfo, IProgressMonitor progressMonitor) {
		//Load the table from file system
		File tableFile = new File(parentFile, tableConfigurationInfo.getTablePath() + ".rulefunctionimpl");
		if (tableFile.canRead()) {
			//Load the table
			StudioCorePlugin.debug(CLASS, "New Table File Path {0}", tableFile);
			try {
				URI fileURI = tableFile.toURI();
				Table tableEModel = (Table)CommonIndexUtils.loadEObject(fileURI);
				reorder(tableEModel, tableConfigurationInfo, progressMonitor);
				//Save model
				ModelUtils.saveEObject(tableEModel);
			} catch (Exception e) {
				StudioCorePlugin.log(e);
			}
		}
	}
	
	/**
	 * 
	 * @param tableEModel
	 * @param tableConfigurationInfo
	 */
	private static void reorder(Table tableEModel, TableConfigurationInfo tableConfigurationInfo, IProgressMonitor progressMonitor) {
		AbstractTable decisionTable = tableConfigurationInfo.getDecisionTable();
		AbstractTable exceptionTable = tableConfigurationInfo.getExceptionTable();
		//Load its columns
		if (decisionTable != null) {
			StudioCorePlugin.debug(CLASS, "Reordering decision table columns for Table {0}", tableConfigurationInfo.getTablePath());
			progressMonitor.subTask("Loading decision model for table at " + tableConfigurationInfo.getTablePath());
			Columns decisionTableColumnsModel = tableEModel.getDecisionTable().getColumns();
			progressMonitor.worked(60);
			reorderColumns(decisionTableColumnsModel, decisionTable.getConfigColumns());
		}
		if (exceptionTable != null) {
			StudioCorePlugin.debug(CLASS, "Reordering exception table columns for Table {0}", tableConfigurationInfo.getTablePath());
			progressMonitor.subTask("Loading exception model for table at " + tableConfigurationInfo.getTablePath());
			Columns exceptionTableColumnsModel = tableEModel.getExceptionTable().getColumns();
			progressMonitor.worked(20);
			reorderColumns(exceptionTableColumnsModel, exceptionTable.getConfigColumns());
		}
	}
	
	/**
	 * 
	 * @param columnsModel
	 * @param configColumns
	 */
	private static void reorderColumns(Columns columnsModel, List<ConfigColumn> configColumns) {
		int loopIndex = 0;
		for (ConfigColumn configColumn : configColumns) {
			//Search for column with this id
			Column modelColumn = columnsModel.search(configColumn.getColumnId());
			if (modelColumn != null) {
				StudioCorePlugin.debug(CLASS, "Model Column for id {0} is {1}", configColumn.getColumnId(), modelColumn);
				columnsModel.move(loopIndex++, modelColumn);
			}
		}
	}
}
