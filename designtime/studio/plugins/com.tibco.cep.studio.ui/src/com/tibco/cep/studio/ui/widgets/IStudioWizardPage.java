package com.tibco.cep.studio.ui.widgets;

import org.eclipse.jface.wizard.IWizardPage;

/**
 * 
 * @author sasahoo
 *
 */
public interface IStudioWizardPage extends IWizardPage {

    /**
     * Notifies that Global Variable has been requested for this dialog page.
     */
    public void performGlobalVariable(String projectName);

	
}
