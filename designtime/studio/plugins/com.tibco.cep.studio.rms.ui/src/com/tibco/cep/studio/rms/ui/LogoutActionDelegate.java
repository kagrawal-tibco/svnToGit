package com.tibco.cep.studio.rms.ui;

import static com.tibco.cep.security.util.AuthTokenUtils.cleanResidualToken;
import static com.tibco.cep.security.util.AuthTokenUtils.getLoggedinUser;
import static com.tibco.cep.studio.rms.ui.utils.ActionConstants.CHECKOUT_ACTION;
import static com.tibco.cep.studio.rms.ui.utils.ActionConstants.COMMIT_ACTION;
import static com.tibco.cep.studio.rms.ui.utils.ActionConstants.GENERATE_DEPLOYABLE_ACTION;
import static com.tibco.cep.studio.rms.ui.utils.ActionConstants.LOGIN_ACTION;
import static com.tibco.cep.studio.rms.ui.utils.ActionConstants.LOGOUT_ACTION;
import static com.tibco.cep.studio.rms.ui.utils.ActionConstants.REVERT_ACTION;
import static com.tibco.cep.studio.rms.ui.utils.ActionConstants.UPDATE_ACTION;
import static com.tibco.cep.studio.rms.ui.utils.ActionConstants.WORKLIST_ACTION;
import static com.tibco.cep.studio.rms.ui.utils.RMSUIUtils.enableDisableAction;
import static com.tibco.cep.studio.rms.ui.utils.RMSUIUtils.goOffline;
import static com.tibco.cep.studio.rms.ui.utils.RMSUIUtils.setlogged;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.tibco.cep.studio.rms.client.ArtifactsManagerClient;
import com.tibco.cep.studio.rms.client.PingerServiceManager;
import com.tibco.cep.studio.rms.core.utils.RMSUtil;
import com.tibco.cep.studio.rms.model.Error;
import com.tibco.cep.studio.rms.ui.actions.AbstractRMSAction;
import com.tibco.cep.studio.rms.ui.utils.ActionConstants;
import com.tibco.cep.studio.rms.ui.utils.Messages;
import com.tibco.cep.studio.ui.StudioUIManager;


public class LogoutActionDelegate extends AbstractRMSAction {

	private IWorkbenchWindow window;

	@Override
	public void init(IAction action) {
		this.window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		StudioUIManager.getInstance().addAction(ActionConstants.LOGOUT_ACTION, action);
		action.setEnabled(false);
	}

	@Override
	public void runWithEvent(IAction action, Event event) {
		String loginURL = RMSUtil.buildRMSURL();
		try {
			Object response = ArtifactsManagerClient.performLogout(getLoggedinUser(), loginURL);
			if (response instanceof String) {
				//Stop relevant pinger
				PingerServiceManager.stopPinger(loginURL);
				MessageDialog.openInformation(window.getShell(), Messages.getString("Logout_Title"), Messages.getString("Logout_Success", response));
			} else if (response instanceof Error) {
				//User is not logged in. Enable login.
				Error error = (Error)response;
				MessageDialog.openError(window.getShell(), Messages.getString("Logout_Title"), error.getErrorString());
			}
			enableDisableActions();
		} catch (Exception e) {
			MessageDialog.openError(window.getShell(), Messages.getString("Logout_Title"), e.getMessage());
			return;
		}
		setlogged(false);
		cleanResidualToken();//Clear the token
		goOffline();
	}

	private void enableDisableActions() {
		enableDisableAction(false, LOGOUT_ACTION);
		enableDisableAction(true, LOGIN_ACTION);
		enableDisableAction(false, CHECKOUT_ACTION);
		enableDisableAction(false, COMMIT_ACTION);
		enableDisableAction(false, WORKLIST_ACTION);
		enableDisableAction(false, UPDATE_ACTION);
		enableDisableAction(false, REVERT_ACTION);
		enableDisableAction(false, GENERATE_DEPLOYABLE_ACTION);
	}
}
