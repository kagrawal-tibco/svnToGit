package com.tibco.cep.studio.rms.ui;



import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;

import com.tibco.cep.studio.rms.core.utils.RMSUtil;
import com.tibco.cep.studio.rms.preferences.util.RMSPreferenceUtils;
import com.tibco.cep.studio.rms.ui.listener.IAuthCompletionListener;
import com.tibco.cep.studio.rms.ui.listener.impl.SavePasswordListener;
import com.tibco.cep.studio.rms.ui.preferences.RMSPreferenceConstants;
import com.tibco.cep.studio.rms.ui.utils.Messages;
import com.tibco.cep.studio.rms.ui.utils.RMSImages;

/** 
 * @author hnembhwa
 * 
 */

public class AuthenticationPage extends WizardPage {

	
	private String username;
	
	private String password;
	
//	private String baseURL;
	private Text usernameText;
	
	private Text passwordText;
	
	private Combo urlCombo;
	
	private Button savePasswordButton;
	
	private boolean useSSL;
	
	private IAuthCompletionListener savePasswordListener = new SavePasswordListener();
	
	private IPreferenceStore preferenceStore = RMSUIPlugin.getDefault().getPreferenceStore();
	
	protected AuthenticationPage() {
		super("Authentication Page");
		setTitle(Messages.getString("Login_title"));
		setDescription(Messages.getString("Login_description"));
		setImageDescriptor(RMSImages.getImageDescriptor(RMSImages.RMS_IMAGES_AUTHENTICATION));
	}

	public void createControl(final Composite parent) {

		setPageComplete(false);

		Group composite = new Group(parent, SWT.SHADOW_ETCHED_IN);
		// composite.setText(Messages.Login);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		composite.setLayout(layout);
		
		Label urlLabel = new Label(composite, SWT.NONE);
		urlLabel.setText(Messages.getString("URL"));

		// URL ComboBox
		urlCombo = new Combo(composite, SWT.BORDER);
		
		int numberOfAuthURLS = preferenceStore.getInt(RMSPreferenceConstants.RMS_AUTH_URLS_SIZE);
		String[] authURLS = 
			RMSPreferenceUtils.getAuthenticationURLs(numberOfAuthURLS).toArray(new String[0]);
		
		authURLS = (authURLS.length == 0) ? new String[] {RMSUtil.buildRMSURL()} : authURLS;
				
		urlCombo.setItems(authURLS);
		urlCombo.setText(authURLS[0]);
		urlCombo.addTraverseListener(new TraverseListener() {

			public void keyTraversed(TraverseEvent e) {
				if (e.detail == SWT.TRAVERSE_RETURN) {
					e.doit = false;
					e.detail = SWT.TRAVERSE_NONE;
					String newText = urlCombo.getText();
					try {
						urlCombo.add(newText);
						urlCombo.setSelection(new Point(0, newText.length()));
					} catch (Exception ex) {
					}
				}
			}
		});
		urlCombo.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				validatePage();
			}
		});
		
		GridData data_combo = new GridData(GridData.FILL_HORIZONTAL);
		urlCombo.setLayoutData(data_combo);

		Label usernameLabel = new Label(composite, SWT.NONE);
		usernameLabel.setText(Messages.getString("Username"));

		// User name TextBox
		usernameText = new Text(composite, SWT.SINGLE | SWT.BORDER);
		GridData data_utext = new GridData(GridData.FILL_HORIZONTAL);
		usernameText.setLayoutData(data_utext);
		usernameText.addFocusListener(new PasswordFetchListener());
		usernameText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				validatePage();
			}
		});
		usernameText.setData(1);

		Label passwordLabel = new Label(composite, SWT.NONE);
		passwordLabel.setText(Messages.getString("Password"));

		// Password TextBox
		passwordText = new Text(composite, SWT.SINGLE | SWT.BORDER | SWT.PASSWORD);
		GridData data_ptext = new GridData(GridData.FILL_HORIZONTAL);
		passwordText.setLayoutData(data_ptext);
		passwordText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				validatePage();
			}
		});
		passwordText.setData(2);
		
		new Label(composite, SWT.NONE);
		
		Composite lComposite =  new Composite(composite, SWT.NONE | SWT.RIGHT_TO_LEFT);
		lComposite.setLayout(new GridLayout(2, false));
		lComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		Label savePasswordLabel = new Label(lComposite, SWT.NONE);
		savePasswordLabel.setText(Messages.getString("Save_Password"));
		
		savePasswordButton = new Button(lComposite, SWT.CHECK);
		
		savePasswordButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (savePasswordButton.getSelection()) {
					IWizard wizard = getWizard();
					if (wizard instanceof AuthenticationWizard) {
						((AuthenticationWizard)wizard).addAuthCompletionListener(savePasswordListener);
					}
				} else {
					IWizard wizard = getWizard();
					if (wizard instanceof AuthenticationWizard) {
						((AuthenticationWizard)wizard).removeAuthCompletionListener(savePasswordListener);
					}
				}
			}
		});
		setControl(composite);
	}
	
	public void setPageComplete(boolean pageComplete) {
		super.setPageComplete(pageComplete);
	}
	
	public boolean canFlipToNextPage() {
		return useSSL;
	}
	
	private class PasswordFetchListener extends FocusAdapter {

		/* (non-Javadoc)
		 * @see org.eclipse.swt.events.FocusAdapter#focusLost(org.eclipse.swt.events.FocusEvent)
		 */
		@Override
		public void focusLost(FocusEvent focusEvent) {
			Widget widget = focusEvent.widget;
			if (widget instanceof Text) {
				String username = usernameText.getText();
				//Fetch password
				String password = RMSPreferenceUtils.getPassword(username);
				passwordText.setText(password);
				
				//Usability: Put the cursor position to the end of the text field
				int pos = password.length();
				if (pos != -1) {
					passwordText.setSelection(pos);
				}
			}
		}
	}

	private void validatePage() {
		username = usernameText.getText();
		password = passwordText.getText();
		String baseURL = urlCombo.getText();

		if (((username != null) && !(username.intern() == ""))
				&& ((baseURL != null) && !(baseURL.intern() == ""))) {
			setPageComplete(true);
		} else {
			setPageComplete(false);
		}
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getURL() {
		return urlCombo.getText();
	}

	public boolean useSSL() {
		return useSSL;
	}

//	public Button getSavePasswordButton() {
//		return savePasswordButton;
//	}
}
