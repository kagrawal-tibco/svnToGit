package com.tibco.cep.studio.ui.wizards;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;
import org.eclipse.ui.internal.ide.IDEWorkbenchMessages;

import com.tibco.cep.studio.common.util.EntityNameHelper;
import com.tibco.cep.studio.ui.util.Messages;

/**
 * @author sasahoo
 * 
 */
public class NewStudioProjectWizardPage extends WizardNewProjectCreationPage {
	
    /**
     * 
     * @param pageName
     */
	public NewStudioProjectWizardPage(String pageName) {
		super(pageName);
	}

	 /* (non-Javadoc)
	 * @see org.eclipse.ui.dialogs.WizardNewProjectCreationPage#validatePage()
	 */
	protected boolean validatePage() {
		
		// check whether the project name is valid
		if(getProjectName().length() == 0) {
			setErrorMessage(Messages.getString("Empty_Name"));
			setPageComplete(false);
			return false;
		}
		
		final IStatus nameStatus= ResourcesPlugin.getWorkspace().validateName(getProjectName(), IResource.PROJECT);
		if (!nameStatus.isOK()) {
			String problemMessage = Messages.getString("BE_Project_invalidname", getProjectName());
//			setErrorMessage(nameStatus.getMessage());
			setErrorMessage(problemMessage);
			setPageComplete(false);
			return false;
		}
		// BE-11670 : check to see if the name contains non-ASCII character
		if (!EntityNameHelper.isPureAscii(getProjectName())) {
			String problemMessage = Messages.getString("BE_Project_nonasciiname", getProjectName());
			setErrorMessage(problemMessage);
			setPageComplete(false);
			return false;
		}
		// this was commented out in rev 27087 on 12/29/09.  I'm not sure why, 
		// as it would allow spaces and invalid chars in project name, causing errors (rhollom)
		if(!EntityNameHelper.isValidBEProjectIdentifier(getProjectName())){
			String problemMessage = Messages.getString("BE_Project_invalidname", getProjectName());
			setErrorMessage(problemMessage);
			return false;
		}
		
		
		/* BE-14713
 Studio project Creation : New Studio project Wizard can create project with name as existing
 project but with different Case. But after creation project is not there in workspace. 
  */
		IProject[] ip=ResourcesPlugin.getWorkspace().getRoot().getProjects();
		for(IProject iProject : ip){
			if(iProject.getName().equalsIgnoreCase(getProjectName())){
				 setErrorMessage(IDEWorkbenchMessages.WizardNewProjectCreationPage_projectExistsMessage);
		            return false;
			}
		}
				
		boolean isValid = super.validatePage();
		return isValid;
	}
}
