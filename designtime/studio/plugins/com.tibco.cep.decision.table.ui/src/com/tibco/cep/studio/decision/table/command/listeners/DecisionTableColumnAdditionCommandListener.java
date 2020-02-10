/**
 *
 */
package com.tibco.cep.studio.decision.table.command.listeners;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.grid.layer.GridLayer;
import org.eclipse.nebula.widgets.nattable.group.ColumnGroupHeaderLayer;
import org.eclipse.nebula.widgets.nattable.group.event.GroupColumnsEvent;

import com.tibco.cep.decision.table.command.ICommandCreationListener;
import com.tibco.cep.decision.table.command.ICreationCommand;
import com.tibco.cep.decision.table.command.impl.AddCommand;
import com.tibco.cep.decision.table.command.impl.CommandEvent;
import com.tibco.cep.decision.table.command.memento.ColumnPositionStateMemento;
import com.tibco.cep.decision.table.model.controller.ColumnModelController;
import com.tibco.cep.decision.table.model.dtmodel.Column;
import com.tibco.cep.decision.table.model.dtmodel.ColumnType;
import com.tibco.cep.decision.table.model.dtmodel.Columns;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRule;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleSet;
import com.tibco.cep.decision.table.model.dtmodel.TableTypes;
import com.tibco.cep.studio.core.utils.DecisionTableColumnIdGenerator;
import com.tibco.cep.studio.decision.table.editor.DTColumnHeaderLayerStack;
import com.tibco.cep.studio.decision.table.editor.DecisionTableDesignViewer;
import com.tibco.cep.studio.decision.table.editor.IDecisionTableEditor;
import com.tibco.cep.studio.decision.table.editor.input.IDecisionTableEditorInput;
import com.tibco.cep.studio.decision.table.utils.DecisionTableUtil;

/**
 *
 *
 */
public class DecisionTableColumnAdditionCommandListener implements ICommandCreationListener<AddCommand<ColumnPositionStateMemento>, ColumnPositionStateMemento> {


	private Table tableEModel;

	private List<ColumnPositionStateMemento> affectedObjects;

	/**
	 * Reference to the editor we use
	 */
	private IDecisionTableEditor decisionTableSWTEditor;

	private TableRuleSet targetRuleSet;
	private NatTable targetTable;

	/**
	 * The column name to create
	 */
	private String columnName;

	/**
	 * The column type to create
	 */
	private ColumnType columnType;

	/**
	 * The full path of the property to create
	 */
	private String propertyPath;

	private int propertyDataType;

	private boolean isArrayProperty;

	private boolean isSubstitutionColumn;

	private ColumnModelController columnModelController;

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.command.ICommandCreationListener#commandCreated(com.tibco.cep.decision.table.command.impl.CommandEvent)
	 */
	public Object commandCreated(CommandEvent<AddCommand<ColumnPositionStateMemento>> commandEvent) {
		Object source = commandEvent.getSource();
		Column createdField = null;
		if (source instanceof AddCommand) {
			@SuppressWarnings("unchecked")
			AddCommand<Column> command = (AddCommand<Column>)source;
			TableTypes tableType = command.getTableType();
			createdField = executeColumnAddition(tableType);
		}
		//TODO : return appropriate object
		return createdField;
	}

	/**
	 * Create backend model representing this column as well as
	 * the UI model column.
	 * @param tableType
	 */
	private Column executeColumnAddition(TableTypes tableType) {
		//Get TableRuleSet
		TableRuleSet tableRuleSet = (tableType == TableTypes.DECISION_TABLE) ? tableEModel.getDecisionTable() : tableEModel.getExceptionTable();
		Columns columnsModel = tableRuleSet.getColumns();
		columnsModel = (columnsModel == null) ? columnModelController.createContainerColumn() : columnsModel;
		tableRuleSet.setColumns(columnsModel);
		Column columnToCreate = addColumn(tableType, columnsModel);
		ColumnPositionStateMemento affectedObject = new ColumnPositionStateMemento(columnToCreate, -1);
		affectedObject.setCurrentOrderIndex(getOrderIndex(columnsModel, columnToCreate));
		affectedObjects.add(affectedObject);
		decisionTableSWTEditor.modified();
		return columnToCreate;
	}


	/**
	 *
	 * @return
	 */
	private DecisionTableColumnIdGenerator getColumnIdGenerator() {
		return decisionTableSWTEditor.getColumnIdGenerator();
	}

