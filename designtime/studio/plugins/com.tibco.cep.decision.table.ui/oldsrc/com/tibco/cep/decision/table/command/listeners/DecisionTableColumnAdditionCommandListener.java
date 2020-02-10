/**
 * 
 */
package com.tibco.cep.decision.table.command.listeners;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.preference.IPreferenceStore;

import com.jidesoft.decision.DecisionConstants;
import com.jidesoft.decision.DecisionDataModel;
import com.jidesoft.decision.DecisionField;
import com.jidesoft.decision.UserObject;
import com.jidesoft.decision.cell.editors.custom.DefaultConverterContext;
import com.tibco.cep.decision.table.command.ICommandCreationListener;
import com.tibco.cep.decision.table.command.ICreationCommand;
import com.tibco.cep.decision.table.command.impl.AddCommand;
import com.tibco.cep.decision.table.command.impl.CommandEvent;
import com.tibco.cep.decision.table.command.memento.ColumnPositionStateMemento;
import com.tibco.cep.decision.table.editors.DecisionTableEditor;
import com.tibco.cep.decision.table.editors.IDecisionTableEditorInput;
import com.tibco.cep.decision.table.model.controller.ColumnModelController;
import com.tibco.cep.decision.table.model.dtmodel.Column;
import com.tibco.cep.decision.table.model.dtmodel.ColumnType;
import com.tibco.cep.decision.table.model.dtmodel.Columns;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleSet;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;
import com.tibco.cep.decision.table.model.dtmodel.TableTypes;
import com.tibco.cep.decision.table.preferences.PreferenceConstants;
import com.tibco.cep.decision.table.ui.DecisionTableUIPlugin;
import com.tibco.cep.decision.table.utils.DecisionTableUtil;
import com.tibco.cep.studio.core.utils.DecisionTableColumnIdGenerator;

/**
 * @author aathalye
 *
 */
public class DecisionTableColumnAdditionCommandListener implements ICommandCreationListener<AddCommand<ColumnPositionStateMemento>, ColumnPositionStateMemento> {
	
	/**
	 * The UI model to operate on
	 */
	private DecisionDataModel decisionDataModel;
	
	private Table tableEModel;
	
	private List<ColumnPositionStateMemento> affectedObjects;
	
	/**
	 * Reference to the editor we use
	 */
	private DecisionTableEditor decisionTableEditor;
	
	/**
	 * The column name to create
	 */
	private String columnName;
	
	/**
	 * The full path of the property to create
	 */
	private String propertyPath;
	
	private int propertyDataType;
	
	private boolean isArrayProperty;
	
	/**
	 * Condition/Action etc on which to create column
	 */
	private int fieldAreaType;
	
	private boolean isCustom;
		
	private boolean isSubstitutionColumn;
		
	private ColumnModelController columnModelController;

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.command.ICommandCreationListener#commandCreated(com.tibco.cep.decision.table.command.impl.CommandEvent)
	 */
	public Object commandCreated(CommandEvent<AddCommand<ColumnPositionStateMemento>> commandEvent) {
		Object source = commandEvent.getSource();
		DecisionField createdField = null;
		if (source instanceof AddCommand) {
			@SuppressWarnings("unchecked")
			AddCommand<Column> command = (AddCommand<Column>)source;
			TableTypes tableType = command.getTableType();
			createdField = executeColumnAddition(tableType);
		}
		return createdField;
	}
	
	/**
	 * Create backend model representing this column as well as
	 * the UI model column.
	 * @param tableType
	 */
	private DecisionField executeColumnAddition(TableTypes tableType) {
		//Get TableRuleSet
		TableRuleSet tableRuleSet = (tableType == TableTypes.DECISION_TABLE) ? tableEModel.getDecisionTable() : tableEModel.getExceptionTable();
		Columns columnsModel = tableRuleSet.getColumns();
		columnsModel = (columnsModel == null) ? columnModelController.createContainerColumn() : columnsModel;
		tableRuleSet.setColumns(columnsModel);
		//1.) Add to backend Model.
		//2.) Add to UI model.
		Column columnToCreate = executeBackEndModelAddition(tableType, columnsModel);
		DecisionField decisionField = executeTableModelAddition(columnToCreate, tableType);
		
		
		//Add column to the column model
		int orderIndex = getOrderIndex(decisionField);
		columnModelController.addColumn(columnsModel, orderIndex, columnToCreate);
		ColumnPositionStateMemento affectedObject = new ColumnPositionStateMemento(columnToCreate, -1);
		affectedObject.setCurrentOrderIndex(orderIndex);
		affectedObjects.add(affectedObject);
		
		return decisionField;
	}
	
	/**
	 * 
	 * @param tableType
	 * @param columnsModel
	 * @return
	 */
	private Column executeBackEndModelAddition(TableTypes tableType, Columns columnsModel) {
		//Get an unused column id
		DecisionTableColumnIdGenerator columnIdGenerator = getColumnIdGenerator();
		String columnId = DecisionTableUtil.getUnusedColumnID(tableType, columnIdGenerator, columnsModel);
		
		Column columnToCreate = columnModelController.createColumn();
		setColumnProps(columnToCreate, columnId);
		return columnToCreate;
	}
	
