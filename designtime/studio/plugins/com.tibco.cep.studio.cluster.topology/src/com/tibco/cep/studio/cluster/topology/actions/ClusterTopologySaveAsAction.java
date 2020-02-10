package com.tibco.cep.studio.cluster.topology.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import com.tibco.cep.studio.cluster.topology.editors.ClusterTopologyEditor;
import com.tibco.cep.studio.cluster.topology.ui.ICommandIds;

/**
 * 
 * @author ggrigore
 *
 */
public class ClusterTopologySaveAsAction extends Action implements IWorkbenchWindowActionDelegate {

	private IWorkbenchWindow window;
	
	public void dispose() {

	}

	public void init(IWorkbenchWindow window) {
		this.window = window;
		setId(ICommandIds.CMD_DEP_SAVE);
		setActionDefinitionId(ICommandIds.CMD_DEP_SAVE);
	}

	public void run(IAction action) {
		IWorkbenchPage page = window.getActivePage();
		IEditorPart activeEditorPart = page.getActiveEditor();
		if (activeEditorPart != null && activeEditorPart instanceof ClusterTopologyEditor) {
			ClusterTopologyEditor dgEditor = (ClusterTopologyEditor) activeEditorPart;
			dgEditor.doSaveAs();
		}
	}

	public void selectionChanged(IAction action, ISelection selection) {

	}

}
