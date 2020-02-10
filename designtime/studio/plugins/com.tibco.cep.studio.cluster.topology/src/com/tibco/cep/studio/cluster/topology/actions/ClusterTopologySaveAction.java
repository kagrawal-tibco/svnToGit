package com.tibco.cep.studio.cluster.topology.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import com.tibco.cep.studio.cluster.topology.editors.ClusterTopologyEditor;

/**
 * 
 * @author ggrigore
 *
 */
public class ClusterTopologySaveAction implements IWorkbenchWindowActionDelegate {

	private IWorkbenchWindow window;
	
	public void dispose() {
	}

	public void init(IWorkbenchWindow window) {
		this.window = window;
	}

	public void run(IAction action) {
		IWorkbenchPage page = window.getActivePage();
		IEditorPart activeEditorPart = page.getActiveEditor();
		if (activeEditorPart != null && activeEditorPart instanceof ClusterTopologyEditor) {
			ClusterTopologyEditor dgEditor = (ClusterTopologyEditor) activeEditorPart;
			dgEditor.doSave(null);
		}
	}

	public void selectionChanged(IAction action, ISelection selection) {
	}
}
