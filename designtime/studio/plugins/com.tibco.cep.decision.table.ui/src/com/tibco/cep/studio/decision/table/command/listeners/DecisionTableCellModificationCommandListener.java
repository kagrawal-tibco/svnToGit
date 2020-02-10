package com.tibco.cep.studio.decision.table.command.listeners;

import com.tibco.cep.decision.table.command.ICommandStackListener;
import com.tibco.cep.decision.table.command.IUndoableCommand;
import com.tibco.cep.decision.table.command.impl.CommandEvent;
import com.tibco.cep.decision.table.command.impl.CommandStack;
import com.tibco.cep.decision.table.command.impl.ModifyCellCommand;
import com.tibco.cep.decision.table.command.IExecutableCommand;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;
import com.tibco.cep.decision.table.model.dtmodel.TableTypes;
import com.tibco.cep.studio.decision.table.editor.DecisionTableDesignViewer;

public class DecisionTableCellModificationCommandListener implements ICommandStackListener<IExecutableCommand>{

	private DecisionTableDesignViewer formViewer;
	
	public DecisionTableCellModificationCommandListener(DecisionTableDesignViewer formViewer) {
		this.formViewer = formViewer;
	}
	
	@Override
	public void commandExecuted(CommandEvent<IExecutableCommand> commandEvent) {
		Object source = commandEvent.getSource();
		if (source instanceof IUndoableCommand) {
			IUndoableCommand command = (IUndoableCommand)source;
			CommandStack<IExecutableCommand> commandStack = command.getOwnerStack();
			if (command instanceof ModifyCellCommand) {
				ModifyCellCommand modifyCellCommand = (ModifyCellCommand)command;
				TableRuleVariable changedVariable = (TableRuleVariable) modifyCellCommand.getModifiedObject();
				TableRuleVariable trv = (TableRuleVariable) modifyCellCommand.getCommandReceiver();
				trv.setExpr(changedVariable.getExpr());
			}
		}
	}

	@Override
	public void commandUndone(CommandEvent<IExecutableCommand> commandEvent) {
		Object source = commandEvent.getSource();
		if (source instanceof IUndoableCommand) {
			IUndoableCommand command = (IUndoableCommand)source;
			CommandStack<IExecutableCommand> commandStack = command.getOwnerStack();
			if (command instanceof ModifyCellCommand) {
				ModifyCellCommand modifyCellCommand = (ModifyCellCommand)command;
				TableRuleVariable changedVariable = (TableRuleVariable) modifyCellCommand.getValue();
				TableRuleVariable trv = (TableRuleVariable) modifyCellCommand.getCommandReceiver();
				trv.setExpr(changedVariable.getExpr());
				//TODO refresh only the required table using TableTypes
				if (modifyCellCommand.getTableType() == TableTypes.DECISION_TABLE) {
					formViewer.getDecisionTable().refresh();					
				}
				else if (modifyCellCommand.getTableType() == TableTypes.EXCEPTION_TABLE) {
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

}
