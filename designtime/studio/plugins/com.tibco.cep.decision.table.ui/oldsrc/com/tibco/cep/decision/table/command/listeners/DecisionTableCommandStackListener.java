/**
 * 
 */
package com.tibco.cep.decision.table.command.listeners;

import java.util.List;

import com.jidesoft.decision.DecisionDataModel;
import com.jidesoft.decision.DecisionField;
import com.jidesoft.decision.DecisionTableModel;
import com.tibco.cep.decision.table.command.ICommandStackListener;
import com.tibco.cep.decision.table.command.IExecutableCommand;
import com.tibco.cep.decision.table.command.IUndoableCommand;
import com.tibco.cep.decision.table.command.impl.CommandEvent;
import com.tibco.cep.decision.table.command.impl.CommandStack;
import com.tibco.cep.decision.table.command.impl.ModifyCellCommand;
import com.tibco.cep.decision.table.model.dtmodel.Column;
import com.tibco.cep.decision.table.model.dtmodel.ColumnType;
import com.tibco.cep.decision.table.model.dtmodel.Columns;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleSet;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;
import com.tibco.cep.decision.table.model.dtmodel.TableTypes;
import com.tibco.cep.studio.core.utils.ModelUtils;

/**
 * Handle cell modification undo requests.
 * @author aathalye
 *
 */
public class DecisionTableCommandStackListener implements ICommandStackListener<IExecutableCommand> {
	
	/**
	 * UI model
	 */
	private DecisionTableModel decisionTableModel;
	
	/**
	 * Backend Model
	 */
	private Table decisionTableEModel;
	
	public DecisionTableCommandStackListener(DecisionTableModel decisionTableModel, 
			                                 Table decisionTableEModel) {
		this.decisionTableModel = decisionTableModel;
		this.decisionTableEModel = decisionTableEModel;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.command.ICommandStackListener#commandExecuted()
	 */
	public void commandExecuted(CommandEvent<IExecutableCommand> commandEvent) {
		
	}
	
	private int getContainingRuleIdIndex(TableRuleVariable trv) {
		//Get id
		String id = trv.getId();
		String ruleId = 
			ModelUtils.DecisionTableUtils.getContainingRuleId(id);
		if (ruleId == null) {
			return -1;
		}
		DecisionDataModel decisionDataModel = decisionTableModel.getDecisionDataModel();
		//Get index in UI model
		final int indexInUIModel = decisionDataModel.getRowIndex(Integer.parseInt(ruleId));
		return indexInUIModel;
	}
	
	private void handleModification(final ModifyCellCommand command, 
			                        final CommandStack<IExecutableCommand> commandStack) {
		
		// since we added 2 listeners for decision table and exception table, we must check 
		// that the handleModification() method of the correct listener is called
		if(command.getTableType() != decisionTableModel.getDecisionDataModel().getTableType())	{
			return;
		}
		
		Object commandReceiver = command.getCommandReceiver();
		if (commandReceiver instanceof TableRuleVariable) {
			TableRuleVariable trv = (TableRuleVariable)commandReceiver;
			String colId = trv.getColId();
			DecisionField decisionField = getDecisionField(colId, command.getTableType());
			//Get index in UI model
			final int indexInUIModel = getContainingRuleIdIndex(trv);
			int columnIndex = decisionTableModel.getDecisionFieldIndex(decisionField);
			/**
			 * The index is reduced by one in following method giving incorrect
			 * decision entry, hence increment by 1
			 */
			TableRuleVariable oldValue = (TableRuleVariable)command.getValue();
			decisionTableModel.setValueAt(oldValue, indexInUIModel, columnIndex + 1);
		}
	}
	
	private DecisionField getDecisionField(String colId, TableTypes tableType) {
		//Handled exception table		
		TableRuleSet decisionTableRuleSet = 
			(tableType == TableTypes.DECISION_TABLE) ? decisionTableEModel.getDecisionTable() : decisionTableEModel.getExceptionTable();
		Columns columns = decisionTableRuleSet.getColumns();
		//Search for column with this id
		Column column = columns.search(colId);
		DecisionField decisionField = null;
		DecisionDataModel decisionDataModel = decisionTableModel.getDecisionDataModel();
		if (column != null) {
			ColumnType columnType = column.getColumnType();
			String columnName = column.getName();
			switch (columnType) {
			case CONDITION :
			case CUSTOM_CONDITION :	{
				List<DecisionField> conditionFields = decisionDataModel.getConditionFields();
				for (DecisionField conditionField : conditionFields) {
					if (conditionField.getName().equals(columnName)) {
						decisionField = conditionField;
						break;
					}
				}
				break;
			}
			case ACTION :
			case CUSTOM_ACTION :	{
				List<DecisionField> actionFields = decisionDataModel.getActionFields();
				for (DecisionField actionField : actionFields) {
					if (actionField.getName().equals(columnName)) {
						decisionField = actionField;
						break;
					}
				}
				break;
			}
			}
		}
		return decisionField;
	}
	
	

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.command.ICommandStackListener#commandUndone()
	 */
	
	public void commandUndone(CommandEvent<IExecutableCommand> commandEvent) {
		Object source = commandEvent.getSource();
		if (source instanceof IUndoableCommand) {
			IUndoableCommand command = (IUndoableCommand)source;
			CommandStack<IExecutableCommand> commandStack = command.getOwnerStack();
			if (command instanceof ModifyCellCommand) {
				ModifyCellCommand modifyCellCommand = (ModifyCellCommand)command;
				handleModification(modifyCellCommand, commandStack);
			}
		}
	}
}
