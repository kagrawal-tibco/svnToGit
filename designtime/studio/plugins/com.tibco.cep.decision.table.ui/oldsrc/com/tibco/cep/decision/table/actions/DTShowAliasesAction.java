package com.tibco.cep.decision.table.actions;

import javax.swing.JTable;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;

import com.jidesoft.decision.DecisionTablePane;
import com.jidesoft.grid.TableUtils;
import com.tibco.cep.decision.table.ICommandIds;
import com.tibco.cep.decision.table.editors.DecisionTableDesignViewer;
import com.tibco.cep.decision.table.editors.DecisionTableEditor;
import com.tibco.cep.decision.table.preferences.PreferenceConstants;
import com.tibco.cep.decision.table.ui.DecisionTableUIPlugin;
import com.tibco.cep.decision.table.utils.DTImages;

public class DTShowAliasesAction extends Action {

private final IWorkbenchWindow window;
	
	public DTShowAliasesAction(String text, IWorkbenchWindow window) {
		super(text);
		this.window = window;
		// The id is used to refer to the action in a menu or toolbar
		setId(ICommandIds.CMD_DT_SHOW_ALL);
		// Associate the action with a pre-defined command, to allow key
		// bindings.
		setActionDefinitionId(ICommandIds.CMD_DT_SHOW_ALL);
		setImageDescriptor(DTImages.getImageDescriptor(DTImages.DT_IMAGES_SHOW_TEXT));
	}

	public void run() {
		IEditorPart editor = window.getActivePage().getActiveEditor();
		if (editor instanceof DecisionTableEditor) {
			DecisionTableEditor decisionTableEditor = (DecisionTableEditor) editor;
			DecisionTableDesignViewer decisionTableDesignViewer = decisionTableEditor.getDesignViewer();
		
//			boolean isShowText = RuleVariableConverter.isTextAliasVisible();
			boolean isShowText = false;
			DecisionTablePane decisionTablePane = decisionTableDesignViewer.getDecisionTablePane();
			DecisionTablePane exceptionTablePane = decisionTableDesignViewer.getExceptionTablePane();
			
			if (decisionTablePane != null) {
				isShowText = decisionTableDesignViewer.isToggleTextAliasFlag();
				doShowText(decisionTableDesignViewer, decisionTablePane, true, isShowText); 
			}
			if (exceptionTablePane != null) {
				isShowText = decisionTableDesignViewer.isExpToggleTextAliasFlag();
				doShowText(decisionTableDesignViewer, exceptionTablePane, false, isShowText); 
			}
		}
	}
	
	private void doShowText(DecisionTableDesignViewer viewer, 
			                DecisionTablePane tablePane, 
			                boolean isDecisionTable, 
			                boolean isShowText) {
		stopCellEditing(tablePane);
//		RuleVariableConverter.setTextAliasVisible(!isShowText);
		if (isDecisionTable) {
//			viewer.getToggleTextAliasButton().setSelected(RuleVariableConverter.isTextAliasVisible());
			viewer.getToggleTextAliasButton().setSelected(!isShowText);
		} else {
			viewer.getExpToggleTextAliasButton().setSelected(!isShowText);
		}
		IPreferenceStore prefStore = DecisionTableUIPlugin.getDefault().getPreferenceStore();
		if (prefStore.getBoolean(PreferenceConstants.AUTO_RESIZE_COLUMN)) {
			JTable mainTable = tablePane.getTableScrollPane().getMainTable();
			TableUtils.autoResizeAllColumns(mainTable);
		}
		tablePane.repaint();
	}
	
	protected void stopCellEditing(DecisionTablePane _pivotTablePane) {
		/*if (_pivotTablePane.getRowHeaderTable() != null
				&& _pivotTablePane.getRowHeaderTable().isEditing()) {
			_pivotTablePane.getRowHeaderTable().getCellEditor().stopCellEditing();
		}
		if (_pivotTablePane.getColumnHeaderTable() != null
				&& _pivotTablePane.getColumnHeaderTable().isEditing()) {
			_pivotTablePane.getColumnHeaderTable().getCellEditor().stopCellEditing();
		}
		if (_pivotTablePane.getDataTable() != null
				&& _pivotTablePane.getDataTable().isEditing()) {
			_pivotTablePane.getDataTable().getCellEditor().stopCellEditing();
		}
		if (_pivotTablePane instanceof SimpleDecisionTablePane
				&& ((SimpleDecisionTablePane) _pivotTablePane).getDecisionTable() != null
					&& ((SimpleDecisionTablePane) _pivotTablePane).getDecisionTable().isEditing()) {
			((SimpleDecisionTablePane) _pivotTablePane).getDecisionTable()
					.getCellEditor().stopCellEditing();
		}*/
	}
}
