package com.tibco.cep.studio.ui.wizards;


import org.eclipse.core.resources.IProject;
import org.eclipse.ui.IWorkbenchWindow;

import com.tibco.cep.studio.common.configuration.StudioProjectConfiguration;
import com.tibco.cep.studio.core.configuration.manager.StudioProjectConfigurationManager;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.util.Messages;
import com.tibco.cep.studio.ui.widgets.StudioWizard;

/**
 * 
 * @author sasahoo
 * 
 */
public class EnterpriseArchiveBuildWizard extends StudioWizard {

	private EnterpriseArchiveBuildWizardPage fDeployableWizardBuildPage;
	
	private IWorkbenchWindow fWindow;
	
	private IProject fProject;

	/**
	 * @param window
	 * @param title
	 * @param selection 
	 */
	public EnterpriseArchiveBuildWizard(IWorkbenchWindow window, String title, IProject project) {
		setWindowTitle(title);
		setDefaultPageImageDescriptor(StudioUIPlugin.getImageDescriptor("icons/buildEARWizard.png"));
		this.fWindow = window;
		this.fProject = project;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#addPages()
	 */
	@Override
	public void addPages() {
		String projectName = this.fProject.getName();
		if (projectName != null) {
			this.fDeployableWizardBuildPage = new EnterpriseArchiveBuildWizardPage(projectName);
			addPage(this.fDeployableWizardBuildPage);
		}
	}
	

	


	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#performCancel()
	 */
	@Override
	public boolean performCancel() {
		return true;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		try {
			String projectName = this.fProject.getName();
			StudioProjectConfiguration pc = StudioProjectConfigurationManager
					.getInstance().getProjectConfiguration(projectName);

			StudioProjectConfigurationManager.getInstance().saveConfiguration(
					projectName, pc);
		} catch (Exception e1) {
			StudioUIPlugin.errorDialog(this.fWindow.getShell(), Messages
					.getString("Build.EAR.title"), Messages
					.getString("Build.EAR.failed"), e1);
		}
		return true;
	}

	/**
	 * @return
	 */
	public EnterpriseArchiveBuildWizardPage getProjectPage() {
		return fDeployableWizardBuildPage;
	}

	@Override
	public boolean isGlobalVarAvailable() {
		return true;
	}

	@Override
	public String getProjectName() {
		return this.fProject.getName();
	}

}