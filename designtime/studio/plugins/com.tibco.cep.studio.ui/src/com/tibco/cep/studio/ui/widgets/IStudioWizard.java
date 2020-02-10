package com.tibco.cep.studio.ui.widgets;

import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.jface.wizard.IWizard;


/**
 * 
 * @author sasahoo
 *
 */
public interface IStudioWizard extends IWizard {

	
    /**
	 * Returns whether Global Variable is available for this wizard.
	 * <p>
	 * The result of this method is typically used by the container to show or hide a button labeled
	 * "Global Variable".
	 * </p>
	 * <p>
	 * <strong>Note:</strong> This wizard's container might be a {@link TrayDialog} which provides
	 * its own Global Variable support that is independent of this property.
	 * </p>
	 * <p>
	 * <strong>Note 2:</strong> In the default {@link StudioWizardDialog} implementation, the "Global Variable"
	 * button only works when {@link org.eclipse.jface.dialogs.IDialogPage#performHelp()} is implemented.
	 * </p>
	 * 
	 * @return <code>true</code> if Global Variable is available, <code>false</code> otherwise
	 */
    public boolean isGlobalVarAvailable();
    
    /**
     * Returns current project in this wizard.
     *
     * @return the project Name
     */
    public String getProjectName(); 
	
}
