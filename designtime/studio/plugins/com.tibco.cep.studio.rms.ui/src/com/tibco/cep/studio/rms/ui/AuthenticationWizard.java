package com.tibco.cep.studio.rms.ui;

import static com.tibco.cep.studio.rms.ui.utils.RMSUIUtils.checkServerStatus;
import static com.tibco.cep.studio.rms.ui.utils.RMSUIUtils.setlogged;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.tibco.cep.security.tokens.AuthToken;
import com.tibco.cep.security.util.AuthTokenUtils;
import com.tibco.cep.studio.rms.client.ArtifactsManagerClient;
import com.tibco.cep.studio.rms.ui.listener.AuthCompletionEvent;
import com.tibco.cep.studio.rms.ui.listener.IAuthCompletionListener;
import com.tibco.cep.studio.rms.ui.listener.impl.AuthPreferenceListener;
import com.tibco.cep.studio.rms.ui.listener.impl.AuthStatusLineInfoUpdateListener;
import com.tibco.cep.studio.rms.ui.listener.impl.PostAuthMenuUpdateListener;
import com.tibco.cep.studio.rms.ui.utils.Messages;

/**
 * 
 * The wizard class for authentication.
 * 
 * @author hnembhwa
 * 
 */

public class AuthenticationWizard extends Wizard {
	@SuppressWarnings("unused")
	private IWorkbenchWindow window;
	private AuthenticationPage authenticationPage;
	@SuppressWarnings("unused")
	private IAction action;
	
	private LoginActionDelegate loginAction;
	
	private List<IAuthCompletionListener> authCompletionListeners = new ArrayList<IAuthCompletionListener>();

	public AuthenticationWizard(LoginActionDelegate loginAction) {
		this.window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		setWindowTitle(Messages.getString("Login_wizard"));
		this.loginAction = loginAction;
		authCompletionListeners.add(new AuthPreferenceListener());
		authCompletionListeners.add(new PostAuthMenuUpdateListener());
		authCompletionListeners.add(new AuthStatusLineInfoUpdateListener());
	}

	@Override
	public void addPages() {
		authenticationPage = new AuthenticationPage();
		addPage(authenticationPage);
//		addPage(new OnewaySSLConfigPage());
//		addPage(new TwoWaySSLConfigPage());
	}

	@Override
	public boolean performCancel() {
		loginAction.setOkButtonPressed(false);
		return true;
	}

	@Override
	public boolean performFinish() {
		loginAction.setOkButtonPressed(true);
		IRunnableWithProgress op = new IRunnableWithProgress() {

			public void run(IProgressMonitor monitor)
			throws InvocationTargetException, InterruptedException {
				monitor.beginTask(Messages.getString("Login_Task_Name"), 100);
				monitor.worked(10);
				String inUsername = null;
				String inPassword = null;
				String inURL = null;
//				boolean savePassword = false;

//				boolean useSSL = false;
//				String trustStorePath = null;
//				String trustStorePassword = null;
//				String trustStoreType = null;

				IWizardPage[] pages = getPages();
				for (IWizardPage page : pages) {
					if (page instanceof AuthenticationPage) {
						AuthenticationPage mp = (AuthenticationPage) page;
						inURL = mp.getURL();
						inUsername = mp.getUsername();
						inPassword = mp.getPassword();
//						useSSL = mp.useSSL();
//						savePassword = mp.getSavePasswordButton().getSelection();
					}
//					if (useSSL && page instanceof OnewaySSLConfigPage) {
//						OnewaySSLConfigPage sslPage = (OnewaySSLConfigPage)page;
//						//Get field values
//						trustStorePath = sslPage.getTrustorePath();
//						trustStorePassword = sslPage.getTrustorePassword();
//						trustStoreType = sslPage.getTrustoreType();
//					}
				}
				final String usrnme = inUsername;
				final String pswrd = inPassword;

				String tokenS = null;
				try {
					tokenS = ArtifactsManagerClient.authenticate(inURL, usrnme, pswrd);
					monitor.worked(40);
					if (tokenS != null) {
						AuthToken token = AuthTokenUtils.storeAuthToken(tokenS, RMSUIPlugin.getDefault().getStateLocation().toString() + AuthTokenUtils.AUTH_TOKEN_FILE);
						if (token == null) {
							//Failure Auth
							loginAction.setStatus(false);
							return;
						}
						fireAuthSuccessEvent(inURL, usrnme, pswrd);
						monitor.worked(50);
						monitor.setTaskName(Messages.getString("Login_Task_Name_Complete"));
						monitor.done();
						loginAction.setStatus(true);
						setlogged(true);
						checkServerStatus(inURL);
					} else {
						monitor.setTaskName(Messages.getString("Login_Task_Name_Complete"));
						monitor.done();
						loginAction.setStatus(false);
					}
				} catch (Exception e) {
					RMSUIPlugin.log(e);
					monitor.done();
					loginAction.setEx(e);
					loginAction.setStatus(false);
				}

			}
		};
		try {
			getContainer().run(false, true, op);
		} catch (InvocationTargetException e2) {
			RMSUIPlugin.log(e2);
		} catch (InterruptedException e2) {
			RMSUIPlugin.log(e2);
		}
		return true;
	}
	
	/**
	 * 
	 * @param <I>
	 * @param authCompletionListener
	 * @return
	 */
	public <I extends IAuthCompletionListener> boolean addAuthCompletionListener(I authCompletionListener) {
		return authCompletionListeners.add(authCompletionListener);
	}
	
	/**
	 * 
	 * @param <I>
	 * @param authCompletionListener
	 * @return
	 */
	public <I extends IAuthCompletionListener> boolean removeAuthCompletionListener(I authCompletionListener) {
		return authCompletionListeners.remove(authCompletionListener);
	}

	@Override
	public IWizardPage getStartingPage() {
		return super.getStartingPage();
	}

	
	private void fireAuthSuccessEvent(String authenticationURL, String username, String password) {
		AuthCompletionEvent authCompletionEvent = 
			new AuthCompletionEvent(authenticationURL, username, password, AuthCompletionEvent.AUTH_SUCCESS_EVENT);
		for (IAuthCompletionListener listener : authCompletionListeners) {
			listener.authCompleted(authCompletionEvent);
		}
	}
}