	/**
	 * 
	 * @param columnToCreate
	 * @param columnId
	 */
	private void setColumnProps(Column columnToCreate, String columnId) {
		//Call controller setters
		columnModelController.setColumnId(columnToCreate, columnId);
		columnModelController.setColumnName(columnToCreate, columnName);
		columnModelController.setSubstitution(columnToCreate, isSubstitutionColumn);
		columnModelController.setPropertyPath(columnToCreate, propertyPath);
		columnModelController.setPropertyType(columnToCreate, propertyDataType);
		columnModelController.setArrayProperty(columnToCreate, isArrayProperty);
		
		ColumnType columnType = 
			(fieldAreaType == DecisionConstants.AREA_CONDITION) ?
					(isCustom) ? ColumnType.CUSTOM_CONDITION : ColumnType.CONDITION 
							: (isCustom) ? ColumnType.CUSTOM_ACTION : ColumnType.ACTION; 
		columnModelController.setColumnType(columnToCreate, columnType);
	}
	
	/**
	 * Get the index at which this field was added in the UI model
	 * so that same order should be maintained in the backend model.
	 * @param decisionField
	 * @return
	 */
	private int getOrderIndex(DecisionField decisionField) {
		int fieldIndex = decisionDataModel.getDecisionFieldIndex(decisionField);
		return fieldIndex;
	}
	
	/**
	 * 
	 * @return
	 */
	private DecisionTableColumnIdGenerator getColumnIdGenerator() {
		IDecisionTableEditorInput decisionTableEditorInput = (IDecisionTableEditorInput)decisionTableEditor.getEditorInput();
		return decisionTableEditorInput.getColumnIdGenerator();
	}
	
	/**
	 * 
	 * @param column
	 * @return
	 */
	private DecisionField executeTableModelAddition(Column column, TableTypes tableType) {
		String columnId = column.getId();
		DecisionField decisionField = new DecisionField(columnName, TableRuleVariable.class);
		decisionField.setAlias(column.getAlias());
		decisionField.setId(columnId);
		UserObject userObject = new UserObject();
		userObject.setColumn(column);
		decisionField.setConverterContext(new DefaultConverterContext(columnId, userObject, column.getColumnType() == ColumnType.ACTION, tableType));
		if (fieldAreaType == DecisionConstants.AREA_CONDITION) {
			decisionDataModel.addConditionField(decisionField);
		} else {
			decisionDataModel.addActionField(decisionField);
		}
		
		//Update the internal structures
		decisionDataModel.updateConditionFields();
		decisionDataModel.updateActionFields();
		
		setFieldProps(decisionField);
		return decisionField;
	}
	
	/**
	 * 
	 * @param decisionField
	 */
	private void setFieldProps(DecisionField decisionField) {
		decisionField.setAreaType(fieldAreaType);
		if (fieldAreaType == DecisionConstants.AREA_ACTION) {
			decisionField.setAllowedAsActionField(true);
			decisionField.setAllowedAsConditionField(false);
			decisionField.setAllowedAsUnassignedField(false);
		} else {
			decisionField.setAllowedAsActionField(false);
			decisionField.setAllowedAsConditionField(true);
			decisionField.setAllowedAsUnassignedField(false);
		}

		IPreferenceStore prefStore = DecisionTableUIPlugin.getDefault().getPreferenceStore();
		//Field ID same as column ID.
		String fieldTitle = 
			(prefStore.getBoolean(PreferenceConstants.SHOW_COLUMN_ID)) ? "[" + decisionField.getId() + "] " + columnName : columnName;
		decisionField.setTitle(fieldTitle);
		decisionField.setFilterable(true);
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
			DecisionDataModel decisionDataModel, 
			Table tableEModel,
			DecisionTableEditor decisionTableEditor, 
			String columnName,
			String propertyPath, 
			int propertyDataType, 
			boolean isArrayProperty,
			int fieldAreaType,
			boolean isCustom, 
			boolean isSubstitutionColumn) {
		this.decisionDataModel = decisionDataModel;
		this.tableEModel = tableEModel;
		this.decisionTableEditor = decisionTableEditor;
		this.columnName = columnName;
		this.propertyPath = propertyPath;
		this.propertyDataType = propertyDataType;
		this.isArrayProperty = isArrayProperty;
		this.fieldAreaType = fieldAreaType;
		this.isCustom = isCustom;
		this.isSubstitutionColumn = isSubstitutionColumn;
		affectedObjects = new ArrayList<ColumnPositionStateMemento>();
		
		String projectName = ((IDecisionTableEditorInput)decisionTableEditor.getEditorInput()).getProjectName();
		columnModelController = new ColumnModelController(projectName);
	}


	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.command.ICommandCreationListener#commandUndone(com.tibco.cep.decision.table.command.impl.CommandEvent)
	 */
	public void commandUndone(CommandEvent<AddCommand<ColumnPositionStateMemento>> commandEvent) {
		Object source = commandEvent.getSource();
		if (source instanceof ICreationCommand) {
			ICreationCommand command = (ICreationCommand)source;
			Object createdObject = command.getCreatedObject();
			if (createdObject instanceof DecisionField) {
				decisionDataModel.setAdjusting(true);
				DecisionField createdField = (DecisionField)createdObject;
				int areaType = createdField.getAreaType();
				
				switch (areaType) {
				case DecisionConstants.AREA_CONDITION :
					decisionDataModel.removeConditionField(createdField);
					break;
				case DecisionConstants.AREA_ACTION :
					decisionDataModel.removeActionField(createdField);
					break;
				}
			}
		}
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
