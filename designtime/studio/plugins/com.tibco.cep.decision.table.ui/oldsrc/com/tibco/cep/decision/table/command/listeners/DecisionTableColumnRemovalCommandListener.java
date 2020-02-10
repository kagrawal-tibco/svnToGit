/**
 * 
 */
package com.tibco.cep.decision.table.command.listeners;

import java.util.ArrayList;
import java.util.List;

import com.jidesoft.decision.DecisionConstants;
import com.jidesoft.decision.DecisionDataModel;
import com.jidesoft.decision.DecisionField;
import com.jidesoft.decision.DecisionFieldBox;
import com.jidesoft.decision.DecisionRule;
import com.jidesoft.decision.DecisionTablePane;
import com.tibco.cep.decision.table.command.CommandFacade;
import com.tibco.cep.decision.table.command.ICommandCreationListener;
import com.tibco.cep.decision.table.command.ICommandErrorListener;
import com.tibco.cep.decision.table.command.IRemovalCommand;
import com.tibco.cep.decision.table.command.impl.CommandEvent;
import com.tibco.cep.decision.table.command.impl.RemoveCommand;
import com.tibco.cep.decision.table.command.memento.ColumnPositionStateMemento;
import com.tibco.cep.decision.table.editors.DecisionTableEditor;
import com.tibco.cep.decision.table.editors.IDecisionTableEditorInput;
import com.tibco.cep.decision.table.model.controller.ColumnModelController;
import com.tibco.cep.decision.table.model.dtmodel.Column;
import com.tibco.cep.decision.table.model.dtmodel.ColumnType;
import com.tibco.cep.decision.table.model.dtmodel.Columns;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRule;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleSet;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;
import com.tibco.cep.decision.table.model.dtmodel.TableTypes;
import com.tibco.cep.studio.core.utils.ModelUtils;

/**
 * @author aathalye
 *
 */
public class DecisionTableColumnRemovalCommandListener implements ICommandCreationListener<RemoveCommand<Object>, Object> {
	
	/**
	 * The UI command receiver
	 */
	private DecisionTablePane decisionTablePane;
	
	private String project;
	
	/**
	 * Contain the removed column and its associated TRVs
	 */
	private List<Object> affectedObjects;
	
	private DecisionFieldBox fieldBoxToRemove;
	
	private ColumnModelController columnModelController;
	

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.command.ICommandCreationListener#commandCreated(com.tibco.cep.decision.table.command.impl.CommandEvent)
	 */
	public Object commandCreated(CommandEvent<RemoveCommand<Object>> commandEvent) {
		Object source = commandEvent.getSource();
		if (source instanceof IRemovalCommand) {
			IRemovalCommand command = (IRemovalCommand)source;
			TableTypes tableType = command.getTableType();
			Table tableEModel = command.getParent();
			int removalIndex = decisionTablePane.removeFieldBox(fieldBoxToRemove);
			if (removalIndex == -1) {
				takeErrorAction(command);
				return null;
			}
			//If removal is successful return the same field box
			//Look in DecisionTableModelListener for row removal after all columns have been removed.
			DecisionDataModel decisionDataModel = decisionTablePane.getDecisionDataModel();
			int columnCount = 
				decisionDataModel.getConditionFieldCount() + decisionDataModel.getActionFieldCount();
			if (columnCount == 0) {
				flushRules(decisionDataModel, tableEModel);
			}
			DecisionField removedField = fieldBoxToRemove.getField();
			removeRelevantColumnEntries(removedField, tableEModel, tableType);
			return new RemovedObject<DecisionField>(removalIndex, removedField);
		}
		return null;
	}
	
	class RemovedObject<T> {
		/**
		 * The index at which to insert if needed
		 */
		private int index;
		
		private T object;

		/**
		 * @param index
		 * @param object
		 */
		RemovedObject(int index, T object) {
			this.index = index;
			this.object = object;
		}

		/**
		 * @return the index
		 */
		final int getIndex() {
			return index;
		}

		/**
		 * @return the object
		 */
		final T getObject() {
			return object;
		}
	}
	
