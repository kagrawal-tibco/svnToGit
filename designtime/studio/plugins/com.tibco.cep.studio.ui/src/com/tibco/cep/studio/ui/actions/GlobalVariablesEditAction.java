package com.tibco.cep.studio.ui.actions;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

import com.tibco.cep.studio.ui.AbstractNavigatorNode;
import com.tibco.cep.studio.ui.util.StudioResourceUtils;

/*
@author ssailapp
@date Feb 10, 2010 5:31:35 PM
 */

public class GlobalVariablesEditAction implements IWorkbenchWindowActionDelegate {

	@SuppressWarnings("unused")
	private IWorkbenchWindow window;
	private IStructuredSelection selection;
	private IProject project;
	
	@Override
	public void dispose() {
	}

	@Override
	public void init(IWorkbenchWindow window) {
		this.window = window;
	}

	@Override
	public void run(IAction action) {
		try {
			if (selection != null) {
				if (StudioResourceUtils.isStudioProject(selection)) {
					Object firstElement = selection.getFirstElement();
					if (!(firstElement instanceof IProject)) {
						if (!StudioResourceUtils.isStudioProject(selection)) {
							return;
						}
						project = StudioResourceUtils
								.getCurrentProject(selection);
					} else {
						project = (IProject) firstElement;
					}
				}
			}
			if (project != null && project.isOpen()) {
				IFolder gvFolder = project.getFolder("defaultVars");
				if (!gvFolder.exists()) {
					gvFolder.create(true, true, null);
				}
				IFile gvFile = gvFolder.getFile("defaultVars.substvar");
				IEditorInput editorInput = new FileEditorInput(gvFile);
				IWorkbenchWindow window=PlatformUI.getWorkbench().getActiveWorkbenchWindow();
				IWorkbenchPage page = window.getActivePage();
				page.openEditor(editorInput, "com.tibco.cep.studio.ui.editor.GlobalVariablesEditor");
			}
		} catch (Exception e) {
		}
	}

	@Override
	public void selectionChanged(IAction action, ISelection _selection) {
		if (_selection instanceof IStructuredSelection) {
			this.selection = (IStructuredSelection)_selection;
			if (selection.size() == 1){
				try {
					action.setEnabled(true);
					if(selection.getFirstElement() instanceof AbstractNavigatorNode){
						action.setEnabled(false);
						return;
					}
					if( StudioResourceUtils.isStudioProject(selection)) {
						project = StudioResourceUtils.getCurrentProject(selection);
						if (project != null && project.isOpen()) {
							action.setEnabled(true);
						} else {
							action.setEnabled(false);
						}
					} else {
						action.setEnabled(false);
					}
				} catch (Exception e) {
					action.setEnabled(false);
				}
			} else {
				action.setEnabled(false);
			}	
		} else if(_selection.isEmpty()) {
			action.setEnabled(false);
		}
	}

}
