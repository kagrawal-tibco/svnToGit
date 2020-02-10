package com.tibco.cep.studio.ui.wizards;

import org.eclipse.core.resources.IFile;
import org.eclipse.ui.INewWizard;

public interface INewSharedResourceWizard extends INewWizard {
	
	/**
	 * return true if the wizard allows opening of the editor after creating the new resource
	 * @return
	 */
	boolean canOpenEditor();
	
	/**
	 * set to true if the wizard allows opening of the editor after creating the new resource
	 * @param allow
	 */
	void setOpenEditor(boolean allow);
	
	/**
	 * returns the IFile object for this resource 
	 * @return
	 */
	
	IFile getFile();

}