	private void flushRules(DecisionDataModel decisionDataModel, Table tableEModel) {
		List<DecisionRule> allRules = decisionDataModel.getRules();
		ICommandCreationListener<RemoveCommand<TableRule>, TableRule> listener = 
			new DecisionTableRowRemovalCommandListener(decisionDataModel, tableEModel,
					allRules.toArray(new DecisionRule[allRules.size()]));
	
		CommandFacade.getInstance().executeRemoval(project, tableEModel, TableTypes.DECISION_TABLE, listener);
	}
	/**
	 * Remove the backend column and all its relevant conditions/actions
	 * @param decisionField
	 * @param tableType
	 */
	private void removeRelevantColumnEntries(DecisionField decisionField,
			                                 Table tableEModel,
			                                 TableTypes tableType) {
		//Get column name
		String columnName = decisionField.getName();
		TableRuleSet tableRuleSet = (tableType == TableTypes.DECISION_TABLE) ? tableEModel.getDecisionTable() : tableEModel.getExceptionTable();
		Columns columns = tableRuleSet.getColumns();
		ColumnType columnType = 
			(decisionField.getAreaType() == DecisionConstants.AREA_CONDITION) ? 
					ColumnType.CONDITION : ColumnType.ACTION;
		removeTableRuleVariables(tableRuleSet, columnName, columnType);
		Column column = columns.searchByOrdinal(columnName, columnType);
		//Get its position before remove
		int position = columns.getColumn().indexOf(column);
		ColumnPositionStateMemento columnPositionStateMemento = new ColumnPositionStateMemento(column, position);
		columnModelController.removeColumn(columns, column);
		affectedObjects.add(columnPositionStateMemento);
	}
	
	
		
	private void takeErrorAction(IRemovalCommand command) {
		//It cannot be removed
		//Remove this command itself
		ICommandErrorListener<IRemovalCommand> errorListener = 
			new DefaultCommandErrorListener<IRemovalCommand>();
		CommandEvent<IRemovalCommand> commandEvent = new CommandEvent<IRemovalCommand>(command);
		commandEvent.setError(true);
		errorListener.takeAction(commandEvent);
	}

	/**
	 * @param decisionTablePane
	 * @param tableEModel
	 * @param fieldBoxToRemove
	 */
	public DecisionTableColumnRemovalCommandListener(
			DecisionTablePane decisionTablePane, Table tableEModel,
			DecisionFieldBox fieldBoxToRemove) {
		this.decisionTablePane = decisionTablePane;
		this.fieldBoxToRemove = fieldBoxToRemove;
		affectedObjects = new ArrayList<Object>();
		DecisionTableEditor decisionTableEditor = decisionTablePane.getDecisionTableEditor();
		IDecisionTableEditorInput editorInput = (IDecisionTableEditorInput)decisionTableEditor.getEditorInput();
		project = editorInput.getProjectName();
		columnModelController = new ColumnModelController(project);
	}

	/**
	 * Remove all conditions/actions using this column id
	 * before the column id is removed
	 * @param tableRuleSet
	 * @param columnName
	 * @param columnType
	 */
	private void removeTableRuleVariables(TableRuleSet tableRuleSet,
			                              String columnName, 
			                              ColumnType columnType) {
		if (tableRuleSet == null) {
			return;
		}
		// Get all columns
		Columns columns = tableRuleSet.getColumns();
		Column column = columns.searchByOrdinal(columnName, columnType);
		if (column == null) {
			return;
		}
		// Get its id
		String colId = column.getId();
		// Get all rules in it
		List<TableRule> allRules = tableRuleSet.getRule();

		for (TableRule tableRule : allRules) {
			switch (columnType.massagedOrdinal()) {
			case 1: {
				List<TableRuleVariable> conditions = tableRule.getCondition();
				// Search for condition with this col id
				TableRuleVariable toRemove = null;
				for (TableRuleVariable trv : conditions) {
					if (trv.getColId().equals(colId) && !trv.getExpr().isEmpty()) {
						toRemove = trv;
						break;
					}
				}
				conditions.remove(toRemove);
				if (toRemove != null) {
					affectedObjects.add(toRemove);
				}
				break;
			}
			case 2: {
				List<TableRuleVariable> actions = tableRule.getAction();
				// Search for action with this col id
				TableRuleVariable toRemove = null;
				for (TableRuleVariable trv : actions) {
					if (trv.getColId().equals(colId) && !trv.getExpr().isEmpty()) {
						toRemove = trv;
						break;
					}
				}
				actions.remove(toRemove);
				if (toRemove != null) {
					affectedObjects.add(toRemove);
				}
				break;
			}
			}
		}
	}
	
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.command.ICommandCreationListener#commandUndone(com.tibco.cep.decision.table.command.impl.CommandEvent)
	 */
	public void commandUndone(CommandEvent<RemoveCommand<Object>> commandEvent) {
		Object source = commandEvent.getSource();
		if (source instanceof IRemovalCommand) {
			IRemovalCommand removalCommand = (IRemovalCommand)source;
			if (removalCommand.isDefunct()) {
				return;
			}
			Object removedObject = removalCommand.getRemovedObject();
			Table tableEModel = removalCommand.getParent();
			if (removedObject instanceof RemovedObject) {
				@SuppressWarnings("unchecked")
				RemovedObject<DecisionField> removedFieldBox = 
					(RemovedObject<DecisionField>)removedObject;
				DecisionField decisionField = removedFieldBox.getObject();
				int removalIndex = removedFieldBox.getIndex();
				DecisionDataModel decisionDataModel = decisionTablePane.getDecisionDataModel();
				decisionDataModel.setAdjusting(true);
				
				addColumnEntries(decisionDataModel, tableEModel, removalCommand.getTableType());
				int areaType = decisionField.getAreaType();
				switch (areaType) {
				case DecisionConstants.AREA_CONDITION :
					decisionDataModel.addConditionField(removalIndex, decisionField);
					break;
				case DecisionConstants.AREA_ACTION :
					int insertIndex = removalIndex - decisionDataModel.getConditionFieldCount();
					decisionDataModel.addActionField(insertIndex, decisionField);
					break;
				}
			}
		}
	}
	
