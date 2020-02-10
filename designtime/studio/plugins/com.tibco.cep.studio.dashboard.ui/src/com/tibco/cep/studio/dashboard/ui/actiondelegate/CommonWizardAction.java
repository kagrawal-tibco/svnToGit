package com.tibco.cep.studio.dashboard.ui.actiondelegate;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;

import com.tibco.cep.studio.core.nature.StudioProjectNature;
import com.tibco.cep.studio.dashboard.ui.DashboardUIPlugin;
import com.tibco.cep.studio.ui.util.StudioResourceUtils;
import com.tomsawyer.graph.TSGraphObject;

public abstract class CommonWizardAction {

	private IWorkbenchWindow window;

	protected void setWorkbenchWindow(IWorkbenchWindow window) {
		this.window = window;
	}

	protected IProject getStudioProject(ISelection selection) {
		try {
			IProject project = null;
			if (selection != null && selection instanceof IStructuredSelection) {
				IStructuredSelection structuredSelection = (IStructuredSelection) selection;
				if (structuredSelection.isEmpty() == false) {
					if (StudioResourceUtils.isStudioProject(structuredSelection)) {
						project = StudioResourceUtils.getCurrentProject(structuredSelection);
					}
					else {
						Object firstElement = structuredSelection.getFirstElement();
						if (firstElement instanceof TSGraphObject) {
							IFile file = (IFile) ((TSGraphObject) firstElement).getAttributeValue(IFile.class.getName());
							if (file != null) {
								project = file.getProject();
							}
						}
					}
				}
			}
			if (project == null && window != null) {
				IWorkbenchPage activePage = window.getActivePage();
				if (activePage != null) {
					IEditorPart editor = activePage.getActiveEditor();
					if (editor != null) {
						project = (IProject) editor.getAdapter(IProject.class);
					}
				}
			}
			if (project != null && project.isAccessible() == true && project.hasNature(StudioProjectNature.STUDIO_NATURE_ID) == true) {
				return project;
			}
			return null;
		} catch (CoreException e) {
			MultiStatus status = new MultiStatus(DashboardUIPlugin.PLUGIN_ID, IStatus.WARNING, new IStatus[]{e.getStatus()}, "could not find project for "+selection, e);
			DashboardUIPlugin.getInstance().getLog().log(status);
			return null;
		} catch (Exception e) {
			IStatus status = new Status(IStatus.WARNING, DashboardUIPlugin.PLUGIN_ID, "could not find project for "+selection, e);
			DashboardUIPlugin.getInstance().getLog().log(status);
			return null;
		}
	}

}
