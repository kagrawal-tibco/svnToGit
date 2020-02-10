package com.tibco.cep.studio.dashboard.ui.wizards;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;

import com.tibco.cep.studio.dashboard.core.insight.model.LocalConfig;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalHeader;
import com.tibco.cep.studio.dashboard.ui.DashboardUIPlugin;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;
import com.tibco.cep.studio.ui.wizards.EntityFileCreationWizard;

public class HeaderWizard extends BaseViewsElementWizard {

	private ImageReferringEntityFileCreationWizardPage page;

	public HeaderWizard() {
		super(BEViewsElementNames.HEADER, BEViewsElementNames.HEADER, "New Header Wizard", "HeaderPage", "New Header", "Create a new Header");
		setDefaultPageImageDescriptor(DashboardUIPlugin.getInstance().getImageRegistry().getDescriptor("header_wizard.png"));
	}

	protected EntityFileCreationWizard createPage() {
		page = new ImageReferringEntityFileCreationWizardPage(pageName, _selection, elementType, elementTypeName);
		page.setDescription(pageDesc);
		page.setTitle(pageTitle);
		return page;
	}

	@Override
	public boolean performFinish() {
		final IProject project = entityCreatingWizardPage.getProject();
		try {
			if (isHeaderAlreadyCreated(project)) {
				MessageDialog.openError(getShell(), "Header", "Header already created in the project.");
				return true;
			}
			return super.performFinish();
		} catch (CoreException e) {
			String msg = e.getMessage();
			if (msg == null || msg.trim().length() == 0) {
				msg = "An error occured while creating the header page";
			}
			entityCreatingWizardPage.setErrorMessage(msg);
			e.printStackTrace();
			return false;
		}
	}

	private boolean isHeaderAlreadyCreated(IContainer folder) throws CoreException {
		for (IResource resource : folder.members()) {
			if (resource instanceof IContainer) {
				if (isHeaderAlreadyCreated((IContainer) resource)) {
					return true;
				}
			} else if (resource instanceof IFile) {
				IFile file = (IFile) resource;
				if (file.getName().endsWith(".header")) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	protected void persistFirstPage(LocalConfig localConfig, String elementName, String elementDesc, String baseURI, IProgressMonitor monitor) throws Exception {
		IResource imageToUse = page.getSelectedImageResource();
		if (imageToUse != null) {
			localConfig.setPropertyValue(LocalHeader.PROP_KEY_HEADER_IMAGE, "/"+imageToUse.getProjectRelativePath());
		}
		super.persistFirstPage(localConfig, elementName, elementDesc, baseURI, monitor);
	}

}