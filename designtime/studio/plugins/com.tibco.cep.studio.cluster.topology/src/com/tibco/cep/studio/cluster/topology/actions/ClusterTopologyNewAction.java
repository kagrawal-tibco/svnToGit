package com.tibco.cep.studio.cluster.topology.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;

/**
 * 
 * @author ggrigore
 *
 */
public class ClusterTopologyNewAction implements IWorkbenchWindowActionDelegate {

	public void dispose() {

	}

	public void init(IWorkbenchWindow window) {
	}

	public void run(IAction action) {
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
//		ClusterTopologyEditorInput input = new ClusterTopologyEditorInput(Messages.getString("untitled"));
//		
//		try {
//			page.openEditor(input, ClusterTopologyEditor.ID);
//		}
//		catch (PartInitException e) {
//			System.out.println(e.getMessage());
//		}    	
	}

	public void selectionChanged(IAction action, ISelection selection) {

	}

}
