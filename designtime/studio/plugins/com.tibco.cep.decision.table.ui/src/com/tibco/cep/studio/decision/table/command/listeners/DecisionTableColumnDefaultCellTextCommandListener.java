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
import com.tibco.cep.decision.table.command.memento.ColumnDefaultCellTextStateMemento;
import com.tibco.cep.decision.table.model.dtmodel.Column;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableTypes;
import com.tibco.cep.studio.decision.table.editor.DecisionTableDesignViewer;
import com.tibco.cep.studio.decision.table.editor.IDecisionTableEditor;
import com.tibco.cep.studio.decision.table.editor.input.IDecisionTableEditorInput;
import com.tibco.cep.studio.decision.table.model.update.listener.TableModelColumnUpdateRequestEvent;
import com.tibco.cep.studio.decision.table.model.update.listener.TableModelColumnUpdateRequestEvent.TableModelColumnUpdateRequestEventType;
import com.tibco.cep.studio.decision.table.model.update.listener.impl.TableModelColumnDefaultCellTextUpdateRequestListener;

public class DecisionTableColumnDefaultCellTextCommandListener implements ICommandCreationListener<RenameCommand<ColumnDefaultCellTextStateMemento>, ColumnDefaultCellTextStateMemento> {
	
	
	private String projectName;
	
	private TableTypes tableType;
	
	private List<ColumnDefaultCellTextStateMemento> affectedObjects;
	
	/**
	 * The UI column whose defaultCellText is changed.
	 */
	private Column colInQuestion;
	
	/**
	 * The UI command receiver
	 */
	private DecisionTableDesignViewer formViewer;
	
	private String newDefaultCellText;
	
	/**
	 * This listener updates backend model.
	 */
	private TableModelColumnDefaultCellTextUpdateRequestListener tableModelColumnDefaultCellTextUpdateRequestListener;
	
	/**
	 * 
	 * @param projectName
	 * @param fieldToRename
	 * @param decisionTablePane
	 * @param newAlias
	 */
	public DecisionTableColumnDefaultCellTextCommandListener(String projectName,
			                                       Column colInQuestion,
			                                       DecisionTableDesignViewer formViewer,
			                                       String newDefaultCellText,
			                                       TableTypes tableType) {
		this.projectName = projectName;
		this.colInQuestion = colInQuestion;
		this.formViewer = formViewer;
		this.newDefaultCellText = newDefaultCellText;
		this.tableType = tableType;
		affectedObjects = new ArrayList<ColumnDefaultCellTextStateMemento>(0);
		tableModelColumnDefaultCellTextUpdateRequestListener = new TableModelColumnDefaultCellTextUpdateRequestListener();
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.command.ICommandCreationListener#commandCreated(com.tibco.cep.decision.table.command.impl.CommandEvent)
	 */
	@Override
	public Object commandCreated(CommandEvent<RenameCommand<ColumnDefaultCellTextStateMemento>> commandEvent) {
		Object eventSource = commandEvent.getSource();
		if (eventSource instanceof IRenameCommand) {
//			DecisionDataModel decisionDataModel = decisionTablePane.getDecisionDataModel();
			String defaultCellText = colInQuestion.getDefaultCellText();
			defaultCellText = (defaultCellText == null) ? "" : defaultCellText;
//			decisionDataModel.renameAlias(fieldInQuestion, oldAlias, newAlias);
			//Get editor and model from it
			IDecisionTableEditor decisionTableEditor = formViewer.getEditor();
			IDecisionTableEditorInput IDecisionTableEditorInput = (IDecisionTableEditorInput)decisionTableEditor.getEditorInput();
			Table tableEModel = IDecisionTableEditorInput.getTableEModel();
			Column affectedColumn = fireUpdateColumnDefaultCellTextRequestEvent(tableEModel);
			prepareMemento(defaultCellText, affectedColumn);
		}
		return colInQuestion;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.command.ICommandCreationListener#commandUndone(com.tibco.cep.decision.table.command.impl.CommandEvent)
	 */
	@Override
	public void commandUndone(CommandEvent<RenameCommand<ColumnDefaultCellTextStateMemento>> commandEvent) {
		//Do nothing
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.command.ICommandCreationListener#getAffectedObjects()
	 */
	@Override
	public List<ColumnDefaultCellTextStateMemento> getAffectedObjects() {
		return affectedObjects;
	}
	
	private void prepareMemento(String defaultCellText, Column affectedColumn) {
		ColumnDefaultCellTextStateMemento columnAliasStateMemento = new ColumnDefaultCellTextStateMemento(affectedColumn, defaultCellText);
		columnAliasStateMemento.setNewColumnDefaultCellText(newDefaultCellText);
		affectedObjects.add(columnAliasStateMemento);
	}
	
	/**
	 * Update backend model for column alias changes in UI.
	 * @param decisionDataModel
	 * @param tableEModel
	 */
	private Column fireUpdateColumnDefaultCellTextRequestEvent(Table tableEModel) {
		
		TableModelColumnUpdateRequestEvent tableModelColumnUpdateRequestEvent =
			new TableModelColumnUpdateRequestEvent(colInQuestion,
												   projectName, 
					                               tableEModel,
					                               TableModelColumnUpdateRequestEventType.DEFAULTCELLTEXT,
					                               tableType);
		tableModelColumnUpdateRequestEvent.setChangedDefaultCellText(newDefaultCellText);
		tableModelColumnDefaultCellTextUpdateRequestListener.doChange(tableModelColumnUpdateRequestEvent);
		//Return the affected object
		return tableModelColumnDefaultCellTextUpdateRequestListener.getAffectedColumn();
	}
}
