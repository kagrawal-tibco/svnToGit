/**
 * 
 */
package com.tibco.cep.studio.decision.table.command.listeners;

import java.util.ArrayList;
import java.util.List;

import com.tibco.cep.decision.table.command.ICommandCreationListener;
import com.tibco.cep.decision.table.command.IRenameCommand;
import com.tibco.cep.decision.table.command.impl.CommandEvent;
import com.tibco.cep.decision.table.command.impl.RenameCommand;
import com.tibco.cep.decision.table.command.memento.ColumnAliasStateMemento;
import com.tibco.cep.decision.table.model.dtmodel.Column;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableTypes;
import com.tibco.cep.studio.decision.table.editor.DecisionTableDesignViewer;
import com.tibco.cep.studio.decision.table.editor.IDecisionTableEditor;
import com.tibco.cep.studio.decision.table.editor.input.IDecisionTableEditorInput;
import com.tibco.cep.studio.decision.table.model.update.listener.TableModelColumnUpdateRequestEvent;
import com.tibco.cep.studio.decision.table.model.update.listener.TableModelColumnUpdateRequestEvent.TableModelColumnUpdateRequestEventType;
import com.tibco.cep.studio.decision.table.model.update.listener.impl.TableModelColumnAliasUpdateRequestListener;

public class DecisionTableColumnAliasCommandListener implements ICommandCreationListener<RenameCommand<ColumnAliasStateMemento>, ColumnAliasStateMemento> {
	
	
	private String projectName;
	
	private TableTypes tableType;
	
	private List<ColumnAliasStateMemento> affectedObjects;
	
	/**
	 * The UI column whose alias is changed.
	 */
	private Column colInQuestion;
	
	/**
	 * The UI command receiver
	 */
	private DecisionTableDesignViewer formViewer;
	
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
			                                       Column colInQuestion,
			                                       DecisionTableDesignViewer formViewer,
			                                       String newAlias,
			                                       TableTypes tableType) {
		this.projectName = projectName;
		this.colInQuestion = colInQuestion;
		this.formViewer = formViewer;
		this.newAlias = newAlias;
		this.tableType = tableType;
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
//			DecisionDataModel decisionDataModel = decisionTablePane.getDecisionDataModel();
			String oldAlias = colInQuestion.getAlias();
			oldAlias = (oldAlias == null) ? "" : oldAlias;
//			decisionDataModel.renameAlias(fieldInQuestion, oldAlias, newAlias);
			//Get editor and model from it
			IDecisionTableEditor decisionTableEditor = formViewer.getEditor();
			Table tableEModel = decisionTableEditor.getTable();
			Column affectedColumn = fireUpdateColumnAliasRequestEvent(tableEModel);
			prepareMemento(oldAlias, affectedColumn);
		}
		return colInQuestion;
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
	private Column fireUpdateColumnAliasRequestEvent(Table tableEModel) {
		
		TableModelColumnUpdateRequestEvent tableModelColumnUpdateRequestEvent =
			new TableModelColumnUpdateRequestEvent(colInQuestion,
												   projectName, 
					                               tableEModel,
					                               TableModelColumnUpdateRequestEventType.ALIAS,
					                               tableType);
		tableModelColumnUpdateRequestEvent.setChangedAlias(newAlias);
		tableModelColumnAliasUpdateRequestListener.doChange(tableModelColumnUpdateRequestEvent);
		//Return the affected object
		return tableModelColumnAliasUpdateRequestListener.getAffectedColumn();
	}
}