	/**
	 * Add a {@link TableRuleVariable} back into the column
	 * @param tableRuleSet
	 * @param column
	 * @param index
	 * @param tableRuleVariable
	 */
	private void addTableRuleVariable(TableRuleSet tableRuleSet,
                                      Column column,
                                      int index,
                                      TableRuleVariable tableRuleVariable) {
		String trvId = tableRuleVariable.getId();
		//Get tableRule into which the TRV has to be added
		TableRule tableRule = null;
		String containingRuleId = 
			ModelUtils.DecisionTableUtils.getContainingRuleId(trvId);
		for (TableRule tr : tableRuleSet.getRule()) {
			if (tr.getId().equals(containingRuleId)) {
				tableRule = tr;
				break;
			}
		}
		switch (column.getColumnType()) {
		case CONDITION :
		case CUSTOM_CONDITION :
			int nConditions = tableRule.getCondition().size();
			index = (index <= nConditions) ? index : nConditions;
			tableRule.getCondition().add(index, tableRuleVariable);
			break;
		case ACTION :
		case CUSTOM_ACTION :
			DecisionDataModel decisionDataModel = decisionTablePane.getDecisionDataModel();
			//We have to do this because empty condition columns are not entered
			//in the backend model resulting in incorrect count
			int insertIndex = index - decisionDataModel.getConditionFieldCount();
			int nActions = tableRule.getAction().size();
			insertIndex = (insertIndex <= nActions) ? insertIndex : nActions;
			tableRule.getAction().add(insertIndex, tableRuleVariable);
			break;
		}
	}
	
	/**
	 * @param decisionDataModel
	 * @param tableEModel
	 * @param tableType
	 */
	private void addColumnEntries(DecisionDataModel decisionDataModel, 
			                      Table tableEModel,
			                      TableTypes tableType) {
		TableRuleSet tableRuleSet = 
			(tableType == TableTypes.DECISION_TABLE) ? tableEModel.getDecisionTable()
					: tableEModel.getExceptionTable();
		//Get all affected objects
		Column column = null;
		//This is the same index where we will add the entries.
		//However this index begins with 1
		int colIndex = -1;
		for (int loop = affectedObjects.size() - 1; loop >= 0; loop--) {
			Object affectedObject = affectedObjects.get(loop);
			if (affectedObject instanceof ColumnPositionStateMemento) {
				ColumnPositionStateMemento columnPositionStateMemento = (ColumnPositionStateMemento)affectedObject;
				column = columnPositionStateMemento.getMonitored();
				//Get original position
				Integer position = (Integer)columnPositionStateMemento.getValue();
				columnModelController.addColumn(tableRuleSet.getColumns(), position, column);
			}
			if (affectedObject instanceof TableRuleVariable) {
				if (column != null) {
					TableRuleVariable tableRuleVariable = (TableRuleVariable)affectedObject;
					if (colIndex != -1) {
						addTableRuleVariable(tableRuleSet, column, colIndex - 1, tableRuleVariable);
					}
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.command.ICommandCreationListener#getAffectedObjects()
	 */
	public List<Object> getAffectedObjects() {
		return affectedObjects;
	}
}
