package com.tibco.cep.studio.rms.ui.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;

import com.tibco.cep.studio.rms.client.ArtifactsManagerClient;
import com.tibco.cep.studio.rms.core.utils.RMSUtil;
import com.tibco.cep.studio.rms.ui.utils.ActionConstants;
import com.tibco.cep.studio.rms.ui.utils.RMSUIUtils;
import com.tibco.cep.studio.rms.ui.wizards.RMSArtifactsCheckoutDialog;
import com.tibco.cep.studio.ui.StudioUIManager;
/**
 * 
 * @author hitesh
 *
 */
public class CheckoutActionDelegate extends AbstractRMSAction {	

	private boolean status;
	
	private boolean okButtonPressed;
	
	private String url;
	
	private String directoryLocation;
	
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDirectoryLocation() {
		return directoryLocation;
	}

	public void setDirectoryLocation(String directoryLocation) {
		this.directoryLocation = directoryLocation;
	}
	
	@Override
	public void init(IAction action) {
		StudioUIManager.getInstance().addAction(ActionConstants.CHECKOUT_ACTION, action);
		action.setEnabled(false);
	}
	
	@Override
	public void runWithEvent(IAction action, Event event) {
		
		setOkButtonPressed(false);
		String[] projectsList = null;
		try {
			// Use the same URL as was used for successful login
			String loginURL = RMSUtil.buildRMSURL();
			projectsList = ArtifactsManagerClient.fetchServedProjects(loginURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//Should not be enabled on a selection
		RMSArtifactsCheckoutDialog dialog = new RMSArtifactsCheckoutDialog(
					shell, "Checkout", projectsList);
		dialog.open();
	}

	public boolean isStatus() {
		return status;
	}
	
	public void setStatus(boolean status) {
		this.status = status;
	}

	public boolean isOkButtonPressed() {
		return okButtonPressed;
	}

	public void setOkButtonPressed(boolean okButtonPressed) {
		this.okButtonPressed = okButtonPressed;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.cep.studio.rms.actions.AbstractRMSAction#selectionChanged(org
	 * .eclipse.jface.action.IAction, org.eclipse.jface.viewers.ISelection)
	 */
	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		super.selectionChanged(action, selection);
		boolean isLoggedIn = RMSUIUtils.islogged();
		action.setEnabled(isLoggedIn);
	}
	
	public static class WarningConfirmationDialog extends MessageDialog {

		public WarningConfirmationDialog(Shell parentShell, String dialogTitle,
				Image dialogTitleImage, String dialogMessage,
				int dialogImageType, String[] dialogButtonLabels,
				int defaultIndex) {
			super(parentShell, dialogTitle, dialogTitleImage, dialogMessage,
					dialogImageType, dialogButtonLabels, defaultIndex);
			// TODO Auto-generated constructor stub
		}

		public static boolean openConfirm0(Shell parent, String title,
				String message) {
			WarningConfirmationDialog dialog = new WarningConfirmationDialog(
					parent, title, null, // accept the default window icon
					message, WARNING, new String[] { IDialogConstants.OK_LABEL,
							IDialogConstants.CANCEL_LABEL }, 0); // OK is the default
			return dialog.open() == 0;
		}
	}
}
