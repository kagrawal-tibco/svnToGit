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
import com.tibco.cep.decision.table.command.memento.ColumnNameStateMemento;
import com.tibco.cep.decision.table.model.dtmodel.Column;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableTypes;
import com.tibco.cep.studio.decision.table.editor.DecisionTableDesignViewer;
import com.tibco.cep.studio.decision.table.editor.IDecisionTableEditor;
import com.tibco.cep.studio.decision.table.editor.input.IDecisionTableEditorInput;
import com.tibco.cep.studio.decision.table.model.update.listener.TableModelColumnUpdateRequestEvent;
import com.tibco.cep.studio.decision.table.model.update.listener.TableModelColumnUpdateRequestEvent.TableModelColumnUpdateRequestEventType;
import com.tibco.cep.studio.decision.table.model.update.listener.impl.TableModelColumnNameUpdateRequestListener;

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
	private Column colToRename;
	
	/**
	 * The UI command receiver
	 */
	private DecisionTableDesignViewer formViewer;
	
	/**
	 * The new name of the field.
	 */
	private String newName;
	
	/**
	 * This listener updates backend model.
	 */
	private TableModelColumnNameUpdateRequestListener tableModelColumnUpChangeRequestListener;

	private TableTypes tableType;

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.command.ICommandCreationListener#commandCreated(com.tibco.cep.decision.table.command.impl.CommandEvent)
	 */
	@Override
	public Object commandCreated(CommandEvent<RenameCommand<ColumnNameStateMemento>> commandEvent) {
		Object eventSource = commandEvent.getSource();
		if (eventSource instanceof IRenameCommand) {
			String oldName = colToRename.getName();
			//Get editor and model from it
			IDecisionTableEditor decisionTableEditor = formViewer.getEditor();
			Table tableEModel = decisionTableEditor.getTable();
			Column affectedColumn = fireUpdateColumnNameRequestEvent(tableEModel);
			prepareMemento(oldName, affectedColumn);
		}
		return colToRename;
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
	private Column fireUpdateColumnNameRequestEvent(Table tableEModel) {
		
		TableModelColumnUpdateRequestEvent tableModelColumnUpdateRequestEvent =
			new TableModelColumnUpdateRequestEvent(colToRename,
												   projectName, 
					                               tableEModel,
					                               TableModelColumnUpdateRequestEventType.NAME,
					                               tableType);
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
                                                    Column colToRename,
                                                    DecisionTableDesignViewer formViewer,
                                                    String newName,
                                                    TableTypes tableType) {
        this(projectName, colToRename, formViewer, newName, tableType, new TableModelColumnNameUpdateRequestListener());
    }
    
	/**
	 * 
	 * @param fieldToRename
	 * @param decisionDataModel
	 * @param newName
	 * @param update listener chosen by the caller
	 */
	public DecisionTableColumnRenameCommandListener(String projectName,
			                                        Column colToRename,
			                                        DecisionTableDesignViewer formViewer,
			                                        String newName,
			                                        TableTypes tableType,
			                                        TableModelColumnNameUpdateRequestListener updateListener) {
		this.projectName = projectName;
		this.colToRename = colToRename;
		this.formViewer = formViewer;;
		this.newName = newName;
		this.tableType = tableType;
		affectedObjects = new ArrayList<ColumnNameStateMemento>(0);
		tableModelColumnUpChangeRequestListener = updateListener;
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
