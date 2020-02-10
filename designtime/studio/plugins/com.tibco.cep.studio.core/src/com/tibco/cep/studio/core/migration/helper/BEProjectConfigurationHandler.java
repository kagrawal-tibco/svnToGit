package com.tibco.cep.studio.core.migration.helper;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.ext.DefaultHandler2;

import com.tibco.cep.decision.table.model.dtmodel.TableTypes;
import com.tibco.cep.studio.core.StudioCorePlugin;

/**
 * Handler for the sax parser to receive sax event notifications.
 * @author aathalye
 *
 */
public class BEProjectConfigurationHandler extends DefaultHandler2 {
	
	private static final String CLASS = BEProjectConfigurationHandler.class.getName();
	
	/**
	 * Root directory of project
	 */
	private File parentDirectory;
	
	private IProgressMonitor progressMonitor;
	
	/**
	 * 
	 * @param parentDirectory
	 */
	public BEProjectConfigurationHandler(File parentDirectory, IProgressMonitor progressMonitor) {
		this.parentDirectory = parentDirectory;
		this.progressMonitor = progressMonitor;
	}

	private static final String ELEM_DECISION_TABLE_RESOURCE_SETTINGS = "decisionTableResourceSettings";
	
	private static final String ELEM_DECISION_TABLE = "decisionTable";
	
	private static final String ELEM_EXCEPTION_TABLE = "exceptionTable";
	
	private static final String ELEM_COLUMN = "column";
	
	private static final String ATTR_PATH = "path";
	
	private static final String ATTR_COLUMN_ID = "id";
	
	/**
	 * Volatile instance changing as every <decisionTableResourceSettings> is encountered.
	 */
	private TableConfigurationInfo tableConfigurationInfo;
	
	/**
	 * Volatile instance changing as every <decisionTable> is encountered.
	 */
	private AbstractTable decisionTable;
	
	/**
	 * Volatile instance changing as every <exceptionTable> is encountered. 
	 */
	private AbstractTable exceptionTable;
	
	/**
	 * The current table type
	 */
	private AbstractTable currentTable;
		
	
	/* (non-Javadoc)
	 * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void endElement(String uri,
			               String localName,
			               String name)
			               throws SAXException {
		if (ELEM_DECISION_TABLE.equals(localName)) {
			tableConfigurationInfo.setDecisionTable(decisionTable);
		} else if (ELEM_EXCEPTION_TABLE.equals(localName)) {
			tableConfigurationInfo.setExceptionTable(exceptionTable);
		} else if (ELEM_DECISION_TABLE_RESOURCE_SETTINGS.equals(localName)) {
			//Do what needs to be done
			TableColumnReorderer.reorder(parentDirectory, tableConfigurationInfo, progressMonitor);
		}
	}

	/* (non-Javadoc)
	 * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 */
	@Override
	public void startElement(String uri, String localName, String name,
			Attributes attributes) throws SAXException {
		if (ELEM_DECISION_TABLE_RESOURCE_SETTINGS.equals(localName)) {
			//Create a TableConfigurationInfo
			tableConfigurationInfo = new TableConfigurationInfo();
			String tablePath = attributes.getValue("", ATTR_PATH);
			StudioCorePlugin.debug(CLASS, "Encountered table config settings for table {0}", tablePath);
			tableConfigurationInfo.setTablePath(tablePath);
		} else if (ELEM_DECISION_TABLE.equals(localName)) {
			decisionTable = new AbstractTable(TableTypes.DECISION_TABLE);
			currentTable = decisionTable;
		} else if (ELEM_EXCEPTION_TABLE.equals(localName)) {
			exceptionTable = new AbstractTable(TableTypes.EXCEPTION_TABLE);
			currentTable = exceptionTable;
		} else if (ELEM_COLUMN.equals(localName)) {
			populateConfigColumnInfo(currentTable, attributes);
		}
	}
	
	/**
	 * 
	 * @param abstractTable
	 * @param attributes
	 */
	private void populateConfigColumnInfo(AbstractTable abstractTable, Attributes attributes) {
		//Get column id
		String columnId = attributes.getValue("", ATTR_COLUMN_ID);
		ConfigColumn configColumn = new ConfigColumn(columnId);
		abstractTable.addConfigColumn(configColumn);
	}
	

	class AbstractTable {
		
		private TableTypes tableType;
		
		private List<ConfigColumn> configColumns;
		
		public AbstractTable(TableTypes tableType) {
			this.tableType = tableType;
			configColumns = new ArrayList<ConfigColumn>();
		}
		
		void addConfigColumn(ConfigColumn configColumn) {
			configColumns.add(configColumn);
		}

		/**
		 * @return the tableType
		 */
		final TableTypes getTableType() {
			return tableType;
		}
		
		final List<ConfigColumn> getConfigColumns() {
			return Collections.unmodifiableList(configColumns);
		}
	}
	
	class ConfigColumn {
		/**
		 * ID is sufficient.
		 */
		private String columnId;

		ConfigColumn(String columnId) {
			this.columnId = columnId;
		}

		/**
		 * @return the columnId
		 */
		final String getColumnId() {
			return columnId;
		}
	}
	
	class TableConfigurationInfo {
		
		private String tablePath;
		
		private AbstractTable decisionTable;
		
		private AbstractTable exceptionTable;
		
		

		/**
		 * @param tablePath the tablePath to set
		 */
		final void setTablePath(String tablePath) {
			this.tablePath = tablePath;
		}

		/**
		 * @return the decisionTable
		 */
		final AbstractTable getDecisionTable() {
			return decisionTable;
		}

		/**
		 * @param decisionTable the decisionTable to set
		 */
		final void setDecisionTable(AbstractTable decisionTable) {
			this.decisionTable = decisionTable;
		}

		/**
		 * @return the exceptionTable
		 */
		final AbstractTable getExceptionTable() {
			return exceptionTable;
		}

		/**
		 * @param exceptionTable the exceptionTable to set
		 */
		final void setExceptionTable(AbstractTable exceptionTable) {
			this.exceptionTable = exceptionTable;
		}

		/**
		 * @return the tablePath
		 */
		final String getTablePath() {
			return tablePath;
		}
	}
}
