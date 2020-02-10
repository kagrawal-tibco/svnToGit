package com.tibco.cep.studio.dashboard.ui.wizards.skin;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.ISetSelectionTarget;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalECoreFactory;
import com.tibco.cep.studio.dashboard.core.util.DashboardCoreResourceUtils;
import com.tibco.cep.studio.dashboard.ui.DashboardUIPlugin;
import com.tibco.cep.studio.dashboard.ui.utils.DashboardResourceUtils;
import com.tibco.cep.studio.dashboard.ui.wizards.BaseViewsElementWizard;
import com.tibco.cep.studio.dashboard.ui.wizards.DashboardEntityFileCreationWizard;
import com.tibco.cep.studio.dashboard.utils.SystemElementsCreator;
import com.tibco.cep.studio.ui.wizards.EntityFileCreationWizard;

public class SystemElementWizard extends BaseViewsElementWizard {

	public SystemElementWizard() {
		super("SystemElement", "System Skin", "New Dashboard System Skin Wizard", "DashboardSystemElementPage", "New Dashboard System Skin", "Create a new Dashboard System Skin");
		setDefaultPageImageDescriptor(DashboardUIPlugin.getInstance().getImageRegistry().getDescriptor("system_wizard.png"));
	}

	@Override
	protected EntityFileCreationWizard createPage() {
		EntityFileCreationWizard page = new DashboardEntityFileCreationWizard(pageName, _selection, elementType, elementTypeName, true);
		page.setDescription(pageDesc);
		page.setTitle(pageTitle);
		return page;
	}

	@Override
	public boolean performFinish() {
		final IProject project = entityCreatingWizardPage.getProject();
		final String elementName = entityCreatingWizardPage.getFileName();
		try {
			if (isSystemElementsAlreadyCreated(project)) {
				MessageDialog.openError(getShell(), "Dashboard System Skin", "System skin already created in the project.");
				return true;
			}
			final String baseURI = DashboardResourceUtils.getCurrentProjectBaseURI(project);
			final String fname = baseURI + entityCreatingWizardPage.getContainerFullPath().toPortableString() + "/" + elementName + "." + "system";
			// create the new entity creation operation
			WorkspaceModifyOperation op = new WorkspaceModifyOperation() {
				protected void execute(IProgressMonitor monitor) throws CoreException {
					try {
						String folder = DashboardResourceUtils.getFolder(getModelFile());
						List<Entity> systemElements = new SystemElementsCreator(project.getName(),folder,folder).create();
						DashboardCoreResourceUtils.persistEntities(systemElements, fname, monitor);
						// Ask the LocalECoreFactory to load the system skin elements in memory.
						LocalECoreFactory.getInstance(project).loadSystemElements(URI.createFileURI(fname).toString());
					} catch (Exception e) {
						String msg = e.getMessage();
						if (msg == null || msg.trim().length() == 0) {
							msg = "An error occured while creating the system skin";
						}
						throw new CoreException(new Status(IStatus.ERROR, DashboardUIPlugin.PLUGIN_ID, msg, e));
					}
					// refresh the project
					project.refreshLocal(IProject.DEPTH_INFINITE, null);
					// reveal and select the newly create system element
					IWorkbenchWindow workbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
					IWorkbenchPage workbenchPage = workbenchWindow.getActivePage();
					final IWorkbenchPart activePart = workbenchPage.getActivePart();
					if (activePart instanceof ISetSelectionTarget) {
						getShell().getDisplay().asyncExec(new Runnable() {
							public void run() {
								((ISetSelectionTarget) activePart).selectReveal(_selection);
							}
						});
					}
					IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(entityCreatingWizardPage.getContainerFullPath().append(elementName + ".system"));
					workbenchPage.openEditor(new FileEditorInput(file), PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(fname).getId());
				}
			};
			// run the new entity creation operation
			try {
				getContainer().run(false, true, op);
			} catch (InterruptedException e) {
				entityCreatingWizardPage.setMessage("System skin creation was interrupted. Please try again", WizardPage.WARNING);
				return false;
			} catch (InvocationTargetException e) {
				Throwable targetException = e.getTargetException();
				if (targetException != null) {
					entityCreatingWizardPage.setErrorMessage(targetException.getMessage());
				} else {
					entityCreatingWizardPage.setErrorMessage("An unexpected error occurred. See logs for more details");
				}
				return false;
			}
		} catch (Exception e) {
			String msg = e.getMessage();
			if (msg == null || msg.trim().length() == 0) {
				msg = "An error occured while creating the system skin";
			}
			entityCreatingWizardPage.setErrorMessage(msg);
			e.printStackTrace();
			return false;
		}

		return true;
	}

	private boolean isSystemElementsAlreadyCreated(IContainer folder) throws CoreException {
		for (IResource resource : folder.members()) {
			if (resource instanceof IContainer) {
				if (isSystemElementsAlreadyCreated((IContainer) resource)) {
					return true;
				}
			} else if (resource instanceof IFile) {
				IFile file = (IFile) resource;
				if (file.getName().endsWith(".system")) {
					return true;
				}
			}
		}
		return false;
	}

}