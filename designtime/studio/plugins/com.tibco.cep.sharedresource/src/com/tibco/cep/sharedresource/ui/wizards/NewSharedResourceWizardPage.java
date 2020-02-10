package com.tibco.cep.sharedresource.ui.wizards;

import java.io.InputStream;

import org.eclipse.jface.viewers.IStructuredSelection;

import com.tibco.cep.studio.ui.wizards.StudioNewFileWizardPage;


/*
@author ssailapp
@date Sep 21, 2009 2:30:13 PM
 */

public class NewSharedResourceWizardPage extends StudioNewFileWizardPage {

    public NewSharedResourceWizardPage(String pageName, IStructuredSelection selection) {
        super(pageName, selection);
    }

    protected boolean validatePage() {
    	return (validateSharedResourceFileName() && super.validatePage());
    }
    
    @Override
    protected InputStream getInitialContents() {
        return null;
    	/*
        try {
            return EditorsUIPlugin.getDefault().getBundle().getEntry("/templates/sharedrestemplate.txt").openStream();
        } catch (IOException e) {
            return null; // ignore and create empty comments
        }
        */
    }
}
