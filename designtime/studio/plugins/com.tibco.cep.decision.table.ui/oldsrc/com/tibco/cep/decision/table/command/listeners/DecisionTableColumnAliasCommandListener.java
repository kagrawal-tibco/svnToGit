/**
 * 
 */
package com.tibco.cep.decision.table.command.listeners;

import java.util.ArrayList;
import java.util.List;

import com.jidesoft.decision.DecisionDataModel;
import com.jidesoft.decision.DecisionField;
import com.jidesoft.decision.DecisionTablePane;
import com.tibco.cep.decision.table.command.ICommandCreationListener;
import com.tibco.cep.decision.table.command.IRenameCommand;
import com.tibco.cep.decision.table.command.impl.CommandEvent;
import com.tibco.cep.decision.table.command.impl.RenameCommand;
import com.tibco.cep.decision.table.command.memento.ColumnAliasStateMemento;
import com.tibco.cep.decision.table.editors.DecisionTableEditor;
import com.tibco.cep.decision.table.editors.IDecisionTableEditorInput;
import com.tibco.cep.decision.table.model.dtmodel.Column;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.update.listener.TableModelColumnUpdateRequestEvent;
import com.tibco.cep.decision.table.model.update.listener.TableModelColumnUpdateRequestEvent.TableModelColumnUpdateRequestEventType;
import com.tibco.cep.decision.table.model.update.listener.impl.TableModelColumnAliasUpdateRequestListener;

/**
 * @author aathalye
 *
 */
public class DecisionTableColumnAliasCommandListener implements ICommandCreationListener<RenameCommand<ColumnAliasStateMemento>, ColumnAliasStateMemento> {
	
	
	private String projectName;
	
	private List<ColumnAliasStateMemento> affectedObjects;
	
	/**
	 * The UI column whose alias is changed.
	 */
	private DecisionField fieldInQuestion;
	
	/**
	 * The UI command receiver
	 */
	private DecisionTablePane decisionTablePane;
	
	private String newAlias;
	
	/**
	 * This listener updates backend model.
	 */
	private TableModelColumnAliasUpdateRequestListener tableModelColumnAliasUpdateRequestListener;
	
	/**
	 * 
	 * @param projectName
	 * @param fieldToRename
	 * @param decisionTablePane
	 * @param newAlias
	 */
	public DecisionTableColumnAliasCommandListener(String projectName,
			                                       DecisionField fieldInQuestion,
			                                       DecisionTablePane decisionTablePane,
			                                       String newAlias) {
		this.projectName = projectName;
		this.fieldInQuestion = fieldInQuestion;
		this.decisionTablePane = decisionTablePane;
		this.newAlias = newAlias;
		affectedObjects = new ArrayList<ColumnAliasStateMemento>(0);
		tableModelColumnAliasUpdateRequestListener = new TableModelColumnAliasUpdateRequestListener();
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.command.ICommandCreationListener#commandCreated(com.tibco.cep.decision.table.command.impl.CommandEvent)
	 */
	@Override
	public Object commandCreated(CommandEvent<RenameCommand<ColumnAliasStateMemento>> commandEvent) {
		Object eventSource = commandEvent.getSource();
		if (eventSource instanceof IRenameCommand) {
			DecisionDataModel decisionDataModel = decisionTablePane.getDecisionDataModel();
			String oldAlias = fieldInQuestion.getAlias();
			oldAlias = (oldAlias == null) ? "" : oldAlias;
			decisionDataModel.renameAlias(fieldInQuestion, oldAlias, newAlias);
			//Get editor and model from it
			DecisionTableEditor decisionTableEditor = decisionTablePane.getDecisionTableEditor();
			IDecisionTableEditorInput IDecisionTableEditorInput = (IDecisionTableEditorInput)decisionTableEditor.getEditorInput();
			Table tableEModel = IDecisionTableEditorInput.getTableEModel();
			Column affectedColumn = fireUpdateColumnAliasRequestEvent(decisionDataModel, tableEModel);
			prepareMemento(oldAlias, affectedColumn);
		}
		return fieldInQuestion;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.command.ICommandCreationListener#commandUndone(com.tibco.cep.decision.table.command.impl.CommandEvent)
	 */
	@Override
	public void commandUndone(CommandEvent<RenameCommand<ColumnAliasStateMemento>> commandEvent) {
		//Do nothing
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.command.ICommandCreationListener#getAffectedObjects()
	 */
	@Override
	public List<ColumnAliasStateMemento> getAffectedObjects() {
		return affectedObjects;
	}
	
	private void prepareMemento(String oldAlias, Column affectedColumn) {
		ColumnAliasStateMemento columnAliasStateMemento = new ColumnAliasStateMemento(affectedColumn, oldAlias);
		columnAliasStateMemento.setNewColumnAlias(newAlias);
		affectedObjects.add(columnAliasStateMemento);
	}
	
	/**
	 * Update backend model for column alias changes in UI.
	 * @param decisionDataModel
	 * @param tableEModel
	 */
	private Column fireUpdateColumnAliasRequestEvent(DecisionDataModel decisionDataModel,
			                                         Table tableEModel) {
		
		TableModelColumnUpdateRequestEvent tableModelColumnUpdateRequestEvent =
			new TableModelColumnUpdateRequestEvent(fieldInQuestion,
												   projectName, 
					                               tableEModel,
					                               TableModelColumnUpdateRequestEventType.ALIAS,
					                               decisionDataModel.getTableType());
		tableModelColumnUpdateRequestEvent.setChangedAlias(newAlias);
		tableModelColumnAliasUpdateRequestListener.doChange(tableModelColumnUpdateRequestEvent);
		//Return the affected object
		return tableModelColumnAliasUpdateRequestListener.getAffectedColumn();
	}
}
