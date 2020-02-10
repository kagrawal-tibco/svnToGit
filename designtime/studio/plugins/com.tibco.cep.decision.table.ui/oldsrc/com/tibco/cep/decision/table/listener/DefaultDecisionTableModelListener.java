/**
 * 
 */
package com.tibco.cep.decision.table.listener;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import com.jidesoft.decision.DecisionDataModelEvent;
import com.jidesoft.decision.DecisionDataModelListener;
import com.jidesoft.decision.DecisionField;
import com.jidesoft.decision.DecisionTableModel;
import com.jidesoft.decision.DecisionTablePane;
import com.tibco.cep.decision.table.command.listeners.DecisionTableColumnOrderShufflingListener;
import com.tibco.cep.decision.table.command.listeners.DecisionTableColumnRenameCommandListener;
import com.tibco.cep.decision.table.editors.DecisionTableEditor;
import com.tibco.cep.decision.table.editors.IDecisionTableEditorInput;
import com.tibco.cep.decision.table.model.DecisionTableModelManager;
import com.tibco.cep.decision.table.model.dtmodel.ColumnType;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;
import com.tibco.cep.decision.table.model.dtmodel.TableTypes;
import com.tibco.cep.decision.table.model.update.listener.ITableModelChangeRequestEvent;
import com.tibco.cep.decision.table.model.update.listener.ITableModelChangeRequestListener;
import com.tibco.cep.decision.table.model.update.listener.TableModelChangeRequestEvent;
import com.tibco.cep.decision.table.model.update.listener.TableModelChangeRequestEvent.TableModelChangeRequestEventType;
import com.tibco.cep.decision.table.model.update.listener.TableModelColumnUpdateRequestEvent;
import com.tibco.cep.decision.table.model.update.listener.TableModelColumnUpdateRequestEvent.TableModelColumnUpdateRequestEventType;
import com.tibco.cep.decision.table.model.update.listener.impl.TableModelCellModificationUpdateRequestListener;
import com.tibco.cep.decision.table.utils.DecisionTableUtil;
import com.tibco.cep.studio.ui.util.StudioUIUtils;

/**
 * Date: Feb 10, 2011
 * <p>
 * The default {@link TableModelListener} for decisiontable and exceptiontable also.
 * @author aathalye
 *
 */
public class DefaultDecisionTableModelListener implements TableModelListener, DecisionDataModelListener {
	
	private String projectName;
	
	/**
	 * Reference to the editor we are working with
	 */
	protected DecisionTableEditor decisionTableEditor;
	
	/**
	 * To be obtained from editor.
	 */
	protected Table tableEModel;
	
	/**
	 * Which table type we are working with.
	 */
	protected TableTypes tableType;
	
	/**
	 * Register listeners for sending model change request notifications
	 * when table changes.
	 */
	private List<ITableModelChangeRequestListener> tableModelChangeRequestListeners = new ArrayList<ITableModelChangeRequestListener>();
	
	/**
	 * 
	 * @param decisionTableEditor
	 */
	public DefaultDecisionTableModelListener(DecisionTableEditor decisionTableEditor, TableTypes tableType) {
		if (decisionTableEditor == null) {
			throw new IllegalArgumentException("Editor argument cannot be null");
		}
		if (tableType == null) tableType = TableTypes.DECISION_TABLE;
		this.decisionTableEditor = decisionTableEditor;
		this.tableType = tableType;
		//Get Model manager
		DecisionTableModelManager decisionTableModelManager = decisionTableEditor.getDecisionTableModelManager();
		if (decisionTableModelManager != null) {
			tableEModel = decisionTableModelManager.getTabelEModel();
		}
		IDecisionTableEditorInput decisionTableEditorInput = (IDecisionTableEditorInput)decisionTableEditor.getEditorInput();
		this.projectName = decisionTableEditorInput.getProjectName();
		if (!decisionTableEditorInput.isReadOnly()) {
			registerDefaultListeners();
		}
	}
	
	protected void registerDefaultListeners() {
		tableModelChangeRequestListeners.add(new TableModelCellModificationUpdateRequestListener());
	}

	/**
	 * Handle updates coming only from {@link DecisionTableModel}.
	 */
	@Override
	public void tableChanged(TableModelEvent tableModelEvent) {
		if (!(tableModelEvent.getSource() instanceof DecisionTableModel)) {
			return;
		}
		// TODO Auto-generated method stub
		switch (tableModelEvent.getType()) {
		
		/**
		 * All events are update events except when modifying
		 * cells during sorting.
		 */
		case TableModelEvent.UPDATE :
			fireModelChangeRequestEvent(tableModelEvent);
		}
	}
	
