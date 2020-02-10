package com.tibco.cep.decision.table.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;

import com.jidesoft.decision.DecisionTablePane;
import com.tibco.cep.decision.table.ICommandIds;
import com.tibco.cep.decision.table.editors.DecisionTableEditor;
import com.tibco.cep.decision.table.utils.DTImages;

public class DTMergeRowsAction extends Action {

	private final IWorkbenchWindow window;

	public DTMergeRowsAction(String text, IWorkbenchWindow window) {
		super(text);
		this.window = window;
		// The id is used to refer to the action in a menu or toolbar
		setId(ICommandIds.CMD_DT_MERGE_ROWS);
		// Associate the action with a pre-defined command, to allow key
		// bindings.
		setActionDefinitionId(ICommandIds.CMD_DT_MERGE_ROWS);
		setImageDescriptor(DTImages
				.getImageDescriptor(DTImages.DT_IMAGES_MERGE_ROWS));
	}

	public void run() {
		IEditorPart editor = window.getActivePage().getActiveEditor();
		if (editor instanceof DecisionTableEditor) {
//			DecisionTableEditor decisionTableEditor = (DecisionTableEditor) editor;
//			DecisionTableDesignViewer decisionTableDesignViewer = decisionTableEditor.getDesignViewer();
			/*if (decisionTableDesignViewer.get_pivotTablePane() instanceof SimpleDecisionTablePane) {
				SimpleDecisionTablePane decisionTable = (SimpleDecisionTablePane) decisionTableDesignViewer.get_pivotTablePane();
				doMerge(decisionTableDesignViewer, decisionTable, true); 
			}
			if (decisionTableDesignViewer.getExceptionTablePane() instanceof SimpleDecisionTablePane) {
				SimpleDecisionTablePane exceptionTable = (SimpleDecisionTablePane) decisionTableDesignViewer.getExceptionTablePane();
				doMerge(decisionTableDesignViewer, exceptionTable, false); 
			}*/
		}
	}

//	private void doMerge(DecisionTableDesignViewer viewer, DecisionTablePane tablePane, boolean isDecisionTable)
//	{
		/*stopCellEditing(tablePane);
		tablePane.getPivotDataModel().getRowHeaderTableModel().setCellSpanOn(
						!tablePane.getPivotDataModel().getRowHeaderTableModel().isCellSpanOn());
		tablePane.getPivotDataModel()
				.getRowHeaderTableModel().fireTableDataChanged();
		if (isDecisionTable) {
			viewer.getToggleMergeButton().setSelected(
				tablePane.getPivotDataModel().getRowHeaderTableModel().isCellSpanOn());
		} else {
			viewer.getExpToggleMergeButton().setSelected(
					tablePane.getPivotDataModel().getRowHeaderTableModel().isCellSpanOn());
		}*/
//	}
	
	protected void stopCellEditing(DecisionTablePane _pivotTablePane) {
		/*if (_pivotTablePane.getRowHeaderTable() != null
				&& _pivotTablePane.getRowHeaderTable().isEditing()) {
			_pivotTablePane.getRowHeaderTable().getCellEditor()
					.stopCellEditing();
		}
		if (_pivotTablePane.getColumnHeaderTable() != null
				&& _pivotTablePane.getColumnHeaderTable().isEditing()) {
			_pivotTablePane.getColumnHeaderTable().getCellEditor()
					.stopCellEditing();
		}
		if (_pivotTablePane.getDataTable() != null
				&& _pivotTablePane.getDataTable().isEditing()) {
			_pivotTablePane.getDataTable().getCellEditor().stopCellEditing();
		}
		if (_pivotTablePane instanceof SimpleDecisionTablePane
				&& ((SimpleDecisionTablePane) _pivotTablePane)
						.getDecisionTable() != null
				&& ((SimpleDecisionTablePane) _pivotTablePane)
						.getDecisionTable().isEditing()) {
			((SimpleDecisionTablePane) _pivotTablePane).getDecisionTable()
					.getCellEditor().stopCellEditing();
		}*/
	}

}
