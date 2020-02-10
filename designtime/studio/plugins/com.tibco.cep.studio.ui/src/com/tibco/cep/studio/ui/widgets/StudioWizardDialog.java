package com.tibco.cep.studio.ui.widgets;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;

import com.tibco.cep.studio.ui.util.GvUiUtil;

/**
 * 
 * @author sasahoo
 *
 */
public class StudioWizardDialog extends WizardDialog {

	private IStudioWizard wizard;

	/**
	 * @param parentShell
	 * @param wizard
	 */
	public StudioWizardDialog(Shell parentShell, IStudioWizard wizard) {
		super(parentShell, wizard);
		this.wizard = wizard;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.TrayDialog#createHelpControl(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createHelpControl(Composite parent) {
		Control helpControl = super.createHelpControl(parent);
		if (wizard.isGlobalVarAvailable()) {
			GvUiUtil gvUiUtil = new GvUiUtil(ResourcesPlugin.getWorkspace().getRoot().getProject(wizard.getProjectName()));
			if (helpControl instanceof ToolBar) {
				ToolBar toolBar = (ToolBar) helpControl;
				gvUiUtil.createGvButton(toolBar);
			} 
		}
		return helpControl;
	}
}