	/**
	 * Cell Update case.
	 * @param tableModelEvent
	 */
	private void fireModelChangeRequestEvent(TableModelEvent tableModelEvent) {
		//Get column, first, last, row.
		int columnAffected = tableModelEvent.getColumn();
		int firstRow; int lastRow;
		firstRow = tableModelEvent.getFirstRow();
		lastRow = tableModelEvent.getLastRow();
		
		DecisionTablePane tablePane = (tableType == TableTypes.DECISION_TABLE) 
				? (DecisionTablePane)decisionTableEditor.getDecisionTablePane() 
				: (DecisionTablePane)decisionTableEditor.getExceptionTablePane();

		if (columnAffected > -1 && firstRow > -1 && lastRow > -1) {
			//Truly update case
			TableModel sourceTableModel = (TableModel)tableModelEvent.getSource();
			fireCellUpdateRequestEvent(sourceTableModel, tablePane, columnAffected, firstRow, lastRow);
		}
	}
	
	/**
	 * 
	 * @param sourceTableModel
	 * @param tablePane
	 * @param column
	 * @param firstRow
	 * @param lastRow
	 */
	private void fireCellUpdateRequestEvent(TableModel sourceTableModel,
			                                DecisionTablePane tablePane,
			                                int column,
			                                int firstRow,
			                                int lastRow) {
		//Takes care of both regular and undo case.
		for (int loop = firstRow; loop <= lastRow; loop++) {
			//Get value at each such cell
			TableRuleVariable valueAtCell = (TableRuleVariable)sourceTableModel.getValueAt(loop, column);
			TableModelChangeRequestEvent tableModelChangeRequestEvent = 
				new TableModelChangeRequestEvent(valueAtCell, 
						                         tableEModel,
						                         tablePane.getDecisionDataModel(),
						                         TableModelChangeRequestEventType.UPDATE,
						                         tableType);
			//Each row update is notified.
			fireModelUpdateRequestEvent(tableModelChangeRequestEvent);
		}
	}

	/**
     * Invoked when a <code>DecisionDataModelEvent</code> happened.
     *
     * @param decisionDataModelEvent DecisionDataModelEvent
     * @see DecisionTableColumnOrderShufflingListener#eventHappened(DecisionDataModelEvent)
     * @see DecisionTableColumnRenameCommandListener#commandCreated(com.tibco.cep.decision.table.command.impl.CommandEvent)
     */
	//TODO Overview Refresh.
	@Override
	public void eventHappened(DecisionDataModelEvent decisionDataModelEvent) {
		//Only handle alias/name rename events because the UI order stuff is handled
		//else where.
		boolean makeEditorDirty = false;
		switch (decisionDataModelEvent.getType()) {
			
			case DecisionDataModelEvent.DECISION_CONDITION_ALIAS_UPDATED :
				//Works for custom condition also.
				fireUpdateColumnAliasRequestEvent(decisionDataModelEvent, ColumnType.CONDITION);
				makeEditorDirty = true;
				break;
			case DecisionDataModelEvent.DECISION_ACTION_ALIAS_UPDATED : 
				//Works for custom action also.
				fireUpdateColumnAliasRequestEvent(decisionDataModelEvent, ColumnType.ACTION);
				makeEditorDirty = true;
				break;
			case DecisionDataModelEvent.DECISION_CONDITION_UPDATED :
				//Only make editor dirty here.
			case DecisionDataModelEvent.DECISION_ACTION_UPDATED : 
				makeEditorDirty = true;
				break;
		}
		if (makeEditorDirty) {
			StudioUIUtils.invokeOnDisplayThread(new Runnable() {
				public void run() {
					decisionTableEditor.editorContentModified();
				}
			}, false);
		}
	}
	
	/**
	 * Send {@link ITableModelChangeRequestEvent} to all listeners.
	 * @param <T>
	 * @param tableModelChangeRequestEvent
	 */
	private <T extends ITableModelChangeRequestEvent> void fireModelUpdateRequestEvent(T tableModelChangeRequestEvent) {
		for (ITableModelChangeRequestListener listener : tableModelChangeRequestListeners) {
			listener.doChange(tableModelChangeRequestEvent);
		}
	}
	
	/**
	 * Update backend model for column alias changes in UI.
	 * @param decisionDataModelEvent
	 * @param columnType -> The column type updated (condition/action)
	 */
	private void fireUpdateColumnAliasRequestEvent(DecisionDataModelEvent decisionDataModelEvent, ColumnType columnType) {
		//Get the actual field
		DecisionField field = decisionDataModelEvent.getField();
		String columnName = field.getName();
		columnName = DecisionTableUtil.tokenizeColumnName(columnName);
		String alias = field.getAlias();
		
		TableModelColumnUpdateRequestEvent tableModelColumnUpdateRequestEvent =
			new TableModelColumnUpdateRequestEvent(field,
					                               projectName,
					                               tableEModel,
					                               TableModelColumnUpdateRequestEventType.ALIAS,
					                               tableType);
		tableModelColumnUpdateRequestEvent.setChangedAlias(alias);
		fireModelUpdateRequestEvent(tableModelColumnUpdateRequestEvent);
	}
	
	/**
	 * Dispose listeners.
	 */
	public void dispose() {
		tableModelChangeRequestListeners.clear();
		tableModelChangeRequestListeners = null;
	}
}
