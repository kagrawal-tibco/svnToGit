/**
 * 
 */
package com.tibco.cep.decision.table.command.listeners;

import static com.tibco.cep.decision.table.ui.DecisionTableUIPlugin.debug;
import static com.tibco.cep.studio.core.utils.ModelUtils.DecisionTableUtils.getContainingRuleId;
import static com.tibco.cep.studio.core.utils.ModelUtils.DecisionTableUtils.getTableRuleInfoFromId;

import java.util.List;

import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.decision.table.checkpoint.ICheckpointDeltaEntry;
import com.tibco.cep.decision.table.command.ICommandStackListener;
import com.tibco.cep.decision.table.command.ICreationCommand;
import com.tibco.cep.decision.table.command.IExecutableCommand;
import com.tibco.cep.decision.table.command.IRemovalCommand;
import com.tibco.cep.decision.table.command.IRenameCommand;
import com.tibco.cep.decision.table.command.impl.CheckPointCommand;
import com.tibco.cep.decision.table.command.impl.CommandEvent;
import com.tibco.cep.decision.table.command.impl.CommandStack;
import com.tibco.cep.decision.table.command.impl.ModifyCellCommand;
import com.tibco.cep.decision.table.command.impl.PropertyUpdateCommand;
import com.tibco.cep.decision.table.command.memento.ColumnNameStateMemento;
import com.tibco.cep.decision.table.command.memento.ColumnPositionStateMemento;
import com.tibco.cep.decision.table.constraintpane.DecisionTable;
import com.tibco.cep.decision.table.constraintpane.DecisionTableCreator;
import com.tibco.cep.decision.table.model.dtmodel.Column;
import com.tibco.cep.decision.table.model.dtmodel.Columns;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRule;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;
import com.tibco.cep.decision.table.model.dtmodel.TableTypes;
import com.tibco.cep.studio.core.utils.ModelUtils.DecisionTableUtils.TableRuleInfo;

/**
 * @author aathalye
 *
 */
public class DecisionTableCommandStackConstraintUpdateListener implements ICommandStackListener<IExecutableCommand> {
	
	private DecisionTable constraintsTable;
	
	private Table tableEModel;
	
	private DecisionTableCreator constraintsTableCreator;
	
	private static final String CLASS = DecisionTableCommandStackConstraintUpdateListener.class.getName();
	
