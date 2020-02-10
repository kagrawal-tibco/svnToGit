package com.tibco.cep.decision.table.editors;

import java.awt.Component;
import java.awt.KeyboardFocusManager;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellEditor;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.actions.ActionFactory;

import com.jidesoft.decision.DecisionTablePane;
import com.tibco.cep.decision.table.command.CommandFacade;

public class DecisionTableActionHandler extends Action {
	
	private DecisionTableEditor decisionTableEditor;
	public DecisionTableActionHandler(String id, DecisionTableEditor decisionTableEditor) {
		super();
		setId(id);
		this.decisionTableEditor = decisionTableEditor;
	}

	@Override
	public void run() {

		Component comp = KeyboardFocusManager.getCurrentKeyboardFocusManager()
				.getPermanentFocusOwner();
		if (comp instanceof JTextField) {
			JTextField focused = (JTextField) comp;
			
			if (ActionFactory.COPY.getId().equals(getId())) {
				focused.copy();
			} else if (ActionFactory.PASTE.getId().equals(getId())) {
				focused.paste();
			} else if (ActionFactory.SELECT_ALL.getId().equals(getId())) {
				focused.selectAll();
			} else if (ActionFactory.FIND.getId().equals(getId())) {
				doFind(focused);
			} else if (ActionFactory.CUT.getId().equals(getId())) {
				focused.cut();
			} else if (ActionFactory.UNDO.getId().equals(getId())) {
				stopEditing(decisionTableEditor);
				executeUndo();
			}
		} else if (comp instanceof JTable) {
			if (ActionFactory.UNDO.getId().equals(getId())) {
				stopEditing(decisionTableEditor);
				executeUndo();
			}
		} else if (ActionFactory.FIND.getId().equals(getId())) {
			doFind(null);
		}

	}

	private void doFind(JTextField focused) {

		DecisionTableDesignViewer decisionTableDesignViewer = decisionTableEditor
					.getDesignViewer();

		decisionTableDesignViewer.performFind(focused);
		

	}
	
	private void executeUndo() {
		if(decisionTableEditor.getEditorInput() instanceof IDecisionTableEditorInput) {
			IDecisionTableEditorInput editorInput = (IDecisionTableEditorInput)decisionTableEditor.getEditorInput();
			String project = editorInput.getProjectName();
			String tablePath = editorInput.getResourcePath();
			
			stopEditing(decisionTableEditor);
			CommandFacade.getInstance().undo(project, tablePath);
		}
	}
	
	/**
	 * Stop cell editing to take care of Undo while cell typing
	 * @param decisionTableEditor
	 */
	private void stopEditing(DecisionTableEditor decisionTableEditor) {
		DecisionTableDesignViewer decisionTableDesignViewer = decisionTableEditor.getDecisionTableDesignViewer();
		DecisionTablePane decisionTablePane = decisionTableDesignViewer.getDecisionTablePane();
		DecisionTablePane exceptionTablePane = decisionTableDesignViewer.getExceptionTablePane();
		
		JTable mainDTTable = decisionTablePane.getTableScrollPane().getMainTable();
		JTable mainETTable = exceptionTablePane.getTableScrollPane().getMainTable();
		
		TableCellEditor dtCellEditor = mainDTTable.getCellEditor();
		if (dtCellEditor != null) {
			dtCellEditor.stopCellEditing();
		}
		TableCellEditor etCellEditor = mainETTable.getCellEditor();
		if (etCellEditor != null) {
			etCellEditor.stopCellEditing();
		}
	}

}
