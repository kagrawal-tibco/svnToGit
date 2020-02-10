package com.tibco.cep.bpmn.ui.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

public class BpmnSaveAction implements IWorkbenchWindowActionDelegate {

	private IWorkbenchWindow window;
	
	public void dispose() {
		// TODO Auto-generated method stub
	}
	public void init(IWorkbenchWindow window) {
	this.window = window;
	}

	public void run(IAction action) {
		IWorkbenchPage page = window.getActivePage();
		IEditorPart editor = page.getActiveEditor();
		editor.doSave(null);
	}

	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub
	}
}
