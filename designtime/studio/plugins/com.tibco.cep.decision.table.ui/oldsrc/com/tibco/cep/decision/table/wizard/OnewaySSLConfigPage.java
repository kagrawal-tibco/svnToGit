package com.tibco.cep.decision.table.wizard;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
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

import com.tibco.cep.decision.table.ui.utils.Messages;
import com.tibco.cep.decision.table.utils.DTImages;

/**
 * 
 * @author hitesh
 * 
 */

public class OnewaySSLConfigPage extends WizardPage {
	
	private Text trustStoreText;
	private Text trustStorePasswordText;
	private Combo trustStoreTypeCombo;
	private Button checkButton;

	protected OnewaySSLConfigPage() {
		super("SSL Page");
		setTitle(Messages.getString("SSL_title"));
		setDescription(Messages.getString("SSL_description"));
		setImageDescriptor(DTImages.getImageDescriptor(DTImages.DT_IMAGES_AUTHENTICATION));
	}
	
	protected String getTrustorePath() {
		return trustStoreText.getText();
	}
	
	protected String getTrustorePassword() {
		return trustStorePasswordText.getText();
	}
	
	protected String getTrustoreType() {
		return trustStoreTypeCombo.getText();
	}
	
	public void createControl(final Composite parent) {
		
		final Group composite = new Group(parent, SWT.SHADOW_ETCHED_IN);
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		composite.setLayout(layout);

		Label  trustLabel= new Label(composite, SWT.NONE);
		trustLabel.setText(Messages.getString("SSL_TrustPath"));
		
		trustStoreText = new Text(composite, SWT.SINGLE | SWT.BORDER);
		GridData trust_utext = new GridData(GridData.FILL_HORIZONTAL);
		trustStoreText.setLayoutData(trust_utext);
		trustStoreText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
			// TODO: Add validate page  
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
		fd.setText(Messages.getString("SSL_FileDialog_Description"));
		String dir = fd.open();
		if (dir != null) {
			trustStoreText.setText(dir);
		}
		}
		});
				
		Label trustPassword= new Label(composite, SWT.NONE);
		trustPassword.setText(Messages.getString("SSL_TrustPassword"));
		
		trustStorePasswordText = new Text(composite, SWT.SINGLE | SWT.BORDER | SWT.PASSWORD);
		GridData data_ptext = new GridData(GridData.FILL_HORIZONTAL);
		trustStorePasswordText.setLayoutData(data_ptext);
		
		Label emptyLabel = new Label(composite, SWT.NONE);
		emptyLabel.setVisible(false);
		
		Label trustStore= new Label(composite, SWT.NONE);
		trustStore.setText(Messages.getString("SSL_TrustStore"));
		
		trustStoreTypeCombo = new Combo(composite, SWT.READ_ONLY);
		trustStoreTypeCombo.setItems(new String[] { "JKS", "PKCS12" });
		trustStoreTypeCombo.setText("JKS");
		
		GridData data_combo = new GridData(GridData.FILL_HORIZONTAL);
		trustStoreTypeCombo.setLayoutData(data_combo);
		
		Label emptyLabel1 = new Label(composite, SWT.NONE);
		emptyLabel1.setVisible(false);
		
		Label sslLabel = new Label(composite, SWT.NONE);
		sslLabel.setText(Messages.getString("SSL_ClientAuth"));

		checkButton = new Button(composite, SWT.CHECK);
		checkButton.setSelection(false);
		checkButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		checkButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				// boolean enableSSL = checkButton.getSelection();
				// if(enableSSL){
				// IWizard wizard = getWizard();
				// if (wizard instanceof AuthenticationWizard){
				// AuthenticationWizard authWizard = (AuthenticationWizard)
				// wizard;
				// authWizard.addPage(new SSLPage("SSL Page"));
				// }
				// }
			}
		});
		
		Label emptyLabel2 = new Label(composite, SWT.NONE);
		emptyLabel2.setVisible(false);
		
		setControl(composite);
	}
}
