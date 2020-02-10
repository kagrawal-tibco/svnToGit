package com.tibco.cep.bpmn.ui.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import com.tibco.cep.bpmn.ui.editor.BpmnEditor;
import com.tibco.cep.bpmn.ui.utils.ICommandIds;


public class BpmnSaveAsAction extends Action implements IWorkbenchWindowActionDelegate {

	private IWorkbenchWindow window;
	
	public void dispose() {
		// TODO Auto-generated method stub

	}

	public void init(IWorkbenchWindow window) {
		this.window = window;
		setId(ICommandIds.CMD_DG_SAVE);
		setActionDefinitionId(ICommandIds.CMD_DG_SAVE);
	}

	public void run(IAction action) {
		IWorkbenchPage page = window.getActivePage();

		IEditorPart activeEditorPart = page.getActiveEditor();
		BpmnEditor dgEditor = null;
		if (activeEditorPart instanceof BpmnEditor) {
			dgEditor = (BpmnEditor) activeEditorPart;
		}
		dgEditor.doSaveAs();
	}

	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub

	}

}
