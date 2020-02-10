package com.tibco.cep.studio.ui.actions;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.util.StudioResourceUtils;

public class ShowGVEditorAction extends ShowGVViewAction {

	private GlobalVariablesAction gvAction;
	private IProject project;
	
	/**
	 * @param gvAction
	 * @param name
	 * @param window
	 * @param newImage
	 */
	public ShowGVEditorAction(GlobalVariablesAction gvAction, 
			                  String name, 
			                  IWorkbenchWindow window,
			                  ImageDescriptor newImage) {
		this(name, window, newImage);
		this.gvAction = gvAction;
	}
	
	public ShowGVEditorAction(String name, IWorkbenchWindow window,
			ImageDescriptor newImage) {
		super(name, window, newImage);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run() {
		try {
			IStructuredSelection selection = gvAction.getSelection();
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
		}
		catch (Exception e) {
			StudioUIPlugin.debug(e.getMessage());
		}
	}
}