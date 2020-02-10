/**
 * 
 */
package com.tibco.cep.decision.table.command.impl;

import java.util.ArrayList;
import java.util.List;

import com.tibco.cep.decision.table.command.AbstractExecutableCommand;
import com.tibco.cep.decision.table.command.IExecutableCommand;
import com.tibco.cep.decision.table.command.IMemento;
import com.tibco.cep.decision.table.command.IModificationCommand;
import com.tibco.cep.decision.table.model.dtmodel.Column;
import com.tibco.cep.decision.table.model.dtmodel.ColumnType;
import com.tibco.cep.decision.table.model.dtmodel.Columns;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRule;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleSet;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;
import com.tibco.cep.decision.table.model.dtmodel.TableTypes;

/**
 * @author aathalye
 *
 */
public class ModifyCellCommand extends AbstractExecutableCommand<TableRuleVariable> implements IModificationCommand {
	
		
	/**
	 * The new value
	 */
	private ModifyCommandExpression changedExpression;
	/**
	 * @param trv
	 * @param commandReceiver
	 */
	public ModifyCellCommand(CommandStack<IExecutableCommand> commandStack,
			                 Table parent,
			                 TableTypes tableType,
			                 TableRuleVariable commandReceiver,
			                 ModifyCommandExpression changedExpression) {
		super(parent, commandReceiver, commandStack, tableType);
		this.changedExpression = changedExpression;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.command.IUndoableCommand#undo()
	 */
	@Override
	public void undo() {
		//Get the memento for this
		if (memento != null) {
			if (commandReceiver instanceof TableRuleVariable) {
				TableRuleVariable tableRuleVariable = (TableRuleVariable)commandReceiver;
				String ruleId = getContainingRuleId(tableRuleVariable.getId());
				TableRule tableRule = null;
				tableRule = searchTableType(ruleId, parent.getDecisionTable());
				if (tableRule == null) {
					tableRule = searchTableType(ruleId, parent.getExceptionTable());
				}
				//Check in DT and ET
				if (tableRule != null) {
					if (isCondition(tableRuleVariable, tableRule)) {
						for (TableRuleVariable condition : tableRule.getCondition()) {
							if (condition.getId().equals(tableRuleVariable.getId())) {
								//Set this
								condition = (TableRuleVariable)memento.getValue();
								break;
							}
						}
					} else {
						for (TableRuleVariable action : tableRule.getAction()) {
							if (action.getId().equals(tableRuleVariable.getId())) {
								//Set this
								action = (TableRuleVariable)memento.getValue();
								break;
							}
						}
					}
				}
			}
		}
		//Do nothing?
	}
	
	private TableRule searchTableType(String ruleId, 
			                          TableRuleSet tableRuleSet) {
		for (TableRule temp : tableRuleSet.getRule()) {
			if (temp.getId().equals(ruleId)) {
				return temp;
			}
		}
		return null;
	}
	
	

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.command.IModificationCommand#getModifiedObject()
	 */
	@Override
	public Object getModifiedObject() {
		return commandReceiver;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.command.IExecutableCommand#getAffectedObjects()
	 */
	public List<TableRuleVariable> getAffectedObjects() {
		List<TableRuleVariable> affectedObjects =
			new ArrayList<TableRuleVariable>();
		affectedObjects.add((TableRuleVariable)commandReceiver);
		return affectedObjects;
	}

	

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.command.IExecutableCommand#execute()
	 */
	public void execute() {
		if (commandReceiver instanceof TableRuleVariable) {
			TableRuleVariable tableRuleVariable = (TableRuleVariable)commandReceiver;
			//Check if it has any expression already
			if ((tableRuleVariable.getExpr() == null && changedExpression.getExpr() != null) 
					|| (tableRuleVariable.getExpr() != null && changedExpression.getExpr() == null) 
					|| (tableRuleVariable.getExpr() != null && !tableRuleVariable.getExpr().equals(changedExpression.getExpr()))) {
				tableRuleVariable.setExpr(changedExpression.getExpr());
				tableRuleVariable.setDisplayValue(changedExpression.getCellValue());
				
			} else {
				Object oldValue = memento.getValue();
				if (oldValue instanceof TableRuleVariable) {
					TableRuleVariable oldTableRuleVariable = (TableRuleVariable)oldValue;
					//Compare with old value because it is possible that there was some
					//old value which was removed and then entire cell became empty
					//which means the editor should become dirty.
					if (oldTableRuleVariable.getExpr().equals(changedExpression.getExpr())) {
						//Make it a defunct command
						this.setDefunct(true);
					}
				}
			}
		}
	}
	
	private String getContainingRuleId(String id) {
		String ruleId = null;
		ruleId = id.substring(0, id.indexOf('_'));
		return ruleId;
	}
	
	
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.command.IExecutableCommand#canUndo()
	 */
	public boolean canUndo() {
		return true;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.command.IExecutableCommand#shouldDirty()
	 */
	public boolean shouldDirty() {
		return true;
	}

	private boolean isCondition(TableRuleVariable trv, TableRule tableRule) {
		TableRuleSet container = (TableRuleSet)tableRule.eContainer();
		//Get columns
		Columns columns = container.getColumns();
		Column column = columns.search(trv.getColId());
		if (column != null) {
			return column.getColumnType() == ColumnType.CONDITION;
		}
		return false;
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.command.IUndoableCommand#saveMememnto(com.tibco.cep.decision.table.command.IMemento)
	 */
	public void saveMemento(IMemento memento) {
		this.memento = memento;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof ModifyCellCommand)) {
			return false;
		}
		ModifyCellCommand other = (ModifyCellCommand)obj;
		
		if (changedExpression == null) {
//			changedExpression.setExpr("");
			return false;
		}
		if (!changedExpression.equals(other.changedExpression)) {
			return false;
		}
		TableRuleVariable thisTRV = (TableRuleVariable)commandReceiver;
		TableRuleVariable otherTRV = (TableRuleVariable)other.getCommandReceiver();
		if (!thisTRV.getId().equals(otherTRV.getId())) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.command.IUndoableCommand#getValue()
	 */
	public Object getValue() {
		if (memento != null) {
			return memento.getValue();
		}
		return null;
	}
	
	public String toString() {
		return "ModifyCell Command " + changedExpression;
	}
}
