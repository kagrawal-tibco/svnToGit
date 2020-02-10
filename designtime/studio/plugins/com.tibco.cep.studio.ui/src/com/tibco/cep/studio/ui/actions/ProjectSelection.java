package com.tibco.cep.studio.ui.actions;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionDelegate;

import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.util.Messages;
import com.tibco.cep.studio.ui.util.StudioResourceUtils;
import com.tomsawyer.graph.TSGraphObject;

/*
@author ssailapp
@date Dec 9, 2011
 */

public class ProjectSelection extends ActionDelegate {

	protected ISelection fSelection;
	protected IProject fProject;
	protected IWorkbenchWindow fWindow;
	protected boolean validProject = false;
	
	public IProject getProject() {
		return fProject;
	}
	
	public void selectionChanged(IAction action, ISelection selection) {
		this.fSelection = selection;
		try {
			this.fProject = getProjectFromSelection(selection);
			// if project is not null then project is open and is a valid studio project
			validProject = fProject != null && fProject.isOpen();
			action.setEnabled(validProject);
		} catch (Exception e) {
			StudioUIPlugin.log(Messages.getString("project.selection.failed"),e); //$NON-NLS-1$
		}
	}
		
	protected IProject getProjectFromSelection(ISelection selection) throws Exception {
		if ( (selection != null) && 
				(selection instanceof IStructuredSelection)) {
			IStructuredSelection structuredSelection = (IStructuredSelection)selection;
			
			if(!selection.isEmpty()){
				if (StudioResourceUtils.isStudioProject(structuredSelection)) {
					return StudioResourceUtils.getCurrentProject(structuredSelection);
				}
				Object obj = structuredSelection.getFirstElement();
				if(obj instanceof TSGraphObject) {
					IFile file = (IFile) ((TSGraphObject)obj).getAttributeValue(IFile.class.getName());
					if(file != null) {
						return file.getProject();
					}
				}
			} 
			
			// if selection is empty then check active editor
			if(fWindow != null) {
				IWorkbenchPage activePage = fWindow.getActivePage();
				if(activePage != null) {
					IEditorPart editor = activePage.getActiveEditor();
					if(editor != null) {
						return (IProject) editor.getAdapter(IProject.class);
					}
				}
			} 
		} else if(selection != null && selection instanceof ITextSelection) {
			final IWorkbench wb = PlatformUI.getWorkbench();
			ActiveProject ap = new ActiveProject();
			wb.getDisplay().syncExec(ap);
			return ap.getProj();
		}
		return null;
	}
	
	public static class ActiveProject implements Runnable {
		IProject proj = null;
		
		public IProject getProj() {
			return proj;
		}
		@Override
		public void run() {
			final IWorkbench wb = PlatformUI.getWorkbench();
			IWorkbenchWindow wbWindow = wb.getActiveWorkbenchWindow();
			if(wbWindow != null) {
				IWorkbenchPage ap = wbWindow.getActivePage();
				if(ap != null) {
					IEditorPart ae = ap.getActiveEditor();
					if(ae != null) {
						proj = (IProject) ae.getAdapter(IProject.class);
					}
				}
			}
		}		
	}
}

