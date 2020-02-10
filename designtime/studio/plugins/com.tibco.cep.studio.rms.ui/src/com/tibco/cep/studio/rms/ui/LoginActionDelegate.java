package com.tibco.cep.studio.rms.ui;

import static com.tibco.cep.studio.rms.ui.utils.RMSUIUtils.setlogged;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.tibco.cep.studio.rms.ui.actions.AbstractRMSAction;
import com.tibco.cep.studio.rms.ui.utils.ActionConstants;
import com.tibco.cep.studio.rms.ui.utils.Messages;
import com.tibco.cep.studio.ui.StudioUIManager;

/**
 * 
 * @author hnembhwa
 * 
 */

public class LoginActionDelegate extends AbstractRMSAction {

	private boolean okButtonPressed;
	private boolean status;
	private String recentURL;
	private String userName;
	private String password;
	private boolean savePassword;
	private IWorkbenchWindow window;
	private Exception ex; // Any exception during connect

	public static final String CLASS = LoginActionDelegate.class.getName();	
	
	public boolean isOkButtonPressed() {
		return okButtonPressed;
	}
	
	public void setOkButtonPressed(boolean okButtonPressed) {
		this.okButtonPressed = okButtonPressed;
	}
	public String getRecentURL() {
		return recentURL;
	}

	public void setRecentURL(String recentURL) {
		this.recentURL = recentURL;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	
	public final void setEx(Exception ex) {
		this.ex = ex;
	}
	
	public Exception getEx() {
		return ex;
	}

	public IWorkbenchWindow getWindow() {
		return window;
	}

	public boolean isSavePassword() {
		return savePassword;
	}

	public void setSavePassword(boolean savePassword) {
		this.savePassword = savePassword;
	}

	public boolean isStatus() {
		return status;
	}

	@Override
	public void init(IAction action) {
		this.window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		StudioUIManager.getInstance().addAction(ActionConstants.LOGIN_ACTION, action);
	}

	@Override
	public void runWithEvent(IAction action, Event event) {
		//super.run(action);
		Shell shell = this.window.getShell();
//		DecisionTableUtil.hideAllEditorPopups(window.getActivePage().getActiveEditor(), true);
		AuthenticationWizard wizard = new AuthenticationWizard(this);
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
		if (isOkButtonPressed()) {
			if (isStatus()) {
				MessageDialog.openInformation(this.window.getShell(),
						Messages.getString("Login_title"), Messages.getString("Login_message_success"));
				setlogged(true);
			} else {
				MessageDialog.openError(window.getShell(), Messages.getString("Login_title"), Messages.getString("Login_message_failed"));
				runWithEvent(action, event);
			}
		}
	}
}
