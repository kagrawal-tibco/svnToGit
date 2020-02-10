package com.tibco.cep.studio.ui.wizards;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardContainer;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.wizards.newresource.BasicNewProjectResourceWizard;

import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.actions.CreateStudioProjectOperation;
import com.tibco.cep.studio.ui.util.Messages;
import com.tibco.cep.studio.ui.util.StudioResourceUtils;
import com.tibco.cep.studio.ui.util.StudioWorkbenchConstants;


/**
 *@author sasahoo
 */

public class NewStudioProjectWizard extends Wizard implements INewWizard {

	public static NewStudioProjectWizardPage _mainPage;
	public IProject _newProject;
	private IConfigurationElement _config;

	public NewStudioProjectWizard() {
		setWindowTitle(Messages.getString("DesignerProjectNewWizard_Title"));
		setDefaultPageImageDescriptor(StudioUIPlugin.getImageDescriptor("icons/wizard/newStudioProjectWizard.png"));
	}

	/***************************************************************************
	 * Add the one page to the wizard, the reused page
	 * <code>WizardNewProjectCreationPage</code>. This page provides basic
	 * project name validation and allows for
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#addPages()
	 */
	public void addPages() {
		_mainPage = new NewStudioProjectWizardPage(Messages.getString("DesignerProjectNewWizard_Title"));
		_mainPage.setDescription(Messages.getString("DesignerProjectNewWizard_Description"));
		_mainPage.setTitle(Messages.getString("DesignerProjectNewWizard_Title"));
		addPage(_mainPage);
	}

	/***************************************************************************
	 * Returns the newly created project.
	 * 
	 * @return the created project, or <code>null</code> if project not
	 *         created
	 */
	public IProject getNewProject() {
		return _newProject;
	}

	/***************************************************************************
	 * Initializes this creation wizard using the passed workbench and object
	 * selection.
	 * <p>
	 * This method is called after the no argument constructor and before other
	 * methods are called.
	 * </p>
	 * 
	 * @param workbench
	 *            the current workbench
	 * @param selection
	 *            the current object selection
	 * 
	 * @see org.eclipse.ui.IWorkbenchWizard#init(IWorkbench,
	 *      IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		// set the Parser
		System.setProperty("javax.xml.parsers.DocumentBuilderFactory",
		"com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl");
		setNeedsProgressMonitor(true);
	}
	/***************************************************************************
	 * Creates a project with a <code>CustomNature</code> association.
	 * 
	 * @return <code>true</code> to indicate the finish request was accepted,
	 *         and <code>false</code> to indicate that the finish request was
	 *         refused
	 * 
	 * @see org.eclipse.jface.wizard.IWizard#performFinish()
	 */
	public boolean performFinish() {
		if(!_mainPage.getLocationPath().toFile().exists()){
			resultError();
			return false;
		}
		createNewProject();
		if (_newProject == null)
			return false;
		if (_config != null) {
			BasicNewProjectResourceWizard.updatePerspective(_config);
			BasicNewProjectResourceWizard.selectAndReveal(this._newProject, PlatformUI.getWorkbench().getActiveWorkbenchWindow());
		}
		StudioWorkbenchConstants._newProject = _newProject;//Saving the new project
		//Switching to Designer perspective
		StudioResourceUtils.switchStudioPerspective();

		return true;
	}

	/***************************************************************************
	 * Creates a new project resource with the name selected in the wizard page.
	 * Project creation is wrapped in a <code>WorkspaceModifyOperation</code>.
	 * <p>
	 * 
	 * @see org.eclipse.ui.actions.WorkspaceModifyOperation
	 * 
	 * @return the created project resource, or <code>null</code> if the
	 *         project was not created
	 */
	public IProject createNewProject(){
		try{
			_newProject = _mainPage.getProjectHandle();
			//File targetLocation = new File(_mainPage.getLocationPath().toOSString() + Path.SEPARATOR + _mainPage.getProjectName()); 
			File targetLocation = new File(_mainPage.getLocationPath().toOSString());
			WorkspaceModifyOperation op = new CreateStudioProjectOperation(_newProject, targetLocation, _mainPage.useDefaults());
			try {
				getContainer().run(true, true, op);
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//Switching to Designer perspective if current one is any other perspective
			StudioResourceUtils.switchStudioPerspective();

		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
		return _newProject;
	}

	protected void resultError() {
		_mainPage.setErrorMessage(Messages.getString("new_project_creation_fail"));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.IExecutableExtension#setInitializationData(
	 *      org.eclipse.core.runtime.IConfigurationElement, java.lang.String,
	 *      java.lang.Object)
	 */
	public void setInitializationData(IConfigurationElement config,
			String propertyName, Object data) throws CoreException {
		_config = config;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#setContainer(org.eclipse.jface.wizard.IWizardContainer)
	 */
	public void setContainer(IWizardContainer wizardContainer) {
		if (wizardContainer != null) {
			((WizardDialog) wizardContainer).setHelpAvailable(false);
		}
		super.setContainer(wizardContainer);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#isHelpAvailable()
	 */
	public boolean isHelpAvailable() {
		return false;
	}
}