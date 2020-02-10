/**
 * 
 */
package com.tibco.cep.decision.table.wizard;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * @author aathalye
 *
 */
public class TwoWaySSLConfigPage extends WizardPage {
	
	private Text identityStoreText;
	
	private Text identityStorePasswordText;
	
	private Text identityStoreCertAliasText;
	
	private Combo identityStoreTypeCombo;
	
	private String identityStorePath;
	
	private String identityStorePasswd;
	
	private String certAlias;
	
	private String identityStoreType;
	
	/**
	 * @param pageName
	 */
	public TwoWaySSLConfigPage() {
		super("SSL Client Configuration Page");
		setTitle("Two Way SSL Configuration");
		setDescription("Enter client identity configuration information below");
	}
	
	
	
	protected final String getIdentityStorePath() {
		return identityStorePath;
	}




	protected final String getIdentityStorePasswd() {
		return identityStorePasswd;
	}




	protected final String getCertAlias() {
		return certAlias;
	}




	protected final String getIdentityStoreType() {
		return identityStoreType;
	}




	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(Composite parent) {
		
		final Group composite = new Group(parent, SWT.SHADOW_ETCHED_IN);
		// composite.setText(Messages.Login);
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		composite.setLayout(layout);

		Label  identityStoreLabel= new Label(composite, SWT.NONE);
		identityStoreLabel.setText("Identity Store Path:");
		
		identityStoreText = new Text(composite, SWT.SINGLE | SWT.BORDER);
		GridData id_utext = new GridData(GridData.FILL_HORIZONTAL);
		identityStoreText.setLayoutData(id_utext);
		identityStoreText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				validatePage();
			}
		});
		
		Button browse = new Button(composite, SWT.NONE);
		browse.setText("...");
		browse.addSelectionListener(new SelectionListener() {
		public void widgetDefaultSelected(SelectionEvent e) {
			// TODO Auto-generated method stub
			}

		public void widgetSelected(SelectionEvent e) {
		FileDialog fd = new FileDialog(composite.getShell(), SWT.OPEN);
		fd.setText("Please select a Keystore File");
		String dir = fd.open();
		if (dir != null) {
			identityStoreText.setText(dir);
		}
		}
		});
				
		Label idStorePasswd = new Label(composite, SWT.NONE);
		idStorePasswd.setText("Identity Store Password:");
		
		identityStorePasswordText = new Text(composite, SWT.SINGLE | SWT.BORDER | SWT.PASSWORD);
		GridData data_ptext = new GridData(GridData.FILL_HORIZONTAL);
		identityStorePasswordText.setLayoutData(data_ptext);
		identityStorePasswordText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				validatePage();
			}
		});
		
		Label emptyLabel = new Label(composite, SWT.NONE);
		emptyLabel.setVisible(false);
		
		Label idStoreCertAlias = new Label(composite, SWT.NONE);
		idStoreCertAlias.setText("Identity Store Certificate alias:");
		
		identityStoreCertAliasText = new Text(composite, SWT.SINGLE | SWT.BORDER);
		GridData alias_gd = new GridData(GridData.FILL_HORIZONTAL);
		identityStoreCertAliasText.setLayoutData(alias_gd);
		identityStoreCertAliasText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				validatePage();
			}
		});
		
		Label anotherEmptyLabel = new Label(composite, SWT.NONE);
		anotherEmptyLabel.setVisible(false);
		
		Label trustStore= new Label(composite, SWT.NONE);
		trustStore.setText("Identity Store Type:");
		
		identityStoreTypeCombo = new Combo(composite, SWT.READ_ONLY);
		identityStoreTypeCombo.setItems(new String[] { "JKS", "PKCS12" });
		identityStoreTypeCombo.setText("JKS");
		
		GridData data_combo = new GridData(GridData.FILL_HORIZONTAL);
		identityStoreTypeCombo.setLayoutData(data_combo);
		
		Label emptyLabel1 = new Label(composite, SWT.NONE);
		emptyLabel1.setVisible(false);
		
		setControl(composite);

	}
	
	private void validatePage() {
		identityStorePath = identityStoreText.getText();
		identityStorePasswd = identityStorePasswordText.getText();
		identityStoreType = identityStoreTypeCombo.getText();
		certAlias = identityStoreCertAliasText.getText();

		if (((identityStorePath != null) && !(identityStorePath.intern() == ""))
				&& ((identityStorePasswd != null) && !(identityStorePasswd.intern() == ""))
				&& ((certAlias != null) && !(certAlias.intern() == ""))) {
			setPageComplete(true);
		} else {
			setPageComplete(false);
		}
	}

}
