/**
 * 
 */
package com.tibco.cep.studio.ui.wizards;

import org.eclipse.jface.viewers.IStructuredSelection;

import com.tibco.cep.studio.core.util.StudioJavaUtil;
import com.tibco.cep.studio.ui.util.Messages;

/**
 * @author sshekhar
 *
 */
public class JavaSourceFileCreationWizard extends EntityFileCreationWizard {

	/**
	 * @param pageName
	 * @param selection
	 * @param type
	 */
	public JavaSourceFileCreationWizard(String pageName, IStructuredSelection selection, String type ) {
		super(pageName, selection, type);
	}

	/**
	 * @param pageName
	 * @param selection
	 * @param type
	 * @param currentProjectName
	 */
	public JavaSourceFileCreationWizard(String pageName,
			IStructuredSelection selection, String type, String currentProjectName) {
		super(pageName, selection, type, currentProjectName);
	}


	@Override
	protected boolean validatePage() {

		boolean valid  = super.validatePage();
		if (!valid) {
			return valid;
		}

		if (!StudioJavaUtil.isInsideJavaSourceFolder(getContainerFullPath().toPortableString(), getProject().getName())) {
			String problemMessage = Messages.getString("invalid.javaSource.folder");
			setErrorMessage(problemMessage);
			return false;
		}

		return true;
	}
}
