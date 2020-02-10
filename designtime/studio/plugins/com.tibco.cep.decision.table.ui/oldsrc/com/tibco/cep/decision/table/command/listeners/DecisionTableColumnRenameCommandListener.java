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
import com.tibco.cep.decision.table.command.memento.ColumnNameStateMemento;
import com.tibco.cep.decision.table.editors.DecisionTableEditor;
import com.tibco.cep.decision.table.editors.IDecisionTableEditorInput;
import com.tibco.cep.decision.table.model.dtmodel.Column;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.update.listener.TableModelColumnUpdateRequestEvent;
import com.tibco.cep.decision.table.model.update.listener.TableModelColumnUpdateRequestEvent.TableModelColumnUpdateRequestEventType;
import com.tibco.cep.decision.table.model.update.listener.impl.TableModelColumnNameUpdateRequestListener;

/**
 * @author aathalye
 *
 */
public class DecisionTableColumnRenameCommandListener implements ICommandCreationListener<RenameCommand<ColumnNameStateMemento>, ColumnNameStateMemento> {
	
	/**
	 * Project Name is always required :)
	 */
	private String projectName;
	
	private List<ColumnNameStateMemento> affectedObjects;
	
	/**
	 * The UI column renamed.
	 */
	private DecisionField fieldToRename;
	
	/**
	 * The UI command receiver
	 */
	private DecisionTablePane decisionTablePane;
	
	/**
	 * The new name of the field.
	 */
	private String newName;
	
	/**
	 * This listener updates backend model.
	 */
	private TableModelColumnNameUpdateRequestListener tableModelColumnUpChangeRequestListener;

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.command.ICommandCreationListener#commandCreated(com.tibco.cep.decision.table.command.impl.CommandEvent)
	 */
	@Override
	public Object commandCreated(CommandEvent<RenameCommand<ColumnNameStateMemento>> commandEvent) {
		Object eventSource = commandEvent.getSource();
		if (eventSource instanceof IRenameCommand) {
			DecisionDataModel decisionDataModel = decisionTablePane.getDecisionDataModel();
			String oldName = fieldToRename.getName();
			decisionDataModel.renameField(fieldToRename, newName, projectName);
			//Get editor and model from it
			DecisionTableEditor decisionTableEditor = decisionTablePane.getDecisionTableEditor();
			IDecisionTableEditorInput IDecisionTableEditorInput = (IDecisionTableEditorInput)decisionTableEditor.getEditorInput();
			Table tableEModel = IDecisionTableEditorInput.getTableEModel();
			Column affectedColumn = fireUpdateColumnNameRequestEvent(decisionDataModel, tableEModel);
			prepareMemento(oldName, affectedColumn);
		}
		return fieldToRename;
	}
	
	private void prepareMemento(String oldName, Column affectedColumn) {
		ColumnNameStateMemento columnNameStateMemento = new ColumnNameStateMemento(affectedColumn, oldName);
		columnNameStateMemento.setNewColumnName(newName);
		affectedObjects.add(columnNameStateMemento);
	}
	
	/**
	 * Update backend model for column name changes in UI.
	 * @param decisionDataModel
	 * @param tableEModel
	 */
	private Column fireUpdateColumnNameRequestEvent(DecisionDataModel decisionDataModel,
			                                        Table tableEModel) {
		
		TableModelColumnUpdateRequestEvent tableModelColumnUpdateRequestEvent =
			new TableModelColumnUpdateRequestEvent(fieldToRename,
												   projectName, 
					                               tableEModel,
					                               TableModelColumnUpdateRequestEventType.NAME,
					                               decisionDataModel.getTableType());
		tableModelColumnUpdateRequestEvent.setChangedName(newName);
		
		tableModelColumnUpChangeRequestListener.doChange(tableModelColumnUpdateRequestEvent);
		//Return the affected object
		return tableModelColumnUpChangeRequestListener.getAffectedColumn();
	}
	
	/**
	 * 
	 * @param fieldToRename
	 * @param decisionDataModel
	 * @param newName
	 */
	public DecisionTableColumnRenameCommandListener(String projectName,
			                                        DecisionField fieldToRename,
			                                        DecisionTablePane decisionTablePane,
			                                        String newName) {
		this.projectName = projectName;
		this.fieldToRename = fieldToRename;
		this.decisionTablePane = decisionTablePane;
		this.newName = newName;
		affectedObjects = new ArrayList<ColumnNameStateMemento>(0);
		tableModelColumnUpChangeRequestListener = new TableModelColumnNameUpdateRequestListener();
	}



	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.command.ICommandCreationListener#commandUndone(com.tibco.cep.decision.table.command.impl.CommandEvent)
	 */
	@Override
	public void commandUndone(CommandEvent<RenameCommand<ColumnNameStateMemento>> commandEvent) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.command.ICommandCreationListener#getAffectedObjects()
	 */
	@Override
	public List<ColumnNameStateMemento> getAffectedObjects() {
		return affectedObjects;
	}
}
