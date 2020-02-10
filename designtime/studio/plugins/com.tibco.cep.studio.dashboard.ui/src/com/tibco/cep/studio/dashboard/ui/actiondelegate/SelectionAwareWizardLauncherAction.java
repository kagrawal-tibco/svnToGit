package com.tibco.cep.studio.dashboard.ui.actiondelegate;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWizard;

import com.tibco.cep.studio.dashboard.ui.DashboardUIPlugin;

public class SelectionAwareWizardLauncherAction extends Action {

	protected IWorkbenchWindow window;

	protected IStructuredSelection selection;

	protected String extensionId;

	protected String wizardId;

	SelectionAwareWizardLauncherAction(IWorkbenchWindow window, String text, int style, String extensionId, String wizardId) {
		super(text, style);
		this.window = window;
		this.extensionId = extensionId;
		this.wizardId = wizardId;
	}

	public void selectionChanged(IStructuredSelection selection) {
		this.selection = selection;
	}

	public void run() {
		try {
			IWorkbenchWizard wizard = createWizard();
			if (wizard != null) {
				wizard.init(window.getWorkbench(), selection);
				WizardDialog dialog = new WizardDialog(window.getShell(), wizard);
				dialog.open();
			}
			else {
				DashboardUIPlugin.getInstance().getLog().log(new Status(IStatus.ERROR, DashboardUIPlugin.PLUGIN_ID, "No wizard found with id as "+wizardId+" under "+extensionId));
				MessageDialog.openError(window.getShell(), getText(), "could not find appropriate wizard to launch");
			}
		} catch (CoreException e) {
			DashboardUIPlugin.getInstance().getLog().log(e.getStatus());
			MessageDialog.openError(window.getShell(), getText(), e.getStatus().getMessage());
		}
	}

	protected IWorkbenchWizard createWizard() throws CoreException {
		IExtension extension = Platform.getExtensionRegistry().getExtension("org.eclipse.ui.newWizards", extensionId);
		if (extension != null) {
			IConfigurationElement[] configurationElements = extension.getConfigurationElements();
			for (IConfigurationElement iConfigurationElement : configurationElements) {
				if (wizardId.equals(iConfigurationElement.getAttribute("id")) == true){
					Object executableExtension = iConfigurationElement.createExecutableExtension("class");
					if (executableExtension instanceof IWorkbenchWizard) {
						return (IWorkbenchWizard) executableExtension;
					}
				}
			}
		}
		return null;
	}

}