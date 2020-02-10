package com.tibco.cep.studio.decision.table.command.listeners;

import java.util.ArrayList;
import java.util.List;

import com.tibco.cep.decision.table.command.ICommandCreationListener;
import com.tibco.cep.decision.table.command.IUndoableCommand;
import com.tibco.cep.decision.table.command.impl.CommandEvent;
import com.tibco.cep.decision.table.command.impl.RemoveCommand;
import com.tibco.cep.decision.table.model.dtmodel.Column;
import com.tibco.cep.decision.table.model.dtmodel.ColumnType;
import com.tibco.cep.decision.table.model.dtmodel.Columns;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRule;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleSet;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;
import com.tibco.cep.decision.table.model.dtmodel.TableTypes;
import com.tibco.cep.studio.decision.table.editor.DecisionTableDesignViewer;

public class DecisionTableCellRemoveCommandListener implements ICommandCreationListener<RemoveCommand<TableRuleVariable>, TableRuleVariable>{

	private DecisionTableDesignViewer formViewer;
	private Table table;
	private TableRule rule;
	private TableRuleVariable ruleVariable;
	
	public DecisionTableCellRemoveCommandListener(DecisionTableDesignViewer formViewer, Table table, TableRule rule, TableRuleVariable ruleVariable) {
		this.formViewer = formViewer;
		this.table = table;
		this.rule = rule;
		this.ruleVariable = ruleVariable;
	}

	@Override
	public Object commandCreated(CommandEvent<RemoveCommand<TableRuleVariable>> commandEvent) {
		Object source = commandEvent.getSource();
		if (source instanceof IUndoableCommand) {
			IUndoableCommand command = (IUndoableCommand)source;
			if (command instanceof RemoveCommand<?>) {
				if (isCondition(ruleVariable, rule)) {
					rule.getCondition().remove(ruleVariable);
				} else {
					rule.getAction().remove(ruleVariable);
				}
				if (command.getTableType() == TableTypes.DECISION_TABLE) {
					formViewer.getDecisionTable().refresh();					
				}
				else if (command.getTableType() == TableTypes.EXCEPTION_TABLE) {
					formViewer.getExceptionTable().refresh();
				}
				return ruleVariable;
			}
		}
		return null;
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
	
	@Override
	public void commandUndone(CommandEvent<RemoveCommand<TableRuleVariable>> commandEvent) {
		Object source = commandEvent.getSource();
		if (source instanceof IUndoableCommand) {
			IUndoableCommand command = (IUndoableCommand)source;
			if (command instanceof RemoveCommand<?>) {
				RemoveCommand<?> removeCellCommand = (RemoveCommand<?>)command;
				if (isCondition(ruleVariable, rule)) {
					rule.getCondition().add(ruleVariable);
				} else {
					rule.getAction().add(ruleVariable);
				}
				if (removeCellCommand.getTableType() == TableTypes.DECISION_TABLE) {
					formViewer.getDecisionTable().refresh();					
				}
				else if (removeCellCommand.getTableType() == TableTypes.EXCEPTION_TABLE) {
					formViewer.getExceptionTable().refresh();
				}
			}
		}
	}

	public String getContainingRuleId(String trvId) {
		String ruleId = null;
		ruleId = trvId.substring(0, trvId.indexOf('_'));
		return ruleId;
	}

	@Override
	public List<TableRuleVariable> getAffectedObjects() {
		List<TableRuleVariable> objs = new ArrayList<TableRuleVariable>();
		objs.add(ruleVariable);
		return objs;
	}

}