	/**
	 *
	 * @param decisionDataModel
	 * @param tableEModel
	 * @param decisionTableEditor
	 * @param columnName
	 * @param propertyPath
	 * @param propertyDataType
	 * @param isArrayProperty
	 * @param fieldAreaType
	 * @param isCustom
	 * @param isPrimitive
	 * @param isSubstitutionColumn
	 * @param isContainedRefConceptProp
	 */
	public DecisionTableColumnAdditionCommandListener(
			IDecisionTableEditor decisionTableSWTEditor,
			TableRuleSet targetRuleSet,
			NatTable targetTable,
			ColumnType colType,
			String projectName,
			String columnName,
			String propertyPath,
			int propertyDataType,
			boolean isArrayProperty,
			boolean isCustom,
			boolean isSubstitutionColumn) {
		this.decisionTableSWTEditor = decisionTableSWTEditor;
		this.targetRuleSet = targetRuleSet;
		this.targetTable = targetTable;
		this.columnName = columnName;
		this.propertyPath = propertyPath;
		this.propertyDataType = propertyDataType;
		this.isArrayProperty = isArrayProperty;
		this.isSubstitutionColumn = isSubstitutionColumn;
		affectedObjects = new ArrayList<ColumnPositionStateMemento>();
		columnModelController = new ColumnModelController(projectName);
		tableEModel = decisionTableSWTEditor.getTable();
		this.columnType = colType;
	}


	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.command.ICommandCreationListener#commandUndone(com.tibco.cep.decision.table.command.impl.CommandEvent)
	 */
	public void commandUndone(CommandEvent<AddCommand<ColumnPositionStateMemento>> commandEvent) {
		Object source = commandEvent.getSource();
		if (source instanceof ICreationCommand) {
			ICreationCommand command = (ICreationCommand)source;
			Object createdObject = command.getCreatedObject();
			TableTypes tableType = command.getTableType();
			TableRuleSet tableRuleSet = (tableType == TableTypes.DECISION_TABLE) ? tableEModel.getDecisionTable() : tableEModel.getExceptionTable();
			EList<Column> columns = tableRuleSet.getColumns().getColumn();
			columns.remove(createdObject);

			DTColumnHeaderLayerStack<TableRule> columnHeaderLayer = (DTColumnHeaderLayerStack<TableRule>) ((GridLayer)targetTable.getLayer()).getColumnHeaderLayer();
			ColumnGroupHeaderLayer headerLayer = columnHeaderLayer.getColumnGroupHeaderLayer();
			headerLayer.clearAllGroups();
			decisionTableSWTEditor.getDtDesignViewer().configureColumnGroups(targetRuleSet, columnHeaderLayer);
			headerLayer.fireLayerEvent(new GroupColumnsEvent(headerLayer));

			targetTable.refresh();
			decisionTableSWTEditor.modified();
		}
	}

	/**
	 *
	 * @param columnsModel
	 * @param tableType
	 * @return
	 */
	private Column setColumnProps(Columns columnsModel, TableTypes tableType) {
		//Get an unused column id
		DecisionTableColumnIdGenerator columnIdGenerator = getColumnIdGenerator();
		String columnId = DecisionTableUtil.getUnusedColumnID(tableType, columnIdGenerator, columnsModel);
		Column columnToCreate = columnModelController.createColumn();

		//Call controller setters
		columnModelController.setColumnId(columnToCreate, columnId);
		columnModelController.setColumnName(columnToCreate, columnName);
		columnModelController.setSubstitution(columnToCreate, isSubstitutionColumn);
		columnModelController.setPropertyPath(columnToCreate, propertyPath);
		columnModelController.setPropertyType(columnToCreate, propertyDataType);
		columnModelController.setArrayProperty(columnToCreate, isArrayProperty);
		columnModelController.setColumnType(columnToCreate, columnType);

		return columnToCreate;
	}
	
	/**
	 * Get the index at which this field was added in the UI model
	 * so that same order should be maintained in the backend model.
	 * @param decisionField
	 * @return
	 */
	private int getOrderIndex(Columns columnsModel, Column columnToCreate) {
		EList<Column> columns = columnsModel.getColumn();
		if (columns.size() > 0) {
			int actionCounter = 0;
			int conditionCounter = 0;
			for (Column column : columns) {
				if (column.equals(columnToCreate)) {
					continue;
				}
				if (ColumnType.CONDITION == column.getColumnType() || ColumnType.CUSTOM_CONDITION == column.getColumnType()) {
					conditionCounter++;
				}
				else if (ColumnType.ACTION == column.getColumnType() || ColumnType.CUSTOM_ACTION == column.getColumnType()) {
					actionCounter++;
				}
			}
			if (ColumnType.CONDITION == columnToCreate.getColumnType() || ColumnType.CUSTOM_CONDITION == columnToCreate.getColumnType()) {
				return conditionCounter;
			}
			else if(ColumnType.ACTION == columnToCreate.getColumnType() || ColumnType.CUSTOM_ACTION == columnToCreate.getColumnType()) {
				return conditionCounter + actionCounter;
			}
		}
		return -1;
	}

	protected Column addColumn(TableTypes tableType, Columns columnsModel) {
		DTColumnHeaderLayerStack<TableRule> columnHeaderLayer = (DTColumnHeaderLayerStack<TableRule>) ((GridLayer)targetTable.getLayer()).getColumnHeaderLayer();
		Column newColumn = setColumnProps(columnsModel, tableType);
		EList<Column> columns = columnsModel.getColumn();

		int idx = columns.size();
		if (columnType == ColumnType.CUSTOM_CONDITION || columnType == ColumnType.CONDITION && idx > 0) {
			for (int i=0; i<columns.size(); i++) {
				idx = i;
				Column column = columns.get(i);
				if (column.getColumnType() != ColumnType.CONDITION && column.getColumnType() != ColumnType.CUSTOM_CONDITION) {
					break;
				}
			}
		}
		columns.add(idx, newColumn);
		ColumnGroupHeaderLayer headerLayer = columnHeaderLayer.getColumnGroupHeaderLayer();
		if (newColumn.getColumnType() == ColumnType.CONDITION || newColumn.getColumnType() == ColumnType.CUSTOM_CONDITION) {
			// just reconfigure rather than off-setting each subsequent column
			headerLayer.clearAllGroups();
			decisionTableSWTEditor.getDtDesignViewer().configureColumnGroups(targetRuleSet, columnHeaderLayer);
		} else {
			headerLayer.addColumnsIndexesToGroup(DecisionTableDesignViewer.ACTIONS_COLUMN_GROUP, idx);
		}
		headerLayer.fireLayerEvent(new GroupColumnsEvent(headerLayer));

		targetTable.refresh();
		return newColumn;
	}



	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.command.ICommandCreationListener#getAffectedObjects()
	 */

	public List<ColumnPositionStateMemento> getAffectedObjects() {
		return affectedObjects;
	}

	/**
	 * @return the columnModelController
	 */
	public final ColumnModelController getColumnModelController() {
		return columnModelController;
	}
}
