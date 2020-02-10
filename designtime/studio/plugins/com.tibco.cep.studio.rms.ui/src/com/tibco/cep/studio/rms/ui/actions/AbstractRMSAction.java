/**
 * 
 */
package com.tibco.cep.studio.rms.ui.actions;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionDelegate2;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;

import com.tibco.cep.studio.rms.ui.RMSUIPlugin;
import com.tibco.cep.studio.ui.util.StudioResourceUtils;

/**
 * @author aathalye
 *
 */
public abstract class AbstractRMSAction implements IObjectActionDelegate, IWorkbenchWindowActionDelegate, IActionDelegate2 {

	protected ISelection _selection;
	protected IResource m_SelectedResource;
	protected Shell shell;
	protected IWorkbenchWindow window;
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction, org.eclipse.jface.viewers.ISelection)
	 */
	
	public void selectionChanged(IAction action, ISelection selection) {
		this._selection = selection;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.IObjectActionDelegate#setActivePart(org.eclipse.jface.action.IAction, org.eclipse.ui.IWorkbenchPart)
	 */
	
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		// TODO Auto-generated method stub
		
	}
	
	protected void getResourceFromSelection() {
		window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		shell = window.getShell();
		if (!(_selection.isEmpty()) && (_selection instanceof IStructuredSelection)) {
			IStructuredSelection structuredSelection = (IStructuredSelection)_selection;
			Object firstElement = structuredSelection.getFirstElement();
			if (!(firstElement instanceof IProject)) {
				try {
					if (!StudioResourceUtils.isStudioProject(structuredSelection)) {
						return;
					}
				} catch (Exception e) {
					RMSUIPlugin.log(e);
				}
			} 
			m_SelectedResource = (IResource)firstElement;
		} else {
			MessageDialog.openInformation(shell, "Select Project", "Please select a project and perform the operation.");
		}
	}
	

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
	}

	@Override
	public void init(IWorkbenchWindow window) {
	}
	
	public void getResourceFromEditor() {
	}

	@Override
	public void run(IAction action) {
		getResourceFromEditor();
	}
}
