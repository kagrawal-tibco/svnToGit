package com.tibco.cep.studio.rms.ui.actions;


import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;

import com.tibco.cep.studio.rms.client.ArtifactsManagerClient;
import com.tibco.cep.studio.rms.core.utils.RMSUtil;
import com.tibco.cep.studio.rms.ui.RMSUIPlugin;
import com.tibco.cep.studio.rms.ui.utils.ActionConstants;
import com.tibco.cep.studio.rms.ui.utils.RMSUIUtils;
import com.tibco.cep.studio.rms.ui.wizards.RMSGenerateDeployableWizard;
import com.tibco.cep.studio.ui.StudioUIManager;

/**
 * @author hitesh
 *
 */
public class GenerateDeployableAction extends AbstractRMSAction implements IWorkbenchWindowActionDelegate {
	
	private boolean success = true;
	private boolean cancel = false;
	private String error = null;
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.rms.ui.actions.AbstractRMSAction#selectionChanged(org.eclipse.jface.action.IAction, org.eclipse.jface.viewers.ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		super.selectionChanged(action, selection);
		boolean isLoggedIn = RMSUIUtils.islogged();
		action.setEnabled(isLoggedIn);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate2#init(org.eclipse.jface.action.IAction)
	 */
	@Override
	public void init(IAction action) {
		StudioUIManager.getInstance().addAction(ActionConstants.GENERATE_DEPLOYABLE_ACTION, action);
		action.setEnabled(false);
		window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		shell = window.getShell();;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate2#runWithEvent(org.eclipse.jface.action.IAction, org.eclipse.swt.widgets.Event)
	 */
	public void runWithEvent(IAction action, Event event) {
		try {
			String[] projectsList = null;

			// Use the same URL as was used for successful login
			String loginURL = RMSUtil.buildRMSURL();
			projectsList = ArtifactsManagerClient.fetchServedProjects(loginURL);
			RMSGenerateDeployableWizard wizard = new RMSGenerateDeployableWizard(window, this, "Generate Deployable", projectsList);
			wizard.setNeedsProgressMonitor(true);
			WizardDialog dialog = new WizardDialog(shell, wizard) {
				@Override
				protected void createButtonsForButtonBar(Composite parent) {
					super.createButtonsForButtonBar(parent);
					Button finishButton = getButton(IDialogConstants.FINISH_ID);
					finishButton.setText(IDialogConstants.OK_LABEL);
				}
			};
			dialog.setMinimumPageSize(320, 5);
			dialog.create();
			dialog.open();
			if (cancel) {
				MessageDialog.openError(window.getShell(), 
										"Generate Deployable", 
										"Deployable generation has been cancelled");
				cancel=false;
				return;
			}
			if (success) {
				MessageDialog.openInformation(window.getShell(), 
						"Generate Deployable", 
						"Deployable generated successfully.");
			} else {
				
				String errorStr  = error == null ? "" : error;
				
				MessageDialog.openError(window.getShell(), 
						"Generate Deployable", 
						"Deployable generation has been failed." + errorStr);
			}
			
		} catch (Exception e) {
			RMSUIPlugin.debug(e.getMessage());
		}
	}
	
	/**
	 * @param success
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	/**
	 * @param cancel
	 */
	public void setCancelled(boolean cancel) {
		this.cancel = cancel;
	}
	
	/**
	 * @param error
	 */
	public void setError(String error) {
		this.error = error;
	}
}