	public DecisionTableCommandStackConstraintUpdateListener(final DecisionTable constraintsTable, 
			                                                 final Table tableEModel) {
		this.constraintsTable = constraintsTable;
		this.tableEModel = tableEModel;
		constraintsTableCreator = new DecisionTableCreator(tableEModel);
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.command.ICommandStackListener#commandExecuted(com.tibco.cep.decision.table.command.impl.CommandEvent)
	 */
	
	public void commandExecuted(CommandEvent<IExecutableCommand> commandEvent) {
				
		Object source = commandEvent.getSource();
		if (source instanceof CheckPointCommand) {			
			CheckPointCommand checkpointCommand = (CheckPointCommand)source;			
			CommandStack<IExecutableCommand> commandStack = checkpointCommand.getOwnerStack();
			CommandStack<IExecutableCommand>.CheckpointDeltaList checkpointDeltaCommands = 
						commandStack.getCheckpointDelta();
			for (int loop = checkpointDeltaCommands.size() - 1; loop >= 0; loop--) {
				ICheckpointDeltaEntry<IExecutableCommand> deltaEntry = checkpointDeltaCommands.get(loop);
				IExecutableCommand command = deltaEntry.getCommand();
				// disable table analysis for Exception table
				// commands on exception table are ignored
				if (TableTypes.EXCEPTION_TABLE == command.getTableType()) {
					continue;
				}
				if (command instanceof IExecutableCommand) {
					debug(CLASS, "Executing constraints update for command {0}", command);
					IExecutableCommand executableCommand = (IExecutableCommand)command;
					updateConstraintsTable(executableCommand);
				}
			}
		}
	}
	
	protected DecisionTable getConstraintsTable() {
		return constraintsTable;
	}

	
	private void updateConstraintsTable(TableRuleVariable trv) {
		String colId = trv.getColId();
		Columns columns = tableEModel.getDecisionTable().getColumns();
		Column column = columns.search(colId);
		String columnAlias = column.getName();
		constraintsTable.updateCell(columnAlias, trv, column.getColumnType());
	}
	
	private void addNewCellEntry(TableRuleVariable trv) {
		String trvId = trv.getId();
		TableRuleInfo tableRuleInfo = 
			getTableRuleInfoFromId(getContainingRuleId(trvId), tableEModel);
		//TableRuleInfo could come out null because the rule was removed already
		//This is likely in the event modify was executed followed by removal in the stack
		//but the stack traversal happens such that modify comes before removal.
		if (tableRuleInfo != null) {
			addNewCellEntry(trv, tableRuleInfo.getTableRule());
		}
	}
	
	private void addNewCellEntry(TableRuleVariable trv, TableRule ruleToAddIn) {
		constraintsTableCreator.processExistingRule(constraintsTable, ruleToAddIn, trv);
	}
	
	private void updateConstraintsTable(IExecutableCommand executableCommand) {
		if (executableCommand instanceof ModifyCellCommand) {
			ModifyCellCommand modifyCellCommand = (ModifyCellCommand)executableCommand;
			updateConstraintsTable(modifyCellCommand);
		} else if (executableCommand instanceof ICreationCommand) {
			ICreationCommand creationCommand = (ICreationCommand)executableCommand;
			updateConstraintsTable(creationCommand);
		} else if (executableCommand instanceof IRemovalCommand) {
			IRemovalCommand removalCommand = (IRemovalCommand)executableCommand;
			updateConstraintsTable(removalCommand);
		} else if (executableCommand instanceof IRenameCommand) {
			IRenameCommand renameCommand = (IRenameCommand)executableCommand;
			updateConstraintsTable(renameCommand);
		} else if (executableCommand instanceof PropertyUpdateCommand) {
			PropertyUpdateCommand propertyUpdateCommand = (PropertyUpdateCommand)executableCommand;
			updateConstraintsTable(propertyUpdateCommand);
		}
	}
	
	/**
	 * 
	 * @param modifyCellCommand
	 */
	private void updateConstraintsTable(ModifyCellCommand modifyCellCommand) {
		Object commandReceiver = modifyCellCommand.getCommandReceiver();
		if (commandReceiver instanceof TableRuleVariable) {
			Object value = modifyCellCommand.getValue();
			//A table rule variable's contents may be empty in which case
			//it will not be present in constraints table
			if (value instanceof TableRuleVariable) {
				String oldExpression = ((TableRuleVariable)value).getExpr();
				if (oldExpression == null || oldExpression.length() == 0) {
					debug(CLASS, "Value is null.. New cell addition");
					addNewCellEntry((TableRuleVariable)commandReceiver);
				} else {
					updateConstraintsTable((TableRuleVariable)commandReceiver);
				}
			}
		}
	}
	
	/**
	 * 
	 * @param renameCommand
	 */
	private void updateConstraintsTable(IRenameCommand renameCommand) {
		List<?> affectedObjects = renameCommand.getAffectedObjects();
		for (Object affectedObject : affectedObjects) {
			if (affectedObject instanceof ColumnNameStateMemento) {
				ColumnNameStateMemento columnNameStateMemento = (ColumnNameStateMemento)affectedObject;
				constraintsTableCreator.renameColumn(constraintsTable, columnNameStateMemento);
			}
		}
	}
	
	/**
	 * Update toggle status of constraint table cell.
	 * @param propertyUpdateCommand
	 */
	private void updateConstraintsTable(PropertyUpdateCommand propertyUpdateCommand) {
		List<?> affectedObjects = propertyUpdateCommand.getAffectedObjects();
		for (Object affectedObject : affectedObjects) {
			if (affectedObject instanceof TableRuleVariable) {
				TableRuleVariable affectedTableRuleVariable = (TableRuleVariable)affectedObject;
				if (propertyUpdateCommand.getPropertyFeature() == PropertyUpdateCommand.PROPERTY_FEATURE.ENABLED) {
					constraintsTableCreator.toggleCellEnableStatus(constraintsTable, affectedTableRuleVariable);
				}
			}
		}
	}
	
	/**
	 * 
	 * @param removalCommand
	 */
	private void updateConstraintsTable(IRemovalCommand removalCommand) {
		List<?> affectedObjects = removalCommand.getAffectedObjects();
		
		for (Object affectedObject : affectedObjects) {
			if (affectedObject instanceof TableRule) {
				TableRule tableRule = (TableRule)affectedObject;
				debug(CLASS, "Updating constraints table for removed Rule {0}", tableRule);
				constraintsTableCreator.removeExistingRule(constraintsTable, tableRule);
			} else if (affectedObject instanceof TableRuleVariable)	{
				TableRuleVariable tableRuleVariable = (TableRuleVariable)affectedObject;
				//Update the constraints table only for non-empty cells
				if (!tableRuleVariable.getExpr().trim().isEmpty()) {
					debug(CLASS, "Updating constraints table for removed RuleVariable {0}", tableRuleVariable);
					constraintsTableCreator.removeExistingRuleVariable(constraintsTable, tableRuleVariable);
				}
			} else if (affectedObject instanceof ColumnPositionStateMemento) {
				ColumnPositionStateMemento columnPositionStateMemento = (ColumnPositionStateMemento)affectedObject;
				Column column = columnPositionStateMemento.getMonitored();
				debug(CLASS, "Updating constraints table for removed Column {0} ", column);
				constraintsTableCreator.removeExistingColumn(constraintsTable, column);
			} 
		}
	}
	
	/**
	 * 
	 * @param creationCommand
	 */
	private void updateConstraintsTable(ICreationCommand creationCommand) {
		List<?> affectedObjects = creationCommand.getAffectedObjects();
		for (Object affectedObject : affectedObjects) {
			if (affectedObject instanceof TableRule) {
				TableRule tableRule = (TableRule)affectedObject;
				debug(CLASS, "Updating constraints table with new Rule {0}", tableRule);
				constraintsTableCreator.processNewRule(constraintsTable, tableRule);
			}
			if (affectedObject instanceof TableRuleVariable) {
				TableRuleVariable tableRuleVariable = (TableRuleVariable)affectedObject;
				//A tableRule is mandatory so this should be null
				EObject commandReceiver = creationCommand.getCommandReceiver();
				if (commandReceiver instanceof TableRule) {
					addNewCellEntry(tableRuleVariable, (TableRule)commandReceiver);
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.command.ICommandStackListener#commandUndone(com.tibco.cep.decision.table.command.impl.CommandEvent)
	 */
	
	public void commandUndone(CommandEvent<IExecutableCommand> commandEvent) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if (obj instanceof DecisionTableCommandStackConstraintUpdateListener) {
			DecisionTableCommandStackConstraintUpdateListener constraintsListener = 
					(DecisionTableCommandStackConstraintUpdateListener)obj;
			if (constraintsListener.getConstraintsTable().equals(this.constraintsTable)
				&& constraintsListener.tableEModel.equals(this.tableEModel)) {
					return true;
			}
		}
		return false;
	}
}
